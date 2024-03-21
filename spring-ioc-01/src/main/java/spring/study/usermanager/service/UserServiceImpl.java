package spring.study.usermanager.service;

import spring.study.usermanager.dao.UserDao;
import spring.study.usermanager.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/13 10:39
 */
public class UserServiceImpl implements UserSerivce{
    private UserDao userDao;


    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> queryUsers(Map<String, Object> parma) {
        return userDao.queryUsers(parma);
    }
}
