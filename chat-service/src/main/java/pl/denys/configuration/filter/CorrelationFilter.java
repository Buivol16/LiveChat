package pl.denys.configuration.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.denys.configuration.context.CorrelationIdContextHolder;

import java.io.IOException;
import java.util.UUID;

public class CorrelationFilter implements Filter {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;

        var corId = req.getHeader(CORRELATION_ID_HEADER);

        if (corId == null) {
            corId = UUID.randomUUID().toString();
        }

        CorrelationIdContextHolder.setCorrelationId(corId);

        res.setHeader(CORRELATION_ID_HEADER, corId);

        chain.doFilter(request,response);
    }
}
