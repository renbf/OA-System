package com.xhkj.project.system.service;

import com.xhkj.project.system.domain.AttachmentFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 附件 服务层
 * 
 * @author rbf
 * @date 2020-10-22
 */
public interface IAttachmentFileService
{
	/**
     * 查询附件信息
     * 
     * @param fileId 附件ID
     * @return 附件信息
     */
	public AttachmentFile selectAttachmentFileById(Long fileId);
	
	/**
     * 查询附件列表
     * 
     * @param attachmentFile 附件信息
     * @return 附件集合
     */
	public Map<String, Object> selectAttachmentFileList(AttachmentFile attachmentFile);
	
	/**
     * 新增附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	public Map<String, Object> insertAttachmentFile(AttachmentFile attachmentFile);
	
	/**
     * 修改附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	public Map<String, Object> updateAttachmentFile(AttachmentFile attachmentFile);
		
	/**
     * 删除附件信息
     * 
     * @param fileId 需要删除的数据ID
     * @return 结果
     */
	public Map<String, Object> deleteAttachmentFileByIds(Long fileId);
	//以上自动生成的尽量别动

	/**
	 * 新增附件
	 *
	 * @return 结果
	 */
	public Map<String, Object> insertAttachmentFile(MultipartFile file);

	/**
	 * 批量新增附件
	 * @param files
	 * @return
	 */
	public Map<String, Object> insertAttachmentFileBatch(MultipartFile[] files);
}
