package com.example.justlifetest.api.constants;

public final class ApiGroups {

    private ApiGroups() {
        // Private constructor to prevent instantiation
    }

    public static final class Booking {
        public static final String NAME = "BookingApiController";
        public static final String TITLE = "BookingApi";
        public static final String DESC = "Booking Api";
        public static final String PATHS = "/api/booking/**";

        private Booking() {
            // Private constructor to prevent instantiation
        }
    }

}
