package org.miejski.service;

import org.miejski.domain.Group;
import org.miejski.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class GroupService {

    private Map<User, Group> assignedGroups = new HashMap<>();

    @Autowired
    private GroupAllocator groupAllocator;

    public Group getAssignedGroup(String userId) {
        User user = User.of(userId);
        Optional<Group> assignedGroup = ofNullable(assignedGroups.get(user));

        if (assignedGroup.isPresent()) {
            return assignedGroup.get();
        } else {
            Group group = Group.of(groupAllocator.assignGroup());
            assignedGroups.put(user, group);
            return group;
        }
    }
}
