import request from '@/utils/request'

// 查询其他费用列表
export function listFee(query) {
  return request({
    url: '/business/fee/list',
    method: 'get',
    params: query
  })
}

// 查询其他费用详细
export function getFee(otherId) {
  return request({
    url: '/business/fee/' + otherId,
    method: 'get'
  })
}

// 新增其他费用
export function addFee(data) {
  return request({
    url: '/business/fee',
    method: 'post',
    data: data
  })
}

// 修改其他费用
export function updateFee(data) {
  return request({
    url: '/business/fee',
    method: 'put',
    data: data
  })
}

// 删除其他费用
export function delFee(otherId) {
  return request({
    url: '/business/fee/' + otherId,
    method: 'delete'
  })
}

// 导出其他费用
export function exportFee(query) {
  return request({
    url: '/business/fee/export',
    method: 'get',
    params: query
  })
}