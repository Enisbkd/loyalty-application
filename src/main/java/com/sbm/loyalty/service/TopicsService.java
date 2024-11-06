package com.sbm.loyalty.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicsService {

    @Transactional(transactionManager = "topicsTransactionManager")
    public void someTopicsTransactionMethod() {
        // This will use the 'topicsTransactionManager'
    }
}
