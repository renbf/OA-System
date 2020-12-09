
<template>
  <div class="contain-reader">
    <el-card class="box-card" v-for="item in req">
    <div slot="header" class="clearfix">

      <span class="big" >{{item.titles}}</span>
      <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"  @click.native="edit(item)"></el-button>
    </div>
    <div class="father"> <span class="son">{{item.ready}}</span></div>
    <div class="father"> <span class="conten">{{item.number}}</span>件</div>
  </el-card>


</div>
</template>


<script>
  import {approveList as approveListLittle} from "@/api/business/mywork/extrawork";
  import {approveList as approveListLeave} from "@/api/business/mywork/leave";
  import {approveList as approveListReimburse} from "@/api/business/mywork/reimburse";
  import { todolistBusiProject,projectLeaderWorkflowCount } from "@/api/business/mywork/myproject";

  export default {
    name: "page-reader",
    data(){
      return{
        req:[
          {index:1,titles:"加班审批",ready:"待审批",number:"0"},
          {index:2,titles:"请假审批",ready:"待审批",number:"0"},
          {index:3,titles:"面试申请",ready:"待审批",number:"0"},
          {index:4,titles:"差旅费报销（元）",ready:"待审批",number:"0"},
          {index:5,titles:"费用报销（元）",ready:"待审批",number:"0"},
          {index:6,titles:"入职申请",ready:"待审批",number:"0"},
          {index:7,titles:"离职申请",ready:"待审批",number:"0"},
          {index:8,titles:"调岗申请",ready:"待审批",number:"0"},
          {index:9,titles:"项目待办",ready:"待审批",number:"0"},
          {index:10,titles:"xxx项目组职申请",ready:"待审批",number:"0"},
        ]
      }
    },
    created() {
      //获取加班审批数量
      this.getLittleNum();
      //获取请假审批数量
      this.getLeaveNum();
      //获取差旅费审批数量
      this.getReimburseChaiNum();
      //获取费用审批数量
      this.getReimburseFeiNum();
      //待办项目审批列表
      this.gettodolistBusiProject();
      //项目负责人审批待办数量
      this.getProjectLeaderWorkflowCount();
    },
    methods:{
      getLittleNum(){
        this.loading = true;
        approveListLittle({workflowId: this.GLOBAL.EXTRA_WORKFLOWID}).then(response => {
          this.req[0].number = response.total;
          this.loading = false;
        });
      },
      getLeaveNum(){
        this.loading = true;
        approveListLeave({workflowId: this.GLOBAL.LEAVE_WORKFLOWID}).then(response => {
          this.req[1].number = response.total;
          this.loading = false;
        });
      },

      getReimburseChaiNum(){
        this.loading = true;
        approveListReimburse({workflowId: this.GLOBAL.TRAVEL_EXPENSE_WORKFLOWID,reimburseType :'travel'}).then(response => {
          this.req[3].number = response.total;
          this.loading = false;
        });
      },

      getReimburseFeiNum(){
        this.loading = true;
        approveListReimburse({workflowId: this.GLOBAL.EXPENSE_WORKFLOWID,reimburseType :'expenses'}).then(response => {
          this.req[4].number = response.total;
          this.loading = false;
        });
      },


      edit(item){
        if(item.index===1){
          this.$router.push({ path:'/myreader/little'})
        } else if (item.index===2) {
          this.$router.push({ path:'/myreader/leave'})
        }else if (item.index===3) {
          this.$router.push({ path:'/myreader/face'})
        }else if (item.index===4) {
          this.$router.push({ path:'/myreader/business'})
        }
        else if (item.index===5) {
          this.$router.push({ path:'/myreader/money'})
        }
        else if (item.index===6) {
          this.$router.push({ path:'/myreader/entry'})
        }
        else if (item.index===7) {
          this.$router.push({ path:'/myreader/down'})
        }
        else if (item.index===9) {
          this.$router.push({ path:'/myreader/projectTodo'})
        }
        else if (item.index===10) {
          this.$router.push({ path:'/myreader/xxx',query:{projectId:item.projectId}})
        }

      },
      gettodolistBusiProject() {
        let _this = this;
        todolistBusiProject().then(response => {
          if(response.code == 200){
            let todolist = response.data;
            let list = [];
            todolist.forEach((item)=>{
              let obj = {
                index:10,
                titles:item.projectName,
                ready:"待审批",
                number:item.todoNum,
                projectId:item.projectId
              }
              list.push(obj);
            });
            _this.req =_this.req.concat(list);
          }
        });
      },
      //
      getProjectLeaderWorkflowCount() {
        let _this = this;
        projectLeaderWorkflowCount().then(response => {
          if(response.code == 200){
            let number = response.data;
            _this.req[8].number = number;
          }
        });
      },
    }

  }

</script>

<style lang="scss" scoped>

  .box-card{
    float:left;
  }
  .big{
    font-size:20px;
    font-weight:bold;
  }
.little{
  margin-top: -2px;

}


  .box-card {
    width: 316px;
    height: 184px;
    margin-left: 20px;
    margin-top: 18px;
  }
  .father{
    text-align: center;
    margin-top: 5px;
    font-size: 14px;
  }
  .conten{
    font-size:40px;
    font-weight:bold;
    margin-top:5px;

  }
</style>
