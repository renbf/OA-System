package com.xhkj.project.business.controller;

import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.service.BusiHolsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 调休年假核减Controller
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@RestController
@RequestMapping("/business/holsCheck")
public class BusiHolsCheckController extends BaseController
{
    @Autowired
    private BusiHolsCheckService iBusiHolsCheckService;

    /**
     * 获取登录人的调休及年假剩余时长
     * @return BusiHolsCheck
     */
    @GetMapping("getHolsCheckInfo")
    public AjaxResult holsRestHours(){

        return AjaxResult.success(iBusiHolsCheckService.selectBusiHolsCheckById(Long.valueOf(SecurityUtils.getUserId()));
    }



}
