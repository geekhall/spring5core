package cn.geekhall.mapper;

import java.sql.ResultSet;

/**
 * IRowMapper
 *
 * @author yiny
 * @date  2023/1/14 20:13
 */
public interface IRowMapper<T> {
    // 将结果集中的数据封装到对象中
    T mapping(ResultSet rs) throws Exception;
}
