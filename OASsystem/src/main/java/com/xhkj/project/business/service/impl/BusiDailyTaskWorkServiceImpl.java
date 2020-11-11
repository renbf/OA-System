package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiDailyTaskWorkMapper;
import com.xhkj.project.business.domain.BusiDailyTaskWork;
import com.xhkj.project.business.service.IBusiDailyTaskWorkService;

/**
 * 日常任务工作Service业务层处理
 * 
 * @author xhkj
 * @date 2020-11-10
 */
@Service
public class BusiDailyTaskWorkServiceImpl implements IBusiDailyTaskWorkService 
{
    @Autowired
    private BusiDailyTaskWorkMapper busiDailyTaskWorkMapper;

    /**
     * 查询日常任务工作
     * 
     * @param dailyTaskWorksId 日常任务工作ID
     * @return 日常任务工作
     */
    @Override
    public BusiDailyTaskWork selectBusiDailyTaskWorkById(Long dailyTaskWorksId)
    {
        return busiDailyTaskWorkMapper.selectBusiDailyTaskWorkById(dailyTaskWorksId);
    }

    /**
     * 查询日常任务工作列表
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 日常任务工作
     */
    @Override
    public List<BusiDailyTaskWork> selectBusiDailyTaskWorkList(BusiDailyTaskWork busiDailyTaskWork)
    {
        return busiDailyTaskWorkMapper.selectBusiDailyTaskWorkList(busiDailyTaskWork);
    }

    /**
     * 新增日常任务工作
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 结果
     */
    @Override
    public int insertBusiDailyTaskWork(BusiDailyTaskWork busiDailyTaskWork)
    {
        busiDailyTaskWork.setCreateTime(DateUtils.getNowDate());
        return busiDailyTaskWorkMapper.insertBusiDailyTaskWork(busiDailyTaskWork);
    }

    /**
     * 修改日常任务工作
     * 
     * @param busiDailyTaskWork 日常任务工作
     * @return 结果
     */
    @Override
    public int updateBusiDailyTaskWork(BusiDailyTaskWork busiDailyTaskWork)
    {
        busiDailyTaskWork.setUpdateTime(DateUtils.getNowDate());
        return busiDailyTaskWorkMapper.updateBusiDailyTaskWork(busiDailyTaskWork);
    }

    /**
     * 批量删除日常任务工作
     * 
     * @param dailyTaskWorksIds 需要删除的日常任务工作ID
     * @return 结果
     */
    @Override
    public int deleteBusiDailyTaskWorkByIds(Long[] dailyTaskWorksIds)
    {
        return busiDailyTaskWorkMapper.deleteBusiDailyTaskWorkByIds(dailyTaskWorksIds);
    }

    /**
     * 删除日常任务工作信息
     * 
     * @param dailyTaskWorksId 日常任务工作ID
     * @return 结果
     */
    @Override
    public int deleteBusiDailyTaskWorkById(Long dailyTaskWorksId)
    {
        return busiDailyTaskWorkMapper.deleteBusiDailyTaskWorkById(dailyTaskWorksId);
    }
}
