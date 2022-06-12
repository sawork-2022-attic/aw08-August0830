package com.micropos.posorder;

import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

@Component
public class HttpInboundGateway {

    public static final Logger log = LoggerFactory.getLogger(HttpInboundGateway.class);

    public String generateOrder(String orderInfo)
    {
        String[] info = orderInfo.split("\\|");
        int cnt=0;
        String addr = null;
        for(String str : info){
            if(str.startsWith("addr:")){
                addr = new StringBuilder(str.substring(4)).toString();
            }
            else
                cnt++;
        }
        String deliveryInfo = String.format("%d|%s",cnt,addr);
        log.info("place order {}", deliveryInfo);
        return deliveryInfo;
    }

    @Bean
    public IntegrationFlow inGate(){
        return IntegrationFlows.from(
                Http.inboundGateway("/order")
                        .requestMapping(r -> r.methods(HttpMethod.POST)
                                .consumes("text/plain"))
                        .requestPayloadType(String.class)
                        .id("deliveryInfo"))
                .<String>handle((p,h) -> generateOrder(p))
                .channel("sendOrderChannel")
                .get();
        
    }
}
