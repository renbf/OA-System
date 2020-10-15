package com.xhkj.project.system.controller;

import java.util.Date;
import java.util.List;

import com.xhkj.common.constant.UserConstants;
import com.xhkj.common.exception.CustomException;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.bean.BeanUtils;
import com.xhkj.project.system.domain.SysWorkflowGroup;
import com.xhkj.project.system.domain.SysWorkflowNode;
import com.xhkj.project.system.domain.WorkflowBillTrace;
import com.xhkj.project.system.domain.vo.SysWorkflowNodeVo;
import com.xhkj.project.system.domain.vo.SysWorkflowStepVo;
import com.xhkj.project.system.domain.vo.WorkflowBillTraceVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xhkj.framework.aspectj.lang.annotation.Log;
import com.xhkj.framework.aspectj.lang.enums.BusinessType;
import com.xhkj.project.system.domain.SysWorkflow;
import com.xhkj.project.system.service.ISysWorkflowService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

import static com.xhkj.framework.web.domain.AjaxResult.CODE_TAG;

/**
 * 流程定义Controller
 *
 * @author xhkj
 * @date 2020-03-31
 */
@RestController
@RequestMapping("/system/workflow")
public class SysWorkflowController extends BaseController
{
    @Autowired
    private ISysWorkflowService sysWorkflowService;

    /**
     * 新增流程名称
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:add')")
    @Log(title = "新增流程名称", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysWorkflow sysWorkflow)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.insertSysWorkflow(sysWorkflow);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("新增流程名称异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 新增流程步骤
     */
//    @PreAuthorize("@ss.hasPermi('system:workflow:addStep')")
    @Log(title = "新增流程步骤", businessType = BusinessType.INSERT)
    @PostMapping("/addStep")
    public AjaxResult addStep(@RequestBody SysWorkflowStepVo sysWorkflowStepVo)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.insertSysWorkflowStep(sysWorkflowStepVo);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("新增流程步骤异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 修改流程步骤
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:editStep')")
    @Log(title = "修改流程步骤", businessType = BusinessType.UPDATE)
    @PutMapping("/editStep")
    public AjaxResult editStep(@Validated @RequestBody SysWorkflowStepVo sysWorkflowStepVo)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.updateSysWorkflowStep(sysWorkflowStepVo);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("修改流程步骤异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 启用停用流程
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:startAndEnd')")
    @Log(title = "启用停用流程", businessType = BusinessType.UPDATE)
    @PostMapping("/startAndEnd")
    public AjaxResult startAndEnd(Long workflowId,String status)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.startAndEnd(workflowId,status);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("启用停用流程异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除流程
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:remove')")
    @Log(title = "删除流程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workflowId}")
    public AjaxResult remove(@PathVariable Long workflowId)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.remove(workflowId);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("删除流程异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除流程步骤
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:remove')")
    @Log(title = "删除流程步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeStep/{workflowStepId}")
    public AjaxResult removeStep(@PathVariable Long workflowStepId)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.removeStep(workflowStepId);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("删除流程步骤异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 新增流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:addWorkflowNode')")
    @Log(title = "新增流程节点", businessType = BusinessType.INSERT)
    @PostMapping("/addWorkflowNode")
    public AjaxResult addWorkflowNode(@RequestBody SysWorkflowNodeVo sysWorkflowNodeVo)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.addWorkflowNode(sysWorkflowNodeVo);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("新增流程节点异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 修改流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:editWorkflowNode')")
    @Log(title = "修改流程节点", businessType = BusinessType.UPDATE)
    @PutMapping("/editWorkflowNode")
    public AjaxResult editWorkflowNode(@RequestBody SysWorkflowNodeVo sysWorkflowNodeVo)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.editWorkflowNode(sysWorkflowNodeVo);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("修改流程节点异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:deleteWorkflowNode')")
    @Log(title = "删除流程节点", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteWorkflowNode")
    public AjaxResult deleteWorkflowNode(Long workflowNodeId)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.deleteWorkflowNode(workflowNodeId);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("删除流程节点异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 移动流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:workflow:moveWorkflowNode')")
    @Log(title = "移动流程节点", businessType = BusinessType.UPDATE)
    @PostMapping("/moveWorkflowNode")
    public AjaxResult moveWorkflowNode(Long workflowNodeId,String flag)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            ajaxResult = sysWorkflowService.moveWorkflowNode(workflowNodeId,flag);
        }catch (Exception e){
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("移动流程节点异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 查询流程审核步骤列表
     */
    @GetMapping("/listGroup")
    public TableDataInfo listGroup()
    {
        startPage();
        List<SysWorkflowGroup> list = sysWorkflowService.selectSysWorkflowGroupList(null);
        return getDataTable(list);
    }

    /**
     * 查询流程列表
     */
    @GetMapping("/workflowList")
    public TableDataInfo workflowList(SysWorkflow sysWorkflow)
    {
        startPage();
        List<SysWorkflow> list = sysWorkflowService.selectSysWorkflowList(sysWorkflow);
        return getDataTable(list);
    }

    /**
     * 查询流程列表
     */
    @GetMapping("/workflowStepList")
    public AjaxResult workflowStepList(Long workflowId)
    {
        AjaxResult ajaxResult = sysWorkflowService.workflowStepList(workflowId);
        return ajaxResult;
    }
    /**
     * 查询流程节点列表
     */
    @GetMapping("/getSysWorkflowNodes")
    public AjaxResult getSysWorkflowNodes(Long workflowStepNodeId)
    {
        AjaxResult ajaxResult = sysWorkflowService.getSysWorkflowNodes(workflowStepNodeId);
        return ajaxResult;
    }


    /**
     * 审批方法
     * @return
     */
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody WorkflowBillTraceVo workflowBillTraceVo)
    {
        AjaxResult ajaxResult = null;
        Long[] billIds = workflowBillTraceVo.getBillIds();

        WorkflowBillTrace workflowBillTrace = new WorkflowBillTrace();
        BeanUtils.copyProperties(workflowBillTraceVo, workflowBillTrace);

        for (int i = 0; i < billIds.length; i++) {
            Long billId = billIds[i];
            workflowBillTrace.setBillId(billId);
            ajaxResult = sysWorkflowService.submitToNextWorkflow(workflowBillTrace);
        }

        return ajaxResult;
    }

}
