package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/03/13.
 */
public class TemplateBody {
    @Nonnull
    public String body;
    @Nonnull
    public String name;
    @Nonnull
    public List<Tagging> tags;
    @Nonnull
    public String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateBody that = (TemplateBody) o;

        if (!body.equals(that.body)) return false;
        if (!name.equals(that.name)) return false;
        if (!tags.equals(that.tags)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = body.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TemplateBody{" +
                "body='" + body + '\'' +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                '}';
    }
}
