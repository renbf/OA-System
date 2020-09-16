<template>
  <div class="app-container system">
    <el-row :gutter="20">

      <!--      列表-->
    <!--      每日工作时间-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>每日工作时间</h3>
              <p>设置公司每日起始与结束时间</p>
            </div>
            <div style="float: right">
<!--              <h3>时长</h3>-->
              <b style="font-size:28px;" >{{this.workduringForm.workduring}}</b>小时
            </div>
          </div>
          <div class="text item"  @click="worktimeadd">
            <div class="lf">
              <p>起始时间</p>
              <p><b style="font-size:28px;">{{beginSr}}</b></p>
            </div>
            <div class="lf" style="margin-left: 50px;">
              <p>至</p>
            </div>
            <div class="lf" style="margin-left: 50px;">
              <p>结束时间</p>
              <p><b style="font-size:28px;">{{endSr}}</b></p>
            </div>
          </div>
        </el-card>
      </template>

      <!--       晚上加班时间段-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>每日加班时间段</h3>
              <p>设置公司晚上加班时间段范围</p>
            </div>
          </div>
          <div class="text item"  @click="workoverPeriod">
            加班时长规定：1、加班时长根据每个时段进行区分并自动计算加班时长。
          </div>
        </el-card>
      </template>
<!--       年休假天数-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>年休假天数</h3>
              <p>设置公司年休假天数</p>
            </div>
            <!--            <div style="float: right">-->
            <!--              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>-->
            <!--            </div>-->
          </div>
          <div class="text item"  @click="workyear">
            <p>
              年休假
            </p>
            <p>
              <b style="font-size:28px;">{{workyearForm.annualLeave}}</b>天
            </p>
          </div>
        </el-card>
      </template>

<!--      请假规定-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>请假规定</h3>
              <p>设置公司请假规定内容</p>
            </div>
            <!--            <div style="float: right">-->
            <!--              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>-->
            <!--            </div>-->
          </div>
          <div class="text item" @click="workleave">
            {{askleaveForm.leavePre}}
          </div>
        </el-card>
      </template>
<!--      加班规定-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>加班规定</h3>
              <p>设置公司加班规定内容</p>
            </div>
            <!--            <div style="float: right">-->
            <!--              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>-->
            <!--            </div>-->
          </div>
          <div class="text item" @click="addovertime">
            {{overtimeForm.overtime}}
          </div>
        </el-card>
      </template>
    </el-row>

