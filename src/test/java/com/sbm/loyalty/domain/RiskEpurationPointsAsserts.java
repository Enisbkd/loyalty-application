package com.sbm.loyalty.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RiskEpurationPointsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiskEpurationPointsAllPropertiesEquals(RiskEpurationPoints expected, RiskEpurationPoints actual) {
        assertRiskEpurationPointsAutoGeneratedPropertiesEquals(expected, actual);
        assertRiskEpurationPointsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiskEpurationPointsAllUpdatablePropertiesEquals(RiskEpurationPoints expected, RiskEpurationPoints actual) {
        assertRiskEpurationPointsUpdatableFieldsEquals(expected, actual);
        assertRiskEpurationPointsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiskEpurationPointsAutoGeneratedPropertiesEquals(RiskEpurationPoints expected, RiskEpurationPoints actual) {
        assertThat(expected)
            .as("Verify RiskEpurationPoints auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiskEpurationPointsUpdatableFieldsEquals(RiskEpurationPoints expected, RiskEpurationPoints actual) {
        assertThat(expected)
            .as("Verify RiskEpurationPoints relevant properties")
            .satisfies(e -> assertThat(e.getPoints()).as("check points").isEqualTo(actual.getPoints()))
            .satisfies(e -> assertThat(e.getValidUntil()).as("check validUntil").isEqualTo(actual.getValidUntil()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiskEpurationPointsUpdatableRelationshipsEquals(RiskEpurationPoints expected, RiskEpurationPoints actual) {
        // empty method
    }
}