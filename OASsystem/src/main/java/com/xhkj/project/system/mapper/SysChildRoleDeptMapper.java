package com.xhkj.project.system.mapper;

import java.util.List;
import com.xhkj.project.system.domain.SysChildRoleDept;

/**
 * 角色和部门关联Mapper接口
 * 
 * @author xhkj
 * @date 2020-07-30
 */
public interface SysChildRoleDeptMapper 
{
    /**
     * 查询角色和部门关联
     * 
     * @param roleId 角色和部门关联ID
     * @return 角色和部门关联
     */
    public SysChildRoleDept selectSysChildRoleDeptById(Long roleId);

    /**
     * 查询角色和部门关联列表
     * 
     * @param sysChildRoleDept 角色和部门关联
     * @return 角色和部门关联集合
     */
    public List<SysChildRoleDept> selectSysChildRoleDeptList(SysChildRoleDept sysChildRoleDept);

    /**
     * 新增角色和部门关联
     * 
     * @param sysChildRoleDept 角色和部门关联
     * @return 结果
     */
    public int insertSysChildRoleDept(SysChildRoleDept sysChildRoleDept);

    /**
     * 修改角色和部门关联
     * 
     * @param sysChildRoleDept 角色和部门关联
     * @return 结果
     */
    public int updateSysChildRoleDept(SysChildRoleDept sysChildRoleDept);

    /**
     * 删除角色和部门关联
     * 
     * @param roleId 角色和部门关联ID
     * @return 结果
     */
    public int deleteSysChildRoleDeptById(Long roleId);

    /**
     * 批量删除角色和部门关联
     * 
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysChildRoleDeptByIds(Long[] roleIds);

    /**
     * 批量新增角色和部门关联
     *
     * @param list 角色和部门关联信息
     * @return 结果
     */
    public int insertSysChildRoleDeptBatch(List<SysChildRoleDept> list);
    //以上自动生成的尽量别动
}
