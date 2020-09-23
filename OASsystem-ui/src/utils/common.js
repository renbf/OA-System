import moment from 'moment'
import request from "@/utils/request";

/**
 * 通用js方法封装处理
 * Copyright (c) 2019 xhkj
 */

const baseURL = process.env.VUE_APP_BASE_API

// 日期格式化
export function parseTime(time, pattern) {
	if (arguments.length === 0 || !time) {
		return null
	}
	const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
	let date
	if (typeof time === 'object') {
		date = time
	} else {
		if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
			time = parseInt(time)
		} else if (typeof time === 'string') {
			time = time.replace(new RegExp(/-/gm), '/');
		}
		if ((typeof time === 'number') && (time.toString().length === 10)) {
			time = time * 1000
		}
		date = new Date(time)
	}
	const formatObj = {
		y: date.getFullYear(),
		m: date.getMonth() + 1,
		d: date.getDate(),
		h: date.getHours(),
		i: date.getMinutes(),
		s: date.getSeconds(),
		a: date.getDay()
	}
	const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
		let value = formatObj[key]
		// Note: getDay() returns 0 on Sunday
		if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
		if (result.length > 0 && value < 10) {
			value = '0' + value
		}
		return value || 0
	})
	return time_str
}

// 表单重置
export function resetForm(refName) {
	if (this.$refs[refName]) {
		this.$refs[refName].resetFields();
	}
}

// 添加日期范围
export function addDateRange(params, dateRange) {
	var search = params;
	search.beginTime = "";
	search.endTime = "";
	if (null != dateRange && '' != dateRange) {
		search.beginTime = dateRange[0];
		search.endTime = dateRange[1];
	}
	return search;
}

//所有字典
var dictAllData;
request({
  url: '/system/dict/data/getAllDict',
  method: 'get',
}).then(response=>{
  if(response.code == 200){
    dictAllData = response.data;
    console.log(dictAllData,111)
  }
})

export function selectDictByType(dataType) {
   return dictAllData[dataType];
}

// 根据类型 回显数据字典
export function selectDictLabelByType(dataType, value) {
  var actions = [];
  var datas = dictAllData[dataType];

  Object.keys(datas).map((key) => {
    if (datas[key].dictValue == ('' + value)) {
      actions.push(datas[key].dictLabel);
      return false;
    }
  })
  return actions.join('');
}

// 回显数据字典
export function selectDictLabel(datas, value) {
	var actions = [];
	Object.keys(datas).map((key) => {
		if (datas[key].dictValue == ('' + value)) {
			actions.push(datas[key].dictLabel);
			return false;
		}
	})
	return actions.join('');
}

// 通用下载方法
export function download(fileName) {
	window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
}

// 字符串格式化(%s )
export function sprintf(str) {
	var args = arguments, flag = true, i = 1;
	str = str.replace(/%s/g, function () {
		var arg = args[i++];
		if (typeof arg === 'undefined') {
			flag = false;
			return '';
		}
		return arg;
	});
	return flag ? str : '';
}

// 转换字符串，undefined,null等转化为""
export function praseStrEmpty(str) {
    if (!str || str == "undefined" || str == "null") {
        return "";
    }
    return str;
}

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 * @param {*} rootId 根Id 默认 0
 */
export function handleTree(data, id, parentId, children, rootId) {
	id = id || 'id'
	parentId = parentId || 'parentId'
	children = children || 'children'
	rootId = rootId || 0
	//对源数据深度克隆
	const cloneData = JSON.parse(JSON.stringify(data))
	//循环所有项
	const treeData =  cloneData.filter(father => {
	  let branchArr = cloneData.filter(child => {
		//返回每一项的子级数组
		return father[id] === child[parentId]
	  });
	  branchArr.length > 0 ? father.children = branchArr : '';
	  //返回第一层
	  return father[parentId] === rootId;
	});
	return treeData != '' ? treeData : data;
  }


//判断是否为空
export function isNotEmpty(data) {
  if (data != undefined && data != null && data != "") {
    return true;
  } else {
    return false;
  }
}

