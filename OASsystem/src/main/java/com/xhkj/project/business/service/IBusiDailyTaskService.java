package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiDailyTask;

/**
 * 日常任务Service接口
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public interface IBusiDailyTaskService 
{
    /**
     * 查询日常任务
     * 
     * @param dailyTaskId 日常任务ID
     * @return 日常任务
     */
    public BusiDailyTask selectBusiDailyTaskById(Long dailyTaskId);

    /**
     * 查询日常任务列表
     * 
     * @param busiDailyTask 日常任务
     * @return 日常任务集合
     */
    public List<BusiDailyTask> selectBusiDailyTaskList(BusiDailyTask busiDailyTask);

    /**
     * 新增日常任务
     * 
     * @param busiDailyTask 日常任务
     * @return 结果
     */
    public int insertBusiDailyTask(BusiDailyTask busiDailyTask);

    /**
     * 修改日常任务
     * 
     * @param busiDailyTask 日常任务
     * @return 结果
     */
    public int updateBusiDailyTask(BusiDailyTask busiDailyTask);

    /**
     * 批量删除日常任务
     * 
     * @param dailyTaskIds 需要删除的日常任务ID
     * @return 结果
     */
    public int deleteBusiDailyTaskByIds(Long[] dailyTaskIds);

    /**
     * 删除日常任务信息
     * 
     * @param dailyTaskId 日常任务ID
     * @return 结果
     */
    public int deleteBusiDailyTaskById(Long dailyTaskId);
}
