package main.by.library.dao;

import main.by.library.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    boolean blockUser(User user);

    Optional<User> findUserByUsername(String username);

    boolean isUserExist(String username);

    List<User> findAllDebtors(int limit, int offset);

}
