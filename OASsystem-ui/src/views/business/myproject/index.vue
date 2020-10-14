<template>
  <div class="app-container travel_container myproject">
    <el-row :gutter="10"  class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建项目</el-button
        >
      </el-col>
    </el-row>
    <el-row >
      <h3>进行中</h3>
    </el-row>

    <!--进行中项目模块-->
    <!--进行中项目模块-->
    <!--进行中项目模块-->
    <el-row class="doing" v-for="item in busiProjectUseList">

      <el-card class="box-card lf" >
        <div slot="header" class="clearfix">
          <span><b>{{item.projectName}}</b></span>
          <el-button icon="el-icon-setting" style="float: right;" circle @click.native="editproject(item)"></el-button>
          <p>{{item.projectStartDate}}至{{item.projectEndDate}}</p>
        </div>
        <div class="text item" @click="projectdetail(item)">
          <p>{{item.projectDesc}}</p>
          <p><span>总计项目任务:{{item.taskNums}}件</span><span>部门:{{item.deptNames}}</span></p>
          <el-progress :percentage="50"></el-progress>
        </div>
      </el-card>
    </el-row>

    <el-row>
      <h3 class="h3">已完成</h3>
    </el-row>
    <el-row class="end" v-for="item in busiProjectDoneList">
      <el-card class="box-card lf">
        <div slot="header" class="clearfix">
          <span><b>{{item.projectName}}</b></span>
          <p>{{item.projectStartDate}}至{{item.projectEndDate}}</p>
        </div>
        <div class="text item">
          <p>{{item.projectDesc}}</p>
          <p><span>总计项目任务:{{item.taskNums}}件</span><span>部门:{{item.deptNames}}</span></p>
          <p style="margin:0;font-size: 12px;">完成时间:2021-01-01(用时：130%)</p>
          <el-progress :percentage="100"  :format="format"></el-progress>
        </div>
      </el-card>
    </el-row>



<!--    新建项目-->
    <el-dialog :title="addproject"
               :visible.sync="addopen"
               width="800px" class="abow_dialog">
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
  </div>
</template>
<script>
  import { listDept,userDeptList } from "@/api/system/dept";
  import { userDeptUsers } from "@/api/system/user";
  import { listBusiProject,addBusiProject } from "@/api/business/mywork/myproject";
    export default {
      name: "index",
      data() {
        return {
          // 穿梭框
          // canyupeople: generateData(),
          addproject: '',
          addopen: false,
          addform: {
            projectName: '',
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
          department: [],

          matters_needing_attention: undefined,
// 穿梭框内容

          busiProjectUseList:[],
          busiProjectDoneList:[],
          //责任人
          userDeptUserList:[],
          memberList:[]
        }

      },
      created() {
        //获取部门列表
        userDeptList().then(response => {
          if (response.code == 200) {
            this.department = response.data;
          }
        });
        this.getlistBusiProject();
        this.getUserDeptUsers();
      },

      methods: {
        getlistBusiProject() {
          let _this = this;
          listBusiProject({projectProgress:'0'}).then(response => {
            if(response.code == 200){
              _this.busiProjectUseList = response.data;
              _this.formatList(_this.busiProjectUseList);
            }
          });

          listBusiProject({projectProgress:'1'}).then(response => {
            if(response.code == 200){
              _this.busiProjectDoneList = response.data;
              _this.formatList(_this.busiProjectUseList);
            }
          });

        },
        formatList(list) {
          list.forEach((val) =>{
            val.projectStartDate = val.projectStartDate.substring(0, 10);
            val.projectEndDate = val.projectEndDate.substring(0, 10);
          })
        },
        getUserDeptUsers() {
          let _this = this;
          userDeptUsers().then(response => {
            if (response.code == 200) {
              _this.userDeptUserList = response.data;
            }
          });
        },
        handleAdd() {
          this.addproject = "新建项目";
          this.addopen = true;
        },
        format(percentage) {
          return percentage === 100 ? '完成' : `${percentage}%`;
        },
        handleCheckedCitiesChange(list) {
          let _this = this;
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
          let a =_this.memberList;
        },
        filterMethod(query, item) {
          // let aa = item.label.indexOf(query) > -1;
          if (query == "") {
            return true;
          }else{
            return item.label.indexOf(query) > -1;
          }
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

              } else {
                addBusiProject(addform).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("新增成功");
                    this.addopen = false;
                  } else {
                    this.msgError(response.msg);
                  }
                });
              }
            }
          });
        },
        cancel() {
          this.addopen = false;
        },
        editproject(item) {
          let projectId = item.projectId;
          this.$router.push({ path:'/myproject/detail',query:{projectId:projectId}})
        },
        projectdetail(item) {
          this.$router.push({ path:'/myproject/look'})
        }
      }
    }
</script>

  <style>
   .myproject .doing,.myproject .end{
     width: 316px;
   }
  .myproject .doing .clearfix,.myproject .end .clearfix{
    margin:0;
    margin-top: 10px;
  }
.myproject .doing .el-card__header,.myproject .end .el-card__header{
  padding:2px 20px;
}
  .doing{
      display:inline-block;
      margin-bottom: 10px;
      margin-left: 50px;
    }
  .h3{
    margin-top: 300px;
  }
  .end{
    float: left;
    margin-left: 50px;
  }

</style>
