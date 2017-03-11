package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.qiita4jv2.api.AccessTokensApi;
import com.github.k24.qiita4jv2.model.Item;
import com.github.k24.qiita4jv2.util.Success;
import com.github.k24.retrofit2.converter.jsonic.JsonicConverterFactory;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.List;

/**
 * Created by k24 on 2017/03/11.
 */
public class Qiita4jv2Sample {
    public static void main(String[] args) {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println(throwable.toString());
            }
        });

        String accessToken = args.length > 0 ? args[0] : "";
        if (accessToken.isEmpty()) {
            accessToken = System.getenv("QIITA_ACCESS_TOKEN");
            if (accessToken == null || accessToken.isEmpty()) {
                System.out.println("Set AccessToken with args[0] or QIITA_ACCESS_TOKEN");
                return;
            }
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = QiitaApiAgent.okHttpClientBuilder(accessToken)
                .addInterceptor(loggingInterceptor)
                .build();

        QiitaApiAgent qiitaApiAgent = QiitaApiAgent.create(
                new QiitaApiAgent.Config(JsonicConverterFactory.create(),
                        new RxJava2DeferredFactory(Schedulers.io())
                ),
                okHttpClient,
                accessToken);

        try {
            qiitaApiAgent.itemsApi().items(null, null, "tag:android")
                    .then(QiitaPagination.<List<Item>>mapPaginate())
                    .then(new Deferred.OnResolved<QiitaPagination<List<Item>>, Success>() {
                        @Override
                        public Success onResolved(QiitaPagination<List<Item>> listQiitaPagination) throws Exception {
                            System.out.println("totalCount: " + listQiitaPagination.totalCount());
                            for (Item item : listQiitaPagination.data()) {
                                System.out.println(item.title);
                            }
                            return Success.SUCCESS;
                        }
                    }).waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
