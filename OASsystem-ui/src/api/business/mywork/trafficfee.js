import request from '@/utils/request'

// 查询交通费用列表
export function listTrafficFee(query) {
  return request({
    url: '/business/trafficFee/list',
    method: 'get',
    params: query
  })
}

// 查询交通费用详细
export function getTrafficFee(trafficId) {
  return request({
    url: '/business/trafficFee/' + trafficId,
    method: 'get'
  })
}

// 新增交通费用
export function addTrafficFee(data) {
  return request({
    url: '/business/trafficFee',
    method: 'post',
    data: data
  })
}

// 修改交通费用
export function updateTrafficFee(data) {
  return request({
    url: '/business/trafficFee',
    method: 'put',
    data: data
  })
}

// 删除交通费用
export function delTrafficFee(trafficId) {
  return request({
    url: '/business/trafficFee/' + trafficId,
    method: 'delete'
  })
}

// 导出交通费用
export function exportTrafficFee(query) {
  return request({
    url: '/business/trafficFee/export',
    method: 'get',
    params: query
  })
}
