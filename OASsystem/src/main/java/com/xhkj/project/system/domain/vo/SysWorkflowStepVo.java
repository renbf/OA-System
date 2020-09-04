package com.xhkj.project.system.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.system.domain.SysWorkflowNode;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 流程步骤定义对象 sys_workflow_step
 *
 * @author xhkj
 * @date 2020-04-17
 */
public class SysWorkflowStepVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流程步骤ID
     */
    private Long workflowStepId;
    /**
     * 流程步骤名称
     */
    private String workflowStepName;
    /**
     * 流程ID
     */
    private Long workflowId;
    /**
     * 流程步骤类型
     */
    private String stepType;
    /**
     * 排序字段
     */
    private Integer sortOrder;
    /**
     * 备注
     */
    private String remarks;

    private Long deptId;
    /**
     * 流程步骤节点ID
     */
    private Long workflowStepNodeId;

    private List<SysWorkflowNode> sysWorkflowNodes;

    public List<SysWorkflowNode> getSysWorkflowNodes() {
        return sysWorkflowNodes;
    }

    public void setSysWorkflowNodes(List<SysWorkflowNode> sysWorkflowNodes) {
        this.sysWorkflowNodes = sysWorkflowNodes;
    }

    public Long getWorkflowStepNodeId() {
        return workflowStepNodeId;
    }

    public void setWorkflowStepNodeId(Long workflowStepNodeId) {
        this.workflowStepNodeId = workflowStepNodeId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getWorkflowStepId() {
        return workflowStepId;
    }

    public void setWorkflowStepId(Long workflowStepId) {
        this.workflowStepId = workflowStepId;
    }

    public String getWorkflowStepName() {
        return workflowStepName;
    }

    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
