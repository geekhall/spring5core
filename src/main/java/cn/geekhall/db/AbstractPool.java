package cn.geekhall.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * AbstractPool
 *
 * @author yiny
 * @date 2023/1/14 21:21
 */
public abstract class AbstractPool {
    public String propertiesName = "db.properties";

    private  static AbstractPool instance = null;

    protected int maxConnect = 100; // 最大连接数

    protected int normalConnect = 10; // 保持连接数

    protected String driverName = null; // 驱动名称

    protected Driver driver = null; // 驱动

    protected AbstractPool() {
        try {
            init();
            loadDriver(driverName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init() throws IOException {
        InputStream is = AbstractPool.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties properties = new Properties();
        properties.load(is);
        this.driverName = properties.getProperty("driver");
        this.maxConnect = Integer.parseInt(properties.getProperty("maxConnect"));
        this.normalConnect = Integer.parseInt(properties.getProperty("normalConnect"));
    }

    protected void loadDriver(String driverName) {
        try {
            driver = (Driver) Class.forName(driverName).newInstance();
            DriverManager.registerDriver(driver);
            System.out.println("成功注册JDBC驱动程序" + driverName);
        } catch (Exception e) {
            System.out.println("无法注册JDBC驱动程序:" + driverName + ",错误:" + e);
            e.printStackTrace();
        }
    }

    public static synchronized AbstractPool getInstance() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (instance == null) {
            instance = (AbstractPool) Class.forName("cn.geekhall.db.ConnectionPool").newInstance();
        }
        return instance;
    }

    // 获取一个连接
    public abstract Connection getConnection();

    // 获得一个连接，有时间限制
    public abstract Connection getConnection(long time);

    // 将连接对象返回给连接池
    public abstract void freeConnection(Connection conn);

    // 获取当前空闲的连接数
    public abstract int getFreeNum();

    // 获取当前连接池中工作的连接数
    public abstract int getActiveNum();

    protected synchronized void release() {
        try {
            DriverManager.deregisterDriver(driver);
            System.out.println("撤销JDBC驱动程序" + driver.getClass().getName());
        } catch (Exception e) {
            System.out.println("无法撤销JDBC驱动程序的注册:" + driver.getClass().getName());
        }
    }
}
