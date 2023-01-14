package cn.geekhall.util;

import cn.geekhall.bean.Player;

import java.util.List;

/**
 * PlayerManager
 *
 * @author yiny
 * @date 2023/1/14 19:09
 */
public class PlayerManager {

    private static PlayerManager instance = null;
    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public static List<Player> list() {
        String sql = "select * from h_player";
        return PlayerTemplate.query(sql);
    }

    public static Player get(long id) {
        String sql = "SELECT * FROM h_player WHERE id = ?";
        List<Player> list = PlayerTemplate.query(sql, id);
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
