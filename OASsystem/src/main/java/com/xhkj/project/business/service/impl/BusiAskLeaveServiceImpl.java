package com.xhkj.project.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xhkj.common.constant.DictConst;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.framework.aspectj.lang.annotation.DataScope;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.business.domain.BusiHolsCheck;
import com.xhkj.project.business.domain.BusiReimburse;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiAskLeaveVo;
import com.xhkj.project.business.service.BusiHolsCheckService;
import com.xhkj.project.system.domain.SysCompanyConfig;
import com.xhkj.project.system.domain.WorkflowBillTrace;
import com.xhkj.project.system.service.ISysConfigService;
import com.xhkj.project.system.service.ISysWorkflowService;
import com.xhkj.project.system.service.SysCompanyConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiAskLeaveMapper;
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.service.BusiAskLeaveService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.xhkj.framework.web.domain.AjaxResult.CODE_TAG;


/**
 * 请假倒休Service业务层处理
 *
 * @author xhkj
 * @date 2020-04-15
 */
@Service
public class BusiAskLeaveServiceImpl implements BusiAskLeaveService
{

    @Autowired
    private BusiAskLeaveMapper busiAskLeaveMapper;
    @Autowired
    private SysCompanyConfigService sysCompanyConfigService;
    @Autowired
    private BusiHolsCheckService busiHolsCheckService;
    @Autowired
    private ISysWorkflowService sysWorkflowService;



    @Override
    public BusiAskLeave selectBusiAskLeaveById(Long leaveId) {
        return busiAskLeaveMapper.selectBusiAskLeaveById(leaveId);
    }

    /**
     * 查询请假倒休列表
     *
     * @param busiAskLeave 请假倒休
     * @return 请假倒休
     */
    @Override
    public List<BusiAskLeave> selectBusiAskLeaveList(BusiAskLeave busiAskLeave)
    {
        return busiAskLeaveMapper.selectBusiAskLeaveList(busiAskLeave);
    }

    /**
     * 新增请假倒休
     *
     * @param busiAskLeave 请假倒休
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBusiAskLeave(BusiAskLeaveVo busiAskLeaveVo)
    {

        BusiAskLeave busiAskLeave =  new BusiAskLeave();
        Long userId = Long.valueOf(SecurityUtils.getUserId());


        //新增请假信息
        BeanUtils.copyProperties(busiAskLeaveVo,busiAskLeave);

        busiAskLeave.setCreateTime(new Date());
        busiAskLeave.setCreateBy(String.valueOf(userId));
        String leaveDatesStr = busiAskLeave.getLeaveDates();
        if(StringUtils.isNotBlank(leaveDatesStr)){
            splitLeaveTime(busiAskLeave, leaveDatesStr);
        }

        int num = busiAskLeaveMapper.insertBusiAskLeave(busiAskLeave);


        //如果是表单点提交 或 列表页点击报送按钮  则开启审批流程
        if(!busiAskLeaveVo.getSaveFlag()){
            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(busiAskLeave.getLeaveId());
            wfbt.setWorkflowId(busiAskLeave.getWorkflowId());

            //发起流程申请
            sysWorkflowService.submitToNextWorkflow(wfbt,"busiAskLeaveServiceImpl","approvedToDo");
        }

        return num;
    }

    private void splitLeaveTime(BusiAskLeave busiAskLeave, String leaveDatesStr) {
        String start = "";
        String end ="";
        if(StringUtils.isNotBlank(leaveDatesStr)){
            Object array = JSONArray.parseArray(leaveDatesStr);
            if(array instanceof JSONArray){
                JSONArray leaveDates = (JSONArray) array;
                int size = leaveDates.size()-1;

                JSONArray startInfos = (JSONArray)leaveDates.get(0);
                JSONArray endInfos = (JSONArray)leaveDates.get(size);

                JSONArray startInfoArray = (JSONArray) startInfos.get(0);
                start = startInfoArray.get(0).toString().split(" ")[0];

                JSONArray endInfoArray = (JSONArray) endInfos.get(0);
                end = endInfoArray.get(1).toString().split(" ")[0];

                busiAskLeave.setLeaveBeginTime(start);
                busiAskLeave.setLeaveEndTime(end);
            }
        }
    }



    /**
     * 审核通过以后执行的方法
     * 更新加班、年休假时长
     * @param busiAskLeaveVo
     * @param userId
     */
    private void updateLeaveSurTime(BusiAskLeaveVo busiAskLeaveVo) {
        String leaveType = busiAskLeaveVo.getLeaveType();
        if(StringUtils.equals(leaveType, DictConst.BUS_LEAVE_TYPE_1)||StringUtils.equals(leaveType,DictConst.BUS_LEAVE_TYPE_4)){
            BusiHolsCheck busiHolsCheck = new BusiHolsCheck();
            //更新加班、年休假时长
            if(StringUtils.equals(leaveType,DictConst.BUS_LEAVE_TYPE_1)){
                busiHolsCheck.setLeaveRest(new BigDecimal(busiAskLeaveVo.getOvertimeSurTime()));
                busiHolsCheck.setLeaveRestUnit(busiAskLeaveVo.getLeaveHoursUnit());
            }
            if(StringUtils.equals(leaveType,DictConst.BUS_LEAVE_TYPE_4)){
                busiHolsCheck.setHolsRestDays(new BigDecimal(busiAskLeaveVo.getAnnLeaSurTime()));
            }

            busiHolsCheck.setUserId(Long.valueOf(SecurityUtils.getUserId()));
            busiHolsCheckService.updateBusiHolsCheck(busiHolsCheck);
        }
    }

