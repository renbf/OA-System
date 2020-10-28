package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.mapper.AttachmentMapper;
import com.xhkj.project.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimTravelSubsidyMapper;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import com.xhkj.project.business.service.IBusiReimTravelSubsidyService;

/**
 * 交通费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimTravelSubsidyServiceImpl implements IBusiReimTravelSubsidyService 
{
    @Autowired
    private BusiReimTravelSubsidyMapper busiReimTravelSubsidyMapper;
    @Autowired
    private IAttachmentService iAttachmentService;
    @Autowired
    private AttachmentMapper attachmentMapper;



    /**
     * 查询交通费用
     * 
     * @param travelId 交通费用ID
     * @return 交通费用
     */
    @Override
    public BusiReimTravelSubsidy selectBusiReimTravelSubsidyById(Long travelId)
    {
        BusiReimTravelSubsidy busiReimTravelSubsidy = busiReimTravelSubsidyMapper.selectBusiReimTravelSubsidyById(travelId);
        String fileIds = busiReimTravelSubsidy.getFileIds();
        if(StringUtils.isNoneBlank(fileIds)){
            String[] ids = fileIds.split(",");
            List<Attachment> attachments = attachmentMapper.selectFileList(ids);
            busiReimTravelSubsidy.setFileList(attachments);
        }

        return busiReimTravelSubsidy;

    }

    /**
     * 查询交通费用列表
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 交通费用
     */
    @Override
    public List<BusiReimTravelSubsidy> selectBusiReimTravelSubsidyList(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {

        List<BusiReimTravelSubsidy> busiReimTravelSubsidies = busiReimTravelSubsidyMapper.selectBusiReimTravelSubsidyList(busiReimTravelSubsidy);

        busiReimTravelSubsidies.stream().forEach(e->{
            String fileIds = e.getFileIds();
            if(StringUtils.isNoneBlank(fileIds)){
                String[] ids = fileIds.split(",");
                List<Attachment> attachments = attachmentMapper.selectFileList(ids);
                e.setFileList(attachments);
            }
        });

        return busiReimTravelSubsidies;

    }

    /**
     * 新增交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    @Override
    public int insertBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        int i = 0;
        Long trafficId = busiReimTravelSubsidy.getTravelId();
        if(trafficId!=null){
            busiReimTravelSubsidy.setUpdateBy(SecurityUtils.getUserId());
            busiReimTravelSubsidy.setUpdateTime(DateUtils.getNowDate());
            i = busiReimTravelSubsidyMapper.updateBusiReimTravelSubsidy(busiReimTravelSubsidy);
        }else{
            busiReimTravelSubsidy.setCreateBy(SecurityUtils.getUserId());
            busiReimTravelSubsidy.setCreateTime(DateUtils.getNowDate());
            i = busiReimTravelSubsidyMapper.insertBusiReimTravelSubsidy(busiReimTravelSubsidy);
        }

        return i;

    }

    /**
     * 修改交通费用
     * 
     * @param busiReimTravelSubsidy 交通费用
     * @return 结果
     */
    @Override
    public int updateBusiReimTravelSubsidy(BusiReimTravelSubsidy busiReimTravelSubsidy)
    {
        busiReimTravelSubsidy.setUpdateTime(DateUtils.getNowDate());
        return busiReimTravelSubsidyMapper.updateBusiReimTravelSubsidy(busiReimTravelSubsidy);
    }

    /**
     * 批量删除交通费用
     * 
     * @param travelIds 需要删除的交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTravelSubsidyByIds(Long[] travelIds)
    {
        return busiReimTravelSubsidyMapper.deleteBusiReimTravelSubsidyByIds(travelIds);
    }

    /**
     * 删除交通费用信息
     * 
     * @param travelId 交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimTravelSubsidyById(Long travelId)
    {

        int i = 0;

        BusiReimTravelSubsidy busiReimTravelSubsidy = this.selectBusiReimTravelSubsidyById(travelId);
        List<Attachment> fileList = busiReimTravelSubsidy.getFileList();

        boolean isDelete = iAttachmentService.deleteFileList(fileList);

        if(isDelete){
            i = busiReimTravelSubsidyMapper.deleteBusiReimTravelSubsidyById(travelId);
        }

        return i;

    }
}
