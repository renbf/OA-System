package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * 任务表 busi_task
 * 
 * @author rbf
 * @date 2020-09-30
 */
public class BusiTask extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务ID */
	private Long taskId;
	/** 任务名称 */
	private String taskName;
	/** 项目ID */
	private Long projectId;
	/** 任务开始日期 */
	private Date taskStartDate;
	/** 任务结束日期 */
	private Date taskEndDate;
	/** 任务描述 */
	private String taskDesc;
	/** 是否启用（0否 1是） */
	private String status;
	/** 项目进度（0进行中 1完成） */
	private String taskProgress;
	/** 是否删除（0否 1是） */
	private String deleteFlag;
	/** 排序字段 */
	private Integer sortOrder;
//以上自动生成的尽量别动
	private List<Date> taskDate;

	private List<Long> userList;

	public List<Date> getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(List<Date> taskDate) {
		this.taskDate = taskDate;
	}

	public List<Long> getUserList() {
		return userList;
	}

	public void setUserList(List<Long> userList) {
		this.userList = userList;
	}

	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}

	public Long getTaskId() 
	{
		return taskId;
	}
	public void setTaskName(String taskName) 
	{
		this.taskName = taskName;
	}

	public String getTaskName() 
	{
		return taskName;
	}
	public void setProjectId(Long projectId) 
	{
		this.projectId = projectId;
	}

	public Long getProjectId() 
	{
		return projectId;
	}
	public void setTaskStartDate(Date taskStartDate) 
	{
		this.taskStartDate = taskStartDate;
	}

	public Date getTaskStartDate() 
	{
		return taskStartDate;
	}
	public void setTaskEndDate(Date taskEndDate) 
	{
		this.taskEndDate = taskEndDate;
	}

	public Date getTaskEndDate() 
	{
		return taskEndDate;
	}
	public void setTaskDesc(String taskDesc) 
	{
		this.taskDesc = taskDesc;
	}

	public String getTaskDesc() 
	{
		return taskDesc;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setTaskProgress(String taskProgress) 
	{
		this.taskProgress = taskProgress;
	}

	public String getTaskProgress() 
	{
		return taskProgress;
	}
	public void setDeleteFlag(String deleteFlag) 
	{
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() 
	{
		return deleteFlag;
	}
	public void setSortOrder(Integer sortOrder) 
	{
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() 
	{
		return sortOrder;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("projectId", getProjectId())
            .append("taskStartDate", getTaskStartDate())
            .append("taskEndDate", getTaskEndDate())
            .append("taskDesc", getTaskDesc())
            .append("status", getStatus())
            .append("taskProgress", getTaskProgress())
            .append("deleteFlag", getDeleteFlag())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
