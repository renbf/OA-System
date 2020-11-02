package com.xhkj.project.business.service;

import java.util.List;

import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.domain.BusiReimburse;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseVo;

/**
 * 报销信息Service接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface IBusiReimburseService 
{
    /**
     * 查询报销信息
     * 
     * @param reimburseId 报销信息ID
     * @return 报销信息
     */
    public BusiReimburse selectBusiReimburseById(Long reimburseId);


    public BusiReimburseVo getRemburseDetail(Long reimburseIdString ,String reimburseType);


    /**
     * 查询报销信息列表
     * 
     * @param busiReimburse 报销信息
     * @return 报销信息集合
     */
    public List<BusiReimburseAprVo> selectBusiReimburseList(BusiReimburseAprVo busiReimburseAprVo);



    /**
     * 新增报销信息
     * 
     * @param busiReimburse 报销信息
     * @return 结果
     */
    public Long insertBusiReimburse(BusiReimburse busiReimburse);

    /**
     * 修改报销信息
     * 
     * @param busiReimburse 报销信息
     * @return 结果
     */
    public int updateBusiReimburse(BusiReimburse busiReimburse);

    /**
     * 批量删除报销信息
     * 
     * @param reimburseIds 需要删除的报销信息ID
     * @return 结果
     */
    public int deleteBusiReimburseByIds(Long[] reimburseIds);

    /**
     * 删除报销信息信息
     * 
     * @param reimburseId 报销信息ID
     * @return 结果
     */
    public int deleteBusiReimburseById(Long reimburseId);


    public AjaxResult billSumbit(Long[] reimburseIds);

}
