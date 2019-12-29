package ru.levchenko.hibernateCRUD.Dao;


import ru.levchenko.hibernateCRUD.models.User;
import ru.levchenko.hibernateCRUD.models.UserRole;

import java.util.List;

public interface UserCrudDao extends CrudDao<User> {
    List<UserRole> roles(long id);

    List<User> findUsersByRole(String role);

}
