package com.sbm.loyalty.service.impl;

import com.sbm.loyalty.domain.RiskEpurationPoints;
import com.sbm.loyalty.repository.RiskEpurationPointsRepository;
import com.sbm.loyalty.service.RiskEpurationPointsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sbm.loyalty.domain.RiskEpurationPoints}.
 */
@Service
@Transactional
public class RiskEpurationPointsServiceImpl implements RiskEpurationPointsService {

    private static final Logger LOG = LoggerFactory.getLogger(RiskEpurationPointsServiceImpl.class);

    private final RiskEpurationPointsRepository riskEpurationPointsRepository;

    public RiskEpurationPointsServiceImpl(RiskEpurationPointsRepository riskEpurationPointsRepository) {
        this.riskEpurationPointsRepository = riskEpurationPointsRepository;
    }

    @Override
    public RiskEpurationPoints save(RiskEpurationPoints riskEpurationPoints) {
        LOG.debug("Request to save RiskEpurationPoints : {}", riskEpurationPoints);
        return riskEpurationPointsRepository.save(riskEpurationPoints);
    }

    @Override
    public RiskEpurationPoints update(RiskEpurationPoints riskEpurationPoints) {
        LOG.debug("Request to update RiskEpurationPoints : {}", riskEpurationPoints);
        return riskEpurationPointsRepository.save(riskEpurationPoints);
    }

    @Override
    public Optional<RiskEpurationPoints> partialUpdate(RiskEpurationPoints riskEpurationPoints) {
        LOG.debug("Request to partially update RiskEpurationPoints : {}", riskEpurationPoints);

        return riskEpurationPointsRepository
            .findById(riskEpurationPoints.getId())
            .map(existingRiskEpurationPoints -> {
                if (riskEpurationPoints.getPoints() != null) {
                    existingRiskEpurationPoints.setPoints(riskEpurationPoints.getPoints());
                }
                if (riskEpurationPoints.getValidUntil() != null) {
                    existingRiskEpurationPoints.setValidUntil(riskEpurationPoints.getValidUntil());
                }

                return existingRiskEpurationPoints;
            })
            .map(riskEpurationPointsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RiskEpurationPoints> findAll(Pageable pageable) {
        LOG.debug("Request to get all RiskEpurationPoints");
        return riskEpurationPointsRepository.findAll(pageable);
    }

    /**
     *  Get all the riskEpurationPoints where LoyaltyPoints is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RiskEpurationPoints> findAllWhereLoyaltyPointsIsNull() {
        LOG.debug("Request to get all riskEpurationPoints where LoyaltyPoints is null");
        return StreamSupport.stream(riskEpurationPointsRepository.findAll().spliterator(), false)
            .filter(riskEpurationPoints -> riskEpurationPoints.getLoyaltyPoints() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RiskEpurationPoints> findOne(Long id) {
        LOG.debug("Request to get RiskEpurationPoints : {}", id);
        return riskEpurationPointsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RiskEpurationPoints : {}", id);
        riskEpurationPointsRepository.deleteById(id);
    }
}
