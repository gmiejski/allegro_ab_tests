package org.miejski.service.group.allocator;

import org.miejski.domain.group.GroupDefinition;
import org.miejski.service.group.GroupDefinitionsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Component("statisticalGroupAllocator")
public class StatisticalGroupAllocator implements GroupAllocator {

    final List<GroupDefinition> groupDefinitions;
    final List<String> groups;

    @Autowired
    public StatisticalGroupAllocator(GroupDefinitionsProvider groupDefinitionsProvider) {
        this.groupDefinitions = groupDefinitionsProvider.getGroups();
        AllocatorStatePreconditions.checkGroupsDefinitionState(groupDefinitions);
        this.groups = groupDefinitions.stream()
                .flatMap(groupDefinition -> range(0, groupDefinition.getWeight().getValue())
                        .boxed().map(index -> groupDefinition.getGroup().name()))
                .collect(toList());
    }

    public String assignGroup() {
        int assignedGroupIndex = new Random().nextInt(groups.size());
        return groups.get(assignedGroupIndex);
    }
}
