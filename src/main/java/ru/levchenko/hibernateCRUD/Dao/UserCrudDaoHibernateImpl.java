package ru.levchenko.hibernateCRUD.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.levchenko.hibernateCRUD.models.User;
import ru.levchenko.hibernateCRUD.models.UserRole;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UserCrudDaoHibernateImpl implements UserCrudDao {

    private SessionFactory sessionFactory;

    private Session session;

    public UserCrudDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserRole> roles(long id) {
        List<UserRole> roleList;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery("from UserRole as ur where ur.user.id =:id", UserRole.class);
        query.setParameter("id", id);
        roleList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        session = null;

        return roleList;
    }


    @Override
    public List<User> findUsersByRole(String role) {
        List<User> userList;

        session = sessionFactory.openSession();
        session.getTransaction().begin();

        Query query = session.createQuery("select user from UserRole as ur where ur.userRoleKey.role =:role", User.class);
        query.setParameter("role", role);
        userList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        session = null;

        return userList;
    }

    @Override
    public Optional<User> find(long id) {
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

        Query query = session.createQuery(" from User ", User.class);

        userList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        session = null;
        return userList;
    }

    @Override
    public void delete(long id) {

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

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = cb.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);

        //Join<User, UserRole> userRoleJoin = userRoot.join("roles"); решить проблемы select n+1

        userCriteriaQuery.distinct(true);
        userCriteriaQuery.where(cb.equal(userRoot.get("id"), model.getId()));
        userCriteriaQuery.select(userRoot);

        List<UserRole> userRoles = session.createQuery(userCriteriaQuery)
                .getSingleResult().getRoles();

        UserRole[] userRolesArray = userRoles.toArray(new UserRole[userRoles.size()]);

        updateRoleByUser(model, userRolesArray, session);

        session.getTransaction().commit();
        session.close();
        session = null;
    }


    private void updateRoleByUser(User updateUser, UserRole[] oldUserRoles, Session session) {

        for (UserRole userRole : oldUserRoles) {
            if (!updateUser.getRoles().stream().anyMatch((a -> a.equals(userRole)))) {
                session.delete(userRole);
                session.flush();
            }
        }
        Iterator<UserRole> iteratorFromUpdateUser = updateUser.getRoles().iterator();
        while (iteratorFromUpdateUser.hasNext()) {
            UserRole userRole = iteratorFromUpdateUser.next();
            if (!Arrays.stream(oldUserRoles).anyMatch(a -> a.equals(userRole))) {
                session.saveOrUpdate(userRole);
                session.flush();
            }
        }
        session.update(updateUser);

    }
}




