package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotBlank;
/**
 * 流程审核步骤对应的审核人对象 sys_workflow_node_checker
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflowNodeChecker extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审核人ID */
    private Long workflowNodeCheckerId;

    /** 审核步骤ID,主键 (根据主键生成策略决定值) */
    @Excel(name = "审核步骤ID,主键 (根据主键生成策略决定值)")
    private Long workflowNodeId;

    /** 审核步骤名称(冗余存储,方便查询) */
    @Excel(name = "审核步骤名称(冗余存储,方便查询)")
    private String workflowNodeName;

    /** 审批人类别（1指定角色 2指定人员） */
    @Excel(name = "审批人类别", readConverterExp = "1=指定角色,2=指定人员")
    private String workflowNodeCheckerType;

    /** 审核人名称(角色ID或用户ID) */
    @Excel(name = "审核人名称(角色ID或用户ID)")
    private Long workflowNodeRoleId;

    /** 审核人名称(角色名或用户名),修改时注意和父表sys_workflow_node保持对应 */
    @Excel(name = "审核人名称(角色名或用户名),修改时注意和父表sys_workflow_node保持对应")
    private String workflowNodeRoleName;

    /** 审核人名称(角色ID或用户ID) */
    @Excel(name = "审核人名称(角色ID或用户ID)")
    private Long workflowNodeUserId;

    /** 审核人名称(角色名或用户名),修改时注意和父表sys_workflow_node保持对应 */
    @Excel(name = "审核人名称(角色名或用户名),修改时注意和父表sys_workflow_node保持对应")
    private String workflowNodeUserName;

    /** 审核步骤的次序 = 排序字段 */
    @Excel(name = "审核步骤的次序 = 排序字段")
    private Integer sortOrder;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public Long getWorkflowNodeCheckerId() { return workflowNodeCheckerId; }
    public void setWorkflowNodeCheckerId(Long workflowNodeCheckerId) { this.workflowNodeCheckerId = workflowNodeCheckerId; }

    @NotBlank(message = "审核步骤ID,主键 (根据主键生成策略决定值)不能为空")
    public Long getWorkflowNodeId() { return workflowNodeId; }
    public void setWorkflowNodeId(Long workflowNodeId) { this.workflowNodeId = workflowNodeId; }

    @NotBlank(message = "审核步骤名称(冗余存储,方便查询)不能为空")
    public String getWorkflowNodeName() { return workflowNodeName; }
    public void setWorkflowNodeName(String workflowNodeName) { this.workflowNodeName = workflowNodeName; }

    @NotBlank(message = "审批人类别（1指定角色 2指定人员）不能为空")
    public String getWorkflowNodeCheckerType() { return workflowNodeCheckerType; }
    public void setWorkflowNodeCheckerType(String workflowNodeCheckerType) { this.workflowNodeCheckerType = workflowNodeCheckerType; }

    public Long getWorkflowNodeRoleId() { return workflowNodeRoleId; }
    public void setWorkflowNodeRoleId(Long workflowNodeRoleId) { this.workflowNodeRoleId = workflowNodeRoleId; }

    public String getWorkflowNodeRoleName() { return workflowNodeRoleName; }
    public void setWorkflowNodeRoleName(String workflowNodeRoleName) { this.workflowNodeRoleName = workflowNodeRoleName; }

    public Long getWorkflowNodeUserId() { return workflowNodeUserId; }
    public void setWorkflowNodeUserId(Long workflowNodeUserId) { this.workflowNodeUserId = workflowNodeUserId; }

    public String getWorkflowNodeUserName() { return workflowNodeUserName; }
    public void setWorkflowNodeUserName(String workflowNodeUserName) { this.workflowNodeUserName = workflowNodeUserName; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowNodeCheckerId", getWorkflowNodeCheckerId())
            .append("workflowNodeId", getWorkflowNodeId())
            .append("workflowNodeName", getWorkflowNodeName())
            .append("workflowNodeCheckerType", getWorkflowNodeCheckerType())
            .append("workflowNodeRoleId", getWorkflowNodeRoleId())
            .append("workflowNodeRoleName", getWorkflowNodeRoleName())
            .append("workflowNodeUserId", getWorkflowNodeUserId())
            .append("workflowNodeUserName", getWorkflowNodeUserName())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
