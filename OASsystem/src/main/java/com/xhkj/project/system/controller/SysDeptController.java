package com.xhkj.project.system.controller;

import java.util.List;

import com.xhkj.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xhkj.common.constant.UserConstants;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.framework.aspectj.lang.annotation.Log;
import com.xhkj.framework.aspectj.lang.enums.BusinessType;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.system.domain.SysDept;
import com.xhkj.project.system.service.ISysDeptService;

/**
 * 部门信息
 * 
 * @author xhkj
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
            {
                return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
            }
            dept.setCreateBy(SecurityUtils.getUsername());
            ajaxResult = deptService.insertDept(dept);
        } catch (Exception e) {
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("新增部门异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
            {
                return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
            }
            else if (dept.getParentId().equals(dept.getDeptId()))
            {
                return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
            }
            dept.setUpdateBy(SecurityUtils.getUsername());
            ajaxResult = deptService.updateDept(dept);
        } catch (Exception e) {
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("修改部门异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (deptService.hasChildByDeptId(deptId))
            {
                return AjaxResult.error("存在下级部门,不允许删除");
            }
            if (deptService.checkDeptExistUser(deptId))
            {
                return AjaxResult.error("部门存在用户,不允许删除");
            }
            ajaxResult = deptService.deleteDeptById(deptId);
        } catch (Exception e) {
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("删除部门异常");
            }
        }
        return ajaxResult;
    }

    /**
     * 获取当前用户管理的部门列表
     */
    @GetMapping("/userDeptList")
    public AjaxResult userDeptList()
    {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            ajaxResult = deptService.userDeptList();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                CustomException be = (CustomException)e;
                ajaxResult = AjaxResult.error(-1, be.getMessage());
            }else{
                ajaxResult = AjaxResult.error("获取当前用户管理的部门列表异常");
            }
        }
        return ajaxResult;
    }

}
