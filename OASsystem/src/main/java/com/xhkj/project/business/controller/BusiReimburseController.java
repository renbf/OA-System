package com.xhkj.project.business.controller;

import java.util.List;

import com.xhkj.framework.aspectj.lang.annotation.DataScope;
import com.xhkj.project.business.domain.vo.BusiAskLeaveAprVo;
import com.xhkj.project.business.domain.vo.BusiReimburseAprVo;
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
import com.xhkj.project.business.domain.BusiReimburse;
import com.xhkj.project.business.service.IBusiReimburseService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 报销信息Controller
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@RestController
@RequestMapping("/business/reimburse")
public class BusiReimburseController extends BaseController
{
    @Autowired
    private IBusiReimburseService busiReimburseService;

    /**
     * 查询报销信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:list')")
    @GetMapping("/list")
    @DataScope(deptAlias = "sd", userAlias = "su")
    public TableDataInfo list(BusiReimburseAprVo busiReimburseAprVo)
    {
        startPage();
        List<BusiReimburseAprVo> list = busiReimburseService.selectBusiReimburseList(busiReimburseAprVo);
        return getDataTable(list);
    }

    /**
     * 导出报销信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:export')")
    @Log(title = "报销信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiReimburseAprVo busiReimburseAprVo)
    {
        List<BusiReimburseAprVo> list = busiReimburseService.selectBusiReimburseList(busiReimburseAprVo);
        ExcelUtil<BusiReimburseAprVo> util = new ExcelUtil<BusiReimburseAprVo>(BusiReimburseAprVo.class);
        return util.exportExcel(list, "reimburse");
    }

    /**
     * 获取报销信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:query')")
    @GetMapping(value = "/{reimburseId}")
    public AjaxResult getInfo(@PathVariable("reimburseId") Long reimburseId)
    {
        return AjaxResult.success(busiReimburseService.selectBusiReimburseById(reimburseId));
    }

    @PreAuthorize("@ss.hasPermi('business:reimburse:query')")
    @GetMapping(value = "/getRemburseDetail/{reimburseId}/{reimburseType}")
    public AjaxResult getRemburseDetail(@PathVariable("reimburseId") Long reimburseId , @PathVariable("reimburseType") String reimburseType)
    {
        return AjaxResult.success(busiReimburseService.getRemburseDetail(reimburseId,reimburseType));
    }

    /**
     * 新增报销信息
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:add')")
    @Log(title = "报销信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiReimburse busiReimburse)
    {
        Long reimburseId = busiReimburseService.insertBusiReimburse(busiReimburse);
        return AjaxResult.success(reimburseId);
    }

    /**
     * 修改报销信息
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:edit')")
    @Log(title = "报销信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiReimburse busiReimburse)
    {
        return toAjax(busiReimburseService.updateBusiReimburse(busiReimburse));
    }

    /**
     * 删除报销信息
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:remove')")
    @Log(title = "报销信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reimburseIds}")
    public AjaxResult remove(@PathVariable Long[] reimburseIds)
    {
        return toAjax(busiReimburseService.deleteBusiReimburseByIds(reimburseIds));
    }

    /**
     * 报送-发起流程
     */
    @PreAuthorize("@ss.hasPermi('business:reimburse:submit')")
    @Log(title = "请假上报", businessType = BusinessType.INSERT)
    @PostMapping("/billSumbit/{reimburseIds}")
    public AjaxResult billSumbit(@PathVariable("reimburseIds") Long[] reimburseIds)
    {
        return busiReimburseService.billSumbit(reimburseIds);
    }


}
