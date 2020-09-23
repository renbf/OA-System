import request from "@/utils/request";

// 查询加班列表
export function listExtraWork(query) {
  return request({
    url: "/business/extraWork/list",
    method: "get",
    params: query
  });
}

// 查询加班详细
export function getExtraWork(extraWorkId) {
  return request({
    url: "/business/extraWork/" + extraWorkId,
    method: "get"
  });
}

// 新增加班或保存yes
export function addExtraWork(data) {
  return request({
    url: "/business/extraWork",
    method: "post",
    data: JSON.stringify(data)
  });
}

// 修改加班
export function updateExtraWork(data) {
  return request({
    url: "/business/extraWork",
    method: "put",
    data: JSON.stringify(data)
  });
}

// 删除加班
export function delExtraWorks(extraWorkIds) {
  return request({
    url: "/business/extraWork/" + extraWorkIds,
    method: "delete"
  });
}

// 导出加班
export function exportExtraWork(query) {
  return request({
    url: "/business/extraWork/export",
    method: "get",
    params: query
  });
}

//加班上报
export function extraWorkSumbit(extraWorkIds) {
  return request({
    url: "/business/extraWork/extraWorkSumbit/" + extraWorkIds,
    method: "post",
  });
}

