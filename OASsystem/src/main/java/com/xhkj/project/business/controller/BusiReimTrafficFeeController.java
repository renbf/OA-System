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
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.business.service.IBusiReimTrafficFeeService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 交通费用Controller
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@RestController
@RequestMapping("/business/trafficFee")
public class BusiReimTrafficFeeController extends BaseController
{
    @Autowired
    private IBusiReimTrafficFeeService busiReimTrafficFeeService;

    /**
     * 查询交通费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:fee:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiReimTrafficFee busiReimTrafficFee)
    {
        startPage();
        List<BusiReimTrafficFee> list = busiReimTrafficFeeService.selectBusiReimTrafficFeeList(busiReimTrafficFee);
        return getDataTable(list);
    }

    /**
     * 导出交通费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:fee:export')")
    @Log(title = "交通费用", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiReimTrafficFee busiReimTrafficFee)
    {
        List<BusiReimTrafficFee> list = busiReimTrafficFeeService.selectBusiReimTrafficFeeList(busiReimTrafficFee);
        ExcelUtil<BusiReimTrafficFee> util = new ExcelUtil<BusiReimTrafficFee>(BusiReimTrafficFee.class);
        return util.exportExcel(list, "fee");
    }

    /**
     * 获取交通费用详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:fee:query')")
    @GetMapping(value = "/{trafficId}")
    public AjaxResult getInfo(@PathVariable("trafficId") Long trafficId)
    {
        return AjaxResult.success(busiReimTrafficFeeService.selectBusiReimTrafficFeeById(trafficId));
    }

    /**
     * 新增交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:add')")
    @Log(title = "交通费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiReimTrafficFee busiReimTrafficFee)
    {
        return toAjax(busiReimTrafficFeeService.insertBusiReimTrafficFee(busiReimTrafficFee));
    }

    /**
     * 修改交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:edit')")
    @Log(title = "交通费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiReimTrafficFee busiReimTrafficFee)
    {
        return toAjax(busiReimTrafficFeeService.updateBusiReimTrafficFee(busiReimTrafficFee));
    }

    /**
     * 删除交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:remove')")
    @Log(title = "交通费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{trafficIds}")
    public AjaxResult remove(@PathVariable Long[] trafficIds)
    {
        return toAjax(busiReimTrafficFeeService.deleteBusiReimTrafficFeeByIds(trafficIds));
    }

    /**
     * 删除交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:remove')")
    @Log(title = "交通费用", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{trafficId}")
    public AjaxResult remove(@PathVariable Long trafficId)
    {
        return toAjax(busiReimTrafficFeeService.deleteBusiReimTrafficFeeById(trafficId));
    }
}
