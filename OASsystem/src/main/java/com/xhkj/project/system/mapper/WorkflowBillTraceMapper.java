package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.WorkflowBillTrace;
import java.util.List;	
/**
 * 追踪审核单审批 数据层
 * 
 * @author rbf
 * @date 2020-04-17
 */
public interface WorkflowBillTraceMapper
{
	/**
     * 查询追踪审核单审批信息
     * 
     * @param checkId 追踪审核单审批ID
     * @return 追踪审核单审批信息
     */
	public WorkflowBillTrace selectWorkflowBillTraceById(Long checkId);
	
	/**
     * 查询追踪审核单审批列表
     * 
     * @param workflowBillTrace 追踪审核单审批信息
     * @return 追踪审核单审批集合
     */
	public List<WorkflowBillTrace> selectWorkflowBillTraceList(WorkflowBillTrace workflowBillTrace);
	
	/**
     * 新增追踪审核单审批
     * 
     * @param workflowBillTrace 追踪审核单审批信息
     * @return 结果
     */
	public int insertWorkflowBillTrace(WorkflowBillTrace workflowBillTrace);
	
	/**
     * 修改追踪审核单审批
     * 
     * @param workflowBillTrace 追踪审核单审批信息
     * @return 结果
     */
	public int updateWorkflowBillTrace(WorkflowBillTrace workflowBillTrace);
	
	/**
     * 删除追踪审核单审批
     * 
     * @param checkId 追踪审核单审批ID
     * @return 结果
     */
	public int deleteWorkflowBillTraceById(Long checkId);
	
	/**
     * 批量删除追踪审核单审批
     * 
     * @param checkIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWorkflowBillTraceByIds(String[] checkIds);

	/**
	 * 批量新增追踪审核单审批
	 *
	 * @param list 追踪审核单审批信息
	 * @return 结果
	 */
	public int insertWorkflowBillTraceBatch(List<WorkflowBillTrace> list);
	//以上自动生成的尽量别动
}