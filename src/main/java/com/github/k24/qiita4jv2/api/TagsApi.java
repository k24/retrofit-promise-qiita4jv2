package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.Tag;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by k24 on 2017/02/12.
 */
public interface TagsApi {
    @GET("tags")
    Deferred.Promise<Response<List<Tag>>> tags(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("sort") Sort sort);

    @GET("tags/{tag_id}")
    Deferred.Promise<Tag> tag(@Path("tag_id") String tagId);

    @GET("users/{user_id}/following_tags")
    Deferred.Promise<Response<List<Tag>>> tags(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @DELETE("tags/{tag_id}/following")
    Deferred.Promise<Success> unfollow(@Path("tag_id") String tagId);

    // Not documented about result of not following
    @GET("tags/{tag_id}/following")
    Deferred.Promise<Tag> following(@Path("tag_id") String tagId);

    // Should return Tag!
    @PUT("tags/{tag_id}/following")
    Deferred.Promise<Success> follow(@Path("tag_id") String tagId);

    enum Sort {
        COUNT,
        NAME;

        @Override
        public String toString() {
            switch (this) {
                case COUNT:
                    return "count";
                case NAME:
                    return "name";
                default:
                    return name();
            }
        }
    }
}
