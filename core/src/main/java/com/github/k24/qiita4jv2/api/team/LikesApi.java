package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Like;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by k24 on 2017/02/12.
 */
public interface LikesApi {
    @GET("items/{item_id}/likes")
    Deferred.Promise<List<Like>> likes(@Path("item_id") String itemId);

    @DELETE("items/{item_id}/like")
    Deferred.Promise<Success> unlike(@Path("item_id") String itemId);

    @PUT("items/{item_id}/like")
    Deferred.Promise<Success> like(@Path("item_id") String itemId);

    @GET("items/{item_id}/like")
    Deferred.Promise<Success> liking(@Path("item_id") String itemId);
}
