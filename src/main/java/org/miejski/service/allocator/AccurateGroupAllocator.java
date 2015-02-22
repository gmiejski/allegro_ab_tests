package org.miejski.service.allocator;

import org.miejski.domain.group.GroupCounter;
import org.miejski.domain.group.GroupDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Component("standardGroupAllocator")
public class AccurateGroupAllocator implements GroupAllocator {

    final List<GroupDefinition> groupDefinitions;
    final List<GroupCounter> groupCounters;
    private Map<String, AtomicInteger> aaa = new ConcurrentHashMap<>();

    public AccurateGroupAllocator(List<GroupDefinition> groupsDefinitions) {
        this.groupDefinitions = groupsDefinitions;
        this.groupCounters = groupDefinitions.stream().map(GroupCounter::new).collect(toList());
        groupDefinitions.forEach(x -> aaa.put(x.getGroup().name(), new AtomicInteger()));
    }

    public AccurateGroupAllocator() {
        int[] weights = new int[]{2, 3, 5};
        this.groupDefinitions = stream(weights).boxed().map(x -> GroupDefinition.of("group" + x, x)).collect(toList());
        this.groupCounters = groupDefinitions.stream().map(GroupCounter::new).collect(toList());
        groupDefinitions.forEach(x -> aaa.put(x.getGroup().name(), new AtomicInteger()));
    }

    @Override
    public synchronized String assignGroup() {
        Optional<GroupCounter> assignableGroup = groupCounters.stream().filter(GroupCounter::canAssignMore).findFirst();

        if (!assignableGroup.isPresent()) {
            groupCounters.forEach(GroupCounter::reset);
            assignableGroup = groupCounters.stream().filter(GroupCounter::canAssignMore).findFirst();
        }
        assignableGroup.get().decrease();
        String name = assignableGroup.get().getGroup().name();
        aaa.get(name).incrementAndGet();
        return name;
    }
}
