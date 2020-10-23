package com.xhkj.project.business.service.impl;

import java.util.Date;
import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import com.xhkj.project.business.domain.vo.BusiReimburseVo;
import com.xhkj.project.business.mapper.BusiReimOtherFeeMapper;
import com.xhkj.project.business.mapper.BusiReimTrafficFeeMapper;
import com.xhkj.project.business.mapper.BusiReimTravelSubsidyMapper;
import com.xhkj.project.business.mapper.BusiReimburseMapper;
import com.xhkj.project.business.service.IBusiReimOtherFeeService;
import com.xhkj.project.business.service.IBusiReimTrafficFeeService;
import com.xhkj.project.business.service.IBusiReimTravelSubsidyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhkj.project.business.domain.BusiReimburse;
import com.xhkj.project.business.service.IBusiReimburseService;

/**
 * 报销信息Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimburseServiceImpl implements IBusiReimburseService 
{
    @Autowired
    private BusiReimburseMapper busiReimburseMapper;
    @Autowired
    private IBusiReimOtherFeeService ibusiReimOtherFeeService;
    @Autowired
    private IBusiReimTravelSubsidyService ibusiReimTravelSubsidyService;
    @Autowired
    IBusiReimTrafficFeeService iBusiReimTrafficFeeService;


    /**
     * 查询报销信息
     * 
     * @param reimburseId 报销信息ID
     * @return 报销信息
     */
    @Override
    public BusiReimburse selectBusiReimburseById(Long reimburseId)
    {
        return busiReimburseMapper.selectBusiReimburseById(reimburseId);
    }


    /**
     * 查询报销详细信息
     * @param reimburseId
     * @return
     */
    @Override
    public BusiReimburseVo getRemburseDetail(Long reimburseId)
    {
        BusiReimburseVo busiReimburseVo = new BusiReimburseVo();
        BusiReimburse busiReimburse = busiReimburseMapper.selectBusiReimburseById(reimburseId);

        BeanUtils.copyProperties(busiReimburse,busiReimburseVo);

        List<BusiReimTrafficFee> busiReimTrafficFees = iBusiReimTrafficFeeService.selectBusiReimTrafficFeeList(new BusiReimTrafficFee(reimburseId));
        List<BusiReimTravelSubsidy> busiReimTravelSubsidies = ibusiReimTravelSubsidyService.selectBusiReimTravelSubsidyList(new BusiReimTravelSubsidy(reimburseId));
        List<BusiReimOtherFee> busiReimOtherFees = ibusiReimOtherFeeService.selectBusiReimOtherFeeList(new BusiReimOtherFee(reimburseId));

        busiReimburseVo.setBusiReimTrafficFeeList(busiReimTrafficFees);
        busiReimburseVo.setBusiReimTravelSubsidyList(busiReimTravelSubsidies);
        busiReimburseVo.setBusiReimOtherFeeList(busiReimOtherFees);

        return busiReimburseVo;
    }
    /**
     * 查询报销信息列表
     * 
     * @param busiReimburse 报销信息
     * @return 报销信息
     */
    @Override
    public List<BusiReimburse> selectBusiReimburseList(BusiReimburse busiReimburse)
    {
        return busiReimburseMapper.selectBusiReimburseList(busiReimburse);
    }

    /**
     * 新增报销信息
     * 
     * @param busiReimburse 报销信息
     * @return 结果
     */
    @Override
    public Long insertBusiReimburse(BusiReimburse busiReimburse)
    {
        Long userId = Long.valueOf(SecurityUtils.getUserId());

        busiReimburse.setUserId(userId);
        busiReimburse.setCreateTime(new Date());
        busiReimburse.setCreateBy(String.valueOf(userId));

        busiReimburseMapper.insertBusiReimburse(busiReimburse);
        Long reimburseId = busiReimburse.getReimburseId();
        return reimburseId;
    }

    /**
     * 修改报销信息
     * 
     * @param busiReimburse 报销信息
     * @return 结果
     */
    @Override
    public int updateBusiReimburse(BusiReimburse busiReimburse)
    {
        busiReimburse.setUpdateTime(DateUtils.getNowDate());
        return busiReimburseMapper.updateBusiReimburse(busiReimburse);
    }

    /**
     * 批量删除报销信息
     * 
     * @param reimburseIds 需要删除的报销信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimburseByIds(Long[] reimburseIds)
    {
        return busiReimburseMapper.deleteBusiReimburseByIds(reimburseIds);
    }

    /**
     * 删除报销信息信息
     * 
     * @param reimburseId 报销信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimburseById(Long reimburseId)
    {
        return busiReimburseMapper.deleteBusiReimburseById(reimburseId);
    }
}
