package spring.study.usermanager.ioc;

/**
 * @author zy
 * @date 2024/3/21 19:41
 */
public class PropertyValue {
    private String name;
    private Object value;


    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
