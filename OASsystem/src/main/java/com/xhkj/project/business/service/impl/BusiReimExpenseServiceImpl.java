package com.xhkj.project.business.service.impl;

import java.util.List;
import com.xhkj.common.utils.DateUtils;
import com.xhkj.common.utils.SecurityUtils;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.project.business.domain.BusiReimOtherFee;
import com.xhkj.project.business.domain.BusiReimTrafficFee;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.mapper.AttachmentMapper;
import com.xhkj.project.system.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhkj.project.business.mapper.BusiReimExpenseMapper;
import com.xhkj.project.business.domain.BusiReimExpense;
import com.xhkj.project.business.service.IBusiReimExpenseService;

/**
 * 费用Service业务层处理
 * 
 * @author xhkj
 * @date 2020-11-02
 */
@Service
public class BusiReimExpenseServiceImpl implements IBusiReimExpenseService 
{
    @Autowired
    private BusiReimExpenseMapper busiReimExpenseMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IAttachmentService iAttachmentService;


    /**
     * 查询费用
     * 
     * @param reimExpenseId 费用ID
     * @return 费用
     */
    @Override
    public BusiReimExpense selectBusiReimExpenseById(Long reimExpenseId)
    {
        BusiReimExpense busiReimExpense = busiReimExpenseMapper.selectBusiReimExpenseById(reimExpenseId);
        String fileIds = busiReimExpense.getFileIds();
        if(StringUtils.isNoneBlank(fileIds)){
            String[] ids = fileIds.split(",");
            List<Attachment> attachments = attachmentMapper.selectFileList(ids);
            busiReimExpense.setFileList(attachments);
        }

        return busiReimExpense;

    }

    /**
     * 查询费用列表
     * 
     * @param busiReimExpense 费用
     * @return 费用
     */
    @Override
    public List<BusiReimExpense> selectBusiReimExpenseList(BusiReimExpense busiReimExpense)
    {
        List<BusiReimExpense> busiReimExpenses = busiReimExpenseMapper.selectBusiReimExpenseList(busiReimExpense);

        busiReimExpenses.stream().forEach(e->{
            String fileIds = e.getFileIds();
            if(StringUtils.isNoneBlank(fileIds)){
                String[] ids = fileIds.split(",");
                List<Attachment> attachments = attachmentMapper.selectFileList(ids);
                e.setFileList(attachments);
            }
        });

        return busiReimExpenses;

    }

    /**
     * 新增费用
     * 
     * @param busiReimExpense 费用
     * @return 结果
     */
    @Override
    public int insertBusiReimExpense(BusiReimExpense busiReimExpense)
    {

        int i = 0;
        Long reimExpenseId = busiReimExpense.getReimExpenseId();
        if(reimExpenseId!=null){
            busiReimExpense.setUpdateBy(SecurityUtils.getUserId());
            busiReimExpense.setUpdateTime(DateUtils.getNowDate());
            i = busiReimExpenseMapper.updateBusiReimExpense(busiReimExpense);
        }else{
            busiReimExpense.setCreateBy(SecurityUtils.getUserId());
            busiReimExpense.setCreateTime(DateUtils.getNowDate());
            i = busiReimExpenseMapper.insertBusiReimExpense(busiReimExpense);
        }

        return i;

    }

    /**
     * 修改费用
     * 
     * @param busiReimExpense 费用
     * @return 结果
     */
    @Override
    public int updateBusiReimExpense(BusiReimExpense busiReimExpense)
    {
        busiReimExpense.setUpdateTime(DateUtils.getNowDate());
        return busiReimExpenseMapper.updateBusiReimExpense(busiReimExpense);
    }

    /**
     * 批量删除费用
     * 
     * @param reimExpenseIds 需要删除的费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimExpenseByIds(Long[] reimExpenseIds)
    {
        return busiReimExpenseMapper.deleteBusiReimExpenseByIds(reimExpenseIds);
    }

    /**
     * 删除费用信息
     * 
     * @param reimExpenseId 费用ID
     * @return 结果
     */
    @Override
    public int deleteBusiReimExpenseById(Long reimExpenseId)
    {
        int i = 0;

        BusiReimExpense busiReimExpense = this.selectBusiReimExpenseById(reimExpenseId);
        List<Attachment> fileList = busiReimExpense.getFileList();

        boolean isDelete = iAttachmentService.deleteFileList(fileList);

        if(isDelete){
            i = busiReimExpenseMapper.deleteBusiReimExpenseById(reimExpenseId);
        }

        return i;


    }
}
