package cn.pn.dao;


import cn.pn.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


@Mapper
public interface UserMapper {

    /**
     * 注册查重
     * @param userPhone
     * @return
     */
    User duplicateChecking( String userPhone);

    /**
     * 登录
     * @return User对象
     */
    User getLoginUser( String userPhone);


    /**
     * 注册
     * @param userPhone 用户账号
     * @param userPassword 用户密码
     * @return User对象
     */
    int register(@Param("usercode") String userPhone,@Param("userpwd") String userPassword);

    /**
     * 我的钱包***我的余额
     * @param id 用户id
     * @return
     */
    BigDecimal balance(Integer id);

}
