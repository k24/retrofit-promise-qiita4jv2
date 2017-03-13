package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.qiita4jv2.api.ReactionsApi;
import com.github.k24.qiita4jv2.model.Reaction;
import com.github.k24.qiita4jv2.model.ReactionName;
import com.github.k24.qiita4jv2.util.Success;
import com.github.k24.retrofit2.adapter.promise.PromiseCallAdapterFactory;
import com.github.k24.retrofit2.converter.jsonic.JsonicConverterFactory;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import java.io.IOException;
import java.util.List;

import static com.github.k24.deferred.Assertions.assertThat;

/**
 * Created by k24 on 2017/03/06.
 */
public class QiitaApiAgentReactionTest {

    private MockRetrofit mockRetrofit;
    private MockReactionsApi mockReactionsApi;

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

        BehaviorDelegate<ReactionsApi> delegate = mockRetrofit.create(ReactionsApi.class);
        mockReactionsApi = new MockReactionsApi(delegate).withDefault();
    }

    @Test
    public void postReactionForComment() throws Exception {
        Reaction expected = MockReactionsApi.defaultReactions().get(0);

        Deferred.Promise<Reaction> actualPromise = mockReactionsApi.postReactionForComment("commentId", new ReactionName());

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void reactionsForComment() throws Exception {
        List<Reaction> expected = MockReactionsApi.defaultReactions();

        Deferred.Promise<List<Reaction>> actualPromise = mockReactionsApi.reactionsForComment("commentId");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    private static class MockReactionsApi implements ReactionsApi {
        private final BehaviorDelegate<ReactionsApi> delegate;
        private List<Reaction> reactions;

        public MockReactionsApi(BehaviorDelegate<ReactionsApi> delegate) {
            this.delegate = delegate;
        }

        public MockReactionsApi withDefault() throws IOException {
            reactions = defaultReactions();
            return this;
        }

        public static List<Reaction> defaultReactions() throws IOException {
            return TestFixture.asList(TestFixture.reaction(), 20);
        }

        @Override
        public Deferred.Promise<Reaction> postReactionForComment(@Path("comment_id") String commentId, @Body ReactionName reaction) {
            return single().postReactionForComment(commentId, reaction);
        }

        @Override
        public Deferred.Promise<Reaction> postReactionForItem(@Path("item_id") String itemId, @Body ReactionName reaction) {
            return single().postReactionForItem(itemId, reaction);
        }

        @Override
        public Deferred.Promise<Reaction> deleteReactionFromComment(@Path("comment_id") String commentId, @Path("reaction_name") String reactionName) {
            return single().deleteReactionFromComment(commentId, reactionName);
        }

        @Override
        public Deferred.Promise<Reaction> postReactionForItem(@Path("item_id") String itemId, @Path("reaction_name") String reactionName) {
            return single().postReactionForItem(itemId, reactionName);
        }

        @Override
        public Deferred.Promise<List<Reaction>> reactionsForComment(@Path("comment_id") String commentId) {
            return list().reactionsForComment(commentId);
        }

        @Override
        public Deferred.Promise<List<Reaction>> reactionsForItem(@Path("item_id") String itemId) {
            return list().reactionsForItem(itemId);
        }

        //region Delegates
        private ReactionsApi single() {
            return delegate.returningResponse(reactions.get(0));
        }

        private ReactionsApi list() {
            return delegate.returningResponse(reactions);
        }

        private ReactionsApi success() {
            return delegate.returningResponse(Success.SUCCESS);
        }
        //endregion
    }
}
