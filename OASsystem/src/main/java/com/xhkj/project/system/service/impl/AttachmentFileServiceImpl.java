package com.xhkj.project.system.service.impl;

import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.file.FileUploadSonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xhkj.project.system.mapper.AttachmentFileMapper;
import com.xhkj.project.system.domain.AttachmentFile;
import com.xhkj.project.system.service.IAttachmentFileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 附件 服务层实现
 * 
 * @author rbf
 * @date 2020-10-22
 */
@Service
public class AttachmentFileServiceImpl implements IAttachmentFileService
{
	private static final Logger log = LoggerFactory.getLogger(AttachmentFileServiceImpl.class);
	private static final String readFileUrl = "/api/attachmentFile/download/";
	@Autowired
	private AttachmentFileMapper attachmentFileMapper;

	/**
     * 查询附件信息
     * 
     * @param fileId 附件ID
     * @return 附件信息
     */
    @Override
	public AttachmentFile selectAttachmentFileById(Long fileId)
	{
	    return attachmentFileMapper.selectAttachmentFileById(fileId);
	}
	
	/**
     * 查询附件列表
     * 
     * @param attachmentFile 附件信息
     * @return 附件集合
     */
	@Override
	public Map<String,Object> selectAttachmentFileList(AttachmentFile attachmentFile)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			List<AttachmentFile> list = attachmentFileMapper.selectAttachmentFileList(attachmentFile);
			resultMap.put("code",200);
			resultMap.put("data",list);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	
    /**
     * 新增附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	@Override
	public Map<String,Object> insertAttachmentFile(AttachmentFile attachmentFile)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			attachmentFileMapper.insertAttachmentFile(attachmentFile);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	
	/**
     * 修改附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	@Override
	public Map<String,Object> updateAttachmentFile(AttachmentFile attachmentFile)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
				attachmentFileMapper.updateAttachmentFile(attachmentFile);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	/**
     * 删除附件对象
     * 
     * @param fileId 需要删除的数据ID
     * @return 结果
     */
	@Override
	public Map<String,Object> deleteAttachmentFileByIds(Long fileId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			attachmentFileMapper.deleteAttachmentFileById(fileId);
			resultMap.put("code",200);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
	//以上自动生成的尽量别动


	@Override
	@Transactional
	public Map<String, Object> insertAttachmentFile(MultipartFile file) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			String fileType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
			if(fileType.equals(".jsp")){
				resultMap.put("code",-1);
				resultMap.put("msg","不允许上传.jsp格式");
				return resultMap;
			}
			if(fileType.equals(".exe")){
				resultMap.put("code",-2);
				resultMap.put("msg","不允许上传.exe格式");
				return resultMap;
			}
			if(fileType.equals(".bat")){
				resultMap.put("code",-3);
				resultMap.put("msg","不允许上传.bat格式");
				return resultMap;
			}
			String path = FileUploadSonUtils.upload(file);
			AttachmentFile attachmentFile = new AttachmentFile();
			attachmentFile.setFileName(file.getOriginalFilename());
			attachmentFile.setFilePath(path);
			attachmentFile.setEnableStatus("1");
			attachmentFile.setFileUrl("#");
			attachmentFile.setCreateBy(username);
			attachmentFile.setCreateTime(now);
			attachmentFileMapper.insertAttachmentFile(attachmentFile);
			Long fileId = attachmentFile.getFileId();
			AttachmentFile attachmentFileUp = new AttachmentFile();
			attachmentFileUp.setFileId(fileId);
			attachmentFileUp.setFileUrl(readFileUrl+fileId);
			attachmentFileMapper.updateAttachmentFile(attachmentFileUp);
			resultMap.put("code",200);
			resultMap.put("url", attachmentFileUp.getFileUrl());
			resultMap.put("fileId",fileId);
			resultMap.put("fileName",file.getOriginalFilename());
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> insertAttachmentFileBatch(MultipartFile[] files) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Date now = new Date();
			String username = SecurityUtils.getUsername();
			StringBuffer urlbf = new StringBuffer();
			if(files != null && files.length > 0){
				for(MultipartFile file:files){
					String path = FileUploadSonUtils.upload(file);
					AttachmentFile attachmentFile = new AttachmentFile();
					attachmentFile.setFileName(file.getOriginalFilename());
					attachmentFile.setFilePath(path);
					attachmentFile.setEnableStatus("1");
					attachmentFile.setFileUrl("#");
					attachmentFile.setCreateBy(username);
					attachmentFile.setCreateTime(now);
					attachmentFileMapper.insertAttachmentFile(attachmentFile);
					Long fileId = attachmentFile.getFileId();
					AttachmentFile attachmentFileUp = new AttachmentFile();
					attachmentFileUp.setFileId(fileId);
					attachmentFileUp.setFileUrl(readFileUrl+fileId);
					attachmentFileMapper.updateAttachmentFile(attachmentFileUp);
					urlbf.append(attachmentFileUp.getFileUrl()).append(",");
				}
			}
			String urls = urlbf.toString();
			if (StringUtils.isNotEmpty(urls)) {
				urls = urls.substring(0, urls.length() - 1);
			}
			resultMap.put("code",200);
			resultMap.put("urls", urls);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException();
		}
		return resultMap;
	}
}
