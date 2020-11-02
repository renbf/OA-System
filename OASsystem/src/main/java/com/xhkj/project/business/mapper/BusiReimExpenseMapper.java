package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiReimExpense;

/**
 * 费用Mapper接口
 * 
 * @author xhkj
 * @date 2020-11-02
 */
public interface BusiReimExpenseMapper 
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
     * 删除费用
     * 
     * @param reimExpenseId 费用ID
     * @return 结果
     */
    public int deleteBusiReimExpenseById(Long reimExpenseId);

    /**
     * 批量删除费用
     * 
     * @param reimExpenseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiReimExpenseByIds(Long[] reimExpenseIds);

    /**
     * 批量新增费用
     *
     * @param list 费用信息
     * @return 结果
     */
    public int insertBusiReimExpenseBatch(List<BusiReimExpense> list);
    //以上自动生成的尽量别动
}
