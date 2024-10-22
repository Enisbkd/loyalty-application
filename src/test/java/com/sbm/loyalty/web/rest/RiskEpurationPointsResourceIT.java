package com.sbm.loyalty.web.rest;

import static com.sbm.loyalty.domain.RiskEpurationPointsAsserts.*;
import static com.sbm.loyalty.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.loyalty.IntegrationTest;
import com.sbm.loyalty.domain.RiskEpurationPoints;
import com.sbm.loyalty.repository.RiskEpurationPointsRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RiskEpurationPointsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RiskEpurationPointsResourceIT {

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final Integer DEFAULT_VALID_UNTIL = 1;
    private static final Integer UPDATED_VALID_UNTIL = 2;

    private static final String ENTITY_API_URL = "/api/risk-epuration-points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RiskEpurationPointsRepository riskEpurationPointsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiskEpurationPointsMockMvc;

    private RiskEpurationPoints riskEpurationPoints;

    private RiskEpurationPoints insertedRiskEpurationPoints;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskEpurationPoints createEntity() {
        return new RiskEpurationPoints().points(DEFAULT_POINTS).validUntil(DEFAULT_VALID_UNTIL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskEpurationPoints createUpdatedEntity() {
        return new RiskEpurationPoints().points(UPDATED_POINTS).validUntil(UPDATED_VALID_UNTIL);
    }

    @BeforeEach
    public void initTest() {
        riskEpurationPoints = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRiskEpurationPoints != null) {
            riskEpurationPointsRepository.delete(insertedRiskEpurationPoints);
            insertedRiskEpurationPoints = null;
        }
    }

    @Test
    @Transactional
    void createRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RiskEpurationPoints
        var returnedRiskEpurationPoints = om.readValue(
            restRiskEpurationPointsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskEpurationPoints)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RiskEpurationPoints.class
        );

        // Validate the RiskEpurationPoints in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRiskEpurationPointsUpdatableFieldsEquals(
            returnedRiskEpurationPoints,
            getPersistedRiskEpurationPoints(returnedRiskEpurationPoints)
        );

        insertedRiskEpurationPoints = returnedRiskEpurationPoints;
    }

    @Test
    @Transactional
    void createRiskEpurationPointsWithExistingId() throws Exception {
        // Create the RiskEpurationPoints with an existing ID
        riskEpurationPoints.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskEpurationPointsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskEpurationPoints)))
            .andExpect(status().isBadRequest());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRiskEpurationPoints() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        // Get all the riskEpurationPointsList
        restRiskEpurationPointsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskEpurationPoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL)));
    }

    @Test
    @Transactional
    void getRiskEpurationPoints() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        // Get the riskEpurationPoints
        restRiskEpurationPointsMockMvc
            .perform(get(ENTITY_API_URL_ID, riskEpurationPoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(riskEpurationPoints.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL));
    }

    @Test
    @Transactional
    void getNonExistingRiskEpurationPoints() throws Exception {
        // Get the riskEpurationPoints
        restRiskEpurationPointsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRiskEpurationPoints() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskEpurationPoints
        RiskEpurationPoints updatedRiskEpurationPoints = riskEpurationPointsRepository.findById(riskEpurationPoints.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRiskEpurationPoints are not directly saved in db
        em.detach(updatedRiskEpurationPoints);
        updatedRiskEpurationPoints.points(UPDATED_POINTS).validUntil(UPDATED_VALID_UNTIL);

        restRiskEpurationPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRiskEpurationPoints.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRiskEpurationPoints))
            )
            .andExpect(status().isOk());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRiskEpurationPointsToMatchAllProperties(updatedRiskEpurationPoints);
    }

    @Test
    @Transactional
    void putNonExistingRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riskEpurationPoints.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskEpurationPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskEpurationPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskEpurationPoints)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiskEpurationPointsWithPatch() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskEpurationPoints using partial update
        RiskEpurationPoints partialUpdatedRiskEpurationPoints = new RiskEpurationPoints();
        partialUpdatedRiskEpurationPoints.setId(riskEpurationPoints.getId());

        partialUpdatedRiskEpurationPoints.points(UPDATED_POINTS).validUntil(UPDATED_VALID_UNTIL);

        restRiskEpurationPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskEpurationPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskEpurationPoints))
            )
            .andExpect(status().isOk());

        // Validate the RiskEpurationPoints in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskEpurationPointsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRiskEpurationPoints, riskEpurationPoints),
            getPersistedRiskEpurationPoints(riskEpurationPoints)
        );
    }

    @Test
    @Transactional
    void fullUpdateRiskEpurationPointsWithPatch() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskEpurationPoints using partial update
        RiskEpurationPoints partialUpdatedRiskEpurationPoints = new RiskEpurationPoints();
        partialUpdatedRiskEpurationPoints.setId(riskEpurationPoints.getId());

        partialUpdatedRiskEpurationPoints.points(UPDATED_POINTS).validUntil(UPDATED_VALID_UNTIL);

        restRiskEpurationPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskEpurationPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskEpurationPoints))
            )
            .andExpect(status().isOk());

        // Validate the RiskEpurationPoints in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskEpurationPointsUpdatableFieldsEquals(
            partialUpdatedRiskEpurationPoints,
            getPersistedRiskEpurationPoints(partialUpdatedRiskEpurationPoints)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riskEpurationPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskEpurationPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskEpurationPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRiskEpurationPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskEpurationPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskEpurationPointsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(riskEpurationPoints)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskEpurationPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRiskEpurationPoints() throws Exception {
        // Initialize the database
        insertedRiskEpurationPoints = riskEpurationPointsRepository.saveAndFlush(riskEpurationPoints);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the riskEpurationPoints
        restRiskEpurationPointsMockMvc
            .perform(delete(ENTITY_API_URL_ID, riskEpurationPoints.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return riskEpurationPointsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected RiskEpurationPoints getPersistedRiskEpurationPoints(RiskEpurationPoints riskEpurationPoints) {
        return riskEpurationPointsRepository.findById(riskEpurationPoints.getId()).orElseThrow();
    }

    protected void assertPersistedRiskEpurationPointsToMatchAllProperties(RiskEpurationPoints expectedRiskEpurationPoints) {
        assertRiskEpurationPointsAllPropertiesEquals(
            expectedRiskEpurationPoints,
            getPersistedRiskEpurationPoints(expectedRiskEpurationPoints)
        );
    }

    protected void assertPersistedRiskEpurationPointsToMatchUpdatableProperties(RiskEpurationPoints expectedRiskEpurationPoints) {
        assertRiskEpurationPointsAllUpdatablePropertiesEquals(
            expectedRiskEpurationPoints,
            getPersistedRiskEpurationPoints(expectedRiskEpurationPoints)
        );
    }
}
