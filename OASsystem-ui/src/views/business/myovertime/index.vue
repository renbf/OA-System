<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新建申请</el-button
        >
        <!-- v-hasPermi="['business:extraWork:remove:add']" -->
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:extraWork:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-message"
          size="mini"
          @click="handleReport"
          >报送</el-button
        >
      </el-col>

      <!--导出-->
      <el-col :span="1.5">
        <export-import
          excel-title="我的加班列表"
          exp-function="json"
          :set-export-data="setExportData"/>
      </el-col>

      <el-col :span="1.5">
        <span class="font">剩余加班时长:<input class="width" v-model="realOvertimeSurTime" />{{workHourUnit}}</span>
      </el-col>
    </el-row>


    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="加班时间">
        <el-date-picker
          v-model="dateRanges"
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
      <el-form-item label="陈述" prop="extraWorkReason">
        <el-input
          v-model="queryParams.extraWorkReason"
          placeholder="请输入陈述内容"
          clearable
          size="medium"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-table id="tableData" ref="multipleTable" v-loading="loading" :data="extraWorkList" @selection-change="handleSelectionChange" @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="加班时间" prop="extraWorkDates" :show-overflow-tooltip="true"  width="300" align="center">
        <template slot-scope="scope">
          <span>{{ JSON.parse(scope.row.extraWorkDates)[0] }} - {{ JSON.parse(scope.row.extraWorkDates)[1] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(department, scope.row.deptId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="加班项目" align="center">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(overtimeOptions, scope.row.extraWorkPrjName) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="加班时长" prop="extraWorkHours" :show-overflow-tooltip="true" width="80" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.extraWorkHours}}{{scope.row.extraHoursUnit}}</span>
        </template>
      </el-table-column>
      <el-table-column label="理由陈述" prop="extraWorkReason" :show-overflow-tooltip="true" align="center"/>
      <el-table-column label="申请时间" align="center" prop="createTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(GLOBAL.SYS_CHECK_STATUS, scope.row.approvalStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前审批人" prop="curApprover" :show-overflow-tooltip="true" align="center"/>

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <div v-if="scope.row.approvalStatus == 2">
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleUpdate(scope.row)">编辑</el-button>
            <!-- v-hasPermi="['business:extraWork:edit']" -->
            <el-button size="mini" type="text" icon="el-icon-delete" @click.stop="handleDelete(scope.row)" v-hasPermi="['business:extraWork:remove']">删除</el-button>
            <el-button size="mini" type="text" icon="el-icon-message" @click.stop="handleReport(scope.row)">报送</el-button>
          </div>
          <!-- 4 通过什么按钮都没有 -->
          <div v-else-if="scope.row.approvalStatus == 0 || scope.row.approvalStatus == 1 || scope.row.approvalStatus == 99 "></div>
          <div v-else-if="scope.row.approvalStatus == -1">
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" icon="el-icon-message" @click.stop="handleReport(scope.row)" >报送</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>


    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改加班对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门" prop="deptId">
          <el-col :span="7">
            <el-select
              v-model="form.deptId"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in department"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="在项目中" prop="inPrjFlag">
          <el-radio-group v-model="form.inPrjFlag">
            <el-radio
              v-for="(dict,index) in yesOrNo"
              :key="index"
              :label="dict.dictValue"
              :value="dict.dictValue"
              @change="editRules()"
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>

        <el-form-item label="审批流程" prop="workflowId">
          <el-select
            v-model="form.workflowId"
            placeholder="选择审批流程"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in workflowOptions"
              :key="dict.workflowId"
              :label="dict.workflowName"
              :value="dict.workflowId"
            />
          </el-select>
        </el-form-item>


        <el-form-item
          label="加班项目"
          class="extraWork_after"
          prop="extraWorkPrjName"
          v-show="form.inPrjFlag"
        >
          <el-col :span="20">
            <el-select
              v-model="form.extraWorkPrjName"
              placeholder="请选择"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in overtimeOptions"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="加班时间" prop="dateRange" class="extraWork_time">
          <el-col
            :span="20"
            class="mar-bot"
            :key="index"
          >
            <el-date-picker
              v-model="form.dateRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="dateChange(form.dateRange)"
              value-format="yyyy-MM-dd HH:mm"
              format="yyyy-MM-dd HH:mm"
              :default-time= "defaultDate"
            >
            </el-date-picker>
          </el-col>
          <transition name="fade" enter-active-class="animated fadeInLeft">
            <div class="el-form-item__error" v-if="err_blo_hide">
              请选择加班日期
            </div>
          </transition>
        </el-form-item>
        <el-form-item label="加班时长" prop="extraWorkHours">
          <el-col :span="10">
            <el-input disabled="" v-model="form.extraWorkHours" ></el-input>
            <span class="font_margin_top" >{{form.extraHoursUnit}}</span>
          </el-col>
        </el-form-item>
        <el-form-item label="理由陈述" prop="extraWorkReason">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="form.extraWorkReason"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <el-collapse>
        <el-collapse-item title="加班申请注意事项" name="1">
          <div v-for="(item,index) in overtimePrecautions">
            {{item}}
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="save">保 存</el-button>
        <el-button type="primary" @click="save('submit')">提交</el-button>
      </div>
    </el-dialog>

    <!-- 查看加班对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="detail"
      width="800px"
      id="diadetail"
    >
      <el-col :span="10" v-show="billTracesFlag">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :icon="activity.icon"
            :type="activity.type"
            :color="activity.color"
            :size="activity.size"
            :timestamp="activity.timestamp"
          >
            <p>{{ activity.workflowNodeName }}</p>
            <p>{{ activity.checkRemarks }}</p>
          </el-timeline-item>
        </el-timeline>
      </el-col>
      <el-col :span="12">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="部门" prop="deptId">
              <el-select disabled placeholder="请选择" clearable v-model="form.deptId">
                <el-option
                  v-for="(item, index) in department"
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
            label="加班项目"
            class="extraWork_after"
            prop="extraWorkPrjName"
            v-show="form.inPrjFlag"
          >
            <el-select
              v-model="form.extraWorkPrjName"
              placeholder="请选择"
              clearable
              :style="{ width: '100%' }"
              disabled
            >
              <el-option
                v-for="(item, index) in overtimeOptions"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="加班时间" prop="extraWork_time" class="extraWork_time">
            <el-col
              :span="20"
              class="mar-bot"
              :key="index"
            >
              <el-date-picker
                disabled
                v-model="form.dateRange"
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
          <el-form-item label="加班时长" prop="extraWorkHours">
            <el-col :span="10">
              <el-input disabled="" v-model="form.extraWorkHours"></el-input>
              <span class="font_margin_top" >{{form.extraHoursUnit}}</span>
            </el-col>
          </el-form-item>
          <el-form-item label="理由陈述" prop="extraWorkReason">
            <el-input
              type="textarea"
              placeholder="请输入"
              disabled
              v-model="form.extraWorkReason"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </el-col>
    </el-dialog>
  </div>
</template>

<script>

import { delExtraWorks,updateExtraWork, addExtraWork,listExtraWork,extraWorkSumbit } from "@/api/business/mywork/extrawork";
import { getHolsCheckInfo } from "@/api/business/mywork/holscheck";
import { listComConfig} from "@/api/system/comconfig";
import { getDeptList } from "@/api/system/dept";
import {isNotEmpty} from "../../../utils/common";


export default {
  name: "extraWork",
  data() {
    return {
      activities: [],
      billTracesFlag: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 时间错误信息提示
      err_blo_hide: false,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 日常工作表格数据
      extraWorkList: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查看是否显示弹出层
      detail: false,
      defaultDate: [],
      workHourUnit: '',
      department: [],
      yesOrNo: [],
      workHour: "",
      overtimePrecautions:[],
      dateRanges: [],

      statusOptions: [],
      realOvertimeSurTime: 0,
      overPeriodStart: "",
      overPeriodEnd: "",
      overPeriodVal: [],
      overDayVal: [],
      workflowOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        extraWorkReason: '',
        approvalStatus: ''
      },
      // 表单参数
      form: {
        extraWorkId:'',
        inPrjFlag: true,
        deptId: '',
        extraWorkPrjName: '',
        extraWorkReason: '',
        extraWorkHours: 0,
        extraHoursUnit: '',
        dateRange: [[]],
        saveFlag: true,
        overtimeSurTime: 0,
        extraWorkDates: ""
      },
      overtimeOptions: [
        {
          dictValue: "0",
          dictLabel: "小额贷款项目"
        },
        {
          dictValue: "1",
          dictLabel: "网信办项目"
        }
      ],
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "部门不能为空", trigger: "blur" }
        ],
        extraWorkPrjName: [
          { required: true , message: "加班项目不能为空", trigger: "blur" }
        ],
        extraWorkHours: [
          { required: true, message: "加班时长不能为空", trigger: "blur" }
        ],
        extraWorkReason: [
          { required: true, message: "加班理由不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {

    this.getList()
    this.form.dateRange.push([]);

    //获取工时
    listComConfig({}).then(response => {

      this.workHour = eval(response.rows.filter(e => Object.is(e.comConfigKey,'workHour')))[0].comConfigValue;

      let eval1 = eval(response.rows.filter(e => Object.is (e.comConfigKey,'workHourUnit')));
      this.form.extraHoursUnit = this.workHourUnit = eval1[0].comConfigValue;

      this.overPeriodVal  = eval(response.rows.filter(e => Object.is(e.comConfigKey,'overPeriod'))[0].comConfigValue);
      this.overDayVal  = eval(response.rows.filter(e => Object.is(e.comConfigKey,'overDay'))[0].comConfigValue);

      this.overPeriodStart = this.overPeriodVal[0][0][0];
      this.overPeriodEnd = this.overPeriodVal[0][0][1];
      this.overtimePrecautions = eval(response.rows.filter(e => Object.is(e.comConfigKey,'overtimePrecautions')))[0].comConfigValue;
      this.overtimePrecautions = this.overtimePrecautions.replace("\"","").replace("\"","").split("；");

      this.defaultDate.push(this.overPeriodStart +":00")
      this.defaultDate.push(this.overPeriodEnd +":00")

    });
    //审批状态
    this.statusOptions =  this.selectDictByType(this.GLOBAL.SYS_CHECK_STATUS)

    //系统是否
    this.getDicts("sys_yes_no").then(response => {
      response.data.forEach( (val) => this.yesOrNo.push({'dictValue': eval(val.dictValue),'dictLabel': val.dictLabel}))
    });
    //获取部门列表
    getDeptList({parentId:'100'}).then(response => {
      response.data.forEach( (val) => this.department.push({'dictValue': val.deptId,'dictLabel': val.deptName}) )
    });

    //获取加班
    getHolsCheckInfo().then(response => {
      if(response.data){
        this.realOvertimeSurTime = this.form.overtimeSurTime = response.data.leaveRest
      }
    });

    this.getWorkflowList({workflowGroupId:this.GLOBAL.EXTRA_WORKFLOWID}).then(response => {
      this.workflowOptions = response.rows;
    });

  },
  methods: {

    setExportData(){
      let dataArray = [];
      this.extraWorkList.forEach((extraWork, i) => {
        const { extraWorkDates,extraWorkHours,extraHoursUnit,deptId,extraWorkReason,createTime,approvalStatus,curApprover  } = extraWork;
        var dataFormat = {
          "加班时间":JSON.parse(extraWorkDates)[0],
          "加班时长":extraWorkHours+extraHoursUnit,
          "部门":this.selectDictLabel(this.department, deptId),
          "理由陈述":extraWorkReason,
          "申请时间":createTime,
          "状态":this.selectDictLabel(this.statusOptions, approvalStatus),
          "当前审批人":curApprover,
        }
        dataArray.push(dataFormat)
      });

      return dataArray;
    },

    editRules(){
      if(!this.form.inPrjFlag){
        this.rules.extraWorkPrjName[0].required = false
        this.form.extraWorkPrjName = '';
      }else{
        this.rules.extraWorkPrjName[0].required = true
      }
    },


    /** 查询日常工作列表 */
    getList() {
      this.loading = true;
      listExtraWork(this.addDateRange(this.queryParams, this.dateRanges)).then(response => {
        this.extraWorkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    dateChange(dateRange) {
      if(!dateRange){
        this.form.dateRange = [];
        this.form.extraWorkHours = null;
        return;
      }else{
        this.err_blo_hide = false;

        var dateRangeCopy = [[]];
        dateRangeCopy[0].push(dateRange)
        let timehour = this.common.calculateHours(dateRangeCopy,this.workHour,0);


        let startDay = dateRange[0].split(" ")[0];
        let endDay = dateRange[1].split(" ")[0]


        //实际选择的开始日期与结束日期
        let startTime = new Date(dateRange[0]);
        let endTime = new Date(dateRange[1]);
        //实际开始结束日期相差毫秒数
        let realSubMils = endTime - startTime;


        let comStartTime = new Date(startDay +" "+ this.overPeriodStart);
        if(comStartTime.getTime()!=startTime.getTime()){
          this.$message.error('错了哦，加班开始日期选错,请修正！');
          return;
        }
        this.form.extraWorkHours = timehour;

        // 加班调休加班时长重新计算
        if(Object.is(this.workHourUnit,'时')){
          this.form.overtimeSurTime += timehour;
        }else if(Object.is(this.workHourUnit,'天')){

          let length = this.overPeriodVal.length;
          //循环加班时间段 计算加班天数
          for (let i = 0; i < length; i++) {

            let overDay = this.overDayVal[i];
            let overPeriod = this.overPeriodVal[i][0];

            //公司规定晚上加班开始、结束时间
            let comStartTimeY = new Date(startDay +" "+ overPeriod[0]);
            let comendTimeY = new Date(startDay +" "+ overPeriod[1]);
            //公司规定相差毫秒数  10800000
            let comSubMils = comendTimeY - comStartTimeY;

            //如果实际时间 大于等于 公司规定时间，就获取其实际加班时长 并进行计算
            if(realSubMils>=comSubMils){
              timehour = overDay;
            }
          }

          this.form.overtimeSurTime += this.form.extraWorkHours = eval(timehour);
        }

      }


    },
    handleRowClick(row, column, event) {
      if (column.label == "操作") {
      } else {
        // 这里的弹框标题是动态获取
        this.detail = true;

        let name = this.$store.state.user.nickName;
        this.title = name + " / 加班申请查看";
        this.form = row;
        this.form.dateRange = eval(this.form.extraWorkDates)[0][0]


        //查看流程节点信息
        this.getBillTraces(this.form.extraWorkId,this.form.workflowId).then(response => {

          if (response.code === 200) {
            this.activities = response.data;
            if(!isNotEmpty(this.activities)){
              this.billTracesFlag = false;
            }
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


    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }

      this.form= {
          extraWorkId:'',
          inPrjFlag: true,
          deptId: '',
          extraWorkPrjName: '',
          extraWorkReason: '',
          extraWorkHours: 0,
          dateRange: [[]],
          saveFlag: true,
          extraHoursUnit: ''
      }
      this.resetForm("form");

      this.err_blo_hide = false

      this.form.overtimeSurTime=this.realOvertimeSurTime
      this.form.extraHoursUnit=this.workHourUnit
    },


    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRanges = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.extraWorkId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新建加班申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.$nextTick(function(){

        const { extraWorkId,extraWorkDates,deptId,inPrjFlag,extraWorkHours,extraWorkPrjName,extraWorkReason,overtimeSurTime } = row;
        Object.assign(this.form, {
          dateRange: eval(extraWorkDates),
          extraWorkId,
          deptId,
          inPrjFlag,
          extraWorkHours,
          extraWorkPrjName,
          extraWorkReason,
          overtimeSurTime
        });

        this.editRules();

      })

      this.open = true;
      this.title = "编辑加班申请";
    },


    //清空时间
    clearDate(){
      this.form.dateRange = []
      this.form.extraWorkHours = 0;
    },

    //保存加班提交
    save(type) {

      if(isNotEmpty(type) && type == 'submit'){
        this.form.saveFlag = false
      }

      this.$refs["form"].validate(valid => {
        if (this.form.dateRange[0].length == 0) {
          this.err_blo_hide = true;
        } else {
          this.err_blo_hide = false;
        }

        if (valid && this.form.dateRange[0].length != 0) {
          this.open = false;
          this.$confirm("请确认是否保存", "提交保存", {
                dangerouslyUseHTMLString: true,
                distinguishCancelAndClose: true,
                confirmButtonText: "保存",
                cancelButtonText: "返回列表",
                type: "warning"
              }).then(() => {

                //组装数据
                this.form.userId = this.$store.state.user.userId;
                this.form.extraWorkDates = JSON.stringify(this.form.dateRange)

                if (this.form.extraWorkId) {
                  updateExtraWork(this.form).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("更新成功");
                      this.reset();
                      this.getList();
                    } else {
                      this.msgError(response.msg);
                    }
                  });
                }else{
                  addExtraWork(this.form).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("保存成功");
                      this.reset();
                      this.getList();
                    } else {
                      this.msgError(response.msg);
                    }
                  });
                }

              }).catch(() => {
                this.reset();
                this.getList();
              });
        }
      });
    },

    returnResult(response){
      if (response.code === 200) {
        this.$confirm("保存成功", "保存成功", {
            dangerouslyUseHTMLString: true,
            showConfirmButton: false,
            distinguishCancelAndClose: true,
            cancelButtonText: "返回列表",
            type: "success"
          }).catch(() => {
          this.reset();
          this.getList();
        });
      } else {
        this.msgError(response.msg);
      }
    },


    /** 删除按钮操作 */
    handleDelete(row) {
      const extraWorkIds = row.extraWorkId || this.ids;
      this.$confirm(
        "请确认是否删除",
        {
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "删除",
          cancelButtonText: "返回列表",
          type: "warning"
        }
      )
        .then(() => {
          delExtraWorks(extraWorkIds).then(response => {
            if (response.code === 200) {
              this.msgSuccess("删除成功");
              this.reset();
              this.getList();
            }
          });
        })
        .catch(() => {
          this.reset();
          this.getList();
        });
    },

    //报送加班
    handleReport(row) {
      const workIds = row.extraWorkId || this.ids;
      this.$confirm(
        "请确认是否报送",
        {
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "报送",
          cancelButtonText: "返回列表",
          type: "warning"
        }
      )
        .then(() => {
          extraWorkSumbit(workIds).then(response => {
            if (response.code === 200) {
              this.msgSuccess("报送成功");
              this.reset();
              this.getList();
            }
          });
        })
        .catch(() => {
          this.reset();
          this.getList();
        });
    },

  },




}

</script>

<style lang="scss">
.span_right {
  margin-right: 10px;
}
.width_to {
  width: 21% !important;
}
.el-timeline-item__tail {
  top: 14px;
}
.width {
  width: 35px;
  border: none;
}
.el-icon-arrow-right:before {
  color: #333;
  font-weight: bold;
}
.font {
  font-size: 14px;
  font-weight: bold;
  margin-top: 6px;
  display: inline-block;
}
.margin-right {
  margin-right: 5px;
}
.over {
  margin-left: 10px;
}
.font_margin_top {
  font-size: 14px;
  font-weight: bold;
}
.el-date-editor--datetimerange.el-input__inner {
  width: 350px;
}
.tip {
  color: #c0c4cc;
  font-size: 10px;
}
.mar {
  margin-top: 10px;
}
.mar-bot {
  margin-bottom: 10px;
}
.el-collapse-item__header {
  color: #f56c6c;
}
.extraWork_time .el-form-item__label:before {
  content: "*";
  color: #ff4949;
  margin-right: 4px;
}
#diadetail .el-dialog__body {
  height: 500px;
}
#diadetail .el-dialog__body > .el-col:nth-child(1) {
  overflow: hidden;
  overflow-y: scroll;
  height: 422px;
}
</style>
