
<template>

<!--  <el-tabs v-model="editableTabsValue" type="border-card" closable @tab-remove="removeTab">-->
<!--    <el-tab-pane-->
<!--      v-for="(item, index) in editableTabs"-->
<!--      :key="item.name"-->
<!--      :label="item.title"-->
<!--      :name="item.name"-->
<!--    >-->
<!--      <iframe :src="item.content" style="width: 100%;height: 450px" frameborder="0"></iframe>-->
<!--    </el-tab-pane>-->
<!--  </el-tabs>-->
<!--  <el-tabs v-model="activeName">-->
<!--    <el-tab-pane label="日常工作" name="first" :key="'first'">-->
<!--      <child1></child1>-->
<!--    </el-tab-pane>-->

<!--    <el-tab-pane label="加班申请" name="second" :key="'second'">-->
<!--      <child2></child2>-->
<!--    </el-tab-pane>-->

<!--  </el-tabs>-->
  <div>
    <el-row class="home_nav" type="flex" justify="flex-start" align="middle">
      <el-col :span="12">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="workdaily">日常工作</el-menu-item>
          <el-menu-item index="addwork">加班申请</el-menu-item>
          <el-menu-item index="expenses">报销申请</el-menu-item>
          <el-menu-item index="delay">调休申请</el-menu-item>
          <el-menu-item index="audition">面试申请</el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
    <component :is="activeIndex"></component>
  </div>

</template>

<script>
  import workdaily from './tabwork/workdaily'
  import addwork from './tabwork/otherwork'
  import expenses from './tabwork/expenses'


  export default {
    // name: 'tabZujian',
    components:{
      workdaily,
      addwork,
      delay,
      expenses
    },
    data() {
      return {
        //默认第一个选项卡
        activeIndex:'workdaily'
      }
    },
    methods:{
      handleSelect(index) {
        this.activeIndex = index
      }
    }
  }
  // export default {
  //   data() {
  //     return {
  //       // activeName: 'first',
  //       // tabList:[
  //       //   {
  //       //     title:'日常工作',
  //       //     name:'first',
  //       //     id:1,
  //       //     content:'./workdaily'
  //       //   }
  //       //   // {title:'报销申请',name:'second',id:2,content:'./addMywork'},
  //       //   // {title:'调休申请',name:'third',id:3,content:'./workdaily'}
  //       //   ],
  //       defaultProps: {
  //         children: 'children',
  //         label: 'label'
  //       },
  //       editableTabsValue: '1',
  //       editableTabs: [
  //         {
  //         title: '首页',
  //         name: '1',
  //         content: '/mywork/workdaily'
  //       }
  //       ],
  //       tabIndex: 1
  //     };
  //   },
  //   created(){
  //     if(this.active==true){
  //       this.activeName='1'
  //     }
  //   },
  //   methods: {
  //     //标签页处理
  //     handleNodeClick(data) {
  //       if (!data.children) {
  //         //取得当前已打开的tab页
  //         let tabs = this.editableTabs;
  //         let flag = true;//表示可以新打开
  //         tabs.forEach((tab, index) => {
  //           if (tab.title === data.label) {
  //             //如果打开则选中
  //             this.editableTabsValue = tab.name;
  //             flag = false;
  //             return;
  //           }
  //         });
  //         if (flag) {//如果没有打开则添加
  //           let newTabName = ++this.tabIndex + '';
  //           this.editableTabs.push({
  //             title: data.label,
  //             name: newTabName,
  //             content: data.url
  //           });
  //           this.editableTabsValue = newTabName;
  //         }
  //       }
  //     },
  //     //标签页移除
  //     removeTab(targetName) {
  //       let tabs = this.editableTabs;
  //       let activeName = this.editableTabsValue;
  //       if (activeName === targetName) {
  //         tabs.forEach((tab, index) => {
  //           if (tab.name === targetName) {
  //             let nextTab = tabs[index + 1] || tabs[index - 1];
  //             if (nextTab) {
  //               activeName = nextTab.name;
  //             }
  //           }
  //         });
  //       }
  //
  //       this.editableTabsValue = activeName;
  //       this.editableTabs = tabs.filter(tab => tab.name !== targetName);
  //     }
  //   }
  // };

</script>

<style scoped>
  .el-form-item{
    width: 542px;
  }
</style>
