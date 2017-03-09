package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface AccessTokensApi {
    enum Scope {
        READ("read_qiita"),
        READ_TEAM("read_qiita_team"),
        WRITE("write_qiita"),
        WRITE_TEAM("write_qiita_team");

        private final String key;

        Scope(String key) {
            this.key = key;
        }

        public String key() {
            return key;
        }

        public static Scope fromKey(String key) {
            if (key == null) return null;
            for (Scope scope : values()) {
                if (key.equals(scope.key)) return scope;
            }
            return null;
        }

        public static List<Scope> transformScopes(List<String> scopes) {
            if (scopes == null) return null;
            ArrayList<Scope> values = new ArrayList<>();
            for (String scope : scopes) {
                Scope value = Scope.fromKey(scope);
                if (value != null)
                    values.add(value);
            }
            return values;
        }
    }

    @POST("access_tokens")
    Deferred.Promise<AccessTokenResponse> accessToken(@Body AccessTokenBody accessTokenBody);

    // Maybe "token" of the response is "access_token".
    @DELETE("access_tokens/{access_token}")
    Deferred.Promise<Success> deleteAccessToken(@Path("access_token") String token);

    class AccessTokenBody {
        public String client_id;
        public String client_secret;
        public String code;
    }

    class AccessTokenResponse {
        public String client_id;
        public List<String> scopes;
        public String token;
    }
}
