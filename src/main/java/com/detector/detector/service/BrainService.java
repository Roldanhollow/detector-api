package com.detector.detector.service;

import com.detector.detector.configuration.DetectorConfiguration;
import com.detector.detector.module.ModeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BrainService {
    private static final Logger LOGGER = LogManager.getLogger(BrainService.class);

    protected final DetectorConfiguration configuration;

    @Autowired
    public BrainService(DetectorConfiguration configuration) {
        this.configuration = configuration;
    }

    final String PATTERN = "^(.)\\1*$";

    /**
     * Detecta si el DNA pertenece a un mutante
     * @param dna DNA
     * @return (true/false)
     */
    public boolean isMutantDetected(String[] dna) {
        LOGGER.info(" == Starting process == ");
        int matchCounter = 0;
        for (ModeEnum modeEnum : ModeEnum.values()) {
            matchCounter += executeCase(modeEnum, dna);
            if (matchCounter > configuration.getLimitValueForHumans()) {
                LOGGER.info(" == Process done. Mutant DNA == ");
                return true;
            }
        }
        LOGGER.info(" == Process done. Human DNA == ");
        return false;
    }

    /**
     * Ejecuta un caso dependiendo del modo de ejecucion
     * @param modeEnum Modo {{@link ModeEnum}} de ejecucion
     * @param dna DNA
     * @return Cantidad de casos encontrados
     */
    private int executeCase(ModeEnum modeEnum, String[] dna) {
        LOGGER.info("    [+] Finding patterns on " + modeEnum.name() + " mode ");
        switch (modeEnum) {
            case HORIZONTAL:
                return matchCounter(dna);
            case VERTICAL:
                return matchCounter(turnVerticalDna(dna));
            case DIAGONAL:
                return matchCounter(turnDiagonalDna(dna));
            case DIAGONAL_INVERSE:
                if (configuration.withSpecialCase()) {
                    return matchCounter(turnDiagonalDna(turnInverseDna(dna)));
                }
        }
        return 0;
    }

    /**
     * Se encarga de buscar {VALID_SEQUENCE} iguales bases consecutivas en el DNA
     * @param dna DNA
     * @return Cantidad de casos
     */
    private int matchCounter(String[] dna) {
        int counter = 0;
        for (String dnaPart: dna) {
            for (int row = 0; row <= dnaPart.length() - configuration.getValidNumberSequence(); row++) {
                String fragment = dnaPart.substring(row, configuration.getValidNumberSequence() + row);
                if (fragment.matches(PATTERN)) {
                    LOGGER.info("        - Fragment [" + fragment + "] found on ["+ dnaPart +"]");
                    counter++;
                    break;
                }
            }
        }
        LOGGER.info("        >> Found " + counter + " case(s) ");
        return counter;
    }

    /**
     * Pasa un DNA a modo vertical
     * @param dna DNA
     * @return DNA vertical
     */
    private String[] turnVerticalDna(String[] dna) {
        String[] newDna = new String[dna.length];
        for (int column = 0; column < dna.length; column++)
            for (int row = 0; row < dna.length; row++) {
                newDna[column] = Objects.nonNull(newDna[column]) ? newDna[column] : "";
                newDna[column] += dna[row].split("")[column];
            }
        LOGGER.info("     [+++] DNA was turned vertical successfully == ");
        return newDna;
    }

    /**
     * Invierte cada parte de un DNA
     * @param dna DNA
     * @return DNA invertido
     */
    private String[] turnInverseDna(String[] dna) {
        String[] newDna = new String[dna.length];
        for (int index = 0; index< dna.length; index++) {
            newDna[index] = new StringBuilder(dna[index]).reverse().toString();
        }
        LOGGER.info("     [+++] DNA was turned inverse successfully == ");
        return newDna;
    }

    /**
     * Extrae las diagonales validas de un DNA teniendo como criterio {VALID_SEQUENCE}
     * @param dna DNA
     * @return Diagonales validas
     */
    private String[] turnDiagonalDna(String[] dna) {
        int reference = ((dna.length - configuration.getValidNumberSequence()) * 2) + 1;
        String[] newDna = new String[reference];
        for (int mirror = 0; mirror <= dna.length - configuration.getValidNumberSequence(); mirror++) {
            int position = Math.max((mirror * 2) - 1, 0); //0,0    1,1       2,3     3,5     4,7    ...
            for (int sector = 0; sector < dna.length - mirror; sector++) {
                newDna[position] = Objects.nonNull(newDna[position]) ? newDna[position] : "";
                newDna[position] += dna[sector].split("")[mirror + sector];

                if (position > 0) {
                    newDna[position + 1] = Objects.nonNull(newDna[position + 1]) ? newDna[position + 1] : "";
                    newDna[position + 1] += dna[mirror + sector].split("")[sector];
                }
            }
        }
        LOGGER.info("     [+++] DNA was turned vertical successfully == ");
        return newDna;
    }
}
