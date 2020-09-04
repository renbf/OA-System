// 设置每日工作时间
export function addtime(data) {
  return request({
    url: '/system/addtime',
    method: 'post',
    data: data
  })
}
// 设置每日工作时长
export function addDuringtime(data) {
  return request({
    url: '/system/addDuringtime',
    method: 'post',
    data: data
  })
}
// 设置年休假天数

export function addyeartime(data) {
  return request({
    url: '/system/addyeartime',
    method: 'post',
    data: data
  })
}
// 请假规定设置

export function addleave(data){
  return request({
    url: '/system/addleave',
    method: 'post',
    data: data
  })
}

// 加班规定设置
export function addovertime(data){
  return request({
    url: '/system/addovertime',
    method: 'post',
    data: data
  })
}
