<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新建工作</el-button
        >
        <!-- v-hasPermi="['system:role:add']" -->
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:role:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-message"
          size="mini"
          @click="handleReport"
          >报送</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:post:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="提交时间">
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
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="选择状态"
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
      <el-form-item label="标题" prop="workTitle">
        <el-input
          v-model="queryParams.workTitle"
          placeholder="请输入标题"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>


    <el-table
      ref="multipleTable"
      v-loading="loading"
      :data="workList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-columnd
        label="标题"
        prop="workTitle"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="内容"
        prop="workContent"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="工作时间"
        align="center"
        prop="workTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="部门"
        align="center"
        prop="bumen[0].dictLabel"
        width="100"
      >
      </el-table-column>
      <el-table-column
        label="状态"
        align="center"
        :formatter="statusFormat"
        width="100"
      >
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!-- 不是已报送就显示 加入 1是已报送 -->
          <div v-if="scope.row.zhuangtai[0].dictValue !== 1">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click.stop="handleUpdate(scope.row)"
              >修改</el-button
            >
            <!-- v-hasPermi="['system:role:edit']" -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click.stop="handleDelete(scope.row)"
              v-hasPermi="['system:role:remove']"
              >删除</el-button
            >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-message"
              @click.stop="handleReport(scope.row)"
              v-hasPermi="['system:role:remove']"
              >报送</el-button
            >
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改工作日常对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="部门" prop="selectDepartment"> -->
        <el-form-item label="部门" prop="bumenStatus">
          <el-radio-group v-model="form.bumenStatus">
            <el-radio
              v-for="dict in department"
              :key="dict.dictValue"
              :label="dict.dictValue"
              border
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="标题" prop="workTitle">
          <el-input v-model="form.workTitle" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="工作时间" prop="workTime">
          <el-time-picker
            is-range
            v-model="form.workTime"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm"
            format="HH:mm"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="工作内容" prop="workContent">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="form.workContent"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="save">保 存</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </div>
    </el-dialog>
    <!-- 查看工作日常对话框 -->
    <el-dialog :title="title" :visible.sync="detail" width="500px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item
          label="部门"
          prop="bumenStatus"
          v-model="form.bumenStatus"
        >
          <el-input
            :key="dict.dictValue"
            class="width_to"
            v-for="dict in department"
            v-show="dict.dictValue == form.bumenStatus ? true : false"
            :value="dict.dictLabel"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="workTitle">
          <el-input
            v-model="form.workTitle"
            disabled
            placeholder="请输入标题"
          />
        </el-form-item>
        <el-form-item label="工作时间" prop="workTime">
          <el-time-picker
            disabled
            is-range
            v-model="form.workTime"
            readonly
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm"
            format="HH:mm"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="工作内容" prop="workContent">
          <el-input
            type="textarea"
            placeholder="请输入"
            disabled
            v-model="form.workContent"
          >
          </el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  addWork,
  detailWork,
  editWork,
  workList,
  exportWork,
  delWork,
  department,
  bumen
} from "@/api/business/mywork/work";

