package com.xhkj.project.system.controller;

import com.xhkj.framework.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.xhkj.project.system.domain.AttachmentFile;
import com.xhkj.project.system.service.IAttachmentFileService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * 附件 信息操作处理
 * 
 * @author rbf
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/api/attachmentFile")
public class AttachmentFileController
{
    private static final Logger log = LoggerFactory.getLogger(AttachmentFileController.class);

	@Autowired
	private IAttachmentFileService attachmentFileService;
	@Autowired
	private ServerConfig serverConfig;
	/**
	 * 新增保存附件
	 */
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,Object> addSave(MultipartFile file)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = attachmentFileService.insertAttachmentFile(file);
		} catch (Exception e) {
			log.error("新增保存附件异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","新增保存附件异常");
		}
		return resultMap;
	}

	/**
	 * 批量保存附件
	 */
	@PostMapping("/uploads")
	@ResponseBody
	public Map<String,Object> addSaveBatch(MultipartFile[] files)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = attachmentFileService.insertAttachmentFileBatch(files);
		} catch (Exception e) {
			log.error("批量保存附件异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","批量保存附件异常");
		}
		return resultMap;
	}
	@GetMapping("/download/{fileId}")
	public String downloadFile(HttpServletResponse response, @PathVariable("fileId") String fileId) {
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			Long fileIdL = Long.valueOf(fileId);
			AttachmentFile attachmentFile = attachmentFileService.selectAttachmentFileById(fileIdL);
			if (Objects.nonNull(attachmentFile)) {
				String fileName = attachmentFile.getFileName();// 文件名
				//设置文件路径
				File file = new File(attachmentFile.getFilePath());
				if (file.exists()) {
					response.setContentType("application/force-download");// 设置强制下载不打开
					response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(),"iso-8859-1"));// 设置文件名
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					os.flush();
					return "下载成功";
				}
			}
		} catch (Exception e) {
			log.error("下载异常",e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "下载失败";
	}

	/**
	 * 批量保存附件
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Map<String,Object> remove(Long fileId)
	{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			AttachmentFile attachmentFile = attachmentFileService.selectAttachmentFileById(fileId);
			if (Objects.nonNull(attachmentFile)) {
				String fileName = attachmentFile.getFileName();// 文件名
				//设置文件路径
				File file = new File(attachmentFile.getFilePath());
				if (file.exists()) {
					file.delete();
				}
			}
			resultMap = attachmentFileService.deleteAttachmentFileByIds(fileId);
		} catch (Exception e) {
			log.error("删除附件异常",e);
			resultMap.put("code",-1);
			resultMap.put("msg","删除附件异常");
		}
		return resultMap;
	}
	
}
