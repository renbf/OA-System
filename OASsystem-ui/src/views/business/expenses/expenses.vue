<template>
  <div class="app-container travel_container">
    <el-row :gutter="10"  class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建报销</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-message"
          size="mini"
          :disabled="multiple"
          @click="handleReport"
        >报送</el-button
        >
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--        >导出</el-button-->
<!--        >-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <export-import
          excel-title="费用报销列表"
          exp-function="json"
          :set-export-data="setExportData"/>
      </el-col>
      <el-col :span="1.5">
        <b>单位：元</b>
      </el-col>
    </el-row>
    <el-form :modal="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="申请时间" prop="time">
        <el-date-picker
          v-model="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="billStatus">
        <el-select v-model="queryParams.billStatus" placeholder="请选择状态">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-select v-model="queryParams.deptId" placeholder="请选择部门">
          <el-option
            v-for="dict in departmentOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="multipleTable"
      v-loading="loading"
      :data="expensesList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报销时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="项目"
        prop="projectName"
        :show-overflow-tooltip="true"
        width="200"
      />
      <el-table-column label="部门" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(departmentOptions, scope.row.deptId) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="总金额"
        prop="expensesTotal"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ selectDictLabelByType(GLOBAL.SYS_CHECK_STATUS, scope.row.billStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="当前审批人"
        prop="orginHandler"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">

          <!--  未报送  拒绝  按钮全部显示 -->
          <div v-if="scope.row.billStatus == 2 || scope.row.billStatus == -1">
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleUpdate(scope.row)" v-hasPermi="['business:reimburse:edit']">编辑</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleDelete(scope.row)" v-hasPermi="['business:reimburse:remove']">删除</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit-outline" @click.stop="handleReport(scope.row)" v-hasPermi="['business:reimburse:submit']">报送</el-button>
          </div>
          <!-- 通过 待审核  审批中  什么按钮都没有 -->
          <div v-else-if="scope.row.billStatus == 0 || scope.row.billStatus == 1 || scope.row.billStatus == 99 "></div>

        </template>
      </el-table-column>
    </el-table>
<!--    添加报销-->
    <el-dialog
      :title="title"
      @close="closeEven"
      :visible.sync="dialogVisible"
      width="600px" class="abow_dialog">

      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col>
            <el-form-item label="部门" prop="deptId">
              <el-col :span="7">
                <el-select
                  v-model="form.deptId"
                  placeholder="请选择"
                  clearable
                >
                  <el-option
                    v-for="(item, index) in departmentOptions"
                    :key="index"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  ></el-option>
                </el-select>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="在项目中" prop="inPrjFlag">
              <el-radio-group v-model="form.inPrjFlag"  @change="changeHandler">
                <el-radio
                  v-for="(dict,index) in yesOrNo"
                  :key="index"
                  :label="dict.dictValue"
                  :value="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
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
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="报销项目" prop="projectId">
              <el-select
                v-model="form.projectId"
                placeholder="选择报销项目"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="dict in projectOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="报销事由" prop="reimburseReason">
            <el-input v-model="form.reimburseReason" type="textarea" placeholder="请输入内容"></el-input>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div style="float:right">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="reimburseSave">保存</el-button>
            </div>
          </el-col>
        </el-row>


        <el-row>
          <el-col>
            <el-form-item label="报销项目">
              <el-button type="primary" icon="el-icon-plus" circle @click="addexpense"></el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <template v-if="travel">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in expenseList" >
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span  style="font-size: 16px;line-height: 35px;"><b>{{item.reimExpenseName}}</b></span>
                      <el-button icon="el-icon-delete"  style="float: right;" circle @click="deleteticket(item)"></el-button>
                    </div>
                    <div class="text item train" @click="editexpense(item)">
                      <div>
                        <p><b>{{item.reimburseReason}}</b></p>
                        <p>附件 {{item.fileNum}}张</p>
                      </div>
                      <div>
                        <p>￥<b>{{item.amount}}</b></p>
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
              <el-button type="primary" icon="el-icon-download" circle @click="downloadFile"></el-button>
              <span>共{{fillAllNum}}张</span>
            </el-form-item>
          </el-col>
        </el-row>
<!--        附件-->
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
      </el-form>


    </el-dialog>


<!--新建报销项目-->
    <el-dialog
      :title="expensetitle"
      :visible.sync="expense"
      width="600px" class="abow_dialog">

      <el-form ref="form" :model="expenseform" :rules="expenserules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="expenseform.reimExpenseName"></el-input>
        </el-form-item>
        <el-form-item label="报销事由">
          <el-input type="textarea" v-model="expenseform.reimburseReason"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="10">
            <el-form-item label="金额" prop="amount">
              <el-input  v-model="expenseform.amount" @input="transpormoneychange"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="票据数量" prop="billsNum">
              <el-input-number v-model="expenseform.billsNum" @input="handleChange" :min="0" label="描述文字"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上传附件" prop="fileList">
          <el-upload
            ref="upload"
            class="upload-demo"
            :headers="headers"
            :action= "GLOBAL.UPLOADFILE_URL"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :auto-upload="true"
            :file-list="expenseform.fileList"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <span>共{{fujiannum}}张</span>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <span class="lf"><b style="font-size: 18px;">￥{{expenseform.amountTotal}}</b></span>
        <el-button @click="expense=false">取消</el-button>
        <el-button type="primary" @click="saveexpense">保存</el-button>
      </div>
    </el-dialog>
<!--查看-->
    <el-dialog
      :title="expensedetailtitle"
      :visible.sync="expensedetail"
      width="800px" class="abow_dialog">
      <el-row>
        <el-col :span="8" v-show="billTracesFlag">
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
                  v-for="(item, index) in departmentOptions"
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
            <!--        报销项目-->
            <el-row>
              <el-col>
                <el-form-item label="报销项目">
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>

              <el-col :span="24">
                <template v-if="travel">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in expenseList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;line-height: 35px;"><b>{{item.reimExpenseName}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p><b>{{item.reimburseReason}}</b></p>
                            <p>附件 {{item.fileNum}}张</p>
                          </div>
                          <div>
                            <p>￥<b>{{item.amount}}</b></p>
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
            <el-row>
              <el-col>
                <el-form-item label="附件">
                  <el-button type="primary" icon="el-icon-download" circle @click="downloadFile"></el-button>
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
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <span class="lf"><b>￥{{amountTotalAll}}</b></span>
      </span>
    </el-dialog>



  </div>
</template>

<script>
  import {expensesList,loadAll} from "@/api/business/mywork/expenses"
  import { billSumbit,listReimburse, getReimburse, delReimburse, addReimburse, updateReimburse, exportReimburse,getRemburseDetail } from "@/api/business/mywork/reimburse";
  import { listExpense, getExpense, delExpense, addExpense, updateExpense, exportExpense } from "@/api/business/mywork/expense";
  import { getToken } from '@/utils/auth'
  import {uploadFile,delFile} from '@/api/system/file'
  import {isNotEmpty} from "../../../utils/common";

  export default {
        name: "expenses",
        data(){
          return{
            headers: { 'Authorization': 'Bearer ' + getToken() },
            datetime:[],
            queryParams:{
              deptId:undefined,
              billStatus:undefined,
              reimburseType :'expenses'
            },
            expensesList:[],
            dateRange: [],
            departmentOptions:[],
            statusOptions:[],
            workflowOptions:[],
            trafficTypeOptions:[],
          // 遮罩层
            loading: true,
            dialogVisible: false,
            expense:false,
            beaway:false,
            otheropen:false,
            value1:'',
            value2:'',
            travel:true,
            // 非多个禁用
            multiple: true,
            butie:true,
            other:true,
            fujian:true,
            expensedetail:false,
            fujiannum:0,
            expensemoney:0,
            expensenum:0,
            amountTotalAll:0,
            beawayTotal:0,
            otherTotal:0,
            yesOrNo: [],
            fileList: [],
            fileIds:[],
            fillAllNum:0,
            uploadForm: new FormData(),
            form:{
              workflowId:'',
              reimburseId:'',
              deptId: '',
              inPrjFlag: true,
              projectId:'',
              projectName:'',
              reimburseReason: '',
            },
            projectOptions: [
              {
                dictValue: "0",
                dictLabel: "小额贷款项目"
              },
              {
                dictValue: "1",
                dictLabel: "网信办项目"
              }
            ],
            delfileIds:[],
            expenseform:{
              fileNum:0,
              reimburseId: '',
              reimExpenseName:'',
              reimburseReason:'',
              amount:0,
              billsNum:1,
              amountTotal:0,
              fileIds:[],
              fileList: []
            },
            detailform:{},
            title:"",
            expensetitle:"",
            beawaytitle:"",
            othertitle:"",
            expensedetailtitle:"",
            expenseList:[],

            // 附件

          fujianList:[
            // {url:'http://localhost/dev-api/profile/file/20201028/3388080a391e9922c0b458d31f38b5d7.jpg'}
          ],
            //部门label
            department: [
              {
                dictValue: "1",
                dictLabel: "技术部"
              },
              {
                dictValue: "2",
                dictLabel: "软件部"
              }
            ],
            billTracesFlag: true,
            activities: [],
            expenserules:{
              trafficType: [
                { required: true, message: "交通类型不能为空", trigger: "blur" }
              ],
              trafficDate:[
                { required: true, message: "时间不能为空", trigger: "blur" }
              ],
              departureStation:[
                { required: true, message: "起始位置", trigger: "blur" }
              ],
              terminalStation:[
                { required: true, message: "结束位置", trigger: "blur" }
              ],
              amount:[
                { required: true, message: "金额", trigger: "blur" }
              ],
              billsNum:[
                { required: true, message: "票据数量", trigger: "blur" }
              ],
              fileNum:[
                { required: true, message: "附件", trigger: "blur" }
              ]
            },
          }
        },
        created() {
          this.getList();
          // 状态
          this.getDicts("sys_check_status").then(response => {
            this.statusOptions = response.data;
          });
          //系统是否
          this.getDicts("sys_yes_no").then(response => {
            response.data.forEach( (val) => this.yesOrNo.push({'dictValue': eval(val.dictValue),'dictLabel': val.dictLabel}))
          });
          //获取部门列表
          this.getDeptList({parentId:'100'}).then(response => {
            response.data.forEach((val)=> this.departmentOptions.push({'dictValue': val.deptId,'dictLabel': val.deptName}))
          });

          this.getWorkflowList({workflowGroupId:this.GLOBAL.EXPENSE_WORKFLOWID}).then(response => {
            this.workflowOptions = response.rows;
          });


        },
        methods:{
          setExportData(){
            let dataArray = [];
            this.expensesList.forEach((data, i) => {
              const { createTime,projectName,deptId,expensesTotal,billStatus,orginHandler  } = data;
              var format = {
                "报销时间":createTime,
                "项目":projectName,
                "部门":this.selectDictLabel(this.departmentOptions, deptId),
                "总金额":expensesTotal,
                "状态":this.selectDictLabel(this.statusOptions, billStatus),
                "当前审批人":orginHandler,
              }
              dataArray.push(format)
            });

            return dataArray;
          },
            closeEven(){
              this.getList()
            },
            getList(){
              this.loading = true;
              listReimburse(this.addDateRange(this.queryParams, this.datetime)).then(response => {
                this.expensesList = response.rows;
                this.total = response.total;
                this.loading = false;
              });
            },
            // 新建
            handleAdd(){
              this.dialogVisible=true;
              this.reset();
            },

            downloadFile(){
              this.filesToZip(this.fujianList,"fileall");
            },


            reimburseSave(){
              this.projectOptions.forEach(e=>{
                if(Object.is(e.dictValue,this.form.projectId)){
                  this.form.projectName = e.dictLabel;
                }
              })

              this.form.reimburseType = 'expenses';

              addReimburse(this.form).then(response => {
                if (response.code === 200) {
                  this.form.reimburseId = response.data;
                  this.msgSuccess("保存成功");
                } else {
                  this.msgError(response.msg);
                }
              });

            },

          saveexpense(){
              this.fileIds = JSON.stringify(this.fileIds);
              if(isNotEmpty(this.fileIds)){
                this.expenseform.fileIds = this.fileIds.replace("[","").replace("]","")
              }


              if(isNotEmpty(this.expenseform.trafficDate)){
                this.expenseform.trafficStartDate = this.expenseform.trafficDate[0];
                this.expenseform.trafficEndDate = this.expenseform.trafficDate[1];
              }

              this.expenseform.reimburseId = this.form.reimburseId;
              this.expenseform.fileNum = this.fujiannum

            addExpense(this.expenseform).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("保存成功");
                  this.delFiles()
                  this.getListExpense({'reimburseId':this.form.reimburseId});
                  this.expense = false;

                } else {
                  this.msgError(response.msg);
                }
              });

          },


          getListExpense(reimburseId){
            listExpense(reimburseId).then(response => {
              this.expenseList = response.rows;
            });
          },



          delFiles(){
              if(isNotEmpty(this.delfileIds)){
                this.delfileIds.forEach(e=>{
                  delFile(e)
                })
              }
          },

          // 删除
          handleDelete(row){
            const reimburseIds = row.reimburseId || this.ids;
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
                delReimburse(reimburseIds).then(response => {
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
          //报送请假
          handleReport(row) {
            const reimburseIds = row.reimburseId || this.ids;
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
                billSumbit(reimburseIds).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("报送成功");
                    this.reset();
                    this.getList();
                  }
                })
              })
              .catch(() => {
                this.reset();
                this.getList();
              });
          },
          // 导出
          handleExport(){},
          // 编辑
          handleUpdate(row){
            this.form = row;
            this.dialogVisible=true;

            getRemburseDetail(row.reimburseId,row.reimburseType).then(response => {
              this.expenseList = response.data.busiReimExpenseList;
              //获取全部文件信息
              this.getAllFileList(response.data)
            });
          },
          // 行点击
          handleRowClick(row){
            this.expensedetail=true;
            this.expensedetailtitle='查看'
            this.form = row;
            getRemburseDetail(row.reimburseId,row.reimburseType).then(response => {
              this.expenseList = response.data.busiReimExpenseList;
              this.amountTotalAll =  response.data.amountTotal;
              //获取全部文件信息
              this.getAllFileList(response.data)
            });


            //查看流程节点信息
            this.getBillTraces(this.form.reimburseId,this.form.workflowId).then(response => {
              if (response.code === 200) {
                this.activities = response.data;
                if(!isNotEmpty(this.activities)){
                  this.billTracesFlag = false;
                }
                this.activities.forEach( e=>{
                  e.checkRemarks = e.checkRemarks ? e.checkRemarks : "审核通过"
                  e.type = 'success'
                  e.icon = "el-icon-check"
                  e.timestamp = e.checkerUserName + "(" + e.checkerDeptName+ ")" + this.parseTime(e.createTime)
                })

              } else {
                this.msgError(response.msg);
              }
            });
          },

          //获取全部文件信息
          getAllFileList(allInfo){
            this.fillAllNum = 0;
            this.fujianList = [];
            this.setFujianList(allInfo.busiReimExpenseList)

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

          handleSelectionChange(selection){
            this.ids = selection.map(item => item.reimburseId);
            this.multiple = !selection.length;
          },
          // 搜索
          handleQuery(){
            this.queryParams.pageNum = 1;
            this.getList();
          },
          // 重置
          resetQuery(){
            this.datetime = []
            this.queryParams={
                deptId:undefined,
                billStatus:undefined
            },
            this.handleQuery();
          },
          // 删除文件
          handleRemove(file, fileList) {
            this.delfileIds.push(file.id)
            this.fujiannum -= 1;
            let fileIndex = this.fileIds.indexOf(file.id);
            this.fileIds.splice(fileIndex, 1);
          },
          handleSuccess(response, file, fileList){
            this.fujiannum += 1;
            this.fileIds.push(response.fileId)
          },
          changeHandler(){},

          editexpense(data){
            let {fileIds } = data;
            this.expenseform = data;

            this.getFileInfo(this.expenseform,fileIds)

            this.expense=true
          },


          getFileInfo(form,fileIds){

            if(isNotEmpty(fileIds)){
              if(fileIds instanceof Array){
                fileIds = JSON.stringify(fileIds).replace("[","").replace("]","")
              }

              let fileIdStr = fileIds.split(',');
              this.fileIds =  fileIdStr.map(Number);
              this.fujiannum = this.fileIds.length;

              if(isNotEmpty(form.fileList)){
                form.fileList.forEach(e=>{
                  e.name = e.fileNameReal
                  e.url =process.env.VUE_APP_BASE_API + e.previewPath;
                })
              }
            }

          },

          // 删除报销项目
          deleteticket(data){
            this.$confirm(
              "请确认是否删除",
              {
                dangerouslyUseHTMLString: true,
                distinguishCancelAndClose: true,
                confirmButtonText: "删除",
                cancelButtonText: "返回",
                type: "warning"
              }
            ).then(() => {
              delExpense(data.reimExpenseId).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("删除成功");
                    this.getListExpense(data.reimburseId);
                  }
                });
              }).catch(() => {
              this.getListExpense(data.reimburseId);
              });
          },


          addexpense(){

            if(!isNotEmpty(this.form.reimburseId)){
              this.msgWarning('请先保存报销基础信息！');
              return
            }

            this.expense=true
            this.expensetitle='新增'
            this.fujiannum = 0;
            this.fileIds = []

            this.resetexpense();
          },

          resetexpense(){
            this.expenseform={
              fileNum:0,
              reimburseId: '',
              reimExpenseName:'',
              reimburseReason:'',
              amount:0,
              billsNum:1,
              amountTotal:0,
              fileIds:[],
              fileList: []
            }

          },

          reset() {
            this.expenseList = []
            this.fujianList = []
            this.fillAllNum = 0
            this.form={
              workflowId:'',
              reimburseId:'',
              deptId: '',
              inPrjFlag: true,
              projectId:'',
              projectName:'',
              reimburseReason: '',
            }
          },

          // 搜索建议
          querySearch(queryString, cb) {
            var restaurants = loadAll();
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
          },
          createFilter(queryString) {
            return (restaurant) => {
              return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
          },

          handleSelect(item) {
            console.log(item);
          },
          handleChange(value){
            this.expensenum=value;
            this.expenseform.amountTotal=this.expensemoney*this.expensenum;
          },
          transpormoneychange(value){
            this.expensemoney=value;
            this.expenseform.amountTotal=this.expensemoney*this.expensenum;
          },
          handlePreview(file) {
            console.log(file);
          },
          cancelexpense(){},
        }
    }
</script>

<style>
  .train{
    display: flex;
    justify-content:space-around
  }
  .train p{
    text-align: center;
  }

  .travel_container .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .travel_container .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .travel_container .el-carousel__indicators--horizontal{
    display: none;
  }
  .lf{
    float:left;
  }
  .travel_container .el-card{
    height: 200px;
  }
  .travel_container .el-card__header{
    padding:0;
  }
  .travel_container .el-card__header .clearfix{
    margin:10px;
  }
  .travel_container .el-carousel__item{
    width: 62%;
  }
  .travel_container .el-carousel__item,.el-card{
    border-radius: 20px;
  }
  .travel_container .el-carousel__arrow--left {
    left: 74px;
  }
  .travel_container .box-card b{
    font-size: 16px;
  }
  .travel_container .box-card{
    width: 100%;
  }
  .travel_container .el-button--medium.is-circle{
    padding:6px;
  }
  .el-dialog{
    height: 800px;
    overflow: scroll;
  }
</style>
