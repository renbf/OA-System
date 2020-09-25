package com.xhkj.project.business.service.impl;

import com.xhkj.common.utils.ServletUtils;
import com.xhkj.framework.security.LoginUser;
import com.xhkj.framework.security.service.TokenService;
import com.xhkj.project.business.domain.vo.BusiProjectVo;
import com.xhkj.project.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xhkj.project.business.mapper.BusiProjectMapper;
import com.xhkj.project.business.domain.BusiProject;
import com.xhkj.project.business.service.IBusiProjectService;


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
			resultMap.put("code",0);
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
	public Map<String,Object> insertBusiProject(BusiProject busiProject)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			busiProjectMapper.insertBusiProject(busiProject);
			resultMap.put("code",0);
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
				busiProjectMapper.updateBusiProject(busiProject);
			resultMap.put("code",0);
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
			resultMap.put("code",0);
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
			LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
			SysUser user = loginUser.getUser();
			busiProjectVo.setMemberId(user.getUserId());
			List<BusiProjectVo> list = busiProjectMapper.selectBusiProjects(busiProjectVo);
			resultMap.put("code",0);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
}
