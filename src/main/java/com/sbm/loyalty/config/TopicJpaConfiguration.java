package com.sbm.loyalty.config;

import com.sbm.loyalty.domain.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = Test.class,
        entityManagerFactoryRef = "topicsEntityManagerFactory",
        transactionManagerRef = "topicsTransactionManager"
)
public class TopicJpaConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory(
            @Qualifier("topicsDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
//                .properties(hibernateProperties())
                .packages(Test.class)
                .build();
    }

    @Bean(name = "topicsTransactionManager")
    public PlatformTransactionManager topicsTransactionManager(
            @Qualifier("topicsEntityManagerFactory") LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(topicsEntityManagerFactory.getObject()));
    }

//    private HashMap<String, Object> hibernateProperties() {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//        return properties;
//    }
}
