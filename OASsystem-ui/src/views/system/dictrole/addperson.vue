<template>
  <div class="app-container addperson">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >添加成员</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="用户名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>



        <el-table v-loading="loading" :data="roleList">
          <el-table-column label="姓名" align="center" prop="userName" :show-overflow-tooltip="true" />
          <el-table-column label="角色" align="center" prop="role" :show-overflow-tooltip="true" />
          <el-table-column label="手机号码" align="center" prop="phonenumber" />
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
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-key"
                @click="handleResetPwd(scope.row)"
              >重置</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
        />
      </el-col>
    </el-row>


<!--    添加成员弹框-->
    <el-dialog :title="title" :visible.sync="open"  width="650px">
      <el-form ref="form" :model="addForm" :rules="rules" label-width="80px">
        <el-row>
            <el-form-item label="选择成员" prop="choseperson">
              <template>
                <el-transfer
                  filterable
                  :filter-method="filterMethod"
                  filter-placeholder="请输入城市拼音"
                  v-model="addForm.chosepeople"
                  :data="chosedata">
                </el-transfer>
              </template>
            </el-form-item>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="部门角色" prop="deptrole">
              <el-select v-model="addForm.roleId" placeholder="请选择">
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
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>


<!--    修改、查看成员弹框-->
    <el-dialog :title="edittitle" :visible.sync="editopen"  width="650px">
      <el-form ref="form" :model="editForm" :rules="editrules" label-width="80px">
        <el-form-item label="成员">
          <el-tag type="info">迈克尔</el-tag>
        </el-form-item>
        <el-form-item label="部门角色">
          <el-select v-model="editForm.roleId" placeholder="选择角色" :disabled="editordetail">
            <el-option
              v-for="item in roleOptions"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editcancel">取 消</el-button>
        <el-button type="primary" @click="submiteditForm">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  .addperson .el-transfer__button{
    display: block;
    margin-left: 0;
  }
</style>

<script>
  import { treeselect } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import { optionselect } from "@/api/system/role";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "User",
    components: {Treeselect},

    data() {
      const generateData = _ => {
        const data = [];
        const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
        const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
        cities.forEach((city, index) => {
          data.push({
            label: city,
            key: index,
            pinyin: pinyin[index]
          });
        });
        return data;
      };
      return {
        // 遮罩层
        loading: false,
        // 表单参数
        form: {

        },
        deptName:'',
        deptOptions:[],
        // 弹框
        open:false,
        // 标题
        title:'添加成员',
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 判断是否为查看还是编辑
        editordetail:false,
        // 添加成员form
        addForm:{
          chosepeople:[],
          roleId:''
        },
        // 修改成员
        editForm:{},
        editrules: {
          choseperson: [
            {required: true, message: "选择成员不能为空", trigger: "blur"}
          ],
        },
        // 修改查看弹框
        edittitle:'',
        editopen:false,
        // 穿梭框data
        chosedata:generateData(),
        filterMethod(query, item) {
          return item.pinyin.indexOf(query) > -1;
        },
        filterNode(){},
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userName: undefined,
          phonenumber: undefined,
          status: undefined,
          deptId: undefined
        },
        roleList:[
          {
          userName:'张三',
          role:'软件部经理(软件部)',
          phonenumber:'15764475997',
        },
          {
            userName:'李四',
            role:'软件部经理(软件部)',
            phonenumber:'15764475997',
          },
        ],
        // 角色
        roleOptions:[],
        total:1,
        // 表单校验
        rules: {
          choseperson: [
            {required: true, message: "选择成员不能为空", trigger: "blur"}
          ],
          deptrple: [
            {required: true, message: "选择成员不能为空", trigger: "blur"}
          ],
        },
      };
    },
    watch: {
      // 根据名称筛选部门树
      deptName(val) {
        this.$refs.tree.filter(val);
      }
    },
    created() {
      this.getTreeselect();
    },
    methods: {
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      //获取角色列表
      getRoles(){
        optionselect().then(response => {
          this.roleOptions = response.data;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      handleAdd() {
        this.open=true
      },
      handleQuery(){},
      resetQuery(){},
      handleUpdate(){},
      handleResetPwd(){},
      // 部门树点击事件
      handleNodeClick(){},
      // 弹框 保存
      submitForm(){},

      // 修改
      submiteditForm(){},
      // 取消
      editcancel(){
        this.editopen = false;
        this.reset();
      }
    }
  }
</script>
