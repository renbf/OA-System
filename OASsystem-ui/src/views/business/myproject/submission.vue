
<template>
  <div class="contain-little">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="OA项目开发-项目组申请">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="primary text" @click="handleProjectApplyOpen" v-if="projectInfo.projectProgress < 100"> <span class="el-icon-plus" style="margin-right:3px;"></span>新建申请</el-button>
      <el-button type="danger" @click="handleProjectApplyDel" v-if="projectInfo.projectProgress < 100"> <span class="el-icon-delete" style="margin-right:3px;"></span>删除</el-button>
      <el-button type="success"  @click.stop="submissionReport" v-if="projectInfo.projectProgress < 100"> <span class="el-icon-message" style="margin-right:3px;"></span >报送</el-button>
      <el-button type="warning"><span class="el-icon-download" style="margin-right:3px;"></span>导出</el-button>
    </div>

    <!--新建申请弹框一层-->
    <el-dialog
      :title="projectApplyTitle"
      :visible.sync="projectApplyOpen"
      width="30%"
      >
      <el-form ref="projectApplyForm" :model="projectApplyForm" :rules="projectApplyFormRules">
        <el-form-item prop="projectApplyTitle"><span>标题</span>
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="projectApplyForm.projectApplyTitle"
            maxlength="10"
            show-word-limit
            style="width:400px;margin-left:50px;"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="content"><span>申请内容</span>
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="projectApplyForm.content"
            style="width:400px;margin-left:20px;">
          </el-input></el-form-item>
        <el-form-item><span>审批人</span>

          <el-button type="primary" icon="el-icon-plus" circle size="small" style="margin-left:10px;" @click="submissionOpen1=true"></el-button>
          <span style="margin-left:10px;">注：审批顺序添加顺序依次审批</span>
        </el-form-item>
        <el-form-item style="padding:0 70px" >
          <el-tag
            :key="item.shenpiUserId"
            v-for="(item,index) in projectApplyForm.shenpiUserList"
            closable
            :disable-transitions="false"
            @close="handleClose(index)">
            {{item.shenpiUserName}}
          </el-tag>
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="projectApplySubmitForm(0)">保存</el-button>
    <el-button type="primary" @click="projectApplySubmitForm(1)">提交</el-button>
  </span>
    </el-dialog>

    <!--新建申请弹框二层-->
    <el-dialog
      title="添加审批人"
      :visible.sync="submissionOpen1"
      width="30%"
    >
      <el-form>
        <el-form-item><span>审批人</span>
          <!--<el-cascader :options="select" style="margin-left:20px;width:400px;"></el-cascader>-->
          <el-select v-model="shenpiUser.shenpiUserId" placeholder="请选择" ref="shenpiren">
            <el-option
              v-for="item in busiProjectMembers"
              :key="item.memberId"
              :label="item.memberName"
              :value="item.memberId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="submissionOpen1= false">取 消</el-button>
    <el-button type="primary" @click="dialog2SubmitForm">确 定</el-button>
  </span>
    </el-dialog>

    <!--table弹框-->
    <el-dialog
      title="审批项目申请"
      :visible.sync="submissionOpen2"
      width="40%"
    >
      <div style="height: 400px;width:200px;">
        <el-steps direction="vertical" :active="projectApplyForm.activeNum" finish-status="success">
          <!--<el-step title="丹尼尔" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="迈克尔" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="伊利斯" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="翠丝"  description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>-->
          <el-step :title="item.shenpiUserName"  :description="item.description" v-for="item in projectApplyForm.shenpiUserList"></el-step>

        </el-steps>
      </div>
      <div style="float:right;top:20px;right:50px;" class="dialogtext">
        <el-form ref="projectApplyForm" :model="projectApplyForm" label-width="80px">
          <el-form-item style="margin-top:90px;font-weight: bold">
            标题
            <el-input
              :disabled="true"
              style="width:350px;margin-left: 40px"
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="projectApplyForm.projectApplyTitle">
            </el-input>
            <div style="margin: 20px 0;"></div>

          </el-form-item>
          <el-form-item style="font-weight: bold">
            申请内容
            <el-input
              :disabled="true"
              style="width:350px;margin-left: 10px;margin-right:40px;"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入内容"
              v-model="projectApplyForm.content">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>


    <!--修改项目申请1层-->
    <el-dialog
      title="修改项目申请"
      :visible.sync="submissionUpdate"
      width="30%"
    >
      <el-form ref="UpdataForm" :model="UpdataForm"  >
        <el-form-item><span>标题</span>
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="UpdataForm.text"
            maxlength="10"
            show-word-limit
            style="width:400px;margin-left:50px;"
          >
          </el-input>
        </el-form-item>
        <el-form-item><span>申请内容</span>
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="UpdataForm.textarea2"
            style="width:400px;margin-left:20px;">
          </el-input></el-form-item>
        <el-form-item><span>审批人</span>

          <el-button type="primary" icon="el-icon-plus" circle size="small" style="margin-left:10px;" @click="submissionUpdate2=true"></el-button>
          <span style="margin-left:10px;">注：审批顺序添加顺序依次审批</span>
        </el-form-item>
        <el-form-item style="padding:0 70px" >
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>


        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="submissionSave">保存</el-button>
    <el-button type="primary" @click="submissionSubmit">提交</el-button>
  </span>
    </el-dialog>

    <!--修改项目申请2层-->
    <el-dialog
      title="添加审批人"
      :visible.sync="submissionUpdate2"
      width="30%"
      >
      <el-form ref="UpdataForm" :model="UpdataForm">
        <el-form-item><span>审批人</span>
          <el-cascader :options="UpdataForm.select" style="margin-left:20px;width:400px;"></el-cascader>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="submissionCancel">取 消</el-button>
    <el-button type="primary" @click="submissionSubmitForm">确 定</el-button>
  </span>
    </el-dialog>

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
      <el-button type="primary" style="margin-left:20px;" @click="searchButton"> <span class="el-icon-search"></span>搜索</el-button>
      <el-button style="margin-left:20px;"> <span class="el-icon-refresh-right"></span>重置</el-button>

    </div>

    <el-table
      :data="tableData"
      style="width: 100%;margin-top:20px;"
      @selection-change="handleSelectionChange"
      @row-click="handleLookOpen">
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
        prop="projectApplyTitle"
        label="标题"
        width="180">
      </el-table-column>
      <el-table-column
        prop="content"
        label="申请内容">
      </el-table-column>
      <el-table-column
        prop="shenpiUserNames"
        label="审批人"
      >
      </el-table-column>
      <el-table-column
        label="状态"
        width="130">
        <template slot-scope="scope">
          <span v-show="scope.row.status == 0">未报送</span>
          <span v-show="scope.row.status == 1">审批中</span>
          <span v-show="scope.row.status == 2">通过</span>
          <span v-show="scope.row.status == 3">拒绝</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="currentShenpiUserName"
        label="当前审批人">
      </el-table-column>
      <el-table-column
      prop="caozuo"
      label="操作">
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <span class="el-icon-edit-outline" v-show="scope.row.status == 0 && projectInfo.projectProgress < 100" @click.stop="handleUpdateProjectApply(scope.row)">编辑</span>
          <span class="el-icon-delete" v-show="scope.row.status == 0 && projectInfo.projectProgress < 100" @click.stop="delsubmission(scope.row)">删除</span>
          <span class="el-icon-message" style="margin-left:5px;" v-show="scope.row.status == 0 && projectInfo.projectProgress < 100" @click.stop="submissionReport(scope.row)" >报送</span>

        </template>

    </el-table-column>


    </el-table>
    <el-pagination
      style="margin-top:15px"
      @size-change="submission1Change"
      @current-change="submission2Change"
      :current-page="pageInfo.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total">
    </el-pagination>
  </div>
