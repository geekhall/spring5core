package cn.geekhall.designpattern.prototype.deepclone;

/**
 * DeepCloneTest
 *
 * @author yiny
 * @date 2023/1/15 14:12
 */
public class DeepCloneTest {
    public static void main(String[] args) {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        try {
            QiTianDaSheng clone = (QiTianDaSheng) qiTianDaSheng.clone();
            System.out.println("深克隆：" + (qiTianDaSheng.jingubang == clone.jingubang));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        QiTianDaSheng q = new QiTianDaSheng();
        QiTianDaSheng n = q.shallowClone(q);
        System.out.println("浅克隆：" + (q.jingubang == n.jingubang));
    }
}
