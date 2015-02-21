package org.miejski.controller

import org.miejski.domain.Group
import org.miejski.service.GroupService
import spock.lang.Specification

class GroupsControllerTest extends Specification {

    def GroupsController instance
    def GroupService groupService

    def static USER_ID = "userId"

    void setup() {
        groupService = Mock(GroupService)
        instance = new GroupsController(groupService: groupService)
    }

    def "should return group retrieved from groupService"() {
        given:
        def groupName = "groupName"

        when:
        def group = instance.getGroup(USER_ID)

        then:
        group.name() == groupName
        1 * groupService.getAssignedGroup(_ as String) >> Group.of(groupName)
    }
}
