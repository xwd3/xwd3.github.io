package cn.pn.service;

import cn.pn.dao.UserMapper;
import cn.pn.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by dzt66 on 2019/8/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


    @Override
    public User duplicateChecking(String userPhone) {
        User user = userMapper.duplicateChecking(userPhone);
        if (null == user && "".equals(user)) {
            return null;
        }
        return user;
    }

    @Override
    public User getLoginUser(String userPhone, String userPassword) {
        User user = userMapper.getLoginUser(userPhone);
        if (null == user && "".equals(user)) {
            return null;
        }
        return user;
    }


    @Override
    public boolean register(String userPhone, String userPassword) {
        if (userMapper.register(userPhone, userPassword) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal balance(Integer id) {
        return userMapper.balance(id);
    }
}
