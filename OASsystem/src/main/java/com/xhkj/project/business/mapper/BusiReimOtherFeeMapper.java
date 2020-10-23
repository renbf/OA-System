package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimOtherFee;

/**
 * 交通费用Mapper接口
 * 
 * @author xhkj
 * @date 2020-10-21
 */
public interface BusiReimOtherFeeMapper 
{
    /**
     * 查询交通费用
     * 
     * @param otherId 交通费用ID
     * @return 交通费用
     */
    public BusiReimOtherFee selectBusiReimOtherFeeById(Long otherId);


    /**
     * 查询交通费用列表
     * 
     * @param busiReimOtherFee 交通费用
     * @return 交通费用集合
     */
    public List<BusiReimOtherFee> selectBusiReimOtherFeeList(BusiReimOtherFee busiReimOtherFee);

    /**
     * 新增交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    public int insertBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee);

    /**
     * 修改交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    public int updateBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee);

    /**
     * 删除交通费用
     * 
     * @param otherId 交通费用ID
     * @return 结果
     */
    public int deleteBusiReimOtherFeeById(Long otherId);

    /**
     * 批量删除交通费用
     * 
     * @param otherIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiReimOtherFeeByIds(Long[] otherIds);

    /**
     * 批量新增交通费用
     *
     * @param list 交通费用信息
     * @return 结果
     */
    public int insertBusiReimOtherFeeBatch(List<BusiReimOtherFee> list);
    //以上自动生成的尽量别动
}
