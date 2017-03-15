package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Team;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public interface TeamsApi {
    @GET("teams")
    Deferred.Promise<List<Team>> teams();
}
