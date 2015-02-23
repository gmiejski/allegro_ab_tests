package org.miejski.service;

import org.miejski.domain.group.Group;
import org.miejski.persistence.GroupRepository;
import org.miejski.service.group.allocator.GroupAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Resource(name = "standardGroupAllocator")
    private GroupAllocator standardGroupAllocator;

    public Group getAssignedGroup(String userId) {
        Optional<Group> assignedGroup = groupRepository.getGroup(userId);

        return assignedGroup.orElseGet(() -> {
            Group group = Group.of(standardGroupAllocator.assignGroup());
            groupRepository.assignGroup(userId, group.name());
            return group;
        });
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
