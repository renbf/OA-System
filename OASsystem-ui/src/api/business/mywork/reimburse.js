import request from '@/utils/request'

// 查询报销信息列表
export function listReimburse(query) {
  return request({
    url: '/business/reimburse/list',
    method: 'get',
    params: query
  })
}

// 查询报销信息
export function getReimburse(reimburseId) {
  return request({
    url: '/business/reimburse/' + reimburseId,
    method: 'get'
  })
}

// 查询报销信息详细
export function getRemburseDetail(reimburseId,reimburseType) {
  return request({
    url: '/business/reimburse/getRemburseDetail/' + reimburseId + '/' + reimburseType,
    method: 'get'
  })
}


// 新增报销信息
export function addReimburse(data) {
  return request({
    url: '/business/reimburse',
    method: 'post',
    data: data
  })
}

// 修改报销信息
export function updateReimburse(data) {
  return request({
    url: '/business/reimburse',
    method: 'put',
    data: data
  })
}

// 删除报销信息
export function delReimburse(reimburseId) {
  return request({
    url: '/business/reimburse/' + reimburseId,
    method: 'delete'
  })
}

// 导出报销信息
export function exportReimburse(query) {
  return request({
    url: '/business/reimburse/export',
    method: 'get',
    params: query
  })
}


//请假上报
export function billSumbit(reimburseIds) {
  return request({
    url: "/business/reimburse/billSumbit/" + reimburseIds,
    method: "post",
  });
}

