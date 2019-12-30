package ru.levchenko.hibernateCRUD.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private UserRolesENUM role;


    public UserRole() {
    }
}





