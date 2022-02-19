package main.by.library.services;

import main.by.library.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean addNewUser(User user);

    int getCountUser();

    List<User> findAllUsers(int limit, int offset);

    boolean blockUser(User user);

    Optional<User> findUserByUsername(String username);

    boolean isUserExist(String username);

    Optional<User> findUserById(int userId);

    boolean updateUserData(User user);

    List<User> findAllDebtors(int limit, int offset);

    boolean delete(User user);
}
