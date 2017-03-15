package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/03/14.
 */
public class Group {
    @Nonnull
    public String created_at;
    public long id;
    @Nonnull
    public String name;
    private boolean isPrivate;
    @Nonnull
    public String updated_at;
    @Nonnull
    public String url_name;

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean value) {
        this.isPrivate = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != group.id) return false;
        if (isPrivate != group.isPrivate) return false;
        if (!created_at.equals(group.created_at)) return false;
        if (!name.equals(group.name)) return false;
        if (!updated_at.equals(group.updated_at)) return false;
        return url_name.equals(group.url_name);
    }

    @Override
    public int hashCode() {
        int result = created_at.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (isPrivate ? 1 : 0);
        result = 31 * result + updated_at.hashCode();
        result = 31 * result + url_name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", isPrivate=" + isPrivate +
                ", updated_at='" + updated_at + '\'' +
                ", url_name='" + url_name + '\'' +
                '}';
    }
}
