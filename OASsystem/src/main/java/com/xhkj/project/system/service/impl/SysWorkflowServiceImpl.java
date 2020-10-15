package com.xhkj.project.system.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.xhkj.common.constant.UserConstants;
import com.xhkj.common.exception.CustomException;
import com.xhkj.common.utils.ReflectionUtils;
import com.xhkj.common.utils.ServletUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.common.utils.spring.SpringUtils;
import com.xhkj.framework.security.LoginUser;
import com.xhkj.framework.security.service.TokenService;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.system.domain.*;
import com.xhkj.project.system.domain.vo.SysWorkflowNodeVo;
import com.xhkj.project.system.domain.vo.SysWorkflowStepVo;
import com.xhkj.project.system.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.system.service.ISysWorkflowService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;

/**
 * 流程定义Service业务层处理
 * 
 * @author xhkj
 * @date 2020-03-31
 */
@Service
public class SysWorkflowServiceImpl implements ISysWorkflowService 
{
    private static final Logger log = LoggerFactory.getLogger(SysWorkflowServiceImpl.class);
    @Autowired
    private SysWorkflowMapper sysWorkflowMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysWorkflowStepMapper sysWorkflowStepMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysWorkflowStepNodeMapper sysWorkflowStepNodeMapper;
    @Autowired
    private SysWorkflowNodeMapper sysWorkflowNodeMapper;
    @Autowired
    private SysWorkflowNodeCheckerMapper sysWorkflowNodeCheckerMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysWorkflowGroupMapper sysWorkflowGroupMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private WorkflowBillMapper workflowBillMapper;
    @Autowired
    private WorkflowBillTraceMapper workflowBillTraceMapper;

    /**
     * 查询流程
     *
     * @param workflowId 流程ID
     * @return 流程
     */
    @Override
    public SysWorkflow selectSysWorkflowById(Long workflowId)
    {
        return sysWorkflowMapper.selectSysWorkflowById(workflowId);
    }

    /**
     * 查询流程列表
     *
     * @param sysWorkflow 流程
     * @return 流程
     */
    @Override
    public List<SysWorkflow> selectSysWorkflowList(SysWorkflow sysWorkflow)
    {
        return sysWorkflowMapper.selectSysWorkflows(sysWorkflow);
    }

