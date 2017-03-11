package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.deferred.RxJava2Promise;
import com.github.k24.qiita4jv2.api.TagsApi;
import com.github.k24.qiita4jv2.model.Tag;
import com.github.k24.qiita4jv2.util.Success;
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

import java.io.IOException;
import java.util.List;

import static com.github.k24.deferred.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by k24 on 2017/03/06.
 */
public class QiitaApiAgentTagTest {

    private MockRetrofit mockRetrofit;
    private MockTagsApi mockTagsApi;

    @Before
    public void setUp() throws Exception {
        mockRetrofit = new MockRetrofit.Builder(new Retrofit.Builder()
                .baseUrl("https://qiita.com/api/v2/")
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(new RxJava2DeferredFactory(Schedulers.io())))
                .addConverterFactory(JsonicConverterFactory.create())
                .client(new OkHttpClient())
                .build())
                .build();

        BehaviorDelegate<TagsApi> delegate = mockRetrofit.create(TagsApi.class);
        mockTagsApi = new MockTagsApi(delegate).withDefault();
    }

    @Test
    public void tags() throws Exception {
        List<Tag> expected = MockTagsApi.getDefault();

        Deferred.Promise<Response<List<Tag>>> actualPromise = mockTagsApi.tags(0, 20, TagsApi.Sort.COUNT);

        assertThat(actualPromise)
                .isNotNull();
        Response<List<Tag>> response = RxJava2Promise.maybe(actualPromise).blockingGet();
        assertThat(response.body())
                .isEqualTo(expected);
    }

    @Test
    public void tag() throws Exception {
        Tag expected = MockTagsApi.getDefault().get(0);

        Deferred.Promise<Tag> actualPromise = mockTagsApi.tag("tagId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void unfollow() throws Exception {
        Success expected = Success.SUCCESS;

        Deferred.Promise<Success> actualPromise = mockTagsApi.unfollow("tagId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    private static class MockTagsApi implements TagsApi {
        private final BehaviorDelegate<TagsApi> delegate;
        private List<Tag> tags;

        private MockTagsApi(BehaviorDelegate<TagsApi> delegate) {
            this.delegate = delegate;
        }

        public MockTagsApi withDefault() throws IOException {
            tags = getDefault();
            return this;
        }

        public static List<Tag> getDefault() throws IOException {
            return TestFixture.asList(TestFixture.tag(), 20);
        }

        @Override
        public Deferred.Promise<Response<List<Tag>>> tags(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("sort") Sort sort) {
            return list().tags(page, perPage, sort);
        }

        @Override
        public Deferred.Promise<Tag> tag(@Path("tag_id") String tagId) {
            return single().tag(tagId);
        }

        @Override
        public Deferred.Promise<Response<List<Tag>>> tags(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().tags(userId, page, perPage);
        }

        @Override
        public Deferred.Promise<Success> unfollow(@Path("tag_id") String tagId) {
            return success().unfollow(tagId);
        }

        @Override
        public Deferred.Promise<Tag> following(@Path("tag_id") String tagId) {
            return success().following(tagId);
        }

        @Override
        public Deferred.Promise<Success> follow(@Path("tag_id") String tagId) {
            return success().follow(tagId);
        }

        //region Delegates
        private TagsApi single() {
            return delegate.returningResponse(tags.get(0));
        }

        private TagsApi list() {
            return delegate.returningResponse(tags);
        }

        private TagsApi success() {
            return delegate.returningResponse(Success.SUCCESS);
        }
        //endregion
    }
}
