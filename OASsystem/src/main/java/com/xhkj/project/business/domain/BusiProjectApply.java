package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * 项目申请表 busi_project_apply
 *
 * @author rbf
 * @date 2020-09-30
 */
public class BusiProjectApply extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目申请ID
	 */
	private Long projectApplyId;
	/**
	 * 项目申请标题
	 */
	private String projectApplyTitle;
	/**
	 * 项目ID
	 */
	private Long projectId;
	/**
	 * 项目申请内容
	 */
	private String content;
	/**
	 * 状态（0未报送 1审批中 2审核通过 3审核拒绝）
	 */
	private String status;
	/**
	 * 是否删除（0否 1是）
	 */
	private String deleteFlag;
	/**
	 * 排序字段
	 */
	private Integer sortOrder;
//以上自动生成的尽量别动
	private List<BusiProjectApplyShenpi> shenpiUserList;

	public List<BusiProjectApplyShenpi> getShenpiUserList() {
		return shenpiUserList;
	}

	public void setShenpiUserList(List<BusiProjectApplyShenpi> shenpiUserList) {
		this.shenpiUserList = shenpiUserList;
	}

	public void setProjectApplyId(Long projectApplyId) {
		this.projectApplyId = projectApplyId;
	}

	public Long getProjectApplyId() {
		return projectApplyId;
	}

	public void setProjectApplyTitle(String projectApplyTitle) {
		this.projectApplyTitle = projectApplyTitle;
	}

	public String getProjectApplyTitle() {
		return projectApplyTitle;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("projectApplyId", getProjectApplyId())
				.append("projectApplyTitle", getProjectApplyTitle())
				.append("projectId", getProjectId())
				.append("content", getContent())
				.append("status", getStatus())
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
