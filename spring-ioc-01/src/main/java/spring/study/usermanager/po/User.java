package spring.study.usermanager.po;

import lombok.Data;

import java.util.Date;

/**
 * @author zy
 * @date 2024/3/13 10:39
 */
@Data
public class User {
    private int id;
    private String username;
    private Date brithDate;
    private String addr;
    private String sex;
}
