package com.sbm.loyalty.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RiskEpurationPointsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RiskEpurationPoints getRiskEpurationPointsSample1() {
        return new RiskEpurationPoints().id(1L).points(1).validUntil(1);
    }

    public static RiskEpurationPoints getRiskEpurationPointsSample2() {
        return new RiskEpurationPoints().id(2L).points(2).validUntil(2);
    }

    public static RiskEpurationPoints getRiskEpurationPointsRandomSampleGenerator() {
        return new RiskEpurationPoints()
            .id(longCount.incrementAndGet())
            .points(intCount.incrementAndGet())
            .validUntil(intCount.incrementAndGet());
    }
}
