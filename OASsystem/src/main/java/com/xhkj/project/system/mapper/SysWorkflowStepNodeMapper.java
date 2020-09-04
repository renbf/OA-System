package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflowStepNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 流程步骤节点 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface SysWorkflowStepNodeMapper
{
	/**
     * 查询流程步骤节点信息
     * 
     * @param workflowStepNodeId 流程步骤节点ID
     * @return 流程步骤节点信息
     */
	public SysWorkflowStepNode selectSysWorkflowStepNodeById(Long workflowStepNodeId);
	
	/**
     * 查询流程步骤节点列表
     * 
     * @param sysWorkflowStepNode 流程步骤节点信息
     * @return 流程步骤节点集合
     */
	public List<SysWorkflowStepNode> selectSysWorkflowStepNodeList(SysWorkflowStepNode sysWorkflowStepNode);
	
	/**
     * 新增流程步骤节点
     * 
     * @param sysWorkflowStepNode 流程步骤节点信息
     * @return 结果
     */
	public int insertSysWorkflowStepNode(SysWorkflowStepNode sysWorkflowStepNode);
	
	/**
     * 修改流程步骤节点
     * 
     * @param sysWorkflowStepNode 流程步骤节点信息
     * @return 结果
     */
	public int updateSysWorkflowStepNode(SysWorkflowStepNode sysWorkflowStepNode);
	
	/**
     * 删除流程步骤节点
     * 
     * @param workflowStepNodeId 流程步骤节点ID
     * @return 结果
     */
	public int deleteSysWorkflowStepNodeById(Long workflowStepNodeId);
	
	/**
     * 批量删除流程步骤节点
     * 
     * @param workflowStepNodeIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowStepNodeByIds(String[] workflowStepNodeIds);

	/**
	 * 批量新增流程步骤节点
	 *
	 * @param list 流程步骤节点信息
	 * @return 结果
	 */
	public int insertSysWorkflowStepNodeBatch(List<SysWorkflowStepNode> list);
	//以上自动生成的尽量别动

	public int deleteSysWorkflowStepNodeByStepId(Long workflowStepId);

	public List<SysWorkflowStepNode> selectSysWorkflowStepNodesByStepId(Long workflowStepId);

	public SysWorkflowStepNode selectSysWorkflowStepNodeByStepId(@Param("workflowStepId") Long workflowStepId);

}