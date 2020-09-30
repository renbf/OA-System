<template>
  <div class="app-container travel_container projectdetail">
    <el-collapse v-model="activeNames" @change="handleChange">
      <el-collapse-item :title="title" name="1">
        <el-card class="box-card">
          <div  style="width:30%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
            <el-form  ref="form" :model="detailform" label-width="80px">
              <el-form-item label="部门">
                <el-tag type="info">软件部</el-tag>
              </el-form-item>
              <el-form-item label="项目任务">
                <el-tag type="info">35件</el-tag>
              </el-form-item>
              <el-form-item label="项目时间">
                <el-date-picker
                  v-model="detailform.time"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="理由陈述">
                <el-input type="textarea" v-model="detailform.remark"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <div style="width:45%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
            <p>参与人员</p>
            <p>软件部</p>
            <p>
              <el-tag type="info">迈克尔</el-tag>
              <el-tag type="info">丹尼尔</el-tag>
              <el-tag type="info">斯坦</el-tag>
            </p>
            <p>设计部</p>
            <p>
              <el-tag type="info">迈克尔</el-tag>
              <el-tag type="info">丹尼尔</el-tag>
              <el-tag type="info">斯坦</el-tag>
            </p>
          </div>
          <div style="width: 15%;float:left;padding:0 20px;position: relative">
            <svg-bar :value="projectprocess" :options="projectoptions" style="position:absolute;left:27px;top:7px"></svg-bar>
            <svg-bar :value="timeprocess" :options="timeoptions" style="position:absolute"></svg-bar>
            <div style="position: absolute;width: 192px;height: 220px;left:32px;top:20px" class="clear">
                <div class="lf" style="width:50%;height:80%;border-right: 2px dotted #ddd;text-align: center">
                  <p style="margin-top:60%;margin-bottom:0">
                    <span style="display: inline-block;width:6px;height:12px;background:#1989FA"></span>
                    <span>任务进度</span>
                  </p>
                  <p style="margin-top: 0"><b style="font-size: 25px;">{{projectprocess}}%</b></p>
                </div>
                <div class="lf" style="width:50%;height: 100%;text-align: center">
                  <p style="margin-top: 60%;margin-bottom:0">
                    <span style="display: inline-block;width:6px;height:12px;background:#C6E2FF"></span>
                    <span>时间进度</span>
                  </p>
                  <p style="margin-top: 0"><b style="font-size: 25px;">{{timeprocess}}%</b></p>
                </div>
            </div>
          </div>
        </el-card>
      </el-collapse-item>
    </el-collapse>
