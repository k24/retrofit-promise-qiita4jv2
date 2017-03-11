package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.deferred.RxJava2Promise;
import com.github.k24.qiita4jv2.api.AccessTokensApi;
import com.github.k24.qiita4jv2.api.ItemsApi;
import com.github.k24.qiita4jv2.model.Item;
import com.github.k24.qiita4jv2.model.ItemBody;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.k24.deferred.Assertions.assertThat;

/**
 * Created by k24 on 2017/02/14.
 */
public class QiitaApiAgentItemTest {

    private MockRetrofit mockRetrofit;
    private MockItemsApi mockItemsApi;

    @Before
    public void setUp() throws Exception {
        mockRetrofit = new MockRetrofit.Builder(new Retrofit.Builder()
                .baseUrl("https://qiita.com/api/v2/")
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(new RxJava2DeferredFactory(Schedulers.io())))
                .addConverterFactory(JsonicConverterFactory.create())
                .client(new OkHttpClient())
                .build())
                .build();

        AccessTokensApi.AccessTokenResponse response = new AccessTokensApi.AccessTokenResponse();
        response.token = "token";
        response.scopes = Arrays.asList(AccessTokensApi.Scope.READ.key(), AccessTokensApi.Scope.WRITE.key());

        BehaviorDelegate<ItemsApi> delegate = mockRetrofit.create(ItemsApi.class);
        mockItemsApi = new MockItemsApi(delegate).withDefault();
    }

    @Test
    public void authenticatedUserItems() throws Exception {
        List<Item> expected = MockItemsApi.defaultItems();

        Deferred.Promise<Response<List<Item>>> actualPromise = mockItemsApi.authenticatedUserItems(0, 20);

        assertThat(actualPromise)
                .isNotNull();
        Response<List<Item>> response = RxJava2Promise.maybe(actualPromise).blockingGet();
        Assertions.assertThat(response.body())
                .isEqualTo(expected);
    }

    @Test
    public void items() throws Exception {
        List<Item> expected = MockItemsApi.defaultItems();

        Deferred.Promise<Response<List<Item>>> actualPromise = mockItemsApi.items(0, 20, "query");

        assertThat(actualPromise)
                .isNotNull();
        Response<List<Item>> response = RxJava2Promise.maybe(actualPromise).blockingGet();
        Assertions.assertThat(response.body())
                .isEqualTo(expected);
    }

    @Test
    public void item() throws Exception {
        Item expected = TestFixture.item();

        Deferred.Promise<Item> actualPromise = mockItemsApi.item("itemid");

        assertThat(actualPromise)
                .isNotNull()
                .verify(expected);
    }

    @Test
    public void stock() throws Exception {
        Deferred.Promise<Success> actualPromise = mockItemsApi.stock("itemid");

        assertThat(actualPromise)
                .isNotNull()
                .verify(Success.SUCCESS);
    }

    private static class MockItemsApi implements ItemsApi {
        private final BehaviorDelegate<ItemsApi> delegate;
        private List<Item> items;

        public MockItemsApi(BehaviorDelegate<ItemsApi> delegate) {
            this.delegate = delegate;
        }

        public MockItemsApi withDefault() throws IOException {
            items = defaultItems();
            return this;
        }

        public static List<Item> defaultItems() throws IOException {
            return TestFixture.asList(TestFixture.item(), 20);
        }

        @Override
        public Deferred.Promise<Response<List<Item>>> authenticatedUserItems(@Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().authenticatedUserItems(page, perPage);
        }

        @Override
        public Deferred.Promise<Response<List<Item>>> items(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("query") String query) {
            return list().items(page, perPage, query);
        }

        @Override
        public Deferred.Promise<Item> postItem(@Body ItemBody item) {
            return single().postItem(item);
        }

        @Override
        public Deferred.Promise<Item> deleteItem(@Path("item_id") String itemId) {
            return single().deleteItem(itemId);
        }

        @Override
        public Deferred.Promise<Item> item(@Path("item_id") String itemId) {
            return single().item(itemId);
        }

        @Override
        public Deferred.Promise<Item> patchItem(@Path("item_id") String itemId, @Body ItemBody item) {
            return single().patchItem(itemId, item);
        }

        @Override
        public Deferred.Promise<Success> stock(@Path("item_id") String itemId) {
            return success().stock(itemId);
        }

        @Override
        public Deferred.Promise<Success> unstock(@Path("item_id") String itemId) {
            return success().unstock(itemId);
        }

        @Override
        public Deferred.Promise<Success> stocking(@Path("item_id") String itemId) {
            return success().stocking(itemId);
        }

        @Override
        public Deferred.Promise<Response<List<Item>>> itemsByTag(@Path("tag_id") String tagId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().itemsByTag(tagId, page, perPage);
        }

        @Override
        public Deferred.Promise<Response<List<Item>>> itemsByUser(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().itemsByUser(userId, page, perPage);
        }

        @Override
        public Deferred.Promise<Response<List<Item>>> stockesByUser(@Path("user_id") String userId, @Query("page") Integer page, @Query("per_page") Integer perPage) {
            return list().stockesByUser(userId, page, perPage);
        }

        //region Delegates
        private ItemsApi single() {
            return delegate.returningResponse(items.get(0));
        }

        private ItemsApi list() {
            return delegate.returningResponse(items);
        }

        private ItemsApi success() {
            return delegate.returningResponse(Success.SUCCESS);
        }
        //endregion
    }
}