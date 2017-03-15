package com.github.k24.qiita4jv2.model.team;

import com.github.k24.qiita4jv2.model.User;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/03/14.
 */
public class Like {
    @Nonnull
    public String created_at;
    @Nonnull
    public User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (!created_at.equals(like.created_at)) return false;
        return user.equals(like.user);
    }

    @Override
    public int hashCode() {
        int result = created_at.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "created_at='" + created_at + '\'' +
                ", user=" + user +
                '}';
    }
}
