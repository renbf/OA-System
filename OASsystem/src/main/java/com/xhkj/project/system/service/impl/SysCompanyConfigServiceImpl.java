package com.xhkj.project.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.system.service.SysCompanyConfigService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.system.mapper.SysCompanyConfigMapper;
import com.xhkj.project.system.domain.SysCompanyConfig;

/**
 * 工时配置Service业务层处理
 * 
 * @author xhkj
 * @date 2020-05-28
 */
@Service
public class SysCompanyConfigServiceImpl implements SysCompanyConfigService
{
    @Autowired
    private SysCompanyConfigMapper sysCompanyConfigMapper;

    /**
     * 查询工时配置
     * 
     * @param companyConfigId 工时配置ID
     * @return 工时配置
     */
    @Override
    public SysCompanyConfig selectSysCompanyConfigById(Long companyConfigId)
    {
        return sysCompanyConfigMapper.selectSysCompanyConfigById(companyConfigId);
    }

    /**
     * 查询工时配置列表
     * 
     * @param sysCompanyConfig 工时配置
     * @return 工时配置
     */
    @Override
    public List<SysCompanyConfig> selectSysCompanyConfigList(SysCompanyConfig sysCompanyConfig)
    {
        return sysCompanyConfigMapper.selectSysCompanyConfigList(sysCompanyConfig);
    }

    /**
     * 新增工时配置
     * 
     * @param sysCompanyConfig 工时配置
     * @return 结果
     */
    @Override
    public int insertSysCompanyConfig(SysCompanyConfig sysCompanyConfig)
    {
        sysCompanyConfig.setCreateTime(DateUtils.getNowDate());
        sysCompanyConfig.setCreateBy(SecurityUtils.getUsername());
        return sysCompanyConfigMapper.insertSysCompanyConfig(sysCompanyConfig);
    }

    /**
     * 修改工时配置
     * 
     * @param sysCompanyConfig 工时配置
     * @return 结果
     */
    @Override
    public int updateSysCompanyConfig(SysCompanyConfig sysCompanyConfig)
    {
        sysCompanyConfig.setUpdateTime(DateUtils.getNowDate());
        return sysCompanyConfigMapper.updateSysCompanyConfig(sysCompanyConfig);
    }

    /**
     * 批量删除工时配置
     * 
     * @param workHourConfigIds 需要删除的工时配置ID
     * @return 结果
     */
    @Override
    public int deleteSysCompanyConfigByIds(Long[] workHourConfigIds)
    {
        return sysCompanyConfigMapper.deleteSysCompanyConfigByIds(workHourConfigIds);
    }

    /**
     * 删除工时配置信息
     * 
     * @param companyConfigId 工时配置ID
     * @return 结果
     */
    @Override
    public int deleteSysCompanyConfigById(Long companyConfigId)
    {
        return sysCompanyConfigMapper.deleteSysCompanyConfigById(companyConfigId);
    }


    @Override
    public int addComConfigList(List<SysCompanyConfig> list)
    {

        Date nowDate = DateUtils.getNowDate();
        String username = SecurityUtils.getUsername();
        for (SysCompanyConfig sysCompanyConfig : list) {
            sysCompanyConfig.setCreateTime(nowDate);
            sysCompanyConfig.setCreateBy(username);
        }

        return sysCompanyConfigMapper.addComConfigList(list);
    }


    @Override
    public int addOverPeriodList(List<SysCompanyConfig> list)
    {
        Object[] keys = list.stream().map(SysCompanyConfig::getComConfigKey).toArray();
        String keyStrs[] = Arrays.stream(keys).toArray(String[]::new);

        sysCompanyConfigMapper.deleteSysCompanyConfigByKeys(keyStrs);

        Date nowDate = DateUtils.getNowDate();
        String username = SecurityUtils.getUsername();
        for (SysCompanyConfig sysCompanyConfig : list) {
            sysCompanyConfig.setCreateTime(nowDate);
            sysCompanyConfig.setCreateBy(username);
            sysCompanyConfig.setUpdateTime(nowDate);
            sysCompanyConfig.setUpdateBy(username);
        }

        return sysCompanyConfigMapper.addComConfigList(list);
    }

    @Override
    public int updateComConfigList(List<SysCompanyConfig> list)
    {

        Date nowDate = DateUtils.getNowDate();
        String username = SecurityUtils.getUsername();
        for (SysCompanyConfig sysCompanyConfig : list) {
            sysCompanyConfig.setUpdateTime(nowDate);
            sysCompanyConfig.setUpdateBy(username);
        }

        return sysCompanyConfigMapper.updateComConfigList(list);
    }


    @Override
    public SysCompanyConfig selectSysCompanyConfig(SysCompanyConfig sysCompanyConfig)
    {
        return sysCompanyConfigMapper.selectSysCompanyConfig(sysCompanyConfig);
    }

    @Override
    public String getConfigValue(String comConfigKey) {
        return this.selectSysCompanyConfig(new SysCompanyConfig(comConfigKey)).getComConfigValue();
    }

}
