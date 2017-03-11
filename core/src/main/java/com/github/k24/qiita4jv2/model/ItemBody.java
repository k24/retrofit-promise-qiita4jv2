package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/13.
 */
public class ItemBody {
    @Nonnull
    public String body;
    public boolean coediting; // Only Team
    public boolean gist;
    private boolean isPrivate; // Only non-Team
    //    public tags;
    @Nonnull
    public String title;
    public boolean tweet;

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

        ItemBody itemBody = (ItemBody) o;

        if (coediting != itemBody.coediting) return false;
        if (gist != itemBody.gist) return false;
        if (isPrivate != itemBody.isPrivate) return false;
        if (tweet != itemBody.tweet) return false;
        if (!body.equals(itemBody.body)) return false;
        return title.equals(itemBody.title);

    }

    @Override
    public int hashCode() {
        int result = body.hashCode();
        result = 31 * result + (coediting ? 1 : 0);
        result = 31 * result + (gist ? 1 : 0);
        result = 31 * result + (isPrivate ? 1 : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (tweet ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemBody{" +
                "body='" + body + '\'' +
                ", coediting=" + coediting +
                ", gist=" + gist +
                ", isPrivate=" + isPrivate +
                ", title='" + title + '\'' +
                ", tweet=" + tweet +
                '}';
    }
}
