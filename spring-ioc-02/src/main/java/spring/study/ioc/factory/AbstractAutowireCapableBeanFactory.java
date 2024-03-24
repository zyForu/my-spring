package spring.study.ioc.factory;

import com.mysql.cj.util.StringUtils;
import spring.study.ioc.model.BeanDefination;
import spring.study.ioc.model.PropertyValue;
import spring.study.ioc.resolver.BeanDefinatioValueResolver;
import spring.study.ioc.utils.ReflectUtils;

import java.util.List;

/**
 * @author zy
 * @date 2024/3/24 14:24
 * 专门处理bean的加载
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    public BeanDefination getBeanDefination(String name) {
        return null;
    }

    @Override
    public Object createBean(BeanDefination bd) {
        /**
         * 1.创建bean
         * 2.依赖注入
         * 3.调用bean的初始化方法
         */
        Object bean = createBeanInstance(bd);
        popularBean(bean, bd);
        initializeBean(bean, bd);
        return bean;
    }

    private void initializeBean(Object bean, BeanDefination bd) {
        String initMethod = bd.getInitMethod();
        if(!StringUtils.isNullOrEmpty(initMethod)) {
            ReflectUtils.invokeMd(bean, initMethod);
        }
    }

    private void popularBean(Object bean, BeanDefination bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        BeanDefinatioValueResolver valueResolver = new BeanDefinatioValueResolver(this);

        for(PropertyValue pv: propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();
            Object obj = valueResolver.resolveValue(value);
            ReflectUtils.assignProperty(bean, name, obj);
        }
    }

    private Object createBeanInstance(BeanDefination bd) {
        return ReflectUtils.getInstance(bd.getClazzType());
    }
}
