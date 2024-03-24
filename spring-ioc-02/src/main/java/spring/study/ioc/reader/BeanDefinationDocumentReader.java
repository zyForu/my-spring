package spring.study.ioc.reader;

import org.dom4j.Element;

/**
 * @author zy
 * @date 2024/3/24 15:32
 */
public interface BeanDefinationDocumentReader {
    void loadBeanDefiantion(Element rootElement);
}
