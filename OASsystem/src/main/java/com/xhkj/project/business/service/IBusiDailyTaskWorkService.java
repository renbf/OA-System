package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiDailyTaskWork;

/**
 * 日常任务工作Service接口
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public interface IBusiDailyTaskWorkService 
{
    /**
     * 查询日常任务工作
     * 
     * @param dailyTaskWorksId 日常任务工作ID
     * @return 日常任务工作
     */
    public BusiDailyTaskWork selectBusiDailyTaskWorkById(Long dailyTaskWorksId);

    /**
     * 查询日常任务工作列表
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 日常任务工作集合
     */
    public List<BusiDailyTaskWork> selectBusiDailyTaskWorkList(BusiDailyTaskWork busiDailyTaskWork);

    /**
     * 新增日常任务工作
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 结果
     */
    public int insertBusiDailyTaskWork(BusiDailyTaskWork busiDailyTaskWork);

    /**
     * 修改日常任务工作
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 结果
     */
    public int updateBusiDailyTaskWork(BusiDailyTaskWork busiDailyTaskWork);

    /**
     * 批量删除日常任务工作
     * 
     * @param dailyTaskWorksIds 需要删除的日常任务工作ID
     * @return 结果
     */
    public int deleteBusiDailyTaskWorkByIds(Long[] dailyTaskWorksIds);

    /**
     * 删除日常任务工作信息
     * 
     * @param dailyTaskWorksId 日常任务工作ID
     * @return 结果
     */
    public int deleteBusiDailyTaskWorkById(Long dailyTaskWorksId);
}
