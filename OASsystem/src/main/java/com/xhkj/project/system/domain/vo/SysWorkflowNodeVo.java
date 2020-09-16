package com.xhkj.project.system.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 步骤节点流程审核对象 sys_workflow_node
 *
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflowNodeVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程审核节点ID */
    private Long workflowNodeId;

    /** 流程步骤节点ID */
    private Long workflowStepNodeId;

    /** 审核步骤名称 */
    private String workflowNodeName;

    /** 审批人类别（1指定角色 2指定人员） */
    private String workflowNodeCheckerType;

    /** 审批方式(来自字典表：依次审批、会签:所有人需全部通过或拒绝、或签:一名审批给拒绝或通过即可) */
    private String workflowNodeCheckType;

    /** 是否是短信通知 */
    private String isMessage;

    /** 审核步骤的次序 = 排序字段 */
    private Integer sortOrder;

    /** 备注 */
    private String remarks;
    /** 审核人角色ID */
    private List<Long> workflowNodeRoleIds;
    /** 审核人用户ID */
    private List<Long> workflowNodeUserIds;

    public List<Long> getWorkflowNodeRoleIds() {
        return workflowNodeRoleIds;
    }

    public void setWorkflowNodeRoleIds(List<Long> workflowNodeRoleIds) {
        this.workflowNodeRoleIds = workflowNodeRoleIds;
    }

    public List<Long> getWorkflowNodeUserIds() {
        return workflowNodeUserIds;
    }

    public void setWorkflowNodeUserIds(List<Long> workflowNodeUserIds) {
        this.workflowNodeUserIds = workflowNodeUserIds;
    }

    public Long getWorkflowNodeId() { return workflowNodeId; }
    public void setWorkflowNodeId(Long workflowNodeId) { this.workflowNodeId = workflowNodeId; }

    public Long getWorkflowStepNodeId() { return workflowStepNodeId; }
    public void setWorkflowStepNodeId(Long workflowStepNodeId) { this.workflowStepNodeId = workflowStepNodeId; }

    public String getWorkflowNodeName() { return workflowNodeName; }
    public void setWorkflowNodeName(String workflowNodeName) { this.workflowNodeName = workflowNodeName; }

    public String getWorkflowNodeCheckerType() { return workflowNodeCheckerType; }
    public void setWorkflowNodeCheckerType(String workflowNodeCheckerType) { this.workflowNodeCheckerType = workflowNodeCheckerType; }

    public String getWorkflowNodeCheckType() { return workflowNodeCheckType; }
    public void setWorkflowNodeCheckType(String workflowNodeCheckType) { this.workflowNodeCheckType = workflowNodeCheckType; }

    public String getIsMessage() { return isMessage; }
    public void setIsMessage(String isMessage) { this.isMessage = isMessage; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

}
