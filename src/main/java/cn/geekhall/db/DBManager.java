package cn.geekhall.db;

import cn.geekhall.bean.Player;
import cn.geekhall.mapper.IRowMapper;

import java.util.Collections;
import java.util.List;

/**
 * PlayerManager
 *
 * @author yiny
 * @date 2023/1/14 19:09
 */
public class DBManager {

    private static DBManager instance = null;
    private DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static List<Player> list(IRowMapper<Player> rsh) {
        String sql = "select * from h_player";
        return Collections.singletonList(DBTemplate.query(sql, rsh));
    }

    public static <T> T get(long id, IRowMapper<T> rsh) {
        String sql = "SELECT * FROM h_player WHERE id = ?";
        List<T> list = Collections.singletonList(DBTemplate.query(sql, rsh, id));
        return list.size() > 0 ? list.get(0) : null;
    }

    public void update(Player player) {
        String sql = "UPDATE h_player SET name = ?, age = ?, email = ? WHERE id = ?";
        Object[] params = {player.getName(), player.getAge(), player.getEmail(), player.getId()};
        System.out.println("update sql: " + sql);
        PlayerTemplate.update(sql, params);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM h_player WHERE id = ?";
        PlayerTemplate.update(sql, id);
    }

    public void add(Player player) {
        String sql = "INSERT INTO h_player (name, age, email) VALUES (?, ?, ?)";
        Object[] params = {player.getName(), player.getAge(), player.getEmail()};
        PlayerTemplate.update(sql, params);
    }
}
