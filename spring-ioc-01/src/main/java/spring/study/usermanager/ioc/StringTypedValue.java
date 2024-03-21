package spring.study.usermanager.ioc;

/**
 * @author zy
 * @date 2024/3/21 19:47
 */
public class StringTypedValue {
    private Class<?> targetType;
    private String  value;

    public Class<?> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
