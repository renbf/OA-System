package com.xhkj.project.system.service;

import java.util.List;
import com.xhkj.project.system.domain.SysCompanyConfig;

/**
 * 工时配置Service接口
 * 
 * @author xhkj
 * @date 2020-05-28
 */
public interface SysCompanyConfigService
{
    /**
     * 查询工时配置
     * 
     * @param workHourConfigId 工时配置ID
     * @return 工时配置
     */
    public SysCompanyConfig selectSysCompanyConfigById(Long workHourConfigId);

    /**
     * 查询工时配置列表
     * 
     * @param sysCompanyConfig 工时配置
     * @return 工时配置集合
     */
    public List<SysCompanyConfig> selectSysCompanyConfigList(SysCompanyConfig sysCompanyConfig);

    /**
     * 新增工时配置
     * 
     * @param sysCompanyConfig 工时配置
     * @return 结果
     */
    public int insertSysCompanyConfig(SysCompanyConfig sysCompanyConfig);

    /**
     * 修改工时配置
     * 
     * @param sysCompanyConfig 工时配置
     * @return 结果
     */
    public int updateSysCompanyConfig(SysCompanyConfig sysCompanyConfig);

    /**
     * 批量删除工时配置
     * 
     * @param workHourConfigIds 需要删除的工时配置ID
     * @return 结果
     */
    public int deleteSysCompanyConfigByIds(Long[] workHourConfigIds);

    /**
     * 删除工时配置信息
     * 
     * @param companyConfigId 工时配置ID
     * @return 结果
     */
    public int deleteSysCompanyConfigById(Long companyConfigId);

    /**
     * 批量新增
     * @param list
     * @return
     */
    public int addComConfigList(List<SysCompanyConfig> list);



    public SysCompanyConfig selectSysCompanyConfig(SysCompanyConfig sysCompanyConfig);


    public String getConfigValue(String comConfigKey);


}
