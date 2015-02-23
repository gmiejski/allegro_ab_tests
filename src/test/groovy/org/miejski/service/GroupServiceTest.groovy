package org.miejski.service

import org.miejski.domain.group.Group
import org.miejski.persistence.GroupRepository
import org.miejski.service.group.allocator.AccurateGroupAllocator
import spock.lang.Specification

class GroupServiceTest extends Specification {

    def GroupService instance
    def AccurateGroupAllocator groupAllocator
    def GroupRepository groupsRepository

    def static USER_ID = "userName"

    void setup() {
        groupAllocator = Mock(AccurateGroupAllocator)
        groupsRepository = Mock(GroupRepository)
        instance = new GroupService(standardGroupAllocator: groupAllocator, groupRepository: groupsRepository)
    }

    def "should call groupAllocator when looking up group for unassigned user"() {
        given:
        groupsRepository.getGroup(USER_ID) >> Optional.empty()

        when:
        instance.getAssignedGroup(USER_ID)

        then:
        1 * groupAllocator.assignGroup()
    }

    def "should return same group once its assigned to user"() {
        given:
        def assignedGroupName = "group1"
        groupAllocator.assignGroup() >>> [assignedGroupName, "group2"]
        groupsRepository.getGroup(USER_ID) >>> [Optional.empty(), Optional.of(Group.of(assignedGroupName))]
        def assignedGroup = instance.getAssignedGroup(USER_ID)

        when:
        def retrievedGroup = instance.getAssignedGroup(USER_ID)

        then:
        assignedGroup == retrievedGroup
    }
}
