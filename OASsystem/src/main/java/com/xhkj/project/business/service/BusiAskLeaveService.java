package com.xhkj.project.business.service;

import java.util.List;

import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.domain.vo.BusiAskLeaveVo;

/**
 * 请假倒休Service接口
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public interface BusiAskLeaveService
{
    /**
     * 查询请假倒休
     * 
     * @param leaveId 请假倒休ID
     * @return 请假倒休
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
    public int insertBusiAskLeave(BusiAskLeaveVo busiAskLeaveVo);

    /**
     * 修改请假倒休
     * 
     * @param busiAskLeave 请假倒休
     * @return 结果
     */
    public int updateBusiAskLeave(BusiAskLeaveVo busiAskLeaveVo);

    /**
     * 批量删除请假倒休
     * 
     * @param leaveIds 需要删除的请假倒休ID
     * @return 结果
     */
    public int deleteBusiAskLeaveByIds(Long[] leaveIds);

    /**
     * 删除请假倒休信息
     * 
     * @param leaveId 请假倒休ID
     * @return 结果
     */
    public int deleteBusiAskLeaveById(Long leaveId);

    public int leaveSumbit(Long[] leaveIds);
}
