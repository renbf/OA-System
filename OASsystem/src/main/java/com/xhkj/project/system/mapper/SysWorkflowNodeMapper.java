package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflowNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 步骤节点流程审核 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface SysWorkflowNodeMapper
{
	/**
     * 查询步骤节点流程审核信息
     * 
     * @param workflowNodeId 步骤节点流程审核ID
     * @return 步骤节点流程审核信息
     */
	public SysWorkflowNode selectSysWorkflowNodeById(Long workflowNodeId);
	
	/**
     * 查询步骤节点流程审核列表
     * 
     * @param sysWorkflowNode 步骤节点流程审核信息
     * @return 步骤节点流程审核集合
     */
	public List<SysWorkflowNode> selectSysWorkflowNodeList(SysWorkflowNode sysWorkflowNode);
	
	/**
     * 新增步骤节点流程审核
     * 
     * @param sysWorkflowNode 步骤节点流程审核信息
     * @return 结果
     */
	public int insertSysWorkflowNode(SysWorkflowNode sysWorkflowNode);
	
	/**
     * 修改步骤节点流程审核
     * 
     * @param sysWorkflowNode 步骤节点流程审核信息
     * @return 结果
     */
	public int updateSysWorkflowNode(SysWorkflowNode sysWorkflowNode);
	
	/**
     * 删除步骤节点流程审核
     * 
     * @param workflowNodeId 步骤节点流程审核ID
     * @return 结果
     */
	public int deleteSysWorkflowNodeById(Long workflowNodeId);
	
	/**
     * 批量删除步骤节点流程审核
     * 
     * @param workflowNodeIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowNodeByIds(String[] workflowNodeIds);

	/**
	 * 批量新增步骤节点流程审核
	 *
	 * @param list 步骤节点流程审核信息
	 * @return 结果
	 */
	public int insertSysWorkflowNodeBatch(List<SysWorkflowNode> list);
	//以上自动生成的尽量别动
	public List<SysWorkflowNode> selectSysWorkflowNodesByStepNodeId(Long workflowStepNodeId);

	public SysWorkflowNode selectNextSysWorkflowNodeBySortOrder(@Param("workflowStepNodeId") Long workflowStepNodeId,@Param("sortOrder") Integer sortOrder);

	public SysWorkflowNode selectUpSysWorkflowNodeBySortOrder(@Param("workflowStepNodeId") Long workflowStepNodeId,@Param("sortOrder") Integer sortOrder);

	public int updateSysWorkflowNodeNextSort(SysWorkflowNode sysWorkflowNode);

}