package com.xhkj.project.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.ServletUtils;
import com.xhkj.framework.security.LoginUser;
import com.xhkj.framework.security.service.TokenService;
import com.xhkj.project.business.domain.BusiProjectMember;
import com.xhkj.project.business.domain.BusiTask;
import com.xhkj.project.business.domain.BusiTaskMember;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import com.xhkj.project.business.domain.vo.BusiTaskVo;
import com.xhkj.project.business.mapper.BusiProjectMemberMapper;
import com.xhkj.project.business.mapper.BusiTaskMapper;
import com.xhkj.project.business.mapper.BusiTaskMemberMapper;
import com.xhkj.project.system.domain.SysUser;
import com.xhkj.project.system.mapper.SysUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xhkj.project.business.mapper.BusiProjectMapper;
import com.xhkj.project.business.domain.BusiProject;
import com.xhkj.project.business.service.IBusiProjectService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 项目 服务层实现
 * 
 * @author rbf
 * @date 2020-09-24
 */
@Service
public class BusiProjectServiceImpl implements IBusiProjectService
{
	private static final Logger log = LoggerFactory.getLogger(BusiProjectServiceImpl.class);
	@Autowired
	private TokenService tokenService;
	@Autowired
	private BusiProjectMapper busiProjectMapper;
	@Autowired
	private BusiProjectMemberMapper busiProjectMemberMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private BusiTaskMapper busiTaskMapper;
	@Autowired
	private BusiTaskMemberMapper busiTaskMemberMapper;

	/**
     * 查询项目信息
     * 
     * @param projectId 项目ID
     * @return 项目信息
     */
    @Override
	public BusiProject selectBusiProjectById(Long projectId)
	{
	    return busiProjectMapper.selectBusiProjectById(projectId);
	}
	
