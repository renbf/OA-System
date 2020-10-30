package com.xhkj.project.business.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 报销信息对象 busi_reimburse
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public class BusiReimburseVo extends BaseEntity
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

    //报销类型
    private String reimburseType;

//以上自动生成的尽量别动
    //总金额
    private Double amountTotal;

    /** 交通费用 list */
    private List<BusiReimTrafficFee> busiReimTrafficFeeList;
    /** 出差补助费用 list */
    private List<BusiReimTravelSubsidy> busiReimTravelSubsidyList;
    /** 其他费用 list */
    private List<BusiReimOtherFee> busiReimOtherFeeList;


    public String getReimburseType() {
        return reimburseType;
    }

    public void setReimburseType(String reimburseType) {
        this.reimburseType = reimburseType;
    }


    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public List<BusiReimTrafficFee> getBusiReimTrafficFeeList() {
        return busiReimTrafficFeeList;
    }

    public void setBusiReimTrafficFeeList(List<BusiReimTrafficFee> busiReimTrafficFeeList) {
        this.busiReimTrafficFeeList = busiReimTrafficFeeList;
    }

    public List<BusiReimTravelSubsidy> getBusiReimTravelSubsidyList() {
        return busiReimTravelSubsidyList;
    }

    public void setBusiReimTravelSubsidyList(List<BusiReimTravelSubsidy> busiReimTravelSubsidyList) {
        this.busiReimTravelSubsidyList = busiReimTravelSubsidyList;
    }

    public List<BusiReimOtherFee> getBusiReimOtherFeeList() {
        return busiReimOtherFeeList;
    }

    public void setBusiReimOtherFeeList(List<BusiReimOtherFee> busiReimOtherFeeList) {
        this.busiReimOtherFeeList = busiReimOtherFeeList;
    }

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