</template>


<script>
  import { getProjectInfo,addProjectApply,updateProjectApply,removeProjectApply,listProjectApply,listProjectApplyShenpi,baosongProjectApply } from "@/api/business/mywork/myproject";
  export default {
    name: "page-little",
    data(){
      return{
        projectId:this.$route.query.projectId,
        busiProjectMembers:[],
        textarea1:"项目任务延时申请",
        textarea3:"因功能修改需重新调整，需增加任务时间，故作此申请",
        inputVisible: false,
        submissionOpen:false,
        submissionOpen1:false,
        submissionOpen2:false,
        submissionUpdate:false,
        submissionUpdate2:false,
        dynamicTags: ['张三', '李四', '王五'],
        //新建项目审批注释
        annotation:"注：审批顺序添加顺序依次审批",
        //新建任务弹框布尔类型确认谈框
        projectApplyTitle: '',
        projectApplyOpen: false,
        projectApplyForm: {
          projectApplyId:undefined,
          projectId:this.projectId,
          projectApplyTitle:undefined,
          content:undefined,
          shenpiUserList:[],
          activeNum:0
        },
        projectApplyFormRules: {
          projectApplyTitle: [{required: true, message: "标题不能为空", trigger: "blur"}],
          content: [{required: true, message: "申请内容不能为空", trigger: "blur"}],
        },
        shenpiUser: {
          shenpiUserId:undefined,
          shenpiUserName:undefined
        },
        //陈述理由数据
        input: '',
        //新建申请数据
        submissionForm:{
          projectApplyTitle:"项目任务延时申请",
          content:"因功能修改需重新调整，需增加任务时间，故作此申请",
        },
        //编辑数据
        UpdataForm:{
          text: '',
          textarea2: '',
          select:[{
            value: 'ziyuan',
            label: '软件部',
            children: [{
              value: 'axure',
              label: '任宝峰'
            }, {
              value: 'sketch',
              label: '嘉琪'
            }, {
              value: 'jiaohu',
              label: '安仔'
            }]

          }],

        },


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
        value: '',
        //部门选择数据
        res: [{
          value: '选项1',
          label: '软件部'
        }, {
          value: '选项2',
          label: '销售部'
        }, {
          value: '选项3',
          label: '技术部'
        }, {
          value: '选项4',
          label: '开发部'
        }, ],
        //表格数据
        tableData: [],
        queryParams:{
          projectId:this.projectId,
          searchDate: [],
          content: undefined,
          status: undefined,
          page:1,
          limit:10
        },
        pageInfo: {},
        projectApplyIds: [],
        projectInfo: {},
      }

    },
    created() {
      this.getProject();
      this.getApplyList();
    },
    methods:{
      //删除
      submissionDelete(){
        this.$confirm('确认是否删除?', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      //报送
      submissionReport(item){
        this.$confirm('请确认是否提交报送？提交后不可进行修改', '提交报送', {
          confirmButtonText: "报送",
          cancelButtonText: "返回列表",
          type: 'warning'
        }).then(() => {
          let _this = this;
          let projectApplyIds = [];
          if (item != undefined && item.projectApplyId != undefined) {
            projectApplyIds = [item.projectApplyId];
          }else{
            projectApplyIds = _this.projectApplyIds;
          }
          baosongProjectApply({projectApplyIds:projectApplyIds}).then(response => {
            if(response.code == 200){
              this.getApplyList();
              this.msgSuccess("报送成功");
            }
          });
        }).catch(() => {
        });
      },
      // 导出
      submissionExport(){

      },
      // lookClose(done){
      //   this.$confirm('确认关闭？')
      //     .then(_ => {
      //       done();
      //     })
      //     .catch(_ => {});
      //
      // },
      //tag标签删除
      handleClose(index) {
        let _this = this;
        _this.projectApplyForm.shenpiUserList.splice(index, 1);
      },
      // 删除
      delsubmission(item) {
        this.$confirm('确认是否删除?', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeProjectApply({projectApplyIds:[item.projectApplyId]}).then(response => {
            if(response.code == 200){
              this.getApplyList();
              this.msgSuccess("删除成功");
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      submission(){
        this.submissionClick=true;
      },

      //添加批注人

      goBack(){
        let projectId = this.projectId;
        this.$router.push({ path:'/myproject/look',query:{projectId:projectId}})
      },
      //新建保存提交
     OpenSave(){
        this.submissionOpen=false
      },
      OpenSubmit(){
        this.submissionOpen=false
      },
      //新建取消确定
      OpenCancel(){
        this.submissionOpen1=false
      },
      OpenSubmitForm(){
        this.submissionOpen1=false
      },

      //编辑保存提交
      submissionCancel(){
        this.submissionUpdate2=false
      },
      submissionSubmitForm(){
        this.submissionUpdate2=false
      },
      submissionSave(){
        this.submissionUpdate=false
      },
      submissionSubmit(){
        this.submissionUpdate=false
      },
      //项目申请新增弹框
      handleProjectApplyOpen(){
        this.resetProjectApplyForm();
        this.projectApplyTitle = "新建项目申请";
        this.projectApplyOpen = true;
      },
      resetProjectApplyForm(){
        this.projectApplyForm = {
          projectApplyId:undefined,
          projectId:this.projectId,
          projectApplyTitle:undefined,
          content:undefined,
          shenpiUserList:[]
        }
        this.resetForm("projectApplyForm");
      },
      //项目申请编辑弹框
      handleUpdateProjectApply(item) {
        this.resetProjectApplyForm();
        this.projectApplyTitle = "编辑项目申请";
        this.projectApplyOpen=true;
        this.updateSetProjectApplyValue(item);
      },
      updateSetProjectApplyValue(item) {
        let _this = this;
        _this.projectApplyForm = {
          projectApplyId:item.projectApplyId,
          projectId:this.projectId,
          projectApplyTitle:item.projectApplyTitle,
          content:item.content,
          shenpiUserList:[]
        };
        listProjectApplyShenpi({projectApplyId:item.projectApplyId}).then(response => {
          if(response.code == 200){
            _this.projectApplyForm.shenpiUserList = response.data;
            _this.projectApplyForm.shenpiUserList.forEach((item)=>{
              let checkStatus = item.checkStatus;
              let isCurrent = item.isCurrent;
              let checkStatusText = '';
              if(checkStatus == '-1'){
                checkStatusText = '待审核';
              }else if(checkStatus == '1'){
                checkStatusText = '通过';
              }else if(checkStatus == '0'){
                checkStatusText = '拒绝';
              }
              if (isCurrent == '1') {
                _this.projectApplyForm.activeNum = item.sortOrder +1;
              }
              item.description = item.shenpiUserName + item.updateTime.substring(0,10) + checkStatusText;
            })
          }
        });
      },
      projectApplySubmitForm(status) {
        let _this = this;
        let msg = '';
        let title = '';
        if (status == '1') {
          msg = '确认报送?';
          title = '提交报送';
        }else{
          msg = '确认保存?';
          title = '项目申请保存';
        }
        this.$confirm(msg, title, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.$refs.projectApplyForm.validate(valid => {
            if (valid) {
              let form = _this.projectApplyForm;
              form.status = status;
              let length = form.shenpiUserList.length;
              if (length <= 0) {
                this.msgError("审批人不能为空");
                return false;
              }else{
                form.shenpiUserList.forEach((item, index) => {
                  item.sortOrder = index;
                });
              }
              if (form.projectApplyId != undefined) {
                updateProjectApply(form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.projectApplyOpen = false;
                    this.getApplyList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              } else {
                addProjectApply(form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("新增成功");
                    this.projectApplyOpen = false;
                    this.getApplyList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              }
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '操作异常'
          });
        });

      },
      //添加审批人
      dialog2SubmitForm() {
        let _this = this;
        let shenpiren = _this.$refs.shenpiren.selected.label;
        let length = _this.projectApplyForm.shenpiUserList.length;
        let shenpiUser = {
          shenpiUserId:_this.shenpiUser.shenpiUserId,
          shenpiUserName:shenpiren
        };
        _this.projectApplyForm.shenpiUserList.push(shenpiUser);
        _this.submissionOpen1 = false;
      },
      getProject() {
        let _this = this;
        getProjectInfo({projectId:_this.projectId}).then(response => {
          if(response.code == 200){
            let projectInfo = response.data;
            _this.projectInfo = projectInfo;
            _this.busiProjectMembers= projectInfo.busiProjectMembers;
          }
        });
      },
      getApplyList() {
        let _this = this;
        listProjectApply(_this.queryParams).then(response => {
          if(response.code == 200){
            _this.tableData= response.data;
            _this.pageInfo = response.pageInfo;
          }
        });
      },
      searchButton() {
        let _this = this;
        _this.queryParams.page = 1;
        this.getApplyList();
      },
      //每页多少条
      submission1Change(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.limit = val;
        this.getApplyList();
      },
      //第几页
      submission2Change(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.page = val;
        this.getApplyList();
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
      //批量删除
      handleProjectApplyDel() {
        let _this = this;
        this.$confirm('确认是否删除?', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeProjectApply({projectApplyIds:_this.projectApplyIds}).then(response => {
            if(response.code == 200){
              this.getApplyList();
              this.msgSuccess("删除成功");
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleLookOpen(row, column, event){
        this.submissionOpen2 = true;
        this.updateSetProjectApplyValue(row);
      }
    }
  }

</script>

<style lang="scss">
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  /*.submissionright{*/
    /*float:right;*/
    /*top:0;*/
    /*right:50px;*/
  /*}*/

  .dialogtext{
    position: absolute;

  }

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
