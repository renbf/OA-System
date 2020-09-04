<template>
  <div class="app-container role">
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
            >新增角色</el-button>
          </el-col>
        </el-row>
        <!--        查询的form表单-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

          <el-form-item label="角色名称">
            <el-input
              v-model="queryParams.roleName"
              placeholder="请输入角色名称"
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
      <template v-for="item in roleList">
        <el-card class="box-card" v-show="item.status == 0">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h2>{{item.roleName}}</h2>
              <p>{{item.createTime}}</p>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-setting" circle @click="handleUpdate(item)"></el-button>
              <el-button icon="el-icon-delete" circle @click="handleDelete(item)"></el-button>
              <el-button icon="el-icon-circle-check" circle @click="handleLimit(item)"></el-button>
            </div>
          </div>
          <div class="text item">
            <p>
             使用人数
            </p>
            <p>
              <b style="font-size:28px;">{{item.nums}}</b>人
            </p>
          </div>
        </el-card>
        <!--      不可点击-->
        <el-card class="box-card cantclick" v-show="item.status == 1">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h2>{{item.roleName}}</h2>
              <p>{{item.createTime}}</p>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-setting" circle></el-button>
              <el-button icon="el-icon-delete" circle></el-button>
            </div>
          </div>
          <div class="text item">
            <p>
              使用人数
            </p>
            <p>
              <b style="font-size:28px;">{{item.nums}}</b>人
            </p>
          </div>
        </el-card>
      </template>
      <template>

      </template>
    </el-row>


    <!--    新建/编辑角色-->
    <el-dialog :title="title" :visible.sync="open"  width="600px">
      <el-form ref="roleForm" :model="roleForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="roleForm.roleName" placeholder="请输入角色名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="权限编码" prop="roleKey">
              <el-input v-model="roleForm.roleKey" placeholder="请输入权限编码" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="角色概述" prop="remark">
              <el-input type="textarea" v-model="roleForm.remark" placeholder="请输入角色概述" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-switch v-model="roleForm.status" active-text="启用" active-value="0" inactive-value="1"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="角色权限">
              <el-tree
                :data="menuOptions"
                show-checkbox
                ref="menu"
                node-key="id"
                :default-expanded-keys="[1]"
                :props="defaultProps">
              </el-tree>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>

