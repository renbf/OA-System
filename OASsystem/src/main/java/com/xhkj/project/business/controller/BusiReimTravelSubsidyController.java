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
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import com.xhkj.project.business.service.IBusiReimTravelSubsidyService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 出差补贴费用Controller
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@RestController
@RequestMapping("/business/subsidy")
public class BusiReimTravelSubsidyController extends BaseController
{
    @Autowired
    private IBusiReimTravelSubsidyService busiReimTravelSubsidyService;

    /**
     * 查询出差补贴列表
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        startPage();
        List<BusiReimTravelSubsidy> list = busiReimTravelSubsidyService.selectBusiReimTravelSubsidyList(busiReimTravelSubsidy);
        return getDataTable(list);
    }

    /**
     * 导出出差补贴列表
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:export')")
    @Log(title = "出差补贴", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        List<BusiReimTravelSubsidy> list = busiReimTravelSubsidyService.selectBusiReimTravelSubsidyList(busiReimTravelSubsidy);
        ExcelUtil<BusiReimTravelSubsidy> util = new ExcelUtil<BusiReimTravelSubsidy>(BusiReimTravelSubsidy.class);
        return util.exportExcel(list, "subsidy");
    }

    /**
     * 获取出差补贴详细信息
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:query')")
    @GetMapping(value = "/{travelId}")
    public AjaxResult getInfo(@PathVariable("travelId") Long travelId)
    {
        return AjaxResult.success(busiReimTravelSubsidyService.selectBusiReimTravelSubsidyById(travelId));
    }

    /**
     * 新增出差补贴
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:add')")
    @Log(title = "出差补贴", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        return toAjax(busiReimTravelSubsidyService.insertBusiReimTravelSubsidy(busiReimTravelSubsidy));
    }

    /**
     * 修改出差补贴
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:edit')")
    @Log(title = "出差补贴", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        return toAjax(busiReimTravelSubsidyService.updateBusiReimTravelSubsidy(busiReimTravelSubsidy));
    }

    /**
     * 删除出差补贴
     */
//    @PreAuthorize("@ss.hasPermi('business:subsidy:remove')")
    @Log(title = "出差补贴", businessType = BusinessType.DELETE)
	@DeleteMapping("/{travelIds}")
    public AjaxResult remove(@PathVariable Long[] travelIds)
    {
        return toAjax(busiReimTravelSubsidyService.deleteBusiReimTravelSubsidyByIds(travelIds));
    }

    
    @Log(title = "出差补贴", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{travelId}")
    public AjaxResult remove(@PathVariable Long travelId)
    {
        return toAjax(busiReimTravelSubsidyService.deleteBusiReimTravelSubsidyById(travelId));
    }
    
    
}
