package com.sbm.loyalty.service;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class DeuxGammaService {

    @PersistenceContext
    private final EntityManager em2Gamma;

    private final PlatformTransactionManager todosTransactionManager;


    private static final Logger log = LoggerFactory.getLogger(CalculateEpurationPointsService.class);


    public DeuxGammaService(@Qualifier("todosEntityManagerFactory") EntityManagerFactory entityManagerFactoryTwo,
                            @Qualifier("todosTransactionManager") PlatformTransactionManager todosTransactionManager) {
        this.em2Gamma = entityManagerFactoryTwo.createEntityManager();
        this.todosTransactionManager = todosTransactionManager;
    }

    //    @Transactional(transactionManager = "todosTransactionManager")
//    public int updateValidUntil(String validUntil) {
//        Query query = em2Gamma.createNativeQuery("UPDATE DGDINFRA.ENVPARAM x " +
//            " SET x.LIPARAM= :VALIDUNTIL" +
//            " WHERE x.COTYPARAM='FIDEXP' " +
//            " AND x.COPARAM='FID'");
//        query.setParameter("VALIDUNTIL", validUntil);
//        log.debug("Updating validUntil with value : " + validUntil);
//
//        return query.executeUpdate();
//    }

    public void updateEntity(String validUntil) {
        EntityTransaction transaction = em2Gamma.getTransaction();
        TransactionStatus status = todosTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            transaction.begin(); // Start the transaction

            Query query = em2Gamma.createNativeQuery("UPDATE DGDINFRA.ENVPARAM x " +
                " SET x.LIPARAM= :VALIDUNTIL" +
                " WHERE x.COTYPARAM='FIDEXP' " +
                " AND x.COPARAM='FID'");
            query.setParameter("VALIDUNTIL", validUntil);
            query.executeUpdate();

            todosTransactionManager.commit(status); // Commit the transaction
        } catch (RuntimeException e) {
            transaction.rollback(); // Rollback on error
            throw e; // Rethrow the exception
        }
    }


}

