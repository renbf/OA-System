package com.xhkj.project.business.controller;

import java.util.List;

import com.xhkj.project.business.domain.vo.BusiAskLeaveVo;
import com.xhkj.project.business.service.BusiHolsCheckService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.xhkj.project.business.domain.BusiAskLeave;
import com.xhkj.project.business.service.BusiAskLeaveService;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 请假倒休Controller
 * 
 * @author xhkj
 * @date 2020-04-15
 */
@RestController
@RequestMapping("/business/leave")
public class BusiAskLeaveController extends BaseController
{
    @Autowired
    private BusiAskLeaveService busiAskLeaveService;
    @Autowired
    private BusiHolsCheckService iBusiHolsCheckService;
    /**
     * 查询请假倒休列表
     */
    @PreAuthorize("@ss.hasPermi('business:leave:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiAskLeave busiAskLeave)
    {
        startPage();
        List<BusiAskLeave> list = busiAskLeaveService.selectBusiAskLeaveList(busiAskLeave);
        return getDataTable(list);
    }

    /**
     * 导出请假倒休列表
     */
    @PreAuthorize("@ss.hasPermi('business:leave:export')")
    @Log(title = "请假倒休", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiAskLeave busiAskLeave)
    {
        List<BusiAskLeave> list = busiAskLeaveService.selectBusiAskLeaveList(busiAskLeave);
        ExcelUtil<BusiAskLeave> util = new ExcelUtil<BusiAskLeave>(BusiAskLeave.class);
        return util.exportExcel(list, "leave");
    }

    /**
     * 获取请假倒休详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:leave:query')")
    @GetMapping(value = "/{leaveId}")
    public AjaxResult getInfo(@PathVariable("leaveId") Long leaveId)
    {
        return AjaxResult.success(busiAskLeaveService.selectBusiAskLeaveById(leaveId));
    }

    /**
     * 新增请假倒休
     */
    @PreAuthorize("@ss.hasPermi('business:leave:add')")
    @Log(title = "请假倒休", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BusiAskLeaveVo busiAskLeaveVo)
    {
        return toAjax(busiAskLeaveService.insertBusiAskLeave(busiAskLeaveVo));
    }

    /**
     * 修改请假倒休
     */
    @PreAuthorize("@ss.hasPermi('business:leave:edit')")
    @Log(title = "请假倒休", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BusiAskLeaveVo busiAskLeaveVo)
    {
        return toAjax(busiAskLeaveService.updateBusiAskLeave(busiAskLeaveVo));
    }

    /**
     * 删除请假倒休
     */
    @PreAuthorize("@ss.hasPermi('business:leave:remove')")
    @Log(title = "请假倒休", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leaveIds}")
    public AjaxResult remove(@PathVariable("leaveIds") Long[] leaveIds)
    {
        return toAjax(busiAskLeaveService.deleteBusiAskLeaveByIds(leaveIds));
    }


    /**
     * 报送-发起流程
     */
    @PreAuthorize("@ss.hasPermi('business:leave:submit')")
    @Log(title = "请假上报", businessType = BusinessType.INSERT)
    @PostMapping("/leaveSumbit/{leaveIds}")
    public AjaxResult leaveSumbit(@PathVariable("leaveIds") Long[] leaveIds)
    {
        return toAjax(busiAskLeaveService.leaveSumbit(leaveIds));
    }

}
