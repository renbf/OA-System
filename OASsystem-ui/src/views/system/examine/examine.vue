<template>
  <div class="app-container step">
    <el-row :gutter="20">

      <!--信曾哥审批-->
      <el-col :span="24" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增审批</el-button>
          </el-col>
        </el-row>
        <!--        查询的form表单-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

          <el-form-item label="流程名称">
            <el-input
              v-model="queryParams.workflowName"
              placeholder="请输入流程名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--      列表-->
      <!--      可点击-->
      <template v-for="item in workflowList">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h2>{{item.workflowName}}</h2>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-setting" circle @click="Toedit(item.workflowId)"></el-button>
<!--              <el-button icon="el-icon-delete" circle></el-button>-->
            </div>
          </div>
          <div class="text item" @click.stop="Todetail(item.workflowId)">
            <p>
              <span>审批部门：</span>
              <span>{{item.workflowStepNum}}个</span>
            </p>
            <p>
              <span>审批概述：</span>
              <span>{{item.workflowDesc}}</span>
            </p>
          </div>
        </el-card>
      </template>
    </el-row>


    <!--    新建/编辑审批流程-->
    <el-dialog :title="title" :visible.sync="open"  width="600px">
      <el-form ref="addForm" :model="addForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="审批名称" prop="workflowName">
              <el-input v-model="addForm.workflowName" placeholder="请输入审批名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="审批概述" prop="workflowDesc">
              <el-input type="textarea" v-model="addForm.workflowDesc" placeholder="请输入审批概述" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-switch v-model="addForm.status" active-text="启用" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>

    <!--    查阅审批流程-->

    <el-dialog :title="detailtitle" :visible.sync="detailopen"  width="600px">
      <el-collapse v-model="activeNames" v-for="(item,index) in SysWorkflowStepList">
        <el-collapse-item :title="item.workflowStepName" :name="index">
          <template v-for="item1 in item.sysWorkflowNodes">
          <p>{{item1.workflowNodeName}}</p>
          <div>
            <span>参与人员</span>
            <span>
              <el-tag type="info" v-for="item2 in item1.sysWorkflowNodeCheckers">{{item2.workflowNodeUserName}}</el-tag>
            </span>
          </div>
          </template>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="edit">编辑</el-button>
      </div>
    </el-dialog>

<!--    编辑-->

    <!--    删除流程失败-->
    <el-dialog title="操作失败" :visible.sync="deleteopen" width="600px">
      <el-row>
        <el-form ref="form">
          <el-form-item label="失败原因">
            <el-input v-model="deletereason" disabled/>
          </el-form-item>
        </el-form>
      </el-row>
      <h4>未完成任务</h4>
      <el-row>
        <template>
          <el-table
            :data="deletetable"
            style="width: 100%"
            height="250"
          >
            <el-table-column
              prop="name"
              label="名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="people"
              label="角色">
            </el-table-column>
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="pageNum"
            :limit.sync="pageSize"
          />
        </template>
      </el-row>
    </el-dialog>


<!--    编辑审批流程-->
    <el-dialog :title="edittitle" :visible.sync="editopen"  width="600px">
      <el-collapse v-model="activeNames" v-for="item in SysWorkflowStepList">
        <el-collapse-item :name="item.workflowStepId">
          <template slot="title">
            <span style="float:left;">
              <b>{{item.workflowStepName}}</b>
            </span>
            <span style="float:right;margin: 0 -350px 0 auto;">
              <el-button type="primary" icon="el-icon-delete" circle @click.stop="deleteStep(item.workflowStepId)"></el-button>
              <el-button icon="el-icon-setting" circle @click.stop="openWorkflowNode(item)"></el-button>
            </span>
          </template>
          <template v-for="item1 in item.sysWorkflowNodes">
            <p>{{item1.workflowNodeName}}</p>
            <div>
              <span>参与人员</span>
              <span>
              <el-tag type="info" v-for="item2 in item1.sysWorkflowNodeCheckers">{{item2.workflowNodeUserName}}</el-tag>
              </span>
            </div>
          </template>

        </el-collapse-item>
      </el-collapse>
      <div class="add" @click="addonestep">
        <p>
          <span class="el-icon-plus" style="font-size:38px;"></span>
        </p>
        <p>新增</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <!--<el-button type="primary" @click="savestep">保存</el-button>-->
        <el-button type="primary" @click="closeEditopen">关闭</el-button>
      </div>
    </el-dialog>

