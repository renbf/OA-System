import request from '@/utils/request'

// 查询其他费用列表
export function listOtherFee(query) {
  return request({
    url: '/business/otherFee/list',
    method: 'get',
    params: query
  })
}

// 查询其他费用详细
export function getOtherFee(OtherId) {
  return request({
    url: '/business/otherFee/' + OtherId,
    method: 'get'
  })
}

// 新增其他费用
export function addOtherFee(data) {
  return request({
    url: '/business/otherFee',
    method: 'post',
    data: data
  })
}

// 修改其他费用
export function updateOtherFee(data) {
  return request({
    url: '/business/otherFee',
    method: 'put',
    data: data
  })
}

// 删除其他费用
export function delOtherFee(OtherId) {
  return request({
    url: '/business/otherFee/remove/' + OtherId,
    method: 'delete'
  })
}


// 导出其他费用
export function exportOtherFee(query) {
  return request({
    url: '/business/otherFee/export',
    method: 'get',
    params: query
  })
}
