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
    <el-row class="doing" v-for="(item,index) in busiProjectUseList">

      <el-card class="box-card lf" :class="statusClass(item)">
        <div slot="header" class="clearfix" @click="projectdetail(item)">
          <span><b>{{item.projectName}}</b></span>
          <el-button icon="el-icon-delete" style="float: right;" circle v-if="item.status == 0" @click="deleteProject(item)"></el-button>
          <el-button icon="el-icon-setting" style="float: right;margin-right:5px;" circle @click.stop="editproject(item)" v-if="userId == 1 || userId == item.leaderId" v-hasPermi="['api:busiProject:edit']"></el-button>
          <p>{{item.projectStartDate}}至{{item.projectEndDate}}</p>
        </div>
        <div class="text item"  @click="projectdetail(item)">
          <p>{{item.projectDesc}}</p>
          <p><span>总计项目任务:{{item.taskNums}}件</span><span>部门:{{item.deptNames}}</span></p>
          <el-progress :percentage="item.projectProgress"></el-progress>
        </div>
      </el-card>
    </el-row>

    <el-row>
      <h3 class="h3">已完成</h3>
    </el-row>
    <el-row class="end" v-for="item in busiProjectDoneList">
      <el-card class="box-card lf" >
        <div slot="header" class="clearfix" @click="projectFinish(item)">
          <span><b>{{item.projectName}}</b></span>
          <p>{{item.projectStartDate}}至{{item.projectEndDate}}</p>
        </div>
        <div class="text item" @click="projectFinish(item)">
          <p>{{item.projectDesc}}</p>
          <p><span>总计项目任务:{{item.taskNums}}件</span><span>部门:{{item.deptNames}}</span></p>
          <p style="margin:0;font-size: 12px;">完成时间:{{item.updateTime}}</p>
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
        <el-form-item label="项目时间" prop="projectDate">
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
  import { listBusiProject,addBusiProject,delBusiProject } from "@/api/business/mywork/myproject";
    export default {
      name: "index",
      data() {
        return {
          userId:this.$store.state.user.userId,
          cards:[
            "card1",
            "card2",
            "card3",

          ],
          addproject: '',
          addopen: false,
          addform: {
            projectName: '',
            bumenStatus: [],
            projectDesc: '',
            projectDate: '',
            userList: [],
            status:'1'
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
          //进行中
          busiProjectUseList:[],
          //已完成
          busiProjectDoneList:[],
          //责任人
          userDeptUserList:[],
          memberList:[]
        }

      },
      computed: {

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
        statusClass(item) {
          if (item.status == '0') {
            return 'card2';
          }else{
            return 'card1';
          }
        },
        getlistBusiProject() {
          let _this = this;
          listBusiProject().then(response => {
            if(response.code == 200){
              let projectList = response.data;
              _this.formatList(projectList);
              _this.busiProjectUseList = [];
              _this.busiProjectDoneList = [];
              projectList.forEach((val)=>{
                let projectProgress = parseInt(val.projectProgress);
                if(projectProgress < 100){
                  _this.busiProjectUseList.push(val);
                }else{
                  _this.busiProjectDoneList.push(val);
                }
              });
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
                    this.getlistBusiProject();
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
          let projectId = item.projectId;
          this.$router.push({ path:'/myproject/look',query:{projectId:projectId}})
        },
      projectFinish(item){
          let projectId= item.projectId;
          this.$router.push({ path:'/myproject/Finish',query:{projectId:projectId}})
        },
        deleteProject(item) {
          let _this = this;
          this.$confirm('是否确认删除项目吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return delBusiProject(item.projectId);
          }).then(() => {
            this.msgSuccess("删除成功");
            this.getlistBusiProject();
          }).catch(function() {});
        }
      }
    }
</script>

  <style>
    .card2{
      background-color: rgba(242, 242, 242,0.5);
      color:rgba(169, 169, 169,0.8);
    }
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
