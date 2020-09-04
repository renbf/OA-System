import request from '@/utils/request'

// 查询流程审核步骤列表
export function listNode(query) {
  return request({
    url: '/system/node/list',
    method: 'get',
    params: query
  })
}

// 查询流程审核步骤详细
export function getNode(nodeId) {
  return request({
    url: '/system/node/' + nodeId,
    method: 'get'
  })
}

// 新增流程审核步骤
export function addNode(data) {
  return request({
    url: '/system/node',
    method: 'post',
    data: data
  })
}

// 修改流程审核步骤
export function updateNode(data) {
  return request({
    url: '/system/node',
    method: 'put',
    data: data
  })
}

// 删除流程审核步骤
export function delNode(nodeId) {
  return request({
    url: '/system/node/' + nodeId,
    method: 'delete'
  })
}

// 导出流程审核步骤
export function exportNode(query) {
  return request({
    url: '/system/node/export',
    method: 'get',
    params: query
  })
}