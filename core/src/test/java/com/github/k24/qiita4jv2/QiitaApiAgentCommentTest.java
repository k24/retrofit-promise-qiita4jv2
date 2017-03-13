package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.deferred.RxJava2Promise;
import com.github.k24.qiita4jv2.api.AccessTokensApi;
import com.github.k24.qiita4jv2.api.CommentsApi;
import com.github.k24.qiita4jv2.model.Comment;
import com.github.k24.qiita4jv2.model.CommentBody;
import com.github.k24.qiita4jv2.model.Item;
import com.github.k24.qiita4jv2.util.Success;
import com.github.k24.retrofit2.adapter.promise.PromiseCallAdapterFactory;
import com.github.k24.retrofit2.converter.jsonic.JsonicConverterFactory;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.k24.deferred.Assertions.assertThat;

/**
 * Created by k24 on 2017/03/06.
 */
public class QiitaApiAgentCommentTest {
    private MockRetrofit mockRetrofit;
    private MockCommentsApi mockCommentsApi;

    @Before
    public void setUp() throws Exception {
        mockRetrofit = new MockRetrofit.Builder(new Retrofit.Builder()
                .baseUrl("https://qiita.com/api/v2/")
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(RxJava2DeferredFactory.createWithScheduler(Schedulers.io())))
                .addConverterFactory(JsonicConverterFactory.create())
                .client(new OkHttpClient())
                .build())
                .build();
        NetworkBehavior networkBehavior = mockRetrofit.networkBehavior();
        networkBehavior.setErrorPercent(0);
        networkBehavior.setFailurePercent(0);

        AccessTokensApi.AccessTokenResponse response = new AccessTokensApi.AccessTokenResponse();
        response.token = "token";
        response.scopes = Arrays.asList(AccessTokensApi.Scope.READ.key(), AccessTokensApi.Scope.WRITE.key());

        BehaviorDelegate<CommentsApi> delegate = mockRetrofit.create(CommentsApi.class);
        mockCommentsApi = new MockCommentsApi(delegate).withDefault();
    }

    @Test
    public void deleteComment() throws Exception {
        Success expected = Success.SUCCESS;

        Deferred.Promise<Success> actualPromise = mockCommentsApi.deleteComment("commentId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void comment() throws Exception {
        Comment expected = MockCommentsApi.getDefault().get(0);

        Deferred.Promise<Comment> actualPromise = mockCommentsApi.comment("commentId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void commentsByItem() throws Exception {
        List<Comment> expected = MockCommentsApi.getDefault();

        Deferred.Promise<List<Comment>> actualPromise = mockCommentsApi.commentsByItem("itemId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    private static class MockCommentsApi implements CommentsApi {
        private final BehaviorDelegate<CommentsApi> delegate;
        private List<Comment> comments;

        public MockCommentsApi(BehaviorDelegate<CommentsApi> delegate) {
            this.delegate = delegate;
        }

        public MockCommentsApi withDefault() throws IOException {
            comments = getDefault();
            return this;
        }

        public static List<Comment> getDefault() throws IOException {
            return TestFixture.asList(TestFixture.comment(), 20);
        }

        @Override
        public Deferred.Promise<Success> deleteComment(@Path("comment_id") String commentId) {
            return success().deleteComment(commentId);
        }

        @Override
        public Deferred.Promise<Comment> comment(@Query("comment_id") String commentId) {
            return single().comment(commentId);
        }

        @Override
        public Deferred.Promise<Comment> patchComment(@Body CommentBody comment) {
            return single().patchComment(comment);
        }

        @Override
        public Deferred.Promise<List<Comment>> commentsByItem(@Path("item_id") String itemId) {
            return list().commentsByItem(itemId);
        }

        @Override
        public Deferred.Promise<Comment> postCommentForItem(@Path("item_id") String itemId, @Body CommentBody comment) {
            return single().postCommentForItem(itemId, comment);
        }

        //region Delegates
        private CommentsApi single() {
            return delegate.returningResponse(comments.get(0));
        }

        private CommentsApi list() {
            return delegate.returningResponse(comments);
        }

        private CommentsApi success() {
            return delegate.returningResponse(Success.SUCCESS);
        }
        //endregion
    }
}
