package com.xhkj.project.business.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.project.business.domain.BusiAskLeave;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BusiAskLeaveVo extends BusiAskLeave {
    private static final long serialVersionUID = 1L;


    /*加班剩余时长*/
    private Double overtimeSurTime;
    /*年休假剩余时长*/
    private Double annLeaSurTime;

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

}
