
<template>
  <div class="contain-reader">
    <el-card class="box-card" v-for="item in req">
    <div slot="header" class="clearfix">

      <span class="big" >{{item.titles}}</span>
      <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"  @click.native="edit(item.index)"></el-button>
    </div>
    <div class="father"> <span class="son">{{item.ready}}</span></div>
    <div class="father"> <span class="conten">{{item.number}}</span>件</div>
  </el-card>


</div>
</template>


<script>
  import {approveList as approveListLittle} from "@/api/business/mywork/extrawork";
  import {approveList as approveListLeave} from "@/api/business/mywork/leave";

  export default {
    name: "page-reader",
    data(){
      return{
        req:[
          {index:1,titles:"加班审批",ready:"待审批",number:"12"},
          {index:2,titles:"请假审批",ready:"待审批",number:"0"},
          {index:3,titles:"面试申请",ready:"待审批",number:"0"},
          {index:4,titles:"差旅费报销（元）",ready:"待审批",number:"0"},
          {index:5,titles:"费用报销（元）",ready:"待审批",number:"0"},
          {index:6,titles:"入职申请",ready:"待审批",number:"0"},
          {index:7,titles:"离职申请",ready:"待审批",number:"0"},
          {index:8,titles:"调岗申请",ready:"待审批",number:"0"},
          {index:9,titles:"xxx项目组职申请",ready:"待审批",number:"0"},


        ]
      }
    },
    created() {
      //获取加班审批数量
      this.getLittleNum();

      //获取请假审批数量
      this.getLeaveNum();



    },
    methods:{
      getLittleNum(){
        this.loading = true;
        approveListLittle().then(response => {
          debugger
          this.req[0].number = response.total;
          this.loading = false;
        });
      },
      getLeaveNum(){
        this.loading = true;
        approveListLeave().then(response => {
          this.req[1].number = response.total;
          this.loading = false;
        });
      },

      edit(id){
        if(id===1){
          this.$router.push({ path:'/myreader/little'})
        } else if (id===2) {
          this.$router.push({ path:'/myreader/leave'})
        }else if (id===3) {
          this.$router.push({ path:'/myreader/face'})
        }else if (id===4) {
          this.$router.push({ path:'/myreader/business'})
        }
        else if (id===5) {
          this.$router.push({ path:'/myreader/money'})
        }
        else if (id===6) {
          this.$router.push({ path:'/myreader/entry'})
        }
        else if (id===7) {
          this.$router.push({ path:'/myreader/down'})
        }
        else if (id===9) {
          this.$router.push({ path:'/myreader/xxx'})
        }

      }
    }

  }

</script>

<style lang="scss">

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
