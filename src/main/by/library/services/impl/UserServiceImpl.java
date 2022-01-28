package main.by.library.services.impl;

import main.by.library.dao.UserDao;
import main.by.library.dao.impl.UserDaoImpl;
import main.by.library.entity.User;
import main.by.library.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(){
        userDao = UserDaoImpl.getInstance();
    }

    @Override
    public boolean AddNewUser(User user) {
        return userDao.AddNewUser(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public boolean blockUser(User user) {
        return userDao.blockUser(user);
    }

    @Override
    public List<User> findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User checkAuthentication(String username) {
        return userDao.checkAuthentication(username);
    }

    @Override
    public boolean isUserExist(String value) {
        return userDao.isUserExist(value);
    }
}
