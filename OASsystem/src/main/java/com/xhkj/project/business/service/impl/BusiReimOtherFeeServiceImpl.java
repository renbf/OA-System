package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimOtherFeeMapper;
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.service.IBusiReimOtherFeeService;

/**
 * 交通费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimOtherFeeServiceImpl implements IBusiReimOtherFeeService 
{
    @Autowired
    private BusiReimOtherFeeMapper busiReimOtherFeeMapper;

    /**
     * 查询交通费用
     * 
     * @param otherId 交通费用ID
     * @return 交通费用
     */
    @Override
    public BusiReimOtherFee selectBusiReimOtherFeeById(Long otherId)
    {
        return busiReimOtherFeeMapper.selectBusiReimOtherFeeById(otherId);
    }

    /**
     * 查询交通费用列表
     * 
     * @param busiReimOtherFee 交通费用
     * @return 交通费用
     */
    @Override
    public List<BusiReimOtherFee> selectBusiReimOtherFeeList(BusiReimOtherFee busiReimOtherFee)
    {
        return busiReimOtherFeeMapper.selectBusiReimOtherFeeList(busiReimOtherFee);
    }

    /**
     * 新增交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    @Override
    public int insertBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee)
    {
        busiReimOtherFee.setCreateTime(DateUtils.getNowDate());
        return busiReimOtherFeeMapper.insertBusiReimOtherFee(busiReimOtherFee);
    }

    /**
     * 修改交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    @Override
    public int updateBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee)
    {
        busiReimOtherFee.setUpdateTime(DateUtils.getNowDate());
        return busiReimOtherFeeMapper.updateBusiReimOtherFee(busiReimOtherFee);
    }

    /**
     * 批量删除交通费用
     * 
     * @param otherIds 需要删除的交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimOtherFeeByIds(Long[] otherIds)
    {
        return busiReimOtherFeeMapper.deleteBusiReimOtherFeeByIds(otherIds);
    }

    /**
     * 删除交通费用信息
     * 
     * @param otherId 交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimOtherFeeById(Long otherId)
    {
        return busiReimOtherFeeMapper.deleteBusiReimOtherFeeById(otherId);
    }
}
