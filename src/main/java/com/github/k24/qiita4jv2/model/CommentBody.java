package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/12.
 */
public class CommentBody {
    @Nonnull
    public String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentBody that = (CommentBody) o;

        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public String toString() {
        return "CommentBody{" +
                "body='" + body + '\'' +
                '}';
    }
}
