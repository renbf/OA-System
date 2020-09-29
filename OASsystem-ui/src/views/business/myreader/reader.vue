
<template>
  <div class="contain-reader">
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >加班审批</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">12</span>件</div>
    </el-card>
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >请假审批</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">0</span>件</div>
    </el-card>
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >面试申请</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">0</span>件</div>
    </el-card>
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >差旅费报销（元）</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">0</span>件</div>
    </el-card>
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >费用报销</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">0</span>件</div>
    </el-card>
    <el-card class="box-card" >
      <div slot="header" class="clearfix">

        <span class="big" >入职申请</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">0</span>件</div>
    </el-card>  <el-card class="box-card" >
    <div slot="header" class="clearfix">

      <span class="big" >离职申请</span>
      <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
    </div>
    <div class="father"> <span class="son">待审批</span></div>
    <div class="father"> <span class="conten">0</span>件</div>
  </el-card>  <el-card class="box-card" >
    <div slot="header" class="clearfix">

      <span class="big" >调岗申请</span>
      <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
    </div>
    <div class="father"> <span class="son">待审批</span></div>
    <div class="father"> <span class="conten">0</span>件</div>
  </el-card>  <el-card class="box-card" >
    <div slot="header" class="clearfix">

      <span class="big" >xxx项目组申请</span>
      <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
    </div>
    <div class="father"> <span class="son">待审批</span></div>
    <div class="father"> <span class="conten">0</span>件</div>
  </el-card>



  </div>



</template>


<script>

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
