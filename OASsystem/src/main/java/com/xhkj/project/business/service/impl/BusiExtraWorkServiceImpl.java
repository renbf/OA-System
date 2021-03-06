package com.xhkj.project.business.service.impl;

import java.util.Date;
import java.util.List;

import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiExtraWorkAprVo;
import com.xhkj.project.business.domain.vo.BusiExtraWorkVo;
import com.xhkj.project.system.domain.WorkflowBillTrace;
import com.xhkj.project.system.service.ISysWorkflowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiExtraWorkMapper;
import com.xhkj.project.business.domain.BusiExtraWork;
import com.xhkj.project.business.service.BusiExtraWorkService;
import org.springframework.transaction.annotation.Transactional;

import static com.xhkj.framework.web.domain.AjaxResult.CODE_TAG;

/**
 * 加班Service业务层处理
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@Service
public class BusiExtraWorkServiceImpl implements BusiExtraWorkService
{
    @Autowired
    private BusiExtraWorkMapper busiExtraWorkMapper;
    @Autowired
    private ISysWorkflowService sysWorkflowService;

    /**
     * 查询加班
     * 
     * @param extraWorkId 加班ID
     * @return 加班
     */
    @Override
    public BusiExtraWork selectBusiExtraWorkById(Long extraWorkId)
    {
        return busiExtraWorkMapper.selectBusiExtraWorkById(extraWorkId);
    }

    /**
     * 查询加班列表
     * 
     * @param busiExtraWork 加班
     * @return 加班
     */
    @Override
    public List<BusiExtraWork> selectBusiExtraWorkList(BusiExtraWork busiExtraWork)
    {
        return busiExtraWorkMapper.selectBusiExtraWorkList(busiExtraWork);
    }

    /**
     * 新增加班
     * 
     * @param busiExtraWork 加班
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBusiExtraWork(BusiExtraWorkVo busiExtraWorkVo)
    {
        BusiExtraWork busiExtraWork =  new BusiExtraWork();
        Long userId = Long.valueOf(SecurityUtils.getUserId());

        BeanUtils.copyProperties(busiExtraWorkVo,busiExtraWork);

        busiExtraWork.setCreateBy(String.valueOf(userId));
        busiExtraWork.setCreateTime(new Date());

        int num = busiExtraWorkMapper.insertBusiExtraWork(busiExtraWork);

        //如果是表单点提交 或 列表页点击报送按钮  则开启审批流程
        if(!busiExtraWorkVo.getSaveFlag()){
            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(busiExtraWork.getExtraWorkId());
            wfbt.setWorkflowId(busiExtraWork.getWorkflowId());

            //发起流程申请
            sysWorkflowService.submitToNextWorkflow(wfbt);
        }

        return num;
    }

    /**
     * 修改加班
     * 
     * @param busiExtraWork 加班
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBusiExtraWork(BusiExtraWorkVo busiExtraWorkVo)
    {

        Long userId = Long.valueOf(SecurityUtils.getUserId());

        //更新请假信息
        BusiExtraWork busiExtraWork =  new BusiExtraWork();
        BeanUtils.copyProperties(busiExtraWorkVo,busiExtraWork);
        busiExtraWork.setUpdateTime(new Date());
        busiExtraWork.setUpdateBy(String.valueOf(userId));

        int i = busiExtraWorkMapper.updateBusiExtraWork(busiExtraWork);

        //如果是表单点提交 或 列表页点击报送按钮  则开启审批流程
        if(!busiExtraWorkVo.getSaveFlag()){
            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(busiExtraWork.getExtraWorkId());
            wfbt.setWorkflowId(busiExtraWork.getWorkflowId());

            //发起流程申请
            sysWorkflowService.submitToNextWorkflow(wfbt);
        }

        return i;
    }

    /**
     * 批量删除加班
     * 
     * @param extraWorkIds 需要删除的加班ID
     * @return 结果
     */
    @Override
    public int deleteBusiExtraWorkByIds(Long[] extraWorkIds)
    {
        return busiExtraWorkMapper.deleteBusiExtraWorkByIds(extraWorkIds);
    }

    /**
     * 删除加班信息
     * 
     * @param extraWorkId 加班ID
     * @return 结果
     */
    @Override
    public int deleteBusiExtraWorkById(Long extraWorkId)
    {
        return busiExtraWorkMapper.deleteBusiExtraWorkById(extraWorkId);
    }


    @Override
    public AjaxResult extraWorkSumbit(Long[] extraWorkIds) {

        AjaxResult ajaxResult = null;
        for (int i = 0; i < extraWorkIds.length; i++) {
            Long extraWorkId = extraWorkIds[i];

            BusiExtraWork busiExtraWork = this.selectBusiExtraWorkById(extraWorkId);
            Long workflowId = busiExtraWork.getWorkflowId();

            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(extraWorkId);
            wfbt.setWorkflowId(workflowId);

            //发起流程申请
            ajaxResult = sysWorkflowService.submitToNextWorkflow(wfbt);

        }

        return ajaxResult;
    }



    @Override
    public List<BusiExtraWorkAprVo> approveList(BusiExtraWorkAprVo busiExtraWorkAprVo)
    {
        busiExtraWorkAprVo.setUserId(Long.valueOf(SecurityUtils.getUserId()));
        return busiExtraWorkMapper.approveList(busiExtraWorkAprVo);
    }


}
