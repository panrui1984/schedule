package com.yakx.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakx.common.constant.Constants;
import com.yakx.common.core.domain.event.LogininforEvent;
import com.yakx.common.utils.ServletUtils;
import com.yakx.common.utils.StringUtils;
import com.yakx.system.domain.SysLogininfor;
import com.yakx.system.mapper.SysLogininforMapper;
import com.yakx.system.mapper.SysOperLogMapper;
import com.yakx.system.service.ISysLogininforService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor>  implements ISysLogininforService {

    @Async
    @EventListener
    public void recordLogininfor(LogininforEvent logininforEvent) {
        HttpServletRequest request = logininforEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);

       // String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        //s.append(address);
        s.append(getBlock(logininforEvent.getUsername()));
        s.append(getBlock(logininforEvent.getStatus()));
        s.append(getBlock(logininforEvent.getMessage()));
        // 打印信息到日志
        log.info(s.toString(), logininforEvent.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(logininforEvent.getUsername());
        logininfor.setIpaddr(ip);
        //logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(logininforEvent.getMessage());
        // 日志状态
        if (StringUtils.equalsAny(logininforEvent.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(logininforEvent.getStatus())) {
            logininfor.setStatus(Constants.FAIL);
        }
        // 插入数据
        insertLogininfor(logininfor);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininfor.setLoginTime(new Date());
        this.baseMapper.insert(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        Map<String, Object> params = logininfor.getParams();
        return this.baseMapper.selectList(new LambdaQueryWrapper<SysLogininfor>()
                .like(StringUtils.isNotBlank(logininfor.getIpaddr()), SysLogininfor::getIpaddr, logininfor.getIpaddr())
                .eq(StringUtils.isNotBlank(logininfor.getStatus()), SysLogininfor::getStatus, logininfor.getStatus())
                .like(StringUtils.isNotBlank(logininfor.getUserName()), SysLogininfor::getUserName, logininfor.getUserName())
                .between(params.get("beginTime") != null && params.get("endTime") != null,
                        SysLogininfor::getLoginTime, params.get("beginTime"), params.get("endTime"))
                .orderByDesc(SysLogininfor::getInfoId));
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        this.baseMapper.delete(new LambdaQueryWrapper<>());
    }
}
