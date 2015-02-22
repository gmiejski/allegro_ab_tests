package org.miejski.service.group

import org.miejski.domain.group.GroupDefinition
import spock.lang.Specification

class GroupDefinitionsProviderTest extends Specification {

    def static List<GroupDefinition> groupDefinitions = [GroupDefinition.of("grupa A", 2),
                                                         GroupDefinition.of("grupa B", 3),
                                                         GroupDefinition.of("grupa C", 5),
                                                         GroupDefinition.of("grupa XX", 10)]

    def 'should load groups definitions from file'() {
        given:
        GroupDefinitionsProvider groupDefinitionsProvider = new GroupDefinitionsProvider(path: "groups-test.config")
        groupDefinitionsProvider.retrieveGroupsDefinitionsFromConfig()

        when:
        def groups = groupDefinitionsProvider.getGroups()

        then:
        groups.containsAll(groupDefinitions)
    }
}
