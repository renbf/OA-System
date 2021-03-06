package com.xhkj.project.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.system.domain.WorkflowBill;

import java.util.Date;
import java.util.Objects;

public class BusiAskLeaveAprVo extends BusiAskLeave {
    private static final long serialVersionUID = 1L;

    /** 流程单ID */
    private Long billId;
    /** 流程ID,逻辑外键 (主表: sys_workflow) */
    private Long workflowId;
    /** 流程名称(冗余存储,方便查询) */
    private String workflowName;
    /** 流程分组ID(冗余存储,方便查询) */
    private Long workflowGroupId;
    /** 流程步骤ID */
    private Long workflowStepId;
    /** 流程步骤节点ID */
    private Long workflowStepNodeId;
    /** 流程审核节点ID */
    private Long workflowNodeId;
    /** 审核状态  */
    private String billStatus;
    /** 经办人=创建记录的用户名 */
    private String orginHandler;
    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    /** 备注 */
    private String remarks;

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public Long getWorkflowGroupId() {
        return workflowGroupId;
    }

    public void setWorkflowGroupId(Long workflowGroupId) {
        this.workflowGroupId = workflowGroupId;
    }

    public Long getWorkflowStepId() {
        return workflowStepId;
    }

    public void setWorkflowStepId(Long workflowStepId) {
        this.workflowStepId = workflowStepId;
    }

    public Long getWorkflowStepNodeId() {
        return workflowStepNodeId;
    }

    public void setWorkflowStepNodeId(Long workflowStepNodeId) {
        this.workflowStepNodeId = workflowStepNodeId;
    }

    public Long getWorkflowNodeId() {
        return workflowNodeId;
    }

    public void setWorkflowNodeId(Long workflowNodeId) {
        this.workflowNodeId = workflowNodeId;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getOrginHandler() {
        return orginHandler;
    }

    public void setOrginHandler(String orginHandler) {
        this.orginHandler = orginHandler;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
