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
      <el-form-item label="交通类型" prop="trafficType">
        <el-select v-model="queryParams.trafficType" placeholder="请选择交通类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="起始位置" prop="departureStation">
        <el-input
          v-model="queryParams.departureStation"
          placeholder="请输入起始位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="终点位置" prop="terminalStation">
        <el-input
          v-model="queryParams.terminalStation"
          placeholder="请输入终点位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额" prop="amount">
        <el-input
          v-model="queryParams.amount"
          placeholder="请输入金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="票据数量" prop="billsNum">
        <el-input
          v-model="queryParams.billsNum"
          placeholder="请输入票据数量"
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
          v-hasPermi="['business:fee:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:fee:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:fee:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:fee:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="feeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="trafficId" />
      <el-table-column label="报销id" align="center" prop="reimburseId" />
      <el-table-column label="交通类型" align="center" prop="trafficType" />
      <el-table-column label="时间" align="center" prop="trafficDate" />
      <el-table-column label="起始位置" align="center" prop="departureStation" />
      <el-table-column label="终点位置" align="center" prop="terminalStation" />
      <el-table-column label="金额" align="center" prop="amount" />
      <el-table-column label="票据数量" align="center" prop="billsNum" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:fee:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:fee:remove']"
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
        <el-form-item label="交通类型">
          <el-select v-model="form.trafficType" placeholder="请选择交通类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间" prop="trafficDate">
          <el-input v-model="form.trafficDate" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="起始位置" prop="departureStation">
          <el-input v-model="form.departureStation" placeholder="请输入起始位置" />
        </el-form-item>
        <el-form-item label="终点位置" prop="terminalStation">
          <el-input v-model="form.terminalStation" placeholder="请输入终点位置" />
        </el-form-item>
        <el-form-item label="金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="票据数量" prop="billsNum">
          <el-input v-model="form.billsNum" placeholder="请输入票据数量" />
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
import { listTrafficFee, getTrafficFee, delTrafficFee, addTrafficFee, updateTrafficFee, exportTrafficFee } from "@/api/business/mywork/trafficfee";

export default {
  name: "Fee",
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
      feeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reimburseId: undefined,
        trafficType: undefined,
        trafficDate: undefined,
        departureStation: undefined,
        terminalStation: undefined,
        amount: undefined,
        billsNum: undefined,
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
      listFee(this.queryParams).then(response => {
        this.feeList = response.rows;
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
        trafficId: undefined,
        reimburseId: undefined,
        trafficType: undefined,
        trafficDate: undefined,
        departureStation: undefined,
        terminalStation: undefined,
        amount: undefined,
        billsNum: undefined,
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
      this.ids = selection.map(item => item.trafficId)
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
      const trafficId = row.trafficId || this.ids
      getFee(trafficId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改交通费用";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.trafficId != undefined) {
            updateFee(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addFee(this.form).then(response => {
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
      const trafficIds = row.trafficId || this.ids;
      this.$confirm('是否确认删除交通费用编号为"' + trafficIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFee(trafficIds);
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
          return exportFee(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
