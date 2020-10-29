
<template>
  <div class="contain-little">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="OA项目开发-项目组申请">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="primary text" @click="submissionOpen=true" > <span class="el-icon-plus" style="margin-right:3px;"></span>新建申请</el-button>
      <el-button type="danger" @click="submissionDelete" > <span class="el-icon-delete" style="margin-right:3px;"></span>删除</el-button>
      <el-button type="success" @click="submissionReport"> <span class="el-icon-message" style="margin-right:3px;"></span>报送</el-button>
      <el-button type="warning" @click="submissionExport"><span class="el-icon-download" style="margin-right:3px;"></span>导出</el-button>
    </div>

    <!--新建申请弹框一层-->
    <el-dialog
      title="新建项目申请"
      :visible.sync="submissionOpen"
      width="30%"
    >
      <el-form ref="submissionForm" :model="submissionForm">
        <el-form-item><span>标题</span>
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="submissionForm.text"
            maxlength="10"
            show-word-limit
            style="width:400px;margin-left:50px;"
          >
          </el-input>
        </el-form-item>
        <el-form-item><span>申请内容</span>
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="submissionForm.textarea2"
            style="width:400px;margin-left:20px;">
          </el-input></el-form-item>
        <el-form-item><span>审批人</span>

          <el-button type="primary" icon="el-icon-plus" circle size="small" style="margin-left:10px;" @click="submissionOpen1=true"></el-button>
          <span style="margin-left:10px;">注：审批顺序添加顺序依次审批</span>
        </el-form-item>
        <el-form-item style="padding:0 70px" >
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="OpenSave">保存</el-button>
    <el-button type="primary" @click="OpenSubmit">提交</el-button>
  </span>
    </el-dialog>

    <!--新建申请弹框二层-->
    <el-dialog
      title="添加审批人"
      :visible.sync="submissionOpen1"
      width="30%"
    >
      <el-form ref="submissionForm" :model="submissionForm">
        <el-form-item><span>审批人</span>
          <el-cascader :options="submissionForm.select" style="margin-left:20px;width:400px;"></el-cascader>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="OpenCancel">取 消</el-button>
    <el-button type="primary" @click="OpenSubmitForm">确 定</el-button>
  </span>
    </el-dialog>

    <!--table弹框-->
    <el-dialog
      title="审批项目申请"
      :visible.sync="submissionOpen2"
      width="40%"
    >
      <div style="height: 400px;width:200px;">
        <el-steps direction="vertical" :active="1" finish-status="success">
          <el-step title="丹尼尔" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="迈克尔" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="伊利斯" description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>
          <el-step title="翠丝"  description="丹尼尔 软件部 2020-05-22 这里是审核内容 如未填写默认为 审核通过"></el-step>

        </el-steps>
      </div>
      <div style="float:right;top:20px;right:50px;" class="dialogtext">
        <el-form ref="submissionForm" :model="submissionForm" label-width="80px">
          <el-form-item style="margin-top:90px;font-weight: bold">
            标题
            <el-input
              :disabled="true"
              style="width:350px;margin-left: 40px"
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="submissionForm.textarea1">
            </el-input>
            <div style="margin: 20px 0;"></div>

          </el-form-item>
          <el-form-item style="font-weight: bold">
            申请内容
            <el-input
              :disabled="true"
              style="width:350px;margin-left: 10px;margin-right:40px;"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入内容"
              v-model="submissionForm.textarea3">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>


    <!--修改项目申请1层-->
    <el-dialog
      title="修改项目申请"
      :visible.sync="submissionUpdate"
      width="30%"
    >
      <el-form ref="UpdataForm" :model="UpdataForm"  >
        <el-form-item><span>标题</span>
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="UpdataForm.text"
            maxlength="10"
            show-word-limit
            style="width:400px;margin-left:50px;"
          >
          </el-input>
        </el-form-item>
        <el-form-item><span>申请内容</span>
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="UpdataForm.textarea2"
            style="width:400px;margin-left:20px;">
          </el-input></el-form-item>
        <el-form-item><span>审批人</span>

          <el-button type="primary" icon="el-icon-plus" circle size="small" style="margin-left:10px;" @click="submissionUpdate2=true"></el-button>
          <span style="margin-left:10px;">注：审批顺序添加顺序依次审批</span>
        </el-form-item>
        <el-form-item style="padding:0 70px" >
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>


        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="submissionSave">保存</el-button>
    <el-button type="primary" @click="submissionSubmit">提交</el-button>
  </span>
    </el-dialog>

    <!--修改项目申请2层-->
    <el-dialog
      title="添加审批人"
      :visible.sync="submissionUpdate2"
      width="30%"
      >
      <el-form ref="UpdataForm" :model="UpdataForm">
        <el-form-item><span>审批人</span>
          <el-cascader :options="UpdataForm.select" style="margin-left:20px;width:400px;"></el-cascader>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="submissionCancel">取 消</el-button>
    <el-button type="primary" @click="submissionSubmitForm">确 定</el-button>
  </span>
    </el-dialog>

    <!-- seach栏-->

    <div class="seach" >
      <span style="margin-left:20px;" >申请时间</span>
      <el-date-picker
        style="margin-left:20px;"
        v-model="value1"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>
      <span style="margin-left:20px;">状态</span>
      <el-select v-model="value" placeholder="请选择状态" style="margin-left:20px;">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span style="margin-left:20px;">陈述</span>
      <el-input v-model="input" placeholder="请输入陈述理由" style="width:200px;margin-left:10px;"></el-input>



      <el-button type="primary" style="margin-left:20px;"> <span class="el-icon-search"></span>搜索</el-button>
      <el-button style="margin-left:20px;"> <span class="el-icon-refresh-right"></span>重置</el-button>

    </div>

    <el-table
      :data="tableData"
      style="width: 100%;margin-top:20px;"
      @row-click="submissionOpen2=true">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="date"
        label="申请时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="标题"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="申请内容">
      </el-table-column>
      <el-table-column
        prop="app"
        label="审批人"
      >
      </el-table-column>
      <el-table-column
        prop="time"
        label="状态"
        width="130">
      </el-table-column>
      <el-table-column
        prop="timetwo"
        label="当前审批人">
      </el-table-column>
      <el-table-column
      prop="caozuo"
      label="操作">
        <template slot-scope="scope">
          <!--  2是未报送按钮全部显示 -->
          <span class="el-icon-edit-outline" @click.stop="submissionUpdate=true">编辑</span>
          <span class="el-icon-delete" @click.stop="delsubmission">删除</span>
          <span class="el-icon-message" style="margin-left:5px;" @click.stop="submissionReport" >报送</span>

        </template>

    </el-table-column>


    </el-table>
    <el-pagination
      style="margin-top:15px"
      @size-change="submission1Change"
      @current-change="submission2Change"
      :current-page="submissionPage4"
      :page-sizes="[100, 200, 300, 400]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>


  </div>
