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
    private static final String SCOPE_SINGLETON = "singleton";
    private static final String SCOPE_PROTOTYPE = "prototype";

    public BeanDefination(String beanName, String className, String initMethod, String scope) {
        this.beanName = beanName;
        this.className = className;
        this.initMethod = initMethod;
        this.scope = scope;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isSigleton() {
        if(SCOPE_PROTOTYPE.equals(scope)) {
            return false;
        }else {
            return true;
        }
    }


    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public void setPropertyValueList(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }
}
