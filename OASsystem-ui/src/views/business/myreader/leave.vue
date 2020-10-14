
<template>
  <div class="contain-leave">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="请假审批">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="success"> <span class="el-icon-check"></span>通过</el-button>
      <el-button type="danger"> <span class="el-icon-close"></span>拒绝</el-button>
      <el-button type="warning"><span class="el-icon-download"></span>导出</el-button>

    </div>
    <!-- seach栏-->
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="申请时间" prop="approvalDate">
        <el-date-picker
          v-model="queryParams.approvalDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="approvalStatus">
        <el-select
          v-model="queryParams.approvalStatus"
          placeholder="选择状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="department">
        <el-select v-model="queryParams.department" placeholder="请选择部门" style="margin-left:20px;">
          <el-option
            v-for="item in departmentOption"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="请假类型" prop="leaveType">
        <el-select v-model="queryParams.leaveType" placeholder="请选择请假类型" style="margin-left:20px;">
          <el-option
            v-for="item in leaveTypeOptions"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          @keyup.enter.native="handleQuery"
        >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      class="tables"
      :data="tableData"
      style="width: 100%">
      <el-table-column
        align="center"
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        align="center"
        label="申请时间"
        width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="orginHandler"
        label="申请人"
        width="170">
      </el-table-column>
      <el-table-column
        label="项目"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ selectDictLabel(overtime_options, scope.row.leavePrjName) }}</span>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        prop="leaveHours"
        label="请假时长"
        width="170">
      </el-table-column>
      <el-table-column
        align="center"
        prop="leaveDates"
        label="请假时间">
      </el-table-column>
      <el-table-column label="请假类型" align="center">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(GLOBAL.BUS_LEAVE_TYPE, scope.row.leaveType) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="部门">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(departmentOption, scope.row.deptId) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="billStatus"
        label="状态">
      </el-table-column>
      <el-table-column
      align="center"
      prop="caozuo"
      label="操作">
    </el-table-column>

    </el-table>
<!--    <el-pagination-->
<!--      style="margin-top:15px"-->
<!--      @size-change="handleSizeChange"-->
<!--      @current-change="handleCurrentChange"-->
<!--      :current-page="currentPage4"-->
<!--      :page-sizes="[100, 200, 300, 400]"-->
<!--      :page-size="100"-->
<!--      layout="total, sizes, prev, pager, next, jumper"-->
<!--      :total="400">-->
<!--    </el-pagination>-->


    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>


<script>

  import {approveList } from "@/api/business/mywork/leave";

  export default {
    name: "page-leave",
    data(){
      return{
        departmentOption: [],
        leaveTypeOptions: [],
        statusOptions: [],
        // 总条数
        total: 0,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          department: '',
          leaveType: '',
          approvalDate: '',
          approvalStatus: ''
        },

        //表格数据
        tableData: [{
          applyTime: '2016-05-02',
          leaveType:"加班调休",
          orginHandler: '迈克尔',
          leavePrjName:'马克尔/河北省小贷管理系统',
          leaveHours:'0.5天',
          leaveDates:'2020-05-16 17:30 至 20:30',
          deptId:'技术部',
          billStatus:'待审核',
          caozuo:'审批'
        }],
        overtime_options: [
          {
            dictValue: "0",
            dictLabel: "小额贷款项目"
          },
          {
            dictValue: "1",
            dictLabel: "网信办项目"
          }
        ],

      }

    },
    created() {
      this.getList()

      //获取请假类型
      this.leaveTypeOptions = this.selectDictByType(this.GLOBAL.BUS_LEAVE_TYPE)
      //审批状态
      this.statusOptions = this.selectDictByType(this.GLOBAL.SYS_CHECK_STATUS)
      //获取部门列表
      this.getDeptList({parentId:'100'}).then(response => {
        response.data.forEach( (val) => this.departmentOption.push({'dictValue': val.deptId,'dictLabel': val.deptName}) )
      });
    },
    methods:{
      getList(){
        this.loading = true;
        approveList(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      goBack(){
        this.$router.push({ path:'/myreader/index'})
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
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
