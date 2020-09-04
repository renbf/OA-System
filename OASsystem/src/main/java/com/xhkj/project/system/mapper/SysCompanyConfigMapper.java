package com.xhkj.project.system.mapper;

import java.util.List;
import com.xhkj.project.system.domain.SysCompanyConfig;

/**
 * 工时配置Mapper接口
 * 
 * @author xhkj
 * @date 2020-05-28
 */
public interface SysCompanyConfigMapper
{
    /**
     * 查询工时配置
     * 
     * @param companyConfigId 工时配置ID
     * @return 工时配置
     */
    public SysCompanyConfig selectSysCompanyConfigById(Long companyConfigId);

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
     * 删除工时配置
     * 
     * @param companyConfigId 工时配置ID
     * @return 结果
     */
    public int deleteSysCompanyConfigById(Long workHourConfigId);

    /**
     * 批量删除工时配置
     * 
     * @param companyConfigIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCompanyConfigByIds(Long[] companyConfigIds);

    public int deleteSysCompanyConfig();

    /**
     * 批量新增
     *
     */
    public int addComConfigList(List<SysCompanyConfig> list);


    public SysCompanyConfig selectSysCompanyConfig(SysCompanyConfig sysCompanyConfig);

}
