
<template>
  <div class="contain-little">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="加班审批">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="success"> <span class="el-icon-check"></span>通过</el-button>
      <el-button type="danger"> <span class="el-icon-close"></span>拒绝</el-button>
      <el-button type="warning"><span class="el-icon-download"></span>导出</el-button>

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
       <el-select v-model="value" placeholder="请选择状态" style="margin-left:20px;">
         <el-option
           v-for="item in options"
           :key="item.value"
           :label="item.label"
           :value="item.value">
         </el-option>
       </el-select>
       <span style="margin-left:20px;">部门</span>
       <el-select v-model="value" placeholder="请选择部门" style="margin-left:20px;">
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
        width="130">
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
    <el-pagination
      style="margin-top:15px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="littlePage4"
      :page-sizes="[100, 200, 300, 400]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>


  </div>
</template>


<script>
  import {listLeave } from "@/api/business/mywork/leave";

  export default {
    name: "page-little",
    data(){
      return{

        littlePage4:1,
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
    created() {
      this.getList()
    },
    methods:{

      getList(){
        this.loading = true;
        listLeave(this.addDateRange(this.queryParams, this.leaveDateRange)).then(response => {
          this.leaveList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      goBack(){

        this.$router.push({ path:'/myreader/index'})
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
    },

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
  .el-pagination{
    float:right;
  }

</style>
