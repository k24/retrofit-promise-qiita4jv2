package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import okhttp3.Headers;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by k24 on 2017/02/25.
 */
@SuppressWarnings("WeakerAccess")
public class QiitaPagination<T> {
    private final T data;
    private final Integer totalCount;
    private final Map<String, String> links;

    public QiitaPagination(T data, Integer totalCount, Map<String, String> links) {
        this.data = data;
        this.totalCount = totalCount;
        this.links = links;
    }

    public T data() {
        return data;
    }

    public Integer totalCount() {
        return totalCount;
    }

    public Map<String, String> links() {
        return links;
    }

    @Nonnull
    public static <T> QiitaPagination<T> toPagination(Response<T> response) {
        Headers headers = response.headers();
        String link = headers.get("Link");
        String totalCount = headers.get("Total-Count");
        return new QiitaPagination<>(response.body(), parseTotalCount(totalCount), parseLink(link));
    }

    @Nonnull
    public static <T> Deferred.OnResolved<Response<T>, QiitaPagination<T>> mapPaginate() {
        return new Deferred.OnResolved<Response<T>, QiitaPagination<T>>() {
            @Override
            public QiitaPagination<T> onResolved(Response<T> tResponse) throws Exception {
                return toPagination(tResponse);
            }
        };
    }

    // http://qiita.com/api/v2/docs#%E3%83%9A%E3%83%BC%E3%82%B8%E3%83%8D%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3
    public static Map<String, String> parseLink(String link) {
        try {
            String[] splits = link.split(",");
            Pattern pattern = Pattern.compile("<([^>]+)>; rel=\"(\\w+)\"");
            HashMap<String, String> map = new HashMap<>();

            for (String split : splits) {
                Matcher matcher = pattern.matcher(split.trim());
                if (matcher.matches()) {
                    map.put(matcher.group(2), matcher.group(1));
                }
            }

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer parseTotalCount(String totalCount) {
        try {
            return Integer.valueOf(totalCount, 10);
        } catch (Exception e) {
            return null;
        }
    }

    public static class LinkRel {
        public static final String FIRST = "first";
        public static final String PREV = "prev";
        public static final String NEXT = "next";
        public static final String LAST = "last";
    }
}
