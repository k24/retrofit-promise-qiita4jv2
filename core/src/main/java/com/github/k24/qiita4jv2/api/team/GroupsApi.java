package com.github.k24.qiita4jv2.api.team;

import com.github.k24.deferred.Deferred;
import com.github.k24.qiita4jv2.model.team.Group;
import retrofit2.http.GET;

import java.util.List;

/**
 * For Groups.
 * <p>
 * UNDOCUMENTED
 * <p>
 * Created by k24 on 2017/03/20.
 */
public interface GroupsApi {
    @GET("groups")
    Deferred.Promise<List<Group>> groups();
}
