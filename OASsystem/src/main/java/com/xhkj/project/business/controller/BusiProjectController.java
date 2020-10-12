package com.xhkj.project.business.controller;

import com.xhkj.project.business.domain.BusiTask;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import com.xhkj.project.business.domain.BusiProject;
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
	@PostMapping( "/remove")
	@ResponseBody
	public Map<String,Object> remove(String ids)
	{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
			resultMap = busiProjectService.deleteBusiProjectByIds(ids);
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
	public Map<String,Object> listTask(BusiTask busiTask)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = busiProjectService.selectListTask(busiTask);
		} catch (Exception e) {
			log.error("查询项目列表异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","查询项目列表异常");
		}
		return resultMap;
	}
}
