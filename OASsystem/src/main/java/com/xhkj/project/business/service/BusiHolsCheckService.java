package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiHolsCheck;

/**
 * 年假调休核减Service接口
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public interface BusiHolsCheckService
{
    /**
     * 查询年假调休核减
     * 
     * @param userId 年假调休核减ID
     * @return 年假调休核减
     */
    public BusiHolsCheck selectBusiHolsCheckById(long userId);

    /**
     * 查询年假调休核减列表
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 年假调休核减集合
     */
    public List<BusiHolsCheck> selectBusiHolsCheckList(BusiHolsCheck busiHolsCheck);

    /**
     * 新增年假调休核减
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 结果
     */
    public int insertBusiHolsCheck(BusiHolsCheck busiHolsCheck);

    /**
     * 修改年假调休核减
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 结果
     */
    public int updateBusiHolsCheck(BusiHolsCheck busiHolsCheck);

    /**
     * 批量删除年假调休核减
     * 
     * @param userIds 需要删除的年假调休核减ID
     * @return 结果
     */
    public int deleteBusiHolsCheckByIds(String[] userIds);

    /**
     * 删除年假调休核减信息
     * 
     * @param userId 年假调休核减ID
     * @return 结果
     */
    public int deleteBusiHolsCheckById(String userId);
}