//计算两个时间相差小时数   08:30 12:00
export function toHourDifference(startTime,endTime){
  var amhours ;
  if(startTime && endTime){
    var start = startTime.split(":");
    var startAll = parseInt(start[0]*60)+parseInt(start[1]);
    var end = endTime.split(":");
    var endAll=parseInt(end[0]*60)+parseInt(end[1]);
    amhours = (endAll-startAll) / 60;
  }else{
    amhours = 0;
  }
  return amhours;
}


//计算请假时长
// export function CalculationArray(dateRange,workHour) {
//   var HandleAddRange = [];
//
//   for (var i = 0; i < dateRange.length; i++) {
//     let two = i;
//     if (isNotEmpty(dateRange[i][0])) {
//       let dai = dateRange[i][0];
//       dai = [dai];
//       HandleAddRange.push(dai);
//     } else {
//       this.msgError("新增的请选择时间，否则请删除！");
//       return;
//     }
//   }
//
//   return Calculation(HandleAddRange,workHour);
// }
//
//
// //循环开始计算时间
// function Calculation(HandleAddRange,workHour) {
//   if (HandleAddRange.length >= 1) {
//     for (var a = 0; a < HandleAddRange.length; a++) {
//       for (var b = 0; b < HandleAddRange[a].length; b++) {
//         for (var c = 0; c < HandleAddRange[b].length; c++) {
//           if (isNotEmpty(HandleAddRange[a][b][0])) {
//             let begin = HandleAddRange[a][b][0];
//             let end = HandleAddRange[a][b][1];
//             return gethours(HandleAddRange[a][b][0], HandleAddRange[a][b][1],workHour);
//           }
//         }
//       }
//     }
//   } else {
//     return gethours(HandleAddRange[0][0][0], HandleAddRange[0][0][1],workHour);
//   }
// }


//计算请假时长
export function calculateHours(dateRange,workHour,index) {
  return gethours(dateRange[index][0][0], dateRange[index][0][1],workHour);
}

//公共调用计算
function gethours(begin, end,workHour) {
  var totalmillmunitute = moment(end).diff(begin), //总的毫秒数
    day = 60 * 24 * 60 * 1000,
    days = Math.floor(totalmillmunitute / day), //天数
    Be_left_overminute = totalmillmunitute % day,
    result,minute,s_m_n,
    minute_to_hours;
    // 1小时的毫秒数
    s_m_n = 60 * 1000 * 60;
    // 剩余分钟转换成小时
    minute_to_hours = Be_left_overminute / s_m_n;
    parseInt(minute_to_hours);
    parseFloat(workHour);
    if (minute_to_hours > workHour) {
      days += 1;
      result = Number(days * workHour);
    } else {
      result = Number(days * workHour + minute_to_hours);
    }
    return result;
}


//加
export function floatAdd(arg1,arg2){
  var r1,r2,m;
  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
  m=Math.pow(10,Math.max(r1,r2));
  return (arg1*m+arg2*m)/m;
}

//减
export function floatSub(arg1,arg2){
  var r1,r2,m,n;
  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
  m=Math.pow(10,Math.max(r1,r2));
  //动态控制精度长度
  n=(r1>=r2)?r1:r2;
  return ((arg1*m-arg2*m)/m).toFixed(n);
}

//乘
export function floatMul(arg1,arg2) {
  var m=0,s1=arg1.toString(),s2=arg2.toString();
  try{m+=s1.split(".")[1].length}catch(e){}
  try{m+=s2.split(".")[1].length}catch(e){}
  return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}


//除
export function floatDiv(arg1,arg2){
  var t1=0,t2=0,r1,r2;
  try{t1=arg1.toString().split(".")[1].length}catch(e){}
  try{t2=arg2.toString().split(".")[1].length}catch(e){}

  r1=Number(arg1.toString().replace(".",""));

  r2=Number(arg2.toString().replace(".",""));
  return (r1/r2)*Math.pow(10,t2-t1);
}
//把数组里某一字段拼接起来
export function arrayToStr(arr,attribute){
  let str = "";
  if (Array.isArray(arr)) {
    if(arr.length > 0){
      for (var i = 0; i < arr.length; i++) {
        let value = arr[i][attribute];
        str += value + ",";
      }
      if (str.length > 0) {
        str = str.substring(0, str.length - 1);
      }
    }
  }
  return str;
}


