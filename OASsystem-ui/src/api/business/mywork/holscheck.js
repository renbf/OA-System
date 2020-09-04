import request from "@/utils/request";

// 获取登录人的调休及年假剩余时长
export function getHolsCheckInfo(query) {
  return request({
    url: "/business/holsCheck/getHolsCheckInfo",
    method: "get",
  });
}
