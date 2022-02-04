package main.by.library.services;

import main.by.library.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean addNewUser(User user);

    int getCountUser();

    List<User> findAllUsers(int offset);

    boolean blockUser(User user);

    Optional<User> findUserByUsername(String username);

    User checkAuthentication(String username);

    boolean isUserExist(String value);

    Optional<User> findUserById(int userId);
}
