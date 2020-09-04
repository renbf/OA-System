import request from '@/utils/request'

// 查询公司配置列表
export function listComConfig(query) {
  return request({
    url: '/system/comConfig/list',
    method: 'get',
    params: query
  })
}

// 查询公司配置详细
export function getComConfig(comConfigId) {
  return request({
    url: '/system/comConfig/' + comConfigId,
    method: 'get'
  })
}

// 新增公司配置
export function addComConfig(data) {
  return request({
    url: '/system/comConfig',
    method: 'post',
    data: data
  })
}

// 修改公司配置
export function updateComConfig(data) {
  return request({
    url: '/system/comConfig',
    method: 'put',
    data: data
  })
}

// 删除公司配置
export function delComConfig(comConfigId) {
  return request({
    url: '/system/comConfig/' + comConfigId,
    method: 'delete'
  })
}

// 导出公司配置
export function exportComConfig(query) {
  return request({
    url: '/system/comConfig/export',
    method: 'get',
    params: query
  })
}

// 批量新增
export function addComConfigList(data) {
  return request({
    url: '/system/comConfig/addComConfigList',
    method: 'post',
    data: data
  })
}

//根据key获取值
export function getComConfigInfo(data) {
  return request({
    url: '/system/comConfig/getComConfigInfo',
    method: 'post',
    data: data
  })
}
