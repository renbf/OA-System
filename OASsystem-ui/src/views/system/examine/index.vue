<template>
  <div class="app-container role">
    <el-row :gutter="20">

      <!--      列表-->
      <template v-for="item in workflowGroupList">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <div style="float: left">
              <h3>{{item.workflowGroupName}}</h3>
              <p>{{item.remark}}</p>
            </div>
            <div style="float: right">
              <el-button icon="el-icon-right" circle @click="detail(item.workflowGroupId)"></el-button>
            </div>
          </div>
          <div class="text item">
            <p>
              共有审批流程
            </p>
            <p>
              <b style="font-size:28px;">{{item.count}}</b>条
            </p>
          </div>
        </el-card>
      </template>
    </el-row>
  </div>
</template>

<script>
// import testData from '@/assets/static/system/examine/testData.js';
import { listGroup } from "@/api/system/examine/workflowgroup";

  export default {
    name: "index",
    data(){
      return{
        // 打开弹框
        open:false,
        // 弹框标题
        title:'',
        // 总条数
        total:1,
        // 分页
        pageNum: 1,
        pageSize: 10,
        workflowGroupList:[]
      }
    },
    created() {
      this.getList();
    },
    methods:{
      //查询列表
      getList(){
        listGroup().then(response => {
          this.workflowGroupList = response.rows;
        });
      },
      // 跳转详情页
      detail(workflowGroupId){
        this.$router.push({ path:'../examine/examine',query:{workflowGroupId:workflowGroupId}})
      }
    }
  }
</script>

<style>
  .role .box-card{
    width: 316px;
    height:184px;
    float: left;
    margin-left: 86px;
    margin-top: 30px;
  }
  /*卡片*/
  .role .el-card__header{
    height: 75px;
    width: 100%;
  }
  /*不能点击文字颜色*/
  .role .el-input.is-disabled .el-input__inner{
    color:#666;
  }
  .role .el-card__body p span,.dept .el-card__body p{
    font-size: 14px;
  }
  .role .el-card__body p{
    text-align: center;
  }
  .role .clearfix:after{
    display: block;
    clear: both;
    content:""
  }
  .role .clearfix{
    margin:0;
  }
  .role .clearfix p,.role .clearfix h3{
    margin:0
  }
  .role .clearfix p{
    margin-top: 8px;
    color:#aaa;
    font-size: 12px;
  }
  .clearfix>div{
    height: 75px;
  }

  .role .cantclick{
    background:#F2F2F2;
  }
</style>
