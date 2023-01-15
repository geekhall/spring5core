package cn.geekhall.designpattern.prototype.clone;

/**
 * Client
 *
 * @author yiny
 * @date 2023/1/15 13:52
 */
public class Client {
    private IPrototype prototype;

    public Client(IPrototype prototype) {
        this.prototype = prototype;
    }

    public IPrototype startClone(IPrototype concretePrototype){
        return concretePrototype.clone();
    }
}
