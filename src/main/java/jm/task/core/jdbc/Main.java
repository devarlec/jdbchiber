package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User[] users = {
                new User("Polina", "Zvereva", (byte) 25),
                new User("Freddy", "Fazbear", (byte) 43),
                new User("Bonni", "the Bunny", (byte) 26),
                new User("Chica", "the Chicen", (byte) 43)
        };

        UserService connect = new UserServiceImpl();

        connect.dropUsersTable();
        connect.createUsersTable();

        for (User user : users) {
            connect.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        List<User> tableAllUsers = connect.getAllUsers();
        for (User user : tableAllUsers) {
            System.out.println(user);
        }

        connect.cleanUsersTable();
        connect.dropUsersTable();

    }
}
