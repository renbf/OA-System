
<template>
  <div class="contain-little">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="详情页面">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="success">成功按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
      <el-button type="warning">警告按钮</el-button>

    </div>
    <!-- seach栏-->

     <div class="seach" >
       <span style="margin-left:20px;" >申请时间</span>
       <el-date-picker
         style="margin-left:20px;"
         v-model="value1"
         type="daterange"
         range-separator="至"
         start-placeholder="开始日期"
         end-placeholder="结束日期">
       </el-date-picker>
       <span style="margin-left:20px;">状态</span>
       <el-select v-model="value" placeholder="请选择" style="margin-left:20px;">
         <el-option
           v-for="item in options"
           :key="item.value"
           :label="item.label"
           :value="item.value">
         </el-option>
       </el-select>
       <span style="margin-left:20px;">部门</span>
       <el-select v-model="value" placeholder="请选择" style="margin-left:20px;">
         <el-option
           v-for="item in res"
           :key="item.value"
           :label="item.label"
           :value="item.value">
         </el-option>
       </el-select>



       <el-button type="primary" style="margin-left:20px;"> <span class="el-icon-search"></span>搜索</el-button>
       <el-button style="margin-left:20px;"> <span class="el-icon-refresh-right"></span>重置</el-button>

     </div>

    <el-table
      class="tables"
      :data="tableData"
      style="width: 100%">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
      prop="date"
      label="申请时间"
      width="180">
    </el-table-column>
      <el-table-column
        prop="name"
        label="申请人"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="理由陈述">
      </el-table-column>
      <el-table-column
        prop="app"
        label="项目"
        >
      </el-table-column>
      <el-table-column
        prop="time"
        label="加班时长"
        width="180">
      </el-table-column>
      <el-table-column
        prop="timetwo"
        label="加班时间">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="部门">
      </el-table-column>
      <el-table-column
        prop="zhuangtai"
        label="状态">
      </el-table-column><el-table-column
      prop="caozuo"
      label="操作">
    </el-table-column>

    </el-table>


  </div>
</template>


<script>
  export default {
    name: "page-reader",
    data(){
      return{
        //状态选择数据
        options: [{
          value: '选项1',
          label: '待审批'
        }, {
          value: '选项2',
          label: '通过'
        }, {
          value: '选项3',
          label: '拒绝'
        }, {
          value: '选项4',
          label: '全部'
        }, ],
        value: '',
        //部门选择数据
        res: [{
          value: '选项1',
          label: '软件部'
        }, {
          value: '选项2',
          label: '销售部'
        }, {
          value: '选项3',
          label: '技术部'
        }, {
          value: '选项4',
          label: '开发部'
        }, ],
        //表格数据
        tableData: [{
          date: '2016-05-02',
          name: '迈克尔',
          address: '小贷报表处理',
          app:'马克尔/河北省小贷管理系统',
          time:'0.5天',
          timetwo:'2020-05-16 17:30 至 20:30',
          bumen:'技术部',
          zhuangtai:'待审核',
          caozuo:'审批'
        },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },




        ],
        value: '',
      value1: ''

      }

    },
    methods:{
      goBack(){

        this.$router.push({ path:'/myreader/reader'})
      }
    }
  }

</script>

<style lang="scss">

  .header{
    margin-bottom: 20px;
  }
  .btn{
    margin-left: 20px;
  }
  .seach{
    margin-top: 20px;
  }
.tables{
  margin-left: 20px;
  margin-top: 30px;
}

</style>
