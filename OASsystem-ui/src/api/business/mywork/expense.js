import request from '@/utils/request'

// 查询费用列表
export function listExpense(query) {
  return request({
    url: '/business/expense/list',
    method: 'get',
    params: query
  })
}

// 查询费用详细
export function getExpense(reimExpenseId) {
  return request({
    url: '/business/expense/' + reimExpenseId,
    method: 'get'
  })
}

// 新增费用
export function addExpense(data) {
  return request({
    url: '/business/expense',
    method: 'post',
    data: data
  })
}

// 修改费用
export function updateExpense(data) {
  return request({
    url: '/business/expense',
    method: 'put',
    data: data
  })
}

// 删除费用
export function delExpense(reimExpenseId) {
  return request({
    url: '/business/expense/' + reimExpenseId,
    method: 'delete'
  })
}

// 导出费用
export function exportExpense(query) {
  return request({
    url: '/business/expense/export',
    method: 'get',
    params: query
  })
}