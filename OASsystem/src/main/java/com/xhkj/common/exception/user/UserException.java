package com.xhkj.common.exception.user;

import com.xhkj.common.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author xhkj
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
