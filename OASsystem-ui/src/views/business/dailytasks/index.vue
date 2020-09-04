<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建申请</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
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
        >导出</el-button
        >
      </el-col>
    </el-row>
    <el-form :modal="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="任务时间">
        <el-date-picker
          v-model="queryParams.tasktime"
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
      <el-form-item label="部门">
        <el-select
          v-model="queryParams.deptId"
          placeholder="选择部门"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in bumenOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
<!--    列表-->
    <el-table
      ref="multipleTable"
      v-loading="loading"
      :data="taskList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="每日任务主题"
        prop="Daily_title"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="每日任务时间"
        prop="Daily_time"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="参与人"
        prop="Daily_people"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="更新时间"
        prop="Daily_newtime"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="部门"
        align="center"
        prop="deptId[0].dictLabel"
        width="180"
      >
      </el-table-column>
      <el-table-column
        label="状态"
        align="center"
        :formatter="statusFormat"
        width="100"
      >
      </el-table-column>
      <el-table-column label="任务状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click.stop="handleUpdate(scope.row)"
            >编辑</el-button
            >
          </div>
        </template>
      </el-table-column>
    </el-table>
<!--新建-->
    <el-dialog :title="title" :visible.sync="open" width="650px">

        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="标题"  prop="title">
          <el-input
            type="text"
            placeholder="请输入内容"
            maxlength="10"
            show-word-limit
            v-model="form.title"
          ></el-input>
        </el-form-item>
          <el-form-item label="部门" prop="bumenStatus">
            <el-checkbox-group v-model="form.bumenStatus" @change="handleCheckedCitiesChange">
<!--              <el-checkbox-button v-for="dict in department"-->
<!--                                  :key="dict.dictValue"-->
<!--                                  :label="dict.dictValue"-->
<!--                                  :name="dict.dictLabel"-->
<!--                                  border-->
<!--              ></el-checkbox-button>-->
              <el-checkbox v-for="dict in department"
                           :label="dict.dictLabel"
                           border>
                {{dict.dictLabel}}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="参与人员" prop="peoplevalue">
            <template>
              <el-transfer
                filterable
                :filter-method="filterMethod"
                filter-placeholder="请输入成员名称"
                v-model="form.peoplevalue"
                :data="canyupeople">
              </el-transfer>
            </template>
          </el-form-item>
          <el-form-item label="任务时间" prop="tasktime">
            <el-time-picker
              is-range
              v-model="form.tasktime"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>
    <!--        任务内容-->
          <el-form-item label="任务内容" prop="taskmain">
            <el-input
              type="textarea"
              placeholder="请输入"
              v-model="form.taskmain"
            >
            </el-input>
          </el-form-item>

        </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="save">保 存</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </div>
    </el-dialog>

<!--    编辑-->
    <el-dialog :title="title" :visible.sync="editopen" width="650px">
      <el-form ref="form" :model="editform" label-width="80px">
        <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="任务内容" name="1">
          <el-form-item label="标题">
            <el-input
              type="text"
              placeholder="请输入内容"
              maxlength="10"
              show-word-limit
              v-model="editform.title"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-tag v-for="item in editform.department">{{item.name}}</el-tag>
<!--            <el-radio-group v-model="form.bumenStatus">-->
<!--              <el-radio-->
<!--                v-for="dict in department"-->
<!--                :key="dict.dictValue"-->
<!--                :label="dict.dictValue"-->
<!--                border-->
<!--              >{{ dict.dictLabel }}</el-radio-->
<!--              >-->
<!--            </el-radio-group>-->
          </el-form-item>
<!--          参与人员-->
          <el-form-item label="参与人员">
            <el-tag v-for="item in editform.people">{{item.name}}</el-tag>
          </el-form-item>
<!--          任务时间-->
          <el-form-item label="任务时间">
            <el-time-picker
              is-range
              v-model="editform.tasktime"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围"
              disabled
            >
            </el-time-picker>
          </el-form-item>
