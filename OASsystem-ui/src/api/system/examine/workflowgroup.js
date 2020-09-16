import request from '@/utils/request'

// 查询流程类型列表
export function listGroup(query) {
  return request({
    url: '/system/workflow/listGroup',
    method: 'get',
    params: query
  })
}
// 查询流程类型列表
export function getWorkflowList(query) {
  return request({
    url: '/system/workflow/workflowList',
    method: 'get',
    params: query
  })
}

// 查询流程定义详细
export function getWorkflow(workflowId) {
  return request({
    url: '/system/workflow/' + workflowId,
    method: 'get'
  })
}

// 新增流程名称
export function addWorkflow(data) {
  return request({
    url: '/system/workflow/add',
    method: 'post',
    data: data
  })
}

// 新增流程步骤
export function addWorkflowStep(data) {
  return request({
    url: '/system/workflow/addStep',
    method: 'post',
    data: data
  })
}

// 查询流程类型列表
export function getWorkflowStepList(query) {
  return request({
    url: '/system/workflow/workflowStepList',
    method: 'get',
    params: query
  })
}

// 删除流程步骤
export function removeStep(id) {
  return request({
    url: '/system/workflow/removeStep/'+ id,
    method: 'delete'
  })
}

// 新增流程节点
export function addWorkflowNode(data) {
  return request({
    url: '/system/workflow/addWorkflowNode',
    method: 'post',
    data: data
  })
}

// 删除流程节点
export function deleteWorkflowNode(data) {
  return request({
    url: '/system/workflow/deleteWorkflowNode',
    method: 'delete',
    params:data
  })
}
// 修改流程节点
export function editWorkflowNode(data) {
  return request({
    url: '/system/workflow/editWorkflowNode',
    method: 'put',
    data: data
  })
}

// 查询流程类型列表
export function getSysWorkflowNodes(query) {
  return request({
    url: '/system/workflow/getSysWorkflowNodes',
    method: 'get',
    params: query
  })
}


