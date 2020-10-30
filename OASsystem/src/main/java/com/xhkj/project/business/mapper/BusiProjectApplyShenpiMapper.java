package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiProjectApplyShenpi;
import java.util.List;	
/**
 * 项目申请审批 数据层
 * 
 * @author rbf
 * @date 2020-09-30
 */
public interface BusiProjectApplyShenpiMapper
{
	/**
     * 查询项目申请审批信息
     * 
     * @param projectApplyShenpiId 项目申请审批ID
     * @return 项目申请审批信息
     */
	public BusiProjectApplyShenpi selectBusiProjectApplyShenpiById(Long projectApplyShenpiId);
	
	/**
     * 查询项目申请审批列表
     * 
     * @param busiProjectApplyShenpi 项目申请审批信息
     * @return 项目申请审批集合
     */
	public List<BusiProjectApplyShenpi> selectBusiProjectApplyShenpiList(BusiProjectApplyShenpi busiProjectApplyShenpi);
	
	/**
     * 新增项目申请审批
     * 
     * @param busiProjectApplyShenpi 项目申请审批信息
     * @return 结果
     */
	public int insertBusiProjectApplyShenpi(BusiProjectApplyShenpi busiProjectApplyShenpi);
	
	/**
     * 修改项目申请审批
     * 
     * @param busiProjectApplyShenpi 项目申请审批信息
     * @return 结果
     */
	public int updateBusiProjectApplyShenpi(BusiProjectApplyShenpi busiProjectApplyShenpi);
	
	/**
     * 删除项目申请审批
     * 
     * @param projectApplyShenpiId 项目申请审批ID
     * @return 结果
     */
	public int deleteBusiProjectApplyShenpiById(Long projectApplyShenpiId);
	
	/**
     * 批量删除项目申请审批
     * 
     * @param projectApplyShenpiIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiProjectApplyShenpiByIds(String[] projectApplyShenpiIds);

	/**
	 * 批量新增项目申请审批
	 *
	 * @param list 项目申请审批信息
	 * @return 结果
	 */
	public int insertBusiProjectApplyShenpiBatch(List<BusiProjectApplyShenpi> list);
	//以上自动生成的尽量别动

	public int deleteBusiProjectApplyShenpiByProjectApplyId(Long projectApplyId);
}