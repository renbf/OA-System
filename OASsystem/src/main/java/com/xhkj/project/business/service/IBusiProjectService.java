package com.xhkj.project.business.service;

import com.xhkj.project.business.domain.*;
import com.xhkj.project.business.domain.vo.BusiProjectApplyVo;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import com.xhkj.project.business.domain.vo.BusiTaskVo;

import java.util.Map;

/**
 * 项目 服务层
 * 
 * @author rbf
 * @date 2020-09-24
 */
public interface IBusiProjectService
{
	/**
     * 查询项目信息
     * 
     * @param projectId 项目ID
     * @return 项目信息
     */
	public BusiProject selectBusiProjectById(Long projectId);
	
	/**
     * 查询项目列表
     * 
     * @param busiProject 项目信息
     * @return 项目集合
     */
	public Map<String, Object> selectBusiProjectList(BusiProject busiProject);
	
	/**
     * 新增项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	public Map<String, Object> insertBusiProject(BusiProject busiProject);

	/**
     * 修改项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	public Map<String, Object> updateBusiProject(BusiProject busiProject);

	/**
     * 删除项目信息
     * 
     * @param projectId 需要删除的数据ID
     * @return 结果
     */
	public Map<String, Object> deleteBusiProjectByIds(Long projectId);
	//以上自动生成的尽量别动

	public Map<String, Object> selectBusiProjects(BusiProjectVo busiProjectVo);

	public Map<String, Object> getProjectInfo(Long projectId);

	public Map<String, Object> changeStatus(BusiProject busiProject);

	public Map<String, Object> insertBusiTask(BusiTask busiTask);

	public Map<String, Object> updateBusiTask(BusiTask busiTask);

	public Map<String, Object> updateTaskProgress(BusiTask busiTask);

	public Map<String, Object> selectListTask(BusiTaskVo busiTaskVo);

	public Map<String, Object> getTaskInfo(Long taskId);

	public Map<String, Object> changeTaskStatus(BusiTask busiTask);

	public Map<String, Object> removeTask(BusiTask busiTask);

	public Map<String, Object> closeProject(BusiProject busiProject);

	public Map<String, Object> closeTask(BusiTask busiTask);

	public Map<String, Object> insertBusiTaskLog(BusiTaskLog busiTaskLog);

	public Map<String, Object> updateTaskLog(BusiTaskLog busiTaskLog);

	public Map<String, Object> getDayTaskLog(Long taskId);

	public Map<String, Object> taskLogBaosong(BusiTask busiTask);

	public Map<String, Object> insertProjectApply(BusiProjectApply busiProjectApply);

	public Map<String, Object> updateProjectApply(BusiProjectApply busiProjectApply);

	public Map<String, Object> removeProjectApply(BusiProjectApplyVo busiProjectApplyVo);

	public Map<String, Object> listProjectApply(BusiProjectApplyVo busiProjectApplyVo);

	public Map<String, Object> listProjectApplyShenpi(Long projectApplyId);

	public Map<String, Object> baosongProjectApply(BusiProjectApplyVo busiProjectApplyVo);

	public Map<String, Object> todolist(BusiProjectVo busiProjectVo);

	public Map<String, Object> todolistProjectApply(BusiProjectApplyVo busiProjectApplyVo);

	public Map<String, Object> batchProjectApplyShenpi(BusiProjectApplyVo busiProjectApplyVo);

	public Map<String, Object> projectLeaderWorkflowCount();

	public Map<String, Object> projectLeaderWorkflowList(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow);

	public Map<String, Object> projectLeaderWorkflowShenpi(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow);

}
