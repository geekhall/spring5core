package cn.geekhall;

import java.sql.Connection;
import java.util.List;

import cn.geekhall.bean.Player;
import cn.geekhall.dao.PlayerManager;
import cn.geekhall.mapper.impl.PlayerMapper;

public class Main {
    private static Connection conn;

    public static void main(String[] args) {
        PlayerManager playerManager = PlayerManager.getInstance();
        List<Player> playerList = playerManager.list();
        for (Player user : playerList) {
            System.out.println(user);
        }
        Player jack = playerManager.get(2);
        System.out.println(playerManager);
        System.out.println(jack);
        jack.setAge(40);
        System.out.println(playerManager);
        playerManager.update(jack);
        System.out.println(playerManager.get(2));
        // playerManager.delete(2L);
//        System.out.println(playerManager.get(2));
//        Player player = new Player();
//        player.setName("yiny");
//        player.setAge(30);
//        player.setEmail("yiny@geekhall.com");
//        playerManager.add(player);
        System.out.println("==========================================");
        PlayerMapper playerMapper = new PlayerMapper();
        Player player1 = playerMapper.selectByPrimaryKey(1L);
        System.out.println(player1);


    }
}
