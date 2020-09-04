package com.xhkj.project.system.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xhkj.framework.aspectj.lang.annotation.Log;
import com.xhkj.framework.aspectj.lang.enums.BusinessType;
import com.xhkj.project.system.domain.SysWorkflowNode;
import com.xhkj.project.system.service.ISysWorkflowNodeService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 流程审核步骤Controller
 * 
 * @author lvyan
 * @date 2020-04-01
 */
@RestController
@RequestMapping("/system/node")
public class SysWorkflowNodeController extends BaseController
{
    @Autowired
    private ISysWorkflowNodeService sysWorkflowNodeService;

    /**
     * 查询流程审核步骤列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWorkflowNode sysWorkflowNode)
    {
        startPage();
        List<SysWorkflowNode> list = sysWorkflowNodeService.selectSysWorkflowNodeList(sysWorkflowNode);
        return getDataTable(list);
    }

    /**
     * 导出流程审核步骤列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:export')")
    @Log(title = "流程审核步骤", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWorkflowNode sysWorkflowNode)
    {
        List<SysWorkflowNode> list = sysWorkflowNodeService.selectSysWorkflowNodeList(sysWorkflowNode);
        ExcelUtil<SysWorkflowNode> util = new ExcelUtil<SysWorkflowNode>(SysWorkflowNode.class);
        return util.exportExcel(list, "node");
    }

}
