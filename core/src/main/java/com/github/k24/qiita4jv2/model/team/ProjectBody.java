package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/03/13.
 */
public class ProjectBody {
    public boolean archived;
    @Nonnull
    public String body;
    @Nonnull
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectBody that = (ProjectBody) o;

        if (archived != that.archived) return false;
        if (!body.equals(that.body)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = (archived ? 1 : 0);
        result = 31 * result + body.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProjectBody{" +
                "archived=" + archived +
                ", body='" + body + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

