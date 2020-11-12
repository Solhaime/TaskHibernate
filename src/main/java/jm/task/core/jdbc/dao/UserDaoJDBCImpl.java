package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private final String CREATE = "create table if not exists users (id int auto_increment,name varchar(45) not null,  " +
            "lastName varchar(45) not null,age int null,constraint users_pk primary key (id))";
    private final String DROP = "drop table if exists users";
    private final String INSERT = "insert into users (name, lastName, age) values (?, ?, ?)";
    private final String REMOVE = "delete from users where id = ?";
    private final String GETALL = "select * from users";
    private final String CLEAR = "delete from users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.execute(CREATE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.execute(DROP);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try( PreparedStatement preparedStatement = connection.prepareStatement(INSERT)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            connection.setAutoCommit(false);
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> usersTable = new ArrayList();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(GETALL);
            while(resultSet.next()){
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                usersTable.add(user);
            }
            System.out.println(usersTable.toString().replaceAll("[\\[\\]]",""));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersTable;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.execute(CLEAR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
