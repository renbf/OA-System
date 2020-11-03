package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 请假倒休对象 busi_ask_leave
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public class BusiAskLeave extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long leaveId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 请假类型:1,调休,2,年休,3,请假 */
    @Excel(name = "请假类型")
    private String leaveType;

    @Excel(name = "请假时长")
    private Double leaveHours;

    @Excel(name = "请假时长单位")
    private String leaveHoursUnit;

    /** 请假时间 */
    @Excel(name = "请假时间")
    private String leaveDates;

    /** 请假原因 */
    @Excel(name = "请假原因")
    private String leaveReason;

    /** 部门Id */
    @Excel(name = "部门")
    private Long deptId;

    /** 请假项目 */
    @Excel(name = "请假项目")
    private String leavePrjName;

    /*是否在项目中*/
    private Boolean inPrjFlag;

    /*审核状态*/
    @Excel(name = "审核状态")
    private String approvalStatus;

    /*当前审批人*/
    @Excel(name = "当前审批人")
    private String curApprover;

    /*加班剩余时长*/
    private Double overtimeSurTime;

    /*年休假剩余时长*/
    private Double annLeaSurTime;

    /*请假开始时间*/
    private String leaveBeginTime;
    /*请假结束时间*/
    private String leaveEndTime;

    /** 流程Id */
    private Long workflowId;

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getLeaveHoursUnit() {
        return leaveHoursUnit;
    }

    public void setLeaveHoursUnit(String leaveHoursUnit) {
        this.leaveHoursUnit = leaveHoursUnit;
    }

    public String getLeaveBeginTime() {
        return leaveBeginTime;
    }

    public void setLeaveBeginTime(String leaveBeginTime) {
        this.leaveBeginTime = leaveBeginTime;
    }

    public String getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(String leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public Double getOvertimeSurTime() {
        return overtimeSurTime;
    }

    public void setOvertimeSurTime(Double overtimeSurTime) {
        this.overtimeSurTime = overtimeSurTime;
    }

    public Double getAnnLeaSurTime() {
        return annLeaSurTime;
    }

    public void setAnnLeaSurTime(Double annLeaSurTime) {
        this.annLeaSurTime = annLeaSurTime;
    }


    public String getCurApprover() {

        return curApprover;
    }

    public void setCurApprover(String curApprover) {
        this.curApprover = curApprover;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Boolean getInPrjFlag() {
        return inPrjFlag;
    }

    public void setInPrjFlag(Boolean inPrjFlag) {
        this.inPrjFlag = inPrjFlag;
    }

    public String getLeavePrjName() {
        return leavePrjName;
    }

    public void setLeavePrjName(String leavePrjName) {
        this.leavePrjName = leavePrjName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getLeaveId() { return leaveId; }
    public void setLeaveId(Long leaveId) { this.leaveId = leaveId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @NotBlank(message = "请假类型不能为空")
    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public Double getLeaveHours() {
        return leaveHours;
    }

    public void setLeaveHours(Double leaveHours) {
        this.leaveHours = leaveHours;
    }

    @NotBlank(message = "请假日期不能为空")
    public String getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(String leaveDates) {
        this.leaveDates = leaveDates;
    }
    @NotBlank(message = "请假原因不能为空")
    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("leaveId", getLeaveId())
            .append("userId", getUserId())
            .append("leaveType", getLeaveType())
            .append("leaveHours",getLeaveHours())
            .append("leaveDates",getLeaveDates())
            .append("leaveReason",getLeaveReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("deptId", getDeptId())
            .toString();
    }
}
