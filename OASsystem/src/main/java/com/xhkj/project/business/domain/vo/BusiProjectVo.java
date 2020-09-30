package com.xhkj.project.business.domain.vo;

import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.business.domain.BusiProjectMember;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 项目表 busi_project
 * 
 * @author rbf
 * @date 2020-09-24
 */
public class BusiProjectVo extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 项目ID */
	private Long projectId;
	/** 项目名称 */
	private String projectName;
	/** 负责人Id */
	private Long leaderId;
	/** 负责人名称 */
	private String leaderName;
	/** 项目开始日期 */
	private Date projectStartDate;
	/** 项目结束日期 */
	private Date projectEndDate;
	/** 项目描述 */
	private String projectDesc;
	/** 是否启用（0否 1是） */
	private String status;
	/** 项目进度（0进行中 1完成） */
	private String projectProgress;
	/** 是否删除（0否 1是） */
	private String deleteFlag;
	/** 排序字段 */
	private Integer sortOrder;
	//以上自动生成的尽量别动
	/** 部门名称 */
	private String deptNames;
	/** 成员id */
	private Long memberId;
	/** 项目成员 */
	private List<BusiProjectMember> busiProjectMembers;

	public List<BusiProjectMember> getBusiProjectMembers() {
		return busiProjectMembers;
	}

	public void setBusiProjectMembers(List<BusiProjectMember> busiProjectMembers) {
		this.busiProjectMembers = busiProjectMembers;
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
	public void setProjectStartDate(Date projectStartDate)
	{
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectStartDate()
	{
		return projectStartDate;
	}
	public void setProjectEndDate(Date projectEndDate)
	{
		this.projectEndDate = projectEndDate;
	}

	public Date getProjectEndDate()
	{
		return projectEndDate;
	}
	public void setProjectDesc(String projectDesc)
	{
		this.projectDesc = projectDesc;
	}

	public String getProjectDesc()
	{
		return projectDesc;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}
	public void setProjectProgress(String projectProgress)
	{
		this.projectProgress = projectProgress;
	}

	public String getProjectProgress()
	{
		return projectProgress;
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

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("projectId", getProjectId())
				.append("projectName", getProjectName())
				.append("leaderId", getLeaderId())
				.append("leaderName", getLeaderName())
				.append("projectStartDate", getProjectStartDate())
				.append("projectEndDate", getProjectEndDate())
				.append("projectDesc", getProjectDesc())
				.append("status", getStatus())
				.append("projectProgress", getProjectProgress())
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
