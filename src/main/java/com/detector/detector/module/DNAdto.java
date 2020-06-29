package com.detector.detector.module;

import java.io.Serializable;

public class DNAdto implements Serializable {
    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
