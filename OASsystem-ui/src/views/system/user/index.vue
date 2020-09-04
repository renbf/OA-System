<template>
  <div class="app-container user">
    <el-row :gutter="20">

      <!--用户数据-->
      <el-col :span="24" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:user:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
            >导出</el-button>
          </el-col>
        </el-row>
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
<!--          创建时间-->
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
<!--          停用时间-->
          <el-form-item label="停用时间">
            <el-date-picker
              v-model="dateStop"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="部门名称">
            <el-input
              v-model="deptName"
              placeholder="请输入部门名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
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

          <el-form-item>
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名、姓名或手机号"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
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



        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange" @row-dblclick="handleRowClick">
          <el-table-column type="selection" width="40" align="center" />
<!--          <el-table-column label="用户编号" align="center" prop="userId" />-->
          <el-table-column label="用户名" align="center" prop="userName" :show-overflow-tooltip="true" />
          <el-table-column label="姓名" align="center" prop="nickName" width="160">
            <template slot-scope="scope">
              <span v-if="scope.row.nickName">{{scope.row.nickName}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="部门" align="center" prop="dept.deptName" :show-overflow-tooltip="true" />
          <el-table-column label="角色" align="center" prop="roleNames" :show-overflow-tooltip="true" />
          <el-table-column label="手机号码" align="center" prop="phonenumber" width="120" />

          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>

          <el-table-column label="停用时间" align="center" prop="disableTime" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.disableTime }}</span>
            </template>
          </el-table-column>

          <el-table-column label="备注" align="center" prop="remark" width="160">
            <template slot-scope="scope">
              <span>{{scope.row.remark}}{{scope.row.systemRemark}}</span>
            </template>
          </el-table-column>

          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
                :active-text="scope.row.status=='0'?'启用':'禁用'"
              ></el-switch>
            </template>
          </el-table-column>



          <el-table-column
            label="操作"
            align="center"
            width="180"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click.stop="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >修改</el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click.stop="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >删除</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-refresh"
                @click.stop="handleResetPwd(scope.row)"
                v-hasPermi="['system:user:resetPwd']"
              >重置</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="600px">
      <el-form ref="form" :model="userForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="用户名称" prop="nickName">
              <el-input v-model="userForm.nickName" placeholder="请输入用户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="userForm.userName" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>

          <el-col :span="24" v-show="showPassword">
            <el-form-item label="密码" prop="password">
              <el-input v-model="userForm.password" placeholder="请输入用户密码" type="password" />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-show="showPassword">
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="userForm.confirmPassword" placeholder="请确认密码" type="password" />
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="手机号码">
              <el-input v-model="userForm.phonenumber" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>

<!--          <el-col :span="12">-->
<!--            <el-form-item label="归属部门" prop="deptId">-->
<!--              <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->

<!--          <el-col :span="12">-->
<!--            <el-form-item label="邮箱" prop="email">-->
<!--              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="用户名称" prop="userName">-->
<!--              <el-input v-model="form.userName" placeholder="请输入用户名称" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->

<!--          <el-col :span="12">-->
<!--            <el-form-item label="用户性别">-->
<!--              <el-select v-model="form.sex" placeholder="请选择">-->
<!--                <el-option-->
<!--                  v-for="dict in sexOptions"-->
<!--                  :key="dict.dictValue"-->
<!--                  :label="dict.dictLabel"-->
<!--                  :value="dict.dictValue"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-switch v-model="userForm.status" active-text="启用" active-value="0" inactive-value="1" />
            </el-form-item>
          </el-col>
<!--备注-->
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="userForm.remark" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用户部门"  prop="deptId">
<!--              <el-select v-model="userForm.deptId" placeholder="请选择">-->
<!--                <el-option-->
<!--                  v-for="item in deptOptions"-->
<!--                  :key="item.deptId"-->
<!--                  :label="item.deptName"-->
<!--                  :value="item.deptId"-->
<!--                  :disabled="item.status == 1"-->
<!--                ></el-option>-->
<!--              </el-select>-->
              <el-cascader
                v-model="userForm.deptId"
                :options="deptOptions"
                :props="defaultProps"
                @change="handleChange">
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-for="(mainitem, index) in userForm.domains"  :key="mainitem.key">
            <el-form-item label="用户角色" style="margin-bottom:10px;" :key="mainitem.key" :prop="'domains.'+index+'.value'" :rules="{required: true, message: '所选项不能为空', trigger: 'change'}">
              <el-select v-model="userForm.domains[index].value" placeholder="请选择"  @change="choserole">
                <el-option
                  v-for="item in roleOptions"

                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="selectIdsArr.includes(item.roleId)  || item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-col :span="24" :offset="3">
              <el-button type="primary" icon="el-icon-plus" circle @click.prevent="addDomain" v-show="index == 0 ? true : datePickerShow"></el-button>
              <el-button type="danger" icon="el-icon-delete" circle @click.prevent="removeDomain(index, mainitem)" v-show="index == 0 ? false : datePickerShow"></el-button>
              <span style="display:inline-block;color:#C0C4CC;margin-bottom: 17px;">注：已选择的角色将不可再次选择</span>
            </el-col>
          </el-col>
        </el-row>
      </el-form>



