package main.by.library.services.impl;

import main.by.library.dao.UserDao;
import main.by.library.dao.impl.UserDaoImpl;
import main.by.library.entity.User;
import main.by.library.services.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
    }

    @Override
    public boolean addNewUser(User user) {
        return userDao.addNew(user);
    }

    @Override
    public int getCountUser() {
        return userDao.getCount();
    }

    @Override
    public List<User> findAllUsers(int limit, int offset) {
        return userDao.findAll(limit, offset);
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
    public boolean isUserExist(String username) {
        return userDao.isUserExist(username);
    }

    @Override
    public Optional<User> findUserById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public boolean updateUserData(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> findAllDebtors(int limit, int offset) {
        return userDao.findAllDebtors(limit, offset);
    }

    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }
}