    /**
     * 新增流程
     *
     * @param sysWorkflow 流程
     * @return 结果
     */
    @Override
    public AjaxResult insertSysWorkflow(SysWorkflow sysWorkflow)
    {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            sysWorkflow.setStatus("1");
            sysWorkflow.setWorkflowNodeNum(0);
            sysWorkflow.setCreateBy(username);
            sysWorkflow.setCreateTime(now);
            sysWorkflow.setUpdateTime(now);
            sysWorkflowMapper.insertSysWorkflow(sysWorkflow);
            Long workflowId = sysWorkflow.getWorkflowId();
            return AjaxResult.success(workflowId);
        } catch (Exception e) {
            log.error("新增流程名称异常",e);
            throw new CustomException("新增流程名称异常");
        }
    }

    @Override
    public AjaxResult updateSysWorkflow(SysWorkflow sysWorkflow) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            sysWorkflow.setUpdateBy(username);
            sysWorkflow.setUpdateTime(now);
            sysWorkflowMapper.updateSysWorkflow(sysWorkflow);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("修改流程异常",e);
            throw new CustomException("修改流程异常");
        }
    }

    @Override
    @Transactional
    public AjaxResult insertSysWorkflowStep(SysWorkflowStepVo sysWorkflowStepVo) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            Long deptId = sysWorkflowStepVo.getDeptId();
            Long workflowId = sysWorkflowStepVo.getWorkflowId();
            Integer sortOrder = null;
            SysDept sysDept = sysDeptMapper.selectDeptById(deptId);
            List<SysWorkflowStep> sysWorkflowSteps = sysWorkflowStepMapper.selectSysWorkflowStepsByWorkflowId(workflowId);
            if (CollectionUtils.isEmpty(sysWorkflowSteps)) {
                sortOrder = 1;
            }else{
                SysWorkflowStep sysWorkflowStep = sysWorkflowSteps.get(sysWorkflowSteps.size() - 1);
                sortOrder = sysWorkflowStep.getSortOrder() + 1;
            }
            SysWorkflowStep sysWorkflowStep = new SysWorkflowStep();
            sysWorkflowStep.setWorkflowStepName(sysDept.getDeptName());
            sysWorkflowStep.setWorkflowId(workflowId);
            sysWorkflowStep.setSortOrder(sortOrder);
            //暂时不用
            sysWorkflowStep.setStepType("0");
            sysWorkflowStep.setCreateBy(username);
            sysWorkflowStep.setCreateTime(now);
            sysWorkflowStep.setUpdateTime(now);
            sysWorkflowStepMapper.insertSysWorkflowStep(sysWorkflowStep);
            Long workflowStepId = sysWorkflowStep.getWorkflowStepId();

            if (Objects.isNull(deptId)) {
                return AjaxResult.error("部门不能为空");
            }else{
                List<SysWorkflowStepNode> list = new ArrayList<>();
                SysWorkflowStepNode sysWorkflowStepNode = null;
                SysUser deptAdmin = sysUserMapper.selectDeptLeaderByDeptId(deptId);
                if (Objects.isNull(deptAdmin)) {
                    throw new CustomException("部门还没有管理员");
                }else{
                    sysWorkflowStepNode = new SysWorkflowStepNode();
                    sysWorkflowStepNode.setWorkflowStepId(workflowStepId);
                    sysWorkflowStepNode.setStepType("0");
                    sysWorkflowStepNode.setDeptId(deptId);
                    sysWorkflowStepNode.setCreateBy(username);
                    sysWorkflowStepNode.setCreateTime(now);
                    sysWorkflowStepNode.setUpdateTime(now);
                    sysWorkflowStepNodeMapper.insertSysWorkflowStepNode(sysWorkflowStepNode);
                    Long workflowStepNodeId = sysWorkflowStepNode.getWorkflowStepNodeId();
                    SysWorkflowNode sysWorkflowNode = new SysWorkflowNode();
                    sysWorkflowNode.setWorkflowStepNodeId(workflowStepNodeId);
                    sysWorkflowNode.setWorkflowNodeName("部门主管审批");
                    sysWorkflowNode.setWorkflowNodeCheckerType("2");
                    sysWorkflowNode.setWorkflowNodeCheckType("1");
                    sysWorkflowNode.setIsMessage("0");
                    sysWorkflowNode.setSortOrder(1);
                    sysWorkflowNode.setCreateBy(username);
                    sysWorkflowNode.setCreateTime(now);
                    sysWorkflowNode.setUpdateTime(now);
                    sysWorkflowNodeMapper.insertSysWorkflowNode(sysWorkflowNode);
                    Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
                    SysWorkflowNodeChecker sysWorkflowNodeChecker = new SysWorkflowNodeChecker();
                    sysWorkflowNodeChecker.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeChecker.setWorkflowNodeName("部门主管审批");
                    sysWorkflowNodeChecker.setWorkflowNodeCheckerType("2");
                    sysWorkflowNodeChecker.setWorkflowNodeUserId(deptAdmin.getUserId());
                    sysWorkflowNodeChecker.setWorkflowNodeUserName(deptAdmin.getNickName());
                    sysWorkflowNodeChecker.setCreateBy(username);
                    sysWorkflowNodeChecker.setCreateTime(now);
                    sysWorkflowNodeChecker.setUpdateTime(now);
                    sysWorkflowNodeCheckerMapper.insertSysWorkflowNodeChecker(sysWorkflowNodeChecker);
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新增流程步骤异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("新增流程步骤异常");
            }
        }
    }

    /**
     * 校验流程是否唯一
     *
     * @param sysWorkflow 流程信息
     * @return 结果
     */
    @Override
    public String checkWorkFlowUnique(SysWorkflow sysWorkflow)
    {
        SysWorkflow chkInfo = selectSysWorkflowById(sysWorkflow.getWorkflowId());
        if (StringUtils.isNotNull(chkInfo))
        {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public AjaxResult updateSysWorkflowStep(SysWorkflowStepVo sysWorkflowStepVo) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            Long workflowStepId = sysWorkflowStepVo.getWorkflowStepId();
            SysWorkflowStep sysWorkflowStep = new SysWorkflowStep();
            sysWorkflowStep.setWorkflowStepId(sysWorkflowStepVo.getWorkflowStepId());
            sysWorkflowStep.setWorkflowStepName(sysWorkflowStepVo.getWorkflowStepName());
            sysWorkflowStep.setWorkflowId(sysWorkflowStepVo.getWorkflowId());
            sysWorkflowStep.setSortOrder(sysWorkflowStepVo.getSortOrder());
            sysWorkflowStep.setUpdateBy(username);
            sysWorkflowStep.setUpdateTime(now);
            sysWorkflowStepMapper.updateSysWorkflowStep(sysWorkflowStep);
            Long deptId = sysWorkflowStepVo.getDeptId();
            if (Objects.isNull(deptId)) {
                return AjaxResult.error("部门不能为空");
            }else{
                SysWorkflowStepNode sysWorkflowStepNodeOld = sysWorkflowStepNodeMapper.selectSysWorkflowStepNodeByStepId(workflowStepId);
                if (Objects.nonNull(sysWorkflowStepNodeOld)) {
                        Long deptIdOld = sysWorkflowStepNodeOld.getDeptId();
                        Long workflowStepNodeIdOld = sysWorkflowStepNodeOld.getWorkflowStepNodeId();
                        if (deptId != deptIdOld) {
                            List<SysWorkflowNode> sysWorkflowNodes = sysWorkflowNodeMapper.selectSysWorkflowNodesByStepNodeId(workflowStepNodeIdOld);
                            if (CollectionUtils.isNotEmpty(sysWorkflowNodes)) {
                                for (SysWorkflowNode sysWorkflowNode : sysWorkflowNodes) {
                                    Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
                                    sysWorkflowNodeMapper.deleteSysWorkflowNodeById(workflowNodeId);
                                    sysWorkflowNodeCheckerMapper.deleteSysWorkflowNodeCheckerByNodeId(workflowNodeId);
                                }
                            }
                            sysWorkflowStepNodeMapper.deleteSysWorkflowStepNodeById(workflowStepNodeIdOld);

                            SysUser deptAdmin = sysUserMapper.selectDeptLeaderByDeptId(deptId);
                            if (Objects.isNull(deptAdmin)) {
                                throw new CustomException("部门还没有管理员");
                            }else{
                                SysWorkflowStepNode sysWorkflowStepNode = new SysWorkflowStepNode();
                                sysWorkflowStepNode.setWorkflowStepId(workflowStepId);
                                sysWorkflowStepNode.setStepType("0");
                                sysWorkflowStepNode.setDeptId(deptId);
                                sysWorkflowStepNode.setCreateBy(username);
                                sysWorkflowStepNode.setCreateTime(now);
                                sysWorkflowStepNode.setUpdateTime(now);
                                sysWorkflowStepNodeMapper.insertSysWorkflowStepNode(sysWorkflowStepNode);
                                Long workflowStepNodeId = sysWorkflowStepNode.getWorkflowStepNodeId();
                                SysWorkflowNode sysWorkflowNode = new SysWorkflowNode();
                                sysWorkflowNode.setWorkflowStepNodeId(workflowStepNodeId);
                                sysWorkflowNode.setWorkflowNodeName("部门主管审批");
                                sysWorkflowNode.setWorkflowNodeCheckerType("2");
                                sysWorkflowNode.setWorkflowNodeCheckType("1");
                                sysWorkflowNode.setIsMessage("0");
                                sysWorkflowNode.setSortOrder(1);
                                sysWorkflowNode.setCreateBy(username);
                                sysWorkflowNode.setCreateTime(now);
                                sysWorkflowNode.setUpdateTime(now);
                                sysWorkflowNodeMapper.insertSysWorkflowNode(sysWorkflowNode);
                                Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
                                SysWorkflowNodeChecker sysWorkflowNodeChecker = new SysWorkflowNodeChecker();
                                sysWorkflowNodeChecker.setWorkflowNodeId(workflowNodeId);
                                sysWorkflowNodeChecker.setWorkflowNodeName("部门主管审批");
                                sysWorkflowNodeChecker.setWorkflowNodeCheckerType("2");
                                sysWorkflowNodeChecker.setWorkflowNodeUserId(deptAdmin.getUserId());
                                sysWorkflowNodeChecker.setWorkflowNodeUserName(deptAdmin.getNickName());
                                sysWorkflowNodeChecker.setCreateBy(username);
                                sysWorkflowNodeChecker.setCreateTime(now);
                                sysWorkflowNodeChecker.setUpdateTime(now);
                                sysWorkflowNodeCheckerMapper.insertSysWorkflowNodeChecker(sysWorkflowNodeChecker);
                            }
                        }
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新增流程步骤异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("新增流程步骤异常");
            }
        }
    }

    @Override
    public AjaxResult startAndEnd(Long workflowId,String status) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            SysWorkflow sysWorkflow = sysWorkflowMapper.selectSysWorkflowById(workflowId);
            if (Objects.nonNull(sysWorkflow)) {
                String status1 = sysWorkflow.getStatus();
                if (!status1.equals(status)) {
                    if ("1".equals(status)) {
                        //启用
                        SysWorkflow sysWorkflowUp = new SysWorkflow();
                        sysWorkflowUp.setWorkflowId(workflowId);
                        sysWorkflowUp.setStatus("1");
                        sysWorkflowMapper.updateSysWorkflow(sysWorkflowUp);
                    }else{
                        //停用
                        int count = sysWorkflowMapper.checkWorkBillIsUsed(workflowId);
                        if (count > 0) {
                            return AjaxResult.error("流程正在使用中不能停用");
                        }
                        SysWorkflow sysWorkflowUp = new SysWorkflow();
                        sysWorkflowUp.setWorkflowId(workflowId);
                        sysWorkflowUp.setStatus("0");
                        sysWorkflowMapper.updateSysWorkflow(sysWorkflowUp);
                    }
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("启用停用流程异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("启用停用流程异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult remove(Long workflowId) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            sysWorkflowMapper.deleteSysWorkflowById(workflowId);
            List<SysWorkflowStep> sysWorkflowSteps = sysWorkflowStepMapper.selectSysWorkflowStepsByWorkflowId(workflowId);
            if (CollectionUtils.isNotEmpty(sysWorkflowSteps)) {
                for (SysWorkflowStep sysWorkflowStep : sysWorkflowSteps) {
                    Long workflowStepId = sysWorkflowStep.getWorkflowStepId();
                    SysWorkflowStepNode sysWorkflowStepNode = sysWorkflowStepNodeMapper.selectSysWorkflowStepNodeByStepId(workflowStepId);
                    if (Objects.nonNull(sysWorkflowStepNode)) {
                        Long workflowStepNodeId = sysWorkflowStepNode.getWorkflowStepNodeId();
                        List<SysWorkflowNode> sysWorkflowNodes = sysWorkflowNodeMapper.selectSysWorkflowNodesByStepNodeId(workflowStepNodeId);
                        if (CollectionUtils.isNotEmpty(sysWorkflowNodes)) {
                            for (SysWorkflowNode sysWorkflowNode : sysWorkflowNodes) {
                                Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
                                sysWorkflowNodeMapper.deleteSysWorkflowNodeById(workflowNodeId);
                                sysWorkflowNodeCheckerMapper.deleteSysWorkflowNodeCheckerByNodeId(workflowNodeId);
                            }
                        }
                        sysWorkflowStepNodeMapper.deleteSysWorkflowStepNodeByStepId(workflowStepId);
                    }
                }
                sysWorkflowStepMapper.deleteSysWorkflowStepByWorkflowId(workflowId);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("删除流程异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("删除流程异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult removeStep(Long workflowStepId) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            SysWorkflowStepNode sysWorkflowStepNode = sysWorkflowStepNodeMapper.selectSysWorkflowStepNodeByStepId(workflowStepId);
            if (Objects.nonNull(sysWorkflowStepNode)) {
                int count = sysWorkflowMapper.checkWorkStepIsUsed(workflowStepId);
                if (count > 0) {
                    return AjaxResult.error("流程节点正在使用中不能修改");
                }
                Long workflowStepNodeId = sysWorkflowStepNode.getWorkflowStepNodeId();
                List<SysWorkflowNode> sysWorkflowNodes = sysWorkflowNodeMapper.selectSysWorkflowNodesByStepNodeId(workflowStepNodeId);
                if (CollectionUtils.isNotEmpty(sysWorkflowNodes)) {
                    for (SysWorkflowNode sysWorkflowNode : sysWorkflowNodes) {
                        Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
                        sysWorkflowNodeMapper.deleteSysWorkflowNodeById(workflowNodeId);
                        sysWorkflowNodeCheckerMapper.deleteSysWorkflowNodeCheckerByNodeId(workflowNodeId);
                    }
                }
                sysWorkflowStepNodeMapper.deleteSysWorkflowStepNodeByStepId(workflowStepId);
                sysWorkflowStepMapper.deleteSysWorkflowStepById(workflowStepId);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("删除流程异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("删除流程异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult addWorkflowNode(SysWorkflowNodeVo sysWorkflowNodeVo) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            Long workflowStepNodeId = sysWorkflowNodeVo.getWorkflowStepNodeId();
            String workflowNodeName = sysWorkflowNodeVo.getWorkflowNodeName();
            String workflowNodeCheckerType = sysWorkflowNodeVo.getWorkflowNodeCheckerType();
            Integer sortOrder = sysWorkflowNodeVo.getSortOrder();
            SysWorkflowNode sysWorkflowNode = new SysWorkflowNode();
            sysWorkflowNode.setWorkflowStepNodeId(workflowStepNodeId);
            sysWorkflowNode.setWorkflowNodeCheckerType(workflowNodeCheckerType);
            sysWorkflowNode.setWorkflowNodeCheckType("3");
            sysWorkflowNode.setWorkflowNodeName(workflowNodeName);
            sysWorkflowNode.setIsMessage("0");
            sysWorkflowNode.setSortOrder(sortOrder + 1);
            sysWorkflowNode.setCreateBy(username);
            sysWorkflowNode.setCreateTime(now);
            sysWorkflowNode.setUpdateTime(now);
            sysWorkflowNodeMapper.insertSysWorkflowNode(sysWorkflowNode);
            Long workflowNodeId = sysWorkflowNode.getWorkflowNodeId();
            //指定人员
            List<Long> workflowNodeUserIds = sysWorkflowNodeVo.getWorkflowNodeUserIds();
            for (Long workflowNodeUserId : workflowNodeUserIds) {
                SysUser sysUser = sysUserMapper.selectUserById(workflowNodeUserId);
                SysWorkflowNodeChecker sysWorkflowNodeChecker = new SysWorkflowNodeChecker();
                sysWorkflowNodeChecker.setWorkflowNodeId(workflowNodeId);
                sysWorkflowNodeChecker.setWorkflowNodeName(workflowNodeName);
                sysWorkflowNodeChecker.setWorkflowNodeCheckerType(workflowNodeCheckerType);
                sysWorkflowNodeChecker.setWorkflowNodeUserId(workflowNodeUserId);
                sysWorkflowNodeChecker.setWorkflowNodeUserName(sysUser.getNickName());
                sysWorkflowNodeChecker.setCreateBy(username);
                sysWorkflowNodeChecker.setCreateTime(now);
                sysWorkflowNodeChecker.setUpdateTime(now);
                sysWorkflowNodeCheckerMapper.insertSysWorkflowNodeChecker(sysWorkflowNodeChecker);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新增流程节点异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("新增流程节点异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult editWorkflowNode(SysWorkflowNodeVo sysWorkflowNodeVo) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            Long workflowNodeId = sysWorkflowNodeVo.getWorkflowNodeId();
            String workflowNodeCheckerType = sysWorkflowNodeVo.getWorkflowNodeCheckerType();
            String workflowNodeName = sysWorkflowNodeVo.getWorkflowNodeName();
            int count = sysWorkflowMapper.checkWorkNodeIsUsed(workflowNodeId);
            if (count > 0) {
                return AjaxResult.error("流程节点正在使用中不能修改");
            }
            SysWorkflowNode sysWorkflowNode = new SysWorkflowNode();
            sysWorkflowNode.setWorkflowNodeId(workflowNodeId);
            sysWorkflowNode.setWorkflowNodeCheckerType(workflowNodeCheckerType);
            sysWorkflowNode.setWorkflowNodeCheckType(sysWorkflowNodeVo.getWorkflowNodeCheckType());
            sysWorkflowNode.setWorkflowNodeName(workflowNodeName);
            sysWorkflowNode.setUpdateBy(username);
            sysWorkflowNode.setUpdateTime(now);
            sysWorkflowNodeMapper.updateSysWorkflowNode(sysWorkflowNode);
            sysWorkflowNodeCheckerMapper.deleteSysWorkflowNodeCheckerByNodeId(workflowNodeId);
            if ("1".equals(workflowNodeCheckerType)) {
                //指定角色
                List<Long> workflowNodeRoleIds = sysWorkflowNodeVo.getWorkflowNodeRoleIds();
                for (Long workflowNodeRoleId : workflowNodeRoleIds) {
                    SysRole sysRole = sysRoleMapper.selectRoleById(workflowNodeRoleId);
                    SysWorkflowNodeChecker sysWorkflowNodeChecker = new SysWorkflowNodeChecker();
                    sysWorkflowNodeChecker.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeChecker.setWorkflowNodeName(workflowNodeName);
                    sysWorkflowNodeChecker.setWorkflowNodeCheckerType("1");
                    sysWorkflowNodeChecker.setWorkflowNodeRoleId(workflowNodeRoleId);
                    sysWorkflowNodeChecker.setWorkflowNodeRoleName(sysRole.getRoleName());
                    sysWorkflowNodeChecker.setCreateBy(username);
                    sysWorkflowNodeChecker.setCreateTime(now);
                    sysWorkflowNodeChecker.setUpdateTime(now);
                    sysWorkflowNodeCheckerMapper.insertSysWorkflowNodeChecker(sysWorkflowNodeChecker);
                }
            }else{
                //指定人员
                List<Long> workflowNodeUserIds = sysWorkflowNodeVo.getWorkflowNodeUserIds();
                for (Long workflowNodeUserId : workflowNodeUserIds) {
                    SysUser sysUser = sysUserMapper.selectUserById(workflowNodeUserId);
                    SysWorkflowNodeChecker sysWorkflowNodeChecker = new SysWorkflowNodeChecker();
                    sysWorkflowNodeChecker.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeChecker.setWorkflowNodeName(workflowNodeName);
                    sysWorkflowNodeChecker.setWorkflowNodeCheckerType("2");
                    sysWorkflowNodeChecker.setWorkflowNodeUserId(workflowNodeUserId);
                    sysWorkflowNodeChecker.setWorkflowNodeUserName(sysUser.getNickName());
                    sysWorkflowNodeChecker.setCreateBy(username);
                    sysWorkflowNodeChecker.setCreateTime(now);
                    sysWorkflowNodeChecker.setUpdateTime(now);
                    sysWorkflowNodeCheckerMapper.insertSysWorkflowNodeChecker(sysWorkflowNodeChecker);
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("修改流程节点异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("修改流程节点异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult deleteWorkflowNode(Long workflowNodeId) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            SysWorkflowNode sysWorkflowNode = sysWorkflowNodeMapper.selectSysWorkflowNodeById(workflowNodeId);
            if(Objects.isNull(sysWorkflowNode)){
                return AjaxResult.error("流程节点为空");
            }
            Integer sortOrder = sysWorkflowNode.getSortOrder();
            if (sortOrder == 1) {
                return AjaxResult.error("第一个流程节点不能删除");
            }
            int count = sysWorkflowMapper.checkWorkNodeIsUsed(workflowNodeId);
            if (count > 0) {
                return AjaxResult.error("流程节点正在使用中不能修改");
            }
            sysWorkflowNodeMapper.deleteSysWorkflowNodeById(workflowNodeId);
            sysWorkflowNodeCheckerMapper.deleteSysWorkflowNodeCheckerByNodeId(workflowNodeId);
            sysWorkflowNode.setUpdateTime(now);
            sysWorkflowNode.setUpdateBy(username);
            sysWorkflowNodeMapper.updateSysWorkflowNodeNextSort(sysWorkflowNode);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("删除流程节点异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("删除流程节点异常");
            }
        }
    }

    @Override
    @Transactional
    public AjaxResult moveWorkflowNode(Long workflowNodeId, String flag) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            SysWorkflowNode sysWorkflowNode = sysWorkflowNodeMapper.selectSysWorkflowNodeById(workflowNodeId);
            if(Objects.isNull(sysWorkflowNode)){
                return AjaxResult.error("流程节点为空");
            }
            if (sysWorkflowNode.getSortOrder() == 1) {
                return AjaxResult.error("第一个流程节点不能移动");
            }
            int count = sysWorkflowMapper.checkWorkNodeIsUsed(workflowNodeId);
            if (count > 0) {
                return AjaxResult.error("流程节点正在使用中不能移动");
            }
            Long workflowStepNodeId = sysWorkflowNode.getWorkflowStepNodeId();
            Integer sortOrder = sysWorkflowNode.getSortOrder();
            if ("next".equals(flag)) {
                Integer sortOrderNext = sortOrder + 1;
                SysWorkflowNode sysWorkflowNode1 = sysWorkflowNodeMapper.selectNextSysWorkflowNodeBySortOrder(workflowStepNodeId, sortOrder);
                if (Objects.nonNull(sysWorkflowNode1)) {
                    Long workflowNodeId1 = sysWorkflowNode1.getWorkflowNodeId();
                    int count1 = sysWorkflowMapper.checkWorkNodeIsUsed(workflowNodeId1);
                    if (count1 > 0) {
                        return AjaxResult.error("下一个流程节点正在使用中不能移动");
                    }
                    SysWorkflowNode sysWorkflowNodeUpToNext = new SysWorkflowNode();
                    sysWorkflowNodeUpToNext.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeUpToNext.setSortOrder(sortOrderNext);
                    sysWorkflowNodeMapper.updateSysWorkflowNode(sysWorkflowNodeUpToNext);
                    SysWorkflowNode sysWorkflowNodeNextToUp = new SysWorkflowNode();
                    sysWorkflowNodeNextToUp.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeNextToUp.setSortOrder(sortOrder);
                    sysWorkflowNodeMapper.updateSysWorkflowNode(sysWorkflowNodeNextToUp);
                }
            }else if ("up".equals(flag)) {
                Integer sortOrderUp = sortOrder - 1;
                SysWorkflowNode sysWorkflowNode1 = sysWorkflowNodeMapper.selectUpSysWorkflowNodeBySortOrder(workflowStepNodeId, sortOrder);
                if (Objects.nonNull(sysWorkflowNode1)) {
                    Long workflowNodeId1 = sysWorkflowNode1.getWorkflowNodeId();
                    int count1 = sysWorkflowMapper.checkWorkNodeIsUsed(workflowNodeId1);
                    if (count1 > 0) {
                        return AjaxResult.error("上一个流程节点正在使用中不能移动");
                    }
                    SysWorkflowNode sysWorkflowNodeUpToNext = new SysWorkflowNode();
                    sysWorkflowNodeUpToNext.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeUpToNext.setSortOrder(sortOrder);
                    sysWorkflowNodeMapper.updateSysWorkflowNode(sysWorkflowNodeUpToNext);
                    SysWorkflowNode sysWorkflowNodeNextToUp = new SysWorkflowNode();
                    sysWorkflowNodeNextToUp.setWorkflowNodeId(workflowNodeId);
                    sysWorkflowNodeNextToUp.setSortOrder(sortOrderUp);
                    sysWorkflowNodeMapper.updateSysWorkflowNode(sysWorkflowNodeNextToUp);
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("移动流程节点异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("移动流程节点异常");
            }
        }
    }

    @Override
    public List<SysWorkflowGroup> selectSysWorkflowGroupList(SysWorkflowGroup sysWorkflowGroup) {
        List<SysWorkflowGroup> sysWorkflowGroups = sysWorkflowGroupMapper.selectSysWorkflowGroups(null);
        return sysWorkflowGroups;
    }

    @Override
    public AjaxResult workflowStepList(Long workflowId) {
        List<SysWorkflowStepVo> sysWorkflowStepVos = sysWorkflowStepMapper.selectSysWorkflowStepVosByWorkflowId(workflowId);
        return AjaxResult.success(sysWorkflowStepVos);
    }

    @Override
    public AjaxResult getSysWorkflowNodes(Long workflowStepNodeId) {
        List<SysWorkflowNode> sysWorkflowNodes = sysWorkflowNodeMapper.selectSysWorkflowNodesByStepNodeId(workflowStepNodeId);
        return AjaxResult.success(sysWorkflowNodes);
    }

    @Transactional
    public AjaxResult submitToNextWorkflow(WorkflowBillTrace workflowBillTrace) {
        return submitToNextWorkflowDo(workflowBillTrace,null,null);
    }

    /**
     * 执行回调方法
     * @param workflowBillTrace
     * @param className  bean 名称
     * @param methodName 方法名称
     * @return
     */
    @Override
    public AjaxResult submitToNextWorkflow(WorkflowBillTrace workflowBillTrace,String className,String methodName) {
        return submitToNextWorkflowDo(workflowBillTrace,className,methodName);
    }


    @Transactional
    public AjaxResult submitToNextWorkflowDo(WorkflowBillTrace workflowBillTrace,String className,String methodName) {
        try {
            Date now = new Date();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            SysUser user = loginUser.getUser();
            Long billId = workflowBillTrace.getBillId();
            Long workflowId = workflowBillTrace.getWorkflowId();
            String checkStatus = workflowBillTrace.getCheckStatus();
            String billStatus = null;
            WorkflowBill workflowBill = workflowBillMapper.selectWorkflowBillByKey(billId, workflowId);
            if (Objects.isNull(workflowBill)) {
                //提交审核
                billStatus = "0";
                WorkflowBill firstWorkflowBillNode = workflowBillMapper.selectNextNode(workflowId, null);
                if (Objects.isNull(firstWorkflowBillNode)) {
                    return AjaxResult.error("流程节点为空");
                }

                workflowBill = new WorkflowBill();
                workflowBill.setBillId(billId);
                workflowBill.setWorkflowId(workflowId);
                workflowBill.setWorkflowName(firstWorkflowBillNode.getWorkflowName());
                workflowBill.setWorkflowGroupId(firstWorkflowBillNode.getWorkflowGroupId());
                workflowBill.setWorkflowStepId(firstWorkflowBillNode.getWorkflowStepId());
                workflowBill.setWorkflowStepNodeId(firstWorkflowBillNode.getWorkflowStepNodeId());
                workflowBill.setWorkflowNodeId(firstWorkflowBillNode.getWorkflowNodeId());
                workflowBill.setBillStatus(billStatus);
                workflowBill.setOrginHandler(username);
                workflowBill.setCreateBy(username);
                workflowBill.setCreateTime(now);
                workflowBill.setUpdateTime(now);

                workflowBill.setClassName(className);
                workflowBill.setMethodName(methodName);
                workflowBillMapper.insertWorkflowBill(workflowBill);
            }else{
                Long deptId = user.getDeptId();
                SysDept sysDept = sysDeptMapper.selectDeptById(deptId);

                WorkflowBill workflowBillUp = new WorkflowBill();
                workflowBillUp.setBillId(billId);
                workflowBillUp.setWorkflowId(workflowId);
                if (StringUtils.isEmpty(checkStatus) && "-1".equals(workflowBill.getBillStatus())) {
                    //重新提交审核
                    billStatus = "0";
                    WorkflowBill firstWorkflowBillNode = workflowBillMapper.selectNextNode(workflowId, null);
                    if (Objects.isNull(firstWorkflowBillNode)) {
                        return AjaxResult.error("流程节点为空");
                    }
                    workflowBillUp.setWorkflowName(firstWorkflowBillNode.getWorkflowName());
                    workflowBillUp.setWorkflowGroupId(firstWorkflowBillNode.getWorkflowGroupId());
                    workflowBillUp.setWorkflowStepId(firstWorkflowBillNode.getWorkflowStepId());
                    workflowBillUp.setWorkflowStepNodeId(firstWorkflowBillNode.getWorkflowStepNodeId());
                    workflowBillUp.setWorkflowNodeId(firstWorkflowBillNode.getWorkflowNodeId());
                    workflowBillUp.setBillStatus(billStatus);
                    workflowBillUp.setUpdateBy(username);
                    workflowBillUp.setUpdateTime(now);
                    workflowBillMapper.updateWorkflowBillByKey(workflowBillUp);
                }else{
                    if ("1".equals(checkStatus)) {
                        //通过
                        WorkflowBill nextWorkflowBillNode = workflowBillMapper.selectNextNode(workflowId, workflowBill.getWorkflowNodeId());
                        if (Objects.isNull(nextWorkflowBillNode)) {
                            //审核完成
                            billStatus = "99";
                            workflowBillUp.setWorkflowStepId(workflowBill.getWorkflowStepId());
                            workflowBillUp.setWorkflowStepNodeId(workflowBill.getWorkflowStepNodeId());
                            workflowBillUp.setWorkflowNodeId(workflowBill.getWorkflowNodeId());


                            String classNamePass = workflowBill.getClassName();
                            String methodNamePass  = workflowBill.getMethodName();
                            //如果有回调方法  则进行调用
                            if(StringUtils.isNoneBlank(classNamePass) && StringUtils.isNoneBlank(methodNamePass)){
                                Class[] parameterTypes = {Long.class};
                                Object[] parameters = {billId};

                                try {
                                    ReflectionUtils.invokeMethod(classNamePass,methodNamePass,parameterTypes,parameters);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            //审核中
                            billStatus = "1";
                            workflowBillUp.setWorkflowStepId(nextWorkflowBillNode.getWorkflowStepId());
                            workflowBillUp.setWorkflowStepNodeId(nextWorkflowBillNode.getWorkflowStepNodeId());
                            workflowBillUp.setWorkflowNodeId(nextWorkflowBillNode.getWorkflowNodeId());
                        }
                    }else{
                        //拒绝
                        billStatus = "-1";
                    }
                    workflowBillUp.setBillStatus(billStatus);
                    workflowBillUp.setUpdateBy(username);
                    workflowBillUp.setUpdateTime(now);
                    workflowBillMapper.updateWorkflowBillByKey(workflowBillUp);

                    workflowBillTrace.setWorkflowName(workflowBill.getWorkflowName());
                    workflowBillTrace.setWorkflowGroupId(workflowBill.getWorkflowGroupId());
                    workflowBillTrace.setWorkflowStepId(workflowBill.getWorkflowStepId());
                    workflowBillTrace.setWorkflowStepNodeId(workflowBill.getWorkflowStepNodeId());
                    workflowBillTrace.setWorkflowNodeId(workflowBill.getWorkflowNodeId());
                    workflowBillTrace.setCheckerUserId(user.getUserId());
                    workflowBillTrace.setCheckerUserName(user.getUserName());
                    workflowBillTrace.setCheckerDeptName(sysDept.getDeptName());
                    workflowBillTrace.setCreateBy(username);
                    workflowBillTrace.setCreateTime(now);
                    workflowBillTraceMapper.insertWorkflowBillTrace(workflowBillTrace);
                }
            }
            return AjaxResult.success(billStatus);
        } catch (Exception e) {
            log.error("提交审核接口异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new CustomException("提交审核接口异常");
            }
        }
    }
}
