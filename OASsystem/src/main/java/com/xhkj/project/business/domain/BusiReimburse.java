package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

/**
 * 报销信息对象 busi_reimburse
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public class BusiReimburse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long reimburseId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 部门Id */
    @Excel(name = "部门Id")
    private Long deptId;

    /** 是否在项目中 */
    @Excel(name = "是否在项目中")
    private Boolean inPrjFlag;

    /** 项目id */
    @Excel(name = "项目id")
    private String projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 报销理由 */
    @Excel(name = "报销理由")
    private String reimburseReason;

    /** 流程Id */
    private Long workflowId;

//以上自动生成的尽量别动
    public Long getReimburseId() { return reimburseId; }
    public void setReimburseId(Long reimburseId) { this.reimburseId = reimburseId; }

    @NotBlank(message = "用户id不能为空")
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @NotBlank(message = "部门Id不能为空")
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public Boolean getInPrjFlag() {
        return inPrjFlag;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public void setInPrjFlag(Boolean inPrjFlag) {
        this.inPrjFlag = inPrjFlag;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getReimburseReason() { return reimburseReason; }
    public void setReimburseReason(String reimburseReason) { this.reimburseReason = reimburseReason; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reimburseId", getReimburseId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("inPrjFlag", getInPrjFlag())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("reimburseReason", getReimburseReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
