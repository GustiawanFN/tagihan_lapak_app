package com.lapak.lapak.api;

import static com.lapak.lapak.utility.Utility.BASE_URL_API;

public class Service {
    public static ApiService getAPIService() {
        return Client.getClient(BASE_URL_API).create(ApiService.class);

    }
}
