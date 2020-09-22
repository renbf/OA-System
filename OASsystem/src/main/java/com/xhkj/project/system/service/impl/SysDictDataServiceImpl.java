package com.xhkj.project.system.service.impl;

import java.util.*;

import com.xhkj.common.constant.Constants;
import com.xhkj.common.utils.JedisUtil;
import com.xhkj.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.system.domain.SysDictData;
import com.xhkj.project.system.mapper.SysDictDataMapper;
import com.xhkj.project.system.service.ISysDictDataService;

/**
 * 字典 业务层处理
 *
 * @author xhkj
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;
    @Autowired
    private JedisUtil jedisUtil;



    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataById(Long dictCode)
    {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(Long[] dictCodes)
    {
        return dictDataMapper.deleteDictDataByIds(dictCodes);
    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData)
    {
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        return dictDataMapper.updateDictData(dictData);
    }


    @Override
    public Map<String, List<SysDictData>> getAllDict() {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Map<String, List<SysDictData>> dictGroupMap = new HashMap<>();
        try {
            Map<String, List<SysDictData>> data = (Map<String, List<SysDictData>>)jedisUtil.get(Constants.ALLDICTDATA, Map.class);
            if(Objects.nonNull(data)){
                return data;
            }
            List<SysDictData> dictAllList= dictDataMapper.selectDictDataList(new SysDictData());
            for (SysDictData dict : dictAllList) {
                String dictGroup = dict.getDictType();
                if (!dictGroupMap.containsKey(dictGroup)) {
                    List<SysDictData> dictList = new ArrayList<>();
                    dictList.add(dict);
                    dictGroupMap.put(dictGroup, dictList);
                }else {
                    List<SysDictData> dictList = dictGroupMap.get(dictGroup);
                    dictList.add(dict);
                }
            }
            jedisUtil.setObject(Constants.ALLDICTDATA,dictGroupMap,1800);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return dictGroupMap;
    }


}