<!--    查阅角色-->

    <el-dialog :title="detailtitle" :visible.sync="detailopen"  width="600px">
      <el-form ref="form" :model="roleForm" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="roleForm.roleName" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="角色概述" prop="remark">
              <el-input v-model="roleForm.remark" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="角色权限" prop="menuIds">
              <el-tree :data="menuOptions" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-collapse v-model="useperson">
        <el-collapse-item title="使用用户" name="1">
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
              <el-table-column align="right">
                <template  slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-setting"
                    @click.stop="detailedit(scope.row)"
                  >修改</el-button>
                </template>
              </el-table-column>
            </el-table>
            <pagination
              v-show="total>0"
              :total="total"
              :page.sync="pageNum"
              :limit.sync="pageSize"
            />
          </template>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <!--    停用角色操作失败-->
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

    <!--    删除角色失败-->
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

    <!--    分配数据权限-->
    <el-dialog :title="datalimittitle" :visible.sync="datalimitopen"  width="600px">
      <el-form ref="dataScopeForm" :model="dataScopeForm" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="dataScopeForm.roleName" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="权限范围" prop="limit">
              <el-select v-model="dataScopeForm.dataScope" placeholder="请选择">
                <el-option
                  v-for="item in dataScopeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitDataScope">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { deptListRole, getRole, delRole, addRole, updateRole, exportRole, dataScope, changeRoleStatus } from "@/api/system/role";
  import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
  import { checkValidLowerCase } from "@/utils/validate";
  export default {
    name: "index",
    data(){
      return{
        deptId:this.$route.query.deptId,
        // 查询条件
        queryParams:{
          roleName:''
        },
        // 打开弹框
        open:false,
        // 分配数据权限
        datalimittitle:'分配数据权限',
        datalimitopen:false,
        // 查阅弹框
        detailopen:false,
        // 停用部门失败弹框
        erroropen:false,
        // 失败原因
        errorreason:'有未完成的申请与项目，需完成后方可操作',
        // 删除部门失败
        deleteopen:false,
        deletereason:'有未完成的申请与项目，需完成后方可操作',
        // 弹框标题
        title:'',
        // 查阅标题
        detailtitle:"查阅角色",
        // 折叠面板
        useperson:undefined,
        // 表单
        roleForm:{
          roleId: undefined,
          roleName: undefined,
          roleKey: undefined,
          status: "0",
          menuIds: [],
          remark: undefined
        },
        // 表单
        dataScopeForm:{
          roleId: undefined,
          roleName: undefined,
          dataScope: undefined
        },
        roleList: [],
        // 数据范围选项
        dataScopeOptions: [
          {
            value: "1",
            label: "全部数据权限"
          },
          // {
          //   value: "2",
          //   label: "自定数据权限"
          // },
          {
            value: "3",
            label: "本部门数据权限"
          },
          {
            value: "4",
            label: "本部门及以下数据权限"
          },
          {
            value: "5",
            label: "仅本人数据权限"
          }
        ],
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
        menuOptions: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 表单校验
        rules: {
          roleName: [
            {required: true, message: "角色名称不能为空", trigger: "blur"}
          ],
          roleKey: [
            {required: true, message: "权限编码不能为空", trigger: "blur"},
            {required: true, validator: checkValidLowerCase, message: "权限编码只能是小写字母", trigger: "blur"}
          ],
          remark: [
            {required: true, message: "角色概述不能为空", trigger: "blur"}
          ],
          status: [
            {required: true, message: "状态不能为空", trigger: "blur"}
          ],
        },
        // 权限list
        postOptions:[]
      }
    },
    created() {
      this.getList();
      this.getMenuTreeselect();
    },
    methods:{
      getList(){
        deptListRole({deptId:this.deptId}).then(
          response => {
            this.roleList = response.data;
          }
        );
      },
      // 表单重置
      reset() {
        if (this.$refs.menu != undefined) {
          this.$refs.menu.setCheckedKeys([]);
        }
        this.roleForm = {
          roleId: undefined,
          roleName: undefined,
          roleKey: undefined,
          status: "0",
          menuIds: [],
          remark: undefined
        };
        this.resetForm("roleForm");
      },
      // 表单重置
      resetDataScopeForm() {
        this.dataScopeForm = {
          roleId: undefined,
            roleName: undefined,
            dataScope: undefined
        };
        this.resetForm("dataScopeForm");
      },
      /** 查询菜单树结构 */
      getMenuTreeselect() {
        menuTreeselect().then(response => {
          this.menuOptions = response.data;
        });
      },
      // 所有菜单节点数据
      getMenuAllCheckedKeys() {
        // 目前被选中的菜单节点
        let checkedKeys = this.$refs.menu.getHalfCheckedKeys();
        // 半选中的菜单节点
        let halfCheckedKeys = this.$refs.menu.getCheckedKeys();
        checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
        return checkedKeys;
      },
      // 新增部门
      handleAdd(){
        this.reset();
        this.open = true;
      },
      handleUpdate(item) {
        this.reset();
        const roleId = item.roleId;
        this.$nextTick(() => {
          this.getRoleMenuTreeselect(roleId);
        });
        getRole(roleId).then(response => {
          this.roleForm = response.data;
          this.open = true;
          this.title = "修改角色";
        });
      },
      /** 删除按钮操作 */
      handleDelete(item) {
        const roleIds = item.roleId;
        this.$confirm('是否确认删除角色名为"' + item.roleName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRole(roleIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 根据角色ID查询菜单树结构 */
      getRoleMenuTreeselect(roleId) {
        roleMenuTreeselect(roleId).then(response => {
          this.menuOptions = response.menus;
          this.$refs.menu.setCheckedKeys(response.checkedKeys);
        });
      },
      // 查询条件搜索
      handleQuery(){},
      // 重置
      resetQuery(){},
      // 新建编辑保存
      submitForm(){
        this.$refs.roleForm.validate(valid => {
          if (valid) {
            if (this.roleForm.roleId != undefined) {
              this.roleForm.menuIds = this.getMenuAllCheckedKeys();
              updateRole(this.roleForm).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              this.roleForm.menuIds = this.getMenuAllCheckedKeys();
              addRole(this.roleForm).then(response => {
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
      // 新建保存取消
      cancel(){},
      handleNodeClick(){},
      // 查看修改
      detailedit(){

      },
      handleLimit(item){
        this.datalimitopen=true;
        this.resetDataScopeForm();
        this.dataScopeForm.roleId = item.roleId;
        this.dataScopeForm.roleName = item.roleName;
        this.dataScopeForm.dataScope = item.dataScope;
      },
      /** 提交按钮（数据权限） */
      submitDataScope: function() {
        if (this.dataScopeForm.roleId != undefined) {
          dataScope(this.dataScopeForm).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功");
              this.datalimitopen = false;
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        }
      }
    }
  }
</script>

<style>
  .role .box-card{
    width: 316px;
    height:184px;
    float: left;
    margin-left: 25px;
  }
  /*卡片*/
  .role .el-card__header{
    height: 75px;
    width: 100%;
  }
  /*不能点击文字颜色*/
  .role .el-input.is-disabled .el-input__inner{
    color:#666;
  }
  .role .el-card__body p span,.dept .el-card__body p{
    font-size: 14px;
  }
  .role .el-card__body p{
    text-align: center;
  }
  .role .clearfix:after{
    display: block;
    clear: both;
    content:""
  }
  .role .clearfix{
    margin:0;
  }
  .role .clearfix p,.role .clearfix h2{
    margin:0
  }
  .role .clearfix p{
    margin-top: 8px;
    color:#aaa;
    font-size: 12px;
  }
  .clearfix>div{
    height: 75px;
  }

  .role .cantclick{
    background:#F2F2F2;
  }
</style>
