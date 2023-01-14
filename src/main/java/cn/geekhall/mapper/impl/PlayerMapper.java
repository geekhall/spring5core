package cn.geekhall.mapper.impl;

import cn.geekhall.bean.Player;
import cn.geekhall.db.PlayerTemplate;
import cn.geekhall.mapper.IRowMapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * StudentMapper
 *
 * @author yiny
 * @date 2023/1/14 20:23
 */
public class PlayerMapper implements IRowMapper<List<Player>> {

    @Override
    public List<Player> mapping(ResultSet rs) throws Exception {
        List<Player> list = new ArrayList<>();
        while (rs.next()) {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setName(rs.getString("name"));
            player.setAge(rs.getInt("age"));
            player.setEmail(rs.getString("email"));
            list.add(player);
        }
        return list;
    }

    public Player selectByPrimaryKey(Long id) {
        String sql = "select * from h_player where id = ?";
        List<Player> list = PlayerTemplate.query(sql, id);
        for (Player player : list) {
            System.out.println(player);
        }
        return list.get(0);
    }
}
