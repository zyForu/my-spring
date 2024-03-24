package spring.study.ioc.reader;

import com.mysql.cj.util.StringUtils;
import org.dom4j.Element;
import spring.study.ioc.model.BeanDefination;
import spring.study.ioc.model.PropertyValue;
import spring.study.ioc.model.RuntimeBeanReference;
import spring.study.ioc.model.TypedStringValue;
import spring.study.ioc.registry.BeanDefinationRegistry;
import spring.study.ioc.utils.ReflectUtils;

import java.util.List;

/**
 * @author zy
 * @date 2024/3/24 15:43
 */
public class BeanDefinationParseDelegate {

    private BeanDefinationRegistry registry;

    public BeanDefinationParseDelegate(BeanDefinationRegistry registry) {
        this.registry = registry;
    }

    public void parseElement(Element element) {
        if("bean".equals(element.getName())) {
            parseBeanDefination(element);
        }else {
            parseCustomElement(element);
        }
    }

    private void parseCustomElement(Element element) {

    }

    private void parseBeanDefination(Element element) {
        String id = element.attributeValue("id");
        String classNm = element.attributeValue("class");
        String initMdNm = element.attributeValue("init-method");
        String scope = element.attributeValue("scope");
        if(StringUtils.isNullOrEmpty(classNm)) {
            return;
        }
        Class<?> aClass = ReflectUtils.getClass(classNm);
        if(aClass == null) {
            return;
        }
        if(StringUtils.isNullOrEmpty(id)) {
            String simpleName = aClass.getSimpleName();
            id = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1);
        }
        BeanDefination bd = new BeanDefination();
        bd.setBeanName(id);
        bd.setClazzName(classNm);
        bd.setClazzType(aClass);
        bd.setInitMethod(initMdNm);
        bd.setScope(scope);

        // 处理<property>标签
        List<Element> elements = element.elements();
        for(Element ele : elements) {
            parsePropertyElement(bd, ele);
        }
        registry.registry(id, bd);
    }

    private void parsePropertyElement(BeanDefination bd, Element ele) {
        String name = ele.attributeValue("name");
        String value = ele.attributeValue("value");
        String ref = ele.attributeValue("ref");

        if(StringUtils.isNullOrEmpty(name) || (StringUtils.isNullOrEmpty(value)
            && StringUtils.isNullOrEmpty(ref))) {
            return;
        }

        if(StringUtils.isNullOrEmpty(value)) {
            RuntimeBeanReference beanReference = new RuntimeBeanReference(ref);
            bd.addPropertyValue(new PropertyValue(name, beanReference));
        }else{
            TypedStringValue stringValue = new TypedStringValue(value);
            stringValue.setTargetType(ReflectUtils.getType(bd.getClazzType(), name));
            bd.addPropertyValue(new PropertyValue(name, stringValue));
        }
    }

}
