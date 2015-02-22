package org.miejski.domain.group;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupWeight that = (GroupWeight) o;
        return Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }
}
