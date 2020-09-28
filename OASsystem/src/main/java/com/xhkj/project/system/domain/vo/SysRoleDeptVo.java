package com.xhkj.project.system.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author xhkj
 */
public class SysRoleDeptVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 角色ID */
    private Long roleId;
    /** 角色名称 */
    private String roleName;
    /** 部门ID */
    private Long deptId;
    /** 部门名称 */
    private String deptName;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
