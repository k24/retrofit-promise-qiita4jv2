package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/13.
 */
public class Reaction extends ReactionName {
    @Nonnull
    public String created_at;
    @Nonnull
    public String image_url;
    public User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reaction reaction = (Reaction) o;

        if (!created_at.equals(reaction.created_at)) return false;
        if (!image_url.equals(reaction.image_url)) return false;
        return user != null ? user.equals(reaction.user) : reaction.user == null;

    }

    @Override
    public int hashCode() {
        int result = created_at.hashCode();
        result = 31 * result + image_url.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "created_at='" + created_at + '\'' +
                ", image_url='" + image_url + '\'' +
                ", user=" + user +
                "} " + super.toString();
    }
}
