package com.detector.detector.service;

import com.detector.detector.common.Util;
import com.detector.detector.data.DNARepository;
import com.detector.detector.module.Stat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class DetectorServiceTests {

    @Mock
    private BrainService brainServiceMock;

    @Mock
    private DNARepository dnaRepositoryMock;

    private DetectorService detectorService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        brainServiceMock = Mockito.mock(BrainService.class);
        detectorService = new DetectorService(brainServiceMock, dnaRepositoryMock);
    }

    @Test
    public void isANewDNA_AsHumanAndItShouldCountHumanDNA_As_1() {
        when(brainServiceMock.isMutantDetected(any())).thenReturn(Boolean.FALSE);
        when(dnaRepositoryMock.findByDna(anyString())).thenReturn(Optional.empty());
        boolean isMutant = detectorService.isMutant(Util.HUMAN_DNA);
        Stat stats = detectorService.getStats();
        assertThat(stats.getCountHumanDna()).isEqualTo(1);
        Assert.assertFalse(isMutant);
    }

    @Test
    public void isANewDNA_AsMutantAndItShouldCountMutantDNA_As_1() {
        when(brainServiceMock.isMutantDetected(any())).thenReturn(Boolean.TRUE);
        when(dnaRepositoryMock.findByDna(anyString())).thenReturn(Optional.empty());
        boolean isMutant = detectorService.isMutant(Util.MUTANT_DNA);
        Stat stats = detectorService.getStats();
        assertThat(stats.getCountMutantDna()).isEqualTo(1);
        Assert.assertTrue(isMutant);
    }

    @Test
    public void isDNA_AlreadyRecorded() {
        when(dnaRepositoryMock.findByDna(anyString())).thenReturn(Optional.of(Util.dnaEntityBuilder(Boolean.TRUE)));
        Assert.assertTrue(detectorService.isMutant(Util.MUTANT_DNA));
        when(dnaRepositoryMock.findByDna(anyString())).thenReturn(Optional.of(Util.dnaEntityBuilder(Boolean.FALSE)));
        Assert.assertFalse(detectorService.isMutant(Util.HUMAN_DNA));
    }

    @Test
    public void isANewDNA_InvalidOrWeirdThing_IDontKnowButCountAsHuman() {
        when(brainServiceMock.isMutantDetected(any())).thenReturn(Boolean.FALSE);
        when(dnaRepositoryMock.findByDna(anyString())).thenReturn(Optional.empty());
        Assert.assertFalse(detectorService.isMutant(Util.INVALID_DNA));
    }
}
