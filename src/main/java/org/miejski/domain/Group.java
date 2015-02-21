package org.miejski.domain;

public class Group {

    private String name;

    public static Group of(String name) {
        return new Group(name);
    }

    private Group(String groupName) {
        this.name = groupName;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return !(name != null ? !name.equals(group.name) : group.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Group(" + name + ")";
    }
}
