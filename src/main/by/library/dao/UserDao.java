package main.by.library.dao;

import main.by.library.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    boolean addNewUser(User user);

    int getCountUser();

    List<User> findAllUsers(int offset);

    boolean blockUser(User user);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(int userId);

    boolean isUserExist(String value);

    User checkAuthentication(String username);

    boolean updateUserData(User user);
}
