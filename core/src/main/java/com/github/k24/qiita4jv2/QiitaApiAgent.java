package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.api.*;
import com.github.k24.qiita4jv2.util.SuccessConverterFactory;
import com.github.k24.retrofit2.adapter.promise.PromiseCallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by k24 on 2017/02/12.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class QiitaApiAgent {
    public static final String URL_BASE = "https://qiita.com/api/v2/";
    public static final String URL_OAUTH = URL_BASE + "oauth/authorize";

    public static final String URL_JSON_SCHEMA = "http://qiita.com/api/v2/schema";
    public static final String URL_JSON_SCHEMA_EN = "http://qiita.com/api/v2/schema?locale=en";
    public static final String URL_JSON_SCHEMA_JA = "http://qiita.com/api/v2/schema?locale=ja";

    public static final String FORMAT_AUTHORIZATION_VALUE = "Bearer %s";

    private final Retrofit retrofit;
    private final String accessToken;
    private List<String> scopes;

    public QiitaApiAgent(Retrofit retrofit, String accessToken) {
        this.retrofit = retrofit;
        this.accessToken = accessToken;
    }

    //region For authorization URL
    public static String composeOAuthUrl(String clientId, List<AccessTokensApi.Scope> scopes, String state) {
        if (scopes == null || scopes.isEmpty()) throw new IllegalArgumentException("scopes must have one or more item");
        StringBuilder buf = new StringBuilder();
        for (AccessTokensApi.Scope scope : scopes) {
            buf.append(" ");
            buf.append(scope.key());
        }
        return composeOAuthUrl(clientId, buf.substring(1), state);
    }

    public static String composeOAuthUrl(String clientId, String scope, String state) {
        String enc = "utf-8";
        try {
            return URL_OAUTH + "?"
                    + "client_id=" + URLEncoder.encode(clientId, enc)
                    + "&"
                    + "scope=" + URLEncoder.encode(scope, enc)
                    + "&"
                    + "state=" + URLEncoder.encode(state, enc);
        } catch (UnsupportedEncodingException e) {
            // May not be reached here
            throw new UnsupportedOperationException(e);
        }
    }
    //endregion

    //region Use authorization
    // First
    // - CallAdapter for Promise
    // - Converter for JSON
    // : AccessTokenApi.AccessTokenResponse
    // Next or Restore
    // - CallAdapter for Promise
    // - Converter for JSON
    // - AccessToken or Authenticated HttpClient
    // : Apis

    // First and Next by default
    public static class Config {
        final Converter.Factory converterFactory;
        final Deferred.Factory deferredFactory;

        public Config(Converter.Factory converterFactory, Deferred.Factory deferredFactory) {
            this.converterFactory = converterFactory;
            this.deferredFactory = deferredFactory;
        }
    }

    public static Deferred.Promise<QiitaApiAgent> createWithAuth(final Config config, final AccessTokensApi.AccessTokenBody accessTokenBody) {
        final Retrofit.Builder builder = retrofitBuilder(config);
        return builder.client(new OkHttpClient()).build().create(AccessTokensApi.class).accessToken(accessTokenBody)
                .then(new Deferred.OnResolvedPromise<AccessTokensApi.AccessTokenResponse, QiitaApiAgent>() {
                    @Override
                    public Deferred.Promise<QiitaApiAgent> onResolved(AccessTokensApi.AccessTokenResponse value) throws Exception {
                        return config.deferredFactory
                                .deferred()
                                .resolved(new QiitaApiAgent(builder
                                        .client(okHttpClientBuilder(value.token).build())
                                        .build(), value));
                    }
                });
    }

    protected static Retrofit.Builder retrofitBuilder(Config config) {
        return new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(config.deferredFactory))
                .addConverterFactory(SuccessConverterFactory.create())
                .addConverterFactory(config.converterFactory);
    }

    protected QiitaApiAgent(Retrofit retrofit, AccessTokensApi.AccessTokenResponse accessTokenResponse) {
        this.retrofit = retrofit;
        this.accessToken = accessTokenResponse.token;
        scopes = accessTokenResponse.scopes;
    }

    public static OkHttpClient.Builder okHttpClientBuilder(/*Nonnull*/ final String accessToken) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request request = new Request.Builder()
                                .url(originalRequest.url())
                                .addHeader("Authorization", String.format(Locale.US, FORMAT_AUTHORIZATION_VALUE, accessToken))
                                .method(originalRequest.method(), originalRequest.body())
                                .build();
                        return chain.proceed(request);
                    }
                });
    }
    //endregion

    public String accessToken() {
        return accessToken;
    }

    public List<String> scopes() {
        return scopes;
    }

    protected Retrofit retrofit() {
        return retrofit;
    }

    //region Use user's token
    // First and next by user
    // Get access token
    // Create with OkHttp

    public static Deferred.Promise<AccessTokensApi.AccessTokenResponse> accessToken(final Config config, final AccessTokensApi.AccessTokenBody accessTokenBody) {
        return retrofitBuilder(config)
                .client(new OkHttpClient())
                .build()
                .create(AccessTokensApi.class)
                .accessToken(accessTokenBody);
    }

    public static QiitaApiAgent create(Config config, String accessToken) {
        return create(config, okHttpClientBuilder(accessToken).build(), accessToken);
    }

    public static QiitaApiAgent create(Config config, OkHttpClient okHttpClient, String accessToken) {
        return new QiitaApiAgent(retrofitBuilder(config)
                .client(okHttpClient)
                .build(), accessToken);
    }
    //endregion

    //region Apis
    private final Map<Class, Object> apis = new HashMap<>();

    @SuppressWarnings("unchecked")
    private <T> T ensureApi(Class<T> apiClass) {
        synchronized (apis) {
            Object api = apis.get(apiClass);
            if (api == null) {
                api = retrofit.create(apiClass);
                apis.put(apiClass, api);
            }
            return (T) api;
        }
    }

    protected void clearApis() {
        synchronized (apis) {
            apis.clear();
        }
    }

    public AccessTokensApi accessTokensApi() {
        return ensureApi(AccessTokensApi.class);
    }

    public AuthenticatedUserApi authenticatedUserApi() {
        return ensureApi(AuthenticatedUserApi.class);
    }

    public CommentsApi commentsApi() {
        return ensureApi(CommentsApi.class);
    }

    public ItemsApi itemsApi() {
        return ensureApi(ItemsApi.class);
    }

    public ReactionsApi reactionsApi() {
        return ensureApi(ReactionsApi.class);
    }

    public TagsApi tagsApi() {
        return ensureApi(TagsApi.class);
    }

    public UsersApi usersApi() {
        return ensureApi(UsersApi.class);
    }
    //endregion
}