<!--      <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">-->
<!--        <el-form-item-->
<!--          prop="email"-->
<!--          label="邮箱"-->
<!--          :rules="[-->
<!--      { required: true, message: '请输入邮箱地址', trigger: 'blur' },-->
<!--      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }-->
<!--    ]"-->
<!--        >-->
<!--          <el-input v-model="dynamicValidateForm.email"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item-->
<!--          v-for="(domain, index) in dynamicValidateForm.domains"-->
<!--          :label="'域名' + index"-->
<!--          :key="domain.key"-->
<!--        >-->
<!--          <el-input v-model="domain.value"></el-input><el-button @click.prevent="removeDomain(domain)">删除</el-button>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <el-button type="primary" @click="submitForm('dynamicValidateForm')">提交</el-button>-->
<!--          <el-button @click="addDomain">新增域名</el-button>-->
<!--          <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->




      <el-collapse v-model="matters_needing_attention">
        <el-collapse-item title="新建用户注意事项" name="1">
          <div>
            1、用户密码均为初始密码，用户第一次登录时需另行修改。
          </div>
          <div>
            2、用户状态为“启用”时，用户才可登录系统。
          </div>
          <div>
            3、用户角色为单选，但可拥有多角色。每个部门内只能选择一个角色，已选择的部门则不可再次选择。
          </div>

        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>


<!--    查看弹框-->
    <el-dialog :title="detailtitle" :visible.sync="detailopen" width="600px">
      <el-form ref="form1" :model="userForm" label-width="80px">
        <el-row>
            <el-form-item label="用户名称">
              <el-input v-model="userForm.nickName" disabled/>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="用户名">
            <el-input v-model="userForm.userName" disabled/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="密码">
            <el-input v-model="userForm.password" disabled/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="手机号码">
            <el-input v-model="userForm.phonenumber" disabled/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="用户角色">
            <el-input v-model="userForm.roleNames" disabled/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="userForm.remark"  disabled/>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailtoedit(userForm)">编 辑</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

<!--    操作失败-->
    <el-dialog title="操作失败" :visible.sync="erroropen" width="600px">
      <el-row>
        <el-form ref="form2">
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
        </template>
      </el-row>
    </el-dialog>
  </div>
</template>

<style>
  .user .el-button--medium.is-circle{
    padding:5px;
  }
  .user .el-collapse-item__header{
    color:red!important;
  }
  .user .el-input.is-disabled .el-input__inner{
    color:#666;
  }
</style>

<script>
import { listUser, getUser, delUser, addUser, updateUser, exportUser, resetUserPwd, changeUserStatus, importTemplate } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import getMd5 from "@/assets/js/md5.min";
// import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { arrayToStr,isNotEmpty } from '@/utils/common';

