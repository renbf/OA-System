package com.xhkj.project.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 项目负责人审批表 busi_project_leader_workflow
 * 
 * @author rbf
 * @date 2020-11-18
 */
public class BusiProjectLeaderWorkflow implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 流程单ID */
	private Long billId;
	/** 流程ID,逻辑外键 (主表: sys_workflow) */
	private Long workflowId;
	/** 项目ID */
	private Long projectId;
	/** 项目名称 */
	private String projectName;
	/** 负责人Id */
	private Long leaderId;
	/** 负责人名称 */
	private String leaderName;
	/** 流程分组名称 */
	private String workflowGroupName;
	/** 部门名称 */
	private String deptName;
	/** 审核状态 （0待审核 1通过 99拒绝) */
	private String billStatus;
	/** 提交人昵称 */
	private String createNickName;
	/** 排序字段 */
	private Integer sortOrder;
	/** 创建者的用户ID */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 修改者的用户ID */
	private String updateBy;
	/** 最后一次修改的时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;
//以上自动生成的尽量别动
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
	public void setProjectId(Long projectId) 
	{
		this.projectId = projectId;
	}

	public Long getProjectId() 
	{
		return projectId;
	}
	public void setProjectName(String projectName) 
	{
		this.projectName = projectName;
	}

	public String getProjectName() 
	{
		return projectName;
	}
	public void setLeaderId(Long leaderId) 
	{
		this.leaderId = leaderId;
	}

	public Long getLeaderId() 
	{
		return leaderId;
	}
	public void setLeaderName(String leaderName) 
	{
		this.leaderName = leaderName;
	}

	public String getLeaderName() 
	{
		return leaderName;
	}
	public void setWorkflowGroupName(String workflowGroupName) 
	{
		this.workflowGroupName = workflowGroupName;
	}

	public String getWorkflowGroupName() 
	{
		return workflowGroupName;
	}
	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public String getDeptName() 
	{
		return deptName;
	}
	public void setBillStatus(String billStatus) 
	{
		this.billStatus = billStatus;
	}

	public String getBillStatus() 
	{
		return billStatus;
	}
	public void setCreateNickName(String createNickName) 
	{
		this.createNickName = createNickName;
	}

	public String getCreateNickName() 
	{
		return createNickName;
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
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("billId", getBillId())
            .append("workflowId", getWorkflowId())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("leaderId", getLeaderId())
            .append("leaderName", getLeaderName())
            .append("workflowGroupName", getWorkflowGroupName())
            .append("deptName", getDeptName())
            .append("billStatus", getBillStatus())
            .append("createNickName", getCreateNickName())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
