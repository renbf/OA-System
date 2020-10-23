package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

/**
 * 交通费用对象 busi_reim_other_fee
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public class BusiReimOtherFee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public BusiReimOtherFee() {
    }

    public BusiReimOtherFee(Long reimburseId) {
        this.reimburseId = reimburseId;
    }

    /** 主键id */
    private Long otherId;

    /** 报销id */
    @Excel(name = "报销id")
    private Long reimburseId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String otherFeeName;

    /** 金额 */
    @Excel(name = "金额")
    private Double amount;

//以上自动生成的尽量别动
    public Long getOtherId() { return otherId; }
    public void setOtherId(Long otherId) { this.otherId = otherId; }

    @NotBlank(message = "报销id不能为空")
    public Long getReimburseId() { return reimburseId; }
    public void setReimburseId(Long reimburseId) { this.reimburseId = reimburseId; }

    public String getOtherFeeName() { return otherFeeName; }
    public void setOtherFeeName(String otherFeeName) { this.otherFeeName = otherFeeName; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("otherId", getOtherId())
            .append("reimburseId", getReimburseId())
            .append("otherFeeName", getOtherFeeName())
            .append("amount", getAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
