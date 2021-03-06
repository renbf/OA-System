import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect")
      }
    ]
  },
  {
    path: "/login",
    component: () => import("@/views/login"),
    hidden: true
  },

  {
    path: "/404",
    component: () => import("@/views/error/404"),
    hidden: true
  },
  {
    path: "/401",
    component: () => import("@/views/error/401"),
    hidden: true
  },
  {
    path: "",
    component: Layout,
    redirect: "index",
    children: [
      {
        path: "index",
        component: () => import("@/views/index"),
        name: "首页",
        meta: { title: "首页", icon: "dashboard", noCache: true, affix: true }
      }
    ]
  },
  {
    path: "/user",
    component: Layout,
    hidden: true,
    redirect: "noredirect",
    children: [
      {
        path: "profile",
        component: () => import("@/views/system/user/profile/index"),
        name: "Profile",
        meta: { title: "个人中心", icon: "user" }
      }
    ]
  },
  {
    path: "/dict",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "type/data/:dictId(\\d+)",
        component: () => import("@/views/system/dict/data"),
        name: "Data",
        meta: { title: "字典数据", icon: "" }
      }
    ]
  },
  {
    path: "/job",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "log",
        component: () => import("@/views/monitor/job/log"),
        name: "JobLog",
        meta: { title: "调度日志" }
      }
    ]
  },
  {
    path: "/gen",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "edit",
        component: () => import("@/views/tool/gen/editTable"),
        name: "GenEdit",
        meta: { title: "修改生成配置" }
      }
    ]
  },
  {
    path: "/workflow",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "node/:workflowId",
        component: () => import("@/views/system/workflow/node"),
        name: "workflow",
        meta: { title: "流程节点", icon: "" }
      }
    ]
  },

  // 我的工作>日常工作跳转路由
  {
    path: "/business",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "mywork",
        component: () => import("@/views/business/mywork/index"),
        // name: "mywork",
        meta: { title: "我的工作", icon: "" }
      },
    ]
  },
  {
    path: "/mywork",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "addMywork",
        component: () => import("@/views/business/mywork/addMywork"),
        // name: "mywork",
        meta: { title: "新建工作", icon: "" }
      },
    ]
  },
  {
    path:"/expenses",
    component: Layout,
    hidden: true,
    children: [
      {
        path:"index",
        component: () => import("@/views/business/expenses/index"),
        meta: { title: "我的报销", icon: "" }
      },
      {
        path:"travel",
        component: () => import("@/views/business/expenses/travel"),
        meta: { title: "差旅费报销", icon: "" }
      },
      {
        path:"expenses",
        component: () => import("@/views/business/expenses/expenses"),
        meta: { title: "费用报销", icon: "" }
      },
      ]
  },
  {
    path:"/myproject",
    component: Layout,
    hidden: true,
    children:[
      {
        path:"index",
        component: () => import("@/views/business/myproject/index"),
        meta: { title: "我的项目", icon: "" }
      },
      {
        path:"detail",
        component: () => import("@/views/business/myproject/detail"),
        meta: { title: "项目详情", icon: "" }
      },
      {
        path:"look",
        component: () => import("@/views/business/myproject/look"),
        meta: { title: "OA项目开发", icon: "" }
      },
      {
        path:"submission",
        component: () => import("@/views/business/myproject/submission"),
        meta: { title: "OA项目开发-项目组申请", icon: "" }
      },
      {
        path:"Finish",
        component: () => import("@/views/business/myproject/Finish"),
        meta: { title: "OA项目开发-项目组申请", icon: "" }
      },
      {
        path:"finishsubmission",
        component: () => import("@/views/business/myproject/finishsubmission"),
        meta: { title: "OA项目开发-项目组申请", icon: "" }
      },
    ]
  },

  {
    path:"/myreader",
    component: Layout,
    hidden: true,
    children:[
      {
        path:"index",
        component: () => import("@/views/business/myreader/index"),
        meta: { title: "我的待办", icon: "" }
      },
      {
        path:"little",
        component: () => import("@/views/business/myreader/little"),
        meta: { title: "加班审批", icon: "" }
      },

      {
        path:"leave",
        component: () => import("@/views/business/myreader/leave"),
        meta: { title: "请假审批", icon: "" }
      },

      {
        path:"face",
        component: () => import("@/views/business/myreader/face"),
        meta: { title: "面试申请", icon: "" }
      },
      {
        path:"business",
        component: () => import("@/views/business/myreader/business"),
        meta: { title: "差旅费报销审批", icon: "" }
      },
      {
        path:"money",
        component: () => import("@/views/business/myreader/money"),
        meta: { title: "费用审批", icon: "" }
      },
      {
        path:"entry",
        component: () => import("@/views/business/myreader/entry"),
        meta: { title: "入职申请", icon: "" }
      },
      {
        path:"down",
        component: () => import("@/views/business/myreader/down"),
        meta: { title: "离职申请", icon: "" }
      },
      {
        path:"xxx",
        component: () => import("@/views/business/myreader/xxx"),
        meta: { title: "xxx项目组申请", icon: "" }
      },
      {
        path:"projectTodo",
        component: () => import("@/views/business/myreader/projectTodo"),
        meta: { title: "项目待办", icon: "" }
      },
    ]
  },
  // 部门及角色
  {
    path:"/dictrole",
    component: Layout,
    hidden: true,
    children:[
      {
        path:"index",
        component: () => import("@/views/system/dictrole/index"),
        meta: { title: "部门及角色", icon: "" }
      },
      {
        path:"role",
        component: () => import("@/views/system/dictrole/role"),
        meta: { title: "角色", icon: "" }
      },
      {
        path:"addperson",
        component: () => import("@/views/system/dictrole/addperson"),
        meta: { title: "成员", icon: "" }
      },
    ]
  },

  // // 部门权限
  {
    path:"/deptlimit",
    component: Layout,
    hidden: true,
    children:[
      {
        path:"index",
        component: () => import("@/views/system/deptlimit/index"),
        meta: { title: "部门权限", icon: "" }
      }
    ]
  },

  // 审批流程设置
  {
    path:"/examine",
    component: Layout,
    hidden: true,
    children:[
      {
        path:"index",
        component: () => import("@/views/system/examine/index"),
        meta: { title: "部门权限", icon: "" }
      },
      {
        path:"examine",
        component: () => import("@/views/system/examine/examine"),
        meta: { title: "加班审批流程", icon: "" }
      },
    ]
  }
];

export default new Router({
  mode: "history", // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
});
