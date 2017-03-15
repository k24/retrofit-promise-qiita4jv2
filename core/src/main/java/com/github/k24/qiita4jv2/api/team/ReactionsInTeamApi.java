package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.Reaction;
import com.github.k24.qiita4jv2.model.ReactionName;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface ReactionsInTeamApi {
    @POST("projects/{project_id}/reactions")
    Deferred.Promise<Reaction> postReactionForProject(@Path("project_id") String projectId, @Body ReactionName reaction);

    @DELETE("projects/{project_id}/reactions/{reaction_name}")
    Deferred.Promise<Reaction> postReactionForProject(@Path("project_id") String projectId, @Path("reaction_name") String reactionName);

    @GET("projects/{project_id}/reactions")
    Deferred.Promise<List<Reaction>> reactionsForProject(@Path("project_id") String projectId);
}
