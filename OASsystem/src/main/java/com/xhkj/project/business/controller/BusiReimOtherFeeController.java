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
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.service.IBusiReimOtherFeeService;
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
@RequestMapping("/business/otherFee")
public class BusiReimOtherFeeController extends BaseController
{
    @Autowired
    private IBusiReimOtherFeeService busiReimOtherFeeService;

    /**
     * 查询交通费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:fee:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiReimOtherFee busiReimOtherFee)
    {
        startPage();
        List<BusiReimOtherFee> list = busiReimOtherFeeService.selectBusiReimOtherFeeList(busiReimOtherFee);
        return getDataTable(list);
    }

    /**
     * 导出交通费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:fee:export')")
    @Log(title = "交通费用", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiReimOtherFee busiReimOtherFee)
    {
        List<BusiReimOtherFee> list = busiReimOtherFeeService.selectBusiReimOtherFeeList(busiReimOtherFee);
        ExcelUtil<BusiReimOtherFee> util = new ExcelUtil<BusiReimOtherFee>(BusiReimOtherFee.class);
        return util.exportExcel(list, "fee");
    }

    /**
     * 获取交通费用详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:fee:query')")
    @GetMapping(value = "/{otherId}")
    public AjaxResult getInfo(@PathVariable("otherId") Long otherId)
    {
        return AjaxResult.success(busiReimOtherFeeService.selectBusiReimOtherFeeById(otherId));
    }

    /**
     * 新增交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:add')")
    @Log(title = "交通费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiReimOtherFee busiReimOtherFee)
    {
        return toAjax(busiReimOtherFeeService.insertBusiReimOtherFee(busiReimOtherFee));
    }

    /**
     * 修改交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:edit')")
    @Log(title = "交通费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiReimOtherFee busiReimOtherFee)
    {
        return toAjax(busiReimOtherFeeService.updateBusiReimOtherFee(busiReimOtherFee));
    }

    /**
     * 删除交通费用
     */
    @PreAuthorize("@ss.hasPermi('business:fee:remove')")
    @Log(title = "交通费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{otherIds}")
    public AjaxResult remove(@PathVariable Long[] otherIds)
    {
        return toAjax(busiReimOtherFeeService.deleteBusiReimOtherFeeByIds(otherIds));
    }
}
