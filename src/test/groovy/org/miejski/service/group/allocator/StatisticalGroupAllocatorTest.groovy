package org.miejski.service.group.allocator

import org.miejski.domain.group.GroupDefinition
import org.miejski.service.group.GroupDefinitionsProvider
import spock.lang.Specification

class StatisticalGroupAllocatorTest extends Specification {

    def StatisticalGroupAllocator instance

    def static weights = [2, 3, 5]
    def static List<GroupDefinition> groups = weights.collect { GroupDefinition.of("group" + it, it) }.toList()
    def GroupDefinitionsProvider groupDefinitionsProvider

    void setup() {
        groupDefinitionsProvider = Mock(GroupDefinitionsProvider)
        groupDefinitionsProvider.getGroups() >> groups
        instance = new StatisticalGroupAllocator(groupDefinitionsProvider)
    }

    def "should throw exception when didn't receive proper groups configurations"() {
        given:
        GroupDefinitionsProvider unproperProvider = Mock(GroupDefinitionsProvider)
        unproperProvider.getGroups() >> returned

        when:
        new StatisticalGroupAllocator(unproperProvider)

        then:
        thrown(IllegalStateException)

        where:
        returned                     | _
        null                         | _
        []                           | _
        [GroupDefinition.of("1", 0)] | _
    }

    def "should work properly with at least one proper group definition received"() {
        given:
        groupDefinitionsProvider.getGroups() >> [GroupDefinition.of("1", 0),
                                                 GroupDefinition.of("2", 1),
                                                 GroupDefinition.of("3", 0)]

        when:
        new StatisticalGroupAllocator(groupDefinitionsProvider)

        then:
        noExceptionThrown()
    }

    def "should assign groups to users with sufficient ratio"() {
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
            (map.get(it.group.name()) < allRequestsCount / totalWeights * it.weight.value * 1.15) &&
                    (map.get(it.group.name()) > allRequestsCount / totalWeights * it.weight.value * 0.85)
        }
    }
}
