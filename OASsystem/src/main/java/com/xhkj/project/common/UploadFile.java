package com.xhkj.project.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 *
 * 
 * @author rbf
 * @date 2020-10-23
 */
public class UploadFile implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 文件名称 */
	private String name;
	/** 上传的附件访问路径 */
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
