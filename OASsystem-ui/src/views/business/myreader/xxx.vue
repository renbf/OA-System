

<template>
  <div class="contain-xxx">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="xxx项目组申请">
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
        v-model="queryParams.searchDate"
        type="daterange"
        format="yyyy-MM-dd"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>

      <span style="margin-left:20px;">状态</span>
      <el-select v-model="queryParams.status" placeholder="请选择状态" style="margin-left:20px;">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span style="margin-left:20px;">陈述</span>
      <el-input v-model="queryParams.content" placeholder="请输入陈述理由" style="width:200px;margin-left:10px;"></el-input>


      <el-button type="primary" style="margin-left:20px;"> <span class="el-icon-search"></span>搜索</el-button>
      <el-button style="margin-left:20px;"> <span class="el-icon-refresh-right"></span>重置</el-button>

    </div>

    <el-table
      class="tables"
      :data="tableData"
      @selection-change="handleSelectionChange"
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
        prop="nickName"
        label="申请人"
        width="170">
      </el-table-column>
      <el-table-column
        prop="projectApplyTitle"
        label="标题"
      >
      </el-table-column>
      <el-table-column
        prop="content"
        label="申请内容"
        width="170">
      </el-table-column>
      <el-table-column
        prop="shenpiUserNames"
        label="审核人">
      </el-table-column>

      <el-table-column
        label="状态">
        <template slot-scope="scope">
          <span v-show="scope.row.status == 0">未报送</span>
          <span v-show="scope.row.status == 1">待审核</span>
          <span v-show="scope.row.status == 2">通过</span>
          <span v-show="scope.row.status == 3">拒绝</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="bumen"
        label="操作">
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <span class="el-icon-edit-outline" v-show="scope.row.status == 1" @click.stop="handleUpdateProjectApply(scope.row)">审批</span>
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


  </div>
</template>


<script>
  import { todolistProjectApply } from "@/api/business/mywork/myproject";

  export default {
    name: "page-face",
    data(){
      return{
        projectId:this.$route.query.projectId,
        //状态选择数据
        options: [{
          value: '0',
          label: '未报送'
        }, {
          value: '1',
          label: '审批中'
        }, {
          value: '2',
          label: '通过'
        }, {
          value: '3',
          label: '拒绝'
        }],
        //表格数据
        tableData: [],
        queryParams:{
          projectId:this.$route.query.projectId,
          searchDate: [],
          content: undefined,
          status: undefined,
          page:1,
          limit:10
        },
        pageInfo: {},
        projectApplyIds: []
      }

    },
    created() {
      this.getApplyList();
    },
    methods:{
      goBack(){
        this.$router.push({ path:'/myreader/index'})
      },
      getApplyList() {
        let _this = this;
        todolistProjectApply(_this.queryParams).then(response => {
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
        this.getApplyList();
      },
      //第几页
      handleCurrentChange(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.page = val;
        this.getApplyList();
      },
      //多选
      handleSelectionChange(val) {
        let _this = this;
        let taskIds = [];
        val.forEach((item) => {
          taskIds.push(item.taskId);
        });
        _this.taskIds = taskIds;
      },
      //查看
      handleLookOpen(row, column, event){
        this.resetTaskLookForm();
        this.taskLookTitle = "查看任务";
        this.taskLookOpen = true;
        this.updateSetTaskLookValue(row);
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
