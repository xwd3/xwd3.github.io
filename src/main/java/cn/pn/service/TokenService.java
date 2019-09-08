package cn.pn.service;


import cn.pn.pojo.User;

/**
 * Created by Administrator on 2019/7/31.
 */
public interface TokenService {
    //生成Token
    String generateToken(String userAgent, User user) throws  Exception;

    //保存生成的Token到Redis，服务器端保存token对应的用户信息
    //key是token ,value 是用户对象
    void save(String token, User user);

    //验证Token
    boolean validate(String userAgent, String token) throws  Exception;


    //删除Token
    boolean delete(String token);

    //Token置换
    String  reloadToken(String userAgent, String token) throws Exception ;
}
