<template>
  <div class="app-container dept">
    <el-row :gutter="20">

      <!--部门数据-->
      <el-col :span="24" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增部门</el-button>
          </el-col>
        </el-row>
<!--        查询的form表单-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

          <el-form-item label="部门名称">
            <el-input
              v-model="queryParams.deptName"
              placeholder="请输入部门名称"
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
      <template v-for="item in deptList">
        <el-card class="box-card" v-show="item.status == 0">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h2>{{item.deptName}}</h2>
              <p>{{item.createTime}}</p>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-setting" circle @click="updateDept(item)" ></el-button>
              <el-button icon="el-icon-user-solid" circle @click="settingrole(item)"></el-button>
              <el-button icon="el-icon-delete" circle @click="delDept(item)"></el-button>
            </div>
          </div>
          <div class="text item"  @click.prevent="addperson">
            <p>
              <span>部门主管：{{item.leader}}</span>
              <span>部门成员：{{item.nums}}人</span>
            </p>
            <p>
              部门概述：{{item.remark}}
            </p>
          </div>
        </el-card>

        <el-card class="box-card  cantclick" v-show="item.status == 1">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h2>{{item.deptName}}</h2>
              <p>{{item.createTime}}</p>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-setting" circle disabled></el-button>
              <el-button icon="el-icon-user-solid" circle disabled></el-button>
              <el-button icon="el-icon-delete" circle disabled></el-button>
            </div>
          </div>
          <div class="text item">
            <p>
              <span>部门主管：{{item.leader}}</span>
              <span>部门成员：{{item.nums}}人</span>
            </p>
            <p>
              部门概述：{{item.remark}}
            </p>
          </div>
        </el-card>
      </template>
    </el-row>


<!--    新建/编辑部门-->
    <el-dialog :title="title" :visible.sync="open"  width="600px">
      <el-form ref="deptForm" :model="deptForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="deptForm.deptName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="deptForm.parentId != 0">
          <el-col :span="24">
            <el-form-item label="上级部门" prop="parentId">
              <el-cascader
                v-model="deptForm.parentId"
                :options="deptOptions"
                :props="defaultProps"
                @change="handleChange">
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="部门概述" prop="deptName">
              <el-input type="textarea" v-model="deptForm.remark" placeholder="请输入部门概述" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-switch v-model="deptForm.status" active-text="启用" active-value="0" inactive-value="1"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-col :span="24">
          <el-form-item label="部门角色"  prop="roleId">
            <el-select v-model="deptForm.roleId" placeholder="请选择">
              <el-option
                v-for="item in roleOptions"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
                :disabled="item.status == 1"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>

    <!--    停用部门操作失败-->
    <el-dialog title="操作失败" :visible.sync="erroropen" width="600px">
      <el-row>
        <el-form ref="form">
          <el-form-item label="失败原因">
            <el-input v-model="errorreason" disabled/>
          </el-form-item>
        </el-form>
      </el-row>
      <h4>未完成任务</h4>
      <el-row>
        <template>
          <el-table
            :data="tableData"
            style="width: 100%"
            height="250"
          >
            <el-table-column
              prop="name"
              label="名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="type"
              label="类型"
              width="180">
            </el-table-column>
            <el-table-column
              prop="people"
              label="发起人">
            </el-table-column>
            <el-table-column
              prop="time"
              label="发起时间">
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

<!--    删除部门失败-->
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
  </div>
</template>

