package com.xhkj.project.system.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 附件表 attachment_file
 * 
 * @author rbf
 * @date 2020-10-22
 */
public class AttachmentFile extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 文件id */
	private Long fileId;
	/** 文件名称 */
	private String fileName;
	/** 文件路径 */
	private String filePath;
	/** 文件访问路径 */
	private String fileUrl;
	/** 文件类型 */
	private String fileType;
	/** 状态 0:有效 1:删除 */
	private String enableStatus;
//以上自动生成的尽量别动
	public void setFileId(Long fileId) 
	{
		this.fileId = fileId;
	}

	public Long getFileId() 
	{
		return fileId;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	public String getFileName() 
	{
		return fileName;
	}
	public void setFilePath(String filePath) 
	{
		this.filePath = filePath;
	}

	public String getFilePath() 
	{
		return filePath;
	}
	public void setFileUrl(String fileUrl) 
	{
		this.fileUrl = fileUrl;
	}

	public String getFileUrl() 
	{
		return fileUrl;
	}
	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}

	public String getFileType() 
	{
		return fileType;
	}
	public void setEnableStatus(String enableStatus) 
	{
		this.enableStatus = enableStatus;
	}

	public String getEnableStatus() 
	{
		return enableStatus;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .append("fileUrl", getFileUrl())
            .append("fileType", getFileType())
            .append("enableStatus", getEnableStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
