package spring.study.usermanager.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import spring.study.usermanager.po.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zy
 * @date 2024/3/13 10:39
 */
public class UserDaoImpl implements UserDao {

    private BasicDataSource dataSource;

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() {
        System.out.println("userDao init method invoked");
    }

    @Override
    public List<User> queryUsers(Map<String, Object> param) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            // 1.获取连接
            conn = dataSource.getConnection();
            // 2.准备预处理语句
            ps = conn.prepareStatement("select * from user where name = ?");
            ps.setString(1, (String)param.get("name"));
            // 3.执行语句
            rs = ps.executeQuery();
            // 4.处理结果集

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setSex(rs.getString(3));
                user.setAddr(rs.getString(4));
                user.setBrithDate(rs.getDate(5));
                users.add(user);
            }
            // 5.释放链接
            if(rs != null) {
                rs.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(conn != null) {
                conn.close();
            }
        }catch (SQLException se) {

            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
}
