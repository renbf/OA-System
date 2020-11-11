package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiDailyTaskWork;

/**
 * 日常任务工作Mapper接口
 * 
 * @author xhkj
 * @date 2020-11-10
 */
public interface BusiDailyTaskWorkMapper 
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
     * 删除日常任务工作
     * 
     * @param dailyTaskWorksId 日常任务工作ID
     * @return 结果
     */
    public int deleteBusiDailyTaskWorkById(Long dailyTaskWorksId);

    /**
     * 批量删除日常任务工作
     * 
     * @param dailyTaskWorksIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiDailyTaskWorkByIds(Long[] dailyTaskWorksIds);

    /**
     * 批量新增日常任务工作
     *
     * @param list 日常任务工作信息
     * @return 结果
     */
    public int insertBusiDailyTaskWorkBatch(List<BusiDailyTaskWork> list);
    //以上自动生成的尽量别动
}
