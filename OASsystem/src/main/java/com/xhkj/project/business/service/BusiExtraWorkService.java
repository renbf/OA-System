package com.xhkj.project.business.service;

import java.util.List;
import com.xhkj.project.business.domain.BusiExtraWork;

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
    public int insertBusiExtraWork(BusiExtraWork busiExtraWork);

    /**
     * 修改加班
     * 
     * @param busiExtraWork 加班
     * @return 结果
     */
    public int updateBusiExtraWork(BusiExtraWork busiExtraWork);

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


    public int extraWorkSumbit(Long[] extraWorkIds);
}
