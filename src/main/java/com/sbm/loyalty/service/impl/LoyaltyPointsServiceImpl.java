package com.sbm.loyalty.service.impl;

import com.sbm.loyalty.domain.LoyaltyPoints;
import com.sbm.loyalty.repository.LoyaltyPointsRepository;
import com.sbm.loyalty.service.LoyaltyPointsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sbm.loyalty.domain.LoyaltyPoints}.
 */
@Service
@Transactional
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

    private static final Logger LOG = LoggerFactory.getLogger(LoyaltyPointsServiceImpl.class);

    private final LoyaltyPointsRepository loyaltyPointsRepository;

    public LoyaltyPointsServiceImpl(LoyaltyPointsRepository loyaltyPointsRepository) {
        this.loyaltyPointsRepository = loyaltyPointsRepository;
    }

    @Override
    public LoyaltyPoints save(LoyaltyPoints loyaltyPoints) {
        LOG.debug("Request to save LoyaltyPoints : {}", loyaltyPoints);
        return loyaltyPointsRepository.save(loyaltyPoints);
    }

    @Override
    public LoyaltyPoints update(LoyaltyPoints loyaltyPoints) {
        LOG.debug("Request to update LoyaltyPoints : {}", loyaltyPoints);
        return loyaltyPointsRepository.save(loyaltyPoints);
    }

    @Override
    public Optional<LoyaltyPoints> partialUpdate(LoyaltyPoints loyaltyPoints) {
        LOG.debug("Request to partially update LoyaltyPoints : {}", loyaltyPoints);

        return loyaltyPointsRepository
            .findById(loyaltyPoints.getId())
            .map(existingLoyaltyPoints -> {
                if (loyaltyPoints.getStatusPoints() != null) {
                    existingLoyaltyPoints.setStatusPoints(loyaltyPoints.getStatusPoints());
                }
                if (loyaltyPoints.getMyPoints() != null) {
                    existingLoyaltyPoints.setMyPoints(loyaltyPoints.getMyPoints());
                }

                return existingLoyaltyPoints;
            })
            .map(loyaltyPointsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoyaltyPoints> findAll(Pageable pageable) {
        LOG.debug("Request to get all LoyaltyPoints");
        return loyaltyPointsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoyaltyPoints> findOne(Long id) {
        LOG.debug("Request to get LoyaltyPoints : {}", id);
        return loyaltyPointsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LoyaltyPoints : {}", id);
        loyaltyPointsRepository.deleteById(id);
    }
}
