package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimTrafficFee;

/**
 * 交通费用Service接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface IBusiReimTrafficFeeService 
{
    /**
     * 查询交通费用
     * 
     * @param trafficId 交通费用ID
     * @return 交通费用
     */
    public BusiReimTrafficFee selectBusiReimTrafficFeeById(Long trafficId);

    /**
     * 查询交通费用列表
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 交通费用集合
     */
    public List<BusiReimTrafficFee> selectBusiReimTrafficFeeList(BusiReimTrafficFee busiReimTrafficFee);

    /**
     * 新增交通费用
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 结果
     */
    public int insertBusiReimTrafficFee(BusiReimTrafficFee busiReimTrafficFee);

    /**
     * 修改交通费用
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 结果
     */
    public int updateBusiReimTrafficFee(BusiReimTrafficFee busiReimTrafficFee);

    /**
     * 批量删除交通费用
     * 
     * @param trafficIds 需要删除的交通费用ID
     * @return 结果
     */
    public int deleteBusiReimTrafficFeeByIds(Long[] trafficIds);

    /**
     * 删除交通费用信息
     * 
     * @param trafficId 交通费用ID
     * @return 结果
     */
    public int deleteBusiReimTrafficFeeById(Long trafficId);
}
