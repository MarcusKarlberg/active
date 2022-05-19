package se.mk.active.sampledata;

import se.mk.active.model.Role;
import se.mk.active.model.User;

public final class UserData {
    private UserData() {}

    public static final String USERNAME = "username@hotmail.com";
    public static final String PASSWORD = "username@hotmail.com";
    public static final Long ID = 1L;
    public static final Role ROLE = Role.ROLE_USER;

    public static User createUser() {
        return new User(ID, USERNAME, PASSWORD, ROLE, null);
    }
}
