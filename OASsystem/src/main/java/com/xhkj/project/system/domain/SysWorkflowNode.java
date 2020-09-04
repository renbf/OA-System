package com.xhkj.project.system.domain;

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
public class SysWorkflowNode extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流程审核节点ID
     */
    private Long workflowNodeId;

    /**
     * 流程步骤节点ID
     */
    @Excel(name = "流程步骤节点ID")
    private Long workflowStepNodeId;

    /**
     * 审核步骤名称
     */
    @Excel(name = "审核步骤名称")
    private String workflowNodeName;

    /**
     * 审批人类别（1指定角色 2指定人员）
     */
    @Excel(name = "审批人类别", readConverterExp = "1=指定角色,2=指定人员")
    private String workflowNodeCheckerType;

    /**
     * 审批方式(来自字典表：依次审批、会签:所有人需全部通过或拒绝、或签:一名审批给拒绝或通过即可)
     */
    @Excel(name = "审批方式(来自字典表：依次审批、会签:所有人需全部通过或拒绝、或签:一名审批给拒绝或通过即可)")
    private String workflowNodeCheckType;

    /**
     * 是否是短信通知
     */
    @Excel(name = "是否是短信通知")
    private String isMessage;

    /**
     * 审核步骤的次序 = 排序字段
     */
    @Excel(name = "审核步骤的次序 = 排序字段")
    private Integer sortOrder;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    private List<SysWorkflowNodeChecker> sysWorkflowNodeCheckers;

    public List<SysWorkflowNodeChecker> getSysWorkflowNodeCheckers() {
        return sysWorkflowNodeCheckers;
    }

    public void setSysWorkflowNodeCheckers(List<SysWorkflowNodeChecker> sysWorkflowNodeCheckers) {
        this.sysWorkflowNodeCheckers = sysWorkflowNodeCheckers;
    }

    public Long getWorkflowNodeId() {
        return workflowNodeId;
    }

    public void setWorkflowNodeId(Long workflowNodeId) {
        this.workflowNodeId = workflowNodeId;
    }

    @NotBlank(message = "流程步骤节点ID不能为空")
    public Long getWorkflowStepNodeId() {
        return workflowStepNodeId;
    }

    public void setWorkflowStepNodeId(Long workflowStepNodeId) {
        this.workflowStepNodeId = workflowStepNodeId;
    }

    @NotBlank(message = "审核步骤名称不能为空")
    public String getWorkflowNodeName() {
        return workflowNodeName;
    }

    public void setWorkflowNodeName(String workflowNodeName) {
        this.workflowNodeName = workflowNodeName;
    }

    @NotBlank(message = "审批人类别（1指定角色 2指定人员）不能为空")
    public String getWorkflowNodeCheckerType() {
        return workflowNodeCheckerType;
    }

    public void setWorkflowNodeCheckerType(String workflowNodeCheckerType) {
        this.workflowNodeCheckerType = workflowNodeCheckerType;
    }

    @NotBlank(message = "审批方式(来自字典表：依次审批、会签:所有人需全部通过或拒绝、或签:一名审批给拒绝或通过即可)不能为空")
    public String getWorkflowNodeCheckType() {
        return workflowNodeCheckType;
    }

    public void setWorkflowNodeCheckType(String workflowNodeCheckType) {
        this.workflowNodeCheckType = workflowNodeCheckType;
    }

    @NotBlank(message = "是否是短信通知不能为空")
    public String getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(String isMessage) {
        this.isMessage = isMessage;
    }

    @NotBlank(message = "审核步骤的次序 = 排序字段不能为空")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workflowNodeId", getWorkflowNodeId())
                .append("workflowStepNodeId", getWorkflowStepNodeId())
                .append("workflowNodeName", getWorkflowNodeName())
                .append("workflowNodeCheckerType", getWorkflowNodeCheckerType())
                .append("workflowNodeCheckType", getWorkflowNodeCheckType())
                .append("ismessage", getIsMessage())
                .append("sortOrder", getSortOrder())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remarks", getRemarks())
                .toString();
    }
}
