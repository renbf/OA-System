package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiProjectApply;
import java.util.List;	
/**
 * 项目申请 数据层
 * 
 * @author rbf
 * @date 2020-09-30
 */
public interface BusiProjectApplyMapper
{
	/**
     * 查询项目申请信息
     * 
     * @param projectApplyId 项目申请ID
     * @return 项目申请信息
     */
	public BusiProjectApply selectBusiProjectApplyById(Long projectApplyId);
	
	/**
     * 查询项目申请列表
     * 
     * @param busiProjectApply 项目申请信息
     * @return 项目申请集合
     */
	public List<BusiProjectApply> selectBusiProjectApplyList(BusiProjectApply busiProjectApply);
	
	/**
     * 新增项目申请
     * 
     * @param busiProjectApply 项目申请信息
     * @return 结果
     */
	public int insertBusiProjectApply(BusiProjectApply busiProjectApply);
	
	/**
     * 修改项目申请
     * 
     * @param busiProjectApply 项目申请信息
     * @return 结果
     */
	public int updateBusiProjectApply(BusiProjectApply busiProjectApply);
	
	/**
     * 删除项目申请
     * 
     * @param projectApplyId 项目申请ID
     * @return 结果
     */
	public int deleteBusiProjectApplyById(Long projectApplyId);
	
	/**
     * 批量删除项目申请
     * 
     * @param projectApplyIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiProjectApplyByIds(String[] projectApplyIds);

	/**
	 * 批量新增项目申请
	 *
	 * @param list 项目申请信息
	 * @return 结果
	 */
	public int insertBusiProjectApplyBatch(List<BusiProjectApply> list);
	//以上自动生成的尽量别动
}