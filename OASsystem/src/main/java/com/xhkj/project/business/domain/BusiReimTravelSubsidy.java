package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

/**
 * 交通费用对象 busi_reim_travel_subsidy
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public class BusiReimTravelSubsidy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public BusiReimTravelSubsidy() {
    }

    public BusiReimTravelSubsidy(Long reimburseId) {
        this.reimburseId = reimburseId;
    }

    /** 主键id */
    private Long travelId;

    /** 报销id */
    @Excel(name = "报销id")
    private Long reimburseId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String traSubName;

    /** 人数 */
    @Excel(name = "人数")
    private Integer peopleNum;

    /** 天数 */
    @Excel(name = "天数")
    private Integer days;

    /** 补贴标准 */
    @Excel(name = "补贴标准")
    private Double subsidyStandard;

//以上自动生成的尽量别动
    public Long getTravelId() { return travelId; }
    public void setTravelId(Long travelId) { this.travelId = travelId; }

    @NotBlank(message = "报销id不能为空")
    public Long getReimburseId() { return reimburseId; }
    public void setReimburseId(Long reimburseId) { this.reimburseId = reimburseId; }

    public String getTraSubName() { return traSubName; }
    public void setTraSubName(String traSubName) { this.traSubName = traSubName; }

    public Integer getPeopleNum() { return peopleNum; }
    public void setPeopleNum(Integer peopleNum) { this.peopleNum = peopleNum; }

    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }

    public Double getSubsidyStandard() { return subsidyStandard; }
    public void setSubsidyStandard(Double subsidyStandard) { this.subsidyStandard = subsidyStandard; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("travelId", getTravelId())
            .append("reimburseId", getReimburseId())
            .append("traSubName", getTraSubName())
            .append("peopleNum", getPeopleNum())
            .append("days", getDays())
            .append("subsidyStandard", getSubsidyStandard())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
