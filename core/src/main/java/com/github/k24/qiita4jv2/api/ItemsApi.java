package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.Item;
import com.github.k24.qiita4jv2.model.ItemBody;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface ItemsApi {
    @GET("authenticated_user/items")
    Deferred.Promise<Response<List<Item>>> authenticatedUserItems(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("items")
    Deferred.Promise<Response<List<Item>>> items(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("query") String query);

    @POST("items")
    Deferred.Promise<Item> postItem(@Body ItemBody item);

    @DELETE("items/{item_id}")
    Deferred.Promise<Item> deleteItem(@Path("item_id") String itemId);

    @GET("items/{item_id}")
    Deferred.Promise<Item> item(@Path("item_id") String itemId);

    @PATCH("items/{item_id}")
    Deferred.Promise<Item> patchItem(@Path("item_id") String itemId, @Body ItemBody item);

    @PUT("items/{item_id}/stock")
    Deferred.Promise<Success> stock(@Path("item_id") String itemId);

    @DELETE("items/{item_id}/stock")
    Deferred.Promise<Success> unstock(@Path("item_id") String itemId);

    @PUT("items/{item_id}/stock")
    Deferred.Promise<Success> stocking(@Path("item_id") String itemId);

    @GET("tags/{tag_id}/items")
    Deferred.Promise<Response<List<Item>>> itemsByTag(@Path("tag_id") String tagId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("users/{user_id}/items")
    Deferred.Promise<Response<List<Item>>> itemsByUser(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("users/{user_id}/stocks")
    Deferred.Promise<Response<List<Item>>> stockesByUser(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage);
}
