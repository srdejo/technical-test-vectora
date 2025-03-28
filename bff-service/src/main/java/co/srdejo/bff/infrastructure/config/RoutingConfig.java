package co.srdejo.bff.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class RoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> handleAccountRoutes() {

        return route("account")
                .route(path("/accounts/**"), http("http://account-service:8080"))
                .before(stripPrefix(1))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> handleTransactionsRoutes() {

        return route("transaction")
                .route(path("/transactions/**"), http("http://transaction-service:8080"))
                .before(stripPrefix(1))
                .build();
    }
}
