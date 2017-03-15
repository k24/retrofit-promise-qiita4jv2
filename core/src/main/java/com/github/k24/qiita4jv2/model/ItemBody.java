package com.github.k24.qiita4jv2.model;

import com.github.k24.qiita4jv2.annotation.PersonalOnly;
import com.github.k24.qiita4jv2.annotation.TeamOnly;
import com.github.k24.qiita4jv2.model.team.Tagging;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/02/13.
 */
public class ItemBody {
    @Nonnull
    public String body;
    @TeamOnly
    public boolean coediting;
    public boolean gist;
    @PersonalOnly
    private boolean isPrivate;
    @TeamOnly
    public List<Tagging> tags;
    @Nonnull
    public String title;
    public boolean tweet;

    @PersonalOnly
    public boolean isPrivate() {
        return isPrivate;
    }

    @PersonalOnly
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
