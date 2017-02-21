package org.glenlivet.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.processor.aggregate.jdbc.JdbcAggregationRepository;
import org.apache.camel.spi.AggregationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by appledev122 on 2/20/17.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("jmsConnectionFactory")
    public ActiveMQConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        return factory;
    }

    @Bean("jdbcAggregationRepository")
    public AggregationRepository jdbcAggregationRepository(DataSource dataSource,
                                     PlatformTransactionManager transactionManager) {
        JdbcAggregationRepository ar = new JdbcAggregationRepository();
        ar.setRepositoryName("aggregationRepo3");
        ar.setTransactionManager(transactionManager);
        ar.setDataSource(dataSource);
        ar.setStoreBodyAsText(true);
        List<String> headers = Arrays.asList("JMSCorrelationID");
        ar.setHeadersToStoreAsText(headers);
        return ar;
    }
}
