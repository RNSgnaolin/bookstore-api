package br.com.bookstore.infra;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws IOException, ServletException {

        String traceId = UUID.randomUUID().toString();

        try {
            MDC.put(TRACE_ID, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(traceId);
        }

    }
    
}
