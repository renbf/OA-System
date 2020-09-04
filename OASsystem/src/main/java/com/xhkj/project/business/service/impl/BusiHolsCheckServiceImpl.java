package com.xhkj.project.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiHolsCheckMapper;
import com.xhkj.project.business.domain.BusiHolsCheck;
import com.xhkj.project.business.service.BusiHolsCheckService;

/**
 * 年假调休核减Service业务层处理
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@Service
public class BusiHolsCheckServiceImpl implements BusiHolsCheckService
{
    @Autowired
    private BusiHolsCheckMapper busiHolsCheckMapper;

    /**
     * 查询年假调休核减
     * 
     * @param userId 年假调休核减ID
     * @return 年假调休核减
     */
    @Override
    public BusiHolsCheck selectBusiHolsCheckById(long userId)
    {
        return busiHolsCheckMapper.selectBusiHolsCheckById(userId);
    }

    /**
     * 查询年假调休核减列表
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 年假调休核减
     */
    @Override
    public List<BusiHolsCheck> selectBusiHolsCheckList(BusiHolsCheck busiHolsCheck)
    {
        return busiHolsCheckMapper.selectBusiHolsCheckList(busiHolsCheck);
    }

    /**
     * 新增年假调休核减
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 结果
     */
    @Override
    public int insertBusiHolsCheck(BusiHolsCheck busiHolsCheck)
    {
        return busiHolsCheckMapper.insertBusiHolsCheck(busiHolsCheck);
    }

    /**
     * 修改年假调休核减
     * 
     * @param busiHolsCheck 年假调休核减
     * @return 结果
     */
    @Override
    public int updateBusiHolsCheck(BusiHolsCheck busiHolsCheck)
    {
        return busiHolsCheckMapper.updateBusiHolsCheck(busiHolsCheck);
    }

    /**
     * 批量删除年假调休核减
     * 
     * @param userIds 需要删除的年假调休核减ID
     * @return 结果
     */
    @Override
    public int deleteBusiHolsCheckByIds(String[] userIds)
    {
        return busiHolsCheckMapper.deleteBusiHolsCheckByIds(userIds);
    }

    /**
     * 删除年假调休核减信息
     * 
     * @param userId 年假调休核减ID
     * @return 结果
     */
    @Override
    public int deleteBusiHolsCheckById(String userId)
    {
        return busiHolsCheckMapper.deleteBusiHolsCheckById(userId);
    }
}