export default {
  name: "work",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      bumenStatus: undefined,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 日常工作表格数据
      workList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查看是否显示弹出层
      detail: false,
      openDataScope: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workTitle: undefined,
        routineKey: undefined,
        status: undefined,
        dateRange: ""
      },
      formPoint: {},
      // 表单参数
      form: {},
      // 默认时间选择框默认值
      // default_value:undefined,
      //部门label
      department: [
        {
          dictValue: "1",
          dictLabel: "技术部"
        },
        {
          dictValue: "2",
          dictLabel: "软件部"
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },

      // 表单校验
      rules: {
        bumenStatus: [
          { required: true, message: "部门不能为空", trigger: "change" }
        ],
        workTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        workTime: [
          { required: true, message: "工作时间不能为空", trigger: "blur" }
        ],
        workContent: [
          { required: true, message: "工作内容不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    //     this.getList();
    var data = bumen();
    this.workList = data.data;
    console.log(this.workList);
    this.total = data.total;
    //     报送状态获取
    this.statusOptions = [
      { dictValue: 1, dictLabel: "已报送" },
      { dictValue: 2, dictLabel: "未报送" }
    ];
    this.loading = false;
    //       this.getDicts("sys_normal_disable").then(response => {
    //       this.statusOptions = response.data;
    //     });
    //部门获取数据渲染radio
    //     department("sys_normal_disable").then(response => {
    //       this.department = response.data;
    //     });
  },
  methods: {
    /** 查询日常工作列表 */
    getList() {
      this.loading = true;
      workList(this.queryParams).then(response => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleRowClick(row, column, event) {
      if (column.label == "操作") {
      } else {
        // 这里的弹框标题是动态获取
        this.detail = true;
        let name = row.workName;
        this.title = name + " / 日常工作";
        this.reset();
        this.form = row;
        let bumen = this.form.bumenStatus;
        this.bumenStatus = "2";
        this.form.workTime = ["2020-05-27 09:13", "2020-05-27 09:13"];
      }
    },
    //     statusFormat
    // 字典状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(
        this.statusOptions,
        row.zhuangtai[0].dictValue
      );
    },
    // 取消按
    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.form = {
        workId: undefined,
        workTitle: undefined,
        workTime: undefined,
        bumenStatus: undefined,
        workContent: undefined
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
      this.queryParams.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;

      this.title = "新建日常工作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form = row;
      let bumen = this.form.bumenStatus;
      this.bumenStatus = "1";
      this.form.workTime = ["2020-05-27 09:13", "2020-05-27 09:13"];
      console.log(this.form, "this.form");
      this.open = true;
      this.title = "编辑日常工作";

      //       const roleId = row.workId || this.ids;
      //       this.$nextTick(() => {
      //         this.getRoleMenuTreeselect(roleId);
      //       });
      //       detailWork(workId).then(response => {
      //         this.form = response.data;
      //         this.open = true;
      //         this.title = "编辑日常工作";
      //       });
    },

    //     保存按钮提交
    save() {
      let that = this;
      this.$refs["form"].validate(valid => {
        // console.log(valid);
        if (valid) {
          console.log(this.form, "1111");
          if (this.form.workId != undefined) {
            that.open = false;
            that
              .$confirm("请确认是否提交保存", "提交保存", {
                dangerouslyUseHTMLString: true,
                distinguishCancelAndClose: true,
                confirmButtonText: "提交",
                cancelButtonText: "返回列表",
                type: "warning"
              })
              .then(() => {
                console.log(this.form, 111);
                // this.form.push({ isport: 1 });
                this.form.isport = 1;
                let time = that.getTime();
                console.log(time, "time");
                this.form.time = that.getTime();
                console.log(this.form, 222);
                editWork(this.form).then(response => {
                  if (response.code === 200) {
                    that
                      .$confirm("保存成功", "保存成功", {
                        dangerouslyUseHTMLString: true,
                        showConfirmButton: false,
                        distinguishCancelAndClose: true,
                        cancelButtonText: "返回列表",
                        type: "success"
                      })
                      .catch(() => {
                        this.reset();
                        that.getList();
                      });
                  }
                });
              })
              .catch(() => {
                this.reset();
                that.getList();
              });
          } else {
            //这个是新增
            that.open = false;
            this.$confirm("请确认是否提交保存", "提交保存", {
              dangerouslyUseHTMLString: true,
              distinguishCancelAndClose: true,
              confirmButtonText: "提交",
              cancelButtonText: "返回列表",
              type: "warning"
            })
              .then(() => {
                addWork(this.form).then(response => {
                  if (response.code === 200) {
                    that
                      .$confirm("保存成功", "保存成功", {
                        dangerouslyUseHTMLString: true,
                        showConfirmButton: false,
                        distinguishCancelAndClose: true,
                        cancelButtonText: "返回列表",
                        type: "success"
                      })
                      .catch(() => {
                        that.getList();
                      });
                  } else {
                    this.msgError(response.msg);
                  }
                });
              })
              .catch(action => {
                //点击返回列表清空form数据
                this.reset();
              });
            //     this.form.menuIds = this.getMenuAllCheckedKeys();
          }
        }
      });
    },
    /** 提交报送按钮 */
    submitForm() {
      let that = this;
      this.$refs["form"].validate(valid => {
        // console.log(valid);
        if (valid) {
          if (this.form.workId != undefined) {
            that.open = false;
            that
              .$confirm("请确认是否提交报送", "提交报送", {
                dangerouslyUseHTMLString: true,
                distinguishCancelAndClose: true,
                confirmButtonText: "提交",
                cancelButtonText: "返回列表",
                type: "warning"
              })
              .then(() => {
                editWork(this.form).then(response => {
                  if (response.code === 200) {
                    that
                      .$confirm("报送成功", "报送成功", {
                        dangerouslyUseHTMLString: true,
                        showConfirmButton: false,
                        distinguishCancelAndClose: true,
                        cancelButtonText: "返回列表",
                        type: "success"
                      })
                      .catch(() => {
                        that.reset();
                        that.getList();
                      });
                  }
                });
              })
              .catch(() => {
                that.reset();
                that.getList();
              });
          } else {
            //这个是新增
            that.open = false;
            this.$confirm("请确认是否提交报送", "提交报送", {
              dangerouslyUseHTMLString: true,
              distinguishCancelAndClose: true,
              confirmButtonText: "提交",
              cancelButtonText: "返回列表",
              type: "warning"
            })
              .then(() => {
                addWork(this.form).then(response => {
                  if (response.code === 200) {
                    that
                      .$confirm("报送成功", "报送成功", {
                        dangerouslyUseHTMLString: true,
                        showConfirmButton: false,
                        distinguishCancelAndClose: true,
                        cancelButtonText: "返回列表",
                        type: "success"
                      })
                      .catch(() => {
                        that.reset();
                        that.getList();
                      });
                  } else {
                    this.msgError(response.msg);
                  }
                });
              })
              .catch(action => {
                //点击返回列表清空form数据
                that.reset();
                that.getList();
              });
            //     this.form.menuIds = this.getMenuAllCheckedKeys();
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const workIds = row.workId || this.ids;
      let that = this;
      this.$confirm(
        "请确认是否删除工作编号为" + workIds + "的日常工作",
        "删除工作",
        {
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "删除",
          cancelButtonText: "返回列表",
          type: "warning"
        }
      )
        .then(() => {
          handleDelete(roleIds).then(response => {
            if (response.code === 200) {
              that
                .$confirm("删除成功", "删除成功", {
                  dangerouslyUseHTMLString: true,
                  showConfirmButton: false,
                  distinguishCancelAndClose: true,
                  cancelButtonText: "返回列表",
                  type: "success"
                })
                .catch(() => {
                  that.reset();
                  that.getList();
                });
            }
          });
        })
        .catch(() => {
          that.reset();
          that.getList();
        });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //报送日常工作
    handleReport(row) {
      let isPort = 1;
      let that = this;
      const workIds = row.workId || this.ids;
      this.$confirm(
        "请确认是否报送工作编号为" + workIds + "的日常工作",
        "报送工作",
        {
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "删除",
          cancelButtonText: "返回列表",
          type: "warning"
        }
      )
        .then(() => {
          reportWorkList(workIds).then(response => {
            if (response.code === 200) {
              that
                .$confirm("报送成功", "报送成功", {
                  dangerouslyUseHTMLString: true,
                  showConfirmButton: false,
                  distinguishCancelAndClose: true,
                  cancelButtonText: "返回列表",
                  type: "success"
                })
                .catch(() => {
                  that.reset();
                  that.getList();
                });
            }
          });
        })
        .catch(() => {
          that.reset();
          that.getList();
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      let that = this;
      const workIds = this.ids;
      this.$confirm(
        "请确认是否导出工作编号为" + workIds + "的日常工作",
        "导出工作",
        {
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "",
          cancelButtonText: "返回列表",
          type: "warning"
        }
      )
        .then(() => {
          reportWorkList(workIds).then(response => {
            if (response.code === 200) {
              that
                .$confirm("导出成功", "导出成功", {
                  dangerouslyUseHTMLString: true,
                  showConfirmButton: false,
                  distinguishCancelAndClose: true,
                  cancelButtonText: "返回列表",
                  type: "success"
                })
                .catch(() => {
                  that.reset();
                  that.getList();
                });
            }
          });
        })
        .catch(() => {
          that.reset();
          that.getList();
        });
    }
  }
};
</script>

<style lang="scss">
.span_right {
  margin-right: 10px;
}
.el-dialog__header {
  border-bottom: 1px solid #e4e7ed;
}
.width_to {
  width: 21% !important;
}
.el-collapse .el-collapse-item__header {
color: #f56c6c;
}
</style>
