package org.miejski.persistence

import org.miejski.domain.group.Group
import org.springframework.data.redis.core.BoundValueOperations
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import spock.lang.Specification

class GroupRepositoryTest extends Specification {

    private static final String USER_ID = "userId"
    private static final String GROUP_NAME = "Group1"

    def GroupRepository instance

    def StringRedisTemplate stringRedisTemplate

    void setup() {
        stringRedisTemplate = Mock(StringRedisTemplate)
        instance = new GroupRepository(stringRedisTemplate: stringRedisTemplate)
    }

    def "should return no group if no group is assigned to userId"() {
        given:
        stringRedisTemplate.hasKey(USER_ID) >> false

        when:
        def Optional<Group> group = instance.getGroup(USER_ID)

        then:
        !group.isPresent()
    }

    def "should return group previously assigned"() {
        given:
        stringRedisTemplate.hasKey(USER_ID) >> true
        def BoundValueOperations boundValueOperations = Mock(BoundValueOperations)
        boundValueOperations.get() >> GROUP_NAME
        stringRedisTemplate.boundValueOps(USER_ID) >> boundValueOperations

        when:
        def Optional<Group> group = instance.getGroup(USER_ID)

        then:
        group.isPresent()
        group.get().name() == GROUP_NAME
    }

    def "should save given group assigned to user"() {
        given:
        def valueOperations = Mock(ValueOperations)
        stringRedisTemplate.opsForValue() >> valueOperations

        when:
        instance.assignGroup(USER_ID, GROUP_NAME)

        then:
        1 * valueOperations.set(USER_ID, GROUP_NAME)
    }
}
