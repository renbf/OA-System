<template>
  <div class="app-container travel_container projectdetail">
    <el-collapse v-model="activeNames" @change="handleChange">
      <div class="one" name="1">{{projectInfo.projectName}} 项目负责人：{{projectInfo.leaderName}}
        <el-button icon="el-icon-edit-outline" circle @click="handleEdit"></el-button>

        <el-button icon=" el-icon-switch-button" circle></el-button>

        <el-button icon="el-icon-delete" circle></el-button>
        <el-switch
          v-model="projectInfo.status"
          active-color="#999999"
          inactive-color="#ff4949"
          active-value="1"
          inactive-value="0"
          @change="changeStatusHandle"
        style="margin-left:10px;">
        </el-switch>
        <span style="margin-left:10px">禁用</span>
      </div>


        <el-card class="box-card">
          <div  style="width:30%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
            <el-form  ref="form" :model="projectInfo" label-width="80px">
              <el-form-item label="部门">
                <el-tag type="info">{{projectInfo.deptNames}}</el-tag>
              </el-form-item>
              <el-form-item label="项目任务">
                <el-tag type="info">{{projectInfo.taskNums}}件</el-tag>
              </el-form-item>
              <el-form-item label="项目时间">
                <div class="div1" >
                  <i class="el-icon-date" style="margin-left:10px;"></i>
                  <span style="margin-left:40px;color:rgba(221, 221, 221,0.8);" >{{projectInfo.projectStartDate}}</span>
                  <span style="margin-left:40px;">至</span>
                  <span style="margin-left:40px; color:rgba(221, 221, 221,0.8);">{{projectInfo.projectEndDate}}</span>
                </div>
              </el-form-item>
              <el-form-item label="理由陈述">
                <el-input type="textarea" v-model="projectInfo.projectDesc"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <div style="width:45%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
            <p>参与人员</p>
            <template v-for="item in deptMemberList">
              <p>{{item.deptName}}</p>
              <p>
                <el-tag type="info" v-for="item1 in item.members">{{item1.memberName}}</el-tag>
              </p>
            </template>
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

    <!--项目任务模块-->
    <!--项目任务模块-->
    <!--项目任务模块-->

    <div  style="margin-bottom: 10px">
      <span style="font-size:18px;font-weight: bold;margin-right:10px;">项目任务</span>
      <el-button type="primary" @click="add2"><i class=" el-icon-plus" style="margin-right:5px;" ></i>新建任务
      </el-button>
      <el-button type="danger"> <i class="el-icon-delete" style="margin-right:5px;"></i>删除</el-button>
      <el-button type="warning"><i class=" el-icon-download" style="margin-right:5px;"></i> 导出</el-button>
    </div>
    <div v-if="model=='列表'">

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

            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="model=='时间'">