	/**
     * 查询项目列表
     * 
     * @param busiProject 项目信息
     * @return 项目集合
     */
	@Override
	public Map<String,Object> selectBusiProjectList(BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<BusiProject> list = busiProjectMapper.selectBusiProjectList(busiProject);
			resultMap.put("code",200);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	
    /**
     * 新增项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	@Override
	@Transactional
	public Map<String,Object> insertBusiProject(BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Date> projectDate = busiProject.getProjectDate();
			busiProject.setProjectStartDate(projectDate.get(0));
			busiProject.setProjectEndDate(projectDate.get(1));
			busiProject.setProjectProgress("0");
			busiProject.setDeleteFlag("0");
			busiProject.setCreateBy(username);
			busiProject.setCreateTime(now);
			busiProject.setUpdateTime(now);
			busiProjectMapper.insertBusiProject(busiProject);
			Long projectId = busiProject.getProjectId();
			List<BusiProjectMember> list = new ArrayList<>();
			List<Long> userList = busiProject.getUserList();
			if (CollectionUtils.isNotEmpty(userList)) {
				List<SysUser> sysUsers = sysUserMapper.selectUsersByIds(userList);
				for (SysUser sysUser : sysUsers) {
					BusiProjectMember busiProjectMember = new BusiProjectMember();
					busiProjectMember.setProjectId(projectId);
					busiProjectMember.setDeptId(sysUser.getDeptId());
					busiProjectMember.setDeptName(sysUser.getDeptName());
					busiProjectMember.setMemberId(sysUser.getUserId());
					busiProjectMember.setMemberName(sysUser.getNickName());
					list.add(busiProjectMember);
				}
				busiProjectMemberMapper.insertBusiProjectMemberBatch(list);
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	
	/**
     * 修改项目
     * 
     * @param busiProject 项目信息
     * @return 结果
     */
	@Override
	public Map<String,Object> updateBusiProject(BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Date> projectDate = busiProject.getProjectDate();
			busiProject.setProjectStartDate(projectDate.get(0));
			busiProject.setProjectEndDate(projectDate.get(1));
			busiProject.setUpdateBy(username);
			busiProject.setUpdateTime(now);
			busiProjectMapper.updateBusiProject(busiProject);
			Long projectId = busiProject.getProjectId();
			busiProjectMemberMapper.deleteBusiProjectMemberByProjectId(projectId);
			List<BusiProjectMember> list = new ArrayList<>();
			List<Long> userList = busiProject.getUserList();
			if (CollectionUtils.isNotEmpty(userList)) {
				List<SysUser> sysUsers = sysUserMapper.selectUsersByIds(userList);
				for (SysUser sysUser : sysUsers) {
					BusiProjectMember busiProjectMember = new BusiProjectMember();
					busiProjectMember.setProjectId(projectId);
					busiProjectMember.setDeptId(sysUser.getDeptId());
					busiProjectMember.setDeptName(sysUser.getDeptName());
					busiProjectMember.setMemberId(sysUser.getUserId());
					busiProjectMember.setMemberName(sysUser.getNickName());
					list.add(busiProjectMember);
				}
				busiProjectMemberMapper.insertBusiProjectMemberBatch(list);
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	/**
     * 删除项目对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public Map<String,Object> deleteBusiProjectByIds(String ids)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
				busiProjectMapper.deleteBusiProjectByIds(ids.split(","));
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	//以上自动生成的尽量别动


	@Override
	public Map<String, Object> selectBusiProjects(BusiProjectVo busiProjectVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
            Long userId = Long.valueOf(SecurityUtils.getUserId());
            if (!SecurityUtils.isAdmin(userId)) {
                busiProjectVo.setMemberId(userId);
            }
			List<BusiProjectVo> list = busiProjectMapper.selectBusiProjects(busiProjectVo);
			resultMap.put("code",200);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

    @Override
    public Map<String, Object> getProjectInfo(Long projectId) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			BusiProjectVo busiProjectVo = busiProjectMapper.selectBusiProject(projectId);
			List<BusiProjectMember> busiProjectMembers = busiProjectMemberMapper.selectBusiProjectMembers(projectId);
			busiProjectVo.setBusiProjectMembers(busiProjectMembers);

			resultMap.put("code",200);
            resultMap.put("data",busiProjectVo);
        } catch (Exception e) {
            log.error("",e);
            throw new RuntimeException();
        }
        return resultMap;
    }

	@Override
	public Map<String, Object> changeStatus(BusiProject busiProject) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiProject.setUpdateBy(username);
			busiProject.setUpdateTime(now);
			busiProjectMapper.updateBusiProject(busiProject);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> insertBusiTask(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Date> taskDate = busiTask.getTaskDate();
			busiTask.setTaskStartDate(taskDate.get(0));
			busiTask.setTaskEndDate(taskDate.get(1));
			busiTask.setTaskProgress("0");
            busiTask.setTimeProgress("0");
            busiTask.setTaskStatus("0");
			busiTask.setCreateBy(username);
			busiTask.setCreateTime(now);
			busiTask.setUpdateTime(now);
			busiTaskMapper.insertBusiTask(busiTask);
			Long taskId = busiTask.getTaskId();
			List<BusiTaskMember> list = new ArrayList<>();
			List<Long> userList = busiTask.getUserList();
			if (CollectionUtils.isNotEmpty(userList)) {
				List<SysUser> sysUsers = sysUserMapper.selectUsersByIds(userList);
				for (SysUser sysUser : sysUsers) {
					BusiTaskMember busiTaskMember = new BusiTaskMember();
					busiTaskMember.setTaskId(taskId);
					busiTaskMember.setMemberId(sysUser.getUserId());
					busiTaskMember.setMemberName(sysUser.getNickName());
					list.add(busiTaskMember);
				}
				busiTaskMemberMapper.insertBusiTaskMemberBatch(list);
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> updateBusiTask(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Date> taskDate = busiTask.getTaskDate();
			busiTask.setTaskStartDate(taskDate.get(0));
			busiTask.setTaskEndDate(taskDate.get(1));
			busiTask.setUpdateBy(username);
			busiTask.setUpdateTime(now);
			busiTaskMapper.updateBusiTask(busiTask);
			Long taskId = busiTask.getTaskId();
			busiTaskMemberMapper.deleteBusiTaskMemberByTaskId(taskId);
			List<BusiTaskMember> list = new ArrayList<>();
			List<Long> userList = busiTask.getUserList();
			if (CollectionUtils.isNotEmpty(userList)) {
				List<SysUser> sysUsers = sysUserMapper.selectUsersByIds(userList);
				for (SysUser sysUser : sysUsers) {
					BusiTaskMember busiTaskMember = new BusiTaskMember();
					busiTaskMember.setTaskId(taskId);
					busiTaskMember.setMemberId(sysUser.getUserId());
					busiTaskMember.setMemberName(sysUser.getNickName());
					list.add(busiTaskMember);
				}
				busiTaskMemberMapper.insertBusiTaskMemberBatch(list);
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> selectListTask(BusiTaskVo busiTaskVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			if (Objects.nonNull(busiTaskVo.getPage()) && Objects.nonNull(busiTaskVo.getLimit())) {
				PageHelper.startPage(busiTaskVo.getPage(), busiTaskVo.getLimit());
			}
			List<BusiTaskVo> list = busiTaskMapper.selectBusiTasks(busiTaskVo);
			PageInfo<BusiTaskVo> pageInfo = new PageInfo<BusiTaskVo>(list);
			resultMap.put("code",200);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getTaskInfo(Long taskId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<BusiTaskMember> busiTaskMembers = busiTaskMemberMapper.selectBusiTaskMembers(taskId);

			resultMap.put("code",200);
			resultMap.put("data",busiTaskMembers);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
}
