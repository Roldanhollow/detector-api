package com.detector.detector.service;

import com.detector.detector.data.DNARepository;
import com.detector.detector.model.DNA;
import com.detector.detector.module.Stat;
import com.detector.detector.module.Validator;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class DetectorService {
    private static final Logger LOGGER = LogManager.getLogger(DetectorService.class);

    private final BrainService brainService;
    private final DNARepository dnaRepository;

    private final Counter countMutantDNA;
    private final Counter countHumanDNA;

    @Autowired
    public DetectorService(BrainService brainService, DNARepository dnaRepository) {
        this.brainService = brainService;
        this.dnaRepository = dnaRepository;

        MeterRegistry registry = new SimpleMeterRegistry();
        countMutantDNA = Counter.builder("count_mutant_dna").register(registry);
        countHumanDNA = Counter.builder("count_human_dna").register(registry);
    }

    /**
     * Obtiene el conteo requerido por Magneto
     * @return Objeto {{@link Stat}} con los conteos y ratio
     */
    public Stat getStats() {
        Stat stat = new Stat();
        stat.setCountMutantDna((int) countMutantDNA.count());
        stat.setCountHumanDna((int) countHumanDNA.count());
        stat.setRatio((stat.getCountHumanDna() > 0) ? stat.getCountMutantDna() / stat.getCountHumanDna() : 0);
        return stat;
    }


    /**
     * Encargado de determinar si un DNA pertenece a un mutante o no
     * @param dna DNA
     * @return (true/false)
     */
    public boolean isMutant(String[] dna) {
        String dnaStr = getDNA(dna);
        Optional<DNA> dnaOpt = dnaRepository.findByDna(dnaStr);
        boolean dnaAlreadyExists = dnaOpt.isPresent();
        if (dnaAlreadyExists) {
            DNA dnaEntity = dnaOpt.get();
            LOGGER.info(" == Process done. " + (dnaEntity.isMutant()? "Mutant":"Human") +" DNA == ");
            return dnaOpt.get().isMutant();
        }else{
            return initMutantCheck(dna, dnaStr);
        }
    }

    /**
     * Inicia el proceso de chequeo con el DNA
     * Note: If DNA is invalid this is count as human
     * @param dna DNA
     * @param dnaStr String que contiene el DNA
     * @return (true/false)
     */
    private boolean initMutantCheck(String[] dna, String dnaStr) {
        saveDna(dnaStr);
        if (!Validator.isValidDNA(dna)) {
            LOGGER.info(" == Process done. WARNING: This is a weird DNA but it can be count as HUMAN == ");
            countHumanDNA.increment();
            return false;
        }
        LOGGER.info(" == The DNA is OK == ");
        boolean isMutant = brainService.isMutantDetected(dna);
        incrementStats(isMutant);
        return isMutant;
    }

    /**
     * Registra nuevos DNA's
     * @param dnaStr String que contiene el DNA
     */
    private void saveDna(String dnaStr) {
        DNA dnaEntity = new DNA();
        dnaEntity.setDna(dnaStr);
        dnaRepository.save(dnaEntity);
        LOGGER.info(" == New DNA saved == ");
    }

    /**
     * Convierte el array dado por el DNA a String
     * @param dna DNA
     * @return String que contiene el DNA
     */
    private String getDNA(String[] dna) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, dna.length).forEach(index -> sb.append(dna[index]).append(","));
        return sb.toString();
    }

    /**
     * Encargado de incrementar los contadores basado en si es mutante o no
     * @param isMutant (true/false)
     */
    private void incrementStats(boolean isMutant) {
        if (isMutant) {
            countMutantDNA.increment();
        } else {
            countHumanDNA.increment();
        }
    }
}
