package com.sbm.loyalty.service;

import com.sbm.loyalty.domain.RiskEpurationPoints;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sbm.loyalty.domain.RiskEpurationPoints}.
 */
public interface RiskEpurationPointsService {
    /**
     * Save a riskEpurationPoints.
     *
     * @param riskEpurationPoints the entity to save.
     * @return the persisted entity.
     */
    RiskEpurationPoints save(RiskEpurationPoints riskEpurationPoints);

    /**
     * Updates a riskEpurationPoints.
     *
     * @param riskEpurationPoints the entity to update.
     * @return the persisted entity.
     */
    RiskEpurationPoints update(RiskEpurationPoints riskEpurationPoints);

    /**
     * Partially updates a riskEpurationPoints.
     *
     * @param riskEpurationPoints the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RiskEpurationPoints> partialUpdate(RiskEpurationPoints riskEpurationPoints);

    /**
     * Get all the riskEpurationPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RiskEpurationPoints> findAll(Pageable pageable);

    /**
     * Get all the RiskEpurationPoints where LoyaltyPoints is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<RiskEpurationPoints> findAllWhereLoyaltyPointsIsNull();

    /**
     * Get the "id" riskEpurationPoints.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskEpurationPoints> findOne(Long id);

    /**
     * Delete the "id" riskEpurationPoints.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
