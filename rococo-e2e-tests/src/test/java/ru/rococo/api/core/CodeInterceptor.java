package ru.rococo.api.core;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class CodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());
        if (response.isRedirect()) {
            String location = Objects.requireNonNull(
                    response.header("Location")
            );
            if (location.contains("code=")) {
                // TODO реализовать
//        ApiLoginExtension.setCode(//            StringUtils.substringAfter(
//                location, "code="
//            )
//        );
            }
        }
        return response;
    }
}
