package spring.study.ioc.reader;

import org.dom4j.Element;
import spring.study.ioc.registry.BeanDefinationRegistry;

import java.util.List;

/**
 * @author zy
 * @date 2024/3/24 15:37
 */
public class DefaultDocumentReader implements BeanDefinationDocumentReader {
    private BeanDefinationRegistry registry;

    public DefaultDocumentReader(BeanDefinationRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void loadBeanDefiantion(Element rootElement) {
        List<Element> elements = rootElement.elements();
        BeanDefinationParseDelegate beanDefinationParseDelegate = new BeanDefinationParseDelegate(registry);
        for(Element element : elements) {
            beanDefinationParseDelegate.parseElement(element);
        }
    }
}
