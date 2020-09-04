package com.xhkj.project.api.controller;

import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.xhkj.common.constant.Constants;
import com.xhkj.common.exception.CustomException;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.framework.security.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;

/**
 * 通用接口Controller
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@RestController
@RequestMapping("/api")
public class ApiMainController extends BaseController
{

    private static final String PRE_PACKAGE = "com.xhkj.project.api.controller.";

    private static final String DEFAULT_CLASS = "ApiCommonService";


    @Autowired
    private SysLoginService loginService;

    /**
     * 通过账号密码获取Token
     */
    @PostMapping("/getToken")
    public AjaxResult getToken(@RequestBody JSONObject json)
    {
        logger.info("getToken init...");

        String username = json.getString("username");
        String password = json.getString("password");

        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.ApiLogin(username, password);
        ajax.put(Constants.TOKEN, token);

        logger.info("getToken success...");

        return ajax;
    }


    /**
     * 他通过账号密码获取Token
     */
    @PostMapping("/test")
    public AjaxResult test(@RequestBody JSONObject json)
    {
        logger.info("test init...");

        // 获取ApiId，根据ApiId查询具体调用方法
        String apiId = json.getString("apiId");
        if(StringUtils.isEmpty(apiId)) {
            throw new CustomException("apiId is empty!");
        }

        // 获取调用类
        String service = json.getString("service");
        if(StringUtils.isEmpty(service)) {
            throw new CustomException("service is empty!");
        }

        // 获取调用方法
        String type = json.getString("type");
        if(StringUtils.isEmpty(type)) {
            throw new CustomException("type is empty!");
        }

        // 方案1：Class和method 根据appid通过数据库配置获取，
        // 方案2：Class和method 通过参数直接传递
        String calss = "";
        String mothed = "check";

        try {

            Class<?> cls = Class.forName(PRE_PACKAGE + "ApiCommonService");
            Method method = cls.getMethod(mothed, String.class);
            String aaa = (String) method.invoke(cls.newInstance(),"123");



            logger.info("test aaa..." + aaa);









        } catch(Exception e) {

        }


        AjaxResult ajax = AjaxResult.success();


        logger.info("test success...");

        return ajax;
    }


}
