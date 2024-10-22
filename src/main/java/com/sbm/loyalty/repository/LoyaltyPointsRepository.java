package com.sbm.loyalty.repository;

import com.sbm.loyalty.domain.LoyaltyPoints;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoyaltyPoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {}
