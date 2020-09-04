package com.xhkj.project.business.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BusiAskLeaveVo {
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

    /** 请假时间 */
    @Excel(name = "请假时间")
    private String leaveDates;

    /** 请假理由 */
    @Excel(name = "请假理由")
    private String leaveReason;

    /** 部门Id */
    @Excel(name = "部门")
    private Long deptId;

    /** 请假项目 */
    @Excel(name = "请假项目")
    private String leavePrjName;

    /*加班剩余时长*/
    private Double overtimeSurTime;
    /*年休假剩余时长*/
    private Double annLeaSurTime;

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

    /*判断是保存还是提交*/
    private Boolean saveFlag;

    /*是否在项目中*/
    private Boolean inPrjFlag;

    public Boolean getInPrjFlag() {
        return inPrjFlag;
    }

    public void setInPrjFlag(Boolean inPrjFlag) {
        this.inPrjFlag = inPrjFlag;
    }

    public Boolean getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
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
    @NotBlank(message = "请假理由不能为空")
    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

}
