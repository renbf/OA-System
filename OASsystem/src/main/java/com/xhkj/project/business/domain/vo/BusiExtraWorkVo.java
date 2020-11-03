package com.xhkj.project.business.domain.vo;

import com.xhkj.framework.aspectj.lang.annotation.Excel;
import com.xhkj.framework.web.domain.BaseEntity;
import com.xhkj.project.business.domain.BusiExtraWork;

/**
 * 加班对象 busi_extra_work
 * 
 * @author xhkj
 * @date 2020-04-15
 */
public class BusiExtraWorkVo extends BusiExtraWork
{
    private static final long serialVersionUID = 1L;

    /*判断是保存还是提交*/
    private Boolean saveFlag;

    public Boolean getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
    }
}
