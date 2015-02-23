package org.miejski.service.group.allocator;

import org.miejski.domain.group.GroupDefinition;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toList;

public class AllocatorStatePreconditions {

    public static void checkGroupsDefinitionState(List<GroupDefinition> groupDefinitions) {
        checkState(groupsDefinitionsExists(groupDefinitions), "Didn't receive any group definitions");
        checkState(atLeastOneProperGroupDefinition(groupDefinitions), "Didn't receive any proper group definitions");
    }

    private static boolean atLeastOneProperGroupDefinition(List<GroupDefinition> groupDefinitions) {
        return groupDefinitions.stream().filter(x -> x.getWeight().getValue() != 0).findAny().isPresent();
    }

    private static boolean groupsDefinitionsExists(List<GroupDefinition> groupDefinitions) {
        return groupDefinitions != null && !groupDefinitions.stream().filter(Objects::nonNull).collect(toList()).isEmpty();
    }
}
