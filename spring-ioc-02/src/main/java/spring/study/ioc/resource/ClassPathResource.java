package spring.study.ioc.resource;

import java.io.InputStream;

/**
 * @author zy
 * @date 2024/3/24 12:09
 */
public class ClassPathResource implements Resource{

    private String location;

    public ClassPathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getInputStream() {
        if(location.startsWith("classpath:")) {
            location = location.substring(10);
        }

        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
