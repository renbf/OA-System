package com.xhkj.project.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * 任务日志文件表 busi_task_log_file
 * 
 * @author rbf
 * @date 2020-10-23
 */
public class BusiTaskLogFile implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 日志文件ID */
	private Long taskLogFileId;
	/** 任务日志ID */
	private Long taskLogId;
	/** 文件名称 */
	private String fileName;
	/** 上传的附件访问路径 */
	private String fileUrl;
	/** 备注 */
	private String remark;
//以上自动生成的尽量别动
	public void setTaskLogFileId(Long taskLogFileId) 
	{
		this.taskLogFileId = taskLogFileId;
	}

	public Long getTaskLogFileId() 
	{
		return taskLogFileId;
	}
	public void setTaskLogId(Long taskLogId) 
	{
		this.taskLogId = taskLogId;
	}

	public Long getTaskLogId() 
	{
		return taskLogId;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	public String getFileName() 
	{
		return fileName;
	}
	public void setFileUrl(String fileUrl) 
	{
		this.fileUrl = fileUrl;
	}

	public String getFileUrl() 
	{
		return fileUrl;
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
            .append("taskLogFileId", getTaskLogFileId())
            .append("taskLogId", getTaskLogId())
            .append("fileName", getFileName())
            .append("fileUrl", getFileUrl())
            .append("remark", getRemark())
            .toString();
    }
}
