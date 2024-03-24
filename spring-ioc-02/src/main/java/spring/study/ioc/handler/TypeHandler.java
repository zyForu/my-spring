package spring.study.ioc.handler;

/**
 * @author zy
 * @date 2024/3/24 14:44
 */
public interface TypeHandler {
    boolean supports(Class type);
    Object handle(String value);
}
