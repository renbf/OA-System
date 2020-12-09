

<template>
  <div class="contain-xxx">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="项目待办">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="success" @click="handlebatchProjectApplyShenpi(1)"> <span class="el-icon-check"></span>通过</el-button>
      <el-button type="danger" @click="handlebatchProjectApplyShenpi(0)"> <span class="el-icon-close"></span>拒绝</el-button>
      <el-button type="warning"><span class="el-icon-download"></span>导出</el-button>

    </div>
    <!-- seach栏-->

    <div class="seach" >
      <span style="margin-left:20px;" >申请时间</span>
      <el-date-picker
        style="margin-left:20px;"
        v-model="queryParams.searchDate"
        type="daterange"
        format="yyyy-MM-dd"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>

      <span style="margin-left:20px;">状态</span>
      <el-select v-model="queryParams.billStatus" placeholder="请选择状态" style="margin-left:20px;">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span style="margin-left:20px;">姓名</span>
      <el-input v-model="queryParams.createNickName" placeholder="请输入姓名" style="width:200px;margin-left:10px;"></el-input>


      <el-button type="primary" style="margin-left:20px;" @click="searchButton"> <span class="el-icon-search"></span>搜索</el-button>
      <el-button style="margin-left:20px;"> <span class="el-icon-refresh-right"></span>重置</el-button>

    </div>

    <el-table
      class="tables"
      :data="tableData"
      @selection-change="handleSelectionChange"
      @row-dblclick="handleLookOpen"
      style="width: 100%">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="申请时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="workflowGroupName"
        label="申请类型"
        width="170">
      </el-table-column>
      <el-table-column
        prop="createNickName"
        label="姓名"
      >
      </el-table-column>
      <el-table-column
        prop="deptName"
        label="部门"
        width="170">
      </el-table-column>
      <el-table-column
        prop="projectName"
        label="项目组">
      </el-table-column>

      <el-table-column
        label="状态">
        <template slot-scope="scope">
          <span v-show="scope.row.billStatus == 0">待审核</span>
          <span v-show="scope.row.billStatus == 1">通过</span>
          <span v-show="scope.row.billStatus == 2">拒绝</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="操作">
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <span class="el-icon-edit-outline" v-show="scope.row.billStatus == 0" @click.stop="handleUpdateProjectLeaderApply(scope.row)">审批</span>
        </template>
      </el-table-column>

    </el-table>
    <el-pagination
      style="margin-top:15px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total">
    </el-pagination>


    <el-dialog
      :title="projectApplyTitle"
      :visible.sync="projectApplyOpen"
      width="40%">
      <div style="overflow: hidden;clear: both">
        <div style="float:left;height: 400px;width:200px;">
          <el-steps direction="vertical" :active="projectApplyForm.activeNum" finish-status="success">
            <el-step :title="item.shenpiUserName"  :description="item.description" v-for="item in projectApplyForm.shenpiUserList"></el-step>
          </el-steps>
        </div>
        <div style="float:left;width:511px;top:20px;right:50px;" class="dialogtext">
          <el-form ref="projectApplyForm" :model="projectApplyForm" label-width="80px">

          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button @click="projectLeaderApplySubmitForm(2)">拒绝</el-button>
            <el-button type="primary" @click="projectLeaderApplySubmitForm(1)">通过</el-button>
          </div>
        </div>
      </div>

    </el-dialog>

  </div>
</template>


<script>
  import { projectLeaderWorkflowList } from "@/api/business/mywork/myproject";

  export default {
    name: "page-face",
    data(){
      return{
        //状态选择数据
        options: [{
          value: '0',
          label: '待审核'
        }, {
          value: '1',
          label: '通过'
        }, {
          value: '2',
          label: '拒绝'
        }],
        //表格数据
        tableData: [],
        queryParams:{
          searchDate: [],
          createNickName: undefined,
          billStatus: undefined,
          page:1,
          limit:10
        },
        pageInfo: {},
        projectLeaderApplyForm: {
          billId:undefined,
          workflowId:undefined,
          billStatus:undefined
        },
        projectApplyForm: {
          projectApplyId:undefined,
          projectApplyTitle:undefined,
          content:undefined,
          shenpiUserList:[],
          activeNum:0,
          remark:undefined
        },
        projectApplyTitle: "",
        projectApplyOpen:false,
      }

    },
    created() {
      this.getProjectLeaderWorkflow();
    },
    methods:{
      goBack(){
        this.$router.push({ path:'/myreader/index'})
      },
      getProjectLeaderWorkflow() {
        let _this = this;
        projectLeaderWorkflowList(_this.queryParams).then(response => {
          if(response.code == 200){
            _this.tableData= response.data;
            _this.pageInfo = response.pageInfo;
          }
        });
      },
      //每页多少条
      handleSizeChange(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.limit = val;
        this.getProjectLeaderWorkflow();
      },
      //第几页
      handleCurrentChange(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.page = val;
        this.getProjectLeaderWorkflow();
      },
      //多选
      handleSelectionChange(val) {
        let _this = this;
        let projectApplyIds = [];
        val.forEach((item) => {
          projectApplyIds.push(item.projectApplyId);
        });
        _this.projectApplyIds = projectApplyIds;
      },
      //查看行
      handleLookOpen(row, column, event){
        this.resetProjectApplyForm();
        this.projectApplyLookTitle = "审批项目申请";
        this.projectApplyLookOpen = true;
        this.setProjectApplyValue(row);
      },
      searchButton() {
        let _this = this;
        _this.queryParams.page = 1;
        this.getProjectLeaderWorkflow();
      },
      resetProjectApplyForm(){
        this.projectApplyForm = {
          projectApplyId:undefined,
          projectApplyTitle:undefined,
          content:undefined,
          shenpiUserList:[],
          activeNum:0,
          remark:undefined
        }
        this.resetForm("projectApplyForm");
      },
      setProjectApplyValue(item) {
        let _this = this;
        _this.projectLeaderApplyForm = {
          billId:item.billId,
          workflowId:item.workflowId,
          billStatus:undefined
        };
      },
      //审批弹框
      handleUpdateProjectLeaderApply(item) {
        this.resetProjectApplyForm();
        this.projectApplyTitle = "审批项目申请";
        this.projectApplyOpen = true;
        this.setProjectApplyValue(item);
      },
      //提交审批
      projectLeaderApplySubmitForm(checkStatus) {
        let _this = this;
        let msg = '';
        let title = '';
        if (checkStatus == '1') {
          msg = '确认通过审批?';
          title = '通过审批';
        }else{
          msg = '确认拒绝审批?';
          title = '拒绝审批';
        }
        this.$confirm(msg, title, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let projectLeaderApplyForm = _this.projectLeaderApplyForm;
          projectLeaderApplyForm.billStatus = checkStatus;
          batchProjectApplyShenpi(projectLeaderApplyForm).then(response => {
            if (response.code === 200) {
              this.msgSuccess("审批成功");
              this.projectApplyOpen = false;
              this.getProjectLeaderWorkflow();
            } else {
              this.msgError(response.msg);
            }
          });
        }).catch((err) => {
          this.$message({
            type: 'info',
            message: '操作异常'
          });
        });
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
