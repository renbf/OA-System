package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.system.domain.Attachment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 出差补贴费用对象 busi_reim_travel_subsidy
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
    private Double subsidyAmount;

//以上自动生成的尽量别动

    /** 总金额 */
    private Double amountTotal;
    /** 附件id */
    private String fileIds;
    /** 附件数量 */
    private Integer fileNum;

    private List<Attachment> fileList;


    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getFileNum() {
        return fileNum;
    }

    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
    }

    public List<Attachment> getFileList() {
        return fileList;
    }

    public void setFileList(List<Attachment> fileList) {
        this.fileList = fileList;
    }

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

    public Double getSubsidyAmount() { return subsidyAmount; }
    public void setSubsidyAmount(Double subsidyAmount) { this.subsidyAmount = subsidyAmount; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("travelId", getTravelId())
            .append("reimburseId", getReimburseId())
            .append("traSubName", getTraSubName())
            .append("peopleNum", getPeopleNum())
            .append("days", getDays())
            .append("subsidyAmount", getSubsidyAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
