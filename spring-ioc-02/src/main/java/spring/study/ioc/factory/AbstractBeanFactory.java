package spring.study.ioc.factory;

import spring.study.ioc.model.BeanDefination;
import spring.study.ioc.registry.DefaultSingletonRegistry;

/**
 * @author zy
 * @date 2024/3/24 14:11
 */
public abstract class AbstractBeanFactory extends DefaultSingletonRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) {
        Object singleton = getSingleton(name);
        if(singleton != null) {
            return singleton;
        }

        BeanDefination bd = getBeanDefination(name);
        if(bd == null) {
            return null;
        }

        if(bd.isSingleton()) {
            singleton = createBean(bd);
            addSingleton(name, singleton);
        }else {
            singleton = createBean(bd);
        }
        return singleton;

    }

    public abstract BeanDefination getBeanDefination(String name);


    public abstract Object createBean(BeanDefination bd);
}
