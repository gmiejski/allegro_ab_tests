package org.miejski.domain.group;

public class GroupWeight {

    private final int value;

    public static GroupWeight of(int weight) {
        return new GroupWeight(weight);
    }

    private GroupWeight(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
