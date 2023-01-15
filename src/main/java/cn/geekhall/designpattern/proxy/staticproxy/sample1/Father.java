package cn.geekhall.designpattern.proxy.staticproxy.sample1;

/**
 * Father
 *
 * @author yiny
 * @date 2023/1/15 14:23
 */
public class Father implements IPerson{
    private Son son;

    public Father(Son son) {
        this.son = son;
    }

    @Override
    public void findLove() {
        System.out.println("父亲物色对象");
        this.son.findLove();
        System.out.println("双方同意，确立关系");
    }
}
