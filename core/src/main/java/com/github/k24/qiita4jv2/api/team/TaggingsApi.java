package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Tagging;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by k24 on 2017/02/12.
 */
public interface TaggingsApi {
    @POST("items/{item_id}/taggings")
    Deferred.Promise<Tagging> postTagging(@Path("item_id") String itemId);

    @DELETE("items/{item_id}/taggings/{tagging_id}")
    Deferred.Promise<Success> deleteTagging(@Path("item_id") String itemId, @Path("tagging_id") String taggingId);
}
