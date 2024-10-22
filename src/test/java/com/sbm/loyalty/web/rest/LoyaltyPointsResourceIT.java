package com.sbm.loyalty.web.rest;

import static com.sbm.loyalty.domain.LoyaltyPointsAsserts.*;
import static com.sbm.loyalty.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.loyalty.IntegrationTest;
import com.sbm.loyalty.domain.LoyaltyPoints;
import com.sbm.loyalty.repository.LoyaltyPointsRepository;
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
 * Integration tests for the {@link LoyaltyPointsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoyaltyPointsResourceIT {

    private static final Integer DEFAULT_STATUS_POINTS = 1;
    private static final Integer UPDATED_STATUS_POINTS = 2;

    private static final Integer DEFAULT_MY_POINTS = 1;
    private static final Integer UPDATED_MY_POINTS = 2;

    private static final String ENTITY_API_URL = "/api/loyalty-points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LoyaltyPointsRepository loyaltyPointsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoyaltyPointsMockMvc;

    private LoyaltyPoints loyaltyPoints;

    private LoyaltyPoints insertedLoyaltyPoints;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoyaltyPoints createEntity() {
        return new LoyaltyPoints().statusPoints(DEFAULT_STATUS_POINTS).myPoints(DEFAULT_MY_POINTS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoyaltyPoints createUpdatedEntity() {
        return new LoyaltyPoints().statusPoints(UPDATED_STATUS_POINTS).myPoints(UPDATED_MY_POINTS);
    }

    @BeforeEach
    public void initTest() {
        loyaltyPoints = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedLoyaltyPoints != null) {
            loyaltyPointsRepository.delete(insertedLoyaltyPoints);
            insertedLoyaltyPoints = null;
        }
    }

    @Test
    @Transactional
    void createLoyaltyPoints() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LoyaltyPoints
        var returnedLoyaltyPoints = om.readValue(
            restLoyaltyPointsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loyaltyPoints)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LoyaltyPoints.class
        );

        // Validate the LoyaltyPoints in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLoyaltyPointsUpdatableFieldsEquals(returnedLoyaltyPoints, getPersistedLoyaltyPoints(returnedLoyaltyPoints));

        insertedLoyaltyPoints = returnedLoyaltyPoints;
    }

    @Test
    @Transactional
    void createLoyaltyPointsWithExistingId() throws Exception {
        // Create the LoyaltyPoints with an existing ID
        loyaltyPoints.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoyaltyPointsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loyaltyPoints)))
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoyaltyPoints() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        // Get all the loyaltyPointsList
        restLoyaltyPointsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loyaltyPoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusPoints").value(hasItem(DEFAULT_STATUS_POINTS)))
            .andExpect(jsonPath("$.[*].myPoints").value(hasItem(DEFAULT_MY_POINTS)));
    }

    @Test
    @Transactional
    void getLoyaltyPoints() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        // Get the loyaltyPoints
        restLoyaltyPointsMockMvc
            .perform(get(ENTITY_API_URL_ID, loyaltyPoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loyaltyPoints.getId().intValue()))
            .andExpect(jsonPath("$.statusPoints").value(DEFAULT_STATUS_POINTS))
            .andExpect(jsonPath("$.myPoints").value(DEFAULT_MY_POINTS));
    }

    @Test
    @Transactional
    void getNonExistingLoyaltyPoints() throws Exception {
        // Get the loyaltyPoints
        restLoyaltyPointsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoyaltyPoints() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loyaltyPoints
        LoyaltyPoints updatedLoyaltyPoints = loyaltyPointsRepository.findById(loyaltyPoints.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLoyaltyPoints are not directly saved in db
        em.detach(updatedLoyaltyPoints);
        updatedLoyaltyPoints.statusPoints(UPDATED_STATUS_POINTS).myPoints(UPDATED_MY_POINTS);

        restLoyaltyPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoyaltyPoints.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLoyaltyPoints))
            )
            .andExpect(status().isOk());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLoyaltyPointsToMatchAllProperties(updatedLoyaltyPoints);
    }

    @Test
    @Transactional
    void putNonExistingLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loyaltyPoints.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(loyaltyPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(loyaltyPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loyaltyPoints)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoyaltyPointsWithPatch() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loyaltyPoints using partial update
        LoyaltyPoints partialUpdatedLoyaltyPoints = new LoyaltyPoints();
        partialUpdatedLoyaltyPoints.setId(loyaltyPoints.getId());

        restLoyaltyPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoyaltyPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLoyaltyPoints))
            )
            .andExpect(status().isOk());

        // Validate the LoyaltyPoints in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoyaltyPointsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLoyaltyPoints, loyaltyPoints),
            getPersistedLoyaltyPoints(loyaltyPoints)
        );
    }

    @Test
    @Transactional
    void fullUpdateLoyaltyPointsWithPatch() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loyaltyPoints using partial update
        LoyaltyPoints partialUpdatedLoyaltyPoints = new LoyaltyPoints();
        partialUpdatedLoyaltyPoints.setId(loyaltyPoints.getId());

        partialUpdatedLoyaltyPoints.statusPoints(UPDATED_STATUS_POINTS).myPoints(UPDATED_MY_POINTS);

        restLoyaltyPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoyaltyPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLoyaltyPoints))
            )
            .andExpect(status().isOk());

        // Validate the LoyaltyPoints in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoyaltyPointsUpdatableFieldsEquals(partialUpdatedLoyaltyPoints, getPersistedLoyaltyPoints(partialUpdatedLoyaltyPoints));
    }

    @Test
    @Transactional
    void patchNonExistingLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loyaltyPoints.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(loyaltyPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(loyaltyPoints))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoyaltyPoints() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loyaltyPoints.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyaltyPointsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(loyaltyPoints)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoyaltyPoints in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoyaltyPoints() throws Exception {
        // Initialize the database
        insertedLoyaltyPoints = loyaltyPointsRepository.saveAndFlush(loyaltyPoints);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the loyaltyPoints
        restLoyaltyPointsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loyaltyPoints.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return loyaltyPointsRepository.count();
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

    protected LoyaltyPoints getPersistedLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        return loyaltyPointsRepository.findById(loyaltyPoints.getId()).orElseThrow();
    }

    protected void assertPersistedLoyaltyPointsToMatchAllProperties(LoyaltyPoints expectedLoyaltyPoints) {
        assertLoyaltyPointsAllPropertiesEquals(expectedLoyaltyPoints, getPersistedLoyaltyPoints(expectedLoyaltyPoints));
    }

    protected void assertPersistedLoyaltyPointsToMatchUpdatableProperties(LoyaltyPoints expectedLoyaltyPoints) {
        assertLoyaltyPointsAllUpdatablePropertiesEquals(expectedLoyaltyPoints, getPersistedLoyaltyPoints(expectedLoyaltyPoints));
    }
}
