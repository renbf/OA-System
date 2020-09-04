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
            <!--            <div style="float: right">-->
            <!--              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>-->
            <!--            </div>-->
          </div>
          <div class="text item"  @click="worktimeadd">
            <div class="lf">
              <p>起始时间</p>
              <p><b style="font-size:28px;">08:30</b></p>
            </div>
            <div class="lf" style="margin-left: 50px;">
              <p>至</p>
            </div>
            <div class="lf" style="margin-left: 50px;">
              <p>结束时间</p>
              <p><b style="font-size:28px;">17:30</b></p>
            </div>
          </div>
        </el-card>
      </template>
    <!--每日工作时长-->
      <template>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>每日工作时长</h3>
              <p>设置公司每日工作时间长度</p>
            </div>
<!--            <div style="float: right">-->
<!--              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>-->
<!--            </div>-->
          </div>
          <div class="text item" @click="addDuring">
            <p>
              工作时长
            </p>
            <p>
              <b style="font-size:28px;">08</b>小时
            </p>
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
              <b style="font-size:28px;">08</b>天
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
            请假规定：1、加班申请当月必须及时提交，否则系统无法核算加班倒休定额；
            2、加班申请审批通过，系统确认有考勤记录，SAP自动核算加班倒休定额（核算规则为：加班申请时间段与考勤记录交集）；
            3、次月1日18:30起，员工不可以提交上月加班申请；
            4、次月1日24点前，必须完成加班申请审批，否则会导致您的加班异常。
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
            请假规定：1、加班申请当月必须及时提交，否则系统无法核算加班倒休定额；
            2、加班申请审批通过，系统确认有考勤记录，SAP自动核算加班倒休定额（核算规则为：加班申请时间段与考勤记录交集）；
            3、次月1日18:30起，员工不可以提交上月加班申请；
            4、次月1日24点前，必须完成加班申请审批，否则会导致您的加班异常。
          </div>
        </el-card>
      </template>
    </el-row>

<!--    设置每日工作时间-->
    <el-dialog title="设置每日工作时间" :visible.sync="worktimeopen"  width="600px">
      <el-form ref="form" :model="worktimeForm" :rules="worktimerule" label-width="80px">
        <el-row>
          <el-form-item label="起始时间" prop="begintime">
            <el-time-select
              v-model="worktimeForm.begintime"
              :picker-options="{
                                  start: '08:30',
                                  step: '00:15',
                                  end: '18:30'
                                }"
              placeholder="起始时间">
            </el-time-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="结束时间" prop="endtime">
            <el-time-select
              v-model="worktimeForm.endtime"
              :picker-options="{
                                  start: '08:30',
                                  step: '00:15',
                                  end: '18:30'
                                }"
              placeholder="结束时间">
            </el-time-select>
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
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>
<!--    设置每日工作时长-->
    <el-dialog title="设置每日工作时长" :visible.sync="workduringopen"  width="600px">
      <el-form ref="form" :model="workduringForm" :rules="workduringrule" label-width="80px">
        <el-row>
          <el-form-item label="工作时长" prop="workduring">
            <el-input v-model="workduringForm.workduring" aria-placeholder="请输入每日工作时长"><i slot="suffix" style="font-style:normal;margin-right: 10px;"> /小时 </i></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <el-collapse v-model="workduring_attention">
        <el-collapse-item title="工作时长设置注意事项" name="1">
          <div>
            1、“工作时长”会影响请假与加班的时间天数
          </div>
          <div>
            例：如“工作时长”为8小时，员工A申请8:30至12:00请假，及该员工请假半天。
          </div>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitworkduringForm">保 存</el-button>
      </div>
    </el-dialog>

<!--    设置年休假天数-->
    <el-dialog title="设置年休假天数" :visible.sync="workyearopen"  width="600px">
      <el-form ref="form" :model="workyearForm" :rules="workyearrule" label-width="80px">
        <el-row>
          <el-form-item label="年休假" prop="workduring">
            <el-input v-model="workyearForm.workduring" aria-placeholder="请输入年休假天数"><i slot="suffix" style="font-style:normal;margin-right: 10px;"> /天 </i></el-input>
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
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitworkyearForm">保 存</el-button>
      </div>
    </el-dialog>

<!--    设置请假规定-->
    <el-dialog title="设置请假规定" :visible.sync="askleaveopen"  width="600px">
      <el-form ref="form" :model="askleaveForm" :rules="askleaverule" label-width="80px">
        <el-row>
          <el-form-item label="请假规定" prop="leaverule">
            <el-input type="textarea" v-model="askleaveForm.leaverule" placeholder="请输入" :rows="8"></el-input>
            <p>注：设置完成后，公司请假的相关规定会显示在请假申请页面共人员查阅</p>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
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
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitovertimeForm">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  // import testData from '@/assets/static/system/examine/testData.js';

  // 新增
  import { addtime ,addDuringtime,addyeartime,addleave} from "@/api/system/companysetting";

  export default {
    name: "index",
    data(){
      return{
        // 设置每日工作时间弹框打开
        worktimeopen:false,
        // 设置每日工作时间表单
        worktimeForm:{
          begintime:'',
          endtime:''
        },
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
        workduringForm:{
          workduring:""
        },
        workduringrule:{
          workduring: [
            { required: true, message: "工作时长", trigger: "blur" }
          ]
        },

        // 设置年休假天数
        workyearopen:false,
        workyearForm:{
          workduring:""
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
          leaverule:''
        },
        askleaverule:{
          leaverule: [
            { required: true, message: "请填写请假规定", trigger: "blur" }
          ]
        },

        // 设置加班规定
        overtimeopen:false,
        overtimeForm:{
          overtime:''
        },
        overtimerule:{
          overtime: [
            { required: true, message: "请填写加班规定", trigger: "blur" }
          ]
        },
      }
    },
    created() {
    },
    methods:{
      // 点击新增每日工作
      worktimeadd(){
        this.worktimeopen=true
      },
      // 提交每日工作时间表单
      submitForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            addtime(this.worktimeForm).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.worktimeopen = false;
                } else {
                  this.msgError(response.msg);
                }
              })
          }
        })
      },
      // 点击打开工作时长弹框
      addDuring(){
        this.workduringopen=true;
      },
      // 提交设置每日工作时长
      submitworkduringForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            addDuringtime(this.workduringForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.workduringopen = false;
              } else {
                this.msgError(response.msg);
              }
            })
          }
        })
      },
      // 取消
      cancel(){},
      // 设置年休假天数
      submitworkyearForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            addyeartime(this.workyearForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.workyearopen = false;
              } else {
                this.msgError(response.msg);
              }
            })
          }
        })
      },
      // 打开年休假弹框
      workyear(){
        this.workyearopen=true
      },
      // 打开请假规定
      workleave(){
        this.askleaveopen=true;
      },
      submitleaveForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            addleave(this.askleaveForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.askleaveopen = false;
              } else {
                this.msgError(response.msg);
              }
            })
          }
        })
      },
      // 设置加班规定
      addovertime(){
        this.overtimeopen = true;
      },
      submitovertimeForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            addovertime(this.overtimeForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.overtimeopen = false;
              } else {
                this.msgError(response.msg);
              }
            })
          }
        })
      }
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
