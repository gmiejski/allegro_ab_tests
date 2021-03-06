package org.miejski.service.group.allocator;

import org.miejski.domain.group.GroupCounter;
import org.miejski.domain.group.GroupDefinition;
import org.miejski.service.group.GroupDefinitionsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component("standardGroupAllocator")
public class AccurateGroupAllocator implements GroupAllocator {

    final List<GroupCounter> groupCounters;

    @Autowired
    public AccurateGroupAllocator(GroupDefinitionsProvider groupDefinitionsProvider) {
        List<GroupDefinition> groupDefinitions = groupDefinitionsProvider.getGroups();
        AllocatorStatePreconditions.checkGroupsDefinitionState(groupDefinitions);
        this.groupCounters = groupDefinitions.stream().map(GroupCounter::new).collect(toList());
    }

    @Override
    public synchronized String assignGroup() {
        GroupCounter groupCounter = getFirstAssignableGroup().orElseGet(() -> {
            resetGroupsConfiguration();
            return getFirstAssignableGroup().get();
        });

        groupCounter.decrease();
        return groupCounter.getGroup().name();
    }

    private Optional<GroupCounter> getFirstAssignableGroup() {
        return groupCounters.stream().filter(GroupCounter::canAssignMore).findFirst();
    }

    private void resetGroupsConfiguration() {
        groupCounters.forEach(GroupCounter::reset);
    }
}
