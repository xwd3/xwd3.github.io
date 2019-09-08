package cn.pn.controller;

import cn.pn.pojo.utils.Dto;
import cn.pn.pojo.utils.ToKenVO;
import cn.pn.service.TokenService;
import cn.pn.service.UserService;
import cn.pn.tools.DtoUtil;
import cn.pn.tools.ErrorCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by dzt66 on 2019/8/14.
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/reloadToken", method = RequestMethod.POST)
    public @ResponseBody
    Dto reloadToken(@RequestParam("userAgent") String userAgent, @RequestParam("token") String token) {
        try {
            token = tokenService.reloadToken(userAgent, token);
            ToKenVO vo = new ToKenVO(token, Calendar.getInstance().getTimeInMillis() + 2 * 60 * 60 * 1000, Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public BigDecimal getUserBalance(@RequestParam("id") int id) {
        return userService.balance(id);
    }
}
