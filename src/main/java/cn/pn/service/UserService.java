package cn.pn.service;

import cn.pn.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by dzt66 on 2019/8/9.
 */

public interface UserService {

    /**
     * 注册查重
     * @param userPhone
     * @return User对象
     */
    User duplicateChecking( String userPhone);

    /**
     * 登录
     *
     * @return User对象
     */
    User getLoginUser( String userPhone, String userPassword);

    /**
     * 注册
     *
     * @param userPhone    用户账号
     * @param userPassword 用户密码
     * @return User对象
     */
    boolean register( String userPhone, String userPassword);

    /**
     * 我的钱包***我的余额
     *
     * @param id 用户id
     * @return
     */
    BigDecimal balance(Integer id);


}
