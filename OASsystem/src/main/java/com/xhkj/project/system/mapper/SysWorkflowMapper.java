package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflow;
import java.util.List;	
/**
 * 流程 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface SysWorkflowMapper
{
	/**
     * 查询流程信息
     * 
     * @param workflowId 流程ID
     * @return 流程信息
     */
	public SysWorkflow selectSysWorkflowById(Long workflowId);
	
	/**
     * 查询流程列表
     * 
     * @param sysWorkflow 流程信息
     * @return 流程集合
     */
	public List<SysWorkflow> selectSysWorkflowList(SysWorkflow sysWorkflow);
	
	/**
     * 新增流程
     * 
     * @param sysWorkflow 流程信息
     * @return 结果
     */
	public int insertSysWorkflow(SysWorkflow sysWorkflow);
	
	/**
     * 修改流程
     * 
     * @param sysWorkflow 流程信息
     * @return 结果
     */
	public int updateSysWorkflow(SysWorkflow sysWorkflow);
	
	/**
     * 删除流程
     * 
     * @param workflowId 流程ID
     * @return 结果
     */
	public int deleteSysWorkflowById(Long workflowId);
	
	/**
     * 批量删除流程
     * 
     * @param workflowIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowByIds(String[] workflowIds);

	/**
	 * 批量新增流程
	 *
	 * @param list 流程信息
	 * @return 结果
	 */
	public int insertSysWorkflowBatch(List<SysWorkflow> list);
	//以上自动生成的尽量别动

    public int checkWorkBillIsUsed(Long workflowId);

	public int checkWorkNodeIsUsed(Long workflowNodeId);

	public List<SysWorkflow> selectSysWorkflows(SysWorkflow sysWorkflow);
}