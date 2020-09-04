package com.xhkj.project.system.controller;

import java.util.List;

import com.xhkj.project.system.service.SysCompanyConfigService;
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
import com.xhkj.project.system.domain.SysCompanyConfig;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.common.utils.poi.ExcelUtil;
import com.xhkj.framework.web.page.TableDataInfo;

/**
 * 公司配置Controller
 * 
 * @author xhkj
 * @date 2020-05-28
 */
@RestController
@RequestMapping("/system/comConfig")
public class SysCompanyConfigController extends BaseController
{
    @Autowired
    private SysCompanyConfigService sysCompanyConfigService;

    /**
     * 查询公司配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:comconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCompanyConfig sysCompanyConfig)
    {
        //startPage();
        List<SysCompanyConfig> list = sysCompanyConfigService.selectSysCompanyConfigList(sysCompanyConfig);
        return getDataTable(list);
    }

    /**
     * 导出公司配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:comconfig:export')")
    @Log(title = "公司配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCompanyConfig sysCompanyConfig)
    {
        List<SysCompanyConfig> list = sysCompanyConfigService.selectSysCompanyConfigList(sysCompanyConfig);
        ExcelUtil<SysCompanyConfig> util = new ExcelUtil<SysCompanyConfig>(SysCompanyConfig.class);
        return util.exportExcel(list, "comconfig");
    }

    /**
     * 获取公司配置详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:comconfig:query')")
    @GetMapping(value = "/{companyConfigId}")
    public AjaxResult getInfo(@PathVariable("companyConfigId") Long companyConfigId)
    {
        return AjaxResult.success(sysCompanyConfigService.selectSysCompanyConfigById(companyConfigId));
    }

    /**
     * 新增公司配置
     */
    @PreAuthorize("@ss.hasPermi('system:comconfig:add')")
    @Log(title = "公司配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCompanyConfig sysCompanyConfig)
    {
        return toAjax(sysCompanyConfigService.insertSysCompanyConfig(sysCompanyConfig));
    }

    /**
     * 修改公司配置
     */
    @PreAuthorize("@ss.hasPermi('system:comconfig:edit')")
    @Log(title = "公司配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCompanyConfig sysCompanyConfig)
    {
        return toAjax(sysCompanyConfigService.updateSysCompanyConfig(sysCompanyConfig));
    }

    /**
     * 删除公司配置
     */
    @PreAuthorize("@ss.hasPermi('system:comconfig:remove')")
    @Log(title = "公司配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyConfigIds}")
    public AjaxResult remove(@PathVariable Long[] companyConfigIds)
    {
        return toAjax(sysCompanyConfigService.deleteSysCompanyConfigByIds(companyConfigIds));
    }


    /**
     * 新增公司配置
     */
    //@PreAuthorize("@ss.hasPermi('system:comconfig:add')")
    @Log(title = "公司配置", businessType = BusinessType.INSERT)
    @PostMapping("/addComConfigList")
    public AjaxResult addComConfigList(@RequestBody List<SysCompanyConfig> sysCompanyConfig)
    {
        return toAjax(sysCompanyConfigService.addComConfigList(sysCompanyConfig));
    }


    /**
     * 获取公司配置详细信息
     */
    @PostMapping(value = "getComConfigInfo")
    public AjaxResult getComConfigInfo(@RequestBody SysCompanyConfig sysCompanyConfig)
    {
        return AjaxResult.success(sysCompanyConfigService.selectSysCompanyConfig(sysCompanyConfig));
    }


}
