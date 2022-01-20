package main.by.library.services;

import main.by.library.entity.User;

import java.util.List;

public interface UserService {

    boolean AddNewUser(User user);

    List<User> findAllUsers();

    boolean blockUser(User user);

    List<User> findUserByUsername(String username);

}
