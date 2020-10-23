<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="报销id" prop="reimburseId">
        <el-input
          v-model="queryParams.reimburseId"
          placeholder="请输入报销id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="traSubName">
        <el-input
          v-model="queryParams.traSubName"
          placeholder="请输入项目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="人数" prop="peopleNum">
        <el-input
          v-model="queryParams.peopleNum"
          placeholder="请输入人数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="天数" prop="days">
        <el-input
          v-model="queryParams.days"
          placeholder="请输入天数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="补贴标准" prop="subsidyStandard">
        <el-input
          v-model="queryParams.subsidyStandard"
          placeholder="请输入补贴标准"
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
          v-hasPermi="['business:subsidy:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:subsidy:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:subsidy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:subsidy:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="subsidyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="travelId" />
      <el-table-column label="报销id" align="center" prop="reimburseId" />
      <el-table-column label="项目名称" align="center" prop="traSubName" />
      <el-table-column label="人数" align="center" prop="peopleNum" />
      <el-table-column label="天数" align="center" prop="days" />
      <el-table-column label="补贴标准" align="center" prop="subsidyStandard" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:subsidy:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:subsidy:remove']"
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

    <!-- 添加或修改交通费用对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报销id" prop="reimburseId">
          <el-input v-model="form.reimburseId" placeholder="请输入报销id" />
        </el-form-item>
        <el-form-item label="项目名称" prop="traSubName">
          <el-input v-model="form.traSubName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="人数" prop="peopleNum">
          <el-input v-model="form.peopleNum" placeholder="请输入人数" />
        </el-form-item>
        <el-form-item label="天数" prop="days">
          <el-input v-model="form.days" placeholder="请输入天数" />
        </el-form-item>
        <el-form-item label="补贴标准" prop="subsidyStandard">
          <el-input v-model="form.subsidyStandard" placeholder="请输入补贴标准" />
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
import { listSubsidy, getSubsidy, delSubsidy, addSubsidy, updateSubsidy, exportSubsidy } from "@/api/business/mywork/subsidy";

export default {
  name: "Subsidy",
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
      // 交通费用表格数据
      subsidyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reimburseId: undefined,
        traSubName: undefined,
        peopleNum: undefined,
        days: undefined,
        subsidyStandard: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        reimburseId: [
          { required: true, message: "报销id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询交通费用列表 */
    getList() {
      this.loading = true;
      listSubsidy(this.queryParams).then(response => {
        this.subsidyList = response.rows;
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
        travelId: undefined,
        reimburseId: undefined,
        traSubName: undefined,
        peopleNum: undefined,
        days: undefined,
        subsidyStandard: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
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
      this.ids = selection.map(item => item.travelId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加交通费用";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const travelId = row.travelId || this.ids
      getSubsidy(travelId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改交通费用";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.travelId != undefined) {
            updateSubsidy(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addSubsidy(this.form).then(response => {
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
      const travelIds = row.travelId || this.ids;
      this.$confirm('是否确认删除交通费用编号为"' + travelIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSubsidy(travelIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有交通费用数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSubsidy(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