<!--    新增、编辑流程节点-->
    <el-dialog :title="addsteptitle" :visible.sync="addstepopen"  width="600px">
      <h3>选择审批部门</h3>
      <el-row>
        <el-form ref="workflowStepForm" :model="workflowStepForm" :rules="workflowStepRules">
          <el-form-item label="审批部门">
            <el-select
              v-model="workflowStepForm.deptId"
              placeholder="请选择审批部门"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in bumenOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="savestep">保存</el-button>
        <el-button type="primary" @click="cancel">取消</el-button>
      </div>
    </el-dialog>

    <!--编辑流程节点-->
    <el-dialog :title="workflowStep.workflowStepName" :visible.sync="nodeOpen"  width="600px">
      <template v-for="item1 in workflowNodes">
        <div>
          <p>{{item1.workflowNodeName}}</p>
          <el-button type="primary" icon="el-icon-delete" circle @click.stop="deleteNode(item1)"></el-button>
          <el-button icon="el-icon-setting" circle @click.stop="editNodeOpen(item1)"></el-button>
        </div>
        <div>
          <span>参与人员</span>
          <span>
              <el-tag type="info" v-for="item2 in item1.sysWorkflowNodeCheckers">{{item2.workflowNodeUserName}}</el-tag>
            </span>
        </div>
      </template>
      <div class="add" @click="addOneNode">
        <p>
          <span class="el-icon-plus" style="font-size:38px;"></span>
        </p>
        <p>新增</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>
    <!--新增节点-->
    <el-dialog :title="addNodeTitle" :visible.sync="addNodeOpen"  width="600px">
      <el-form ref="addNodeForm" :model="addNodeForm" :rules="nodeFormRules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="审核步骤名称" prop="workflowNodeName">
              <el-input v-model="addNodeForm.workflowNodeName" placeholder="请输入审核步骤名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="审核人" prop="workflowNodeUserIds">
              <el-select v-model="addNodeForm.workflowNodeUserIds" placeholder="请选择" multiple @change="changeShenheren">
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitNodeForm">保 存</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listDept } from "@/api/system/dept";
  import testData from '@/assets/static/system/examine/testData.js';
  import { getWorkflowList,addWorkflow,addWorkflowStep,getWorkflowStepList,removeStep,addWorkflowNode,getSysWorkflowNodes,deleteWorkflowNode,editWorkflowNode } from "@/api/system/examine/workflowgroup";
  import { listUser } from "@/api/system/user";

  export default {
    name: "index",
    data(){
      return{
        workflowGroupId:this.$route.query.workflowGroupId,
        workflowId:null,
        // 查询条件
        queryParams:{
          workflowName:'',
          workflowGroupId:this.$route.query.workflowGroupId,
        },
        // 打开弹框
        open:false,
        // 查阅弹框
        detailopen:false,
        nodeOpen:false,
        // 编辑审批流程
        edittitle:'',
        // 新增步骤
        addstepopen:false,
        //新增节点
        addNodeOpen:false,
        addsteptitle:'',
        addNodeTitle:'',
        editopen:false,
        // 停用部门失败弹框
        erroropen:false,
        // 失败原因
        errorreason:'有未完成的申请与项目，需完成后方可操作',
        // 删除部门失败
        deleteopen:false,
        deletereason:'有未完成的申请与项目，需完成后方可操作',
        // 弹框标题
        title:'新建审批流程',
        // 查阅标题
        detailtitle:"查阅流程",
        // 折叠面板
        useperson:undefined,
        // 部门
        bumenOptions:[],
        // 添加审核步骤
        workflowStepForm: {
          deptId:null
        },
        // 表单
        addForm:{
          workflowName:'',
          workflowDesc:'',
          status:'1'
        },
        addNodeForm:{
          workflowNodeId:undefined,
          workflowStepNodeId:undefined,
          workflowNodeName:'',
          workflowNodeCheckerType:'',
          sortOrder:undefined,
          workflowNodeUserIds:[],
          workflowNodeRoleIds:[],
        },
        // 弹框折叠面板
        activeNames:[],
        SysWorkflowStepList: [],
        // 停用失败表格
        // 操作失败表格
        tableData: [{
          name:'迈克尔加班申请',
          time: '2016-05-02',
          people: '王小虎',
          type: '加班申请'
        },
          {
            name:'项目',
            time: '2016-05-02',
            people: '静静',
            type: '加班申请'
          },
          {
            name:'请假',
            time: '2016-05-02',
            people: '明明',
            type: '加班申请'
          },
        ],
        // 删除失败
        deletetable:[
          {
            name:'迈克尔加班申请',
            people: '王小虎',
          },
          {
            name:'项目',
            people: '静静'
          },
          {
            name:'请假',
            people: '明明'
          },
        ],
        // 总条数
        total:1,
        // 分页
        pageNum: 1,
        pageSize: 10,

        limitdata: [{
          id: 1,
          label: '工作管理',
          children: [{
            id: 4,
            label: '日常工作',
            children: [{
              id: 9,
              label: '三级 1-1-1'
            }, {
              id: 10,
              label: '三级 1-1-2'
            }]
          }]
        }, {
          id: 2,
          label: '我的部门',
          children: [{
            id: 5,
            label: '部门设置'
          }, {
            id: 6,
            label: '添加成员'
          }]
        }, {
          id: 3,
          label: '公司职员',
          children: [{
            id: 7,
            label: '二级 3-1'
          }, {
            id: 8,
            label: '二级 3-2'
          }]
        }],
        workflowList:[],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 表单校验
        rules: {
          workflowName: [
            {required: true, message: "审批名称不能为空", trigger: "blur"}
          ],
          workflowDesc: [
            {required: true, message: "审批概述不能为空", trigger: "blur"}
          ],
          status: [
            {required: true, message: "状态不能为空", trigger: "blur"}
          ]
        },
        nodeFormRules:{
          workflowNodeName: [
            {required: true, message: "审核步骤名称不能为空", trigger: "blur"}
          ],
          workflowNodeUserIds: [
            {required: true, message: "审批人不能为空", trigger: "blur"}
          ]
        },
        workflowStepRules: {
          deptId: [
            {required:true,message:'部门不能为空',trigger:'blur'}
          ]
        },
        // 权限list
        postOptions:[],
        workflowStep:{},
        //每个部门的流程节点
        workflowNodes: [],
        userList:[]
      }
    },
    created() {
      //获取部门列表
      listDept({parentId:'100'}).then(response => {
        response.data.forEach((val)=> this.bumenOptions.push({'dictValue': val.deptId,'dictLabel': val.deptName})
        )
      });
      this.getList();
      },
    methods:{
      getList(){
        //前端接口调用
        // this.workflowList = testData.workflowList;
        getWorkflowList(this.queryParams).then(response => {
          this.workflowList = response.rows;
        });
      },
      getStepList(workflowId){
        getWorkflowStepList({workflowId:workflowId}).then(response => {
          this.SysWorkflowStepList = response.data;
        });
      },
      getSysWorkflowNodes(workflowStepNodeId){
        getSysWorkflowNodes({workflowStepNodeId:workflowStepNodeId}).then(response => {
          this.workflowNodes = response.data;
        });
      },
      // 表单重置
      reset() {
        this.addForm = {
          workflowName:'',
          workflowDesc:'',
          status:'1'
        };
        this.resetForm("addForm");
      },
      resetAddNodeForm() {
        this.addNodeForm = {
            workflowNodeId:undefined,
            workflowStepNodeId:undefined,
            workflowNodeName:'',
            workflowNodeCheckerType:'',
            sortOrder:undefined,
            workflowNodeUserIds:[],
            workflowNodeRoleIds:[]
        };
        this.resetForm("addNodeForm");
      },
      // 新增审批
      handleAdd(){
        this.reset();
        this.open=true;
      },
      // 查询条件搜索
      handleQuery(){
        this.getList();
      },
      cancel() {

      },
      // 重置
      resetQuery(){},
      deleteStep(workflowStepId){
        removeStep(workflowStepId).then(response => {
          if (response.code === 200) {
            this.msgSuccess("删除流程步骤成功");
            this.getList();
            this.getStepList(this.workflowId);
          } else {
            this.msgError(response.msg);
          }
        });
      },
      // 新建编辑保存
      submitForm(){
        this.$refs.addForm.validate(valid => {
          if (valid) {
            let addForm = this.addForm;
            addForm.workflowGroupId = this.workflowGroupId;
            if (addForm.workflowId != undefined) {

            } else {
              addWorkflow(addForm).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      submitNodeForm(){
        this.$refs.addNodeForm.validate(valid => {
          if (valid) {
            let workflowNodes = this.workflowNodes;
            let addForm = this.addNodeForm;
            addForm.workflowNodeCheckerType = '2';
            let workflowStepNodeId = addForm.workflowStepNodeId;
            if (addForm.workflowNodeId != undefined) {
              editWorkflowNode(addForm).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.addNodeOpen = false;
                  this.getSysWorkflowNodes(workflowStepNodeId);
                  this.resetAddNodeForm();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addForm.sortOrder = workflowNodes[workflowNodes.length - 1].sortOrder;
              addWorkflowNode(addForm).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.addNodeOpen = false;
                  this.getSysWorkflowNodes(workflowStepNodeId);
                  this.resetAddNodeForm();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      // 新建保存取消
      closeEditopen(){
        this.editopen = false;
      },
      handleNodeClick(){},
      // 查看修改
      detailedit(){

      },
      // 编辑
      edit(){
        this.detailopen=false;
        this.editopen=true;
      },
      savestep(){
        this.$refs.workflowStepForm.validate(valid => {
          if (valid) {
            let form = this.workflowStepForm;
            form.workflowId = this.workflowId;
            if (form.workflowStepId != undefined) {

            } else {
              addWorkflowStep(form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增流程步骤成功");
                  this.addstepopen = false;
                  this.getList();
                  this.getStepList(this.workflowId);
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      // 新增步骤
      addonestep(){
        this.addstepopen=true;
        this.addsteptitle="新增流程节点"
      },
      // 新增节点
      addOneNode(){
        this.resetAddNodeForm();
        this.addNodeOpen=true;
        this.addNodeTitle="新增节点";
        this.addNodeForm.workflowStepNodeId = this.workflowStep.workflowStepNodeId;
        this.changeShenheren();
      },
      // 编辑
      Toedit(workflowId){
        this.editopen=true;
        this.workflowId = workflowId;
        this.getStepList(workflowId);
      },
      // 查看
      Todetail(workflowId){
        this.detailopen=true;
        this.workflowId = workflowId;
        this.getStepList(workflowId);
      },
      //打开流程节点编辑
      openWorkflowNode(item) {
        this.nodeOpen = true;
        this.workflowStep= item;
        this.getSysWorkflowNodes(item.workflowStepNodeId);
      },
      deleteNode(item) {
        deleteWorkflowNode({workflowNodeId:item.workflowNodeId}).then(response => {
          if (response.code === 200) {
            this.getSysWorkflowNodes(item.workflowStepNodeId);
          } else {
            this.msgError(response.msg);
          }
        });
      },
      editNodeOpen(item) {
        this.resetAddNodeForm();
        this.addNodeOpen=true;
        this.addNodeTitle="修改节点";
        this.addNodeForm = {
          workflowNodeId:item.workflowNodeId,
          workflowStepNodeId:item.workflowStepNodeId,
          workflowNodeName:item.workflowNodeName,
          sortOrder:item.sortOrder,
          workflowNodeCheckerType:item.workflowNodeCheckerType,
        };

        let sysWorkflowNodeCheckers = item.sysWorkflowNodeCheckers;
        if (item.workflowNodeCheckerType == 2) {
          let workflowNodeUserIds = [];
          sysWorkflowNodeCheckers.forEach((item,index,arr) =>{
            workflowNodeUserIds.push(item.workflowNodeUserId);
          });
          this.addNodeForm.workflowNodeUserIds = workflowNodeUserIds;
        }else if(item.workflowNodeCheckerType == 1) {
          let workflowNodeRoleIds = [];
          sysWorkflowNodeCheckers.forEach((item,index,arr) =>{
            workflowNodeRoleIds.push(item.workflowNodeRoleId);
          });
          this.addNodeForm.workflowNodeRoleIds = workflowNodeRoleIds;
        }
        this.changeShenheren();
      },
      changeShenheren() {
        listUser({deptId:this.workflowStep.deptId}).then(response => {
            this.userList = response.rows;
          }
        );
      }
    }
  }
</script>

<style>
  .step .box-card{
    width: 316px;
    height:184px;
    float: left;
    margin-left: 25px;
  }
  /*卡片*/
  .step .el-card__header{
    height: 75px;
    width: 100%;
  }
  .step .el-card__body{
    padding-top: 0;
  }
  /*不能点击文字颜色*/
  .step .el-input.is-disabled .el-input__inner{
    color:#666;
  }
  .step .el-card__body p span,.dept .el-card__body p{
    font-size: 14px;
  }
  .step .text p{
    text-align: left;
  }
  .step .clearfix:after{
    display: block;
    clear: both;
    content:""
  }
  .step .clearfix h2{
    margin-top: 0;
  }
  .step .clearfix{
    margin:10px 0 0 0;
  }
  .step .clearfix p,.role .clearfix h2{
    margin:0
  }
  .step .clearfix p{
    margin-top: 8px;
    color:#aaa;
    font-size: 12px;
  }

  .step .cantclick{
    background:#F2F2F2;
  }
  .step .add{
    border:1px solid #ddd;
    margin-top:20px;
  }
  .step .add p{
    text-align: center;
    margin-top: 3px;
    margin-bottom: 3px;
  }
</style>
