package com.sbm.loyalty.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class TodosService {

    @Transactional(transactionManager = "todosTransactionManager")
    public void someTodosTransactionMethod() {
        // This will use the 'todosTransactionManager'
    }
}


