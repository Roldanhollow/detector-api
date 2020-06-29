package com.detector.detector.common;

import com.detector.detector.model.DNA;
import com.detector.detector.module.DNAdto;
import com.detector.detector.module.Stat;

import java.util.stream.IntStream;

public class Util {
    public static final String [] MUTANT_DNA = new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    public static final String [] HUMAN_DNA = new String[]{"TTCA","TAGT","TAAG","TGGT"};
    public static final String [] WEIRD_THING_DNA = new String[]{"CGCTGC","GGTCCG","AGACGC","CTCGAT","TTCCTA","ATCCTG"};
    public static final String [] INVALID_DNA = new String[]{""};

    public static DNAdto dnaDTOBuilder() {
        DNAdto dnaDTO = new DNAdto();
        dnaDTO.setDna(MUTANT_DNA);
        return dnaDTO;
    }

    public static DNA dnaEntityBuilder(boolean isMutant) {
        DNA dna = new DNA();
        StringBuilder dnaStr = new StringBuilder();
        IntStream.range(0, MUTANT_DNA.length).forEach(index -> dnaStr.append(MUTANT_DNA[index]).append(","));
        dna.setDna(dnaStr.toString());
        dna.setMutant(isMutant);
        return dna;
    }

    public static Stat statBuilder() {
        Stat stat = new Stat();
        stat.setCountMutantDna(2);
        return stat;
    }
}

