package com.xhkj.project.business.service;

import com.xhkj.project.business.domain.BusiProject;
import com.xhkj.project.business.domain.BusiTask;
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
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public Map<String, Object> deleteBusiProjectByIds(String ids);
	//以上自动生成的尽量别动

	public Map<String, Object> selectBusiProjects(BusiProjectVo busiProjectVo);

	public Map<String, Object> getProjectInfo(Long projectId);

	public Map<String, Object> changeStatus(BusiProject busiProject);

	public Map<String, Object> insertBusiTask(BusiTask busiTask);

	public Map<String, Object> updateBusiTask(BusiTask busiTask);

	public Map<String, Object> selectListTask(BusiTaskVo busiTaskVo);

	public Map<String, Object> getTaskInfo(Long taskId);

}