export default {
  name: "User",
  // components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 查看弹框标题
      detailtitle:"查看",
      // 操作失败弹框
      erroropen:false,
      // 失败原因
      errorreason:'有未完成的申请与项目，需完成后方可操作',
      // 部门树选项
      deptOptions: [],
      // 是否显示弹出层
      open: false,
      // 是否显示查看弹框
      detailopen:false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 状态文字
      activetext:'启用',
      // 停用时间
      dateStop: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 弹框新建用户注意事项
      matters_needing_attention:undefined,
      showPassword:false,
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
      // 部门数据
      // 表单参数
      userForm: {
        roleIds:'',
        nickName:'',
        userName:'',
        password:'',
        confirmPassword:'',
        status:'0',
        deptId:'',
        domains: [
          {value:null}
        ],
        selectedval:[],
      },
      defaultProps: {
        value: "id",
        label: "label",
        children:"children"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      datePickerShow: false,

      // 弹框下拉框选中数据
      selectIdsArr:[],
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ],
        // domainsCheck: [
        //   {
        //     type: "string",
        //     required: true,
        //     message: '角色不能为空',
        //     trigger: 'change',
        //     transform(value) {
        //       var base
        //       if (value) {
        //         //在select中转换要加这个判断
        //         //因为select默认有一个空选项
        //         base = "" + value
        //       }
        //       return base
        //     }
        //   }
        // ],
        // phonenumber: [
        //   { required: true, message: "手机号码不能为空", trigger: "blur" },
        //   {
        //     pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        //     message: "请输入正确的手机号码",
        //     trigger: "blur"
        //   }
        // ]
        deptId: [
          { required: true, message: "用户部门不能为空" }
        ]

      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    // deptName(val) {
    //   this.$refs.tree.filter(val);
    // }
  },
  created() {
    this.getList();
    // this. + ();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      let queryParams = this.addDateRange(this.queryParams, this.dateRange);
      listUser(this.addDateStop(queryParams,this.dateStop)).then(response => {
          this.userList = response.rows;
        this.formatList(this.userList);
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    //列表格式化
    formatList(list){
      list.forEach(
        (val)=> {
          if(val.disableTime != null){
            let a = val.disableTime.substring(0,10);
            val.disableTime = a;
          }
          if(!isNotEmpty(val.remark) && isNotEmpty(val.systemRemark)){
            val.systemRemark = val.systemRemark.substring(1);
          }
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // // 筛选节点
    // filterNode(value, data) {
    //   if (!value) return true;
    //   return data.label.indexOf(value) !== -1;
    // },
    // 节点单击事件
    // handleNodeClick(data) {
    //   this.queryParams.deptId = data.id;
    //   this.getList();
    // },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "禁用";
      this.$confirm('确认要"' + text + '""' + row.userName + '"用户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
          this.getList();
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.userForm = {
        userName: undefined,
        nickName: undefined,
        password: undefined,
        confirmPassword:undefined,
        phonenumber: undefined,
        status: "0",
        // remark: undefined,
        deptId: undefined,
        roleIds:'',
        domains: [
          {value:null}
        ],
      };
      this.resetForm("form");
    },
    addDateStop(params, dateRange) {
      var search = params;
      search.dateStopBeginTime = "";
      search.dateStopEndTime = "";
      if (null != dateRange && '' != dateRange) {
        search.dateStopBeginTime = dateRange[0];
        search.dateStopEndTime = dateRange[1];
      }
      return search;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.dateStop = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      console.log(this.userForm, 666);
      getUser().then(response => {
        // this.deptOptions = response.depts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.userForm.password = this.initPassword;
        this.showPassword = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      console.log(this.userForm, 666);
      const userId = row.userId || this.ids;
      let _this = this;
      getUser(userId).then(response => {
        this.userForm = response.data;
      //   console.log(this.userForm, '渲染');
      //   this.deptOptions = response.depts;
        this.roleOptions = response.roles;
        // // this.userForm.postIds = response.postIds;
        this.userForm.roleIds = response.roleIds;
        this.open = true;
        this.title = "修改用户";
        this.userForm.password = "";
        this.showPassword = false;
        this.selectIdsArr = [];
        this.userForm.domains = [];
        for (let i = 0; i < this.userForm.roleIds.length; i++) {
          let item = {value: this.userForm.roleIds[i]};
          this.userForm.domains.push(item);
          this.selectIdsArr.push(item.value);
        }
        console.log(this.userForm, 777);
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
          resetUserPwd(row.userId, getMd5(value)).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功，新密码是：" + value);
            } else {
              this.msgError(response.msg);
            }
          });
        }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function() {
      let _this = this;
      let rules = _this.rules;
      this.rules.confirmPassword[0].required = false;
      this.rules.password[0].required = false;
      console.log(this.userForm.domains,"角色")
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.userForm.userId != undefined) {
            this.userForm.deptId = this.userForm.deptId[this.userForm.deptId.length - 1];
            this.userForm.roleIds = arrayToStr(this.userForm.domains,"value").split(",");
            updateUser(this.userForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            // 密码使用md5加密传输
            this.userForm.password = getMd5(this.userForm.password);
            this.userForm.deptId = this.userForm.deptId[this.userForm.deptId.length - 1];
            this.userForm.roleIds = arrayToStr(this.userForm.domains,"value").split(",");
            addUser(this.userForm).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUser(userIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUser(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导入按钮操作 */
    // handleImport() {
    //   this.upload.title = "用户导入";
    //   this.upload.open = true;
    // },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 选角色
    choserole(value){
      // console.log(value)
      // this.userForm.selectedval.push(value);
      this.selectIdsArr = [];
      console.log(this.userForm,4);
      for (const item of this.userForm.domains) {
        if (item.value) {
          this.selectIdsArr.push(item.value);
        }
      }
    },
    // 添加单条下拉选
    addDomain(index) {
      // var arr=[];
      this.userForm.domains.push({
        value: 3,
        key: Date.now()
      });
      console.log(this.userForm,11);
      this.datePickerShow = true;
    },
    // 删除添加的下拉选
    removeDomain(item) {
      console.log(this.userForm,2);
      if (item !== -1) {
          this.userForm.domains.splice(item, 1)
          this.selectIdsArr.splice(-1,1);
        }
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 行点击事件
    handleRowClick(row){

      this.reset();
      // this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.userForm = response.data;
        // this.deptOptions = response.depts;
        this.roleOptions = response.roles;
        // this.userForm.postIds = response.postIds;
        this.userForm.roleIds = response.roleIds;
        this.detailopen=true;
        this.title = "查看用户";
        this.userForm.password = "";
      });
    },

    // 查阅跳转编辑
    detailtoedit(userForm){
      this.detailopen=false;
      this.open=true;
      this.title='修改'
      this.handleUpdate(userForm)
    },

    // 部门选择事件
    handleChange(){}
  }
};
</script>
