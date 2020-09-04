package com.xhkj.project.system.service;

import java.util.List;
import com.xhkj.project.system.domain.SysWorkflowNode;

/**
 * 流程审核步骤Service接口
 * 
 * @author lvyan
 * @date 2020-04-01
 */
public interface ISysWorkflowNodeService 
{
    /**
     * 查询流程审核步骤列表
     * 
     * @param sysWorkflowNode 流程审核步骤
     * @return 流程审核步骤集合
     */
    public List<SysWorkflowNode> selectSysWorkflowNodeList(SysWorkflowNode sysWorkflowNode);

}