<script>
  import { listDept, getDept, delDept, addDept, updateDept } from "@/api/system/dept";
  import { optionselect } from "@/api/system/role";
  import { treeselect } from "@/api/system/dept";
    export default {
        name: "index",
        data(){
          return{
            // 查询条件
            queryParams:{
              deptName:''
            },
            // 打开弹框
            open:false,
            // 停用部门失败弹框
            erroropen:false,
            // 失败原因
            errorreason:'有未完成的申请与项目，需完成后方可操作',
            // 删除部门失败
            deleteopen:false,
            deletereason:'有未完成的申请与项目，需完成后方可操作',
            // 弹框标题
            title:'',
            // 表单
            deptForm:{
              deptId:undefined,
              deptName:'',
              remark:'',
              status:'0',
              roleId:null,
              parentId:null
            },
            defaultProps: {
              value: "id",
              label: "label",
              children:"children",
              checkStrictly: true
            },
            // 部门树选项
            deptOptions: [],
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
            // 表单校验
            rules: {
              deptName: [
                {required: true, message: "部门名称不能为空", trigger: "blur"}
              ],
              parentId: [
                {required: true, message: "上级部门不能为空", trigger: "blur"}
              ],
              remark: [
                {required: true, message: "部门概述不能为空", trigger: "blur"}
              ],
              status: [
                {required: true, message: "状态不能为空", trigger: "blur"}
              ],
              roleId: [
                {required: true, message: "部门角色不能为空", trigger: "blur"}
              ],
            },
            // 角色选项
            roleOptions:[],
            deptList:[]
          }
        },
        created(){
          this.getList();
          this.getRoles();
          this.getTreeselect();
        },
        methods:{
          // 表单重置
          reset() {
            this.deptForm = {
              deptId:undefined,
              deptName:'',
              remark:'',
              status:'0',
              roleId:null,
              parentId:null
            };
            this.resetForm("deptForm");
          },
          getList(){
            //前端接口调用
            // this.workflowList = testData.workflowList;
            listDept(this.queryParams).then(response => {
              this.deptList = response.data;
            });
          },
          //获取角色列表
          getRoles(){
            optionselect().then(response => {
              this.roleOptions = response.data;
            });
          },
          /** 查询部门下拉树结构 */
          getTreeselect() {
            treeselect().then(response => {
              this.deptOptions = response.data;
            });
          },
          // 新增部门
          handleAdd(){
            this.open = true;
            this.reset();
          },
          //修改部门
          updateDept(item) {
            this.reset();
            getDept(item.deptId).then(response => {
              this.deptForm = response.data;
              this.open = true;
            });
          },
          // 查询条件搜索
          handleQuery(){
            this.getList();
          },
          // 重置
          resetQuery(){},
          // 新建编辑保存
          submitForm(){
            this.$refs.deptForm.validate(valid => {
              if (valid) {
                if (this.deptForm.deptId != undefined) {
                  updateDept(this.deptForm).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("修改成功");
                      this.open = false;
                      this.getList();
                    } else {
                      this.msgError(response.msg);
                    }
                  });
                } else {
                  addDept(this.deptForm).then(response => {
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
          delDept(item) {
            this.$confirm('是否确认删除名称为"' + item.deptName + '"的部门?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return delDept(item.deptId);
            }).then(() => {
              this.getList();
              this.msgSuccess("删除成功");
            }).catch(function() {});
          },
          // 新建保存取消
          cancel(){},
          // 跳转
          settingrole(item){
            this.$router.push({ path:'../dictrole/role',query:{deptId:item.deptId}})
          },
          // 添加成员
          addperson(){
            this.$router.push({ path:'../dictrole/addperson'})
          },
          handleChange(value){
            this.deptForm.parentId = value[value.length - 1];
          }
        }
    }
</script>

<style>
  .dept .box-card{
    width: 316px;
    height:184px;
    float: left;
    margin-left: 25px;
  }
  /*卡片*/
  .dept .el-card__header{
    height: 75px;
    width: 100%;
  }
  /*不能点击文字颜色*/
  .dept .el-input.is-disabled .el-input__inner{
    color:#666;
  }
  .dept .el-card__body p span,.dept .el-card__body p{
    font-size: 14px;
  }
  .dept .el-card__body p span:nth-child(2){
    float: right;
  }
  .dept .clearfix:after{
    display: block;
    clear: both;
    content:""
  }
  .dept .clearfix{
    margin:0;
  }
  .dept .clearfix p,.dept .clearfix h2{
    margin:0
  }
  .dept .clearfix p{
    margin-top: 8px;
    color:#aaa;
    font-size: 12px;
  }
  .clearfix>div{
    height: 75px;
  }

  .dept .cantclick{
    background:#F2F2F2;
  }
</style>
