package com.sbm.loyalty.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Client getClientSample1() {
        return new Client()
            .id(1)
            .firstName("firstName1")
            .lastName("lastName1")
            .email("email1")
            .dateOfBirth("dateOfBirth1")
            .signUpDate("signUpDate1");
    }

    public static Client getClientSample2() {
        return new Client()
            .id(2)
            .firstName("firstName2")
            .lastName("lastName2")
            .email("email2")
            .dateOfBirth("dateOfBirth2")
            .signUpDate("signUpDate2");
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .id(intCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .dateOfBirth(UUID.randomUUID().toString())
            .signUpDate(UUID.randomUUID().toString());
    }
}
