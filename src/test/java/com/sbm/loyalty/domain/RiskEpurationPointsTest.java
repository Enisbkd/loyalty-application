package com.sbm.loyalty.domain;

import static com.sbm.loyalty.domain.LoyaltyPointsTestSamples.*;
import static com.sbm.loyalty.domain.RiskEpurationPointsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.loyalty.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiskEpurationPointsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskEpurationPoints.class);
        RiskEpurationPoints riskEpurationPoints1 = getRiskEpurationPointsSample1();
        RiskEpurationPoints riskEpurationPoints2 = new RiskEpurationPoints();
        assertThat(riskEpurationPoints1).isNotEqualTo(riskEpurationPoints2);

        riskEpurationPoints2.setId(riskEpurationPoints1.getId());
        assertThat(riskEpurationPoints1).isEqualTo(riskEpurationPoints2);

        riskEpurationPoints2 = getRiskEpurationPointsSample2();
        assertThat(riskEpurationPoints1).isNotEqualTo(riskEpurationPoints2);
    }

    @Test
    void loyaltyPointsTest() {
        RiskEpurationPoints riskEpurationPoints = getRiskEpurationPointsRandomSampleGenerator();
        LoyaltyPoints loyaltyPointsBack = getLoyaltyPointsRandomSampleGenerator();

        riskEpurationPoints.setLoyaltyPoints(loyaltyPointsBack);
        assertThat(riskEpurationPoints.getLoyaltyPoints()).isEqualTo(loyaltyPointsBack);
        assertThat(loyaltyPointsBack.getRiskEpurationPoints()).isEqualTo(riskEpurationPoints);

        riskEpurationPoints.loyaltyPoints(null);
        assertThat(riskEpurationPoints.getLoyaltyPoints()).isNull();
        assertThat(loyaltyPointsBack.getRiskEpurationPoints()).isNull();
    }
}
