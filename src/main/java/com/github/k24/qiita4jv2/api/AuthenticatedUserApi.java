package com.github.k24.qiita4jv2.api;

import com.github.k24.qiita4jv2.model.AuthenticatedUser;
import com.github.k24.deferred.Deferred;
import retrofit2.http.GET;

/**
 * Created by k24 on 2017/02/13.
 */
public interface AuthenticatedUserApi {
    @GET("authenticated_user")
    Deferred.Promise<AuthenticatedUser> authenticatedUser();
}
