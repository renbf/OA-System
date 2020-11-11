package com.xhkj.project.business.controller;

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
import com.xhkj.project.business.domain.BusiDailyTask;
import com.xhkj.project.business.service.IBusiDailyTaskService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 日常任务Controller
 * 
 * @author xhkj
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/business/dailyTask")
public class BusiDailyTaskController extends BaseController
{
    @Autowired
    private IBusiDailyTaskService busiDailyTaskService;

    /**
     * 查询日常任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiDailyTask busiDailyTask)
    {
        startPage();
        List<BusiDailyTask> list = busiDailyTaskService.selectBusiDailyTaskList(busiDailyTask);
        return getDataTable(list);
    }

    /**
     * 导出日常任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:export')")
    @Log(title = "日常任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiDailyTask busiDailyTask)
    {
        List<BusiDailyTask> list = busiDailyTaskService.selectBusiDailyTaskList(busiDailyTask);
        ExcelUtil<BusiDailyTask> util = new ExcelUtil<BusiDailyTask>(BusiDailyTask.class);
        return util.exportExcel(list, "dailyTask");
    }

    /**
     * 获取日常任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:query')")
    @GetMapping(value = "/{dailyTaskId}")
    public AjaxResult getInfo(@PathVariable("dailyTaskId") Long dailyTaskId)
    {
        return AjaxResult.success(busiDailyTaskService.selectBusiDailyTaskById(dailyTaskId));
    }

    /**
     * 新增日常任务
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:add')")
    @Log(title = "日常任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiDailyTask busiDailyTask)
    {
        return toAjax(busiDailyTaskService.insertBusiDailyTask(busiDailyTask));
    }

    /**
     * 修改日常任务
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:edit')")
    @Log(title = "日常任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiDailyTask busiDailyTask)
    {
        return toAjax(busiDailyTaskService.updateBusiDailyTask(busiDailyTask));
    }

    /**
     * 删除日常任务
     */
    @PreAuthorize("@ss.hasPermi('business:dailyTask:remove')")
    @Log(title = "日常任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dailyTaskIds}")
    public AjaxResult remove(@PathVariable Long[] dailyTaskIds)
    {
        return toAjax(busiDailyTaskService.deleteBusiDailyTaskByIds(dailyTaskIds));
    }
}
