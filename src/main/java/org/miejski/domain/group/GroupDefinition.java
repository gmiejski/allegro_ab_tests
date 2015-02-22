package org.miejski.domain.group;

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
}
