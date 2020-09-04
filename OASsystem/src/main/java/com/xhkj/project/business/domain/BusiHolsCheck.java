package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 年假调休核减对象 busi_hols_check
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public class BusiHolsCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long userId;

    /** 调休剩余时长 */
    @Excel(name = "调休剩余时长")
    private BigDecimal leaveRest;

    /** 年假剩余时长 */
    @Excel(name = "年假剩余时长")
    private BigDecimal holsRestDays;

    /** $column.columnComment */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /*调休单位*/
    private String leaveRestUnit;

    public String getLeaveRestUnit() {
        return leaveRestUnit;
    }

    public void setLeaveRestUnit(String leaveRestUnit) {
        this.leaveRestUnit = leaveRestUnit;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @NotBlank(message = "调休剩余时长不能为空")
    public BigDecimal getLeaveRest() { return leaveRest; }
    public void setLeaveRest(BigDecimal leaveRest) { this.leaveRest = leaveRest; }

    @NotBlank(message = "年假剩余时长不能为空")
    public BigDecimal getHolsRestDays() { return holsRestDays; }
    public void setHolsRestDays(BigDecimal holsRestDays) { this.holsRestDays = holsRestDays; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("leaveRest", getLeaveRest())
            .append("holsRestDays", getHolsRestDays())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
