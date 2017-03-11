package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.Reaction;
import com.github.k24.qiita4jv2.model.ReactionName;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface ReactionsApi {
    @POST("comments/{comment_id}/reactions")
    Deferred.Promise<Reaction> postReactionForComment(@Path("comment_id") String commentId, @Body ReactionName reaction);

    @POST("items/{item_id}/reactions")
    Deferred.Promise<Reaction> postReactionForItem(@Path("item_id") String itemId, @Body ReactionName reaction);

    @DELETE("comments/{comment_id}/reactions/{reaction_name}")
    Deferred.Promise<Reaction> deleteReactionFromComment(@Path("comment_id") String commentId, @Path("reaction_name") String reactionName);

    @DELETE("items/{item_id}/reactions/{reaction_name}")
    Deferred.Promise<Reaction> postReactionForItem(@Path("item_id") String itemId, @Path("reaction_name") String reactionName);

    @GET("comments/{comment_id}/reactions")
    Deferred.Promise<List<Reaction>> reactionsForComment(@Path("comment_id") String commentId);

    @GET("items/{item_id}/reactions")
    Deferred.Promise<List<Reaction>> reactionsForItem(@Path("item_id") String itemId);
}
