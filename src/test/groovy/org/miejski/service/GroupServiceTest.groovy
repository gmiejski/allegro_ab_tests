package org.miejski.service

import org.miejski.service.allocator.AccurateGroupAllocator
import spock.lang.Specification

class GroupServiceTest extends Specification {

    def GroupService instance
    def AccurateGroupAllocator groupAllocator

    def static USER_ID = "userName"

    void setup() {
        groupAllocator = Mock(AccurateGroupAllocator)
        instance = new GroupService(standardGroupAllocator: groupAllocator)
    }

    def "should call groupAllocator when looking up group for unassigned user"() {
        when:
        instance.getAssignedGroup(USER_ID)

        then:
        1 * groupAllocator.assignGroup()
    }

    def "should return same group once its assigned to user"() {
        given:
        groupAllocator.assignGroup() >>> ["group1", "group2"]
        def assignedGroup = instance.getAssignedGroup(USER_ID)

        when:
        def retrievedGroup = instance.getAssignedGroup(USER_ID)

        then:
        assignedGroup == retrievedGroup
    }
}
