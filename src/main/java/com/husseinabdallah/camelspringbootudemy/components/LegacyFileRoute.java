package com.husseinabdallah.camelspringbootudemy.components;

import com.husseinabdallah.camelspringbootudemy.beans.NameAddress;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());
    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("inboundMessageBeanIOMapping.xml","inputMessageStream");

    @Override
    public void configure() throws Exception {

        from("file:src/data/input?fileName=inputFile.csv")
                .routeId("legacyFileMoveRouteId")
                .split(body().tokenize("\n",1,true))
                .unmarshal(inboundDataFormat)
                .process(exchange -> {
                    NameAddress filedata =exchange.getIn().getBody(NameAddress.class);
                    logger.info("This is the read fileData:" + filedata.toString());
                    exchange.getIn().setBody(filedata.toString());
                })
                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();
    }
}
