package com.xhkj.project.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.system.domain.Attachment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 交通费用对象 busi_reim_traffic_fee
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public class BusiReimTrafficFee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public BusiReimTrafficFee() {
    }

    public BusiReimTrafficFee(Long reimburseId) {
        this.reimburseId = reimburseId;
    }

    /** 主键id */
    private Long trafficId;

    /** 报销id */
    @Excel(name = "报销id")
    private Long reimburseId;

    /** 交通类型 */
    @Excel(name = "交通类型")
    private String trafficType;

    /** 时间 */
    @Excel(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trafficStartDate;

    /** 时间 */
    @Excel(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trafficEndDate;

    /** 起始位置 */
    @Excel(name = "起始位置")
    private String departureStation;

    /** 终点位置 */
    @Excel(name = "终点位置")
    private String terminalStation;

    /** 金额 */
    @Excel(name = "金额")
    private Double amount;

    /** 票据数量 */
    @Excel(name = "票据数量")
    private Integer billsNum;

    /** 总金额 */
    @Excel(name = "总金额")
    private Double amountTotal;

    /** 附件id */
    private String fileIds;

    private List<Attachment> fileList;

//以上自动生成的尽量别动
    public Long getTrafficId() { return trafficId; }
    public void setTrafficId(Long trafficId) { this.trafficId = trafficId; }

    public List<Attachment> getFileList() {
        return fileList;
    }

    public void setFileList(List<Attachment> fileList) {
        this.fileList = fileList;
    }

    @NotBlank(message = "报销id不能为空")
    public Long getReimburseId() { return reimburseId; }
    public void setReimburseId(Long reimburseId) { this.reimburseId = reimburseId; }

    public String getTrafficType() { return trafficType; }
    public void setTrafficType(String trafficType) { this.trafficType = trafficType; }

    public Date getTrafficStartDate() {
        return trafficStartDate;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public void setTrafficStartDate(Date trafficStartDate) {
        this.trafficStartDate = trafficStartDate;
    }

    public Date getTrafficEndDate() {
        return trafficEndDate;
    }

    public void setTrafficEndDate(Date trafficEndDate) {
        this.trafficEndDate = trafficEndDate;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getDepartureStation() { return departureStation; }
    public void setDepartureStation(String departureStation) { this.departureStation = departureStation; }

    public String getTerminalStation() { return terminalStation; }
    public void setTerminalStation(String terminalStation) { this.terminalStation = terminalStation; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Integer getBillsNum() { return billsNum; }
    public void setBillsNum(Integer billsNum) { this.billsNum = billsNum; }


}
