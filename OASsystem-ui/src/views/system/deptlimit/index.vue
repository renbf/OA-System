<template>
  <div class="app-container">
      <el-row>

          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新建权限</el-button>
          <el-button
            size="mini"
            class="filter-item"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete"
          >删除</el-button>

      </el-row>
      <el-row>
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <!--          创建时间-->
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="queryParams.dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>

          <el-form-item label="状态">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
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

          <el-form-item label="部门名称">
            <el-input
              v-model="queryParams.roleNamedeptName"
              placeholder="请输入部门名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </el-form-item>

          <el-form-item>
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入角色名称"
              clearable
              size="small"
              style="width: 240px"
            />
          </el-form-item>
          <!--          <el-form-item label="手机号码" prop="phonenumber">-->
          <!--            <el-input-->
          <!--              v-model="queryParams.phonenumber"-->
          <!--              placeholder="请输入手机号码"-->
          <!--              clearable-->
          <!--              size="small"-->
          <!--              style="width: 240px"-->
          <!--              @keyup.enter.native="handleQuery"-->
          <!--            />-->
          <!--          </el-form-item>-->


          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-row>

    <el-table
      v-loading="loading"
      :data="deptList"
      @selection-change="handleSelectionChange"
      @row-dblclick="handleRowClick"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="rolelimit" label="权限" />
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          <span>{{scope.row.systemRemark}}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change.stop="handleStatusChange(scope.row)"
            :active-text="scope.row.status=='0'?'启用':'禁用'"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="600px">
      <el-form ref="form" :model="roleForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="roleForm.roleName" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input type="textarea" v-model="roleForm.content" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-switch v-model="roleForm.status" active-text="启用"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="用户权限" prop="rolelimit">
              <el-tree
                :data="limitdata"
                show-checkbox
                node-key="id"
                :default-expanded-keys="[2, 3]"
                :default-checked-keys="[5]"
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

<!--    查阅-->
    <el-dialog :title="detailtitle" :visible.sync="detailopen"  width="600px">
      <el-form ref="form" :model="roleForm" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input v-model="roleForm.roleName" disabled/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="部门">
              <el-input v-model="roleForm.dept" disabled/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input type="textarea" v-model="roleForm.content" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="用户权限">
<!--              <el-tree-->
<!--                :data="limitdata"-->
<!--                show-checkbox-->
<!--                node-key="id"-->
<!--                :default-expanded-keys="[2, 3]"-->
<!--                :default-checked-keys="[5]"-->
<!--                :props="defaultProps">-->
<!--              </el-tree>-->
              <el-tree :data="limitdata" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>


<!--    分配数据权限-->
    <el-dialog :title="datalimittitle" :visible.sync="datalimitopen"  width="600px">
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
            <el-form-item label="权限范围" prop="limit">
              <el-select v-model="roleForm.chosevalue" placeholder="请选择">
                <el-option
                  v-for="item in roleForm.limitoptions"
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
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listDept, getDept, delDept, addDept, updateDept } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import { optionselect } from "@/api/system/role";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Dept",
    components: { Treeselect },
    data() {
      return {
        // 遮罩层
        loading: true,
        // 表格树数据
        deptList: [
          {
            roleName:'技术部',
            rolelimit:'日常工作新建/日常工作修改/日常工作查阅/日常任务新建/日常任务修……'
          },
          {
            roleName:'软件部',
            rolelimit:'日常工作新建/日常工作修改/日常工作查阅/日常任务新建/日常任务修……'
          }
        ],
        // 部门树选项
        deptOptions: [],
        // 角色选项
        roleOptions: [],
        // 弹出层标题
        title: "",
        // 查阅标题
        detailtitle:'查阅权限',
        // 分配数据权限
        datalimittitle:'分配数据权限',
        // 是否显示弹出层
        open: false,
        // 是否显示查阅弹框
        detailopen:false,
        // 分配数据权限弹框
        datalimitopen:false,
        // 状态数据字典
        statusOptions: [],
        // 查询参数
        queryParams: {
          deptName: undefined,
          status: undefined,
          roleNamedeptName:undefined,
          dateRange:[]
        },
        // 表单
        roleForm:{
          roleName:'',
          content:'',
          status:'0',
          postIds:[],
          dept:'',
          // 权限范围
          limitoptions: [{
            value: '选项1',
            label: '本部门及以下数据权限'
          }, {
            value: '选项2',
            label: '双皮奶'
          }, {
            value: '选项3',
            label: '蚵仔煎'
          }, {
            value: '选项4',
            label: '龙须面'
          }, {
            value: '选项5',
            label: '北京烤鸭'
          }],
          chosevalue: ''
        },

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
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          roleName: [
            {required: true, message: "角色名称不能为空", trigger: "blur"}
          ],
          status: [
            {required: true, message: "状态不能为空", trigger: "blur"}
          ],
          postIds: [
            {required: true, message: "用户权限不能为空", trigger: "blur"}
          ],
        },
      };
    },
    created() {
      this.getList();
      this.getRoles();
      this.getDicts("sys_normal_disable").then(response => {
        this.statusOptions = response.data;
      });
    },
    methods: {
      /** 查询部门列表 */
      getList() {
        this.loading = true;
        listDept(this.queryParams).then(response => {
          this.deptList = this.handleTree(response.data, "deptId");
          this.loading = false;
        });
      },
      //获取角色列表
      getRoles(){
        optionselect().then(response => {
          this.roleOptions = response.data;
        });
      },
      /** 转换部门数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.deptId,
          label: node.deptName,
          children: node.children
        };
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        listDept().then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId");
        });
      },
      // 字典状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          deptId: undefined,
          parentId: undefined,
          deptName: undefined,
          orderNum: undefined,
          leader: undefined,
          phone: undefined,
          email: undefined,
          status: "0"
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.postId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd(row) {
        this.reset();
        this.getTreeselect();
        if (row != undefined) {
          this.form.parentId = row.deptId;
        }
        this.open = true;
        this.title = "添加部门";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.getTreeselect();
        getDept(row.deptId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改部门";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.deptId != undefined) {
              updateDept(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addDept(this.form).then(response => {
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
      /** 删除按钮操作 */
      handleDelete(row) {
        this.$confirm('是否确认删除名称为"' + row.deptName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDept(row.deptId);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },

      // 重置
      resetQuery(){},
      // 双击查看
      handleRowClick(){

      },
      // 用户权限点击
      handleNodeClick(){}
    }
  };
</script>
