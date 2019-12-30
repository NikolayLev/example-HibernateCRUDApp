package ru.levchenko.hibernateCRUD.Dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(int id);

    List<T> findAll();

    void delete(int id);

    void save(T model);

    void update(T model);


}
