package ru.levchenko.hibernateCRUD.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users_roles")
public class UserRole {

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private User user;

    public UserRole(User user, UserRolesENUM userRolesENUM) {
        this.user = user;
        userRoleKey = new UserRoleKey();
        userRoleKey.setUser_id(user.getId());
        userRoleKey.setRole(userRolesENUM.name());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @EmbeddedId
    private UserRoleKey userRoleKey;

    public String getRole() {
        return userRoleKey.getRole();
    }

    public void setRole(UserRolesENUM userRole) {
        userRoleKey.setRole(userRole.name());
    }

    @Override
    public String toString() {
        return getRole() + " " + userRoleKey.getUser_id();
    }

    public UserRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return Objects.equals(userRoleKey, userRole.userRoleKey);
    }

    @Override
    public int hashCode() {
        return userRoleKey != null ? userRoleKey.hashCode() : 0;
    }
}


