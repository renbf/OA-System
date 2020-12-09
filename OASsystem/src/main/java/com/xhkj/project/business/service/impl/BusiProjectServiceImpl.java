package com.xhkj.project.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhkj.common.exception.CustomException;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.ServletUtils;
import com.xhkj.framework.security.LoginUser;
import com.xhkj.framework.security.service.TokenService;
import com.xhkj.project.business.domain.*;
import com.xhkj.project.business.domain.vo.BusiProjectApplyVo;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import com.xhkj.project.business.domain.vo.BusiTaskLogVo;
import com.xhkj.project.business.domain.vo.BusiTaskVo;
import com.xhkj.project.business.mapper.*;
import com.xhkj.project.common.UploadFile;
import com.xhkj.project.system.domain.SysUser;
import com.xhkj.project.system.domain.vo.SysRoleDeptVo;
import com.xhkj.project.system.mapper.SysUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	@Autowired
	private BusiTaskLogMapper busiTaskLogMapper;
	@Autowired
	private BusiTaskLogFileMapper busiTaskLogFileMapper;
	@Autowired
	private BusiProjectApplyMapper busiProjectApplyMapper;
	@Autowired
	private BusiProjectApplyShenpiMapper busiProjectApplyShenpiMapper;
	@Autowired
	private BusiProjectLeaderWorkflowMapper busiProjectLeaderWorkflowMapper;

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
			busiProject.setProjectProgress(0);
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
     * @param projectId 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public Map<String,Object> deleteBusiProjectByIds(Long projectId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			BusiProject busiProject = busiProjectMapper.selectBusiProjectById(projectId);
			String status = busiProject.getStatus();
			if ("1".equals(status)) {
				resultMap.put("code",500);
				resultMap.put("msg", "项目在启用中不能删除");
				return resultMap;
			}
			int i = busiTaskMapper.taskLogCount(projectId);
			if (i > 0) {
				resultMap.put("code",500);
				resultMap.put("msg", "项目已有任务日志回复不能删除");
				return resultMap;
			}
			busiProjectMapper.deleteBusiProjectById(projectId);
			busiProjectMemberMapper.deleteBusiProjectMemberByProjectId(projectId);
			BusiTask busiTask = new BusiTask();
			busiTask.setProjectId(projectId);
			List<BusiTask> busiTasks = busiTaskMapper.selectBusiTaskList(busiTask);
			if (CollectionUtils.isNotEmpty(busiTasks)) {
				for (BusiTask busiTask1 : busiTasks) {
					Long taskId = busiTask1.getTaskId();
					busiTaskMapper.deleteBusiTaskById(taskId);
					busiTaskMemberMapper.deleteBusiTaskMemberByTaskId(taskId);
				}
			}
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
			if (!SecurityUtils.isAdmin(userId)) {
				busiProjectVo.setLeaderId(userId);
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
			busiTask.setTaskProgress(0);
            busiTask.setTimeProgress(0);
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
	public Map<String, Object> updateTaskProgress(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiTask.setUpdateBy(username);
			busiTask.setUpdateTime(now);
			busiTaskMapper.updateBusiTask(busiTask);
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
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			if (Objects.nonNull(busiTaskVo.getPage()) && Objects.nonNull(busiTaskVo.getLimit())) {
				PageHelper.startPage(busiTaskVo.getPage(), busiTaskVo.getLimit());
			}
			busiTaskVo.setCreateBy(username);
			busiTaskVo.setMemberId(userId);
			List<BusiTaskVo> list = busiTaskMapper.selectBusiTasks(busiTaskVo);
			PageInfo<BusiTaskVo> pageInfo = new PageInfo<BusiTaskVo>(list);
			resultMap.put("code",200);
			resultMap.put("data",list);
			resultMap.put("pageInfo",pageInfo);
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
			BusiTaskVo busiTaskVo = busiTaskMapper.selectBusiTaskByTaskId(taskId);
			List<BusiTaskMember> busiTaskMembers = busiTaskMemberMapper.selectBusiTaskMembers(taskId);
			BusiTaskLog busiTaskLog = new BusiTaskLog();
			busiTaskLog.setTaskId(taskId);
			List<BusiTaskLogVo> busiTaskLogVos = busiTaskLogMapper.selectBusiTaskLogVos(busiTaskLog);
			resultMap.put("code",200);
			resultMap.put("busiTaskVo",busiTaskVo);
			resultMap.put("busiTaskMembers",busiTaskMembers);
			resultMap.put("busiTaskLogVos",busiTaskLogVos);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> changeTaskStatus(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiTask.setUpdateBy(username);
			busiTask.setUpdateTime(now);
			busiTaskMapper.updateBusiTask(busiTask);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> removeTask(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<Long> taskIds = busiTask.getTaskIds();
			if (CollectionUtils.isNotEmpty(taskIds)) {
				for (Long taskId : taskIds) {
					BusiTask busiTask1 = busiTaskMapper.selectBusiTaskById(taskId);
					String status = busiTask1.getStatus();
					if ("0".equals(status)) {
						int i = busiTaskMapper.taskLogCountByTask(taskId);
						if (i > 0) {
							throw new CustomException("已有任务日志，不能删除");
						}
						busiTaskMapper.deleteBusiTaskById(taskId);
						busiTaskMemberMapper.deleteBusiTaskMemberByTaskId(taskId);
					}else{
						throw new CustomException("在启用中，不能删除");
					}
				}
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			if (e instanceof CustomException) {
				throw (CustomException)e;
			}else{
				throw new RuntimeException();
			}
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> closeProject(BusiProject busiProject) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiProject.setUpdateBy(username);
			busiProject.setUpdateTime(now);
			busiProject.setProjectProgress(100);
			busiProjectMapper.updateBusiProject(busiProject);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> closeTask(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiTask.setUpdateBy(username);
			busiTask.setUpdateTime(now);
			busiTask.setTaskProgress(100);
			busiTask.setTaskStatus("1");
			busiTaskMapper.updateBusiTask(busiTask);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> insertBusiTaskLog(BusiTaskLog busiTaskLog) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiTaskLog.setCreateDate(now);
			busiTaskLog.setCreateTime(now);
			busiTaskLog.setCreateBy(username);
			busiTaskLog.setUpdateTime(now);
			busiTaskLogMapper.insertBusiTaskLog(busiTaskLog);
			Long taskLogId = busiTaskLog.getTaskLogId();
			List<UploadFile> fileList = busiTaskLog.getFileList();
			if (CollectionUtils.isNotEmpty(fileList)) {
				List<BusiTaskLogFile> list = new ArrayList<>();
				BusiTaskLogFile busiTaskLogFile = null;
				for (UploadFile uploadFile : fileList) {
					busiTaskLogFile = new BusiTaskLogFile();
					busiTaskLogFile.setFileId(uploadFile.getFileId());
					busiTaskLogFile.setFileName(uploadFile.getName());
					busiTaskLogFile.setFileUrl(uploadFile.getUrl());
					busiTaskLogFile.setTaskLogId(taskLogId);
					list.add(busiTaskLogFile);
				}
				busiTaskLogFileMapper.insertBusiTaskLogFileBatch(list);
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
	public Map<String, Object> updateTaskLog(BusiTaskLog busiTaskLog) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiTaskLog.setUpdateTime(now);
			busiTaskLog.setUpdateBy(username);
			busiTaskLogMapper.updateBusiTaskLog(busiTaskLog);
			Long taskLogId = busiTaskLog.getTaskLogId();
			List<UploadFile> fileList = busiTaskLog.getFileList();
			if (CollectionUtils.isNotEmpty(fileList)) {
				busiTaskLogFileMapper.deleteBusiTaskLogFileByTaskLogId(taskLogId);
				List<BusiTaskLogFile> list = new ArrayList<>();
				BusiTaskLogFile busiTaskLogFile = null;
				for (UploadFile uploadFile : fileList) {
					busiTaskLogFile = new BusiTaskLogFile();
					busiTaskLogFile.setFileId(uploadFile.getFileId());
					busiTaskLogFile.setFileName(uploadFile.getName());
					busiTaskLogFile.setFileUrl(uploadFile.getUrl());
					busiTaskLogFile.setTaskLogId(taskLogId);
					list.add(busiTaskLogFile);
				}
				busiTaskLogFileMapper.insertBusiTaskLogFileBatch(list);
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getDayTaskLog(Long taskId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			String now = DateUtils.getDate();
			String username = SecurityUtils.getUsername();
			BusiTaskLogVo busiTaskLogVo = busiTaskLogMapper.selectBusiTaskLogByUniqueKey(taskId, now, username);
			if (Objects.nonNull(busiTaskLogVo)) {
				Long taskLogId = busiTaskLogVo.getTaskLogId();
				List<BusiTaskLogFile> busiTaskLogFiles = busiTaskLogFileMapper.selectBusiTaskLogFiles(taskLogId);
				busiTaskLogVo.setBusiTaskLogFiles(busiTaskLogFiles);
			}
			resultMap.put("code",200);
			resultMap.put("data",busiTaskLogVo);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> taskLogBaosong(BusiTask busiTask) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Long> taskIds = busiTask.getTaskIds();
			if (CollectionUtils.isNotEmpty(taskIds)) {
				BusiTaskLog busiTaskLogUp = null;
				for (Long taskId : taskIds) {
					BusiTaskLog busiTaskLog = new BusiTaskLog();
					busiTaskLog.setTaskId(taskId);
					busiTaskLog.setLogStatus("0");
					List<BusiTaskLogVo> busiTaskLogVos = busiTaskLogMapper.selectBusiTaskLogVos(busiTaskLog);
					if (CollectionUtils.isNotEmpty(busiTaskLogVos)) {
						for (BusiTaskLogVo busiTaskLogVo : busiTaskLogVos) {
							Long taskLogId = busiTaskLogVo.getTaskLogId();
							busiTaskLogUp = new BusiTaskLog();
							busiTaskLogUp.setTaskLogId(taskLogId);
							busiTaskLogUp.setLogStatus("1");
							busiTaskLogUp.setUpdateTime(now);
							busiTaskLogUp.setUpdateBy(username);
							busiTaskLogMapper.updateBusiTaskLog(busiTaskLogUp);
						}
					}
				}
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
	public Map<String, Object> insertProjectApply(BusiProjectApply busiProjectApply) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiProjectApply.setCreateBy(username);
			busiProjectApply.setCreateTime(now);
			busiProjectApply.setUpdateTime(now);
			busiProjectApply.setDeleteFlag("0");
			busiProjectApplyMapper.insertBusiProjectApply(busiProjectApply);
			Long projectApplyId = busiProjectApply.getProjectApplyId();
			List<BusiProjectApplyShenpi> shenpiUserList = busiProjectApply.getShenpiUserList();
			if (CollectionUtils.isNotEmpty(shenpiUserList)) {
				for (BusiProjectApplyShenpi busiProjectApplyShenpi : shenpiUserList) {
					Integer sortOrder = busiProjectApplyShenpi.getSortOrder();
					if (sortOrder == 0) {
						busiProjectApplyShenpi.setIsCurrent("1");
					}else{
						busiProjectApplyShenpi.setIsCurrent("0");
					}
					busiProjectApplyShenpi.setProjectApplyId(projectApplyId);
					busiProjectApplyShenpi.setCheckStatus("-1");
					busiProjectApplyShenpi.setCreateBy(username);
					busiProjectApplyShenpi.setCreateTime(now);
					busiProjectApplyShenpi.setUpdateTime(now);
				}
				busiProjectApplyShenpiMapper.insertBusiProjectApplyShenpiBatch(shenpiUserList);
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
	public Map<String, Object> updateProjectApply(BusiProjectApply busiProjectApply) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiProjectApply.setUpdateBy(username);
			busiProjectApply.setUpdateTime(now);
			busiProjectApply.setDeleteFlag("0");
			busiProjectApplyMapper.updateBusiProjectApply(busiProjectApply);
			Long projectApplyId = busiProjectApply.getProjectApplyId();
			List<BusiProjectApplyShenpi> shenpiUserList = busiProjectApply.getShenpiUserList();
			if (CollectionUtils.isNotEmpty(shenpiUserList)) {
				busiProjectApplyShenpiMapper.deleteBusiProjectApplyShenpiByProjectApplyId(projectApplyId);
				for (BusiProjectApplyShenpi busiProjectApplyShenpi : shenpiUserList) {
					Integer sortOrder = busiProjectApplyShenpi.getSortOrder();
					if (sortOrder == 0) {
						busiProjectApplyShenpi.setIsCurrent("1");
					}else{
						busiProjectApplyShenpi.setIsCurrent("0");
					}
					busiProjectApplyShenpi.setProjectApplyId(projectApplyId);
					busiProjectApplyShenpi.setCheckStatus("-1");
					busiProjectApplyShenpi.setCreateBy(username);
					busiProjectApplyShenpi.setCreateTime(now);
					busiProjectApplyShenpi.setUpdateTime(now);
				}
				busiProjectApplyShenpiMapper.insertBusiProjectApplyShenpiBatch(shenpiUserList);
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
	public Map<String, Object> removeProjectApply(BusiProjectApplyVo busiProjectApplyVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<Long> projectApplyIds = busiProjectApplyVo.getProjectApplyIds();
			if (CollectionUtils.isNotEmpty(projectApplyIds)) {
				for (Long projectApplyId : projectApplyIds) {
					BusiProjectApply busiProjectApply = busiProjectApplyMapper.selectBusiProjectApplyById(projectApplyId);
					String status = busiProjectApply.getStatus();
					if ("0".equals(status)) {
						busiProjectApplyMapper.deleteBusiProjectApplyById(projectApplyId);
						busiProjectApplyShenpiMapper.deleteBusiProjectApplyShenpiByProjectApplyId(projectApplyId);
					}else{
						resultMap.put("code",-1);
						resultMap.put("msg","在启用中，不能删除");
						return resultMap;
					}
				}
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
    public Map<String, Object> listProjectApply(BusiProjectApplyVo busiProjectApplyVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			if (Objects.nonNull(busiProjectApplyVo.getPage()) && Objects.nonNull(busiProjectApplyVo.getLimit())) {
				PageHelper.startPage(busiProjectApplyVo.getPage(), busiProjectApplyVo.getLimit());
			}
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			if (!SecurityUtils.isAdmin(userId)) {
				busiProjectApplyVo.setCreateBy(username);
			}
			List<BusiProjectApplyVo> busiProjectApplyVos = busiProjectApplyMapper.selectBusiProjectApplyVos(busiProjectApplyVo);
			PageInfo<BusiProjectApplyVo> pageInfo = new PageInfo<BusiProjectApplyVo>(busiProjectApplyVos);
			resultMap.put("code",200);
			resultMap.put("data",busiProjectApplyVos);
			resultMap.put("pageInfo",pageInfo);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
    }

	@Override
	public Map<String, Object> listProjectApplyShenpi(Long projectApplyId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<BusiProjectApplyShenpi> busiProjectApplyShenpis = busiProjectApplyShenpiMapper.selectBusiProjectApplyShenpis(projectApplyId);
			resultMap.put("code",200);
			resultMap.put("data",busiProjectApplyShenpis);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> baosongProjectApply(BusiProjectApplyVo busiProjectApplyVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			List<Long> projectApplyIds = busiProjectApplyVo.getProjectApplyIds();
			if (CollectionUtils.isNotEmpty(projectApplyIds)) {
				for (Long projectApplyId : projectApplyIds) {
					BusiProjectApply busiProjectApply = busiProjectApplyMapper.selectBusiProjectApplyById(projectApplyId);
					String status = busiProjectApply.getStatus();
					if ("0".equals(status)) {
						BusiProjectApply busiProjectApplyUp = new BusiProjectApply();
						busiProjectApplyUp.setProjectApplyId(projectApplyId);
						busiProjectApplyUp.setStatus("1");
						busiProjectApplyUp.setUpdateBy(username);
						busiProjectApplyUp.setUpdateTime(now);
						busiProjectApplyMapper.updateBusiProjectApply(busiProjectApplyUp);
					}else{
						resultMap.put("code",-1);
						resultMap.put("msg","在启用中，不能报送");
						return resultMap;
					}
				}
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> todolist(BusiProjectVo busiProjectVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			busiProjectVo.setMemberId(userId);
			List<BusiProjectVo> list = busiProjectMapper.todolist(busiProjectVo);
			resultMap.put("code",200);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> todolistProjectApply(BusiProjectApplyVo busiProjectApplyVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			if (Objects.nonNull(busiProjectApplyVo.getPage()) && Objects.nonNull(busiProjectApplyVo.getLimit())) {
				PageHelper.startPage(busiProjectApplyVo.getPage(), busiProjectApplyVo.getLimit());
			}
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			if (!SecurityUtils.isAdmin(userId)) {
				busiProjectApplyVo.setCurrentShenpiUserId(userId);
			}
			List<BusiProjectApplyVo> busiProjectApplyVos = busiProjectApplyMapper.selectBusiProjectApplyVosTodo(busiProjectApplyVo);
			PageInfo<BusiProjectApplyVo> pageInfo = new PageInfo<BusiProjectApplyVo>(busiProjectApplyVos);
			resultMap.put("code",200);
			resultMap.put("data",busiProjectApplyVos);
			resultMap.put("pageInfo",pageInfo);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> batchProjectApplyShenpi(BusiProjectApplyVo busiProjectApplyVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			List<Long> projectApplyIds = busiProjectApplyVo.getProjectApplyIds();
			String status = busiProjectApplyVo.getStatus();
			String remark = busiProjectApplyVo.getRemark();
			if (CollectionUtils.isNotEmpty(projectApplyIds)) {
				for (Long projectApplyId : projectApplyIds) {
					BusiProjectApplyShenpi busiProjectApplyShenpi = busiProjectApplyShenpiMapper.selectBusiProjectApplyShenpiByCurrent(projectApplyId);
					if (Objects.nonNull(busiProjectApplyShenpi)) {
						Long shenpiUserId = busiProjectApplyShenpi.getShenpiUserId();
						if (userId.longValue() == shenpiUserId.longValue()) {
							BusiProjectApply busiProjectApply = new BusiProjectApply();
							busiProjectApply.setProjectApplyId(projectApplyId);
							busiProjectApply.setUpdateTime(now);
							busiProjectApply.setUpdateBy(username);
							BusiProjectApplyShenpi busiProjectApplyShenpiUp = new BusiProjectApplyShenpi();
							busiProjectApplyShenpiUp.setProjectApplyShenpiId(busiProjectApplyShenpi.getProjectApplyShenpiId());
							busiProjectApplyShenpiUp.setRemark(remark);
							busiProjectApplyShenpiUp.setCheckStatus(status);
							busiProjectApplyShenpiUp.setUpdateTime(now);
							busiProjectApplyShenpiUp.setUpdateBy(username);
							if ("1".equals(status)) {
								busiProjectApplyShenpiUp.setIsCurrent("0");
								Integer sortOrder = busiProjectApplyShenpi.getSortOrder();
								BusiProjectApplyShenpi nextNode = busiProjectApplyShenpiMapper.selectNextNode(projectApplyId,sortOrder);
								if (Objects.nonNull(nextNode)) {
									nextNode.setIsCurrent("1");
									busiProjectApplyShenpiMapper.updateBusiProjectApplyShenpi(nextNode);
								}else{
									//审核全部通过
									busiProjectApply.setStatus("2");
									busiProjectApplyMapper.updateBusiProjectApply(busiProjectApply);
								}
							}else{
								//审核拒绝
								busiProjectApply.setStatus("3");
								busiProjectApplyMapper.updateBusiProjectApply(busiProjectApply);
							}
							busiProjectApplyShenpiMapper.updateBusiProjectApplyShenpi(busiProjectApplyShenpiUp);
						}else{
							throw new CustomException("你不是当前审批人");
						}
					}
				}
			}
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> projectLeaderWorkflowCount() {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			int i = busiProjectLeaderWorkflowMapper.projectLeaderWorkflowCount(userId);
			resultMap.put("code",200);
			resultMap.put("data",i);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> projectLeaderWorkflowList(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			if (Objects.nonNull(busiProjectLeaderWorkflow.getPage()) && Objects.nonNull(busiProjectLeaderWorkflow.getLimit())) {
				PageHelper.startPage(busiProjectLeaderWorkflow.getPage(), busiProjectLeaderWorkflow.getLimit());
			}
			Long userId = Long.valueOf(SecurityUtils.getUserId());
			List<BusiProjectLeaderWorkflow> busiProjectLeaderWorkflows = busiProjectLeaderWorkflowMapper.selectBusiProjectLeaderWorkflows(busiProjectLeaderWorkflow);
			PageInfo<BusiProjectLeaderWorkflow> pageInfo = new PageInfo<BusiProjectLeaderWorkflow>(busiProjectLeaderWorkflows);
			resultMap.put("code",200);
			resultMap.put("data",busiProjectLeaderWorkflows);
			resultMap.put("pageInfo",pageInfo);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> projectLeaderWorkflowShenpi(BusiProjectLeaderWorkflow busiProjectLeaderWorkflow) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			busiProjectLeaderWorkflow.setUpdateBy(username);
			busiProjectLeaderWorkflow.setUpdateTime(now);
			busiProjectLeaderWorkflowMapper.updateBusiProjectLeaderWorkflow(busiProjectLeaderWorkflow);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
}
