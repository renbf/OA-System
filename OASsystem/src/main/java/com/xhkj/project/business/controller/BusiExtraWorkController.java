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
import com.xhkj.project.business.domain.BusiExtraWork;
import com.xhkj.project.business.service.BusiExtraWorkService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 加班Controller
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@RestController
@RequestMapping("/business/extraWork")
public class BusiExtraWorkController extends BaseController
{
    @Autowired
    private BusiExtraWorkService busiExtraWorkService;

    /**
     * 查询加班列表
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiExtraWork busiExtraWork)
    {
        startPage();
        List<BusiExtraWork> list = busiExtraWorkService.selectBusiExtraWorkList(busiExtraWork);
        return getDataTable(list);
    }

    /**
     * 导出加班列表
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:export')")
    @Log(title = "加班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiExtraWork busiExtraWork)
    {
        List<BusiExtraWork> list = busiExtraWorkService.selectBusiExtraWorkList(busiExtraWork);
        ExcelUtil<BusiExtraWork> util = new ExcelUtil<BusiExtraWork>(BusiExtraWork.class);
        return util.exportExcel(list, "work");
    }

    /**
     * 获取加班详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:query')")
    @GetMapping(value = "/{extraWorkId}")
    public AjaxResult getInfo(@PathVariable("extraWorkId") Long extraWorkId)
    {
        return AjaxResult.success(busiExtraWorkService.selectBusiExtraWorkById(extraWorkId));
    }

    /**
     * 新增加班
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:add')")
    @Log(title = "加班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addExtraWork(@RequestBody BusiExtraWork busiExtraWork)
    {
        return toAjax(busiExtraWorkService.insertBusiExtraWork(busiExtraWork));
    }

    /**
     * 修改加班
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:edit')")
    @Log(title = "加班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiExtraWork busiExtraWork)
    {
        return toAjax(busiExtraWorkService.updateBusiExtraWork(busiExtraWork));
    }

    /**
     * 删除加班
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:remove')")
    @Log(title = "加班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{extraWorkIds}")
    public AjaxResult remove(@PathVariable Long[] extraWorkIds)
    {
        return toAjax(busiExtraWorkService.deleteBusiExtraWorkByIds(extraWorkIds));
    }

    /**
     * 报送-发起流程
     */
    @PreAuthorize("@ss.hasPermi('business:extraWork:submit')")
    @Log(title = "加班上报", businessType = BusinessType.INSERT)
    @PostMapping("/extraWorkSumbit/{extraWorkIds}")
    public AjaxResult extraWorkSumbit(@PathVariable("extraWorkIds") Long[] extraWorkIds)
    {
        return toAjax(busiExtraWorkService.extraWorkSumbit(extraWorkIds));
    }
}
