package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 追踪审核单审批对象 workflow_bill_trace
 * 
 * @author xhkj
 * @date 2020-04-17
 */
public class WorkflowBillTrace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审批记录ID */
    private Long checkId;
    /** 审批单ID */
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
    /** 审核人的用户id */
    private Long checkerUserId;
    /** 审核人 */
    private String checkerUserName;
    /** 部门 */
    private String checkerDeptName;
    /** 职位 */
    private String checkerUserPost;
    /** 审核状态（1通过 0拒绝） */
    private String checkStatus;
    /** 审核意见 */
    private String checkRemarks;
    /** 附件路径 */
    private String checkAttachmentPath;
    /** 审核人的用户ID = 创建者的用户ID */
    private String createBy;
    /** 创建时间=插入时间,保存到数据库中的时间 */
    private Date createTime;
    //以上自动生成的尽量别动


    public WorkflowBillTrace() {
    }

    public WorkflowBillTrace(Long billId) {
        this.billId = billId;
    }

    public void setCheckId(Long checkId)
    {
        this.checkId = checkId;
    }

    public Long getCheckId()
    {
        return checkId;
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
    public void setCheckerUserId(Long checkerUserId)
    {
        this.checkerUserId = checkerUserId;
    }

    public Long getCheckerUserId()
    {
        return checkerUserId;
    }
    public void setCheckerUserName(String checkerUserName)
    {
        this.checkerUserName = checkerUserName;
    }

    public String getCheckerUserName()
    {
        return checkerUserName;
    }
    public void setCheckerDeptName(String checkerDeptName)
    {
        this.checkerDeptName = checkerDeptName;
    }

    public String getCheckerDeptName()
    {
        return checkerDeptName;
    }
    public void setCheckerUserPost(String checkerUserPost)
    {
        this.checkerUserPost = checkerUserPost;
    }

    public String getCheckerUserPost()
    {
        return checkerUserPost;
    }
    public void setCheckStatus(String checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    public String getCheckStatus()
    {
        return checkStatus;
    }
    public void setCheckRemarks(String checkRemarks)
    {
        this.checkRemarks = checkRemarks;
    }

    public String getCheckRemarks()
    {
        return checkRemarks;
    }
    public void setCheckAttachmentPath(String checkAttachmentPath)
    {
        this.checkAttachmentPath = checkAttachmentPath;
    }

    public String getCheckAttachmentPath()
    {
        return checkAttachmentPath;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("checkId", getCheckId())
                .append("billId", getBillId())
                .append("workflowId", getWorkflowId())
                .append("workflowName", getWorkflowName())
                .append("workflowGroupId", getWorkflowGroupId())
                .append("workflowStepId", getWorkflowStepId())
                .append("workflowStepNodeId", getWorkflowStepNodeId())
                .append("workflowNodeId", getWorkflowNodeId())
                .append("checkerUserId", getCheckerUserId())
                .append("checkerUserName", getCheckerUserName())
                .append("checkerDeptName", getCheckerDeptName())
                .append("checkerUserPost", getCheckerUserPost())
                .append("checkStatus", getCheckStatus())
                .append("checkRemarks", getCheckRemarks())
                .append("checkAttachmentPath", getCheckAttachmentPath())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
