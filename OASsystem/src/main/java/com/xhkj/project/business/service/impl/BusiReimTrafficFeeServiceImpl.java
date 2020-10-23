package com.xhkj.project.business.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.mapper.AttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimTrafficFeeMapper;
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.business.service.IBusiReimTrafficFeeService;

/**
 * 交通费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimTrafficFeeServiceImpl implements IBusiReimTrafficFeeService 
{
    @Autowired
    private BusiReimTrafficFeeMapper busiReimTrafficFeeMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 查询交通费用
     * 
     * @param trafficId 交通费用ID
     * @return 交通费用
     */
    @Override
    public BusiReimTrafficFee selectBusiReimTrafficFeeById(Long trafficId)
    {
        BusiReimTrafficFee busiReimTrafficFee = busiReimTrafficFeeMapper.selectBusiReimTrafficFeeById(trafficId);
        String fileIds = busiReimTrafficFee.getFileIds();
        if(StringUtils.isNoneBlank(fileIds)){
            String[] ids = fileIds.split(",");
            List<Attachment> attachments = attachmentMapper.selectFileList(ids);
        }

        return busiReimTrafficFee;
    }

    /**
     * 查询交通费用列表
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 交通费用
     */
    @Override
    public List<BusiReimTrafficFee> selectBusiReimTrafficFeeList(BusiReimTrafficFee busiReimTrafficFee)
    {
        List<BusiReimTrafficFee> busiReimTrafficFees = busiReimTrafficFeeMapper.selectBusiReimTrafficFeeList(busiReimTrafficFee);

        busiReimTrafficFees.stream().forEach(e->{
            String fileIds = e.getFileIds();
            if(StringUtils.isNoneBlank(fileIds)){
                String[] ids = fileIds.split(",");
                List<Attachment> attachments = attachmentMapper.selectFileList(ids);
                e.setFileList(attachments);
            }
        });

        return busiReimTrafficFees;
    }

    /**
     * 新增交通费用
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 结果
     */
    @Override
    public int insertBusiReimTrafficFee(BusiReimTrafficFee busiReimTrafficFee)
    {

        int i = 0;
        Long trafficId = busiReimTrafficFee.getTrafficId();
        if(trafficId!=null){
            busiReimTrafficFee.setUpdateBy(SecurityUtils.getUserId());
            busiReimTrafficFee.setUpdateTime(DateUtils.getNowDate());
            i = busiReimTrafficFeeMapper.updateBusiReimTrafficFee(busiReimTrafficFee);
        }else{
            busiReimTrafficFee.setCreateBy(SecurityUtils.getUserId());
            busiReimTrafficFee.setCreateTime(DateUtils.getNowDate());
            i = busiReimTrafficFeeMapper.insertBusiReimTrafficFee(busiReimTrafficFee);
        }

        return i;
    }

    /**
     * 修改交通费用
     * 
     * @param busiReimTrafficFee 交通费用
     * @return 结果
     */
    @Override
    public int updateBusiReimTrafficFee(BusiReimTrafficFee busiReimTrafficFee)
    {
        busiReimTrafficFee.setUpdateTime(DateUtils.getNowDate());
        return busiReimTrafficFeeMapper.updateBusiReimTrafficFee(busiReimTrafficFee);
    }

    /**
     * 批量删除交通费用
     * 
     * @param trafficIds 需要删除的交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTrafficFeeByIds(Long[] trafficIds)
    {
        return busiReimTrafficFeeMapper.deleteBusiReimTrafficFeeByIds(trafficIds);
    }

    /**
     * 删除交通费用信息
     * 
     * @param trafficId 交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTrafficFeeById(Long trafficId)
    {
        return busiReimTrafficFeeMapper.deleteBusiReimTrafficFeeById(trafficId);
    }
}
