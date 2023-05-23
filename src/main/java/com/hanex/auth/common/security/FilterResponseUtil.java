package com.hanex.auth.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanex.auth.common.api.ApiException;
import com.hanex.auth.common.api.ApiResponseCode;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void unAuthorized(HttpServletResponse response, Exception e) throws IOException {

        ApiException apiException = new ApiException(ApiResponseCode.UNAUTHORIZED,e.getMessage());
        String responseBody = mapper.writeValueAsString(apiException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse response, Exception e) throws IOException {

        ApiException apiException = new ApiException(ApiResponseCode.FORBIDDEN,e.getMessage());
        String responseBody = mapper.writeValueAsString(apiException);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(responseBody);
    }
}

