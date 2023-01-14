package cn.geekhall.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
  private static String url;
  private static String driver;
  private static String username;
  private static String password;
  private DBUtil() {
  }
  static {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = loader.getResourceAsStream("db.properties");
    Properties properties = new Properties();
    try {
      properties.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    url = properties.getProperty("url");
    driver = properties.getProperty("driver");
    username = properties.getProperty("username");
    password = properties.getProperty("password");
    try {
      Class.forName(driver);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void close(ResultSet rs, Statement st, Connection conn) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (st != null) {
        st.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
