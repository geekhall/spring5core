package cn.geekhall.designpattern.prototype.deepclone;

import java.io.Serializable;

/**
 * Jingubang
 *
 * @author yiny
 * @date 2023/1/15 14:02
 */
public class Jingubang implements Serializable {
    public float h = 100;
    public float d = 10;

    public void big(){
        this.d *= 2;
        this.h *= 2;
    }

    public void small(){
        this.d /= 2;
        this.h /= 2;
    }

}
