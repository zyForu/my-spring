package spring.study.ioc.registry;

/**
 * @author zy
 * @date 2024/3/24 14:01
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 专门处理单例对象的注册与获取服务
 * 1.获取时存在线程安全的问题
 * 2.注册时存在循环依赖的问题
 */
public class DefaultSingletonRegistry implements SingletonBeanDefinationRegistry{

    private Map<String, Object>  sigletonMap = new HashMap<>();

    @Override
    public Object getSingleton(String name) {
        return this.sigletonMap.get(name);
    }

    @Override
    public void addSingleton(String name, Object object) {
        this.sigletonMap.put(name, object);
    }
}
