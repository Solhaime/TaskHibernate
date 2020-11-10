package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory= Util.getSessionFactory();

    private final String CREATE = "create table if not exists users (id int auto_increment,name varchar(45) not null,  " +
            "lastName varchar(45) not null,age int null,constraint users_pk primary key (id))";
    private final String DROP = "drop table if exists users";
    private final String REMOVE = "delete from users where id = ?";


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = new User(name, lastName,age);
            session.save(user);
        }
    }
    /*
    public void saveUser(User user){ //перегруженный saveUser пишущий готового юзера, проще было тестить.
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(user);
        }
    }
     */

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :userId").setParameter("userId" , id).executeUpdate();
        }
    }
    /* SQL вариант который я заменил на HQL
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(REMOVE);
            query.setParameter(1 , id).executeUpdate();
        }
    }
     */

    @Override
    public List<User> getAllUsers() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createQuery("from User").getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
        }
    }
}
