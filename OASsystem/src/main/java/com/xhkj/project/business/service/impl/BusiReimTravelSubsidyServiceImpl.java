package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimTravelSubsidyMapper;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import com.xhkj.project.business.service.IBusiReimTravelSubsidyService;

/**
 * 交通费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimTravelSubsidyServiceImpl implements IBusiReimTravelSubsidyService 
{
    @Autowired
    private BusiReimTravelSubsidyMapper busiReimTravelSubsidyMapper;

    /**
     * 查询交通费用
     * 
     * @param travelId 交通费用ID
     * @return 交通费用
     */
    @Override
    public BusiReimTravelSubsidy selectBusiReimTravelSubsidyById(Long travelId)
    {
        return busiReimTravelSubsidyMapper.selectBusiReimTravelSubsidyById(travelId);
    }

    /**
     * 查询交通费用列表
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 交通费用
     */
    @Override
    public List<BusiReimTravelSubsidy> selectBusiReimTravelSubsidyList(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        return busiReimTravelSubsidyMapper.selectBusiReimTravelSubsidyList(busiReimTravelSubsidy);
    }

    /**
     * 新增交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    @Override
    public int insertBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        busiReimTravelSubsidy.setCreateTime(DateUtils.getNowDate());
        busiReimTravelSubsidy.setCreateBy(SecurityUtils.getUserId());
        return busiReimTravelSubsidyMapper.insertBusiReimTravelSubsidy(busiReimTravelSubsidy);
    }

    /**
     * 修改交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    @Override
    public int updateBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        busiReimTravelSubsidy.setUpdateTime(DateUtils.getNowDate());
        return busiReimTravelSubsidyMapper.updateBusiReimTravelSubsidy(busiReimTravelSubsidy);
    }

    /**
     * 批量删除交通费用
     * 
     * @param travelIds 需要删除的交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTravelSubsidyByIds(Long[] travelIds)
    {
        return busiReimTravelSubsidyMapper.deleteBusiReimTravelSubsidyByIds(travelIds);
    }

    /**
     * 删除交通费用信息
     * 
     * @param travelId 交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTravelSubsidyById(Long travelId)
    {
        return busiReimTravelSubsidyMapper.deleteBusiReimTravelSubsidyById(travelId);
    }
}
