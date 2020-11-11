package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日常任务工作对象 busi_daily_task_work
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public class BusiDailyTaskWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long dailyTaskWorksId;

    /** 日常任务id */
    @Excel(name = "日常任务id")
    private Long dailyTaskId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String workContent;

    /** 文件id */
    @Excel(name = "文件id")
    private String fileIds;

//以上自动生成的尽量别动
    public Long getDailyTaskWorksId() { return dailyTaskWorksId; }
    public void setDailyTaskWorksId(Long dailyTaskWorksId) { this.dailyTaskWorksId = dailyTaskWorksId; }

    public Long getDailyTaskId() { return dailyTaskId; }
    public void setDailyTaskId(Long dailyTaskId) { this.dailyTaskId = dailyTaskId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getWorkContent() { return workContent; }
    public void setWorkContent(String workContent) { this.workContent = workContent; }

    public String getFileIds() { return fileIds; }
    public void setFileIds(String fileIds) { this.fileIds = fileIds; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dailyTaskWorksId", getDailyTaskWorksId())
            .append("dailyTaskId", getDailyTaskId())
            .append("userId", getUserId())
            .append("workContent", getWorkContent())
            .append("fileIds", getFileIds())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
