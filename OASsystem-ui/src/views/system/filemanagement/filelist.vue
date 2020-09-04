<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="文件名称" prop="roleName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          size="small"
          style="width: 240px;"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" size="medium" @click="handleDataScope()"
          >上传文件</el-button
        >
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="fileLIST">
      <el-table-column
        label="文件id"
        prop="id"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="文件名称"
        prop="fileNameReal"
        :show-overflow-tooltip="true"
        width="150"
      >
      </el-table-column>
      <el-table-column
        label="上传时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
          <el-button
            type="text"
            icon="el-icon-tickets"
            size="mini"
            @click="handlePreview(scope.row)"
            >预览与打印</el-button
          >
          <el-button
            type="text"
            icon="el-icon-download"
            size="mini"
            @click="handleDownload(scope.row)"
            >下载</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="file_list"
    />

    <!-- 上传文件对话框 -->
    <el-dialog :title="title" :visible.sync="openDataScope" width="500px">
      <el-upload
        class="upload-demo"
        :action="upload.url"
        :limit="3"
        drag
        accept=".pdf"
        :file-list="fileList"
        :headers="upload.headers"
        :on-remove="handleRemove"
        :before-upload="beforeUpload"
        :on-exceed="handleExceed"
        :auto-upload="true"
        :on-success="onSuccess"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传pdf文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import {
  uploadFile,
  file_List,
  filePreview,
  delFile,
  delDiskFile
} from "@/api/system/file";
// import {  } from "@/api/system/PDF";
import { getToken } from "@/utils/auth";

export default {
  name: "upload_file",
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
      pdf: false,
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      // 只有按钮确定的弹框
      confirm_pdf: false,
      // 上传文件数据
      fileList: [],
      // 文件表格数据
      fileLIST: [],
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: undefined
      },
      // 表单参数
      form: {},
      //设置一些参数
      upload: {
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        //上传地址
        url: process.env.VUE_APP_BASE_API + "/system/attachment/files"
      }
    };
  },
  created() {
    this.file_list();
  },
  methods: {
    /** 查询文件列表 */
    file_list() {
      this.loading = true;
      file_List(this.queryParams).then(response => {
        this.fileLIST = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 预览
    handlePreview(row) {
      var fileId = row.id; //TODO  修改为实际获取方式
      filePreview(fileId)
        .then(res => {
          // 方法1：使用vue-pdf
          // this.src = pdf.createLoadingTask(res);
          // this.src.then(pdf => {
          //   this.viewVisible = true;
          // }).catch((err) => {
          //   console.log("err", err)
          // });

          // 方法2：使用pdf.js
          let blobData = new Blob([res.data]);
          let url = URL.createObjectURL(blobData);
          window.open(
            "../../../../static/pdf/web/viewer.html?file=" +
              encodeURIComponent(URL.createObjectURL(blobData))
          );
        })
        .catch(err => {
          console.log("err", err);
        });
    },
    // 下载
    handleDownload(row) {
      var fileId = row.id; //TODO  修改为实际获取方式
      let name = row.fileNameReal;
      filePreview(fileId)
        .then(res => {
          // 方法1：使用vue-pdf
          // this.src = pdf.createLoadingTask(res);
          // this.src.then(pdf => {
          //   this.viewVisible = true;
          // }).catch((err) => {
          //   console.log("err", err)
          // });

          // 方法2：使用pdf.js
          let blobData = new Blob([res.data]);
          let url = URL.createObjectURL(blobData);
          let link = document.createElement("a");
          link.style.display = "none";
          link.href = url;
          link.setAttribute("download", name);
          document.body.appendChild(link);
          link.click();
          // window.open(
          //   "../../../../static/pdf/web/viewer.html?file=" +
          //     encodeURIComponent(URL.createObjectURL(blobData))
          // );
        })
        .catch(err => {
          console.log("err", err);
        });
    },
    // 上传文件成功提示
    onSuccess(response, file, fileList) {
      if (response.code == 200) {

        var that = this;
        // setTimeout(function() {
        that.file_list();
        // that.fileList = [];
        //   that.openDataScope = false;
        // }, 1000);
        this.$message.success(`上传成功,不上传就关闭窗口啦~`);
      } else {

        this.$message({
          message: "上传失败了呦，可以再尝试一下呦~",
          type: "warning"
        });
      }
    },
    // 删除
    handleRemove(file, fileList) {
      this.$message({
        message: "这里并不会真正删除呦,移步下面吧!",
        type: "warning"
      });
      console.log(file, fileList);
    },
    // 上传前判断格式是否符合
    beforeUpload(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const extension = testmsg === "pdf";
      if (!extension) {
        this.$message({
          message: "上传文件只能是 pdf格式!",
          type: "warning"
        });
      }
      console.log(file, "111");
      //  me.$moment(me.dateValue2).format('YYYY-MM-DD HH:mm:ss');
      return extension;
    },
    // 限制上传文件个数
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件!`);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.file_list();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 文件上传弹框弹出 */
    handleDataScope() {
      this.fileList = [];
      this.openDataScope = true;
      this.title = "文件上传";
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let that = this;
      const roleId = row.id;
      this.$confirm('是否确认删除文件编号为"' + roleId + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
          return delFile(roleId).then(data => {
            that.file_list();
            //删除磁盘数据
            return delDiskFile(data.path).then(res => {

            });
          });
        })
        .catch(error => {
          that.$message.warning(`删除失败!`);
        });
    }
  }
};
</script>
