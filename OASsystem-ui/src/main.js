import Vue from "vue";
import "./assets/styles/element-variables.scss";
import "normalize.css/normalize.css"; // a modern alternative to CSS resets
import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/common.scss"; // common css
import "./assets/icons"; // icon
import "./permission"; // permission control


import Cookies from "js-cookie";
import Element from "element-ui";
import moment from 'moment'

import App from "./App";
import store from "./store";
import router from "./router";
import permission from "./directive/permission";
import Pagination from "@/components/Pagination";
import exportImport from '@/components/customize/exportImport'

import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { getDeptList } from "@/api/system/dept";
import { getWorkflowList} from "@/api/system/examine/workflowgroup";
import { approve,getBillTraces} from "@/api/system/workflow";
import {parseTime, resetForm, addDateRange, selectDictByType,selectDictLabelByType, selectDictLabel, handleTree,isNotEmpty} from "@/utils/common";

import common from "@/utils/common";
Vue.prototype.common = common;

import GLOBAL from "@/utils/global";
Vue.prototype.GLOBAL = GLOBAL;

Vue.prototype.storage = sessionStorage;

// 全局方法挂载
Vue.prototype.getBillTraces = getBillTraces;
Vue.prototype.approve = approve;
Vue.prototype.getWorkflowList = getWorkflowList;
Vue.prototype.getDeptList = getDeptList;
Vue.prototype.getDicts = getDicts;
Vue.prototype.moment = moment;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictByType = selectDictByType;
Vue.prototype.selectDictLabelByType = selectDictLabelByType;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.handleTree = handleTree;
Vue.prototype.isNotEmpty = isNotEmpty;
Vue.prototype.storage = window.sessionStorage;

Vue.config.devtools = true;
Vue.prototype.msgSuccess = function(msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
};

Vue.prototype.msgError = function(msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
};

Vue.prototype.msgInfo = function(msg) {
  this.$message.info(msg);
};

Vue.prototype.msgWarning = function(msg) {
  this.$message({showClose: true,message: msg, type: 'warning'});
};


// 全局组件挂载
Vue.component("Pagination", Pagination);
Vue.component("exportImport", exportImport);

Vue.use(permission);


/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium" // set element-ui default size
});

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: h => h(App)
});

// 进度条
import svg from 'svg-progress-bar'
Vue.use(svg,{componentName: 'svg-bar'})
