<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="流程ID" prop="workflowId">
        <el-input
          v-model="queryParams.workflowId"
          placeholder="请输入流程ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="workflowName">
        <el-input
          v-model="queryParams.workflowName"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:workflow:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:workflow:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:workflow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:workflow:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="workflowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程ID" align="center" prop="workflowId" />
      <el-table-column label="流程名称" align="center" >
        <template slot-scope="scope">
          <router-link :to="'/workflow/node/' + scope.row.workflowId" class="link-type">
            <span>{{ scope.row.workflowName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="流程分组编码" align="center" prop="workflowGroup" />
      <el-table-column label="流程描述" align="center" prop="workflowDesc" />
      <el-table-column label="审核链接url" align="center" prop="linkUrl" />
      <el-table-column label="修改者" align="center" prop="modifyBy" />
      <el-table-column label="修改时间" align="center" prop="modifyDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:workflow:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:workflow:remove']"
          >删除</el-button>
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

    <!-- 添加或修改流程定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="流程ID" prop="workflowId">
          <el-input v-model="form.workflowId" placeholder="请输入流程ID" />
        </el-form-item>
        <el-form-item label="流程名称" prop="workflowName">
          <el-input v-model="form.workflowName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程分组编码" prop="workflowGroup">
          <el-input v-model="form.workflowGroup" placeholder="请输入流程分组编码" />
        </el-form-item>
        <el-form-item label="流程描述" prop="workflowDesc">
          <el-input v-model="form.workflowDesc" placeholder="请输入流程描述" />
        </el-form-item>
        <el-form-item label="审核链接url" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入审核链接url" />
        </el-form-item>
        <el-form-item label="排序字段" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入排序字段" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkflow, getWorkflow, delWorkflow, addWorkflow, updateWorkflow, exportWorkflow } from "@/api/system/workflow";

export default {
  name: "Workflow",
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
      // 流程定义表格数据
      workflowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 提交标识
      submitFlag: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workflowId: undefined,
        workflowName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workflowId: [
          { required: true, message: "流程ID不能为空", trigger: "blur" }
        ],
        workflowName: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        workflowGroup: [
          { required: true, message: "流程分组编码不能为空", trigger: "blur" }
        ],
        linkUrl: [
          { required: true, message: "审核链接url不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listWorkflow(this.queryParams).then(response => {
        this.workflowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        workflowId: undefined,
        workflowName: undefined,
        workflowGroup: undefined,
        workflowDesc: undefined,
        linkUrl: undefined,
        sortOrder: undefined,
        remarks: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workflowId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程定义";
      this.submitFlag = "insert";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const workflowId = row.workflowId || this.ids
      getWorkflow(workflowId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程定义";
        this.submitFlag = "update";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.submitFlag == "update") {
            updateWorkflow(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else if (this.submitFlag == "insert") {
            addWorkflow(this.form).then(response => {
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
      const workflowIds = row.workflowId || this.ids;
      this.$confirm('是否确认删除流程ID为"' + workflowIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWorkflow(workflowIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有流程定义数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportWorkflow(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
