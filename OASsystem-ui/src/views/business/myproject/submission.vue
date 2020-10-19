
<template>
  <div class="contain-little">
    <!--导航栏-->
    <el-card class="header">
      <el-page-header @back="goBack" content="OA项目开发-项目组申请">
      </el-page-header>

    </el-card>
    <!--按钮区-->

    <div class="btn">
      <el-button type="primary text" @click="dialogVisible=true" > <span class="el-icon-plus" style="margin-right:3px;"></span>新建申请</el-button>
      <el-button type="danger"> <span class="el-icon-delete" style="margin-right:3px;"></span>删除</el-button>
      <el-button type="success"> <span class="el-icon-message" style="margin-right:3px;"></span>报送</el-button>
      <el-button type="warning"><span class="el-icon-download" style="margin-right:3px;"></span>导出</el-button>
    </div>


    <!--新建申请弹框一层-->
    <!--新建申请弹框一层-->
    <!--新建申请弹框一层-->
    <el-dialog
      title="新建项目申请"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="lookClose">
      <el-form>
        <el-form-item><span>标题</span>
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="text"
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
            v-model="textarea2"
            style="width:400px;margin-left:20px;">
          </el-input></el-form-item>
        <el-form-item><span>审批人</span>

          <el-button type="primary" icon="el-icon-plus" circle size="small" style="margin-left:10px;" @click="dialog2=true"></el-button>
          <span style="margin-left:10px;">{{annotation}}</span>
        </el-form-item>
        <el-form-item style="padding:0 70px" >
          <el-tag style="width:70px;margin-right:5px;" type="info" >{{one}}</el-tag><i class="el-icon-arrow-right"></i>
          <el-tag style="width:70px;margin-right:5px;" type="info">{{two}}</el-tag><i class="el-icon-arrow-right"></i>
          <el-tag style="width:70px;margin-right:5px;" type="info">{{tree}}</el-tag><i class="el-icon-arrow-right"></i>
          <el-tag style="width:70px;margin-right:5px;" type="info">{{four}}</el-tag><i class="el-icon-arrow-right"></i>



        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">保存</el-button>
    <el-button type="primary" @click="dialogVisible = false">提交</el-button>
  </span>
    </el-dialog>





    <!--新建申请弹框二层-->
    <!--新建申请弹框二层-->
    <!--新建申请弹框二层-->
    <el-dialog
      title="添加审批人"
      :visible.sync="dialog2"
      width="30%"
      :before-close="handleClose2">
      <el-form>
        <el-form-item><span>审批人</span>
          <el-cascader :options="select" style="margin-left:20px;width:400px;"></el-cascader>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialog2= false">取 消</el-button>
    <el-button type="primary" @click="dialog2 = false">确 定</el-button>
  </span>
    </el-dialog>
<!--table弹框三层-->
    <!--table弹框三层-->
    <!--table弹框三层-->


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
         @row-click="submissionClick"

      :data="tableData"
      style="width: 100%">
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
          <span class="el-icon-edit-outline">编辑</span>
          <span class="el-icon-delete" style="margin-left:5px;">删除</span>
          <span class="el-icon-message" style="margin-left:5px;">报送</span>

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
        submissionopen:"false",


        // 审批人
        one:'张三',
        two:'张三',
        tree:'张三',
        four:"李四",


        //新建项目审批注释
        annotation:"注：审批顺序添加顺序依次审批",
        //新建项目申请里标题数据
        text: '',
        //新建项目申请里申请内容
        textarea2: '',
        //新建任务弹框布尔类型确认谈框
        //新建任务弹框布尔类型确认谈框
        //新建任务弹框布尔类型确认谈框

        dialogVisible: false,
        dialog2:false,
        //陈述理由数据
        input: '',

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

        }]

      }

    },

    methods:{
      lookClose(done){
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});

      },
      handleClose2(){

      },
      submissionClick(){
        this.submissionopen=true;
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
      }
    }
  }

</script>

<style lang="scss">

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
