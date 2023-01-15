package cn.geekhall.designpattern.strategy.sample2;

/**
 * MsgResult
 *
 * @author yiny
 * @date 2023/1/15 21:55
 */
public class PayState {
    private int code;
    private String msg;
    private Object data;

    public PayState(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "支付状态{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", 交易详情data=" + data +
                '}';
    }
}
