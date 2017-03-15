package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by k24 on 2017/03/13.
 */
public class Tagging {
    @Nonnull
    public String name;
    @Nonnull
    public List<String> versions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tagging tagging = (Tagging) o;

        if (!name.equals(tagging.name)) return false;
        return versions.equals(tagging.versions);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + versions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tagging{" +
                "name='" + name + '\'' +
                ", versions=" + versions +
                '}';
    }
}
