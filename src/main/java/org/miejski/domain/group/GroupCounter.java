package org.miejski.domain.group;

import java.util.concurrent.atomic.AtomicInteger;

public class GroupCounter {

    private final Group group;
    private final int startingWeight;
    private final AtomicInteger assignsLeft;

    public GroupCounter(GroupDefinition groupDefinition) {
        this.group = groupDefinition.getGroup();
        this.startingWeight = groupDefinition.getWeight().getValue();
        this.assignsLeft = new AtomicInteger(groupDefinition.getWeight().getValue());
    }

    public Group getGroup() {
        return group;
    }

    public void decrease() {
        if (!canAssignMore()) {
            throw new IllegalStateException("Cannot assign more people in current turn to this group!");
        }
        assignsLeft.getAndDecrement();
    }

    public boolean canAssignMore() {
        return assignsLeft.get() > 0;
    }

    public void reset() {
        this.assignsLeft.set(startingWeight);
    }
}
