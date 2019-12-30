package ru.levchenko.hibernateCRUD.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.levchenko.hibernateCRUD.models.User;
import ru.levchenko.hibernateCRUD.models.UserRole;
import ru.levchenko.hibernateCRUD.models.UserRolesENUM;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class UserCrudDaoHibernateImpl implements UserCrudDao {

    private SessionFactory sessionFactory;

    private Session session;

    public UserCrudDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserRole> roles(int id) {
        List<UserRole> roleList;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        roleList = session.createQuery(" from User where id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult()
                .getRoles();

        session.getTransaction().commit();
        session.close();
        session = null;

        return roleList;
    }


    @Override
    public List<User> findUsersByRole(UserRolesENUM role) {
        List<User> userList;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery("select distinct u.users FROM UserRole u JOIN u.users us WHERE u.role= :role");
        query.setParameter("role", role);
        userList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        session = null;

        return userList;
    }

    @Override
    public Optional<User> find(int id) {
        Optional<User> candidate;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery("from User as u where u.id = :id", User.class);
        query.setParameter("id", id);
        candidate = Optional.of((User) query.getSingleResult());
        session.getTransaction().commit();

        session.close();
        session = null;

        return candidate;
    }

    @Override
    public List<User> findAll() {
        List<User> userList;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery(" from User", User.class);

        userList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        session = null;
        return userList;
    }

    @Override
    public void delete(int id) {

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery("delete from User where id=:id", User.class);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        session = null;

    }

    @Override
    public void save(User model) {
        session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.saveOrUpdate(model);
        session.getTransaction().commit();
        session.close();
        session = null;
    }

    //CriteriaQuery
    @Override
    public void update(User model) {
        session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.update(model);
        session.getTransaction().commit();
        session.close();
        session = null;
    }


}




