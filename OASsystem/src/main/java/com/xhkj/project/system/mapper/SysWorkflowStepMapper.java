package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflowStep;
import com.xhkj.project.system.domain.vo.SysWorkflowStepVo;

import java.util.List;
/**
 * 流程步骤定义 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface SysWorkflowStepMapper
{
	/**
     * 查询流程步骤定义信息
     * 
     * @param workflowStepId 流程步骤定义ID
     * @return 流程步骤定义信息
     */
	public SysWorkflowStep selectSysWorkflowStepById(Long workflowStepId);
	
	/**
     * 查询流程步骤定义列表
     * 
     * @param sysWorkflowStep 流程步骤定义信息
     * @return 流程步骤定义集合
     */
	public List<SysWorkflowStep> selectSysWorkflowStepList(SysWorkflowStep sysWorkflowStep);
	
	/**
     * 新增流程步骤定义
     * 
     * @param sysWorkflowStep 流程步骤定义信息
     * @return 结果
     */
	public int insertSysWorkflowStep(SysWorkflowStep sysWorkflowStep);
	
	/**
     * 修改流程步骤定义
     * 
     * @param sysWorkflowStep 流程步骤定义信息
     * @return 结果
     */
	public int updateSysWorkflowStep(SysWorkflowStep sysWorkflowStep);
	
	/**
     * 删除流程步骤定义
     * 
     * @param workflowStepId 流程步骤定义ID
     * @return 结果
     */
	public int deleteSysWorkflowStepById(Long workflowStepId);
	
	/**
     * 批量删除流程步骤定义
     * 
     * @param workflowStepIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowStepByIds(String[] workflowStepIds);

	/**
	 * 批量新增流程步骤定义
	 *
	 * @param list 流程步骤定义信息
	 * @return 结果
	 */
	public int insertSysWorkflowStepBatch(List<SysWorkflowStep> list);
	//以上自动生成的尽量别动

	public List<SysWorkflowStep> selectSysWorkflowStepsByWorkflowId(Long workflowId);

	public int deleteSysWorkflowStepByWorkflowId(Long workflowId);

	public List<SysWorkflowStepVo> selectSysWorkflowStepVosByWorkflowId(Long workflowId);
}