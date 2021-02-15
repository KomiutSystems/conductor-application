package com.komiut.conductor.retrofit;

public class ApiUtils {
    public static APIService getAPIService() {
        return RetrofitClient.getClient(COMMONRETROFIT.BASE_URL).create(APIService.class);
    }
}
