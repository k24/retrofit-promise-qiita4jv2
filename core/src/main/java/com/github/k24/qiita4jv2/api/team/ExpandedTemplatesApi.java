package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.ExpandedTemplate;
import com.github.k24.qiita4jv2.model.team.TemplateBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by k24 on 2017/03/14.
 */
public interface ExpandedTemplatesApi {
    @POST("expanded_templates")
    Deferred.Promise<ExpandedTemplate> postExpandedTemplate(@Body TemplateBody template);
}
