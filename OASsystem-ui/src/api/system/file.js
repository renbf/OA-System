import request from "@/utils/request";
import axios from 'axios'
import { getToken } from '@/utils/auth'

// 查询文件列表
export function file_List(query) {
  return request({
    url: "/system/attachment/filesLists",
    method: "get",
    params: query
  });
}
// 预览与下载接口
export function filePreview(fileId) {
  const baseUrl = process.env.VUE_APP_BASE_API
  var url = baseUrl + "/system/attachment/onlineBrowse"
  return axios({
    method: 'get',
    async: false,
    url: url,
    params: {id:fileId},
    responseType: 'blob',
    headers: { 'Authorization': 'Bearer ' + getToken() }
  });
}
// 删除文件
export function delFile(roleId) {
  return request({
    url: '/system/attachment/deleteFile',
    method: 'post',
    params: {id:roleId}
  })
}
//删除磁盘数据
export function delDiskFile(path) {
  return request({
    url:'/system/attachment/deleteDiskFile',
    method:'post',
    params:{path:path}
  })
}


