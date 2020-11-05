
<template>
  <div class="contain-leave">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="请假审批">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="approvePass"
        >通过</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-close"
          :disabled="multiple"
          size="mini"
          @click="approveRefuse"
        >拒绝</el-button
        >
      </el-col>

      <!--导出-->
      <el-col :span="1.5">
        <export-import
          excel-title="请假审批列表"
          exp-function="json"
          :disabled="multiple"
          :set-export-data="setExportData"
        />
      </el-col>
    </el-row>
    <!-- seach栏-->
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="申请时间" prop="approvalDate">
        <el-date-picker
          v-model="approvalDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="billStatus">
        <el-select
          v-model="queryParams.billStatus"
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
      <el-form-item label="部门" prop="deptId">
        <el-select v-model="queryParams.deptId" placeholder="请选择部门" style="margin-left:20px;">
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
      @selection-change="handleSelectionChange"
      :data="tableData"
      @row-click="handleRowClick"
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
          <span>{{ selectDictLabel(projectNameOptions, scope.row.leavePrjName) }}</span>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="请假时长"
        width="170">
        <template slot-scope="scope">
          <span>{{scope.row.leaveHours}}{{scope.row.leaveHoursUnit}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="请假时间">
        <template slot-scope="scope">
          <span>{{ JSON.parse(scope.row.leaveDates)[0][0][0] }}  -  {{ JSON.parse(scope.row.leaveDates)[0][0][1] }}</span>
        </template>
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
        label="状态">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(GLOBAL.SYS_CHECK_STATUS, scope.row.billStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.billStatus == 0 || scope.row.billStatus == 1">
            <el-button size="mini" type="text" @click.stop="handleRowClick(scope.row)">审批</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog
      :title="title"
      :visible.sync="detail"
      width="900px"
      id="diadetail"
    >
      <el-col :span="10">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :icon="activity.icon"
            :type="activity.type"
            :color="activity.color"
            :size="large"
            :timestamp="activity.timestamp"
          >
            <p>{{ activity.workflowNodeName }}</p>
            <p>{{ activity.checkRemarks }}</p>
          </el-timeline-item>
        </el-timeline>
      </el-col>
      <el-col :span="12">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="部门" prop="deptId">
            <el-select disabled placeholder="请选择" clearable v-model="form.deptId">
              <el-option
                v-for="(item, index) in departmentOption"
                class="width_to"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
                disabled
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="在项目中" prop="inPrjFlag">
            <el-radio-group v-model="form.inPrjFlag" disabled>
              <el-radio
                v-for="(dict,index) in yesOrNo"
                :key="index"
                :label="dict.dictValue"
                :value="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio
              >
            </el-radio-group>
          </el-form-item>

          <el-form-item
            label="请假项目"
            class="leave_after"
            prop="leavePrjName"
            v-show="form.inPrjFlag"
          >
            <el-select
              v-model="form.leavePrjName"
              placeholder="请选择"
              clearable
              :style="{ width: '100%' }"
              disabled
            >
              <el-option
                v-for="(item, index) in projectNameOptions"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请假类型" class="leave_after" prop="leaveType">
            <el-col :span="10">
              <el-select
                disabled
                v-model="form.leaveType"
                placeholder="请选择"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in leaveTypeOptions"
                  :key="index"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="14" v-show="overtime_show">
              <span class="over font_margin_top">加班:</span>
              <input v-model="form.overtimeSurTime" readonly class="width" />
              <span class="margin-right font_margin_top">{{workHourUnit}}</span>
            </el-col>

            <el-col :span="14" v-show="annual_leave_show">
              <span class="font_margin_top">年休假时长:</span>
              <input v-model="form.annLeaSurTime" readonly class="width" />
              <span class="font_margin_top">天</span>
            </el-col>
          </el-form-item>

          <el-form-item label="请假时间" prop="Leave_time" class="Leave_time">
            <el-col
              :span="20"
              class="mar-bot"
              v-for="(item, index) in form.dateRange"
              :key="index"
            >
              <el-date-picker
                disabled
                v-model="item[0]"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd HH:mm"
                format="yyyy-MM-dd HH:mm"
                :default-time="['08:30:00', '12:00:00']"
              >
              </el-date-picker>
            </el-col>
          </el-form-item>

          <el-row>
            <el-col :span="15">
              <el-form-item label="请假时长" prop="leaveHours">
                <el-input disabled v-model="form.leaveHours"></el-input>
                <span class="margin-right font_margin_top">{{form.leaveHoursUnit}}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="理由陈述" prop="leaveReason">
            <el-input
              type="textarea"
              placeholder="请输入"
              disabled
              v-model="form.leaveReason"
            >
            </el-input>
          </el-form-item>

          <el-form-item label="审核意见" prop="checkRemarks">
            <el-input
              type="textarea"
              placeholder="请输入"
              v-model="checkRemarks"
            >
            </el-input>
          </el-form-item>

            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="success"
                  icon="el-icon-check"
                  size="mini"
                  @click="approvePass(form.leaveId)"
                >通过</el-button
                >
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  icon="el-icon-close"
                  size="mini"
                  @click="approveRefuse(form.leaveId)"
                >拒绝</el-button
                >
              </el-col>
            </el-row>
        </el-form>
      </el-col>
    </el-dialog>

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

  import {approveList,leaveSumbit } from "@/api/business/mywork/leave";

  export default {
    name: "page-leave",
    data(){
      return{
        detail: false,
        // 选中数组
        ids: [],
        title: '',
        yesOrNo: [],
        activities: [],
        departmentOption: [],
        leaveTypeOptions: [],
        statusOptions: [],
        // 非多个禁用
        multiple: true,
        checkRemarks: '',
        workHourUnit: '',
        annual_leave_show: false,
        overtime_show: false,
        // 总条数
        total: 0,
        // 查询参数
        approvalDate: '',
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deptId: '',
          leaveType: '',
          billStatus: '',
          workflowId: this.GLOBAL.LEAVE_WORKFLOWID,
        },
        form: {
          leaveId:'',
          inPrjFlag: true,
          leaveType: '',
          deptId: '',
          leavePrjName: '',
          leaveReason: '',
          leaveHours: 0,
          dateRange: [[]],
          saveFlag: true,
          overtimeSurTime: 0,
          leaveHoursUnit: '',
          annLeaSurTime: 0,
        },
        //表格数据
        tableData: [],
        projectNameOptions: [
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
      //系统是否
      this.getDicts("sys_yes_no").then(response => {
        response.data.forEach( (val) => this.yesOrNo.push({'dictValue': eval(val.dictValue),'dictLabel': val.dictLabel}))
      });
    },
    methods:{
      setExportData(){
          let dataArray = [];
          this.tableData.forEach((data, i) => {
            const { leaveDates,applyTime,orginHandler,leavePrjName,deptId,leaveType,leaveHours,approvalStatus,leaveHoursUnit  } = data;
            var format = {
              "申请时间":applyTime,
              "申请人":orginHandler,
              "项目":leavePrjName,
              "请假时长":leaveHours+leaveHoursUnit,
              "请假时间":JSON.parse(leaveDates)[0][0][0],
              "请假类型": this.selectDictLabel(this.leaveTypeOptions, leaveType),
              "部门":this.selectDictLabel(this.departmentOption, deptId),
              "状态":this.selectDictLabel(this.statusOptions, approvalStatus),
            }
            dataArray.push(format)
          });

          return dataArray;
      },
      getList(){
        this.loading = true;
        approveList(this.addDateRange(this.queryParams, this.approvalDate)).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.leaveId);
        this.multiple = !selection.length;
      },


      approvePass(leaveId){
        if(!this.isNotEmpty(this.ids)){
          this.ids.push(leaveId)
        }
        this.approveDo('1')
        this.detail=false;
      },

      //审核拒绝
      approveRefuse(leaveId){
        if(!this.isNotEmpty(this.ids)){
          this.ids.push(leaveId)
        }
        this.approveDo('0')
        this.detail=false;
      },

      approveDo(checkStatus){
        var para = {
          billIds: this.ids,
          workflowId: this.GLOBAL.LEAVE_WORKFLOWID,
          checkStatus: checkStatus,
          checkRemarks: this.checkRemarks
        }
        this.approve(para).then(response => {
          if (response.code === 200) {
            this.msgSuccess("审核成功");
            this.getList();
          }
        })
      },

      handleRowClick(row) {
        if(row.leaveId){
          // 这里的弹框标题是动态获取
          this.detail = true;

          let name = row.orginHandler;
          this.title = name + " / 请假申请查看";
          this.form = row;
          this.form.dateRange = eval(this.form.leaveDates)

          this.overleaveHoursShow(this.form.leaveType);

          //查看流程节点信息
          this.getBillTraces(this.form.leaveId,this.form.workflowId).then(response => {

            if (response.code === 200) {
              this.activities = response.data;
              this.activities.forEach( e=>{
                e.checkRemarks = e.checkRemarks ? e.checkRemarks : "审核通过"
                if(e.checkStatus == '0'){
                  e.type = 'danger'
                  e.icon = 'el-icon-close'
                }else if(e.checkStatus == '1'){
                  e.type = 'success'
                  e.icon = "el-icon-check"
                }
                e.timestamp = e.checkerUserName + "(" + e.checkerDeptName+ ")" + this.parseTime(e.createTime)
              })

            } else {
              this.msgError(response.msg);
            }
          });
        }
      },
      //根据请假类型剩余时长展示
      overleaveHoursShow(leaveType){
        if(leaveType){
          if (leaveType == 1) {
            this.overtime_show = true;
          }else if(leaveType == 4){
            this.annual_leave_show = true;
          }
        }
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
        this.approvalDate = '';
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
  #diadetail .el-dialog__body {
    height: 650px;
  }

</style>
