package ipt.lei.dam.ncrapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String params = paramMapToString(request.getParameterMap());
        String headers = headersToString(request);

        String logEntry = String.format(
                "{\"type\": \"Request\", \"method\": \"%s\", \"uri\": \"%s\", \"params\": %s, \"headers\": %s, \"ip\": \"%s\"}",
                request.getMethod(), request.getRequestURI(), params, headers, request.getRemoteAddr());

        LOGGER.info(logEntry);
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> {
                    try {
                        return "\"" + entry.getKey() + "\": " + objectMapper.writeValueAsString(entry.getValue());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private String headersToString(HttpServletRequest request) {
        Map<String, String> headers = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
        try {
            return objectMapper.writeValueAsString(headers);
        } catch (IOException e) {
            LOGGER.error("Error while parsing headers to JSON", e);
            return "{}";
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long duration = System.currentTimeMillis() - (Long) request.getAttribute("startTime");

        String contentType = response.getContentType() != null ? response.getContentType() : "Unknown";
        String exceptionDetails = ex != null ? ex.getMessage() : "None";

        String logEntry = String.format(
                "{\"type\": \"Response\", \"status\": %d, \"duration\": %d ms, \"contentType\": \"%s\", \"exception\": \"%s\"}",
                response.getStatus(), duration, contentType, exceptionDetails);

        LOGGER.info(logEntry);
    }

}
