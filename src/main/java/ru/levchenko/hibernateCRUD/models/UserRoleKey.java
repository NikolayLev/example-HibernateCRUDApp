package ru.levchenko.hibernateCRUD.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRoleKey implements Serializable {
    static final long serialVersionUID = 1L;

    private String role;
    private long user_id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public UserRoleKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleKey that = (UserRoleKey) o;

        if (user_id != that.user_id) return false;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (int) (user_id ^ (user_id >>> 32));
        return result;
    }

    public String toString() {
        return role + " " + user_id;
    }
}
