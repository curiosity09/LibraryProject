package main.by.library.services.impl;

import main.by.library.dao.UserDao;
import main.by.library.dao.impl.UserDaoImpl;
import main.by.library.entity.User;
import main.by.library.services.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(){
        userDao = UserDaoImpl.getInstance();
    }

    @Override
    public boolean addNewUser(User user) {
        return userDao.addNewUser(user);
    }

    @Override
    public int getCountUser() {
        return userDao.getCountUser();
    }

    @Override
    public List<User> findAllUsers(int offset) {
        return userDao.findAllUsers(offset);
    }

    @Override
    public boolean blockUser(User user) {
        return userDao.blockUser(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
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

    @Override
    public Optional<User> findUserById(int userId) {
        return userDao.findUserById(userId);
    }
}
