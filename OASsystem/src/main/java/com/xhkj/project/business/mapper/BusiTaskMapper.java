package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiTask;
import com.xhkj.project.business.domain.vo.BusiTaskVo;

import java.util.List;
/**
 * 任务 数据层
 * 
 * @author rbf
 * @date 2020-09-30
 */
public interface BusiTaskMapper
{
	/**
     * 查询任务信息
     * 
     * @param taskId 任务ID
     * @return 任务信息
     */
	public BusiTask selectBusiTaskById(Long taskId);
	
	/**
     * 查询任务列表
     * 
     * @param busiTask 任务信息
     * @return 任务集合
     */
	public List<BusiTask> selectBusiTaskList(BusiTask busiTask);

	/**
     * 新增任务
     * 
     * @param busiTask 任务信息
     * @return 结果
     */
	public int insertBusiTask(BusiTask busiTask);
	
	/**
     * 修改任务
     * 
     * @param busiTask 任务信息
     * @return 结果
     */
	public int updateBusiTask(BusiTask busiTask);
	
	/**
     * 删除任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
	public int deleteBusiTaskById(Long taskId);
	
	/**
     * 批量删除任务
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiTaskByIds(String[] taskIds);

	/**
	 * 批量新增任务
	 *
	 * @param list 任务信息
	 * @return 结果
	 */
	public int insertBusiTaskBatch(List<BusiTask> list);
	//以上自动生成的尽量别动

	public List<BusiTaskVo> selectBusiTasks(BusiTaskVo busiTaskVo);

	public BusiTaskVo selectBusiTaskByTaskId(Long taskId);
}