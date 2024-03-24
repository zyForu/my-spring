package spring.study;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import spring.study.ioc.factory.DefaultListableBeanFactory;
import spring.study.ioc.reader.XmlBeanDefinationReader;
import spring.study.po.User;
import spring.study.service.UserSerivce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Before
    // beanFactory initialize
    public void createBeanFactory() {
        defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinationReader xmlBeanDefinationReader = new XmlBeanDefinationReader(defaultListableBeanFactory);
        xmlBeanDefinationReader.loadBeanDefination("beans.xml");
    }

    @Test
    public void testIoc() {
        UserSerivce userService = (UserSerivce)defaultListableBeanFactory.getBean("userService");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "安妮");
        List<User> users = userService.queryUsers(paramMap);
        users.forEach(u -> {
            System.out.println(u.getUsername() + "," + u.getSex());
        });
    }
}
