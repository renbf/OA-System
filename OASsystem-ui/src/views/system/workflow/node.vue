<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
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

    <el-table v-loading="loading" :data="nodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="组织结构ID" align="center" prop="orgId" />-->
<!--      <el-table-column label="审核步骤ID" align="center" prop="nodeId" />-->
      <el-table-column label="审核步骤" align="center" prop="sortOrder" />
      <el-table-column label="审核步骤名称" align="center" prop="nodeName" />
<!--      <el-table-column label="流程ID" align="center" prop="workflowId" />-->
      <el-table-column label="流程名称" align="center" prop="workflowName" />
      <el-table-column label="审批人类别" align="center" prop="checkerType" />
      <el-table-column label="审核人名称" align="center" prop="checkerNames" />
      <el-table-column label="审批方式" align="center" prop="checkType" />
<!--      <el-table-column label="短信通知" align="center" prop="ismessage" />-->
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

    <!-- 添加或修改流程审核步骤对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="流程ID,外键 (主表: sys_workflow)" prop="workflowId">-->
<!--          <el-input v-model="form.workflowId" placeholder="请输入流程ID" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程名称" prop="workflowName">-->
<!--          <el-input v-model="form.workflowName" placeholder="请输入流程名称" />-->
<!--        </el-form-item>-->
        <el-form-item label="审核步骤" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入审核步骤的次序 = 排序字段" />
        </el-form-item>
        <el-form-item label="审核步骤名称" prop="nodeName">
          <el-input v-model="form.nodeName" placeholder="请输入审核步骤名称" />
        </el-form-item>
        <el-form-item label="审批人类别">
          <el-select v-model="form.checkerType" placeholder="请选择审批人类别">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核人名称" prop="checkerNames">
          <el-input v-model="form.checkerNames" placeholder="请选择审核人名称" />
        </el-form-item>
        <el-form-item label="审批方式">
          <el-select v-model="form.checkType" placeholder="请选择审批方式(">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.createDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="修改者的用户ID" prop="modifyBy">
          <el-input v-model="form.modifyBy" placeholder="请输入修改者的用户ID" />
        </el-form-item>
        <el-form-item label="修改时间" prop="modifyDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.modifyDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择修改时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="短信通知，0通知;1不通知" prop="ismessage">
          <el-input v-model="form.ismessage" placeholder="请输入短信通知，0通知;1不通知" />
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
import { listNode, getNode, delNode, addNode, updateNode, exportNode } from "@/api/system/workflowNode";

export default {
  name: "Node",
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
      // 流程审核步骤表格数据
      nodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orgId: undefined,
        nodeName: undefined,
        workflowId: undefined,
        workflowName: undefined,
        checkerType: undefined,
        checkerNames: undefined,
        checkType: undefined,
        sortOrder: undefined,
        createDate: undefined,
        modifyBy: undefined,
        modifyDate: undefined,
        ismessage: undefined,
        remarks: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nodeId: [
          { required: true, message: "审核步骤ID不能为空", trigger: "blur" }
        ],
        nodeName: [
          { required: true, message: "审核步骤名称不能为空", trigger: "blur" }
        ],
        workflowId: [
          { required: true, message: "流程ID不能为空", trigger: "blur" }
        ],
        workflowName: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        checkerType: [
          { required: true, message: "审批人类别不能为空", trigger: "blur" }
        ],
        checkerNames: [
          { required: true, message: "审核人名称不能为空", trigger: "blur" }
        ],
        checkType: [
          { required: true, message: "审批方式不能为空", trigger: "blur" }
        ],
        createDate: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        modifyDate: [
          { required: true, message: "修改时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程审核步骤列表 */
    getList() {
      this.loading = true;
      listNode(this.queryParams).then(response => {
        this.nodeList = response.rows;
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
        orgId: undefined,
        nodeId: undefined,
        nodeName: undefined,
        workflowId: undefined,
        workflowName: undefined,
        checkerType: undefined,
        checkerNames: undefined,
        checkType: undefined,
        sortOrder: undefined,
        createBy: undefined,
        createDate: undefined,
        modifyBy: undefined,
        modifyDate: undefined,
        ismessage: undefined,
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
      this.ids = selection.map(item => item.nodeId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程审核步骤";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const nodeId = row.nodeId || this.ids
      getNode(nodeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程审核步骤";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.nodeId != undefined) {
            updateNode(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addNode(this.form).then(response => {
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
      const nodeIds = row.nodeId || this.ids;
      this.$confirm('是否确认删除流程审核步骤编号为"' + nodeIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNode(nodeIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有流程审核步骤数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportNode(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
