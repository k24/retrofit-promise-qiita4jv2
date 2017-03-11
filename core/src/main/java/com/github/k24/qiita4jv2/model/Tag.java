package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/12.
 */
public class Tag {
    public int followers_count;
    public String icon_url;
    @Nonnull
    public String id;
    public int items_count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (followers_count != tag.followers_count) return false;
        if (items_count != tag.items_count) return false;
        if (icon_url != null ? !icon_url.equals(tag.icon_url) : tag.icon_url != null) return false;
        return id.equals(tag.id);

    }

    @Override
    public int hashCode() {
        int result = followers_count;
        result = 31 * result + (icon_url != null ? icon_url.hashCode() : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + items_count;
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "followers_count=" + followers_count +
                ", icon_url='" + icon_url + '\'' +
                ", id='" + id + '\'' +
                ", items_count=" + items_count +
                '}';
    }
}
