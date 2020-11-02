package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.business.domain.BusiReimTravelSubsidy;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.mapper.AttachmentMapper;
import com.xhkj.project.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimOtherFeeMapper;
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.service.IBusiReimOtherFeeService;

/**
 * 交通费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-10-21
 */
@Service
public class BusiReimOtherFeeServiceImpl implements IBusiReimOtherFeeService 
{
    @Autowired
    private BusiReimOtherFeeMapper busiReimOtherFeeMapper;
    @Autowired
    private IAttachmentService iAttachmentService;
    @Autowired
    private AttachmentMapper attachmentMapper;




    /**
     * 查询交通费用
     * 
     * @param otherId 交通费用ID
     * @return 交通费用
     */
    @Override
    public BusiReimOtherFee selectBusiReimOtherFeeById(Long otherId)
    {
        BusiReimOtherFee busiReimOtherFee = busiReimOtherFeeMapper.selectBusiReimOtherFeeById(otherId);
        String fileIds = busiReimOtherFee.getFileIds();
        if(StringUtils.isNoneBlank(fileIds)){
            String[] ids = fileIds.split(",");
            List<Attachment> attachments = attachmentMapper.selectFileList(ids);
            busiReimOtherFee.setFileList(attachments);
        }

        return busiReimOtherFee;

    }

    /**
     * 查询交通费用列表
     * 
     * @param busiReimOtherFee 交通费用
     * @return 交通费用
     */
    @Override
    public List<BusiReimOtherFee> selectBusiReimOtherFeeList(BusiReimOtherFee busiReimOtherFee)
    {

        List<BusiReimOtherFee> busiReimOtherFees = busiReimOtherFeeMapper.selectBusiReimOtherFeeList(busiReimOtherFee);

        busiReimOtherFees.stream().forEach(e->{
            String fileIds = e.getFileIds();
            if(StringUtils.isNoneBlank(fileIds)){
                String[] ids = fileIds.split(",");
                List<Attachment> attachments = attachmentMapper.selectFileList(ids);
                e.setFileList(attachments);
            }
        });

        return busiReimOtherFees;


    }

    /**
     * 新增交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    @Override
    public int insertBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee)
    {

        int i = 0;
        Long otherId = busiReimOtherFee.getOtherId();
        if(otherId!=null){
            busiReimOtherFee.setUpdateBy(SecurityUtils.getUserId());
            busiReimOtherFee.setUpdateTime(DateUtils.getNowDate());
            i = busiReimOtherFeeMapper.updateBusiReimOtherFee(busiReimOtherFee);
        }else{
            busiReimOtherFee.setCreateBy(SecurityUtils.getUserId());
            busiReimOtherFee.setCreateTime(DateUtils.getNowDate());
            i = busiReimOtherFeeMapper.insertBusiReimOtherFee(busiReimOtherFee);
        }

        return i;

    }

    /**
     * 修改交通费用
     * 
     * @param busiReimOtherFee 交通费用
     * @return 结果
     */
    @Override
    public int updateBusiReimOtherFee(BusiReimOtherFee busiReimOtherFee)
    {
        busiReimOtherFee.setUpdateTime(DateUtils.getNowDate());
        return busiReimOtherFeeMapper.updateBusiReimOtherFee(busiReimOtherFee);
    }

    /**
     * 批量删除交通费用
     * 
     * @param otherIds 需要删除的交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimOtherFeeByIds(Long[] otherIds)
    {
        return busiReimOtherFeeMapper.deleteBusiReimOtherFeeByIds(otherIds);
    }

    /**
     * 删除交通费用信息
     * 
     * @param otherId 交通费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimOtherFeeById(Long otherId)
    {
        int i = 0;

        BusiReimOtherFee busiReimOtherFee = this.selectBusiReimOtherFeeById(otherId);
        List<Attachment> fileList = busiReimOtherFee.getFileList();

        boolean isDelete = iAttachmentService.deleteFileList(fileList);

        if(isDelete){
            i = busiReimOtherFeeMapper.deleteBusiReimOtherFeeById(otherId);
        }

        return i;

    }
}
