package com.xhkj.project.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.system.mapper.SysWorkflowNodeMapper;
import com.xhkj.project.system.domain.SysWorkflowNode;
import com.xhkj.project.system.service.ISysWorkflowNodeService;

/**
 * 流程审核步骤Service业务层处理
 * 
 * @author lvyan
 * @date 2020-04-01
 */
@Service
public class SysWorkflowNodeServiceImpl implements ISysWorkflowNodeService 
{
    @Autowired
    private SysWorkflowNodeMapper sysWorkflowNodeMapper;

    /**
     * 查询流程审核步骤列表
     * 
     * @param sysWorkflowNode 流程审核步骤
     * @return 流程审核步骤
     */
    @Override
    public List<SysWorkflowNode> selectSysWorkflowNodeList(SysWorkflowNode sysWorkflowNode)
    {
        return sysWorkflowNodeMapper.selectSysWorkflowNodeList(sysWorkflowNode);
    }

}
