package com.github.k24.qiita4jv2;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2Promise;
import com.github.k24.qiita4jv2.model.Tag;
import okhttp3.Headers;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by k24 on 2017/03/08.
 */
public class QiitaPaginationTest {

    @Test
    public void toPagination() throws Exception {
        Tag tag = new Tag();
        tag.id = "tagId";
        tag.icon_url = "icon_url";

        QiitaPagination<Tag> pagination = QiitaPagination.toPagination(Response.success(tag, new Headers.Builder()
                .add("Link", linkHeader())
                .add("Total-Count", "6")
                .build()));

        assertThat(pagination.data())
                .isEqualTo(tag);
        assertThat(pagination.links())
                .isNotEmpty()
                .hasSize(4)
                .containsEntry(QiitaPagination.LinkRel.FIRST, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.PREV, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.NEXT, "http://qiita.com/api/v2/users?page=3")
                .containsEntry(QiitaPagination.LinkRel.LAST, "http://qiita.com/api/v2/users?page=6");
        assertThat(pagination.totalCount())
                .isEqualTo(6);
    }

    @Test
    public void mapPaginate() throws Exception {
        Tag tag = new Tag();
        tag.id = "tagId";
        tag.icon_url = "icon_url";

        Deferred.Promise<QiitaPagination<Tag>> actualPromise = RxJava2Promise.just(Response.success(tag, new Headers.Builder()
                .add("Link", linkHeader())
                .add("Total-Count", "6")
                .build()))
                .then(QiitaPagination.<Tag>mapPaginate());

        QiitaPagination<Tag> pagination = actualPromise.waitAndGet();
        assertThat(pagination.data())
                .isEqualTo(tag);
        assertThat(pagination.links())
                .isNotEmpty()
                .hasSize(4)
                .containsEntry(QiitaPagination.LinkRel.FIRST, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.PREV, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.NEXT, "http://qiita.com/api/v2/users?page=3")
                .containsEntry(QiitaPagination.LinkRel.LAST, "http://qiita.com/api/v2/users?page=6");
        assertThat(pagination.totalCount())
                .isEqualTo(6);
    }

    @Test
    public void parseLink() throws Exception {
        Map<String, String> map = QiitaPagination.parseLink(linkHeader());

        assertThat(map)
                .isNotEmpty()
                .hasSize(4)
                .containsEntry(QiitaPagination.LinkRel.FIRST, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.PREV, "http://qiita.com/api/v2/users?page=1")
                .containsEntry(QiitaPagination.LinkRel.NEXT, "http://qiita.com/api/v2/users?page=3")
                .containsEntry(QiitaPagination.LinkRel.LAST, "http://qiita.com/api/v2/users?page=6");
    }

    @Test
    public void parseTotalCount() throws Exception {
        Integer totalCount = QiitaPagination.parseTotalCount("6");

        assertThat(totalCount)
                .isEqualTo(6);
    }

    private static String linkHeader() {
        return "<http://qiita.com/api/v2/users?page=1>; rel=\"first\"," +
                "      <http://qiita.com/api/v2/users?page=1>; rel=\"prev\"," +
                "      <http://qiita.com/api/v2/users?page=3>; rel=\"next\"," +
                "      <http://qiita.com/api/v2/users?page=6>; rel=\"last\"";
    }
}