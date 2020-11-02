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
import com.xhkj.project.business.domain.BusiReimExpense;
import com.xhkj.project.business.service.IBusiReimExpenseService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 费用Controller
 * 
 * @author xhkj
 * @date 2020-11-02
 */
@RestController
@RequestMapping("/business/expense")
public class BusiReimExpenseController extends BaseController
{
    @Autowired
    private IBusiReimExpenseService busiReimExpenseService;

    /**
     * 查询费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:expense:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiReimExpense busiReimExpense)
    {
        startPage();
        List<BusiReimExpense> list = busiReimExpenseService.selectBusiReimExpenseList(busiReimExpense);
        return getDataTable(list);
    }

    /**
     * 导出费用列表
     */
    @PreAuthorize("@ss.hasPermi('business:expense:export')")
    @Log(title = "费用", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiReimExpense busiReimExpense)
    {
        List<BusiReimExpense> list = busiReimExpenseService.selectBusiReimExpenseList(busiReimExpense);
        ExcelUtil<BusiReimExpense> util = new ExcelUtil<BusiReimExpense>(BusiReimExpense.class);
        return util.exportExcel(list, "expense");
    }

    /**
     * 获取费用详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:expense:query')")
    @GetMapping(value = "/{reimExpenseId}")
    public AjaxResult getInfo(@PathVariable("reimExpenseId") Long reimExpenseId)
    {
        return AjaxResult.success(busiReimExpenseService.selectBusiReimExpenseById(reimExpenseId));
    }

    /**
     * 新增费用
     */
    @PreAuthorize("@ss.hasPermi('business:expense:add')")
    @Log(title = "费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiReimExpense busiReimExpense)
    {
        return toAjax(busiReimExpenseService.insertBusiReimExpense(busiReimExpense));
    }

    /**
     * 修改费用
     */
    @PreAuthorize("@ss.hasPermi('business:expense:edit')")
    @Log(title = "费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiReimExpense busiReimExpense)
    {
        return toAjax(busiReimExpenseService.updateBusiReimExpense(busiReimExpense));
    }

    /**
     * 删除费用
     */
    @PreAuthorize("@ss.hasPermi('business:expense:remove')")
    @Log(title = "费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reimExpenseIds}")
    public AjaxResult remove(@PathVariable Long[] reimExpenseIds)
    {
        return toAjax(busiReimExpenseService.deleteBusiReimExpenseByIds(reimExpenseIds));
    }
}
