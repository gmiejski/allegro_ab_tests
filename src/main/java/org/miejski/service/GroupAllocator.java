package org.miejski.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GroupAllocator {

    public String assignGroup() {
        return String.valueOf(new Random().nextInt());
    }
}
