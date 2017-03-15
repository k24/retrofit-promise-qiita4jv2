package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/03/13.
 */
public class Template extends TemplateBody {
    public long id;
    @Nonnull
    public String expanded_body;
    @Nonnull
    public List<Tagging> expanded_tags;
    @Nonnull
    public String expanded_title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        if (id != template.id) return false;
        if (!expanded_body.equals(template.expanded_body)) return false;
        if (!expanded_tags.equals(template.expanded_tags)) return false;
        return expanded_title.equals(template.expanded_title);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + expanded_body.hashCode();
        result = 31 * result + expanded_tags.hashCode();
        result = 31 * result + expanded_title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", expanded_body='" + expanded_body + '\'' +
                ", expanded_tags=" + expanded_tags +
                ", expanded_title='" + expanded_title + '\'' +
                "} " + super.toString();
    }
}
