package cn.geekhall.db;


import cn.geekhall.mapper.IRowMapper;
import cn.geekhall.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * JdbcTemplate
 *
 * @author yiny
 * @date 2023/1/14 19:35
 */
public class DBTemplate {
    private static DBTemplate instance = null;
    private DBTemplate() {
    }

    public static DBTemplate getInstance() {
        if (instance == null) {
            DBTemplate instance = new DBTemplate();
        }
        return instance;
    }
    public static <T> T query(String sql, IRowMapper<T> rsh, Object...params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            rs = ps.executeQuery();
            return rsh.mapping(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

//    public static void update(String sql, Long id) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBUtil.getConnection();
//            ps = conn.prepareStatement(sql);
//            ps.setLong(1, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.close(null, ps, conn);
//        }
//    }
//
//    public static void update(String sql, Object[] params) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBUtil.getConnection();
//            ps = conn.prepareStatement(sql);
//            for (int i = 0; i < params.length; i++) {
//                ps.setObject(i + 1, params[i]);
//            }
//
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.close(null, ps, conn);
//        }
//    }

}
