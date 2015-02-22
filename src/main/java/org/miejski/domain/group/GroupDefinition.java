package org.miejski.domain.group;

import java.util.Objects;

public class GroupDefinition {

    private final Group group;
    private final GroupWeight weight;

    public static GroupDefinition of(String groupName, int weight) {
        return new GroupDefinition(groupName, weight);
    }

    private GroupDefinition(String groupName, int weight) {
        this.group = Group.of(groupName);
        this.weight = GroupWeight.of(weight);
    }

    public Group getGroup() {
        return group;
    }

    public GroupWeight getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupDefinition that = (GroupDefinition) o;
        return Objects.equals(this.getGroup(), that.getGroup())
                && Objects.equals(this.getWeight(), that.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getGroup(), this.getWeight());
    }
}
