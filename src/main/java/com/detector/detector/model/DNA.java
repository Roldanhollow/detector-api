package com.detector.detector.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="dnas")
public class DNA implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dnas_sequence")
    @SequenceGenerator(name = "dnas_sequence", sequenceName = "dnas_sequence", allocationSize = 1)
    private Long id;

    @Column
    private String dna;

    @Column
    private boolean mutant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public boolean isMutant() {
        return mutant;
    }

    public void setMutant(boolean mutant) {
        this.mutant = mutant;
    }
}
