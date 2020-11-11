package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/firsttask?verifyServerCertificate=false&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "jdbcsxqd17823";
    private static Connection connection;
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
        try {


            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();


            Map<String, String> settings = new HashMap<>();
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/firsttask?verifyServerCertificate=false&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=GMT");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "jdbcsxqd17823");
            settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
            settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            //settings.put(Environment.HBM2DDL_AUTO, "update");

            registryBuilder.applySettings(settings);

            registry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);

            sessionFactory = sources.buildMetadata().buildSessionFactory();


        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }
      return sessionFactory;
    }

    /*
    public static void close(){
        getSessionFactory().close();
    }
     */

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL , USERNAME , PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public Util() {

    }

/*
    public void close() {
        try {
            if(!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
     */
}

