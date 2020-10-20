package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiTaskLog;
import java.util.List;	
/**
 * 任务日志 数据层
 * 
 * @author rbf
 * @date 2020-10-20
 */
public interface BusiTaskLogMapper
{
	/**
     * 查询任务日志信息
     * 
     * @param taskLogId 任务日志ID
     * @return 任务日志信息
     */
	public BusiTaskLog selectBusiTaskLogById(Long taskLogId);
	
	/**
     * 查询任务日志列表
     * 
     * @param busiTaskLog 任务日志信息
     * @return 任务日志集合
     */
	public List<BusiTaskLog> selectBusiTaskLogList(BusiTaskLog busiTaskLog);
	
	/**
     * 新增任务日志
     * 
     * @param busiTaskLog 任务日志信息
     * @return 结果
     */
	public int insertBusiTaskLog(BusiTaskLog busiTaskLog);
	
	/**
     * 修改任务日志
     * 
     * @param busiTaskLog 任务日志信息
     * @return 结果
     */
	public int updateBusiTaskLog(BusiTaskLog busiTaskLog);
	
	/**
     * 删除任务日志
     * 
     * @param taskLogId 任务日志ID
     * @return 结果
     */
	public int deleteBusiTaskLogById(Long taskLogId);
	
	/**
     * 批量删除任务日志
     * 
     * @param taskLogIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiTaskLogByIds(String[] taskLogIds);

	/**
	 * 批量新增任务日志
	 *
	 * @param list 任务日志信息
	 * @return 结果
	 */
	public int insertBusiTaskLogBatch(List<BusiTaskLog> list);
	//以上自动生成的尽量别动
}