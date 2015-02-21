package org.miejski.service

import spock.lang.Specification

class GroupServiceTest extends Specification {

    def GroupService instance
    def GroupAllocator groupAllocator

    def static USER_ID = "userName"

    void setup() {
        groupAllocator = Mock(GroupAllocator)
        instance = new GroupService(groupAllocator: groupAllocator)
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
