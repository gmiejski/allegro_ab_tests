package org.miejski.domain;

import java.util.Objects;

public class User {

    private String id;

    public static User of(String userId) {
        return new User(userId);
    }

    private User(String userId) {
        this.id = userId;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(this.id(), user.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id());
    }
}
