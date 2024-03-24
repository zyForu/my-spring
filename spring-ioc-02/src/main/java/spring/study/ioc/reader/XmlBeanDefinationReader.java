package spring.study.ioc.reader;

import org.dom4j.Document;
import spring.study.ioc.registry.BeanDefinationRegistry;
import spring.study.ioc.resource.ClassPathResource;
import spring.study.ioc.resource.Resource;
import spring.study.ioc.utils.DocumentUtils;

import java.io.InputStream;

/**
 * @author zy
 * @date 2024/3/24 15:33
 */
public class XmlBeanDefinationReader implements BeanDefinationReader {
    private BeanDefinationRegistry beanDefinationRegistry;

    public XmlBeanDefinationReader(BeanDefinationRegistry beanDefinationRegistry) {
        this.beanDefinationRegistry = beanDefinationRegistry;
    }

    @Override
    public void loadBeanDefination(String location) {
        ClassPathResource classPathResource = new ClassPathResource(location);
        loadBeanDefiantion(classPathResource);
    }

    @Override
    public void loadBeanDefiantion(Resource resource) {
        InputStream inputStream = resource.getInputStream();
        Document document = DocumentUtils.getDocument(inputStream);
        DefaultDocumentReader defaultDocumentReader = new DefaultDocumentReader(beanDefinationRegistry);
        defaultDocumentReader.loadBeanDefiantion(document.getRootElement());
    }
}
