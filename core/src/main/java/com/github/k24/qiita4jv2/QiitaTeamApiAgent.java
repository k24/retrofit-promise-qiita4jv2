package com.github.k24.qiita4jv2;

import com.github.k24.qiita4jv2.api.AccessTokensApi;
import com.github.k24.qiita4jv2.api.team.ExpandedTemplatesApi;
import com.github.k24.qiita4jv2.api.team.LikesApi;
import com.github.k24.qiita4jv2.api.team.ProjectsApi;
import com.github.k24.qiita4jv2.api.team.ReactionsInTeamApi;
import com.github.k24.qiita4jv2.api.team.TaggingsApi;
import com.github.k24.qiita4jv2.api.team.TeamsApi;
import com.github.k24.qiita4jv2.api.team.TemplatesApi;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by k24 on 2017/03/14.
 */
public class QiitaTeamApiAgent extends QiitaApiAgent {
    protected QiitaTeamApiAgent(Retrofit retrofit, String accessToken) {
        super(retrofit, accessToken);
    }

    protected QiitaTeamApiAgent(Retrofit retrofit, AccessTokensApi.AccessTokenResponse accessTokenResponse) {
        super(retrofit, accessTokenResponse);
    }

    public static QiitaTeamApiAgent create(String teamId, Config config, String accessToken) {
        return create(teamId, config, okHttpClientBuilder(accessToken).build(), accessToken);
    }

    public static QiitaTeamApiAgent create(String teamId, Config config, OkHttpClient okHttpClient, String accessToken) {
        return new QiitaTeamApiAgent(retrofitBuilder(config)
                .baseUrl(HttpUrl.parse(URL_BASE).newBuilder()
                        .host(teamId + "." + URL_BASE_HOST)
                        .build())
                .client(okHttpClient)
                .build(), accessToken);
    }

    public static QiitaTeamApiAgent create(String teamId, QiitaApiAgent qiitaApiAgent) {
        return new QiitaTeamApiAgent(qiitaApiAgent.retrofit().newBuilder()
                .baseUrl(HttpUrl.parse(URL_BASE).newBuilder()
                        .host(teamId + "." + URL_BASE_HOST)
                        .build())
                .build(), qiitaApiAgent.accessToken());
    }

    //region Apis
    public ExpandedTemplatesApi expandedTemplatesApi() {
        return ensureApi(ExpandedTemplatesApi.class);
    }

    public LikesApi likesApi() {
        return ensureApi(LikesApi.class);
    }

    public ProjectsApi projectsApi() {
        return ensureApi(ProjectsApi.class);
    }

    public ReactionsInTeamApi reactionsInTeamApi() {
        return ensureApi(ReactionsInTeamApi.class);
    }

    public TaggingsApi taggingsApi() {
        return ensureApi(TaggingsApi.class);
    }

    public TeamsApi teamsApi() {
        return ensureApi(TeamsApi.class);
    }

    public TemplatesApi templatesApi() {
        return ensureApi(TemplatesApi.class);
    }
    //endregion
}
