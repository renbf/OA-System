package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 加班对象 busi_extra_work
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public class BusiExtraWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long extraWorkId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    @Excel(name = "加班时长")
    private Double extraWorkHours;

    @Excel(name = "加班时长单位")
    private String extraHoursUnit;

    /** 加班时间 */
    @Excel(name = "加班时间")
    private String extraWorkDates;

    /** 理由陈述 */
    @Excel(name = "理由陈述")
    private String extraWorkReason;

    /** 部门Id */
    @Excel(name = "部门")
    private Long deptId;

    /** 请假项目 */
    @Excel(name = "加班项目")
    private String extraWorkPrjName;

    /*是否在项目中*/
    private Boolean inPrjFlag;

    /*审核状态*/
    @Excel(name = "审核状态")
    private String approvalStatus;

    /*当前审批人*/
    @Excel(name = "当前审批人")
    private String curApprover;

    /*剩余加班时长*/
    @Excel(name = "剩余加班时长")
    private String overtimeSurTime;


    public String getExtraHoursUnit() {
        return extraHoursUnit;
    }

    public void setExtraHoursUnit(String extraHoursUnit) {
        this.extraHoursUnit = extraHoursUnit;
    }

    public String getOvertimeSurTime() {
        return overtimeSurTime;
    }

    public void setOvertimeSurTime(String overtimeSurTime) {
        this.overtimeSurTime = overtimeSurTime;
    }

    public Long getExtraWorkId() {
        return extraWorkId;
    }

    public void setExtraWorkId(Long extraWorkId) {
        this.extraWorkId = extraWorkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getExtraWorkHours() {
        return extraWorkHours;
    }

    public void setExtraWorkHours(Double extraWorkHours) {
        this.extraWorkHours = extraWorkHours;
    }

    public String getExtraWorkDates() {
        return extraWorkDates;
    }

    public void setExtraWorkDates(String extraWorkDates) {
        this.extraWorkDates = extraWorkDates;
    }

    public String getExtraWorkReason() {
        return extraWorkReason;
    }

    public void setExtraWorkReason(String extraWorkReason) {
        this.extraWorkReason = extraWorkReason;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getExtraWorkPrjName() {
        return extraWorkPrjName;
    }

    public void setExtraWorkPrjName(String extraWorkPrjName) {
        this.extraWorkPrjName = extraWorkPrjName;
    }

    public Boolean getInPrjFlag() {
        return inPrjFlag;
    }

    public void setInPrjFlag(Boolean inPrjFlag) {
        this.inPrjFlag = inPrjFlag;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCurApprover() {
        return curApprover;
    }

    public void setCurApprover(String curApprover) {
        this.curApprover = curApprover;
    }
}
