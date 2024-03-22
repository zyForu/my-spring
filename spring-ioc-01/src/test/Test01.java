import com.mysql.cj.util.StringUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import spring.study.usermanager.dao.UserDaoImpl;
import spring.study.usermanager.ioc.BeanDefination;
import spring.study.usermanager.ioc.PropertyValue;
import spring.study.usermanager.ioc.RuntimeBeanRef;
import spring.study.usermanager.ioc.StringTypedValue;
import spring.study.usermanager.po.User;
import spring.study.usermanager.service.UserSerivce;
import spring.study.usermanager.service.UserServiceImpl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/21 15:52
 */
public class Test01 {

    private Map<String, BeanDefination> beanDefinationMap = new HashMap<>();
    private Map<String, Object> sigletons = new HashMap<>();

    @Before
    public void before() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("beans.xml");
        Document document = getDocument(inputStream);
        loadBeanDefinations(document.getRootElement());
    }


    // 非ioc的模式：无状态的业务类需要程序员每次去重复创建
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


    // 将一些无状态的业务处理类统一放到内存中，按需获取即可
    @Test
    public void testDb2() {
        // 入参
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("name", "安妮");
        // 获取服务处理类
        UserSerivce userSerivce = (UserSerivce) getBean("userService");
        // 调用服务
        List<User> users = userSerivce.queryUsers(paraMap);
        users.forEach(e ->
        {
            System.out.print("id:" + e.getId() + ",名字:" + e.getUsername() + ",性别:" + e.getSex() + ",生日:" + e.getBrithDate());
            System.out.println();
        });
    }




    public Document getDocument(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(inputStream);
        } catch (DocumentException e) {
            System.out.println("xml读取异常");
        }
        return null;
    }

    public void loadBeanDefinations(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if("bean".equals(element.getName()))  {
                parseDeaultBeanElement(element);
            }else {
                parseCustonElement(element);
            }
        }
    }

    private void parseCustonElement(Element element) {
    }

    private void parseDeaultBeanElement(Element element) {
        String id = element.attributeValue("id");
        String classNm = element.attributeValue("class");
        String initMethod = element.attributeValue("init-method");
        String scope = element.attributeValue("scope");
        BeanDefination bd = new BeanDefination(id, classNm, initMethod, scope);
        bd.setClazz(resolveClass(classNm));

        //处理property

        List<Element> elements = element.elements();
        for (Element ele: elements) {
            bd.addPropertyValue(resolveProperValue(bd, ele));
        }
        beanDefinationMap.put(id, bd);
    }

    private PropertyValue resolveProperValue(BeanDefination bd, Element ele) {
        String name = ele.attributeValue("name");
        String value = ele.attributeValue("value");
        String ref = ele.attributeValue("ref");
        if(value == null && ref == null) {
            return null;
        }
        PropertyValue pv = null;
        if(value != null) {
            Class clazz = resolveTargetClass(bd.getClazz(), name);
            pv = new PropertyValue(name, new StringTypedValue(clazz, value));
        }else {
            pv = new PropertyValue(name, new RuntimeBeanRef(ref));
        }
        return pv;
    }

    private Class resolveTargetClass(Class<?> clazz, String name) {
        /**
         * 解析类中相应属性name的类型
          */
        try {
            Field field = clazz.getDeclaredField(name);
            Class<?> type = field.getType();
            return type;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<?> resolveClass(String classNm) {
        try {
            Class<?> aClass = Class.forName(classNm);
            return aClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Object getBean(String beanId) {
        BeanDefination bd = beanDefinationMap.get(beanId);
        if(bd == null) {
            throw new RuntimeException("没有该Bean定义");
        }

        Object obj = null;
        if(bd.isSigleton()) {
            // 从单例池获取
            obj = sigletons.get(beanId);
            if(obj == null) {
                // 没有则创建
                obj = createBean(bd);
                // 放入单例池
                sigletons.put(beanId, obj);
            }

        }else {
           obj = createBean(bd);
        }
        return obj;
    }

    private Object createBean(BeanDefination bd) {
        /**
         * 1. 获取实例
         * 2. 属性注入
         * 3. 初始化方法执行
         */
        Object obj = getBeanInstance(bd);

        populateBeanProperty(obj,bd);

        initalizeBean(obj,bd);
        return obj;
    }

    private void initalizeBean(Object bean, BeanDefination bd) {
        String initMethod = bd.getInitMethod();
        if(!StringUtils.isNullOrEmpty(initMethod)) {
            Class<?> clazz = bd.getClazz();
            try {
                clazz.getMethod(initMethod).invoke(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateBeanProperty(Object bean, BeanDefination bd) {
        /**
         * 1.属性值生成
         * 2.属性赋值
         */

        List<PropertyValue> propertyValueList = bd.getPropertyValueList();
        for (PropertyValue pv: propertyValueList) {
            Object obj = null;
            if(pv.getValue() instanceof StringTypedValue) {
                obj = resolveBasicType((StringTypedValue)pv.getValue());
            }else {
                obj = resolveRefrenceType((RuntimeBeanRef) pv.getValue());
            }
            propertyAssign(bean, pv, obj);
        }
    }


    private void propertyAssign(Object bean, PropertyValue pv, Object obj) {
        Class<?> aClass = bean.getClass();
        try {
            Field field = aClass.getDeclaredField(pv.getName());
            field.setAccessible(true);
            field.set(bean, obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object resolveRefrenceType(RuntimeBeanRef value) {
        return getBean(value.getRefName());
    }

    private Object resolveBasicType(StringTypedValue stringTypedValue) {
        Class<?> targetType = stringTypedValue.getTargetType();
        if(Integer.class == targetType) {
            return Integer.parseInt(stringTypedValue.getValue());
        }else if(String.class == targetType) {
            return stringTypedValue.getValue();
        }else if(Boolean.class == targetType) {
            return Boolean.parseBoolean(stringTypedValue.getValue());
        }
        // ....
        return null;

    }

    private Object getBeanInstance(BeanDefination bd) {
        // 静态工厂创建bean
        // 动态工厂创建bean
        Class<?> clazz = bd.getClazz();
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
