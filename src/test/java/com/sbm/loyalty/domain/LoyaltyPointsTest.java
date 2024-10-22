package com.sbm.loyalty.domain;

import static com.sbm.loyalty.domain.LoyaltyPointsTestSamples.*;
import static com.sbm.loyalty.domain.RiskEpurationPointsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.loyalty.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoyaltyPoints.class);
        LoyaltyPoints loyaltyPoints1 = getLoyaltyPointsSample1();
        LoyaltyPoints loyaltyPoints2 = new LoyaltyPoints();
        assertThat(loyaltyPoints1).isNotEqualTo(loyaltyPoints2);

        loyaltyPoints2.setId(loyaltyPoints1.getId());
        assertThat(loyaltyPoints1).isEqualTo(loyaltyPoints2);

        loyaltyPoints2 = getLoyaltyPointsSample2();
        assertThat(loyaltyPoints1).isNotEqualTo(loyaltyPoints2);
    }

    @Test
    void riskEpurationPointsTest() {
        LoyaltyPoints loyaltyPoints = getLoyaltyPointsRandomSampleGenerator();
        RiskEpurationPoints riskEpurationPointsBack = getRiskEpurationPointsRandomSampleGenerator();

        loyaltyPoints.setRiskEpurationPoints(riskEpurationPointsBack);
        assertThat(loyaltyPoints.getRiskEpurationPoints()).isEqualTo(riskEpurationPointsBack);

        loyaltyPoints.riskEpurationPoints(null);
        assertThat(loyaltyPoints.getRiskEpurationPoints()).isNull();
    }
}
