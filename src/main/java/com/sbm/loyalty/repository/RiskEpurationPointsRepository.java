package com.sbm.loyalty.repository;

import com.sbm.loyalty.domain.RiskEpurationPoints;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RiskEpurationPoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskEpurationPointsRepository extends JpaRepository<RiskEpurationPoints, Long> {}
