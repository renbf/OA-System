package com.xhkj.project.business.service;

import java.util.List;

import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.BusiExtraWork;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiExtraWorkAprVo;
import com.xhkj.project.business.domain.vo.BusiExtraWorkVo;

/**
 * 加班Service接口
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public interface BusiExtraWorkService
{
    /**
     * 查询加班
     * 
     * @param extraWorkId 加班ID
     * @return 加班
     */
    public BusiExtraWork selectBusiExtraWorkById(Long extraWorkId);

    /**
     * 查询加班列表
     * 
     * @param busiExtraWork 加班
     * @return 加班集合
     */
    public List<BusiExtraWork> selectBusiExtraWorkList(BusiExtraWork busiExtraWork);

    /**
     * 新增加班
     * 
     * @param busiExtraWork 加班
     * @return 结果
     */
    public int insertBusiExtraWork(BusiExtraWorkVo busiExtraWorkVo);

    /**
     * 修改加班
     * 
     * @param busiExtraWork 加班
     * @return 结果
     */
    public int updateBusiExtraWork(BusiExtraWorkVo busiExtraWorkVo);

    /**
     * 批量删除加班
     * 
     * @param extraWorkIds 需要删除的加班ID
     * @return 结果
     */
    public int deleteBusiExtraWorkByIds(Long[] extraWorkIds);

    /**
     * 删除加班信息
     * 
     * @param extraWorkId 加班ID
     * @return 结果
     */
    public int deleteBusiExtraWorkById(Long extraWorkId);


    public AjaxResult extraWorkSumbit(Long[] extraWorkIds);


    public List<BusiExtraWorkAprVo> approveList(BusiExtraWorkAprVo busiAskLeave);



}
