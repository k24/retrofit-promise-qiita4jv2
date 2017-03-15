package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/03/13.
 */
public class Project extends ProjectBody {
    @Nonnull
    public String rendered_body;
    @Nonnull
    public String created_at;
    public long id;
    @Nonnull
    public String updated_at;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (!rendered_body.equals(project.rendered_body)) return false;
        if (!created_at.equals(project.created_at)) return false;
        return updated_at.equals(project.updated_at);
    }

    @Override
    public int hashCode() {
        int result = rendered_body.hashCode();
        result = 31 * result + created_at.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + updated_at.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "rendered_body='" + rendered_body + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", updated_at='" + updated_at + '\'' +
                "} " + super.toString();
    }
}
