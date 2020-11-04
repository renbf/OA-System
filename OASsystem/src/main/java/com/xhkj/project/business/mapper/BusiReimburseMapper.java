package com.xhkj.project.business.mapper;

import java.util.List;

import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.domain.BusiReimburse;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseAprVo;

/**
 * 报销信息Mapper接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface BusiReimburseMapper 
{
    /**
     * 查询报销信息
     * 
     * @param reimburseId 报销信息ID
     * @return 报销信息
     */
    public BusiReimburse selectBusiReimburseById(Long reimburseId);

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
    public int insertBusiReimburse(BusiReimburse busiReimburse);

    /**
     * 修改报销信息
     * 
     * @param busiReimburse 报销信息
     * @return 结果
     */
    public int updateBusiReimburse(BusiReimburse busiReimburse);

    /**
     * 删除报销信息
     * 
     * @param reimburseId 报销信息ID
     * @return 结果
     */
    public int deleteBusiReimburseById(Long reimburseId);

    /**
     * 批量删除报销信息
     * 
     * @param reimburseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiReimburseByIds(Long[] reimburseIds);

    /**
     * 批量新增报销信息
     *
     * @param list 报销信息信息
     * @return 结果
     */
    public int insertBusiReimburseBatch(List<BusiReimburse> list);
    //以上自动生成的尽量别动


    public List<BusiReimburseAprVo> approveList(BusiReimburseAprVo busiReimburseAprVo);



}
