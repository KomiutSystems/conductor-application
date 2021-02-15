package com.komiut.conductor.retrofit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    String token;
    public TokenInterceptor(String token) {
        this.token=token;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                .header("Authorization","Bearer "+token)
                .build();

                return chain.proceed(request);
    }
}
