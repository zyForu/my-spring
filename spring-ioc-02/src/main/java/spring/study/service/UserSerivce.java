package spring.study.service;

import spring.study.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/13 10:39
 */
public interface UserSerivce {
    List<User> queryUsers(Map<String, Object> parma);
}
