import request from "@/utils/request";

// 查询请假倒休列表
export function listLeave(query) {
  return request({
    url: "/business/leave/list",
    method: "get",
    params: query
  });
}

// 查询请假倒休详细
export function getLeave(leaveId) {
  return request({
    url: "/business/leave/" + leaveId,
    method: "get"
  });
}

// 新增请假或倒休保存yes
export function addLeave(data) {
  return request({
    url: "/business/leave/addBusiAskLeave",
    method: "post",
    data: data
  });
}
// 新增请假或倒休提交（报送）
export function onSubmit(data) {
  return request({
    url: "/business/leave/addBusiAskLeave",
    method: "post",
    data: data
  });
}
export function isNotEmpty(data) {
  if (data != undefined && data != null && data != "") {
    return true;
  } else {
    return false;
  }
}
// 修改请假倒休
export function updateLeave(data) {
  return request({
    url: "/business/leave",
    method: "put",
    data: data
  });
}

// 删除请假倒休
export function delLeave(leaveId) {
  return request({
    url: "/business/leave/" + leaveId,
    method: "delete"
  });
}

// 导出请假倒休
export function exportLeave(query) {
  return request({
    url: "/business/leave/export",
    method: "get",
    params: query
  });
}
// 选择时间change
export function changeTime(query) {
  return request({
    url: "/business/leave/export",
    method: "get",
    params: query
  });
}
// 获取请假的注意事项
export function matters_attention(query) {
  return request({
    url: "/business/leave/export",
    method: "get",
    params: query
  });
}
// 假json
export function leaveList() {
  return {
    code: 200,
    total: 4,
    data: [
      {
        leaveId: 1,
        over_time: "2020-05-20",
        Length_of_leave: "0.5" + "天",
        bumen_department: [{ dictValue: 2, dictLabel: "软件部" }],
        Application_time: "2020-05-19",
        Statement_of_reasons: "理由陈述1",
        status: [{ dictValue: 2, dictLabel: "未报送" }],
        Current_approver: "当前审批人1",
        inProject: 0,
        annualLeave: 10,
        overtime: 20,
        selectvalue: 1
      },
      {
        leaveId: 2,
        over_time: "2020-05-22",
        Length_of_leave: "1.5" + "天",
        bumen_department: [{ dictValue: 1, dictLabel: "技术部" }],
        Application_time: "2020-05-19",
        Statement_of_reasons: "理由陈述2",
        status: [{ dictValue: 3, dictLabel: "审批中" }],
        Current_approver: "当前审批人2",
        inProject: 1,
        annualLeave: 10,
        overtime: 20,
        selectvalue: 1
      },
      {
        leaveId: 3,
        over_time: "2020-05-12 05:30:00",
        Length_of_leave: "0.5" + "天",
        bumen_department: [{ dictValue: 2, dictLabel: "软件部" }],
        Application_time: "2020-05-19",
        Statement_of_reasons: "理由陈述3",
        status: [{ dictValue: 4, dictLabel: "通过" }],
        Current_approver: "当前审批人3",
        inProject: 0,
        annualLeave: 10,
        overtime: 20,
        selectvalue: 1
      },
      {
        leaveId: 4,
        over_time: "2020-05-25",
        Length_of_leave: "0.5" + "天",
        bumen_department: [{ dictValue: 1, dictLabel: "技术部" }],
        Application_time: "2020-05-19",
        Statement_of_reasons: "理由陈述4",
        status: [{ dictValue: 5, dictLabel: "拒绝" }],
        Current_approver: "当前审批人4",
        inProject: 1,
        annualLeave: 10,
        overtime: 20,
        selectvalue: 1
      }
    ]
  };
}
