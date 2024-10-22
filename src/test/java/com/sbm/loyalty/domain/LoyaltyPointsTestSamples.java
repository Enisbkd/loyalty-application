package com.sbm.loyalty.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LoyaltyPointsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static LoyaltyPoints getLoyaltyPointsSample1() {
        return new LoyaltyPoints().id(1L).statusPoints(1).myPoints(1);
    }

    public static LoyaltyPoints getLoyaltyPointsSample2() {
        return new LoyaltyPoints().id(2L).statusPoints(2).myPoints(2);
    }

    public static LoyaltyPoints getLoyaltyPointsRandomSampleGenerator() {
        return new LoyaltyPoints()
            .id(longCount.incrementAndGet())
            .statusPoints(intCount.incrementAndGet())
            .myPoints(intCount.incrementAndGet());
    }
}
