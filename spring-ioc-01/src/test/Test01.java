import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import spring.study.usermanager.dao.UserDaoImpl;
import spring.study.usermanager.ioc.BeanDefination;
import spring.study.usermanager.po.User;
import spring.study.usermanager.service.UserSerivce;
import spring.study.usermanager.service.UserServiceImpl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/21 15:52
 */
public class Test01 {

    private Map<String, BeanDefination> beanDefinationMap = new HashMap<>();
    private Map<String, Class<?>> sigletons = new HashMap<>();

    @Before
    public void before() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("resources/beans.xml");


    }

    @Test
    public void testDb() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        // 入参
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("name", "压缩");

        // 数据源信息
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setDataSource(dataSource);
        UserSerivce userSerivce = new UserServiceImpl(userDao);

        // 调用服务
        List<User> users = userSerivce.queryUsers(paraMap);
        users.forEach(e ->
        {
            System.out.print("id:" + e.getId() + ",名字:" + e.getUsername() + ",性别:" + e.getSex() + ",生日:" + e.getBrithDate());
            System.out.println();
        });
    }

    @Test
    public void testDb2() {

    }

}
