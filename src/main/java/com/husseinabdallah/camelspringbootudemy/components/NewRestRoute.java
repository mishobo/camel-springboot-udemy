package com.husseinabdallah.camelspringbootudemy.components;

import com.husseinabdallah.camelspringbootudemy.beans.InboundRestProcessingBean;
import com.husseinabdallah.camelspringbootudemy.beans.NameAddress;
import com.husseinabdallah.camelspringbootudemy.processor.InboundMessageProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.net.ConnectException;

@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        Predicate isCityNairobi = header("userCity").isEqualTo("Nairobi");

        onException(JMSException.class, ConnectException.class)
                .routeId("jmsExceptionRouteId")
                        .handled(true)
                                .log(LoggingLevel.INFO, "JMS Exception; handling gracefully");

        restConfiguration()
                .component("jetty")
                .host("0.0.0.0")
                .port(8080)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true);

        rest("masterclass")
                .produces("application/json")
                .post("nameAddress").type(NameAddress.class).route()
                .routeId("newRestRouteId")
                .log(LoggingLevel.INFO, "Posted data: ${body}")
//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO, "Transformed Body: ${body}")
//                .convertBodyTo(String.class)
//                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");

                .bean(new InboundRestProcessingBean())
                //setup rule: if city = Nairobi, send to MQ else send to both DB and MQ

                .choice()
                .when(isCityNairobi)
                    .to("direct:toActiveMQ")
                .otherwise()
                    .to("direct:toDB")
                    .to("direct:toActiveMQ")
                .end()

                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform().simple("Message Processed and Result Generated with Body ${body}")
                .endRest();

        from("direct:toDB")
                .routeId("toDBId")
                .to("jpa:"+NameAddress.class.getName());

        from("direct:toActiveMQ")
                .routeId("toActiveMQId")
                .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly");
    }
}
