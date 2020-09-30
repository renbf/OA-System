package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 项目申请审批表 busi_project_apply_shenpi
 * 
 * @author rbf
 * @date 2020-09-30
 */
public class BusiProjectApplyShenpi extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 项目申请审批ID */
	private Long projectApplyShenpiId;
	/** 项目申请ID */
	private Long projectApplyId;
	/** 审批人id */
	private Long shenpiUserId;
	/** 审批人名称 */
	private String shenpiUserName;
	/** 审核状态（-1待审核 1通过 0拒绝） */
	private String checkStatus;
	/** 排序字段 */
	private Integer sortOrder;
//以上自动生成的尽量别动
	public void setProjectApplyShenpiId(Long projectApplyShenpiId) 
	{
		this.projectApplyShenpiId = projectApplyShenpiId;
	}

	public Long getProjectApplyShenpiId() 
	{
		return projectApplyShenpiId;
	}
	public void setProjectApplyId(Long projectApplyId) 
	{
		this.projectApplyId = projectApplyId;
	}

	public Long getProjectApplyId() 
	{
		return projectApplyId;
	}
	public void setShenpiUserId(Long shenpiUserId) 
	{
		this.shenpiUserId = shenpiUserId;
	}

	public Long getShenpiUserId() 
	{
		return shenpiUserId;
	}
	public void setShenpiUserName(String shenpiUserName) 
	{
		this.shenpiUserName = shenpiUserName;
	}

	public String getShenpiUserName() 
	{
		return shenpiUserName;
	}
	public void setCheckStatus(String checkStatus) 
	{
		this.checkStatus = checkStatus;
	}

	public String getCheckStatus() 
	{
		return checkStatus;
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
            .append("projectApplyShenpiId", getProjectApplyShenpiId())
            .append("projectApplyId", getProjectApplyId())
            .append("shenpiUserId", getShenpiUserId())
            .append("shenpiUserName", getShenpiUserName())
            .append("checkStatus", getCheckStatus())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
