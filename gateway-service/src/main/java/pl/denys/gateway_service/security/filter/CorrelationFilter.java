package pl.denys.gateway_service.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import pl.denys.gateway_service.security.context.CorrelationIdContextHolder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class CorrelationFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var req = exchange.getRequest();
        var headers = req.getHeaders();
        var headerName = "X-Correlation-ID";
        if (!headers.containsHeader(headerName) || !headers.get(headerName).getFirst().isBlank()){
            var id = UUID.randomUUID().toString();
            log.info("Client has no correlation header. Giving him a new one: {}", id);
            exchange.getRequest().getHeaders().set(headerName, id);
            exchange.getResponse().getHeaders().set(headerName, id);
            CorrelationIdContextHolder.setCorrelationId(id);
        }

        return chain.filter(exchange);
    }
}
