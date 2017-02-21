package org.glenlivet.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.UseLatestAggregationStrategy;
import org.apache.camel.spi.AggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



/**
 * Created by appledev122 on 2/20/17.
 */
@Component
public class AggregatorRoute extends RouteBuilder {

    @Autowired
    @Qualifier("jdbcAggregationRepository")
    private AggregationRepository jdbcAggregationRepository;

    @Override
    public void configure() throws Exception {
        from("activemq:queue:foo.bar")
                .to("log:org.glenlivet.camel?showHeaders=true&showBody=true")
                .aggregate(header("JMSCorrelationID"), new UseLatestAggregationStrategy())
                .aggregationRepository(jdbcAggregationRepository)
                .completionInterval(30000)
                .log("after to: ${body}");
    }
}
