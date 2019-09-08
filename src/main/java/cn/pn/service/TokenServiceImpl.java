package cn.pn.service;


import cn.pn.pojo.User;
import cn.pn.tools.MD5;
import com.alibaba.fastjson.JSON;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2019/7/31.
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    //生成Token的方法
    @Override
    public String generateToken(String userAgent, User user) throws Exception {

        // 客户端标识-USERCODE-USERID-CREATIONDATE-USERAGENT[6位]+RANDOM[6]
        StringBuilder sb = new StringBuilder();
        sb.append("token:");
        //转成UserAgent对象
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        //获取客户端操作系统是否是移动的设备
        if (agent.getOperatingSystem().isMobileDevice()) {
            sb.append("MOBILE-");
        } else {
            sb.append("PC-");
        }
        sb.append(MD5.getMd5(user.getUserPhone(), 32) + "-");
        sb.append(user.getId() + "-");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
        sb.append(MD5.getMd5(userAgent, 6));
        return sb.toString();
    }

    //保存用户的Token信息  PC端设置为2小时，手机端长期保存会话
    @Override
    public void save(String token, User user) {

        if (token.startsWith("token:PC-")) {
            System.out.println(token);
            redisTemplate.opsForValue().set(token, JSON.toJSONString(user), 2 * 60 * 60, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(token, JSON.toJSONString(user));
        }
    }

    //userAgent 获取浏览器的各种信息，包括设备
    @Override
    public boolean validate(String userAgent, String token) throws Exception {
        if (!redisTemplate.hasKey(token)) {
            return false;
        }
        //验证Token是不是由上一次浏览器生成的token
        String agentMD5 = token.split("-")[4];
        System.out.println(agentMD5);
        if (!MD5.getMd5(userAgent, 6).equals(agentMD5)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String token) {
        redisTemplate.delete(token);
        return true;
    }

    private long protectedTime = 30 * 60;//定义置换的时间
    private long delay = 2 * 60;//定义老的token的延时过期时间

    @Override
    public String reloadToken(String userAgent, String token) throws Exception {
        //1、验证token是否有效
        if (!redisTemplate.hasKey(token)) {
            throw new Exception("token不存在");
        }
        //2、能不能置换
        //首先从token中拿到生成的时间
        Date genTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(token.split("-")[3]);
        //然后求出生成时间与当前时间的差，毫秒
        long passed = Calendar.getInstance().getTimeInMillis() - genTime.getTime();
        if (passed < protectedTime*1000) {
            throw new Exception("置换保护期内，不能置换，剩余：" + (protectedTime*1000 - passed) / 1000);
        }
        //3、进行置换
        Object tokenObject = redisTemplate.opsForValue().get(token);
        User user = JSON.parseObject(tokenObject.toString(), User.class);
        String newToken = this.generateToken(userAgent, user);
        //4、老的token延时过期
        redisTemplate.opsForValue().set(token,JSON.toJSONString(user),delay,TimeUnit.SECONDS);
        //5、新的token保存在Redis ，调用save方法保存新的token
        this.save(newToken,user);
        return newToken;
    }


}
