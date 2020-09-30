package com.xhkj.project.business.domain;

import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * 任务成员关系表 busi_task_member
 * 
 * @author rbf
 * @date 2020-09-30
 */
public class BusiTaskMember extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务成员ID */
	private Long taskMemberId;
	/** 任务ID */
	private Long taskId;
	/** 成员id */
	private Long memberId;
	/** 成员名称 */
	private String memberName;
//以上自动生成的尽量别动
	public void setTaskMemberId(Long taskMemberId) 
	{
		this.taskMemberId = taskMemberId;
	}

	public Long getTaskMemberId() 
	{
		return taskMemberId;
	}
	public void setTaskId(Long taskId) 
	{
		this.taskId = taskId;
	}

	public Long getTaskId() 
	{
		return taskId;
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
            .append("taskMemberId", getTaskMemberId())
            .append("taskId", getTaskId())
            .append("memberId", getMemberId())
            .append("memberName", getMemberName())
            .toString();
    }
}
