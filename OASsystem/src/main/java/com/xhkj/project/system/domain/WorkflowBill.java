package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 流程单(所有流程单的父)对象 workflow_bill
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class WorkflowBill extends BaseEntity
{
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
    /** 审核状态 （0待审核 1审核中 99审核完成 -1审核未通过 -2已撤消) */
    private String billStatus;
    /** 经办人=创建记录的用户名 */
    private String orginHandler;
    /** 排序字段 */
    private Integer sortOrder;
    /** 创建者的用户ID */
    private String createBy;
    /** 经办日期 = 创建时间=插入时间,保存到数据库中的时间 */
    private Date createTime;
    /** 修改者的用户ID */
    private String updateBy;
    /** 最后一次修改的时间 */
    private Date updateTime;
    /** 备注 */
    private String remarks;
    //以上自动生成的尽量别动

    //回调方法--类名称
    private String className;
    //回调方法--方法名称
    private String methodName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBillId(Long billId)
    {
        this.billId = billId;
    }

    public Long getBillId()
    {
        return billId;
    }
    public void setWorkflowId(Long workflowId)
    {
        this.workflowId = workflowId;
    }

    public Long getWorkflowId()
    {
        return workflowId;
    }
    public void setWorkflowName(String workflowName)
    {
        this.workflowName = workflowName;
    }

    public String getWorkflowName()
    {
        return workflowName;
    }
    public void setWorkflowGroupId(Long workflowGroupId)
    {
        this.workflowGroupId = workflowGroupId;
    }

    public Long getWorkflowGroupId()
    {
        return workflowGroupId;
    }
    public void setWorkflowStepId(Long workflowStepId)
    {
        this.workflowStepId = workflowStepId;
    }

    public Long getWorkflowStepId()
    {
        return workflowStepId;
    }
    public void setWorkflowStepNodeId(Long workflowStepNodeId)
    {
        this.workflowStepNodeId = workflowStepNodeId;
    }

    public Long getWorkflowStepNodeId()
    {
        return workflowStepNodeId;
    }
    public void setWorkflowNodeId(Long workflowNodeId)
    {
        this.workflowNodeId = workflowNodeId;
    }

    public Long getWorkflowNodeId()
    {
        return workflowNodeId;
    }
    public void setBillStatus(String billStatus)
    {
        this.billStatus = billStatus;
    }

    public String getBillStatus()
    {
        return billStatus;
    }
    public void setOrginHandler(String orginHandler)
    {
        this.orginHandler = orginHandler;
    }

    public String getOrginHandler()
    {
        return orginHandler;
    }
    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateBy()
    {
        return createBy;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }
    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("billId", getBillId())
                .append("workflowId", getWorkflowId())
                .append("workflowName", getWorkflowName())
                .append("workflowGroupId", getWorkflowGroupId())
                .append("workflowStepId", getWorkflowStepId())
                .append("workflowStepNodeId", getWorkflowStepNodeId())
                .append("workflowNodeId", getWorkflowNodeId())
                .append("billStatus", getBillStatus())
                .append("orginHandler", getOrginHandler())
                .append("sortOrder", getSortOrder())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remarks", getRemarks())
                .toString();
    }
}
