package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiAskLeave;

/**
 * 请假倒休Mapper接口
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public interface BusiAskLeaveMapper 
{

    /**
     * 查询请假倒休列表
     *
     * @param busiAskLeave 请假倒休
     * @return 请假倒休集合
     */
    public BusiAskLeave selectBusiAskLeaveById(Long leaveId);

    /**
     * 查询请假倒休列表
     * 
     * @param busiAskLeave 请假倒休
     * @return 请假倒休集合
     */
    public List<BusiAskLeave> selectBusiAskLeaveList(BusiAskLeave busiAskLeave);

    /**
     * 新增请假倒休
     * 
     * @param busiAskLeave 请假倒休
     * @return 结果
     */
    public int insertBusiAskLeave(BusiAskLeave busiAskLeave);

    /**
     * 修改请假倒休
     * 
     * @param busiAskLeave 请假倒休
     * @return 结果
     */
    public int updateBusiAskLeave(BusiAskLeave busiAskLeave);

    /**
     * 删除请假倒休
     * 
     * @param leaveId 请假倒休ID
     * @return 结果
     */
    public int deleteBusiAskLeaveById(Long leaveId);

    /**
     * 批量删除请假倒休
     * 
     * @param leaveIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiAskLeaveByIds(Long[] leaveIds);
}
