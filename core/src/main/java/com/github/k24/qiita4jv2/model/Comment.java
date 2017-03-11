package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/12.
 */
public class Comment extends CommentBody {
    @Nonnull
    public String created_at;
    @Nonnull
    public String id;
    @Nonnull
    public String rendered_body;
    @Nonnull
    public String updated_at;
    public User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!created_at.equals(comment.created_at)) return false;
        if (!id.equals(comment.id)) return false;
        if (!rendered_body.equals(comment.rendered_body)) return false;
        if (!updated_at.equals(comment.updated_at)) return false;
        return user != null ? user.equals(comment.user) : comment.user == null;

    }

    @Override
    public int hashCode() {
        int result = created_at.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + rendered_body.hashCode();
        result = 31 * result + updated_at.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", rendered_body='" + rendered_body + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user=" + user +
                "} " + super.toString();
    }
}