</template>


<script>
  export default {
    name: "page-little",
    data(){
      return{
        submissionPage4:1,
        inputVisible: false,
        submissionOpen:false,
        submissionOpen1:false,
        submissionOpen2:false,
        submissionUpdate:false,
        submissionUpdate2:false,
        dynamicTags: ['张三', '李四', '王五'],
        input: '',
        //新建申请数据
        submissionForm:{
          text: '',
          textarea2: '',
          textarea1:"项目任务延时申请",
          textarea3:"因功能修改需重新调整，需增加任务时间，故作此申请",
          tags: [
            { name: '张三', type: 'info' },
            { name: '李四', type: 'info' },
            { name: '王五', type: 'info' },

          ],
          select:[{
            value: 'ziyuan',
            label: '软件部',
            children: [{
              value: 'axure',
              label: '任宝峰'
            }, {
              value: 'sketch',
              label: '嘉琪'
            }, {
              value: 'jiaohu',
              label: '安仔'
            }]

          }],

        },
        //编辑数据
        UpdataForm:{
          text: '',
          textarea2: '',
          select:[{
            value: 'ziyuan',
            label: '软件部',
            children: [{
              value: 'axure',
              label: '任宝峰'
            }, {
              value: 'sketch',
              label: '嘉琪'
            }, {
              value: 'jiaohu',
              label: '安仔'
            }]

          }],

        },


        //状态选择数据
        options: [{
          value: '选项1',
          label: '待审批'
        }, {
          value: '选项2',
          label: '通过'
        }, {
          value: '选项3',
          label: '拒绝'
        }, {
          value: '选项4',
          label: '全部'
        }, ],
        value: '',
        //部门选择数据
        res: [{
          value: '选项1',
          label: '软件部'
        }, {
          value: '选项2',
          label: '销售部'
        }, {
          value: '选项3',
          label: '技术部'
        }, {
          value: '选项4',
          label: '开发部'
        }, ],
        //表格数据
        tableData: [{
          date: '2016-05-02',
          name: '迈克尔',
          address: '小贷报表处理',
          app:'马克尔/河北省小贷管理系统',
          time:'0.5天',
          timetwo:'2020-05-16 17:30 至 20:30',
          bumen:'技术部',
          zhuangtai:'待审核',
          caozuo:'审批'
        },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },
          {
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },{
            date: '2016-05-02',
            name: '迈克尔',
            address: '小贷报表处理',
            app:'马克尔/河北省小贷管理系统',
            time:'0.5天',
            timetwo:'2020-05-16 17:30 至 20:30',
            bumen:'技术部',
            zhuangtai:'待审核',
            caozuo:'审批'
          },




        ],
        value: '',
        value1: '',
      }

    },

    methods:{
      //删除
      submissionDelete(){
        this.$confirm('确认是否删除?', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      //报送
      submissionReport(){
        this.$confirm('请确认是否提交报送？提交后不可进行修改', '提交报送', {
          confirmButtonText: "报送",
          cancelButtonText: "返回列表",
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '报送成功!'
          });
        }).catch(() => {
        });

      },
      // 导出
      submissionExport(){

      },
      // lookClose(done){
      //   this.$confirm('确认关闭？')
      //     .then(_ => {
      //       done();
      //     })
      //     .catch(_ => {});
      //
      // },
      //tag标签删除
      handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },
      handleClose2(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },

      // 删除
     delsubmission() {
        this.$confirm('确认是否删除?', '确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      //报送
      submissionReport(){
        this.$confirm('请确认是否提交报送？提交后不可进行修改', '提交报送', {
          confirmButtonText: "报送",
          cancelButtonText: "返回列表",
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '报送成功!'
          });
        }).catch(() => {
        });

      },
      submission(){
        this.submissionClick=true;
      },

      //添加批注人

      goBack(){

        this.$router.push({ path:'/myreader/index'})
      },
      submission1Change(val) {
        console.log(`每页 ${val} 条`);
      },
      submission2Change(val) {
        console.log(`当前页: ${val}`);
      },
      //新建保存提交
     OpenSave(){
        this.submissionOpen=false
      },
      OpenSubmit(){
        this.submissionOpen=false
      },
      //新建取消确定
      OpenCancel(){
        this.submissionOpen1=false
      },
      OpenSubmitForm(){
        this.submissionOpen1=false
      },

      //编辑保存提交
      submissionCancel(){
        this.submissionUpdate2=false
      },
      submissionSubmitForm(){
        this.submissionUpdate2=false
      },
      submissionSave(){
        this.submissionUpdate=false
      },
      submissionSubmit(){
        this.submissionUpdate=false
      },


    }
  }

</script>

<style lang="scss">
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  /*.submissionright{*/
    /*float:right;*/
    /*top:0;*/
    /*right:50px;*/
  /*}*/

  .dialogtext{
    position: absolute;

  }

  .header{
    margin-bottom: 20px;
  }
  .btn{
    margin-left: 20px;
  }
  .seach{
    margin-top: 20px;
  }
  .tables{
    margin-left: 20px;
    margin-top: 30px;
  }
  .el-pagination{
    float:right;
  }

</style>
