package spring.study.ioc.registry;

import spring.study.ioc.model.BeanDefination;

/**
 * @author zy
 * @date 2024/3/24 13:21
 */
public interface BeanDefinationRegistry {
    BeanDefination getBeanDefination(String name);

    void registry(String name, BeanDefination bd);
}
