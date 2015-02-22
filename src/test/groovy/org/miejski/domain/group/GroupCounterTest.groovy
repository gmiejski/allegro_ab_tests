package org.miejski.domain.group

import spock.lang.Specification

class GroupCounterTest extends Specification {

    def GroupCounter instance

    def final static int WEIGHT = 2

    void setup() {
        instance = new GroupCounter(GroupDefinition.of("groupName", WEIGHT))
    }

    def "should throw exception when trying to retrieve group and cannot do so"() {
        given:
        (1..WEIGHT).each { instance.decrease() }

        when:
        instance.decrease()

        then:
        thrown(IllegalStateException)
        !instance.canAssignMore()
    }

    def "should not be able to assign more than weight without reset"() {
        given:
        (1..WEIGHT).each { instance.decrease() }

        expect:
        !instance.canAssignMore()
    }

    def "should be able to assign more to this group after reset"() {
        given:
        (1..WEIGHT).each { instance.decrease() }

        when:
        instance.reset()

        then:
        instance.canAssignMore()
    }
}
