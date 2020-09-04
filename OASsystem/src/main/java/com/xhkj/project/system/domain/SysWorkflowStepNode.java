package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotBlank;
/**
 * 流程步骤节点对象 sys_workflow_step_node
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflowStepNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程步骤节点ID */
    private Long workflowStepNodeId;

    /** 流程步骤ID */
    @Excel(name = "流程步骤ID")
    private Long workflowStepId;

    /** 流程步骤类型 */
    @Excel(name = "流程步骤类型")
    private String stepType;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 角色ID */
    @Excel(name = "角色ID")
    private Long roleId;

    /** 排序字段 */
    @Excel(name = "排序字段")
    private Integer sortOrder;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public Long getWorkflowStepNodeId() { return workflowStepNodeId; }
    public void setWorkflowStepNodeId(Long workflowStepNodeId) { this.workflowStepNodeId = workflowStepNodeId; }

    @NotBlank(message = "流程步骤ID不能为空")
    public Long getWorkflowStepId() { return workflowStepId; }
    public void setWorkflowStepId(Long workflowStepId) { this.workflowStepId = workflowStepId; }

    @NotBlank(message = "流程步骤类型不能为空")
    public String getStepType() { return stepType; }
    public void setStepType(String stepType) { this.stepType = stepType; }

    @NotBlank(message = "部门id不能为空")
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowStepNodeId", getWorkflowStepNodeId())
            .append("workflowStepId", getWorkflowStepId())
            .append("stepType", getStepType())
            .append("deptId", getDeptId())
            .append("roleId", getRoleId())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
