package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/03/14.
 */
public class ExpandedTemplate {
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

        ExpandedTemplate that = (ExpandedTemplate) o;

        if (!expanded_body.equals(that.expanded_body)) return false;
        if (!expanded_tags.equals(that.expanded_tags)) return false;
        return expanded_title.equals(that.expanded_title);
    }

    @Override
    public int hashCode() {
        int result = expanded_body.hashCode();
        result = 31 * result + expanded_tags.hashCode();
        result = 31 * result + expanded_title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExpandedTemplate{" +
                "expanded_body='" + expanded_body + '\'' +
                ", expanded_tags=" + expanded_tags +
                ", expanded_title='" + expanded_title + '\'' +
                '}';
    }
}
