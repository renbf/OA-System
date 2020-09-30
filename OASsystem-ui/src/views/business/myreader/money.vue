



<template>
  <div class="contain-money">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="费用报销审批">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="success"> <span class="el-icon-check"></span>通过</el-button>
      <el-button type="danger"> <span class="el-icon-close"></span>拒绝</el-button>
      <el-button type="warning"><span class="el-icon-download"></span>导出</el-button>
      <span style="margin-left:10px;">单位：元</span>

    </div>
    <!-- seach栏-->

    <div class="seach" >
      <span style="margin-left:20px;" >申请时间</span>
      <el-time-picker
        style="width:200px;margin-left:20px"
        is-range
        v-model="value1"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        placeholder="选择时间范围">
      </el-time-picker>
      <span style="margin-left:20px;">状态</span>
      <el-select v-model="value" placeholder="请选择状态" style="margin-left:20px;">
        <el-option
          v-for="item in options"
          :key="item.value1"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span style="margin-left:20px;">部门</span>
      <el-select v-model="value" placeholder="请选择部门" style="margin-left:20px;">
        <el-option
          v-for="item in options"
          :key="item.value1"
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
        label="报销时间"
        width="120">
      </el-table-column>
      <el-table-column
        prop="name"
        label="申请人"
        width="100">
      </el-table-column>
      <el-table-column
        prop="app"
        label="项目"
        width="400"
      >
      </el-table-column>
      <el-table-column
        prop="time"
        label="部门"
        width="120">
      </el-table-column>
      <el-table-column
        prop="timetwo"
        label="原借款"
        width="200">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="应退（补）金额">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="报销项目">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="总金额">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="状态">
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="操作">
      </el-table-column>

    </el-table>
    <el-pagination
      style="margin-top:15px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[100, 200, 300, 400]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>


  </div>
</template>


<script>
  export default {
    name: "page-money",
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

        lx: [{
          value: '选项1',
          label: '加班调休'
        }, {
          value: '选项2',
          label: '事假'
        }, {
          value: '选项3',
          label: '年假'
        }, ],

        //表格数据
        tableData: [{
          date: '2016-05-02',
          type:"加班调休",
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
        value1: '',
        value2: '',
        value3: ''

      }

    },
    methods:{
      goBack(){

        this.$router.push({ path:'/myreader/index'})
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
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
  .el-pagination{
    float:right;
  }

</style>
