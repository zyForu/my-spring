package spring.study.ioc.factory;

import spring.study.ioc.model.BeanDefination;
import spring.study.ioc.registry.BeanDefinationRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/24 15:07
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinationRegistry {
    private Map<String, BeanDefination> beanDefinationMap = new HashMap<>();


    @Override
    public BeanDefination getBeanDefination(String name) {
        return beanDefinationMap.get(name);
    }

    @Override
    public void registry(String name, BeanDefination bd) {
        beanDefinationMap.put(name, bd);
    }
}
