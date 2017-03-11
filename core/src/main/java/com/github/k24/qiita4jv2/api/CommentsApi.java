package com.github.k24.qiita4jv2.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.Comment;
import com.github.k24.qiita4jv2.model.CommentBody;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by k24 on 2017/02/12.
 */
public interface CommentsApi {
    @DELETE("comments/{comment_id}")
    Deferred.Promise<Success> deleteComment(@Path("comment_id") String commentId);

    @GET("comments/{comment_id}")
    Deferred.Promise<Comment> comment(@Query("comment_id") String commentId);

    @PATCH("comments/{comment_id}")
    Deferred.Promise<Comment> patchComment(@Body CommentBody comment);

    @GET("items/{item_id}/comments")
    Deferred.Promise<List<Comment>> commentsByItem(@Path("item_id") String itemId);

    @POST("items/{item_id}/comments")
    Deferred.Promise<Comment> postCommentForItem(@Path("item_id") String itemId, @Body CommentBody comment);
}
