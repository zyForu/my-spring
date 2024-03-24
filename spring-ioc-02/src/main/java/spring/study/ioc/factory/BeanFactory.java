package spring.study.ioc.factory;

/**
 * @author zy
 * @date 2024/3/24 14:09
 */
public interface BeanFactory {
    Object getBean(String name);
}
