//流程审批列表
const workflowGroupList = [{
  workflowGroupName: "加班审批类型",
  remark: "用于各岗位加班审批流程设置",
  count: 3
},{
  workflowGroupName: "请假审批类型",
  remark: "用于各岗位请假审批流程设置",
  count: 4
},{
  workflowGroupName: "面试审批类型",
  remark: "用于各岗位面试审批流程设置",
  count: 5
}];
//流程列表
const workflowList = [{
  workflowName: "经理加班申请",
  workflowDesc: "用于各岗位加班审批流程设置",
  workflowNodeNum: 3
},{
  workflowName: "员工加班申请",
  workflowDesc: "用于各岗位请假审批流程设置",
  workflowNodeNum: 4
},{
  workflowName: "技术部员工加班申请",
  workflowDesc: "用于各岗位面试审批流程设置",
  workflowNodeNum: 5
}];
export default
{
  workflowGroupList,
  workflowList
}
