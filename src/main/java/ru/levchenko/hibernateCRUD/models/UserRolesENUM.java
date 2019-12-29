package ru.levchenko.hibernateCRUD.models;

import java.util.HashMap;
import java.util.Map;

public enum UserRolesENUM {
    USER, ADMIN, NEWS_MAKER, MODERATOR, BATMAN;
    private long id;
    private static final Map<String, UserRolesENUM> lookup = new HashMap<>();

    static {
        for (UserRolesENUM roles : UserRolesENUM.values()) {
            lookup.put(roles.name(), roles);
        }
    }

    public static UserRolesENUM getRole(String role) {
        return lookup.get(role);
    }


}
