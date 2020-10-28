import request from '@/utils/request'

// 查询交通费用列表
export function listSubsidy(query) {
  return request({
    url: '/business/subsidy/list',
    method: 'get',
    params: query
  })
}

// 查询交通费用详细
export function getSubsidy(travelId) {
  return request({
    url: '/business/subsidy/' + travelId,
    method: 'get'
  })
}

// 新增交通费用
export function addSubsidy(data) {
  return request({
    url: '/business/subsidy',
    method: 'post',
    data: data
  })
}

// 修改交通费用
export function updateSubsidy(data) {
  return request({
    url: '/business/subsidy',
    method: 'put',
    data: data
  })
}

// 删除交通费用
export function delSubsidy(travelId) {
  return request({
    url: '/business/subsidy/remove/' + travelId,
    method: 'delete'
  })
}

// 导出交通费用
export function exportSubsidy(query) {
  return request({
    url: '/business/subsidy/export',
    method: 'get',
    params: query
  })
}
