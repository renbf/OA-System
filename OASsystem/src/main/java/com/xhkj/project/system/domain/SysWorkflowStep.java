package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotBlank;
/**
 * 流程步骤定义对象 sys_workflow_step
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflowStep extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程步骤ID */
    private Long workflowStepId;

    /** 流程步骤名称 */
    @Excel(name = "流程步骤名称")
    private String workflowStepName;

    /** 流程ID */
    @Excel(name = "流程ID")
    private Long workflowId;

    /** 流程步骤类型 */
    @Excel(name = "流程步骤类型")
    private String stepType;

    /** 排序字段 */
    @Excel(name = "排序字段")
    private Integer sortOrder;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public Long getWorkflowStepId() { return workflowStepId; }
    public void setWorkflowStepId(Long workflowStepId) { this.workflowStepId = workflowStepId; }

    @NotBlank(message = "流程步骤名称不能为空")
    public String getWorkflowStepName() { return workflowStepName; }
    public void setWorkflowStepName(String workflowStepName) { this.workflowStepName = workflowStepName; }

    @NotBlank(message = "流程ID不能为空")
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }

    @NotBlank(message = "流程步骤类型不能为空")
    public String getStepType() { return stepType; }
    public void setStepType(String stepType) { this.stepType = stepType; }

    @NotBlank(message = "排序字段不能为空")
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowStepId", getWorkflowStepId())
            .append("workflowStepName", getWorkflowStepName())
            .append("workflowId", getWorkflowId())
            .append("stepType", getStepType())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
