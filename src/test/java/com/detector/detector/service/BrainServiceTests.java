package com.detector.detector.service;

import com.detector.detector.common.Util;
import com.detector.detector.configuration.DetectorConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class BrainServiceTests {

    @Mock
    private DetectorConfiguration configurationMock;

    private BrainService brainService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        brainService = new BrainService(configurationMock);
    }

    @Test
    public void isMutantDetected_OK() {
        commonDeclarations();
        Assert.assertTrue(brainService.isMutantDetected(Util.MUTANT_DNA));
    }

    @Test
    public void isHumanDetected_OK() {
        commonDeclarations();
        Assert.assertFalse(brainService.isMutantDetected(Util.HUMAN_DNA));
    }

    @Test
    public void isAHumanWeirdDetected_OK() {
        commonDeclarations();
        when(configurationMock.withSpecialCase()).thenReturn(Boolean.TRUE);
        Assert.assertFalse(brainService.isMutantDetected(Util.WEIRD_THING_DNA));
    }

    private void commonDeclarations() {
        when(configurationMock.getLimitValueForHumans()).thenReturn(1);
        when(configurationMock.getValidNumberSequence()).thenReturn(4);
        when(configurationMock.withSpecialCase()).thenReturn(Boolean.FALSE);
    }
}
