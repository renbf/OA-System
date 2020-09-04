import request from "@/utils/request";
// 添加工作日常
export function addWork(data) {
  return request({
    url: "http://www.baidu.com",
    method: "post",
    data: data
  });
}
// 查看工作日常
export function detailWork(data) {
  return request({
    url: "http://www.baidu.com",
    method: "post",
    data: data
  });
}
// 编辑工作日常
export function editWork(data) {
  return request({
    url: "http://www.baidu.com",
    method: "post",
    data: data
  });
}
// 工作日常列表
export function workList(data) {
  return request({
    url: "http://www.baidu.com",
    method: "post",
    data: data
  });
}
// 报送日常列表
export function reportWorkList(data) {
  return request({
    url: "http://www.baidu.com",
    method: "post",
    data: data
  });
}
// 导出日常工作
export function exportWork(query) {
  return request({
    url: "/system/role/export",
    method: "get",
    params: query
  });
}
// 部门初步获得数据渲染
export function department(query) {
  return request({
    url: "/system/role/export",
    method: "get",
    params: query
  });
}
// 表格上方的删除 单个or 批量
export function handleDelete(workId) {
  return request({
    url: "/system/post/" + workId,
    method: "delete"
  });
}
export function bumen(query) {
  return {
    code: 200,
    total: 4,
    data: [
      {
        workId: 1,
        workTitle: "标题1",
        workContent: "内容1",
        workTime: "2020-05-22 18:30:00",
        bumen: [{ dictValue: 1, dictLabel: "技术部" }],
        zhuangtai: [{ dictValue: 2, dictLabel: "未报送" }],
        workContent: "this is content1",
        bumenStatus: "1",
        workName: "1"
      },
      {
        workId: 2,
        workTitle: "标题2",
        workContent: "内容2",
        workTime: "2020-05-22 18:30:00",
        bumen: [{ dictValue: 2, dictLabel: "软件部" }],
        zhuangtai: [{ dictValue: 1, dictLabel: "已报送" }],
        workContent: "this is content1",
        bumenStatus: "2",
        workName: "2"
      },
      {
        workId: 3,
        workTitle: "标题3",
        workContent: "内容13",
        workTime: "2020-05-22 18:30:00",
        bumen: [{ dictValue: 1, dictLabel: "技术部" }],
        zhuangtai: [{ dictValue: 2, dictLabel: "未报送" }],
        workContent: "this is content3",
        bumenStatus: "1",
        workName: "3"
      },
      {
        workId: 4,
        workTitle: "标题4",
        workContent: "内容4",
        workTime: "2020-05-22 18:30:00",
        bumen: [{ dictValue: 2, dictLabel: "软件部" }],
        zhuangtai: [{ dictValue: 2, dictLabel: "未报送" }],
        workContent: "this is content3",
        bumenStatus: "2",
        workName: "4"
      }
    ]
  };
}
