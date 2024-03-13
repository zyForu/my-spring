package spring.study.usermanager.service;

import spring.study.usermanager.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/13 10:39
 */
public interface UserSerivce {
    List<User> queryUsers(Map<String, Object> parma);
}