<!--        项目进度甘特图-->
      <component :is="activeIndex"></component>
    </div>

    <el-dialog :title="addproject"
               :visible.sync="addopen"
               width="650px" class="abow_dialog">
      <el-form  ref="addform" :model="addform" :rules="addrules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="addform.projectName"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="leaderId">
          <el-select v-model="addform.leaderId" placeholder="请选择" ref="leaderName">
            <el-option
              v-for="item in userDeptUserList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="bumenStatus">
          <el-checkbox-group v-model="addform.bumenStatus" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="dict in department"
                         :label="dict.deptId"
                         border>{{dict.deptName}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="参与人员" prop="userList">
          <template>
            <el-transfer
              filterable
              :filter-method="filterMethod"
              filter-placeholder="请输入成员名称"
              v-model="addform.userList"
              :data="memberList">
            </el-transfer>
          </template>
        </el-form-item>
        <el-form-item label="项目日期" prop="tasktime">
          <el-date-picker
            v-model="addform.projectDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>

        </el-form-item>
        <!--        项目描述-->
        <el-form-item label="项目描述" prop="projectDesc">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="addform.projectDesc"
          >
          </el-input>
        </el-form-item>
        <el-form-item  label="状态"  prop="status">
          <el-switch
            v-model="addform.status"
            active-value="1"
            inactive-value="0"
            active-text="启用">
          </el-switch>
          <span style="font-size: 13px;color:#ccc;margin-left: 20px;">注:状态为启用时参与人才会显示此项目</span>
        </el-form-item>
        <el-collapse v-model="matters_needing_attention">
          <el-collapse-item title="项目注意事项" name="1">
            <div>
              1、当项目状态为“禁用”时，可对项目进行“删除”操作。
            </div>
            <div>
              2、当项目任务有人参与并发表过内容时，项目与项目任务则不能被删除。但可“关闭项目”与“项目任务”。
            </div>
            <div>3、项目任务状态为“禁用”时，可对任务进行“删除”操作。</div>
            <div>
              4、当“关闭项目”或“关闭任务”后“项目”与“任务”将仅能“查看”
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>


<!--项目任务模块-->
    <!--项目任务模块-->
    <!--项目任务模块-->



    <el-dialog :title="header1"
               :visible.sync="add3"
               width="800px" class="abow_dialog">
      <el-form  ref="taskform" :model="taskform" :rules="taskrules" label-width="80px">
        <el-form-item label="名称" prop="taskName">
          <el-input
            style="width:520px;"
            type="text"
            placeholder="请输入名称"
            v-model="taskform.taskName"
            maxlength="10"
            show-word-limit
          >
          </el-input>
        </el-form-item>
        <!--参与人员分栏模块-->
        <!--参与人员分栏模块-->
        <!--参与人员分栏模块-->
        <!--参与人员分栏模块-->


        <el-form-item label="参与人员" prop="userList">
          <el-transfer
            :titles="['项目成员', '参与成员']"
            filterable
            :filter-method="filterMethod"
            filter-placeholder="项目成员"
            v-model="taskform.userList"
            :data="taskMemberList"
            style="margin-bottom: 2px">
          </el-transfer>


        </el-form-item>
        <el-form-item label="任务时间" prop="taskDate">
          <el-date-picker
            v-model="taskform.taskDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <!--        项目描述-->
        <el-form-item label="任务描述" prop="taskDesc">
          <el-input
            :rows="8"
            type="textarea"
            placeholder="请输入内容"
            v-model="taskform.taskDesc"
            maxlength="120"
            show-word-limit
          >
          </el-input>
        </el-form-item>
        <el-form-item  label="状态"  prop="status">
          <el-switch
            v-model="taskform.status"
            active-value="1"
            inactive-value="0"
            active-text="启用">
          </el-switch>
          <span style="font-size: 13px;color:#ccc;margin-left: 20px;">注:状态为启用时参与人才会显示此项目</span>
        </el-form-item>
        <el-collapse v-model="matters_needing_attention">
          <el-collapse-item title="项目注意事项" name="1">
            <div>
              1、当项目状态为“禁用”时，可对项目进行“删除”操作。
            </div>
            <div>
              2、当项目任务有人参与并发表过内容时，项目与项目任务则不能被删除。但可“关闭项目”与“项目任务”。
            </div>
            <div>3、项目任务状态为“禁用”时，可对任务进行“删除”操作。</div>
            <div>
              4、当“关闭项目”或“关闭任务”后“项目”与“任务”将仅能“查看”
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">

        <el-button @click="taskCancel">取消</el-button>
        <el-button type="primary" @click="taskSubmitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import project_progress from './project_progress';
  import { getProjectInfo } from "@/api/business/mywork/myproject";
  import { userDeptList } from "@/api/system/dept";
  import { userDeptUsers } from "@/api/system/user";
  import { listBusiProject,editBusiProject,changeStatus,addBusiTask,listTask } from "@/api/business/mywork/myproject";

  export default {
    name: "detail",
    components: {
      project_progress
    },
    data() {
      return {
        addproject: '',
        addopen: false,
        add3:false,
        projectId:this.$route.query.projectId,
        addform: {
          projectName: '',
          leaderId:undefined,
          bumenStatus: [],
          projectDesc: '',
          projectDate: '',
          userList: [],
          status:''
        },
        addrules: {
          projectName: [{required: true, message: "项目名称不能为空", trigger: "change"}],
          bumenStatus: [{required: true, message: "部门不能为空", trigger: "change"}],
          userList: [{required: true, message: "参与人员不能为空", trigger: "change"}],
          projectDate: [{required: true, message: "项目时间不能为空", trigger: "change"}],
          projectDesc: [{required: true, message: "项目描述不能为空", trigger: "change"}],
          status: [{required: true, message: "状态必须选择", trigger: "change"}]
        },
        //责任人
        userDeptUserList:[],
        memberList:[],
        taskMemberList:[],
        department: [],
        matters_needing_attention: undefined,
        projectInfo: {},
        //部门成员列表
        deptMemberList: [],
        header1: '',
        taskform:{
          taskName:'',
          taskDate: '',
          taskDesc:'',
          status:'',
          userList: [],
        },
        taskrules: {
          taskName: [{required: true, message: "任务名称不能为空", trigger: "change"}],
          taskDate: [{required: true, message: "任务时间不能为空", trigger: "change"}],
          taskDesc: [{required: true, message: "任务描述不能为空", trigger: "change"}],
          userList: [{required: true, message: "参与人员不能为空", trigger: "change"}],
          status: [{required: true, message: "状态必须选择", trigger: "change"}]
        },
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
        activeIndex: 'project_progress',

        value: true,
      }
    },
    created() {
      // 状态
      this.getDicts("sys_check_status").then(response => {
        this.statusOptions = response.data;
      });
      this.getProject();
      //获取部门列表
      userDeptList().then(response => {
        if (response.code == 200) {
          this.department = response.data;
        }
      });
      this.getUserDeptUsers();
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
      add2(){

        this.header1 = "新项目任务";
        this.add3=true;

      },
      getProject() {
        let _this = this;
        getProjectInfo({projectId:_this.projectId}).then(response => {
          if(response.code == 200){
            _this.projectInfo = response.data;
            _this.projectInfo.projectStartDate = _this.projectInfo.projectStartDate.substring(0, 10);
            _this.projectInfo.projectEndDate = _this.projectInfo.projectEndDate.substring(0, 10);
            let busiProjectMembers= _this.projectInfo.busiProjectMembers;
            _this.getDeptMemberList(busiProjectMembers);
          }
        });
      },
      getDeptMemberList(busiProjectMembers) {
        let _this = this;
        let map = new Map();
        _this.deptMemberList = [];
        let deptMemberList = _this.deptMemberList;
        _this.taskMemberList = [];
        busiProjectMembers.forEach((val)=>{
          let deptId = val.deptId;
          _this.taskMemberList.push({key:val.memberId,label:val.memberName});
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
      getUserDeptUsers() {
        let _this = this;
        userDeptUsers().then(response => {
          if (response.code == 200) {
            _this.userDeptUserList = response.data;
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
      },

      handleEdit() {
        this.addproject = "修改项目";
        this.addopen = true;
        let projectInfo = this.projectInfo;
        let deptMemberList = this.deptMemberList;
        let busiProjectMembers = projectInfo.busiProjectMembers;
        let userList = [];
        let bumenStatus = [];
        busiProjectMembers.forEach((val) =>{
          userList.push(val.memberId);
        });
        deptMemberList.forEach((val) =>{
          bumenStatus.push(val.deptId);
        });
        this.handleCheckedCitiesChange(bumenStatus);
        this.addform = {
            projectName: projectInfo.projectName,
            leaderId:projectInfo.leaderId,
            bumenStatus: bumenStatus,
            projectDesc: projectInfo.projectDesc,
            projectDate: [projectInfo.projectStartDate,projectInfo.projectEndDate],
            userList: userList,
            status:projectInfo.status
        }
      },
      handleCheckedCitiesChange(list) {
        let _this = this;
        let userList = _this.addform.userList;
        _this.addform.userList = [];
        _this.memberList = [];
        for (var i in list) {
          let deptId = list[i];
          _this.userDeptUserList.forEach((val) => {
              if (val.deptId == deptId) {
                _this.memberList.push({key: val.userId,label: val.nickName});
              }
            }
          );
        }
      },
      filterMethod(query, item) {
        // let aa = item.label.indexOf(query) > -1;
        if (query == "") {
          return true;
        }else{
          return item.label.indexOf(query) > -1;
        }
      },
      cancel() {
        this.addopen = false;
      },
      taskCancel() {
        this.add3 = false;
      },
      //提交项目
      submitForm(){
        let _this = this;
        let leaderName = _this.$refs.leaderName.selected.label;
        _this.$refs.addform.validate(valid => {
          if (valid) {
            let addform = _this.addform;
            addform.leaderName = leaderName;
            addform.projectId = _this.projectId;
            if (addform.projectId != undefined) {
              editBusiProject(addform).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.addopen = false;
                  this.getProject();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {

            }
          }
        });
      },
      taskSubmitForm() {
        let _this = this;
        _this.$refs.taskform.validate(valid => {
          if (valid) {
            let form = _this.taskform;
            form.projectId = _this.projectId;
            if (form.taskId != undefined) {

            } else {
              addBusiTask(form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.addopen = false;
                  this.getProject();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      changeStatusHandle(value) {
        let _this = this;
        let form = {
          projectId:_this.projectId,
          status:value
        };
        changeStatus(form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("修改成功");
          } else {
            this.msgError(response.msg);
          }
        });
      }
    }

  }
</script>

<style>
  .div1{
    width: 360px;
    height: 30px;
    border-radius: 5px;
   line-height: 30px;
    border:1px solid rgba(221, 221, 221,0.8)
  }
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
  .sty{
    margin-left: 10px;
  }
  .one{
    height: 60px;
    line-height: 60px;
  }
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
