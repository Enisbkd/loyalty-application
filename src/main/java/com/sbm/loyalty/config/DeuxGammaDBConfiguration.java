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
//    entityManagerFactoryRef = "entityManagerFactoryDb2",
//    transactionManagerRef = "transactionManagerDb2"
//)
//public class DeuxGammaDBConfiguration {
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.db2")
//    public DataSourceProperties db2DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "dataSourceDeuxGamma")
//    public DataSource dataSourceDeuxGamma() {
//        return db2DataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//
//    @Bean(name = "entityManagerFactoryDb2")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDb2(
//        EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDeuxGamma") DataSource dataSource) {
//        return builder
//            .dataSource(dataSource)
//            .properties(hibernateProperties())
//            .persistenceUnit("db2")
//            .packages("com.sbm.loyalty.domain")
//            .build();
//    }
//
//    @Bean(name = "transactionManagerDb2")
//    public PlatformTransactionManager transactionManagerDb2(
//        @Qualifier("entityManagerFactoryDb2") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    @Bean(name = "entityManagerDb2")
//    @PersistenceContext(unitName = "db2")
//    public EntityManager entityManagerDb2(
//        @Qualifier("entityManagerFactoryDb2") EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    private HashMap<String, Object> hibernateProperties() {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "none");
//        properties.put("hibernate.entity_manager_factory_interface", "jakarta.persistence.EntityManagerFactory");
//        return properties;
//    }
//}
