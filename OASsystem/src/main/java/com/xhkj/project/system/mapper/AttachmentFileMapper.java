package com.xhkj.project.system.mapper;

import com.xhkj.project.system.domain.AttachmentFile;
import java.util.List;	
/**
 * 附件 数据层
 * 
 * @author rbf
 * @date 2020-10-22
 */
public interface AttachmentFileMapper
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
	public List<AttachmentFile> selectAttachmentFileList(AttachmentFile attachmentFile);
	
	/**
     * 新增附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	public int insertAttachmentFile(AttachmentFile attachmentFile);
	
	/**
     * 修改附件
     * 
     * @param attachmentFile 附件信息
     * @return 结果
     */
	public int updateAttachmentFile(AttachmentFile attachmentFile);
	
	/**
     * 删除附件
     * 
     * @param fileId 附件ID
     * @return 结果
     */
	public int deleteAttachmentFileById(Long fileId);
	
	/**
     * 批量删除附件
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteAttachmentFileByIds(String[] fileIds);

	/**
	 * 批量新增附件
	 *
	 * @param list 附件信息
	 * @return 结果
	 */
	public int insertAttachmentFileBatch(List<AttachmentFile> list);
	//以上自动生成的尽量别动
}