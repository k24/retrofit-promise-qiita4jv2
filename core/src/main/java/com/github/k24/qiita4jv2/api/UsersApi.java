package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.User;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface UsersApi {
    @GET("items/{item_id}/stockers")
    Deferred.Promise<Response<List<User>>> stockers(@Path("item_id") String itemId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("users")
    Deferred.Promise<Response<List<User>>> users(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("users/{user_id}")
    Deferred.Promise<User> user(@Path("user_id") String userId);

    @GET("users/{user_id}/followees")
    Deferred.Promise<Response<List<User>>> followees(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("users/{user_id}/followers")
    Deferred.Promise<Response<List<User>>> followers(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @DELETE("users/{user_id}/following")
    Deferred.Promise<Success> unfollow(@Path("user_id") String userId);

    // ユーザをフォローしている場合に204を返します。 but the response documented for example...
    @GET("users/{user_id}/following")
    Deferred.Promise<User> following(@Path("user_id") String userId);

    @PUT("users/{user_id}/following")
    Deferred.Promise<Success> follow(@Path("user_id") String userId);
}
