package com.sbm.loyalty.service;

import com.sbm.loyalty.domain.LoyaltyPoints;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sbm.loyalty.domain.LoyaltyPoints}.
 */
public interface LoyaltyPointsService {
    /**
     * Save a loyaltyPoints.
     *
     * @param loyaltyPoints the entity to save.
     * @return the persisted entity.
     */
    LoyaltyPoints save(LoyaltyPoints loyaltyPoints);

    /**
     * Updates a loyaltyPoints.
     *
     * @param loyaltyPoints the entity to update.
     * @return the persisted entity.
     */
    LoyaltyPoints update(LoyaltyPoints loyaltyPoints);

    /**
     * Partially updates a loyaltyPoints.
     *
     * @param loyaltyPoints the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoyaltyPoints> partialUpdate(LoyaltyPoints loyaltyPoints);

    /**
     * Get all the loyaltyPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoyaltyPoints> findAll(Pageable pageable);

    /**
     * Get the "id" loyaltyPoints.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoyaltyPoints> findOne(Long id);

    /**
     * Delete the "id" loyaltyPoints.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
