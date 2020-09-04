package com.xhkj.project.system.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 流程分组表 sys_workflow_group
 * 
 * @author rbf
 * @date 2020-07-10
 */
public class SysWorkflowGroup extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 流程分组Id */
	private Long workflowGroupId;
	/** 流程分组名称 */
	private String workflowGroupName;
	/** 是否启用（0否 1是） */
	private String status;
	/** 排序字段 */
	private Integer sortOrder;
	/** 创建者的用户ID */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 修改者的用户ID */
	private String updateBy;
	/** 修改时间 */
	private Date updateTime;
	/** 备注 */
	private String remarks;
//以上自动生成的尽量别动
	/** 流程数量 */
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setWorkflowGroupId(Long workflowGroupId)
	{
		this.workflowGroupId = workflowGroupId;
	}

	public Long getWorkflowGroupId() 
	{
		return workflowGroupId;
	}
	public void setWorkflowGroupName(String workflowGroupName) 
	{
		this.workflowGroupName = workflowGroupName;
	}

	public String getWorkflowGroupName() 
	{
		return workflowGroupName;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowGroupId", getWorkflowGroupId())
            .append("workflowGroupName", getWorkflowGroupName())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
