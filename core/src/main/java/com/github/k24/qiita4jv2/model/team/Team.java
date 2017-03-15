package com.github.k24.qiita4jv2.model.team;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/03/13.
 */
public class Team {
    public boolean active;
    @Nonnull
    public String id;
    @Nonnull
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (active != team.active) return false;
        if (!id.equals(team.id)) return false;
        return name.equals(team.name);
    }

    @Override
    public int hashCode() {
        int result = (active ? 1 : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "active=" + active +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
