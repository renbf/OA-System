package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 任务日志表 busi_task_log
 * 
 * @author rbf
 * @date 2020-10-20
 */
public class BusiTaskLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 日志ID */
	private Long taskLogId;
	/** 任务ID */
	private Long taskId;
	/** 工作内容 */
	private String dayContent;
	/** 上传的附件访问路径 */
	private String attachmentPath;
	/** 报送状态（0未报送 1已报送） */
	private String logStatus;
	/** 创建日期 */
	private Date createDate;
//以上自动生成的尽量别动
	public void setTaskLogId(Long taskLogId) 
	{
		this.taskLogId = taskLogId;
	}

	public Long getTaskLogId() 
	{
		return taskLogId;
	}
	public void setTaskId(Long taskId) 
	{
		this.taskId = taskId;
	}

	public Long getTaskId() 
	{
		return taskId;
	}
	public void setDayContent(String dayContent) 
	{
		this.dayContent = dayContent;
	}

	public String getDayContent() 
	{
		return dayContent;
	}
	public void setAttachmentPath(String attachmentPath) 
	{
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentPath() 
	{
		return attachmentPath;
	}
	public void setLogStatus(String logStatus) 
	{
		this.logStatus = logStatus;
	}

	public String getLogStatus() 
	{
		return logStatus;
	}
	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	public Date getCreateDate() 
	{
		return createDate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskLogId", getTaskLogId())
            .append("taskId", getTaskId())
            .append("dayContent", getDayContent())
            .append("attachmentPath", getAttachmentPath())
            .append("logStatus", getLogStatus())
            .append("createDate", getCreateDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
