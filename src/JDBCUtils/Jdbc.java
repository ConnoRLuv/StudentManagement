package JDBCUtils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Jdbc {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet result = null;

    static {
        InputStream in = Jdbc.class.getClassLoader().getResourceAsStream("db.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");
            Class.forName(driver);
            System.out.println("驱动加载成功");
        } catch (Exception e) {
            System.out.println("驱动加载出现异常");
        }
    }

    public static Connection getConnection() {
        try {
            System.out.println("数据库连接成功");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("数据库连接出现异常");
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet runQuery(String sql) throws SQLException {
        if (conn == null) {
            conn = getConnection();
        }
        if (state == null) {
            state = conn.createStatement();
        }

        return state.executeQuery(sql);
    }

    public static int runUpdate(String sql) throws SQLException {
        int Count = 0;//用来记录受影响的记录行数
        if (conn == null) {
            conn = getConnection();
        }
        if (state == null) {
            state = conn.createStatement();
        }
        Count = state.executeUpdate(sql);
        if (state != null) {
            state.close();
            state = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
        return Count;
    }

    public static void closeConnection(Connection conn) {
        System.out.println("正在关闭连接...");
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println("连接关闭出现异常");
            e.printStackTrace();
        }
    }

    public static void realeaseAll() {
        try {
            if (result != null) {
                result.close();
                result = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (state != null) {
                state.close();
                state = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //用于测试的
    public static void main(String[] args) {
        /*
        try {
            conn = Jdbc.getConnection();
            state = conn.createStatement();
            result = state.executeQuery("SELECT * FROM Student");
            while (result.next()) {
                System.out.println(result.getString("Sname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        try {
            String sql = "SELECT * FROM Student";
            result = Jdbc.runQuery(sql);
            while (result.next()) {
                System.out.println(result.getString("Sname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
