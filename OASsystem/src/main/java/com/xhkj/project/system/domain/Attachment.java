package com.xhkj.project.system.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 附件表 ord_attachment
 *
 * @author x
 * @date 2020-03-23
 */
public class Attachment extends BaseEntity

{
	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;
	/** 业务类型 */
	private String businessType;
	/** 业务Id */
	private String orderId;
	/** 文件路径 */
	private String filePath;
	/** 真实的文件名 */
	private String fileNameReal;
	/** 显示用的文件名 */
	private String fileNameShow;
	/** 文件大小 */
	private Long fileSize;
	/** 创建时间 */
	private Date createTime;

	/** 预览文件路径 */
	private String previewPath;

	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
	public void setBusinessType(String businessType)
	{
		this.businessType = businessType;
	}

	public String getBusinessType()
	{
		return businessType;
	}
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getOrderId()
	{
		return orderId;
	}
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getFilePath()
	{
		return filePath;
	}
	public void setFileNameReal(String fileNameReal)
	{
		this.fileNameReal = fileNameReal;
	}

	public String getFileNameReal()
	{
		return fileNameReal;
	}
	public void setFileNameShow(String fileNameShow)
	{
		this.fileNameShow = fileNameShow;
	}

	public String getFileNameShow()
	{
		return fileNameShow;
	}
	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}

	public Long getFileSize()
	{
		return fileSize;
	}
	@Override
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Override
	public Date getCreateTime()
	{
		return createTime;
	}

    @Override
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessType", getBusinessType())
            .append("orderId", getOrderId())
            .append("filePath", getFilePath())
            .append("fileNameReal", getFileNameReal())
            .append("fileNameShow", getFileNameShow())
            .append("fileSize", getFileSize())
            .append("createTime", getCreateTime())
            .toString();
    }
}
