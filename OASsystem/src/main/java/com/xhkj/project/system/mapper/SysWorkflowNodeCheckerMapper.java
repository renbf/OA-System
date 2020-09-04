package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflowNodeChecker;
import java.util.List;	
/**
 * 流程审核步骤对应的审核人 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface SysWorkflowNodeCheckerMapper
{
	/**
     * 查询流程审核步骤对应的审核人信息
     * 
     * @param workflowNodeCheckerId 流程审核步骤对应的审核人ID
     * @return 流程审核步骤对应的审核人信息
     */
	public SysWorkflowNodeChecker selectSysWorkflowNodeCheckerById(Long workflowNodeCheckerId);
	
	/**
     * 查询流程审核步骤对应的审核人列表
     * 
     * @param sysWorkflowNodeChecker 流程审核步骤对应的审核人信息
     * @return 流程审核步骤对应的审核人集合
     */
	public List<SysWorkflowNodeChecker> selectSysWorkflowNodeCheckerList(SysWorkflowNodeChecker sysWorkflowNodeChecker);
	
	/**
     * 新增流程审核步骤对应的审核人
     * 
     * @param sysWorkflowNodeChecker 流程审核步骤对应的审核人信息
     * @return 结果
     */
	public int insertSysWorkflowNodeChecker(SysWorkflowNodeChecker sysWorkflowNodeChecker);
	
	/**
     * 修改流程审核步骤对应的审核人
     * 
     * @param sysWorkflowNodeChecker 流程审核步骤对应的审核人信息
     * @return 结果
     */
	public int updateSysWorkflowNodeChecker(SysWorkflowNodeChecker sysWorkflowNodeChecker);
	
	/**
     * 删除流程审核步骤对应的审核人
     * 
     * @param workflowNodeCheckerId 流程审核步骤对应的审核人ID
     * @return 结果
     */
	public int deleteSysWorkflowNodeCheckerById(Long workflowNodeCheckerId);
	
	/**
     * 批量删除流程审核步骤对应的审核人
     * 
     * @param workflowNodeCheckerIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowNodeCheckerByIds(String[] workflowNodeCheckerIds);

	/**
	 * 批量新增流程审核步骤对应的审核人
	 *
	 * @param list 流程审核步骤对应的审核人信息
	 * @return 结果
	 */
	public int insertSysWorkflowNodeCheckerBatch(List<SysWorkflowNodeChecker> list);
	//以上自动生成的尽量别动

	public int deleteSysWorkflowNodeCheckerByNodeId(Long workflowNodeId);

	public List<SysWorkflowNodeChecker> getSysWorkflowNodeCheckersByWorkflowNodeId(Long workflowNodeId);

}