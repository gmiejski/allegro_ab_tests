package org.miejski.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Group {

    private String name;

    public static Group of(String name) {
        return new Group(name);
    }

    private Group(String groupName) {
        this.name = groupName;
    }

    @JsonProperty
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return Objects.equals(this.name(), group.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }
}
