package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiTaskMember;
import java.util.List;	
/**
 * 任务成员关系 数据层
 * 
 * @author rbf
 * @date 2020-09-30
 */
public interface BusiTaskMemberMapper
{
	/**
     * 查询任务成员关系信息
     * 
     * @param taskMemberId 任务成员关系ID
     * @return 任务成员关系信息
     */
	public BusiTaskMember selectBusiTaskMemberById(Long taskMemberId);
	
	/**
     * 查询任务成员关系列表
     * 
     * @param busiTaskMember 任务成员关系信息
     * @return 任务成员关系集合
     */
	public List<BusiTaskMember> selectBusiTaskMemberList(BusiTaskMember busiTaskMember);
	
	/**
     * 新增任务成员关系
     * 
     * @param busiTaskMember 任务成员关系信息
     * @return 结果
     */
	public int insertBusiTaskMember(BusiTaskMember busiTaskMember);
	
	/**
     * 修改任务成员关系
     * 
     * @param busiTaskMember 任务成员关系信息
     * @return 结果
     */
	public int updateBusiTaskMember(BusiTaskMember busiTaskMember);
	
	/**
     * 删除任务成员关系
     * 
     * @param taskMemberId 任务成员关系ID
     * @return 结果
     */
	public int deleteBusiTaskMemberById(Long taskMemberId);
	
	/**
     * 批量删除任务成员关系
     * 
     * @param taskMemberIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiTaskMemberByIds(String[] taskMemberIds);

	/**
	 * 批量新增任务成员关系
	 *
	 * @param list 任务成员关系信息
	 * @return 结果
	 */
	public int insertBusiTaskMemberBatch(List<BusiTaskMember> list);
	//以上自动生成的尽量别动
}