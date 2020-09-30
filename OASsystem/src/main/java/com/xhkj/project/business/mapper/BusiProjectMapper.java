package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiProject;
import com.xhkj.project.business.domain.vo.BusiProjectVo;

import java.util.List;
/**
 * 项目 数据层
 * 
 * @author rbf
 * @date 2020-09-24
 */
public interface BusiProjectMapper
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
	public List<BusiProject> selectBusiProjectList(BusiProject busiProject);
	
	/**
     * 新增项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	public int insertBusiProject(BusiProject busiProject);
	
	/**
     * 修改项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	public int updateBusiProject(BusiProject busiProject);
	
	/**
     * 删除项目
     * 
     * @param projectId 项目ID
     * @return 结果
     */
	public int deleteBusiProjectById(Long projectId);
	
	/**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiProjectByIds(String[] projectIds);

	/**
	 * 批量新增项目
	 *
	 * @param list 项目信息
	 * @return 结果
	 */
	public int insertBusiProjectBatch(List<BusiProject> list);
	//以上自动生成的尽量别动

	public List<BusiProjectVo> selectBusiProjects(BusiProjectVo busiProjectVo);

	public BusiProjectVo selectBusiProject(Long projectId);

}