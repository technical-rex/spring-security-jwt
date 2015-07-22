package com.technicalrex.springsecurityjwt.api.jars;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.technicalrex.springsecurityjwt.support.validation.StringConditions;

public class Jar {
    private final String name;
    private final String goal;

    public Jar(String name, String goal) {
        this.name = StringConditions.checkNotBlank(name);
        this.goal = StringConditions.checkNotBlank(goal);
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, goal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jar that = (Jar) o;
        return Objects.equal(this.name, that.name)
                && Objects.equal(this.goal, that.goal);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("goal", goal)
                .toString();
    }
}