<!--          任务内容-->
          <el-form-item label="任务内容">
            <el-input
              type="textarea"
              placeholder="请输入"
              v-model="editform.taskmain"
              disabled
            >
            </el-input>
          </el-form-item>
        </el-collapse-item>
          <!--        工作内容-->
          <el-form-item label="工作内容">
            <el-input
              type="textarea"
              placeholder="请输入"
              v-model="editform.workmain"
            >
            </el-input>
          </el-form-item>
          <!--        上传附件-->
          <el-form-item label="上传附件">
            <el-upload
              class="upload-demo"
              :action="uploadImgUrl"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="3"
              :on-exceed="handleExceed"
              :on-success="uploadsuccess"
              :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
        <!--          任务日志-->
        <el-collapse-item title="任务日志" name="2">
          <el-timeline>
            <el-timeline-item timestamp="2020/06/02" placement="top">
              <el-card  v-for="item in editform.timestamp">
                <h4>{{item.title}}</h4>
                <p v-for="dict in item.timecontent">{{dict.content}}</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2020/06/17" placement="top">
              <el-card>
                <h4>迈克尔 06/17 17:45</h4>
                <p>
                  <i class="el-icon-document"></i>
                  <span>OA系统首页设计.xd</span>
                  <span><i class="el-icon-download"></i></span>
                </p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-collapse-item>
      </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveEdit">保 存</el-button>
        <el-button type="primary" @click="submiteditForm">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
  // 引入日常任务数据方法
  import { taskList,editform} from "@/api/business/mywork/dailytask";
  import { listDept } from "@/api/system/dept";
  export default {
    data() {
      const generateData = _ => {
        const data = [];
        const namelist = ['迈克尔', '丹尼斯', '伊利斯', '卡梅隆'];
        const canyu = ['迈克尔', '丹尼斯', '伊利斯', '卡梅隆'];
        namelist.forEach((name, index) => {
          data.push({
            label: name,
            key: index,
            pinyin: canyu[index]
          });
        });
        return data;
      };
      return {
        // 穿梭框
        canyupeople:generateData(),

        bumenStatus:[],
        multiple:false,
        // 遮罩层
        loading: true,
        // 查询参数
        queryParams:{
          pageNum: 1,
          pageSize: 10,
          tasktime:undefined,
          status:undefined,
          deptId:''
        },
        title:"",
        open:false,
        editopen:false,
        // 表单参数
        form:{
          title:"",
          bumenStatus:[],
          tasktime:[new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)],
          peoplevalue:[],
          taskmain:"",
          workmain:"",
          timestamp:[]
        },
        taskList:"",
        editform:{
          title:"",
          // bumenStatus:[],
          tasktime:[new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)],
          // peoplevalue:[],
          taskmain:"",
          workmain:"",
          timestamp:[]
        },
        bumenOptions:[],
        // 状态数据字典
        statusOptions: [],
        // 表单校验
        rules: {
          title:[
            {required: true, message: "标题不能为空", trigger: "change" }
          ],
          bumenStatus:[
            {required: true, message: "部门不能为空", trigger: "change" }
          ],
          peoplevalue:[
            {required: true, message: "参与人员不能为空", trigger: "change" }
          ],
          tasktime:[
            {required: true, message: "任务时间不能为空", trigger: "change" }
          ]
        },
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
        // 隐藏展示
        activeNames: ['1'],
        // 穿梭框内容

        filterMethod(query, item) {
          return item.pinyin.indexOf(query) > -1;
        },
        uploadImgUrl: process.env.VUE_APP_BASE_API + "/system/attachment/files", // 上传的图片服务器地址
        // 上传列表
        fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
      }
    },
    created() {
      var data=taskList();
      this.taskList=data.data;
      this.total = data.total;
      this.loading = false;


      // 状态
      this.getDicts("sys_check_status").then(response => {
        this.statusOptions = response.data;
      });
      // 部门
      //获取部门列表
      listDept({parentId:'100'}).then(response => {
        response.data.forEach((val)=> this.bumenOptions.push({'dictValue': val.deptId,'dictLabel': val.deptName})
        )
      });
      //审批状态
      this.getDicts("sys_check_status").then(response => {
        this.statusOptions = response.data;
      });


    },
    methods:{
      /** 查询加班列表 */
      getList() {
        this.loading = true;
        taskList(this.queryParams).then(response => {
          this.taskList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 字典状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status[0].dictValue);
      },

      // 发布状态修改
      handleStatusChange(row) {
        let text = row.status === "0" ? "发布" : "未发布";
        this.$confirm('确认要改为"' + text + '""' + row.userName + '"吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          // return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.leaveId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.form.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 新建申请
      handleAdd(){
        this.open=true;
        this.title="新建"
      },
      // 多选框发生改变
      handleCheckedCitiesChange(value){
        let checkedCount = value.length;
        console.log(value,123)
      },
      // 编辑
      handleUpdate(){
        this.editopen=true;
        this.title="编辑"
        // 编辑内容
        var editdata=editform();
        this.editform=editdata.data[0].form;
        // this.timestamp=editdata.data[0].timestamp

      },
      // 删除
      handleDelete(){

      },
      // 报送
      handleReport(){

      },
      // 导出
      handleExport(){

      },
      // 行点击
      handleRowClick(row){
        this.editopen=true;
      },
      // 折叠面板
      handleChange(val){
        console.log(val);
      },
      // 保存新增和编辑
      save(){
        let that = this;
        this.$refs["form"].validate(valid => {
          if (valid) {

          }else{
            // 新增
            this.open = false;
            this.$confirm("请确认是否提交保存", "提交保存", {
              dangerouslyUseHTMLString: true,
              distinguishCancelAndClose: true,
              confirmButtonText: "提交",
              cancelButtonText: "返回列表",
              type: "warning"
            })
              .then(() => {
                addtask(this.form).then(response => {
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
          }
        })
      },
      // 提交表单
      submitForm() {
        let that = this;
        this.$refs["form"].validate(valid => {
          if (valid){
            that.open=false;
            that.$confirm('请确认是否提交日常任务','提交',{
              dangerouslyUseHTMLString: true,
              distinguishCancelAndClose: true,
              confirmButtonText: "提交",
              cancelButtonText: "返回列表",
              type: "warning"
            }).then(()=>{
              editTask(this.form).then(response => {
                if (response.code === 200) {
                  that
                    .$confirm("提交成功", "提交成功", {
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
            }).catch(()=>{
              that.reset();
              that.getList();
            })
          }else{
            // 这里是新增
            that.open = false;
            this.$confirm("请确认是否提交日常任务", "提交", {
              dangerouslyUseHTMLString: true,
              distinguishCancelAndClose: true,
              confirmButtonText: "提交",
              cancelButtonText: "返回列表",
              type: "warning"
            }).then(()=>{

            })
          }
        })
      },


      saveEdit(){
        let that = this;
        that.open = false;
        that.$confirm("请确认是否保存","提交保存",{
          // 是否将 message 属性作为 HTML 片段处理
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "保存",
          cancelButtonText: "返回列表",
          type: "warning"
        })
          .then(() => {
            editTask(this.editform).then(response => {
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
                    this.reset_editform();
                    that.getList();
                  });
              }
            });
          })
          .catch(() => {
            this.reset_editform();
            that.getList();
          });
      },
      submiteditForm(){
        let that = this;
        that.open = false;
        that.$confirm("请确认是否保存","提交保存",{
          // 是否将 message 属性作为 HTML 片段处理
          dangerouslyUseHTMLString: true,
          distinguishCancelAndClose: true,
          confirmButtonText: "保存",
          cancelButtonText: "返回列表",
          type: "warning"
        })
          .then(() => {
            editsubmitTask(this.editform).then(response => {
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
                    this.reset_editform();
                    that.getList();
                  });
              }
            });
          })
          .catch(() => {
            this.reset_editform();
            that.getList();
          });
      },
      // 重置
      // 表单重置
      reset_editform() {
        this.editform = {
          title: undefined,
          department:[],
          people:[],
          tasktime:undefined,
          taskmain:undefined,
          workmain:undefined,
          fileList:[],
          timestamp:[]
        };
        this.resetForm("form");
      },
      // 上传
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file,"上传");
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      uploadsuccess(file){
        console.log(file,"上传成功");
      }
    }
  }
</script>
<style>
  .el-button--primary.is-disabled{
    display: block;
    margin-left: 0;
    margin-right: 0;
  }
</style>
