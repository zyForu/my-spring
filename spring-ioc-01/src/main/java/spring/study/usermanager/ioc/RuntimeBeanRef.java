package spring.study.usermanager.ioc;

/**
 * @author zy
 * @date 2024/3/21 19:45
 */
public class RuntimeBeanRef {
    private String refName;


    public RuntimeBeanRef(String refName) {
        this.refName = refName;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }
}
