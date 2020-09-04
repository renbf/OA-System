package com.xhkj.common.constant;

/**
 * 字典常量
 * 完全按照数据库sys_dict表顺序创建，不能有其他字典
 */
public interface DictConst
{

	/** 【用户性别】 */
	public static final String SYS_USER_SEX = "sys_user_sex";
	/** 男 */
	public static final String SYS_USER_SEX_1 = "0";
	/** 女 */
	public static final String SYS_USER_SEX_2 = "1";
	/** 未知 */
	public static final String SYS_USER_SEX_3 = "2";


	/** 【菜单状态】 */
	public static final String SYS_SHOW_HIDE = "sys_show_hide";
	/** 显示 */
	public static final String SYS_SHOW_HIDE_1 = "0";
	/** 隐藏 */
	public static final String SYS_SHOW_HIDE_2 = "1";


	/** 【系统开关】 */
	public static final String SYS_NORMAL_DISABLE = "sys_normal_disable";
	/** 正常 */
	public static final String SYS_NORMAL_DISABLE_1 = "0";
	/** 停用 */
	public static final String SYS_NORMAL_DISABLE_2 = "1";


	/** 【任务状态】 */
	public static final String SYS_JOB_STATUS = "sys_job_status";
	/** 正常 */
	public static final String SYS_JOB_STATUS_1 = "0";
	/** 暂停 */
	public static final String SYS_JOB_STATUS_2 = "1";


	/** 【任务分组】 */
	public static final String SYS_JOB_GROUP = "sys_job_group";
	/** 默认 */
	public static final String SYS_JOB_GROUP_1 = "DEFAULT";
	/** 系统 */
	public static final String SYS_JOB_GROUP_2 = "SYSTEM";


	/** 【系统是否】 */
	public static final String SYS_YES_NO = "sys_yes_no";
	/** 是 */
	public static final Boolean SYS_YES_NO_1 = true;
	/** 否 */
	public static final Boolean SYS_YES_NO_2 = false;


	/** 【通知类型】 */
	public static final String SYS_NOTICE_TYPE = "sys_notice_type";
	/** 通知 */
	public static final String SYS_NOTICE_TYPE_1 = "1";
	/** 公告 */
	public static final String SYS_NOTICE_TYPE_2 = "2";


	/** 【通知状态】 */
	public static final String SYS_NOTICE_STATUS = "sys_notice_status";
	/** 正常 */
	public static final String SYS_NOTICE_STATUS_1 = "0";
	/** 关闭 */
	public static final String SYS_NOTICE_STATUS_2 = "1";


	/** 【操作类型】 */
	public static final String SYS_OPER_TYPE = "sys_oper_type";
	/** 新增 */
	public static final String SYS_OPER_TYPE_1 = "1";
	/** 修改 */
	public static final String SYS_OPER_TYPE_2 = "2";
	/** 删除 */
	public static final String SYS_OPER_TYPE_3 = "3";
	/** 授权 */
	public static final String SYS_OPER_TYPE_4 = "4";
	/** 导出 */
	public static final String SYS_OPER_TYPE_5 = "5";
	/** 导入 */
	public static final String SYS_OPER_TYPE_6 = "6";
	/** 强退 */
	public static final String SYS_OPER_TYPE_7 = "7";
	/** 生成代码 */
	public static final String SYS_OPER_TYPE_8 = "8";
	/** 清空数据 */
	public static final String SYS_OPER_TYPE_9 = "9";


	/** 【系统状态】 */
	public static final String SYS_COMMON_STATUS = "sys_common_status";
	/** 成功 */
	public static final String SYS_COMMON_STATUS_1 = "0";
	/** 失败 */
	public static final String SYS_COMMON_STATUS_2 = "1";


	/** 【请假类型】 */
	public static final String BUS_LEAVE_TYPE = "bus_leave_type";
	/** 加班调休 */
	public static final String BUS_LEAVE_TYPE_1 = "1";
	/** 请假 */
	public static final String BUS_LEAVE_TYPE_2 = "2";
	/** 事假 */
	public static final String BUS_LEAVE_TYPE_3 = "3";
	/** 年休假 */
	public static final String BUS_LEAVE_TYPE_4 = "4";
	/** 产假 */
	public static final String BUS_LEAVE_TYPE_5 = "5";


	/** 【审批状态】 */
	public static final String SYS_CHECK_STATUS = "sys_check_status";
	/** 已报送 */
	public static final String SYS_CHECK_STATUS_1 = "1";
	/** 未报送 */
	public static final String SYS_CHECK_STATUS_2 = "2";
	/** 审批中 */
	public static final String SYS_CHECK_STATUS_3 = "3";
	/** 通过 */
	public static final String SYS_CHECK_STATUS_4 = "4";
	/** 拒绝 */
	public static final String SYS_CHECK_STATUS_5 = "5";


}
