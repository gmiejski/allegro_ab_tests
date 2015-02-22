package org.miejski.service.allocator;

import org.miejski.domain.group.GroupDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Component("statisticalGroupAllocator")
public class StatisticalGroupAllocator implements GroupAllocator {

    final List<GroupDefinition> groupDefinitions;
    final List<String> groupsMultiplicated;

    public StatisticalGroupAllocator(List<GroupDefinition> groupDefinitions) {
        this.groupDefinitions = groupDefinitions;
        this.groupsMultiplicated = groupDefinitions.stream().flatMap(x -> IntStream.range(0, x.getWeight().getValue()).boxed().map(y -> x.getGroup().name())).collect(toList());
    }

    public StatisticalGroupAllocator() {
        int[] weights = new int[]{2, 3, 5};
        this.groupDefinitions = stream(weights).boxed().map(x -> GroupDefinition.of("group" + x, x)).collect(toList());
        this.groupsMultiplicated = groupDefinitions.stream().flatMap(x -> IntStream.range(0, x.getWeight().getValue()).boxed().map(y -> x.getGroup().name())).collect(toList());
    }

    public String assignGroup() {
        int groupIndex = new Random().nextInt(groupsMultiplicated.size());
        return groupsMultiplicated.get(groupIndex);
    }
}
