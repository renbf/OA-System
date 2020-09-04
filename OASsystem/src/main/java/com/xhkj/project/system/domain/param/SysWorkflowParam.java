package com.xhkj.project.system.domain.param;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.Date;

/**
 * 流程表 sys_workflow
 * 
 * @author rbf
 * @date 2020-04-17
 */
public class SysWorkflowParam implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 流程ID */
	private Long workflowId;
	/** 流程名称 */
	private String workflowName;
	/** 流程分组Id */
	private Long workflowGroupId;
	/** 是否启用（0否 1是） */
	private String status;
	/** 审批数量 = 工作流节点数 */
	private Integer workflowNodeNum;
	/** 流程描述 */
	private String workflowDesc;
	/** 审核链接url */
	private String shnepiLinkUrl;
	/** 排序字段 */
	private Integer sortOrder;
	/** 创建者的用户ID */
	private String createBy;
	/** 创建时间=插入时间,保存到数据库中的时间 */
	private Date createTime;
	/** 修改者的用户ID */
	private String updateBy;
	/** 最后一次修改的时间 */
	private Date updateTime;
	/** 备注 */
	private String remarks;
//以上自动生成的尽量别动
	public void setWorkflowId(Long workflowId) 
	{
		this.workflowId = workflowId;
	}

	public Long getWorkflowId() 
	{
		return workflowId;
	}
	public void setWorkflowName(String workflowName) 
	{
		this.workflowName = workflowName;
	}

	public String getWorkflowName() 
	{
		return workflowName;
	}
	public void setWorkflowGroupId(Long workflowGroupId) 
	{
		this.workflowGroupId = workflowGroupId;
	}

	public Long getWorkflowGroupId() 
	{
		return workflowGroupId;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setWorkflowNodeNum(Integer workflowNodeNum) 
	{
		this.workflowNodeNum = workflowNodeNum;
	}

	public Integer getWorkflowNodeNum() 
	{
		return workflowNodeNum;
	}
	public void setWorkflowDesc(String workflowDesc) 
	{
		this.workflowDesc = workflowDesc;
	}

	public String getWorkflowDesc() 
	{
		return workflowDesc;
	}
	public void setShnepiLinkUrl(String shnepiLinkUrl) 
	{
		this.shnepiLinkUrl = shnepiLinkUrl;
	}

	public String getShnepiLinkUrl() 
	{
		return shnepiLinkUrl;
	}
	public void setSortOrder(Integer sortOrder) 
	{
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() 
	{
		return sortOrder;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowId", getWorkflowId())
            .append("workflowName", getWorkflowName())
            .append("workflowGroupId", getWorkflowGroupId())
            .append("status", getStatus())
            .append("workflowNodeNum", getWorkflowNodeNum())
            .append("workflowDesc", getWorkflowDesc())
            .append("shnepiLinkUrl", getShnepiLinkUrl())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
