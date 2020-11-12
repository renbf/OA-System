package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiDailyTaskMapper;
import com.xhkj.project.business.domain.BusiDailyTask;
import com.xhkj.project.business.service.IBusiDailyTaskService;

/**
 * 日常任务Service业务层处理
 * 
 * @author xhkj
 * @date 2020-11-10
 */
@Service
public class BusiDailyTaskServiceImpl implements IBusiDailyTaskService 
{
    @Autowired
    private BusiDailyTaskMapper busiDailyTaskMapper;

    /**
     * 查询日常任务
     * 
     * @param dailyTaskId 日常任务ID
     * @return 日常任务
     */
    @Override
    public BusiDailyTask selectBusiDailyTaskById(Long dailyTaskId)
    {
        return busiDailyTaskMapper.selectBusiDailyTaskById(dailyTaskId);
    }

    /**
     * 查询日常任务列表
     * 
     * @param busiDailyTask 日常任务
     * @return 日常任务
     */
    @Override
    public List<BusiDailyTask> selectBusiDailyTaskList(BusiDailyTask busiDailyTask)
    {
        return busiDailyTaskMapper.selectBusiDailyTaskList(busiDailyTask);
    }

    /**
     * 新增日常任务
     * 
     * @param busiDailyTask 日常任务
     * @return 结果
     */
    @Override
    public int insertBusiDailyTask(BusiDailyTask busiDailyTask)
    {
        busiDailyTask.setCreateTime(DateUtils.getNowDate());
        return busiDailyTaskMapper.insertBusiDailyTask(busiDailyTask);
    }

    /**
     * 修改日常任务
     * 
     * @param busiDailyTask 日常任务
     * @return 结果
     */
    @Override
    public int updateBusiDailyTask(BusiDailyTask busiDailyTask)
    {
        busiDailyTask.setUpdateTime(DateUtils.getNowDate());
        return busiDailyTaskMapper.updateBusiDailyTask(busiDailyTask);
    }

    /**
     * 批量删除日常任务
     * 
     * @param dailyTaskIds 需要删除的日常任务ID
     * @return 结果
     */
    @Override
    public int deleteBusiDailyTaskByIds(Long[] dailyTaskIds)
    {
        return busiDailyTaskMapper.deleteBusiDailyTaskByIds(dailyTaskIds);
    }

    /**
     * 删除日常任务信息
     * 
     * @param dailyTaskId 日常任务ID
     * @return 结果
     */
    @Override
    public int deleteBusiDailyTaskById(Long dailyTaskId)
    {
        return busiDailyTaskMapper.deleteBusiDailyTaskById(dailyTaskId);
    }
}
