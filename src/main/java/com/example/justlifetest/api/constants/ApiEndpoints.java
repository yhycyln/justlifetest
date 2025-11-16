package com.example.justlifetest.api.constants;

import org.springframework.http.MediaType;

public final class ApiEndpoints {
    public static final String RESPONSE_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

    public static final String API_BASE_URL = "/api";
    public static final String BOOKING_API_URL = API_BASE_URL + "/booking";

    private ApiEndpoints() {

    }
}
