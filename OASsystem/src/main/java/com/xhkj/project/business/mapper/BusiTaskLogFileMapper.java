package com.xhkj.project.business.mapper;

import com.xhkj.project.business.domain.BusiTaskLogFile;
import java.util.List;	
/**
 * 任务日志文件 数据层
 * 
 * @author rbf
 * @date 2020-10-23
 */
public interface BusiTaskLogFileMapper
{
	/**
     * 查询任务日志文件信息
     * 
     * @param taskLogFileId 任务日志文件ID
     * @return 任务日志文件信息
     */
	public BusiTaskLogFile selectBusiTaskLogFileById(Long taskLogFileId);
	
	/**
     * 查询任务日志文件列表
     * 
     * @param busiTaskLogFile 任务日志文件信息
     * @return 任务日志文件集合
     */
	public List<BusiTaskLogFile> selectBusiTaskLogFileList(BusiTaskLogFile busiTaskLogFile);
	
	/**
     * 新增任务日志文件
     * 
     * @param busiTaskLogFile 任务日志文件信息
     * @return 结果
     */
	public int insertBusiTaskLogFile(BusiTaskLogFile busiTaskLogFile);
	
	/**
     * 修改任务日志文件
     * 
     * @param busiTaskLogFile 任务日志文件信息
     * @return 结果
     */
	public int updateBusiTaskLogFile(BusiTaskLogFile busiTaskLogFile);
	
	/**
     * 删除任务日志文件
     * 
     * @param taskLogFileId 任务日志文件ID
     * @return 结果
     */
	public int deleteBusiTaskLogFileById(Long taskLogFileId);
	
	/**
     * 批量删除任务日志文件
     * 
     * @param taskLogFileIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusiTaskLogFileByIds(String[] taskLogFileIds);

	/**
	 * 批量新增任务日志文件
	 *
	 * @param list 任务日志文件信息
	 * @return 结果
	 */
	public int insertBusiTaskLogFileBatch(List<BusiTaskLogFile> list);
	//以上自动生成的尽量别动

	public List<BusiTaskLogFile> selectBusiTaskLogFiles(Long taskLogId);
}