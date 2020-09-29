package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiProjectMember;
import java.util.List;	
/**
 * 项目成员关系 数据层
 * 
 * @author rbf
 * @date 2020-09-24
 */
public interface BusiProjectMemberMapper
{
	/**
     * 查询项目成员关系信息
     * 
     * @param projectMemberId 项目成员关系ID
     * @return 项目成员关系信息
     */
	public BusiProjectMember selectBusiProjectMemberById(Long projectMemberId);
	
	/**
     * 查询项目成员关系列表
     * 
     * @param busiProjectMember 项目成员关系信息
     * @return 项目成员关系集合
     */
	public List<BusiProjectMember> selectBusiProjectMemberList(BusiProjectMember busiProjectMember);
	
	/**
     * 新增项目成员关系
     * 
     * @param busiProjectMember 项目成员关系信息
     * @return 结果
     */
	public int insertBusiProjectMember(BusiProjectMember busiProjectMember);
	
	/**
     * 修改项目成员关系
     * 
     * @param busiProjectMember 项目成员关系信息
     * @return 结果
     */
	public int updateBusiProjectMember(BusiProjectMember busiProjectMember);
	
	/**
     * 删除项目成员关系
     * 
     * @param projectMemberId 项目成员关系ID
     * @return 结果
     */
	public int deleteBusiProjectMemberById(Long projectMemberId);
	
	/**
     * 批量删除项目成员关系
     * 
     * @param projectMemberIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiProjectMemberByIds(String[] projectMemberIds);

	/**
	 * 批量新增项目成员关系
	 *
	 * @param list 项目成员关系信息
	 * @return 结果
	 */
	public int insertBusiProjectMemberBatch(List<BusiProjectMember> list);
	//以上自动生成的尽量别动

	public int deleteBusiProjectMemberByProjectId(Long projectId);

}