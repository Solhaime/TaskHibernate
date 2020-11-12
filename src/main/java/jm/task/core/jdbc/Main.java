package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    static User nikita = new User("Nikita" , "Safronov" , (byte) 25);
    static User aleksey = new User("Aleksey" , "Ktotakoy" , (byte) 18);
    static User andrey = new User("Andrey" , "Fantasia" , (byte) 40);
    static User votTakVot = new User("Aleksandr" , "KuricinAkaNevsky" , (byte) 99);

    public static void main( String[] args ) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser(nikita.getName() , nikita.getLastName() , nikita.getAge());
        userService.saveUser(aleksey.getName() , aleksey.getLastName() , aleksey.getAge());
        userService.saveUser(andrey.getName() , andrey.getLastName() , andrey.getAge());
        userService.saveUser(votTakVot.getName() , votTakVot.getLastName() , votTakVot.getAge());


        /*Вывод в консоль расположен вконце блока try внутри метода getAllUsers(),
        так же устроен и метод saveUser(). таким образом можно еще и узнать,
        не перешло ли управление блоку catch.
        Я перенесу его, если он должен быть именно в main().
         */

        List list = userService.getAllUsers();
        //userService.removeUserById(4); // При желание можно проверить работоспособность.
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }

}

