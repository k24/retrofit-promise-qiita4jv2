package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.deferred.RxJava2Promise;
import com.github.k24.qiita4jv2.api.UsersApi;
import com.github.k24.qiita4jv2.model.User;
import com.github.k24.qiita4jv2.util.Success;
import com.github.k24.qiita4jv2.util.SuccessConverterFactory;
import com.github.k24.retrofit2.adapter.promise.PromiseCallAdapterFactory;
import com.github.k24.retrofit2.converter.jsonic.JsonicConverterFactory;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import java.io.IOException;
import java.util.List;

import static com.github.k24.deferred.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by k24 on 2017/03/06.
 */
public class QiitaApiAgentUserTest {

    private MockRetrofit mockRetrofit;
    private MockUsersApi mockUsersApi;

    @Before
    public void setUp() throws Exception {
        mockRetrofit = new MockRetrofit.Builder(new Retrofit.Builder()
                .baseUrl("https://qiita.com/api/v2/")
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(RxJava2DeferredFactory.createWithScheduler(Schedulers.io())))
                .addConverterFactory(SuccessConverterFactory.create())
                .addConverterFactory(JsonicConverterFactory.create())
                .client(new OkHttpClient())
                .build())
                .build();
        NetworkBehavior networkBehavior = mockRetrofit.networkBehavior();
        networkBehavior.setErrorPercent(0);
        networkBehavior.setFailurePercent(0);

        BehaviorDelegate<UsersApi> delegate = mockRetrofit.create(UsersApi.class);
        mockUsersApi = new MockUsersApi(delegate).withDefault();
    }

    @Test
    public void stockers() throws Exception {
        List<User> expected = MockUsersApi.defaultUsers();

        Deferred.Promise<Response<List<User>>> actualPromise = mockUsersApi.stockers("itemId", 0, 20);

        assertThat(actualPromise)
                .isNotNull();
        Response<List<User>> response = RxJava2Promise.maybe(actualPromise).blockingGet();
        assertThat(response.body())
                .isEqualTo(expected);
    }

    @Test
    public void user() throws Exception {
        User expected = MockUsersApi.defaultUsers().get(0);

        Deferred.Promise<User> actualPromise = mockUsersApi.user("userId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void unfollow() throws Exception {
        Success expected = Success.SUCCESS;

        Deferred.Promise<Success> actualPromise = mockUsersApi.unfollow("userId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    private static class MockUsersApi implements UsersApi {
        private final BehaviorDelegate<UsersApi> delegate;
        private List<User> users;

        public MockUsersApi(BehaviorDelegate<UsersApi> delegate) {
            this.delegate = delegate;
        }

        public MockUsersApi withDefault() throws IOException {
            users = defaultUsers();
            return this;
        }

        public static List<User> defaultUsers() throws IOException {
            return TestFixture.asList(TestFixture.user(), 20);
        }

        @Override
        public Deferred.Promise<Response<List<User>>> stockers(@Path("item_id") String itemId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().stockers(itemId, page, perPage);
        }

        @Override
        public Deferred.Promise<Response<List<User>>> users(@Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().users(page, perPage);
        }

        @Override
        public Deferred.Promise<User> user(@Path("user_id") String userId) {
            return single().user(userId);
        }

        @Override
        public Deferred.Promise<Response<List<User>>> followees(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().followees(userId, page, perPage);
        }

        @Override
        public Deferred.Promise<Response<List<User>>> followers(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().followers(userId, page, perPage);
        }

        @Override
        public Deferred.Promise<Success> unfollow(@Path("user_id") String userId) {
            return success().unfollow(userId);
        }

        @Override
        public Deferred.Promise<User> following(@Path("user_id") String userId) {
            return single().following(userId);
        }

        @Override
        public Deferred.Promise<Success> follow(@Path("user_id") String userId) {
            return success().follow(userId);
        }

        //region Delegates
        private UsersApi single() {
            return delegate.returningResponse(users.get(0));
        }

        private UsersApi list() {
            return delegate.returningResponse(users);
        }

        private UsersApi success() {
            return delegate.returningResponse(Success.SUCCESS);
        }
        //endregion
    }
}
