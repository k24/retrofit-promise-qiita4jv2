package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Project;
import com.github.k24.qiita4jv2.model.team.ProjectBody;
import com.github.k24.qiita4jv2.util.Success;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface ProjectsApi {
    @GET("projects")
    Deferred.Promise<Response<List<Project>>> projects(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @POST("projects")
    Deferred.Promise<Project> postProject(@Body ProjectBody project);

    @DELETE("projects/{project_id}")
    Deferred.Promise<Success> deleteProject(@Path("project_id") String projectId);

    @GET("projects/{project_id}")
    Deferred.Promise<Project> project(@Path("project_id") String projectId);

    @PATCH("projects/{project_id}")
    Deferred.Promise<Project> patchProject(@Body ProjectBody project);
}
