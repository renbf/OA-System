package com.xhkj.project.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.ServletUtils;
import com.xhkj.framework.security.LoginUser;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.project.system.domain.SysRole;
import com.xhkj.project.system.domain.SysRoleDept;
import com.xhkj.project.system.mapper.SysRoleDeptMapper;
import com.xhkj.project.system.service.ISysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.common.constant.UserConstants;
import com.xhkj.common.exception.CustomException;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.framework.aspectj.lang.annotation.DataScope;
import com.xhkj.framework.web.domain.TreeSelect;
import com.xhkj.project.system.domain.SysDept;
import com.xhkj.project.system.mapper.SysDeptMapper;
import com.xhkj.project.system.service.ISysDeptService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门管理 服务实现
 * 
 * @author xhkj
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    private static final Logger log = LoggerFactory.getLogger(SysDeptServiceImpl.class);
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private SysRoleDeptMapper roleDeptMapper;
    @Autowired
    private ISysRoleService roleService;
    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId)
    {
        return deptMapper.selectDeptListByRoleId(roleId);
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        SysDept sysDept = deptMapper.selectDeptById(deptId);
        SysRoleDept sysRoleDept = roleDeptMapper.selectRoleDeptByDeptId(deptId);
        if (Objects.nonNull(sysRoleDept)) {
            sysDept.setRoleId(sysRoleDept.getRoleId());
        }
        return sysDept;
    }

    /**
     * 是否存在子节点
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult insertDept(SysDept dept)
    {
        try {
            Date now = new Date();
            String username = SecurityUtils.getUsername();
            SysDept info = deptMapper.selectDeptById(dept.getParentId());
            // 如果父节点不为正常状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
            {
                throw new CustomException("部门停用，不允许新增");
            }
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
            deptMapper.insertDept(dept);
            //新增角色
            Long roleId = dept.getRoleId();
            if (roleId == null) {
                throw new CustomException("部门角色不能为空");
            }
            Long deptId = dept.getDeptId();
            SysRoleDept sysRoleDept = roleDeptMapper.selectRoleDeptByDeptId(deptId);
            if (Objects.nonNull(sysRoleDept)) {
                throw new CustomException("部门已经有角色");
            }
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(roleId);
            rd.setDeptId(deptId);
            roleDeptMapper.insertRoleDept(rd);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新增部门异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new RuntimeException();
            }
        }
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult updateDept(SysDept dept)
    {
        try {
            SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
            SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
            if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
            {
                String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
                String oldAncestors = oldDept.getAncestors();
                dept.setAncestors(newAncestors);
                updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
            }
            deptMapper.updateDept(dept);
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                // 如果该部门是启用状态，则启用该部门的所有上级部门
                updateParentDeptStatus(dept);
            }
            Long roleId = dept.getRoleId();
            Long deptId = dept.getDeptId();
            SysRoleDept sysRoleDept = roleDeptMapper.selectRoleDeptByDeptId(deptId);
            if (Objects.nonNull(sysRoleDept)) {
                roleDeptMapper.deleteRoleDeptByDeptId(deptId);
            }
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(roleId);
            rd.setDeptId(deptId);
            roleDeptMapper.insertRoleDept(rd);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("修改部门异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new RuntimeException();
            }
        }
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult deleteDeptById(Long deptId)
    {
        try {
            deptMapper.deleteDeptById(deptId);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("删除部门异常",e);
            if (e instanceof CustomException) {
                throw (CustomException)e;
            }else{
                throw new RuntimeException();
            }
        }
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext())
                {
                    SysDept n = (SysDept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
