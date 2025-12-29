package com.yakx.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yakx.common.constant.Constants;
import com.yakx.common.constant.GlobalConstants;
import com.yakx.common.core.domain.R;
import com.yakx.common.core.domain.event.LogininforEvent;
import com.yakx.common.core.domain.model.LoginUser;
import com.yakx.common.enums.DeviceType;
import com.yakx.common.exception.user.CaptchaExpireException;
import com.yakx.common.exception.user.CaptchaException;
import com.yakx.common.helper.LoginHelper;
import com.yakx.common.utils.CacheUtil;
import com.yakx.common.utils.MessageUtils;
import com.yakx.common.utils.ServletUtils;
import com.yakx.common.utils.StringUtils;
import com.yakx.common.utils.spring.SpringUtils;
import com.yakx.web.domain.bo.login.LoginParam;
import com.yakx.web.domain.vo.login.LoginResult;
import com.yakx.web.domain.vo.login.MenuResult;
import com.yakx.web.domain.vo.RouterVO;
import com.yakx.web.util.MakeTree;
import com.yakx.web.domain.Menu;
import com.yakx.web.service.MenuService;
import com.yakx.web.domain.system.Teacher;
import com.yakx.web.service.TeacherService;
import com.yakx.web.domain.system.User;
import com.yakx.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.enumerate.SupplierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yakx
 * @description 登录
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    private final MenuService menuService;

    private final TeacherService teacherService;

    @SaIgnore
    @PostMapping("/login")
    public R login(@Validated @RequestBody LoginParam loginParm) {
        if (StringUtils.isEmpty(loginParm.getUsername()) || StringUtils.isEmpty(loginParm.getPassword())) {
            return R.fail("用户名或密码不能为空!");
        }
        validateCaptcha( loginParm.getUsername(), loginParm.getCode(), loginParm.getUuid());
        if (loginParm.getUserType().equals("2")) {
            QueryWrapper<Teacher> query = new QueryWrapper<>();
            query.lambda().eq(Teacher::getTeacherName, loginParm.getUsername())
                .eq(Teacher::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            Teacher teacher = teacherService.getOne(query);
            if (teacher == null) {
                return R.fail("用户名或密码错误!");
            }
            LoginUser loginUser = buildLoginTeacher(teacher);
            loginUser.setUserType(loginParm.getUserType());
            // 生成token
            LoginHelper.loginByDevice(loginUser, DeviceType.PC);
            Map<String, String> map = new HashMap<>();
            map.put("username", loginUser.getUsername());
            map.put("userId", Long.toString(teacher.getTeacherId()));
            recordLogininfor(loginUser.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            String token = StpUtil.getTokenValue();
            //构造返回值
            LoginResult result = new LoginResult();
            result.setUserId(teacher.getTeacherId());
            result.setToken(token);
            result.setUserType(loginParm.getUserType());
            result.setUsername(teacher.getTeacherName());
            return R.ok("登录成功", result);
        } else if (loginParm.getUserType().equals("3")) {
            //查询用户
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, loginParm.getUsername()).eq(User::getPassword,
                DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            User user = userService.getOne(query);
            if (user == null) {
                return R.fail("用户名或密码错误!");
            }
            LoginUser loginUser = buildLoginUser(user);
            loginUser.setUserType(loginParm.getUserType());
            // 生成token

            LoginHelper.loginByDevice(loginUser, DeviceType.PC);
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("userId", Long.toString(user.getUserId()));
            recordLogininfor(loginUser.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            String token = StpUtil.getTokenValue();
            //构造返回值
            LoginResult result = new LoginResult();
            result.setUserId(user.getUserId());
            result.setToken(token);
            result.setUserType(loginParm.getUserType());
            result.setUsername(user.getUsername());


            LinkedHashMap<String, String> smsmap = new LinkedHashMap<>(1);
             smsmap.put("roomName", "数据侦查实训室实验楼132134");
            smsmap.put("password", "123456");
            smsmap.put("dateTime", "2023-12-26");
            smsmap.put("startTime", "11:40");
            smsmap.put("endTime", "15:50");
             SmsBlend smsBlend = SmsFactory.createSmsBlend(SupplierType.ALIBABA);
             SmsResponse smsResponse = smsBlend.sendMessage("13952089173", "SMS_464321228", smsmap);
             smsResponse = smsBlend.sendMessage("18914766696", "SMS_464321228", smsmap);
              logger.info(smsResponse.getMessage());


            return R.ok("登录成功", result);
        } else {
            return R.fail("用户类型错误!");
        }
    }

    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        try {
            System.out.println("退出");
            LoginUser loginUser = LoginHelper.getLoginUser();
            recordLogininfor(loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
            System.out.println(loginUser.getUserId());
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
        return R.ok("退出成功");
    }

    @GetMapping("/getMenuList")
    public R<MenuResult> getMenuList(Long userId, String userType) {
        List<Menu> menuList = null;

        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser.getUserType().equals("1")) {
            menuList = menuService.getMenuByStuId(userId);
        } else if (loginUser.getUserType().equals("2")) {
            menuList = menuService.getMenuByTeacherId(userId);
        } else if (loginUser.getUserType().equals("3")) {
            menuList = menuService.getMenuByUserId(userId);
        }
        if (CollUtil.isEmpty(menuList)) {
            return R.fail("您暂无菜单权限，请联系管理员!");
        }
        List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2"))
            .collect(Collectors.toList());
        //组装路由数据
        List<RouterVO> router = MakeTree.makeRouter(collect, 0L);
        //权限字段
        Object[] array = menuList.stream().filter(item -> item.getCode() != null).map(item -> item.getCode()).toArray();
        //返回
        MenuResult result = new MenuResult();
        result.setAuthList(array);
        result.setMenuList(router);
        return R.ok("查询成功", result);
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUsername());
        //loginUser.setUserType(user.getu);
        return loginUser;
    }

    private LoginUser buildLoginTeacher(Teacher teacher) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(teacher.getTeacherId());
        loginUser.setUsername(teacher.getTeacherName());
        //loginUser.setUserType(user.getu);
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = CacheUtil.get("captcha", verifyKey);
        CacheUtil.evict("captcha", verifyKey);
        if (captcha == null) {
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }


}
