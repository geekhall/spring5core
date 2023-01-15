package cn.geekhall.designpattern.proxy.staticproxy.sample2;

import lombok.Data;

/**
 * Order
 *
 * @author yiny
 * @date 2023/1/15 14:34
 */
@Data
public class Order {
    private Object orderInfo;
    private Long createTime;
    private String id;

}
