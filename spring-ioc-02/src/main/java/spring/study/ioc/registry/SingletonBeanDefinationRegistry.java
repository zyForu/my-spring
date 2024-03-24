package spring.study.ioc.registry;

import spring.study.ioc.model.BeanDefination;

/**
 * @author zy
 * @date 2024/3/24 13:24
 */
public interface SingletonBeanDefinationRegistry {
    Object getSingleton(String name);

    void addSingleton(String name, Object object);
}
