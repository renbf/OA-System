package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.SysWorkflowGroup;
import java.util.List;	
/**
 * 流程分组 数据层
 * 
 * @author rbf
 * @date 2020-07-10
 */
public interface SysWorkflowGroupMapper
{
	/**
     * 查询流程分组信息
     * 
     * @param workflowGroupId 流程分组ID
     * @return 流程分组信息
     */
	public SysWorkflowGroup selectSysWorkflowGroupById(Long workflowGroupId);
	
	/**
     * 查询流程分组列表
     * 
     * @param sysWorkflowGroup 流程分组信息
     * @return 流程分组集合
     */
	public List<SysWorkflowGroup> selectSysWorkflowGroupList(SysWorkflowGroup sysWorkflowGroup);
	
	/**
     * 新增流程分组
     * 
     * @param sysWorkflowGroup 流程分组信息
     * @return 结果
     */
	public int insertSysWorkflowGroup(SysWorkflowGroup sysWorkflowGroup);
	
	/**
     * 修改流程分组
     * 
     * @param sysWorkflowGroup 流程分组信息
     * @return 结果
     */
	public int updateSysWorkflowGroup(SysWorkflowGroup sysWorkflowGroup);
	
	/**
     * 删除流程分组
     * 
     * @param workflowGroupId 流程分组ID
     * @return 结果
     */
	public int deleteSysWorkflowGroupById(Long workflowGroupId);
	
	/**
     * 批量删除流程分组
     * 
     * @param workflowGroupIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSysWorkflowGroupByIds(String[] workflowGroupIds);

	/**
	 * 批量新增流程分组
	 *
	 * @param list 流程分组信息
	 * @return 结果
	 */
	public int insertSysWorkflowGroupBatch(List<SysWorkflowGroup> list);
	//以上自动生成的尽量别动

	public List<SysWorkflowGroup> selectSysWorkflowGroups(SysWorkflowGroup sysWorkflowGroup);
}