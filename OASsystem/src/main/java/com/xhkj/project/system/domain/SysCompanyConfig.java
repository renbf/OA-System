package com.xhkj.project.system.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 工时配置对象 sys_work_hour_config
 * 
 * @author xhkj
 * @date 2020-05-28
 */
public class SysCompanyConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public SysCompanyConfig() {
    }

    public SysCompanyConfig(String comConfigKey) {
        this.comConfigKey = comConfigKey;
    }

    /** 参数主键 */
    private Long comConfigId;

    /** 公司参数名称 */
    @Excel(name = "公司参数名称")
    private String comConfigName;

    /** 公司参数键名 */
    @Excel(name = "公司参数键名")
    private String comConfigKey;

    /** 公司参数键值 */
    @Excel(name = "公司参数键值")
    private String comConfigValue;


    public Long getComConfigId() { return comConfigId; }
    public void setComConfigId(Long comConfigId) { this.comConfigId = comConfigId; }

    @NotBlank(message = "公司参数名称不能为空")
    @Size(min = 0, max = 100, message = "公司参数名称不能超过100个字符")
    public String getComConfigName() {
        return comConfigName;
    }

    public void setComConfigName(String comConfigName) {
        this.comConfigName = comConfigName;
    }

    @NotBlank(message = "公司参数键名不能为空")
    @Size(min = 0, max = 100, message = "公司参数键名不能超过100个字符")
    public String getComConfigKey() {
        return comConfigKey;
    }

    public void setComConfigKey(String comConfigKey) {
        this.comConfigKey = comConfigKey;
    }

    @NotBlank(message = "公司参数键值不能为空")
    @Size(min = 0, max = 500, message = "公司参数键值不能超过100个字符")
    public String getComConfigValue() {
        return comConfigValue;
    }

    public void setComConfigValue(String comConfigValue) {
        this.comConfigValue = comConfigValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("companyConfigId", getComConfigId())
            .append("comConfigName", getComConfigName())
            .append("comConfigKey", getComConfigKey())
            .append("comConfigValue", getComConfigValue())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
