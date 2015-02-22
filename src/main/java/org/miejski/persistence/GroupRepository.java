package org.miejski.persistence;

import org.miejski.domain.group.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GroupRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Optional<Group> getGroup(String userId) {
        if (stringRedisTemplate.hasKey(userId)) {
            return Optional.of(Group.of(retrieveGroupName(userId)));
        }
        return Optional.empty();
    }

    private String retrieveGroupName(String userId) {
        return stringRedisTemplate.boundValueOps(userId).get();
    }

    public void assignGroup(String userId, String groupName) {
        stringRedisTemplate.opsForValue().set(userId, groupName);
    }
}
