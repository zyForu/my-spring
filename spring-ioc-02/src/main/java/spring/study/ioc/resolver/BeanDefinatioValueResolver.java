package spring.study.ioc.resolver;

import spring.study.ioc.factory.BeanFactory;
import spring.study.ioc.handler.IntegerHandler;
import spring.study.ioc.handler.StringHandler;
import spring.study.ioc.handler.TypeHandler;
import spring.study.ioc.model.RuntimeBeanReference;
import spring.study.ioc.model.TypedStringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zy
 * @date 2024/3/24 14:43
 */
public class BeanDefinatioValueResolver {
    private BeanFactory beanFactory;
    private List<TypeHandler> typeHandlerList;

    public BeanDefinatioValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        initializeTypeHandlers();
    }

    private void initializeTypeHandlers() {
        this.typeHandlerList = new ArrayList<>();
        this.typeHandlerList.add(new StringHandler());
        this.typeHandlerList.add(new IntegerHandler());
    }

    public Object resolveValue(Object value) {
        if(value instanceof RuntimeBeanReference) {
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String beanName = beanReference.getRef();
            return beanFactory.getBean(beanName);
        }else {
            TypedStringValue stringValue = (TypedStringValue) value;
            Class<?> targetType = stringValue.getTargetType();
            for(TypeHandler typeHandler: typeHandlerList) {
                if(typeHandler.supports(targetType)) {
                    return  typeHandler.handle(stringValue.getValue());
                }
            }
        }
        return null;
    }
}
