package ru.levchenko.hibernateCRUD;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.levchenko.hibernateCRUD.Dao.UserCrudDaoHibernateImpl;
import ru.levchenko.hibernateCRUD.models.User;
import ru.levchenko.hibernateCRUD.models.UserRolesENUM;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    static SessionFactory sessionFactory;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("Tamriel");
        user.setPassword("asdfasf3");

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            UserCrudDaoHibernateImpl crudDaoHQL = new UserCrudDaoHibernateImpl(sessionFactory);
            System.out.println(crudDaoHQL.roles(11));
            System.out.println("1");
            System.out.println(crudDaoHQL.findUsersByRole(UserRolesENUM.USER));
            System.out.println("2");
            System.out.println(crudDaoHQL.findAll());
            System.out.println("4");
            user = crudDaoHQL.find(5).get();
            user.setName("Rerere");
            user.setPassword("family");
            user.getRoles().remove(1);
            System.out.println("5");
            crudDaoHQL.update(user);


        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }


    }
}

