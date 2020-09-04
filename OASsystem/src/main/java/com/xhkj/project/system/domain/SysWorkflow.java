package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程对象 sys_workflow
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程ID */
    private Long workflowId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String workflowName;

    /** 流程分组Id */
    @Excel(name = "流程分组Id")
    private Long workflowGroupId;

    /** 是否启用（0否 1是） */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String status;

    /** 审批数量 = 工作流节点数 */
    @Excel(name = "审批数量 = 工作流节点数")
    private Integer workflowNodeNum;

    /** 流程描述 */
    @Excel(name = "流程描述")
    private String workflowDesc;

    /** 审核链接url */
    @Excel(name = "审核链接url")
    private String shnepiLinkUrl;

    /** 排序字段 */
    @Excel(name = "排序字段")
    private Integer sortOrder;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 流程步骤数量 */
    private Integer workflowStepNum;

    public Integer getWorkflowStepNum() {
        return workflowStepNum;
    }

    public void setWorkflowStepNum(Integer workflowStepNum) {
        this.workflowStepNum = workflowStepNum;
    }

    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }

    @NotBlank(message = "流程名称不能为空")
    public String getWorkflowName() { return workflowName; }
    public void setWorkflowName(String workflowName) { this.workflowName = workflowName; }

    public Long getWorkflowGroupId() { return workflowGroupId; }
    public void setWorkflowGroupId(Long workflowGroupId) { this.workflowGroupId = workflowGroupId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getWorkflowNodeNum() { return workflowNodeNum; }
    public void setWorkflowNodeNum(Integer workflowNodeNum) { this.workflowNodeNum = workflowNodeNum; }

    public String getWorkflowDesc() { return workflowDesc; }
    public void setWorkflowDesc(String workflowDesc) { this.workflowDesc = workflowDesc; }

    public String getShnepiLinkUrl() { return shnepiLinkUrl; }
    public void setShnepiLinkUrl(String shnepiLinkUrl) { this.shnepiLinkUrl = shnepiLinkUrl; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowId", getWorkflowId())
            .append("workflowName", getWorkflowName())
            .append("workflowGroupId", getWorkflowGroupId())
            .append("status", getStatus())
            .append("workflowNodeNum", getWorkflowNodeNum())
            .append("workflowDesc", getWorkflowDesc())
            .append("shnepiLinkUrl", getShnepiLinkUrl())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
