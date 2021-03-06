package com.xhkj.project.business.controller;

import com.xhkj.project.business.domain.*;
import com.xhkj.project.business.domain.vo.BusiProjectApplyVo;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import com.xhkj.project.business.domain.vo.BusiTaskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import com.xhkj.project.business.service.IBusiProjectService;


/**
 * 项目 信息操作处理
 * 
 * @author rbf
 * @date 2020-09-24
 */
@Controller
@RequestMapping("/api/busiProject")
public class BusiProjectController
{
    private static final Logger log = LoggerFactory.getLogger(BusiProjectController.class);

	@Autowired
	private IBusiProjectService busiProjectService;
	
	/**
	 * 查询项目列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public Map<String,Object> list(BusiProjectVo busiProjectVo)
	{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			resultMap = busiProjectService.selectBusiProjects(busiProjectVo);
        } catch (Exception e) {
            log.error("查询项目列表异常",e);
            resultMap.put("code",-1);
            resultMap.put("msg","查询项目列表异常");
        }
        return resultMap;
	}
	

	/**
	 * 新增保存项目
	 */
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> addSave(@RequestBody BusiProject busiProject)
	{		
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			resultMap = busiProjectService.insertBusiProject(busiProject);
        } catch (Exception e) {
            log.error("新增保存项目异常",e);
            resultMap.put("code",-1);
            resultMap.put("msg","新增保存项目异常");
        }
        return resultMap;
	}

	/**
	 * 修改保存项目
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Map<String,Object> editSave(@RequestBody BusiProject busiProject)
	{		
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			resultMap = busiProjectService.updateBusiProject(busiProject);
        } catch (Exception e) {
            log.error("修改保存项目异常",e);
            resultMap.put("code",-1);
            resultMap.put("msg","修改保存项目异常");
        }
        return resultMap;
	}
	
	/**
	 * 删除项目
	 */
	@DeleteMapping( "/remove/{projectId}")
	@ResponseBody
	public Map<String,Object> remove(@PathVariable Long projectId)
	{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			resultMap = busiProjectService.deleteBusiProjectByIds(projectId);
        } catch (Exception e) {
            log.error("删除项目异常",e);
            resultMap.put("code",-1);
            resultMap.put("msg","删除项目异常");
        }
        return resultMap;
	}

	/**
	 * 查询项目详情
	 */
	@GetMapping("/getProjectInfo")
	@ResponseBody
	public Map<String,Object> getProjectInfo(Long projectId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.getProjectInfo(projectId);
		} catch (Exception e) {
			log.error("查询项目详情异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","查询项目详情异常");
		}
		return resultMap;
	}

	/**
	 * 启用禁用项目
	 */
	@PostMapping("/changeStatus")
	@ResponseBody
	public Map<String,Object> changeStatus(@RequestBody BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.changeStatus(busiProject);
		} catch (Exception e) {
			log.error("启用禁用项目异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","启用禁用项目异常");
		}
		return resultMap;
	}

	/**
	 * 新增任务
	 */
	@PostMapping("/addTask")
	@ResponseBody
	public Map<String,Object> addTask(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.insertBusiTask(busiTask);
		} catch (Exception e) {
			log.error("新增任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","新增任务异常");
		}
		return resultMap;
	}

	/**
	 * 查询任务列表
	 */
	@GetMapping("/listTask")
	@ResponseBody
	public Map<String,Object> listTask(BusiTaskVo busiTaskVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.selectListTask(busiTaskVo);
		} catch (Exception e) {
			log.error("查询任务列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","查询任务列表异常");
		}
		return resultMap;
	}

	/**
	 * 查询任务详情
	 */
	@GetMapping("/getTaskInfo")
	@ResponseBody
	public Map<String,Object> getTaskInfo(Long taskId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.getTaskInfo(taskId);
		} catch (Exception e) {
			log.error("查询任务详情异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","查询任务详情异常");
		}
		return resultMap;
	}

	/**
	 * 修改任务
	 */
	@PostMapping("/updateTask")
	@ResponseBody
	public Map<String,Object> updateTask(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.updateBusiTask(busiTask);
		} catch (Exception e) {
			log.error("修改任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","修改任务异常");
		}
		return resultMap;
	}

	/**
	 * 启用禁用任务
	 */
	@PostMapping("/changeTaskStatus")
	@ResponseBody
	public Map<String,Object> changeTaskStatus(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.changeTaskStatus(busiTask);
		} catch (Exception e) {
			log.error("启用禁用任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","启用禁用任务异常");
		}
		return resultMap;
	}

	/**
	 * 删除任务
	 */
	@PostMapping( "/removeTask")
	@ResponseBody
	public Map<String,Object> removeTask(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.removeTask(busiTask);
		} catch (Exception e) {
			log.error("删除任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","删除任务异常");
		}
		return resultMap;
	}

	/**
	 * 关闭项目
	 */
	@PostMapping("/closeProject")
	@ResponseBody
	public Map<String,Object> closeProject(@RequestBody BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.closeProject(busiProject);
		} catch (Exception e) {
			log.error("关闭项目异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","关闭项目异常");
		}
		return resultMap;
	}

	/**
	 * 关闭任务
	 */
	@PostMapping("/closeTask")
	@ResponseBody
	public Map<String,Object> closeTask(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.closeTask(busiTask);
		} catch (Exception e) {
			log.error("关闭任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","关闭任务异常");
		}
		return resultMap;
	}

	/**
	 * 新增任务日志
	 */
	@PostMapping("/addTaskLog")
	@ResponseBody
	public Map<String,Object> addTaskLog(@RequestBody BusiTaskLog busiTaskLog)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.insertBusiTaskLog(busiTaskLog);
		} catch (Exception e) {
			log.error("新增任务日志异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","新增任务日志异常");
		}
		return resultMap;
	}

	/**
	 * 修改任务日志
	 */
	@PostMapping("/updateTaskLog")
	@ResponseBody
	public Map<String,Object> updateTaskLog(@RequestBody BusiTaskLog busiTaskLog)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.updateTaskLog(busiTaskLog);
		} catch (Exception e) {
			log.error("修改任务日志异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","修改任务日志异常");
		}
		return resultMap;
	}

	/**
	 * 查询今日任务日志
	 */
	@GetMapping("/getDayTaskLog")
	@ResponseBody
	public Map<String,Object> getDayTaskLog(Long taskId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.getDayTaskLog(taskId);
		} catch (Exception e) {
			log.error("查询今日任务日志异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","查询今日任务日志异常");
		}
		return resultMap;
	}

	/**
	 * 报送任务
	 */
	@PostMapping("/taskLogBaosong")
	@ResponseBody
	public Map<String,Object> taskLogBaosong(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.taskLogBaosong(busiTask);
		} catch (Exception e) {
			log.error("报送任务异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","报送任务异常");
		}
		return resultMap;
	}

	/**
	 * 修改任务进度
	 */
	@PostMapping("/updateTaskProgress")
	@ResponseBody
	public Map<String,Object> updateTaskProgress(@RequestBody BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.updateTaskProgress(busiTask);
		} catch (Exception e) {
			log.error("修改任务进度异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","修改任务进度异常");
		}
		return resultMap;
	}

	/**
	 * 新增项目申请
	 */
	@PostMapping("/addProjectApply")
	@ResponseBody
	public Map<String,Object> addProjectApply(@RequestBody BusiProjectApply busiProjectApply)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.insertProjectApply(busiProjectApply);
		} catch (Exception e) {
			log.error("新增项目申请异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","新增项目申请异常");
		}
		return resultMap;
	}

	/**
	 * 修改项目申请
	 */
	@PostMapping("/updateProjectApply")
	@ResponseBody
	public Map<String,Object> updateProjectApply(@RequestBody BusiProjectApply busiProjectApply)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.updateProjectApply(busiProjectApply);
		} catch (Exception e) {
			log.error("修改项目申请异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","修改项目申请异常");
		}
		return resultMap;
	}

	/**
	 * 删除项目申请
	 */
	@PostMapping( "/removeProjectApply")
	@ResponseBody
	public Map<String,Object> removeProjectApply(@RequestBody BusiProjectApplyVo busiProjectApplyVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.removeProjectApply(busiProjectApplyVo);
		} catch (Exception e) {
			log.error("删除项目申请异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","删除项目申请异常");
		}
		return resultMap;
	}

	/**
	 * 项目申请列表
	 */
	@PostMapping("/listProjectApply")
	@ResponseBody
	public Map<String,Object> listProjectApply(@RequestBody BusiProjectApplyVo busiProjectApplyVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.listProjectApply(busiProjectApplyVo);
		} catch (Exception e) {
			log.error("项目申请列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目申请列表异常");
		}
		return resultMap;
	}

	/**
	 * 项目申请审批列表
	 */
	@GetMapping("/listProjectApplyShenpi")
	@ResponseBody
	public Map<String,Object> listProjectApplyShenpi(Long projectApplyId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.listProjectApplyShenpi(projectApplyId);
		} catch (Exception e) {
			log.error("项目申请审批列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目申请审批列表异常");
		}
		return resultMap;
	}

	/**
	 * 报送项目申请
	 */
	@PostMapping( "/baosongProjectApply")
	@ResponseBody
	public Map<String,Object> baosongProjectApply(@RequestBody BusiProjectApplyVo busiProjectApplyVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.baosongProjectApply(busiProjectApplyVo);
		} catch (Exception e) {
			log.error("报送项目申请异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","报送项目申请异常");
		}
		return resultMap;
	}

	/**
	 * 待办项目审批列表
	 */
	@GetMapping("/todolist")
	@ResponseBody
	public Map<String,Object> todolist(BusiProjectVo busiProjectVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.todolist(busiProjectVo);
		} catch (Exception e) {
			log.error("待办项目审批列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","待办项目审批列表异常");
		}
		return resultMap;
	}

	/**
	 * 项目申请待审核列表
	 */
	@PostMapping("/todolistProjectApply")
	@ResponseBody
	public Map<String,Object> todolistProjectApply(@RequestBody BusiProjectApplyVo busiProjectApplyVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.todolistProjectApply(busiProjectApplyVo);
		} catch (Exception e) {
			log.error("项目申请待审核列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目申请待审核列表异常");
		}
		return resultMap;
	}

	/**
	 * 批量项目申请审批
	 */
	@PostMapping("/batchProjectApplyShenpi")
	@ResponseBody
	public Map<String,Object> batchProjectApplyShenpi(@RequestBody BusiProjectApplyVo busiProjectApplyVo)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.batchProjectApplyShenpi(busiProjectApplyVo);
		} catch (Exception e) {
			log.error("批量项目申请审批异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","批量项目申请审批异常");
		}
		return resultMap;
	}

	/**
	 * 项目负责人审批待办数量
	 */
	@GetMapping("/projectLeaderWorkflowCount")
	@ResponseBody
	public Map<String,Object> projectLeaderWorkflowCount()
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.projectLeaderWorkflowCount();
		} catch (Exception e) {
			log.error("项目负责人审批待办数量异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目负责人审批待办数量异常");
		}
		return resultMap;
	}

	/**
	 * 项目负责人审批列表
	 */
	@PostMapping("/projectLeaderWorkflowList")
	@ResponseBody
	public Map<String,Object> projectLeaderWorkflowList(@RequestBody BusiProjectLeaderWorkflow busiProjectLeaderWorkflow)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.projectLeaderWorkflowList(busiProjectLeaderWorkflow);
		} catch (Exception e) {
			log.error("项目负责人审批列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目负责人审批列表异常");
		}
		return resultMap;
	}

	/**
	 * 项目负责人审批
	 */
	@PostMapping("/projectLeaderWorkflowShenpi")
	@ResponseBody
	public Map<String,Object> projectLeaderWorkflowShenpi(@RequestBody BusiProjectLeaderWorkflow busiProjectLeaderWorkflow)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.projectLeaderWorkflowShenpi(busiProjectLeaderWorkflow);
		} catch (Exception e) {
			log.error("项目负责人审批异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","项目负责人审批异常");
		}
		return resultMap;
	}

}
