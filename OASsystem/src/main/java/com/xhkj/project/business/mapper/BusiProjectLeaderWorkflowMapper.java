package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiProjectLeaderWorkflow;
import java.util.List;	
/**
 * 项目负责人审批 数据层
 * 
 * @author rbf
 * @date 2020-11-18
 */
public interface BusiProjectLeaderWorkflowMapper
{
	/**
     * 查询项目负责人审批信息
     * 
     * @param billId 项目负责人审批ID
     * @return 项目负责人审批信息
     */
	public BusiProjectLeaderWorkflow selectBusiProjectLeaderWorkflowById(Long billId);
	
	/**
     * 查询项目负责人审批列表
     * 
     * @param busiProjectLeaderWorkflow 项目负责人审批信息
     * @return 项目负责人审批集合
     */
	public List<BusiProjectLeaderWorkflow> selectBusiProjectLeaderWorkflowList(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow);
	
	/**
     * 新增项目负责人审批
     * 
     * @param busiProjectLeaderWorkflow 项目负责人审批信息
     * @return 结果
     */
	public int insertBusiProjectLeaderWorkflow(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow);
	
	/**
     * 修改项目负责人审批
     * 
     * @param busiProjectLeaderWorkflow 项目负责人审批信息
     * @return 结果
     */
	public int updateBusiProjectLeaderWorkflow(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow);
	
	/**
     * 删除项目负责人审批
     * 
     * @param billId 项目负责人审批ID
     * @return 结果
     */
	public int deleteBusiProjectLeaderWorkflowById(Long billId);
	
	/**
     * 批量删除项目负责人审批
     * 
     * @param billIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiProjectLeaderWorkflowByIds(String[] billIds);

	/**
	 * 批量新增项目负责人审批
	 *
	 * @param list 项目负责人审批信息
	 * @return 结果
	 */
	public int insertBusiProjectLeaderWorkflowBatch(List<BusiProjectLeaderWorkflow> list);
	//以上自动生成的尽量别动
}