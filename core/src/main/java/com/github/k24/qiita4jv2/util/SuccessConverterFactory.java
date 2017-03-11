package com.github.k24.qiita4jv2.util;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by k24 on 2017/03/04.
 */
public class SuccessConverterFactory extends Converter.Factory {
    public static SuccessConverterFactory create() {
        return new SuccessConverterFactory();
    }

    private SuccessConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (!Success.class.equals(type)) return null;
        return new Converter<ResponseBody, Success>() {
            @Override
            public Success convert(ResponseBody responseBody) throws IOException {
                return Success.SUCCESS;
            }
        };
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }
}
