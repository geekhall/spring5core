package cn.geekhall.designpattern.prototype.deepclone;

import java.io.*;
import java.util.Date;

/**
 * QiTianDaSheng
 *
 * @author yiny
 * @date 2023/1/15 14:03
 */
public class QiTianDaSheng extends Monkey implements Cloneable , Serializable {
    public Jingubang jingubang;

    public QiTianDaSheng() {
        this.birthday = new Date();
        this.jingubang = new Jingubang();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public QiTianDaSheng deepClone() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            QiTianDaSheng copy = (QiTianDaSheng) ois.readObject();
            copy.birthday = new Date();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public QiTianDaSheng shallowClone(QiTianDaSheng target) {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        qiTianDaSheng.height = target.height;
        qiTianDaSheng.weight = target.weight;

        qiTianDaSheng.birthday = new Date();
        qiTianDaSheng.jingubang = target.jingubang;
        return qiTianDaSheng;
    }
}
