package com.detector.detector.data;

import com.detector.detector.model.DNA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DNARepository extends JpaRepository<DNA, Long> {
    Optional<DNA> findByDna(final String dna);
}
