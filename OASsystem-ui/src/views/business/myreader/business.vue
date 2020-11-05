
<template>
  <div class="contain-leave">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="差旅费审批">
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
          excel-title="差旅费审批列表"
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
          <span>{{ selectDictLabel(projectNameOptions, scope.row.projectId) }}</span>
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
        label="交通费"
        prop="trafficFeeTotal"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="出差补贴"
        prop="travelSubsidyTotal"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="其他费用"
        prop="otherFeeTotal"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="总金额"
        prop="amountAllTotal"
        :show-overflow-tooltip="true"
      />

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
      width="800px" class="abow_dialog">
      <el-row>
        <el-col :span="8">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :timestamp="activity.timestamp"
            >
              <p>{{ activity.workflowNodeName }}</p>
              <p>{{ activity.checkRemarks }}</p>
            </el-timeline-item>
          </el-timeline>
        </el-col>
        <el-col :span="14">
          <el-form  ref="form" :model="form" label-width="80px">
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

            <el-form-item label="在项目中">
              <el-radio-group v-model="form.inPrjFlag"  >
                <el-radio
                  v-for="(dict,index) in yesOrNo"
                  :key="index"
                  :label="dict.dictValue"
                  :value="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>

            </el-form-item>
            <el-form-item label="报销项目">
              <el-input type="textarea" v-model="form.projectName" disabled></el-input>
            </el-form-item>
            <el-form-item label="报销事由">
              <el-input type="textarea" v-model="form.reimburseReason" disabled></el-input>
            </el-form-item>
            <!--        交通费-->
            <el-row>
              <el-col>
                <el-form-item label="交通费">
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>

              <el-col :span="24">
                <template v-if="travel">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in transportList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;margin-top: 10px;line-height: 35px;"><b>
                            {{ selectDictLabelByType(GLOBAL.TRANSPORT_TYPE, item.trafficType) }}
                          </b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p><b>{{item.departureStation}}</b></p>
                            <p>{{item.trafficStartDate}}</p>
                            <p>附件 {{item.fileNum}}张</p>
                          </div>
                          <div>
                            <p>至</p>
                          </div>
                          <div>
                            <p><b>{{item.terminalStation}}</b></p>
                            <p>{{item.trafficEndDate}}</p>
                            <p>￥<b>{{item.amountTotal}}</b></p>
                          </div>
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="出差补助">
                </el-form-item>
              </el-col>
            </el-row>
            <!--        出差补贴-->
            <el-row>

              <el-col :span="24">
                <template v-if="butie">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in reunTravcelList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;line-height: 35px;"><b>{{item.traSubName}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p>人数</p>
                            <p><b>{{item.peopleNum}}人</b></p>
                            <p>附件 {{item.fileNum}}张</p>
                          </div>
                          <div>
                            <p>天数</p>
                            <p><b>{{item.days}}天</b></p>
                          </div>
                          <div>
                            <p>补贴标准</p>
                            <p>￥<b>{{item.subsidyAmount}}</b></p>
                            <p>￥<b>{{item.amountTotal}}</b></p>
                          </div>
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
            </el-row>

            <el-row>
              <el-col>
                <el-form-item label="其他费用">
                </el-form-item>
              </el-col>
            </el-row>
            <!--        其他费用-->
            <el-row>
              <el-col :span="24">
                <template v-if="other">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in otherList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;line-height: 35px;"><b>{{item.otherFeeName}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p>附件 {{item.fileNum}}张</p>
                          </div>
                          <div>
                            <p>￥<b>{{item.amountTotal}}</b></p>
                          </div>
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="附件">
                  <span>共{{fillAllNum}}张</span>
                </el-form-item>
              </el-col>
            </el-row>
            <!--        附件-->
            <el-row>
              <el-col :span="24">
                <template v-if="fujian">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in fujianList">
                      <el-card class="box-card">
                        <div class="text item train">
                          <el-image
                            style="width: 100px; height: 100px"
                            :src="item.url"
                          ></el-image>
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
            </el-row>


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
                  @click="approvePass(form.reimburseId)"
                >通过</el-button
                >
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  icon="el-icon-close"
                  size="mini"
                  @click="approveRefuse(form.reimburseId)"
                >拒绝</el-button
                >
              </el-col>
            </el-row>


          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <span class="lf"><b>￥{{amountTotalAll}}</b></span>
      </span>
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

  import { approveList,getRemburseDetail } from "@/api/business/mywork/reimburse";
  import {isNotEmpty} from "../../../utils/common";

  export default {
    name: "page-leave",
    data(){
      return{
        detail: false,
        // 选中数组
        ids: [],
        title: '',
        yesOrNo: [],
        fujianList:[],
        activities: [],
        fujian:true,
        fillAllNum:0,
        butie:true,
        other:true,
        travel:true,
        departmentOption: [],
        leaveTypeOptions: [],
        statusOptions: [],
        // 非多个禁用
        multiple: true,
        checkRemarks: '',
        workHourUnit: '',
        annual_leave_show: false,
        overtime_show: false,
        amountTotalAll:0,
        // 总条数
        total: 0,
        // 查询参数
        approvalDate: '',
        queryParams: {
          deptId:undefined,
          billStatus:undefined,
          reimburseType :'travel',
          workflowId: this.GLOBAL.TRAVEL_EXPENSE_WORKFLOWID,
        },
        form:{
          workflowId:'',
          reimburseId:'',
          deptId: '',
          inPrjFlag: true,
          projectId:'',
          projectName:'',
          reimburseReason: '',
        },
        transportList:[],
        reunTravcelList:[],
        // 其他费用
        otherList:[],
        // 附件

        fujianList:[],
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

          const { applyTime,projectId,deptId,trafficFeeTotal,travelSubsidyTotal,otherFeeTotal,amountAllTotal,billStatus,orginHandler  } = data;
          var format = {
            "申请时间":applyTime,
            "申请人":orginHandler,
            "项目": this.selectDictLabel(this.projectNameOptions, projectId),
            "部门":this.selectDictLabel(this.departmentOption, deptId),
            "交通费": trafficFeeTotal,
            "出差补贴":travelSubsidyTotal,
            "其他费用":otherFeeTotal,
            "总金额":amountAllTotal,
            "状态":this.selectDictLabel(this.statusOptions, billStatus),
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
        this.ids = selection.map(item => item.reimburseId);
        this.multiple = !selection.length;
      },


      approvePass(reimburseId){
        if(!this.isNotEmpty(this.ids)){
          this.ids.push(reimburseId)
        }
        this.approveDo('1')
        this.detail=false;
      },

      //审核拒绝
      approveRefuse(reimburseId){
        if(!this.isNotEmpty(this.ids)){
          this.ids.push(reimburseId)
        }
        this.approveDo('0')
        this.detail=false;
      },

      approveDo(checkStatus){
        var para = {
          billIds: this.ids,
          workflowId: this.GLOBAL.TRAVEL_EXPENSE_WORKFLOWID,
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
        if(row.reimburseId){
          // 这里的弹框标题是动态获取
          this.detail = true;

          let name = row.orginHandler;
          this.title = name + " / 报销申请查看";
          this.form = row;
          getRemburseDetail(row.reimburseId,row.reimburseType).then(response => {
            this.transportList = response.data.busiReimTrafficFeeList;
            this.reunTravcelList = response.data.busiReimTravelSubsidyList
            this.otherList = response.data.busiReimOtherFeeList
            this.amountTotalAll =  response.data.amountTotal;
            //获取全部文件信息
            this.getAllFileList(response.data)
          });

          //查看流程节点信息
          this.getBillTraces(this.form.reimburseId,this.form.workflowId).then(response => {

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
      //获取全部文件信息
      getAllFileList(allInfo){
        this.fillAllNum = 0;
        this.fujianList = [];
        this.setFujianList(allInfo.busiReimTrafficFeeList)
        this.setFujianList(allInfo.busiReimTravelSubsidyList)
        this.setFujianList(allInfo.busiReimOtherFeeList)

        if(isNotEmpty(this.fujianList)){
          this.fujianList.forEach(e=>{
            e.name = e.fileNameReal
            e.url =process.env.VUE_APP_BASE_API + e.previewPath;
          })
        }
      },

      setFujianList(list){
        if(isNotEmpty(list)){
          list.forEach(e=>{
            if(isNotEmpty(e.fileList)){
              e.fileList.forEach(ee=>{
                this.fujianList.push(ee)
                this.fillAllNum += 1;
              })
            }
          })
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


  .train{
    display: flex;
    justify-content:space-around
  }
  .train p{
    text-align: center;
  }

  .abow_dialog .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .abow_dialog .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .abow_dialog .el-carousel__indicators--horizontal{
    display: none;
  }
  .lf{
    float:left;
  }
  .abow_dialog .el-card{
    height: 200px;
  }
  .abow_dialog .el-card__header{
    padding:0;
  }
  .abow_dialog .el-card__header .clearfix{
    margin:10px;
  }
  .abow_dialog .el-carousel__item{
    width: 80%;
  }
  .abow_dialog .el-carousel__item,.el-card{
    border-radius: 20px;
  }
  .abow_dialog .el-carousel__arrow--left {
    left: 74px;
  }
  .abow_dialog .box-card b{
    font-size: 16px;
  }
  .abow_dialog .box-card{
    width: 100%;
  }
  .abow_dialog .el-button--medium.is-circle{
    padding:6px;
  }
  .el-dialog__body{
    height: 800px ;
    overflow-y: scroll;
  }
  .abow_dialog .el-carousel__item--card{
    width: 62%;
  }
</style>
