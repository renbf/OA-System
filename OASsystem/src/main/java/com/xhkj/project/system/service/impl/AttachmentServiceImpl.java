package com.xhkj.project.system.service.impl;


import com.xhkj.common.core.text.Convert;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.business.service.impl.BusiReimTrafficFeeServiceImpl;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.mapper.AttachmentMapper;
import com.xhkj.project.system.service.IAttachmentService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 附件 服务层实现
 * 
 * @author ysu
 * @date 2020-03-23
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService
{
	@Autowired
	private AttachmentMapper attachmentMapper;

	protected final Logger logger = LoggerFactory.getLogger(BusiReimTrafficFeeServiceImpl.class);



	/**
     * 查询附件信息
     * 
     * @param id 附件ID
     * @return 附件信息
     */
    @Override
	public Attachment selectAttachmentById(Long id)
	{
	    return attachmentMapper.selectAttachmentById(id);
	}
	
	/**
     * 查询附件列表
     * 
     * @param attachment 附件信息
     * @return 附件集合
     */
	@Override
	public List<Attachment> selectAttachmentList(Attachment attachment)
	{
	    return attachmentMapper.selectAttachmentList(attachment);
	}
	
    /**
     * 新增附件
     * 
     * @param attachment 附件信息
     * @return 结果
     */
	@Override
	public int insertAttachment(Attachment attachment)
	{
		return attachmentMapper.insertAttachment(attachment);
	}
	
	/**
     * 修改附件
     * 
     * @param attachment 附件信息
     * @return 结果
     */
	@Override
	public int updateAttachment(Attachment attachment)
	{
	    return attachmentMapper.updateAttachment(attachment);
	}

	/**
     * 删除附件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAttachmentByIds(String ids)
	{
		return attachmentMapper.deleteAttachmentByIds(Convert.toStrArray(ids));
	}


	@Override
	public boolean deleteFileList(List<Attachment> fileList){

		boolean isDelete = true;

		if(CollectionUtils.isNotEmpty(fileList)){
			//删除文件
			for (Attachment attachment : fileList) {
				Long id = attachment.getId();
				String filePath = attachment.getFilePath();
				File file = new File(filePath);
				try{
					if(file.exists()){
						isDelete = file.delete();
						if(isDelete){
							this.deleteAttachmentByIds(String.valueOf(id));
						}
					}
				}catch (Exception e){
					e.printStackTrace();
					logger.info("文件删除失败");
				}
			}
		}
		return isDelete;
	}
	
}
