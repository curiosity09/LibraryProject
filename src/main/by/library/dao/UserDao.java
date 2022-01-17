package main.by.library.dao;

import main.by.library.entity.User;

import java.util.List;

public interface UserDao {

    boolean AddNewUser(User user);

    List<User> findAllUsers();

    boolean blockUser(User user);

    List<User> findUserByUsername(String username);
}
