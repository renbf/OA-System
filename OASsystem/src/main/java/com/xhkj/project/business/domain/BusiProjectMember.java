package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * 项目成员关系表 busi_project_member
 * 
 * @author rbf
 * @date 2020-09-24
 */
public class BusiProjectMember extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 项目成员ID */
	private Long projectMemberId;
	/** 项目ID */
	private Long projectId;
	/** 部门id */
	private Long deptId;
	/** 部门名称 */
	private String deptName;
	/** 成员id */
	private Long memberId;
	/** 成员名称 */
	private String memberName;
	//以上自动生成的尽量别动
	public void setProjectMemberId(Long projectMemberId)
	{
		this.projectMemberId = projectMemberId;
	}

	public Long getProjectMemberId()
	{
		return projectMemberId;
	}
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	public Long getProjectId()
	{
		return projectId;
	}
	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}

	public Long getDeptId()
	{
		return deptId;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public String getDeptName()
	{
		return deptName;
	}
	public void setMemberId(Long memberId)
	{
		this.memberId = memberId;
	}

	public Long getMemberId()
	{
		return memberId;
	}
	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}

	public String getMemberName()
	{
		return memberName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("projectMemberId", getProjectMemberId())
				.append("projectId", getProjectId())
				.append("deptId", getDeptId())
				.append("deptName", getDeptName())
				.append("memberId", getMemberId())
				.append("memberName", getMemberName())
				.toString();
	}
}
