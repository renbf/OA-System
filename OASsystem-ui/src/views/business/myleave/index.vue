<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:leave:add']"
          >新建申请</el-button
        >
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="delLeaves"
          v-hasPermi="['business:leave:remove']"
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
          excel-title="我的请假列表"
          exp-function="json"
          :set-export-data="setExportData"/>
      </el-col>

      <el-col :span="1.5">
        <span class="font">剩余加班时长:<input class="width" v-model="realOvertimeSurTime" />{{workHourUnit}}</span>
      </el-col>
      <el-col :span="1.5">
        <span class="font">剩余年休假:<input class="width" v-model="realAnnLeaSurTime" />天</span>
      </el-col>
    </el-row>


    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="请假时间">
        <el-date-picker
          v-model="leaveDateRange"
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
      <el-form-item label="陈述" prop="leaveReason">
        <el-input
          v-model="queryParams.leaveReason"
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
    <el-table id="tableData" ref="multipleTable" v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange" @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="请假时间" prop="leaveDates" :show-overflow-tooltip="true" align="center">
        <template slot-scope="scope">
          <span>{{ JSON.parse(scope.row.leaveDates)[0][0][0] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假时长" prop="leaveHours" :show-overflow-tooltip="true" width="80" align="center"/>
      <el-table-column label="部门" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(department, scope.row.deptId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假项目" align="center">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(overtime_options, scope.row.leavePrjName) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假类型" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(BUS_LEAVE_TYPE, scope.row.leaveType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="理由陈述" prop="leaveReason" :show-overflow-tooltip="true" align="center"/>
      <el-table-column label="申请时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(SYS_CHECK_STATUS, scope.row.approvalStatus) }}</span>
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
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleUpdate(scope.row)" v-hasPermi="['business:leave:edit']">编辑</el-button>
            <!-- v-hasPermi="['business:leave:edit']" -->
            <el-button size="mini" type="text" icon="el-icon-delete" @click.stop="delLeaves(scope.row)" v-hasPermi="['business:leave:remove']">删除</el-button>
            <el-button size="mini" type="text" icon="el-icon-message" @click.stop="handleReport(scope.row)" v-hasPermi="['business:leave:submit']">报送</el-button>
          </div>
          <!-- 4 通过什么按钮都没有 -->
          <div v-else-if="scope.row.approvalStatus == 0 || scope.row.approvalStatus == 1 || scope.row.approvalStatus == 99 "></div>
          <div v-else-if="scope.row.approvalStatus == -1">
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" icon="el-icon-message" @click.stop="handleReport(scope.row)" v-hasPermi="['business:leave:remove']">报送</el-button>
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

    <!-- 添加或修改请假对话框 -->
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
        <el-form-item
          label="请假项目"
          class="leave_after"
          prop="leavePrjName"
          v-show="form.inPrjFlag"
        >
          <el-col :span="20">
            <el-select
              v-model="form.leavePrjName"
              placeholder="请选择"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in overtime_options"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-row>
          <el-col :span="8">
            <el-form-item label="请假类型" class="leave_after" prop="leaveType">

            <el-select
              @change="selectChange(form.leaveType)"
              v-model="form.leaveType"
              placeholder="请选择"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in options"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="14" v-show="overtime_show">
            <el-form-item label="加班" class="leave_after" prop="overtimeSurTime" label-width="50px">
              <input v-model="form.overtimeSurTime" readonly class="width" />
              <span class="margin-right font_margin_top">{{workHourUnit}}</span>
            </el-form-item>
          </el-col>

          <el-col :span="14" v-show="annual_leave_show">
            <el-form-item label="年休假时长" class="leave_after" prop="annLeaSurTime" label-width="90px">
              <input v-model="form.annLeaSurTime" readonly class="width" />
              <span class="font_margin_top">天</span>
            </el-form-item>
          </el-col>

        </el-row>


        <el-form-item label="请假时间" prop="dateRange" class="Leave_time">
          <el-col
            :span="20"
            class="mar-bot"
            v-for="(item, index) in form.dateRange"
            :key="index"
          >
            <span class="tip" v-show="index == 0 ? true : false">注：选择时间请勿包含休息日或法定假日</span>
            <el-date-picker
              v-model="item[0]"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="dateChange(item, index)"
              value-format="yyyy-MM-dd HH:mm"
              format="yyyy-MM-dd HH:mm"
              :default-time= "defaultDate"
            >
            </el-date-picker>
            <el-button
              class="mar"
              type="primary"
              icon="el-icon-plus"
              circle
              @click="addDomain"
            ></el-button>

            <el-button
              type="danger"
              icon="el-icon-delete"
              circle
              @click.prevent="removeDomain(index, item,true)"
              v-show="index == 0 ? false : true"
            ></el-button>
          </el-col>
          <transition name="fade" enter-active-class="animated fadeInLeft">
            <div class="el-form-item__error" v-if="err_blo_hide">
              请选择请假日期
            </div>
          </transition>
        </el-form-item>

        <el-row>
          <el-col :span="10">
            <el-form-item label="请假时长" prop="leaveHours">
                <el-input disabled v-model="form.leaveHours" width="60%"></el-input>
                <!--<span class="font_margin_top">{{workHourUnit}}</span>-->
                <span class="font_margin_top">小时</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="理由陈述" prop="leaveReason">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="form.leaveReason"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <el-collapse>
        <el-collapse-item title="请假申请注意事项" name="1">
          <div v-for="(item,index) in leavePrecautions">
            {{item}}
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="save">保 存</el-button>
        <el-button type="primary" @click="form.saveFlag = false">提交</el-button>
      </div>
    </el-dialog>

    <!-- 查看请假对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="detail"
      width="800px"
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
            :size="activity.size"
            :timestamp="activity.timestamp"
          >
            <p>{{ activity.content }}</p>
            <p>{{ activity.description }}</p>
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
                v-for="(item, index) in overtime_options"
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
                  v-for="(item, index) in options"
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
                <!--<span class="margin-right font_margin_top">{{workHourUnit}}</span>-->
                <span class="font_margin_top">小时</span>
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
        </el-form>
      </el-col>
    </el-dialog>
  </div>
</template>

<script>

import { delLeaves,updateLeave, addLeave,listLeave,leaveSumbit } from "@/api/business/mywork/leave";
import { getHolsCheckInfo } from "@/api/business/mywork/holscheck";
import { listComConfig} from "@/api/system/comconfig";
import { listDept } from "@/api/system/dept";

export default {
  name: "leave",
  data() {
    return {
      activities: [
        {
          content: "主管审批",
          placement: "top",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          size: "large",
          type: "success",
          icon: "el-icon-check",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        },
        {
          content: "主管审批",
          placement: "top",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          size: "large",
          type: "info",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        },
        {
          content: "主管审批",
          placement: "top",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          size: "large",
          type: "info",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        },
        {
          content: "主管审批",
          size: "large",
          placement: "top",
          type: "info",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        },
        {
          content: "主管审批",
          size: "large",
          placement: "top",
          type: "info",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        },
        {
          content: "主管审批",
          size: "large",
          placement: "top",
          type: "info",
          timestamp: "丹尼尔（软件部)2020-05-22 ",
          description: "这里是审核内容,如未填写默认为'审核通过'"
        }
      ],
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
      leaveList: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查看是否显示弹出层
      detail: false,
      // 状态数据字典
      statusOptions: [],
      options: [],
      defaultDate: [],
      workHourUnit: '',
      timeBackUp: [],
      department: [],
      yesOrNo: [],
      workHour: "",
      overtime_show: false,
      annual_leave_show: false,
      leavePrecautions:[],
      leaveDateRange: [],

      BUS_LEAVE_TYPE: "bus_leave_type",
      SYS_CHECK_STATUS: "sys_check_status",

      realOvertimeSurTime: 0,
      realAnnLeaSurTime: 0,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leaveReason: '',
        approvalStatus: ''
      },
      // 表单参数
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
        annLeaSurTime: 0,
      },
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
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "部门不能为空", trigger: "blur" }
        ],
        leaveType: [
          { required: true, message: "请假类型不能为空", trigger: "blur" }
        ],
        leavePrjName: [
          { required: true , message: "请假项目不能为空", trigger: "blur" }
        ],
        leaveHours: [
          { required: true, message: "请假时长不能为空", trigger: "blur" }
        ],
        leaveReason: [
          { required: true, message: "请假理由不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList()
    this.form.dateRange.push([]);

    //获取请假类型
    this.options =  this.selectDictByType(this.BUS_LEAVE_TYPE)
    //审批状态
    this.statusOptions =  this.selectDictByType(this.SYS_CHECK_STATUS)

    //获取工时
    listComConfig({}).then(response => {

      this.workHour = eval(response.rows.filter(e => Object.is(e.comConfigKey,'workHour')))[0].comConfigValue;

      let eval1 = eval(response.rows.filter(e => Object.is (e.comConfigKey,'workHourUnit')));
      this.workHourUnit = eval1[0].comConfigValue;
      let amWorkDate = eval(response.rows.filter(e => Object.is(e.comConfigKey,'amWorkDate'))[0].comConfigValue)[0];
      let pmWorkDate = eval(response.rows.filter(e => Object.is(e.comConfigKey,'pmWorkDate'))[0].comConfigValue)[1];
      this.leavePrecautions = eval(response.rows.filter(e => Object.is(e.comConfigKey,'leavePrecautions')))[0].comConfigValue;
      this.leavePrecautions = this.leavePrecautions.replace("\"","").replace("\"","").split("；");

      this.defaultDate.push(amWorkDate +":00")
      this.defaultDate.push(pmWorkDate +":00")

    });

    //系统是否
    this.getDicts("sys_yes_no").then(response => {
      response.data.forEach( (val) => this.yesOrNo.push({'dictValue': eval(val.dictValue),'dictLabel': val.dictLabel}))
    });
    //获取部门列表
    listDept({parentId:'100'}).then(response => {
      response.data.forEach( (val) => this.department.push({'dictValue': val.deptId,'dictLabel': val.deptName}) )
    });

    //获取加班及年休假时长
    getHolsCheckInfo().then(response => {
      if(response.data){
        this.realOvertimeSurTime = this.form.overtimeSurTime = response.data.leaveRest
        this.realAnnLeaSurTime = this.form.annLeaSurTime = response.data.holsRestDays
      }
    });

  },
  methods: {

    setExportData(){
      let dataArray = [];
      this.leaveList.forEach((leave, i) => {
        const { leaveDates,leaveHours,deptId,leaveType,leaveReason,createTime,approvalStatus,curApprover  } = leave;
        var leaveFormat = {
          "请假时间":JSON.parse(leaveDates)[0][0][0],
          "请假时长":leaveHours,
          "部门":this.selectDictLabel(this.department, deptId),
          "请假类型": this.selectDictLabel(this.options, leaveType),
          "理由陈述":leaveReason,
          "申请时间":createTime,
          "状态":this.selectDictLabel(this.statusOptions, approvalStatus),
          "当前审批人":curApprover,
        }
        dataArray.push(leaveFormat)
      });

      return dataArray;
    },

    editRules(){
      if(!this.form.inPrjFlag){
        this.rules.leavePrjName[0].required = false
        this.form.leavePrjName = '';
      }else{
        this.rules.leavePrjName[0].required = true
      }
    },


    /** 查询日常工作列表 */
    getList() {
      this.loading = true;
      listLeave(this.addDateRange(this.queryParams, this.leaveDateRange)).then(response => {
        this.leaveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });

    },
    // 增加选择时间
    addDomain(index) {
      if(!this.form.leaveType){
        this.msgError("请选择请假类型！");
        this.form.dateRange = [[]];
        return
      }
      this.form.dateRange.push([]);
    }, // 移除选择时间
    removeDomain(index, item, btnFlag) {
      if(btnFlag) {
        if (index !== -1) {
          this.form.dateRange.splice(index, 1);
        }
      }

      if(Object.is(this.form.leaveType,'1')){
        if(Object.is(this.workHourUnit,'时')){
          this.form.overtimeSurTime += this.timeBackUp[index];
        }else if(Object.is(this.workHourUnit,'天')){
          this.form.overtimeSurTime += this.floatDiv(this.timeBackUp[index],this.workHour);
        }
      }
      if(Object.is(this.form.leaveType,'4')){
        // 年休假时长重新计算
        this.form.annLeaSurTime += this.floatDiv(this.form.leaveHours,this.workHour);
      }

      if(this.timeBackUp[index]){
        this.form.leaveHours -= this.timeBackUp[index];
        this.timeBackUp[index] = null;
      }

    },
    dateChange(item,index) {
      // if(item[0] == null || !item[0]){
      //   this.form.dateRange = [[]];
      //   this.form.leaveHours = null;
      //   return;
      // }

      if(!this.form.leaveType){
        this.msgError("请选择请假类型！");
        this.form.dateRange = [[]];
        return
      }
      let add = this.form.dateRange;

      if (add.length > 1) {
        let newArr = [];
        let time = 0;
        add.forEach((value, index) => {
          if (time == 0) {
            newArr.push(value);
          } else {
            value = value.filter(item => item !== undefined);
            newArr.push(value);
          }
          time += 1;
        });
        this.form.dateRange = newArr;
      }
      this.err_blo_hide = false;

      if(!item[0]){
        this.removeDomain(index, item, false)
      }

      if(this.form.dateRange){
        let timehour = this.calculateHours(this.form.dateRange,this.workHour,index);

        // 计算请假时长
        if(this.timeBackUp[index]){
          this.form.leaveHours -= this.timeBackUp[index];
        }
        this.form.leaveHours += timehour;

        //备份计算时长
        this.timeBackUp.splice(index,0,timehour)

        // 加班调休加班时长重新计算
        if(Object.is(this.form.leaveType,'1')){
          if(Number(this.form.leaveHours) > Number(this.form.overtimeSurTime)){
            this.msgError("请假时长不能大于调休时长！");
            this.form.leaveHours = 0;
            this.form.dateRange = [[]]
            return;
          }
          if(Object.is(this.workHourUnit,'时')){
            this.form.overtimeSurTime -= timehour;
          }else if(Object.is(this.workHourUnit,'天')){
            this.form.overtimeSurTime -= this.floatDiv(this.timeBackUp[index],this.workHour);
          }
        }

        if(Object.is(this.form.leaveType,'4')){
          if(Number(this.form.leaveHours) > Number(this.form.annLeaSurTime)){
            this.msgError("年休假时长不能大于调休时长！");
            this.form.leaveHours = 0;
            this.form.dateRange = [[]]
            return;
          }
          // 年休假时长重新计算
          this.form.annLeaSurTime -= this.floatDiv(this.form.leaveHours,this.workHour);
        }

      }

    },
    handleRowClick(row, column, event) {
      if (column.label == "操作") {
      } else {
        // 这里的弹框标题是动态获取
        this.detail = true;

        let name = this.$store.state.user.nickName;
        this.title = name + " / 请假申请查看";
        this.form = row;
        this.form.dateRange = eval(this.form.leaveDates)

        this.overleaveHoursShow(this.form.leaveType);

      }
    },

    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }

      this.form = {
          leaveId:'',
          inPrjFlag: true,
          leaveType: '',
          deptId: '',
          leavePrjName: '',
          leaveReason: '',
          leaveHours: 0,
          dateRange: [[]],
          saveFlag: true,
      }
      this.resetForm("form");

      this.timeBackUp = []
      this.err_blo_hide = false
      this.overtime_show = false
      this.annual_leave_show = false

      this.form.overtimeSurTime=this.realOvertimeSurTime
      this.form.annLeaSurTime=this.realAnnLeaSurTime

    },


    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.leaveDateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.leaveId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新建请假申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.$nextTick(function(){

        const { leaveId,leaveDates,deptId,inPrjFlag,leaveHours,leavePrjName,leaveReason,leaveType,overtimeSurTime,annLeaSurTime } = row;
        Object.assign(this.form, {
          dateRange: eval(leaveDates),
          leaveId,
          deptId,
          inPrjFlag,
          leaveHours,
          leavePrjName,
          leaveReason,
          leaveType,
          overtimeSurTime,
          annLeaSurTime
        });
        //重新计算并备份
        if(this.form.dateRange){
          for (let i = 0; i < this.form.dateRange.length; i++) {
            this.timeBackUp.push(this.calculateHours(this.form.dateRange,this.workHour,i));
          }
        }

        this.overleaveHoursShow(leaveType);

        this.editRules();

      })

      this.open = true;
      this.title = "编辑请假申请";
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

    // 下拉选择加班那个项展示与否
    selectChange(value) {

      if (value == 1) {
        this.clearDate()
        this.overtime_show = true;
        this.annual_leave_show = false;
        // if (this.form.leaveHours > this.form.overtimeSurTime) {
        //   this.msgError("请假时间应小于加班时长");
        // }

      } else if (value == 4) {
        this.clearDate()
        this.annual_leave_show = true;
        this.overtime_show = false;
        // if (this.form.leaveHours > this.form.annLeaSurTime) {
        //   this.msgError("请假时间应小于年休假时长");
        // }
      }else {
        this.overtime_show = false;
        this.annual_leave_show = false;
      }

    },

    //清空时间
    clearDate(){
      this.form.dateRange = [[]]
      this.form.leaveHours = 0;
      this.timeBackUp = [];
    },

    //保存请假提交
    save() {
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
                this.form.leaveDates = JSON.stringify(this.form.dateRange)

                if (this.form.leaveId) {
                  updateLeave(this.form).then(response => {
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
                  });
                }else{
                  addLeave(this.form).then(response => {
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
    delLeaves(row) {
      const leaveIds = row.leaveId || this.ids;
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
          delLeaves(leaveIds).then(response => {
            if (response.code === 200) {
              this.$confirm("删除成功", "删除成功", {
                  dangerouslyUseHTMLString: true,
                  showConfirmButton: false,
                  distinguishCancelAndClose: true,
                  cancelButtonText: "返回列表",
                  type: "success"
                })
                .catch(() => {
                  this.reset();
                  this.getList();
                });
            }
          });
        })
        .catch(() => {
          this.reset();
          this.getList();
        });
    },

    //报送请假
    handleReport(row) {
      const workIds = row.leaveId || this.ids;
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
          leaveSumbit(workIds).then(response => {
            if (response.code === 200) {
              this
                .$confirm("报送成功", "报送成功", {
                  dangerouslyUseHTMLString: true,
                  showConfirmButton: false,
                  distinguishCancelAndClose: true,
                  cancelButtonText: "返回列表",
                  type: "success"
                })
                .catch(() => {
                  this.reset();
                  this.getList();
                });
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
.Leave_time .el-form-item__label:before {
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
