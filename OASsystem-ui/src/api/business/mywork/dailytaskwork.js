import request from '@/utils/request'

// 查询日常任务工作列表
export function listDailyTaskWork(query) {
  return request({
    url: '/business/work/list',
    method: 'get',
    params: query
  })
}

// 查询日常任务工作详细
export function getDailyTaskWork(dailyTaskWorksId) {
  return request({
    url: '/business/work/' + dailyTaskWorksId,
    method: 'get'
  })
}

// 新增日常任务工作
export function addDailyTaskWork(data) {
  return request({
    url: '/business/work',
    method: 'post',
    data: data
  })
}

// 修改日常任务工作
export function updateDailyTaskWork(data) {
  return request({
    url: '/business/work',
    method: 'put',
    data: data
  })
}

// 删除日常任务工作
export function delDailyTaskWork(dailyTaskWorksId) {
  return request({
    url: '/business/work/' + dailyTaskWorksId,
    method: 'delete'
  })
}

// 导出日常任务工作
export function exportDailyTaskWork(query) {
  return request({
    url: '/business/work/export',
    method: 'get',
    params: query
  })
}