<!--    设置每日工作时间-->
    <el-dialog title="设置每日工作时间" :visible.sync="worktimeopen"  width="600px">
      <el-form ref="form" :model="worktimeForm" :rules="worktimerule" label-width="80px">
        <el-row>
          <el-form-item label-width="120px" label="上午起止时间" prop="begintime">
            <el-time-picker v-model="worktimeForm.begintime" is-range format="HH:mm" value-format="HH:mm"
                            start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                            clearable></el-time-picker>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label-width="120px" label="下午起止时间" prop="endtime">
            <el-time-picker v-model="worktimeForm.endtime" is-range format="HH:mm" value-format="HH:mm"
                            start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                            clearable ></el-time-picker>
          </el-form-item>
        </el-row>

      </el-form>
      <el-collapse v-model="matters_needing_attention">
        <el-collapse-item title="工作时间设置注意事项" name="1">
          <div>
            1、“起始时间”与“结束时间”会直接影响“每日工作时长”
          </div>
          <div>
            2、正常工作日，加班申请时间超过“结束时间”及算入加班
          </div>
          <div>
            3、休息日，加班申请时间超过“起始时间”及算入加班
          </div>
          <div>
            4、每日工作时间不影响申请与审批，但会影响“出勤”中的“加班”
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('worktimeopen')">取 消</el-button>
        <el-button type="primary" @click="submitForm('worktime')">保 存</el-button>
      </div>
    </el-dialog>

    <!--    设置每日晚上加班时间段-->
    <el-dialog title="设置每日加班时间段" :visible.sync="overPeriodopen"  width="600px">
      <el-form ref="form" :model="overPeriodForm" label-width="80px">
        <el-row :span="24">
          <el-form-item label-width="120px" label="加班工时单位" prop="workHourUnit">
            <el-radio-group v-model="overPeriodForm.workHourUnit" size="medium">
              <el-radio v-for="(item, index) in workHourUnitOptions"
                        :key="index"
                        :label="item.value"
                        :disabled="item.disabled"
              >{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-row>
        <el-row>
          <div style="margin-top:10px" v-for="(item, index) in overPeriodForm.overPeriod" :key="index">
            <el-col :span="24">
              <el-form-item label-width="120px" label="加班时间段" prop="overPeriod">
                <el-time-picker v-model="item[0]" is-range format="HH:mm" value-format="HH:mm"
                                start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                                clearable :style="{width: '60%'}"></el-time-picker>
                <el-input v-model="overPeriodForm.overDay[index]" placeholder="对应天数" clearable :style="{width: '20%'}" v-show="overDayShow"/>
                <el-button type="primary" icon="el-icon-plus" circle @click="addDomain" v-show="index == 0 ? true : datePickerShow"></el-button>
                <el-button type="danger" icon="el-icon-delete" circle @click.prevent="removeDomain(index, item)" v-show="index == 0 ? false : datePickerShow"></el-button>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
      </el-form>
      <el-collapse v-model="matters_needing_attention">
        <el-collapse-item title="加班时长设置注意事项" name="1">
          <div>
            1、加班时长根据每个时段进行区分并自动计算加班时长
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('overPeriodopen')">取 消</el-button>
        <el-button type="primary" @click="overPeriodSubmitForm()">保 存</el-button>
      </div>
    </el-dialog>

<!--    设置年休假天数-->
    <el-dialog title="设置年休假天数" :visible.sync="workyearopen"  width="600px">
      <el-form ref="form" :model="workyearForm" :rules="workyearrule" label-width="80px">
        <el-row>
          <el-form-item label="年休假" prop="annualLeave">
            <el-input v-model="workyearForm.annualLeave" aria-placeholder="请输入年休假天数"><i slot="suffix" style="font-style:normal;margin-right: 10px;"> /天 </i></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <el-collapse v-model="workyear_attention">
        <el-collapse-item title="年休假设置注意事项" name="1">
          <div>
            1、设置好“年休假天数”后，员工在发起请假申请-年休假申请时，会从设置天数内扣除，天数为“0”时则不能再次申请年休假
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('workyearopen')">取 消</el-button>
        <el-button type="primary" @click="submitworkyearForm">保 存</el-button>
      </div>
    </el-dialog>

<!--    设置请假规定-->
    <el-dialog title="设置请假规定" :visible.sync="askleaveopen"  width="600px">
      <el-form ref="form" :model="askleaveForm" :rules="askleaverule" label-width="80px">
        <el-row>
          <el-form-item label="请假规定" prop="leaverule">
            <el-input type="textarea" v-model="askleaveForm.leavePre" placeholder="请输入" :rows="8"></el-input>
            <p>注：设置完成后，公司请假的相关规定会显示在请假申请页面共人员查阅</p>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('askleaveopen')">取 消</el-button>
        <el-button type="primary" @click="submitleaveForm">保 存</el-button>
      </div>
    </el-dialog>

<!--    加班规定-->
    <el-dialog title="设置加班规定" :visible.sync="overtimeopen"  width="600px">
      <el-form ref="form" :model="overtimeForm" :rules="overtimerule" label-width="80px">
        <el-row>
          <el-form-item label="请假规定" prop="overtime">
            <el-input type="textarea" v-model="overtimeForm.overtime" placeholder="请输入" :rows="8"></el-input>
            <p>注：设置完成后，公司加班的相关规定会显示在加班申请页面共人员查阅</p>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('overtimeopen')">取 消</el-button>
        <el-button type="primary" @click="submitovertimeForm">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  // import testData from '@/assets/static/system/examine/testData.js';

  // 新增
  import { addtime ,addDuringtime,addyeartime,addleave} from "@/api/system/companysetting";
  import { listComConfig, addOverPeriodList,delComConfigs,addComConfigList,addComConfig,updateComConfigList  } from "@/api/system/comconfig";
  import { toHourDifference } from '@/utils/common'
  import { camelCase } from '../../../utils'

  export default {
    name: "index",
    data(){
      return{
        form: {
          workHourTotal: undefined,
          workHourUnit: '时',
        },
        workHourUnitOptions: [{
          "label": "时",
          "value": "时"
        }, {
          "label": "天",
          "value": "天"
        }],



        // 设置每日工作时间弹框打开
        comConfigIds:[],
        worktimeopen:false,
        // 设置每日工作时间表单
        worktimeForm:{
          begintime: ["08:30", "12:00"],
          endtime: ["13:30", "17:30"],
        },
        beginSr: '08:30',
        endSr: '17:30',
        beginId: '',
        endId: '',
        // 工作时间设置注意事项
        matters_needing_attention:'',
        // 每日工作时间验证
        worktimerule:{
          begintime: [
            { required: true, message: "起始时间", trigger: "blur" }
          ],
          endtime: [
            { required: true, message: "起始时间", trigger: "blur" }
          ],
        },
        // 设置每日工作时长
        workduringopen:false,
        workduring_attention:'',
        workHourId:"",
        workduringForm:{
          workduring:""
        },


        // 设置晚上加班时长
        overPeriodopen:false,
        datePickerShow: false,
        overDayShow :false,
        overPeriodId: '',
        workHourUnitId: '',
        overDayId: '',
        overPeriodForm:{
          overPeriod:[[]],
          overDay: [],
          workHourUnit: '时',
        },
        workHourUnitOptions: [{
          "label": "时",
          "value": "时"
        }, {
          "label": "天",
          "value": "天"
        }],
        overPeriodrule:{
          workduring: [
            { required: true, message: "请填写加班时长", trigger: "blur" }
          ]
        },

        // 设置年休假天数
        workyearopen:false,
        annualLeaveId:"",
        workyearForm:{
          annualLeave:""
        },
        workyearrule:{
          workduring: [
            { required: true, message: "请填写年休假天数", trigger: "blur" }
          ]
        },
        workyear_attention:"",

        // 请假规定
        askleaveopen:false,
        askleaveForm:{
          leavePre:''
        },
        askleaverule:{
          workduring: [
            { required: true, message: "请填写请假规定", trigger: "blur" }
          ]
        },

        // 设置加班规定
        overtimeopen:false,
        overtimeForm:{
          overtime:''
        },
        overtimerule:{
          workduring: [
            { required: true, message: "请填写加班规定", trigger: "blur" }
          ]
        },
      }
    },
    created() {
      this.getList();

    },
    watch: {
      'overPeriodForm.workHourUnit':{
        deep:true,
        handler(val){
          if(val=='天'){
            this.overDayShow=true
          }else{
            this.overDayShow=false
          }
        }
      }
    },
    methods:{

      getList(){
        listComConfig({}).then(res => {
          this.comConfigIds = res.rows.map(x => {return x.comConfigId})

          res.rows.forEach(item => {

            let leavePre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'leavePrecautions')))[0]
            this.askleaveForm.leavePre = leavePre.comConfigValue
            this.leavePreId = leavePre.comConfigId;

            let overtimePre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'overtimePrecautions')))[0]
            this.overtimeForm.overtime = overtimePre.comConfigValue
            this.overtimeId = overtimePre.comConfigId;

            let annualLeavePre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'annualLeave')))[0]
            this.workyearForm.annualLeave = annualLeavePre.comConfigValue;
            this.annualLeaveId = annualLeavePre.comConfigId;

            let begintimePre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'begintime')))[0]
            let begintimeArrary = eval(begintimePre.comConfigValue)
            this.worktimeForm.begintime = begintimeArrary
            this.beginSr = begintimeArrary[0]
            this.beginId = begintimePre.comConfigId;

            let endtimePre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'endtime')))[0]
            let endtimeArrary = eval(endtimePre.comConfigValue)
            this.worktimeForm.endtime = endtimeArrary
            this.endSr = endtimeArrary[1]
            this.endId = endtimePre.comConfigId;

            let workHourUnitPre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'workHourUnit')))[0]
            this.overPeriodForm.workHourUnit = workHourUnitPre.comConfigValue
            this.workHourUnitId = workHourUnitPre.comConfigId;

            let overPeriodPre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'overPeriod')))[0]
            this.overPeriodForm.overPeriod = eval(overPeriodPre.comConfigValue)
            this.overPeriodId = overPeriodPre.comConfigId;

            let overDayPre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'overDay')))[0]
            this.overPeriodForm.overDay = eval(overDayPre.comConfigValue)
            this.overDayId = overDayPre.comConfigId;

            let workHourPre = eval(res.rows.filter(e => Object.is(e.comConfigKey,'workHour')))[0].comConfigValue
            this.workduringForm.workduring = workHourPre.comConfigValue
            this.workHourId = workHourPre.comConfigValue

          })
        })



      },

      reset() {
        if (this.$refs.menu != undefined) {
          this.$refs.menu.setCheckedKeys([]);
        }
        this.resetForm("form");
      },

      // 点击新增每日工作
      worktimeadd(){
        //this.reset();
        this.getList();
        this.worktimeopen=true
      },
      // 提交每日工作时间表单
      submitForm(type){
        this.$refs["form"].validate(valid => {
          if (valid) {
            const paramArray = [];
            paramArray.push({comConfigId: this.beginId,comConfigName: '上午起止时间',comConfigKey: 'amWorkDate',comConfigValue: JSON.stringify(this.worktimeForm.begintime)})
            paramArray.push({comConfigId: this.endId,comConfigName: '下午起止时间',comConfigKey: 'pmWorkDate',comConfigValue: JSON.stringify(this.worktimeForm.endtime)})

            let amhours = toHourDifference(this.worktimeForm.begintime[0],this.worktimeForm.begintime[1]);
            let pmhours = toHourDifference(this.worktimeForm.endtime[0],this.worktimeForm.endtime[1]);
            paramArray.push({comConfigId: this.workHourId,comConfigName: '工时',comConfigKey: 'workHour',comConfigValue: amhours+pmhours})


            if(!this.beginId){
              this.addComConfigInfo(paramArray);
            }else{
              updateComConfigList(paramArray).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("更新成功");
                  this.worktimeopen = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              })
            }

          }
        })

      },

      overPeriodSubmitForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            const paramArray = [];
            paramArray.push({comConfigId: this.workHourUnitId,comConfigName: '加班工时单位',comConfigKey: 'workHourUnit',comConfigValue: this.overPeriodForm.workHourUnit})
            paramArray.push({comConfigId: this.overPeriodId,comConfigName: '晚上加班时间段',comConfigKey: 'overPeriod',comConfigValue: JSON.stringify(this.overPeriodForm.overPeriod)})

            if(Object.is(this.overPeriodForm.workHourUnit,'天')){
              paramArray.push({comConfigId: this.overDayId,comConfigName: '晚上加班时间段工时',comConfigKey: 'overDay',comConfigValue: JSON.stringify(this.overPeriodForm.overDay)})
            }

            addOverPeriodList(paramArray).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.overPeriodopen = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            })
          }
        })

      },

      addComConfigInfo(paramArray){
        addComConfigList(paramArray).then(response => {
          if (response.code === 200) {
            this.msgSuccess("新增成功");
            this.worktimeopen = false;
            this.getList();
          } else {
            this.msgError(response.msg);
          }
        })
      },

      // 取消
      cancel(type){
        if(type){
          switch (type) {
            case 'overtimeopen':
              this.overtimeopen = false;
              break;
            case 'askleaveopen':
              this.askleaveopen = false;
              break;
            case 'workyearopen':
              this.workyearopen = false;
              break;
            case 'overPeriodopen':
              this.overPeriodopen = false;
              break;
            case 'worktimeopen':
              this.worktimeopen = false;
              break;
          }
        }

      },
      // 设置年休假天数
      submitworkyearForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {

            const paramArray = [];
            paramArray.push({comConfigId: this.annualLeaveId,comConfigName: '年休假天数',comConfigKey: 'annualLeave',
                              comConfigValue: this.workyearForm.annualLeave})

            if(!this.annualLeaveId){
              this.addComConfigInfo(paramArray);
            }else{
              updateComConfigList(paramArray).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("更新成功");
                  this.workyearopen = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              })
            }

          }
        })
      },

      // 打开晚上加班时间段
      workoverPeriod(){
        //this.reset();
        this.getList();
        this.overPeriodopen=true
      },
      // 打开年休假弹框
      workyear(){
        //this.reset();
        this.getList();
        this.workyearopen=true
      },
      // 打开请假规定
      workleave(){
        //this.reset();
        this.getList();
        this.askleaveopen=true;
      },
      submitleaveForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {

            const paramArray = [];
            paramArray.push({comConfigId: this.leavePreId,comConfigName: '请假注意事项',comConfigKey: 'leavePrecautions',
              comConfigValue: this.askleaveForm.leavePre})

            if(!this.leavePreId){
              this.addComConfigInfo(paramArray);
            }else{
              updateComConfigList(paramArray).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("更新成功");
                  this.askleaveopen = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              })
            }

          }
        })
      },
      // 设置加班规定
      addovertime(){
        //this.reset();
        this.getList();
        this.overtimeopen = true;
      },
      submitovertimeForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {

            const paramArray = [];
            paramArray.push({comConfigId: this.overtimeId,comConfigName: '加班注意事项',comConfigKey: 'overtimePrecautions',
              comConfigValue: this.overtimeForm.overtime})

            if(!this.overtimeId){
              this.addComConfigInfo(paramArray);
            }else{
              updateComConfigList(paramArray).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("更新成功");
                  this.overtimeopen = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              })
            }
          }
        })
      },


      addDomain(index) {
        let arr = [];
        this.overPeriodForm.overPeriod.push(arr);
        this.datePickerShow = true;
      },
      removeDomain(index, item) {
        if(this.overPeriodForm.overDay){
          this.overPeriodForm.overDay.splice(index, 1);
        }

        let add = this.overPeriodForm.overPeriod;
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
          this.overPeriodForm.overPeriod = newArr;
          if (index !== -1) {
            this.overPeriodForm.overPeriod.splice(index, 1);
          }
        } else {
          if (index !== -1) {
            this.overPeriodForm.overPeriod.splice(index, 1);
          }
        }
      },


    }
  }
</script>

<style>
  .system .lf{
    float: left;
  }
  .system .box-card{
    width: 316px;
    height:184px;
    float: left;
    margin-left: 86px;
    margin-top: 30px;
  }
  /*卡片*/
  .system .el-card__header{
    height: 75px;
    width: 100%;
  }
  .system .el-card__body{
    height: 107px;
  }
  .system .el-card__body .item{height: 100%;overflow-y: hidden}
  /*不能点击文字颜色*/
  .system .el-input.is-disabled .el-input__inner{
    color:#666;
  }
  .system .el-card__body p span,.dept .el-card__body p{
    font-size: 14px;
  }
  .system .el-card__body p{
    text-align: center;
    margin: 9px 0;
  }
  .system .clearfix:after{
    display: block;
    clear: both;
    content:""
  }
  .system .clearfix{
    margin:0;
  }
  .system .clearfix p,.system .clearfix h3{
    margin:0
  }
  .system .clearfix p{
    margin-top: 8px;
    color:#aaa;
    font-size: 12px;
  }
  .clearfix>div{
    height: 75px;
  }

  .system .cantclick{
    background:#F2F2F2;
  }
</style>
