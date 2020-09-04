package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.WorkflowBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 流程单(所有流程单的父) 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface WorkflowBillMapper
{
	/**
     * 查询流程单(所有流程单的父)信息
     * 
     * @param billId 流程单(所有流程单的父)ID
     * @return 流程单(所有流程单的父)信息
     */
	public WorkflowBill selectWorkflowBillById(Long billId);
	
	/**
     * 查询流程单(所有流程单的父)列表
     * 
     * @param workflowBill 流程单(所有流程单的父)信息
     * @return 流程单(所有流程单的父)集合
     */
	public List<WorkflowBill> selectWorkflowBillList(WorkflowBill workflowBill);
	
	/**
     * 新增流程单(所有流程单的父)
     * 
     * @param workflowBill 流程单(所有流程单的父)信息
     * @return 结果
     */
	public int insertWorkflowBill(WorkflowBill workflowBill);
	
	/**
     * 修改流程单(所有流程单的父)
     * 
     * @param workflowBill 流程单(所有流程单的父)信息
     * @return 结果
     */
	public int updateWorkflowBill(WorkflowBill workflowBill);
	
	/**
     * 删除流程单(所有流程单的父)
     * 
     * @param billId 流程单(所有流程单的父)ID
     * @return 结果
     */
	public int deleteWorkflowBillById(Long billId);
	
	/**
     * 批量删除流程单(所有流程单的父)
     * 
     * @param billIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkflowBillByIds(String[] billIds);

	/**
	 * 批量新增流程单(所有流程单的父)
	 *
	 * @param list 流程单(所有流程单的父)信息
	 * @return 结果
	 */
	public int insertWorkflowBillBatch(List<WorkflowBill> list);
	//以上自动生成的尽量别动

	public WorkflowBill selectWorkflowBillByKey(@Param("billId") Long billId,@Param("workflowId") Long workflowId);

	public WorkflowBill selectNextNode(@Param("workflowId") Long workflowId,@Param("workflowNodeId") Long workflowNodeId);

    public int updateWorkflowBillByKey(WorkflowBill workflowBill);
}