    /**
     * 修改请假倒休
     *
     * @param busiAskLeave 请假倒休
     * @return 结果
     */


    @Override
    @Transactional
    public int updateBusiAskLeave(BusiAskLeaveVo busiAskLeaveVo)
    {
        Long userId = Long.valueOf(SecurityUtils.getUserId());

        //更新请假信息
        BusiAskLeave busiAskLeave =  new BusiAskLeave();
        BeanUtils.copyProperties(busiAskLeaveVo,busiAskLeave);
        busiAskLeave.setUpdateTime(new Date());
        busiAskLeave.setUpdateBy(String.valueOf(userId));

        String leaveDatesStr = busiAskLeave.getLeaveDates();
        if(StringUtils.isNotBlank(leaveDatesStr)){
            splitLeaveTime(busiAskLeave, leaveDatesStr);
        }

        int i = busiAskLeaveMapper.updateBusiAskLeave(busiAskLeave);

        //如果是表单点提交 或 列表页点击报送按钮  则开启审批流程
        if(!busiAskLeaveVo.getSaveFlag()){
            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(busiAskLeave.getLeaveId());
            wfbt.setWorkflowId(busiAskLeave.getWorkflowId());

            //发起流程申请
            sysWorkflowService.submitToNextWorkflow(wfbt,"busiAskLeaveServiceImpl","approvedToDo");
        }

        return i;
    }

    /**
     * 批量删除请假倒休
     *
     * @param leaveIds 需要删除的请假倒休ID
     * @return 结果
     */
    @Override
    public int deleteBusiAskLeaveByIds(Long[] leaveIds)
    {
        return busiAskLeaveMapper.deleteBusiAskLeaveByIds(leaveIds);
    }

    /**
     * 删除请假倒休信息
     *
     * @param leaveId 请假倒休ID
     * @return 结果
     */
    @Override
    public int deleteBusiAskLeaveById(Long leaveId)
    {
        return busiAskLeaveMapper.deleteBusiAskLeaveById(leaveId);
    }

    @Override
    public AjaxResult leaveSumbit(Long[] leaveIds) {
        AjaxResult ajaxResult = null;
        for (int i = 0; i < leaveIds.length; i++) {
            Long leaveId = leaveIds[i];

            BusiAskLeave busiAskLeave = this.selectBusiAskLeaveById(leaveId);
            Long workflowId = busiAskLeave.getWorkflowId();

            WorkflowBillTrace wfbt = new WorkflowBillTrace();
            wfbt.setBillId(leaveId);
            wfbt.setWorkflowId(workflowId);

            //发起流程申请
            ajaxResult = sysWorkflowService.submitToNextWorkflow(wfbt,"busiAskLeaveServiceImpl","approvedToDo");

        }

        return ajaxResult;

    }

    /**
     * 审核通过后执行的方法
     */
    public void approvedToDo(Long leaveId){
        BusiAskLeaveVo busiAskLeaveVo = busiAskLeaveMapper.selectBusiAskLeaveVoById(leaveId);
        updateLeaveSurTime(busiAskLeaveVo);
    }


    @Override
    public List<BusiAskLeaveAprVo> approveList(BusiAskLeaveAprVo busiAskLeave)
    {
        busiAskLeave.setUserId(Long.valueOf(SecurityUtils.getUserId()));
        return busiAskLeaveMapper.approveList(busiAskLeave);
    }

}
