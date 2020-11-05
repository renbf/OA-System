package com.xhkj.project.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.*;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseVo;
import com.xhkj.project.business.mapper.BusiReimOtherFeeMapper;
import com.xhkj.project.business.mapper.BusiReimTrafficFeeMapper;
import com.xhkj.project.business.mapper.BusiReimTravelSubsidyMapper;
import com.xhkj.project.business.mapper.BusiReimburseMapper;
import com.xhkj.project.business.service.*;
import com.xhkj.project.system.domain.WorkflowBillTrace;
import com.xhkj.project.system.service.ISysWorkflowService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    ISysWorkflowService sysWorkflowService;
    @Autowired
    IBusiReimExpenseService iBusiReimExpenseService;


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
    public BusiReimburseVo getRemburseDetail(Long reimburseId,String reimburseType)
    {
        BusiReimburseVo busiReimburseVo = new BusiReimburseVo();
        BusiReimburse busiReimburse = busiReimburseMapper.selectBusiReimburseById(reimburseId);

        BeanUtils.copyProperties(busiReimburse,busiReimburseVo);
        BigDecimal total = new BigDecimal(0);


        if(StringUtils.equals(reimburseType,"travel")){
            List<BusiReimTrafficFee> busiReimTrafficFees = iBusiReimTrafficFeeService.selectBusiReimTrafficFeeList(new BusiReimTrafficFee(reimburseId));
            List<BusiReimTravelSubsidy> busiReimTravelSubsidies = ibusiReimTravelSubsidyService.selectBusiReimTravelSubsidyList(new BusiReimTravelSubsidy(reimburseId));
            List<BusiReimOtherFee> busiReimOtherFees = ibusiReimOtherFeeService.selectBusiReimOtherFeeList(new BusiReimOtherFee(reimburseId));


            for (BusiReimTrafficFee busiReimTrafficFee : busiReimTrafficFees) {
                BigDecimal amountTotal = new BigDecimal(busiReimTrafficFee.getAmountTotal());
                total = total.add(amountTotal);
            }
            for (BusiReimTravelSubsidy busiReimTravelSubsidy : busiReimTravelSubsidies) {
                BigDecimal amountTotal = new BigDecimal(busiReimTravelSubsidy.getAmountTotal());
                total = total.add(amountTotal);
            }

            for (BusiReimOtherFee busiReimOtherFee : busiReimOtherFees) {
                BigDecimal amountTotal = new BigDecimal(busiReimOtherFee.getAmountTotal());
                total = total.add(amountTotal);
            }

            busiReimburseVo.setBusiReimTrafficFeeList(busiReimTrafficFees);
            busiReimburseVo.setBusiReimTravelSubsidyList(busiReimTravelSubsidies);
            busiReimburseVo.setBusiReimOtherFeeList(busiReimOtherFees);

        }else if(StringUtils.equals(reimburseType,"expenses")){
            List<BusiReimExpense> busiReimExpenses = iBusiReimExpenseService.selectBusiReimExpenseList(new BusiReimExpense(reimburseId));
            for (BusiReimExpense busiReimExpense : busiReimExpenses) {
                BigDecimal amountTotal = new BigDecimal(busiReimExpense.getAmountTotal());
                total = total.add(amountTotal);
            }

            busiReimburseVo.setBusiReimExpenseList(busiReimExpenses);

        }


        busiReimburseVo.setAmountTotal(total.doubleValue());


        return busiReimburseVo;
    }
    /**
     * 查询报销信息列表
     * 
     * @param busiReimburse 报销信息
     * @return 报销信息
     */
    @Override
    public List<BusiReimburseAprVo> selectBusiReimburseList(BusiReimburseAprVo busiReimburseAprVo)
    {
        return busiReimburseMapper.selectBusiReimburseList(busiReimburseAprVo);
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
        Long reimburseId = busiReimburse.getReimburseId();

        if(reimburseId!=null){
            busiReimburse.setUpdateBy(SecurityUtils.getUserId());
            busiReimburse.setUpdateTime(DateUtils.getNowDate());
            busiReimburseMapper.updateBusiReimburse(busiReimburse);
        }else{
            busiReimburse.setUserId(userId);
            busiReimburse.setCreateTime(new Date());
            busiReimburse.setCreateBy(String.valueOf(userId));
            busiReimburseMapper.insertBusiReimburse(busiReimburse);
            reimburseId = busiReimburse.getReimburseId();
        }

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
    @Transactional
    public int deleteBusiReimburseByIds(Long[] reimburseIds)
    {
        for (Long reimburseId : reimburseIds) {
            List<BusiReimTrafficFee> busiReimTrafficFees = iBusiReimTrafficFeeService.selectBusiReimTrafficFeeList(new BusiReimTrafficFee(reimburseId));
            List<BusiReimTravelSubsidy> busiReimTravelSubsidies = ibusiReimTravelSubsidyService.selectBusiReimTravelSubsidyList(new BusiReimTravelSubsidy(reimburseId));
            List<BusiReimOtherFee> busiReimOtherFees = ibusiReimOtherFeeService.selectBusiReimOtherFeeList(new BusiReimOtherFee(reimburseId));

            List<BusiReimExpense> busiReimExpenses = iBusiReimExpenseService.selectBusiReimExpenseList(new BusiReimExpense(reimburseId));
            if(CollectionUtils.isNotEmpty(busiReimExpenses)){
                busiReimExpenses.stream().forEach(e->{
                    iBusiReimExpenseService.deleteBusiReimExpenseById(e.getReimExpenseId());
                });
            }

            if(CollectionUtils.isNotEmpty(busiReimTrafficFees)){
                busiReimTrafficFees.stream().forEach(e->{
                    iBusiReimTrafficFeeService.deleteBusiReimTrafficFeeById(e.getTrafficId());
                });
            }
            if(CollectionUtils.isNotEmpty(busiReimTravelSubsidies)){
                busiReimTravelSubsidies.stream().forEach(e->{
                    ibusiReimTravelSubsidyService.deleteBusiReimTravelSubsidyById(e.getTravelId());
                });
            }
            if(CollectionUtils.isNotEmpty(busiReimOtherFees)){
                busiReimOtherFees.stream().forEach(e->{
                    ibusiReimOtherFeeService.deleteBusiReimOtherFeeById(e.getOtherId());
                });
            }

        }

        int i = busiReimburseMapper.deleteBusiReimburseByIds(reimburseIds);


        return i;
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

    @Override
    public AjaxResult billSumbit(Long[] reimburseIds) {
        AjaxResult ajaxResult = null;
        for (int i = 0; i < reimburseIds.length; i++) {
            Long reimburseId = reimburseIds[i];

            BusiReimburse busiReimburse = busiReimburseMapper.selectBusiReimburseById(reimburseId);
            Long workflowId = busiReimburse.getWorkflowId();

            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(reimburseId);
            wfbt.setWorkflowId(workflowId);

            //发起流程申请
            ajaxResult = sysWorkflowService.submitToNextWorkflow(wfbt);

        }

        return ajaxResult;

    }


    @Override
    public List<BusiReimburseAprVo> approveList(BusiReimburseAprVo busiReimburseAprVo)
    {
        busiReimburseAprVo.setUserId(Long.valueOf(SecurityUtils.getUserId()));
        return busiReimburseMapper.approveList(busiReimburseAprVo);
    }



}
