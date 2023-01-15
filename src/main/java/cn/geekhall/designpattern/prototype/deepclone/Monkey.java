package cn.geekhall.designpattern.prototype.deepclone;

import java.util.Date;

/**
 * Monkey
 *
 * @author yiny
 * @date 2023/1/15 14:01
 */
public class Monkey {
    public int height;
    public int weight;
    public Date birthday;

    public Monkey() {
        this.height = 100;
        this.weight = 30;
        this.birthday = new Date(1990, 1, 1);
    }

    public Monkey deepClone() throws CloneNotSupportedException {
        Monkey monkey = (Monkey) super.clone();
        monkey.birthday = (Date) birthday.clone();
        return monkey;
    }
}
