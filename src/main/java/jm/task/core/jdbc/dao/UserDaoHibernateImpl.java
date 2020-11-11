package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
           try {
               transaction.commit();
           } catch (HibernateException e){
               transaction.rollback();
           }
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            try {
                transaction.commit();
            }catch (HibernateException e){
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName,age);
            session.save(user);
            try {
                transaction.commit();
            }catch (HibernateException e){
                transaction.rollback();
            }
        }
    }
    /*
    public void saveUser(User user){ //перегруженный saveUser пишущий готового юзера, проще тестить.
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(user);
        }
    }
     */

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :userId").setParameter("userId" , id).executeUpdate();
            try {
                transaction.commit();
            }catch (HibernateException e){
                transaction.rollback();
            }
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
            Transaction transaction = session.beginTransaction();
            List from_user = session.createQuery("from User").getResultList();
            try {
                transaction.commit();
            }catch (HibernateException e){
                transaction.rollback();
            }
            return from_user;
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            try {
                transaction.commit();
            }catch (HibernateException e){
                transaction.rollback();
            }
        }
    }
}
