import request from '@/utils/request'

// 查询流程定义列表
export function listWorkflow(query) {
  return request({
    url: '/system/workflow/list',
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

// 新增流程定义
export function addWorkflow(data) {
  return request({
    url: '/system/workflow',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateWorkflow(data) {
  return request({
    url: '/system/workflow',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delWorkflow(workflowId) {
  return request({
    url: '/system/workflow/' + workflowId,
    method: 'delete'
  })
}

// 导出流程定义
export function exportWorkflow(query) {
  return request({
    url: '/system/workflow/export',
    method: 'get',
    params: query
  })
}

// 审批
export function approve(data) {
  return request({
    url: '/system/workflow/approve',
    method: 'post',
    data: data
  })
}


//根据流程ID查询流程审批记录
export function getBillTraces(billId,workflowId) {
  return request({
    url: '/system/workflow/getBillTraces/' + billId +'/'+ workflowId,
    method: 'get'
  })
}

