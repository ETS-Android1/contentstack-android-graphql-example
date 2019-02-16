package com.contentstack.graphql.utils;

import android.util.Log;

import com.contentstack.graphql.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class NetworkInterceptor implements Interceptor {

    @Override public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {

        Request original = chain.request();
        Request request = original.newBuilder().url(BuildConfig.BASE_ENDPOINT)
                .header("operationName", "allProduct")
                .header("query", bodyToString(original))
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }



    private  String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            assert copy.body() != null;
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}

