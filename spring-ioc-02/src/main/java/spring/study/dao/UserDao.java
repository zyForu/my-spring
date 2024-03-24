package spring.study.dao;


import spring.study.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/13 10:38
 */
public interface UserDao {
    List<User> queryUsers(Map<String, Object> param);
}