<!--    项目组申请-->
    <div>
      <p class="apply">
        <span>项目组申请</span>
        <span>
          <el-button>全部报送</el-button>
        </span>
        <span>
          <el-button icon="el-icon-right" size="small" circle></el-button>
        </span>
      </p>
    </div>
    <div class="card-carousel-wrapper">
      <el-button class="card-carousel--nav__left" type="info" icon="el-icon-arrow-left" circle @click="moveCarousel(-1)" :disabled="atHeadOfList"></el-button>
      <div class="card-carousel">
        <div class="card-carousel--overflow-container">
          <div class="card-carousel-cards clear" :style="{ transform: 'translateX' + '(' + currentOffset + 'px' + ')'}">
            <div class="card-carousel--card lf" v-for="item in applyproject">
              <div style="border-bottom:1px solid #ddd">
                <p>
                  <span style="margin-left: 10px;"><b>{{item.title}}</b></span>
                  <span class="rt" style="margin-right: 10px;">
                    <el-button circle icon="el-icon-message" type="danger"></el-button>
                  </span>
                  <span class="rt" style="margin-right: 10px;">
                    <el-button circle icon="el-icon-delete"></el-button>
                  </span>
                </p>
                <p style="margin-left: 10px; font-size: 12px;color:#C0C4CC">申请时间:{{item.applytime}}</p>
              </div>
              <div>
                <p style="margin-left: 10px;font-size: 13px;">申请人：{{item.applypeople}}</p>
                <p style="margin-left: 10px;font-size: 13px;">原  因：{{item.applyreason}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-button class="card-carousel--nav__right" type="info" icon="el-icon-arrow-right" circle @click="moveCarousel(1)" :disabled="atEndOfList"></el-button>
    </div>

<!--    更换视图-->
  <div class="clear">
    <span class="rt">
      <span class="el-icon-menu" @click="changemodel"></span>
      <span>{{model}}模式</span>
    </span>
  </div>
  <h3>项目任务</h3>
    <div v-if="model=='列表'">
      <el-row :gutter="10" class="mb8">
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
        <el-form-item label="任务状态">
          <el-select
            v-model="queryParams.projectstatus"
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
        <el-form-item label="状态">
          <el-select
            v-model="queryParams.status"
            placeholder="选择部门"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in status"
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
<!--      列表-->
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="taskList"
        @selection-change="handleSelectionChange"
        @row-click="handleRowClick"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="编号"
          prop="number"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="标题"
          prop="title"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="任务内容"
          prop="content"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="更新日期"
          prop="updata"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="参与人"
          prop="joinpeople"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="开始时间"
          prop="begintime"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="结束时间"
          prop="endtime"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="任务进度"
          prop="taskprogress"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="时间进度"
          prop="timeprogress"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="任务状态"
          prop="taskstatus"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="状态"
          prop="status"
          :show-overflow-tooltip="true"
        />
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
    </div>
    <div v-if="model=='时间'">
<!--        项目进度甘特图-->
      <component :is="activeIndex"></component>
    </div>
  </div>
</template>

<script>
  import project_progress from './project_progress';
  import { getProjectInfo } from "@/api/business/mywork/myproject";
  export default {
    name: "detail",
    components: {
      project_progress
    },
    data() {
      return {
        projectId:this.$route.query.projectId,
        projectInfo: {},
        deptMemberList: [],
        title: "OA项目开发 编号：xcv23456 项目人：迈克尔",
        currentOffset: 0,
        windowSize: 3,
        paginationFactor: 322,
        loading: false,
        model: '列表',
        applyproject: [
          {
            title: '任务时间增加申请1',
            applytime: '2020-05-21',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请2',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请3',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请4',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请5',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请6',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请7',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请8',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请9',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请10',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          }
        ],
        activeNames: ['1'],
        detailform: {
          time: "",
          remark: '公司内容OA系统开发项目，用于公司OA系统的整体开发，涉及工作管理模块，我的部门模块，我的工作模块，系统设置模块等。'
        },
        projectprocess: '30',
        timeprocess: '60',
        statusOptions: [],
        queryParams: {
          tasktime: '',
          projectstatus: '',
          status: ""
        },
        status: [
          {
            dictValue: "1",
            dictLabel: "未报送"
          },
          {
            dictValue: "2",
            dictLabel: "已报送"
          }
        ],
        taskList: [
          {
            number: '#01',
            title: '报销数据库设计',
            content: '报销数据库设计整体设计',
            updata: '2020-07-01',
            joinpeople: '12人',
            begintime: '2020-07-01',
            endtime: '2020-07-02',
            taskprogress: '30%',
            timeprogress: '50%',
            taskstatus: '正常',
            status: '报送'
          },
          {
            number: '#01',
            title: '报销数据库设计',
            content: '报销数据库设计整体设计',
            updata: '2020-07-01',
            joinpeople: '12人',
            begintime: '2020-07-01',
            endtime: '2020-07-02',
            taskprogress: '30%',
            timeprogress: '50%',
            taskstatus: '正常',
            status: '报送'
          },
        ],
        activeIndex: 'project_progress'
      }
    },
    created() {
      // 状态
      this.getDicts("sys_check_status").then(response => {
        this.statusOptions = response.data;
      });
      this.getProject();
    },
    computed: {
      atEndOfList() {
        return this.currentOffset <= (this.paginationFactor * -1) * (this.applyproject.length - this.windowSize);
      },
      atHeadOfList() {
        return this.currentOffset === 0;
      },
      projectoptions: function () {
        return {
          radius: 100,//大小
          duration: 2,//变化时间长度
          circleWidth: 7,//圆环宽度
          pathColors: ['#eee', '#1989FA'], //圆环 底色和 上面的颜色
          circleLineCap: 'round',//进度条 形状  圆环 或者矩形
          text: function (value) {
            return '';
          },

        }
      },
      timeoptions: function () {
        return {
          radius: 107,//大小
          duration: 2,//变化时间长度
          circleWidth: 7,//圆环宽度
          pathColors: ['#eee', '#C6E2FF'], //圆环 底色和 上面的颜色
          circleLineCap: 'round',//进度条 形状  圆环 或者矩形
          text: function (value) {
            return '';
          },

        }
      },
    },
    methods: {
      getProject() {
        let _this = this;
        getProjectInfo({projectId:_this.projectId}).then(response => {
          if(response.code == 200){
            _this.projectInfo = response.data;
            let busiProjectMembers= _this.projectInfo.busiProjectMembers;
            _this.getDeptMemberList(busiProjectMembers);
          }
        });
      },
      getDeptMemberList(busiProjectMembers) {
        let _this = this;
        let map = new Map();
        let deptMemberList = _this.deptMemberList;
        busiProjectMembers.forEach((val)=>{
          let deptId = val.deptId;
          if (map.has(deptId)) {
            let index = map.get(deptId);
            let deptObj = deptMemberList[index];
            let members = deptObj.members;
            members.push({memberId:val.memberId,memberName:val.memberName});
          }else{
            let size = map.size;
            map.set(deptId,size);
            let members = [];
            members.push({memberId:val.memberId,memberName:val.memberName});
            deptMemberList.push({deptId:deptId,deptName:val.deptName,members:members});
          }
        });

      },
      moveCarousel(direction) {
        // Find a more elegant way to express the :style. consider using props to make it truly generic
        if (direction === 1 && !this.atEndOfList) {
          this.currentOffset -= this.paginationFactor;
        } else if (direction === -1 && !this.atHeadOfList) {
          this.currentOffset += this.paginationFactor;
        }
      },
      handleChange() {
      },
      handleReport() {
      },
      handleExport() {
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        // this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.form.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleRowClick() {
      },
      handleSelectionChange() {
      },
      handleUpdate() {
      },
      changemodel() {
        if (this.model == '时间') {
          this.model = '列表'
        } else {
          this.model = '时间'
        }
      }

    }

  }
</script>

<style>
  .projectdetail .gantt-elastic__grid-line-time {
    display: none;
  }
  .projectdetail .el-button--primary.is-disabled{
    display: block;
    margin-left: 0;
    margin-right: 0;
  }
  .projectdetail .el-button--small.is-circle{
    padding:3px;
    font-size: 20px;
  }
  .projectdetail .card-carousel-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 20px 0 40px;
    color: #666a73;
  }

  .projectdetail .card-carousel {
    display: flex;
    justify-content: center;
    width:85%;
  }
  .projectdetail .card-carousel--overflow-container {
    overflow: hidden;
  }
  /*.projectdetail .card-carousel--nav__left, .card-carousel--nav__right {*/
  /*  display: inline-block;*/
  /*  width: 15px;*/
  /*  height: 15px;*/
  /*  padding: 10px;*/
  /*  box-sizing: border-box;*/
  /*  border-top: 2px solid #42b883;*/
  /*  border-right: 2px solid #42b883;*/
  /*  cursor: pointer;*/
  /*  margin: 0 10px;*/
  /*  transition: transform 150ms linear;*/
  /*}*/
  .projectdetail .card-carousel--nav__left[disabled], .card-carousel--nav__right[disabled] {
    opacity: 0.2;
    border-color: black;
  }
  /*.projectdetail .card-carousel--nav__left {*/
  /*  transform: rotate(-135deg);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__left:active {*/
  /*  transform: rotate(-135deg) scale(0.9);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__right {*/
  /*  transform: rotate(45deg);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__right:active {*/
  /*  transform: rotate(45deg) scale(0.9);*/
  /*}*/

 .projectdetail .card-carousel-cards {
    display: block;
    transition: transform 150ms ease-out;
    transform: translatex(0px);
    width: 4000px;
   overflow: hidden;
  }
  .projectdetail .card-carousel-cards .card-carousel--card {
    width:322px;
    height:184px;
    margin: 0 10px;
    cursor: pointer;
    border:2px solid #ddd;
    background-color: #fff;
    border-radius: 10px;
    z-index: 3;
    margin-bottom: 2px;
  }
  .projectdetail .card-carousel-cards .card-carousel--card:first-child {
    margin-left: 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card:last-child {
    margin-right: 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer {
    border-top: 0;
    padding: 7px 15px;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p {
    padding: 3px 0;
    margin: 0;
    margin-bottom: 2px;
    font-size: 19px;
    font-weight: 500;
    color: #2c3e50;
    user-select: none;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2) {
    font-size: 12px;
    font-weight: 300;
    padding: 6px;
    background: rgba(40, 44, 53, 0.06);
    display: inline-block;
    position: relative;
    margin-left: 4px;
    color: #666a73;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2):before {
    content: "";
    float: left;
    position: absolute;
    top: 0;
    left: -12px;
    width: 0;
    height: 0;
    border-color: transparent rgba(40, 44, 53, 0.06) transparent transparent;
    border-style: solid;
    border-width: 12px 12px 12px 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2):after {
    content: "";
    position: absolute;
    top: 10px;
    left: -1px;
    float: left;
    width: 4px;
    height: 4px;
    border-radius: 2px;
    background: white;
    box-shadow: -0px -0px 0px #004977;
  }

  .projectdetail .lf{
    float: left;
  }
  .projectdetail .rt{
    float: right;
  }
  .projectdetail .clear{
    clear:both;
    content:"";
    display: block;
  }
  .projectdetail .apply >span{
    margin-left: 20px;
  }
</style>


<style lang="scss" scoped>
  .demo{
  padding: 5px 15px;
  position: relative;
  .chart-title {
    position: absolute;
    transform: translateX(-50%);
    left: 50%;
    i {
      font-style: normal;
      padding-right: 20px;
    }
    i:before {
      content: '';
      display: inline-block;
      width: 30px;
      height: 15px;
      border-radius: 5px;
      vertical-align: bottom;
      margin-right: 3px;
      background-color: #3e84e9;
    }
    i.to-be-completed:before {
      background-color: #d4cece;
    }
    i.timeout:before {
      background-color: #c23531;
    }
  }
  #gantt-chart {
    margin: 1em auto;
    height: 500px;
    width: 100%;
  }
  }
</style>
