package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimExpense;

/**
 * 费用Service接口
 * 
 * @author xhkj
 * @date 2020-11-02
 */
public interface IBusiReimExpenseService 
{
    /**
     * 查询费用
     * 
     * @param reimExpenseId 费用ID
     * @return 费用
     */
    public BusiReimExpense selectBusiReimExpenseById(Long reimExpenseId);

    /**
     * 查询费用列表
     * 
     * @param busiReimExpense 费用
     * @return 费用集合
     */
    public List<BusiReimExpense> selectBusiReimExpenseList(BusiReimExpense busiReimExpense);

    /**
     * 新增费用
     * 
     * @param busiReimExpense 费用
     * @return 结果
     */
    public int insertBusiReimExpense(BusiReimExpense busiReimExpense);

    /**
     * 修改费用
     * 
     * @param busiReimExpense 费用
     * @return 结果
     */
    public int updateBusiReimExpense(BusiReimExpense busiReimExpense);

    /**
     * 批量删除费用
     * 
     * @param reimExpenseIds 需要删除的费用ID
     * @return 结果
     */
    public int deleteBusiReimExpenseByIds(Long[] reimExpenseIds);

    /**
     * 删除费用信息
     * 
     * @param reimExpenseId 费用ID
     * @return 结果
     */
    public int deleteBusiReimExpenseById(Long reimExpenseId);
}
