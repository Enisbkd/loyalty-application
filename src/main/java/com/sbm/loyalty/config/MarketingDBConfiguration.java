//package com.sbm.loyalty.config;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@EnableJpaRepositories(
//    entityManagerFactoryRef = "entityManagerMarketingDB",
//    transactionManagerRef = "transactionManagerMarketingDB"
//)
//public class MarketingDBConfiguration {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.db1")
//    public DataSourceProperties db1DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//
//    @Primary
//    @Bean(name = "dataSourceMarketingDB")
//    public DataSource dataSourceMarketingDB() {
//        return db1DataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryMarketingDB")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMarketingDB(
//        EntityManagerFactoryBuilder builder, @Qualifier("dataSourceMarketingDB") DataSource dataSource) {
//        return builder
//            .dataSource(dataSource)
//            .persistenceUnit("db1")
//            .packages("com.sbm.loyalty.domain")
//            .properties(hibernateProperties())
//            .build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManagerMarketingDB")
//    public PlatformTransactionManager transactionManagerMarketingDB(
//        @Qualifier("entityManagerFactoryMarketingDB") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerMarketingDB")
//    @PersistenceContext(unitName = "db1")
//    public EntityManager entityManagerMarketingDB(
//        @Qualifier("entityManagerFactoryMarketingDB") EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    private HashMap<String, Object> hibernateProperties() {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "none"); // Adjust as needed
//        return properties;
//    }
//}
