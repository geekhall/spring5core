package cn.geekhall.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * DBConnectionPool
 *
 * @author yiny
 * @date 2023/1/14 21:44
 */
public class DBConnectionPool extends AbstractPool {
    private int checkedOut; // 已经使用的连接数
    private Vector<Connection> freeConnections = new Vector<>(); // 存放连接池中数据库连接的容器
    private String username = null; // 数据库用户名
    private String password = null; // 数据库密码
    private String url = null; // 数据库连接地址
    private static int num = 0; // 空闲连接数

    private static int active = 0; // 活动连接数

    private static DBConnectionPool instance = null; // 连接池实例


    private DBConnectionPool() {
        try {
            init();
            for (int i = 0; i < normalConnect; i++) { // 创建normalConnect个连接
                Connection conn = newConnection();
                if (conn != null) {
                    freeConnections.addElement(conn);
                    num++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection newConnection() {
        Connection conn = null;
        try {
            if (username == null) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, username, password);
            }
            System.out.println("创建了一个新的连接");
        } catch (SQLException e) {
            System.out.println("无法创建这个URL的连接:" + url);
            return null;
        }
        return conn;
    }

    public static DBConnectionPool getInstance() {
        if (instance == null) {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    private void init() throws IOException {
        InputStream is = DBConnectionPool.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties properties = new Properties();
        properties.load(is);
        this.driverName = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.maxConnect = Integer.parseInt(properties.getProperty("maxConnect"));
        this.normalConnect = Integer.parseInt(properties.getProperty("normalConnect"));
    }

    /**
     * 获取当前空闲的连接数
     *
     * @return 当前空闲的连接数
     */
    @Override
    public int getFreeNum() {
        return num;
    }

    /**
     * 获取当前活动的连接数
     *
     * @return 当前活动的连接数
     */
    @Override
    public int getActiveNum() {
        return active;
    }

    /**
     * 获取一个可用的连接，如果没有，则创建一个连接，且小于最大连接限制
     *
     * @return 返回一个可用的连接对象
     */
    public synchronized Connection getConnection() {
        Connection conn = null;
        if (freeConnections.size() > 0) { // 如果有空闲的连接
            num--;
            conn = freeConnections.firstElement(); // 取出第一个元素
            freeConnections.removeElementAt(0); // 删除第一个元素
            try {
                if (conn.isClosed()) { // 测试此连接是否已关闭
                    System.out.println("从连接池删除一个无效连接");
                    conn = getConnection(); // 递归调用，再次从池中获取连接
                }
            } catch (SQLException e) {
                System.out.println("从连接池删除一个无效连接");
                conn = getConnection(); // 递归调用，再次从池中获取连接
            }
        } else if (maxConnect == 0 || checkedOut < maxConnect) {
            conn = newConnection(); // 创建一个连接
        }
        if (conn != null) {
            checkedOut++;
            active++;
        }
        return conn; // 返回获取到的连接
    }

    /**
     * 获取一个可用的连接，如果没有，则创建一个连接，且小于最大连接限制
     *
     * @param timeout 获取连接的超时时间，单位为毫秒
     * @return 返回一个可用的连接对象
     */
    public synchronized Connection getConnection(long timeout) {
        long startTime = System.currentTimeMillis();
        Connection conn;
        while ((conn = getConnection()) == null) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                return null;
            }
        }
        return conn;
    }

    /**
     * 关闭所有连接
     *
     */
    public synchronized void release() {
        try {
            // 将当前连接池中的连接赋值给枚举
            Enumeration<Connection> allConnections = freeConnections.elements();
            // 遍历枚举中的所有连接并关闭
            while (allConnections.hasMoreElements()) {
                Connection conn = allConnections.nextElement();
                try {
                    conn.close();
                    num--;
                } catch (SQLException e) {
                    System.out.println("无法关闭连接池中的连接");
                }
            }
            freeConnections.removeAllElements();
            checkedOut = 0;
        } catch (Exception e) {
            System.out.println("无法关闭连接池中的连接");
        } finally {
            super.release();
        }
    }

    /**
     * 将不再使用的连接返回给连接池
     *
     * @param conn 需返回给连接池的连接对象
     */
    public synchronized void freeConnection(Connection conn) {
        // 将指定连接对象添加到向量末尾
        freeConnections.addElement(conn);
        num++;
        active--;
        // 唤醒等待的线程
        notifyAll();
    }

}
