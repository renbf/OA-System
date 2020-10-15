package com.xhkj.project.system.service;

import java.util.List;

import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.system.domain.SysWorkflow;
import com.xhkj.project.system.domain.SysWorkflowGroup;
import com.xhkj.project.system.domain.SysWorkflowStep;
import com.xhkj.project.system.domain.WorkflowBillTrace;
import com.xhkj.project.system.domain.vo.SysWorkflowNodeVo;
import com.xhkj.project.system.domain.vo.SysWorkflowStepVo;

/**
 * 流程定义Service接口
 * 
 * @author xhkj
 * @date 2020-03-31
 */
public interface ISysWorkflowService 
{
    /**
     * 查询流程
     *
     * @param workflowId 流程ID
     * @return 流程
     */
    public SysWorkflow selectSysWorkflowById(Long workflowId);

    /**
     * 查询流程列表
     *
     * @param sysWorkflow 流程
     * @return 流程集合
     */
    public List<SysWorkflow> selectSysWorkflowList(SysWorkflow sysWorkflow);

    /**
     * 新增流程
     *
     * @param sysWorkflow 流程
     * @return 结果
     */
    public AjaxResult insertSysWorkflow(SysWorkflow sysWorkflow);

    /**
     * 校验流程是否唯一
     *
     * @param sysWorkflow 流程信息
     * @return 结果
     */
    public String checkWorkFlowUnique(SysWorkflow sysWorkflow);

    /**
     * 修改流程
     *
     * @param sysWorkflow 流程
     * @return 结果
     */
    public AjaxResult updateSysWorkflow(SysWorkflow sysWorkflow);


    public AjaxResult insertSysWorkflowStep(SysWorkflowStepVo sysWorkflowStepVo);

    public AjaxResult updateSysWorkflowStep(SysWorkflowStepVo sysWorkflowStepVo);

    public AjaxResult startAndEnd(Long workflowId,String status);

    public AjaxResult remove(Long workflowId);

    public AjaxResult removeStep(Long workflowStepId);

    public AjaxResult addWorkflowNode(SysWorkflowNodeVo sysWorkflowNodeVo);

    public AjaxResult editWorkflowNode(SysWorkflowNodeVo sysWorkflowNodeVo);

    public AjaxResult deleteWorkflowNode(Long workflowNodeId);

    public AjaxResult moveWorkflowNode(Long workflowNodeId,String flag);

    public List<SysWorkflowGroup> selectSysWorkflowGroupList(SysWorkflowGroup sysWorkflowGroup);

    public AjaxResult workflowStepList(Long workflowId);

    public AjaxResult getSysWorkflowNodes(Long workflowStepNodeId);

    /**
     * 提交审核接口
     * @param workflowBillTrace
     * @return
     */
    public AjaxResult submitToNextWorkflow(WorkflowBillTrace workflowBillTrace);

    public AjaxResult submitToNextWorkflow(WorkflowBillTrace workflowBillTrace,String className,String methodName);

}
