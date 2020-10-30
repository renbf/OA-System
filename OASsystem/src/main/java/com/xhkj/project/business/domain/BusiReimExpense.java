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
public class BusiReimExpense extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long reimExpenseId;

    /** 报销id */
    @Excel(name = "报销id")
    private Long reimburseId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 部门Id */
    @Excel(name = "部门Id")
    private Long deptId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String reimExpenseName;

    /** 报销理由 */
    @Excel(name = "报销理由")
    private String reimburseReason;

    /** 流程Id */
    private Long workflowId;

    /** 金额 */
    @Excel(name = "金额")
    private Double amount;

    /** 票据数量 */
    @Excel(name = "票据数量")
    private Integer billsNum;


//以上自动生成的尽量别动


    /** 总金额 */
    private Double amountTotal;
    /** 附件id */
    private String fileIds;
    /** 附件数量 */
    private Integer fileNum;

    public Long getReimExpenseId() {
        return reimExpenseId;
    }

    public void setReimExpenseId(Long reimExpenseId) {
        this.reimExpenseId = reimExpenseId;
    }

    public String getReimExpenseName() {
        return reimExpenseName;
    }

    public void setReimExpenseName(String reimExpenseName) {
        this.reimExpenseName = reimExpenseName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getBillsNum() {
        return billsNum;
    }

    public void setBillsNum(Integer billsNum) {
        this.billsNum = billsNum;
    }

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

    public Long getReimburseId() { return reimburseId; }
    public void setReimburseId(Long reimburseId) { this.reimburseId = reimburseId; }

    @NotBlank(message = "用户id不能为空")
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @NotBlank(message = "部门Id不能为空")
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }


    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }


    public String getReimburseReason() { return reimburseReason; }
    public void setReimburseReason(String reimburseReason) { this.reimburseReason = reimburseReason; }


}
