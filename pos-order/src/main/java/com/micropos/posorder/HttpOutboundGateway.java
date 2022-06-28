package com.micropos.posorder;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

@Component
public class HttpOutboundGateway {
    @Bean
    public IntegrationFlow outGate()
    {
        
        return IntegrationFlows.from("sendOrderChannel")
        .handle(
            Http.outboundGateway("http://localhost:8086/delivery/")
            .httpMethod(HttpMethod.POST)
            .expectedResponseType(Boolean.class))
        .get();
    }
}
