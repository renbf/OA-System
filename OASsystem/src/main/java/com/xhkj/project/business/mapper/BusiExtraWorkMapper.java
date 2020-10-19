package com.xhkj.project.business.mapper;

import java.util.List;
import com.xhkj.project.business.domain.BusiExtraWork;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiExtraWorkAprVo;

/**
 * 加班Mapper接口
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public interface BusiExtraWorkMapper 
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
     * 删除加班
     * 
     * @param extraWorkId 加班ID
     * @return 结果
     */
    public int deleteBusiExtraWorkById(Long extraWorkId);

    /**
     * 批量删除加班
     * 
     * @param extraWorkIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiExtraWorkByIds(Long[] extraWorkIds);


    public List<BusiExtraWorkAprVo> approveList(BusiExtraWorkAprVo busiExtraWorkAprVo);



}
