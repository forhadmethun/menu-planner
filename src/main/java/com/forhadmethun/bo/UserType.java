package com.forhadmethun.bo;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum UserType {
    ACTIVE ("active"),
    SUPER_ACTIVE ("superactive"),
    BORED ("bored");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public boolean equalsName(String otherName) {
        return type.equals(otherName);
    }

    @Override
    public String toString() {
        return this.type;
    }

    public static boolean isValidUserType(final String userType) {
        return Arrays.stream(UserType.values())
            .map(UserType::toString)
            .collect(Collectors.toSet())
            .contains(userType);
    }

    public static UserType fromString(String text) {
        for (UserType b : UserType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
