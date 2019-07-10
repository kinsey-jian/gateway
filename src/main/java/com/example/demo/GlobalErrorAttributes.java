package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author dong_gui on 2019-07-10.
 */
@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        return addErrorDetails(errorAttributes);
    }

    protected Map<String, Object> addErrorDetails(Map<String, Object> errorAttributes) {
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

        errorAttributes.put("timestamp", timestamp);

        String message = String.valueOf(errorAttributes.get("message"));
        if (StringUtils.isEmpty(message)) {
            errorAttributes.put("message", String.valueOf(errorAttributes.get("error")));
        }

        return errorAttributes;
    }
}