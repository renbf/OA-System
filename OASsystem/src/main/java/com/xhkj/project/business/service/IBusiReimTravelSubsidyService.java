package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;

/**
 * 交通费用Service接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface IBusiReimTravelSubsidyService 
{
    /**
     * 查询交通费用
     * 
     * @param travelId 交通费用ID
     * @return 交通费用
     */
    public BusiReimTravelSubsidy selectBusiReimTravelSubsidyById(Long travelId);

    /**
     * 查询交通费用列表
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 交通费用集合
     */
    public List<BusiReimTravelSubsidy> selectBusiReimTravelSubsidyList(BusiReimTravelSubsidy busiReimTravelSubsidy);

    /**
     * 新增交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    public int insertBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy);

    /**
     * 修改交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    public int updateBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy);

    /**
     * 批量删除交通费用
     * 
     * @param travelIds 需要删除的交通费用ID
     * @return 结果
     */
    public int deleteBusiReimTravelSubsidyByIds(Long[] travelIds);

    /**
     * 删除交通费用信息
     * 
     * @param travelId 交通费用ID
     * @return 结果
     */
    public int deleteBusiReimTravelSubsidyById(Long travelId);
}
