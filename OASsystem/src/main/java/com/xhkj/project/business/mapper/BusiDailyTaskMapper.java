package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiDailyTask;

/**
 * 日常任务Mapper接口
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public interface BusiDailyTaskMapper 
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
     * 删除日常任务
     * 
     * @param dailyTaskId 日常任务ID
     * @return 结果
     */
    public int deleteBusiDailyTaskById(Long dailyTaskId);

    /**
     * 批量删除日常任务
     * 
     * @param dailyTaskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiDailyTaskByIds(Long[] dailyTaskIds);

    /**
     * 批量新增日常任务
     *
     * @param list 日常任务信息
     * @return 结果
     */
    public int insertBusiDailyTaskBatch(List<BusiDailyTask> list);
    //以上自动生成的尽量别动
}
