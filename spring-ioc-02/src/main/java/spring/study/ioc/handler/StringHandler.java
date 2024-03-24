package spring.study.ioc.handler;

/**
 * @author zy
 * @date 2024/3/24 14:46
 */
public class StringHandler implements TypeHandler {
    @Override
    public boolean supports(Class type) {
        return type == String.class;
    }

    @Override
    public Object handle(String value) {
        return value;
    }
}
