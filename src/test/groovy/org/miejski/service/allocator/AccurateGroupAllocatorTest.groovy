package org.miejski.service.allocator

import org.miejski.domain.group.GroupDefinition
import spock.lang.Specification

class AccurateGroupAllocatorTest extends Specification {

    def AccurateGroupAllocator instance

    def static weights = [2, 3, 5]
    def static List<GroupDefinition> groups = weights.collect { GroupDefinition.of("group" + it, it) }.toList()

    void setup() {
        instance = new AccurateGroupAllocator(groups)
    }

    def "should assign to groups exactly as the groups definition states"() {
        given:
        def map = [:]
        def start = System.currentTimeMillis()
        def totalWeights = groups.collect { it.weight.value }.sum()
        def allRequestsCount = 1_000_000

        when:
        (1..allRequestsCount).each {
            def groupName = instance.assignGroup()
            if (!map.containsKey(groupName)) {
                map.put(groupName, 1)
            } else {
                map.put(groupName, map.get(groupName) + 1)
            }
        }
        def end = System.currentTimeMillis()

        then:
        println('Total time: ' + (end - start))
        groups.every {
            map.get(it.group.name()) == (allRequestsCount / totalWeights * it.weight.value)
        }
    }


}
