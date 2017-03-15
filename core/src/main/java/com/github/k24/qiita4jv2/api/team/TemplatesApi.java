package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Template;
import com.github.k24.qiita4jv2.model.team.TemplateBody;
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
public interface TemplatesApi {
    @GET("templates")
    Deferred.Promise<Response<List<Template>>> templates(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @DELETE("templates/{template_id}")
    Deferred.Promise<Success> deleteTemplate(@Path("template_id") String templateId);

    @GET("templates/{template_id}")
    Deferred.Promise<Template> template(@Path("template_id") String templateId);

    @POST("templates")
    Deferred.Promise<Template> postTemplate(@Body TemplateBody template);

    @PATCH("templates/{template_id}")
    Deferred.Promise<Template> patchTemplate(@Path("template_id") String templateId, @Body TemplateBody template);
}
