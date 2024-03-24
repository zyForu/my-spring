package spring.study.ioc.reader;

import spring.study.ioc.resource.Resource;

/**
 * @author zy
 * @date 2024/3/24 15:13
 */
public interface BeanDefinationReader {
    void loadBeanDefination(String location);
    void loadBeanDefiantion(Resource resource);
}
