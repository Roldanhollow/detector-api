package com.detector.detector.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "detector.configuration")
public class DetectorConfiguration {

    private int limitValueForHumans;

    private int validNumberSequence;

    private boolean withSpecialCase;

    public int getLimitValueForHumans() {
        return limitValueForHumans;
    }

    public void setLimitValueForHumans(int limitValueForHumans) {
        this.limitValueForHumans = limitValueForHumans;
    }

    public int getValidNumberSequence() {
        return validNumberSequence;
    }

    public void setValidNumberSequence(int validNumberSequence) {
        this.validNumberSequence = validNumberSequence;
    }

    public boolean withSpecialCase() {
        return withSpecialCase;
    }

    public void setWithSpecialCase(boolean withSpecialCase) {
        this.withSpecialCase = withSpecialCase;
    }
}
