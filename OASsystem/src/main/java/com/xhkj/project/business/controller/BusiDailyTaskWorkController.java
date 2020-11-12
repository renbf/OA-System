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
import com.xhkj.project.business.domain.BusiDailyTaskWork;
import com.xhkj.project.business.service.IBusiDailyTaskWorkService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 日常任务工作Controller
 * 
 * @author xhkj
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/business/dailyTaskWork")
public class BusiDailyTaskWorkController extends BaseController
{
    @Autowired
    private IBusiDailyTaskWorkService busiDailyTaskWorkService;

    /**
     * 查询日常任务工作列表
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiDailyTaskWork busiDailyTaskWork)
    {
        startPage();
        List<BusiDailyTaskWork> list = busiDailyTaskWorkService.selectBusiDailyTaskWorkList(busiDailyTaskWork);
        return getDataTable(list);
    }

    /**
     * 导出日常任务工作列表
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:export')")
    @Log(title = "日常任务工作", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiDailyTaskWork busiDailyTaskWork)
    {
        List<BusiDailyTaskWork> list = busiDailyTaskWorkService.selectBusiDailyTaskWorkList(busiDailyTaskWork);
        ExcelUtil<BusiDailyTaskWork> util = new ExcelUtil<BusiDailyTaskWork>(BusiDailyTaskWork.class);
        return util.exportExcel(list, "dailyTaskWork");
    }

    /**
     * 获取日常任务工作详细信息
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:query')")
    @GetMapping(value = "/{dailyTaskWorksId}")
    public AjaxResult getInfo(@PathVariable("dailyTaskWorksId") Long dailyTaskWorksId)
    {
        return AjaxResult.success(busiDailyTaskWorkService.selectBusiDailyTaskWorkById(dailyTaskWorksId));
    }

    /**
     * 新增日常任务工作
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:add')")
    @Log(title = "日常任务工作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiDailyTaskWork busiDailyTaskWork)
    {
        return toAjax(busiDailyTaskWorkService.insertBusiDailyTaskWork(busiDailyTaskWork));
    }

    /**
     * 修改日常任务工作
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:edit')")
    @Log(title = "日常任务工作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiDailyTaskWork busiDailyTaskWork)
    {
        return toAjax(busiDailyTaskWorkService.updateBusiDailyTaskWork(busiDailyTaskWork));
    }

    /**
     * 删除日常任务工作
     */
//    @PreAuthorize("@ss.hasPermi('business:dailyTaskWork:remove')")
    @Log(title = "日常任务工作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dailyTaskWorksIds}")
    public AjaxResult remove(@PathVariable Long[] dailyTaskWorksIds)
    {
        return toAjax(busiDailyTaskWorkService.deleteBusiDailyTaskWorkByIds(dailyTaskWorksIds));
    }
}
