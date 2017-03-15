package com.github.k24.qiita4jv2.model;

import com.github.k24.qiita4jv2.annotation.TeamOnly;
import com.github.k24.qiita4jv2.model.team.Group;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/13.
 */
public class Item extends ItemBody {
    @Nonnull
    public String rendered_body;
    @Nonnull
    public String created_at;
    @TeamOnly
    public Group group;
    @Nonnull
    public String id;
    @Nonnull
    public String updated_at;
    @Nonnull
    public String url;
    public User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!rendered_body.equals(item.rendered_body)) return false;
        if (!created_at.equals(item.created_at)) return false;
        if (!id.equals(item.id)) return false;
        if (!updated_at.equals(item.updated_at)) return false;
        if (!url.equals(item.url)) return false;
        return user != null ? user.equals(item.user) : item.user == null;

    }

    @Override
    public int hashCode() {
        int result = rendered_body.hashCode();
        result = 31 * result + created_at.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + updated_at.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "rendered_body='" + rendered_body + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                ", user=" + user +
                "} " + super.toString();
    }
}
