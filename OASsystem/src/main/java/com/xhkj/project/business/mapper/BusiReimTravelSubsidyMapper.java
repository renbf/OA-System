package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;

/**
 * 交通费用Mapper接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface BusiReimTravelSubsidyMapper 
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
     * 删除交通费用
     * 
     * @param travelId 交通费用ID
     * @return 结果
     */
    public int deleteBusiReimTravelSubsidyById(Long travelId);

    /**
     * 批量删除交通费用
     * 
     * @param travelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiReimTravelSubsidyByIds(Long[] travelIds);

    /**
     * 批量新增交通费用
     *
     * @param list 交通费用信息
     * @return 结果
     */
    public int insertBusiReimTravelSubsidyBatch(List<BusiReimTravelSubsidy> list);
    //以上自动生成的尽量别动
}
