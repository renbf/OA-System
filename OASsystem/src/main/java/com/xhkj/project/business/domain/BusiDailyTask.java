package com.xhkj.project.business.domain;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日常任务对象 busi_daily_task
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public class BusiDailyTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long dailyTaskId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 标题 */
    @Excel(name = "标题")
    private String taskTitle;

    /** 参与部门 */
    @Excel(name = "参与部门")
    private String joinDepts;

    /** 参与人员 */
    @Excel(name = "参与人员")
    private String joinUsers;

    /** 任务时间 */
    @Excel(name = "任务时间")
    private String taskTime;

    /** 任务内容 */
    @Excel(name = "任务内容")
    private String taskText;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Boolean taskStatus;

//以上自动生成的尽量别动
    public Long getDailyTaskId() { return dailyTaskId; }
    public void setDailyTaskId(Long dailyTaskId) { this.dailyTaskId = dailyTaskId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTaskTitle() { return taskTitle; }
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }

    public String getJoinDepts() { return joinDepts; }
    public void setJoinDepts(String joinDepts) { this.joinDepts = joinDepts; }

    public String getJoinUsers() { return joinUsers; }
    public void setJoinUsers(String joinUsers) { this.joinUsers = joinUsers; }

    public String getTaskTime() { return taskTime; }
    public void setTaskTime(String taskTime) { this.taskTime = taskTime; }

    public String getTaskText() { return taskText; }
    public void setTaskText(String taskText) { this.taskText = taskText; }

    public Boolean getTaskStatus() { return taskStatus; }
    public void setTaskStatus(Boolean taskStatus) { this.taskStatus = taskStatus; }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dailyTaskId", getDailyTaskId())
            .append("userId", getUserId())
            .append("taskTitle", getTaskTitle())
            .append("joinDepts", getJoinDepts())
            .append("joinUsers", getJoinUsers())
            .append("taskTime", getTaskTime())
            .append("taskText", getTaskText())
            .append("taskStatus", getTaskStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
