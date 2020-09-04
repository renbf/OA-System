<template>
  <div class="app-container">
    <el-card class="box-card" v-for="item in expenseList">
      <div slot="header" class="clearfix">
        <span>{{item.title}}</span>
        <el-button icon="el-icon-right" style="float: right;padding:5px;" circle @click="godetail(item.index)"></el-button>
      </div>
      <div class="text item">{{item.content}}</div>
    </el-card>
  </div>
</template>

<script>

    export default {
        name: "index",
        data(){
          return{
            expenseList:[
              {index:1,title:"差旅费报销（元）",content:"用于填写员工出差时发生的费用,如：交通费、住宿费、出差补助、预制金额、补领金额、退回金额。"},
              {index:2,title:"费用报销（元）",content:"用于填写员工发生的专项费用，如：办公费、水电费、邮寄费、交通费、房租、物业费、宽带费、电话费、加班餐补等"}
              ]
          }
        },
      created() {

      },
      methods:{
        godetail(index){
          if(index==1){
            // _this.$router.push({ path:'./expenses/travel'})
            this.$router.push({ path:'/expenses/travel'})
          }else if(index==2){
            this.$router.push({ path:'/expenses/expenses'})
          }
        }
      }
    }
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    display: inline-block;
    width: 316px;
    margin-right: 22px;
  }
  .el-card__header{
    padding:0;
    font-weight: bold;
  }
</style>
