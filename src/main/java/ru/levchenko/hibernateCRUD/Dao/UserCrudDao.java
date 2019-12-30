package ru.levchenko.hibernateCRUD.Dao;


import ru.levchenko.hibernateCRUD.models.User;
import ru.levchenko.hibernateCRUD.models.UserRole;
import ru.levchenko.hibernateCRUD.models.UserRolesENUM;

import java.util.List;

public interface UserCrudDao extends CrudDao<User> {
    List<UserRole> roles(int id);

    List<User> findUsersByRole(UserRolesENUM role);

}
