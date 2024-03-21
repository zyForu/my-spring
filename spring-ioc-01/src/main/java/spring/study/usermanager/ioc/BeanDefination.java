package spring.study.usermanager.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zy
 * @date 2024/3/21 19:34
 */
public class BeanDefination {
    private String beanName;
    private String className;
    private Class<?> clazz;
    private String initMethod;
    private String scope;

    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public BeanDefination(String beanName, String className, String initMethod, String scope) {
        this.beanName = beanName;
        this.className = className;
        this.initMethod = initMethod;
        this.scope = scope;
    }
}
