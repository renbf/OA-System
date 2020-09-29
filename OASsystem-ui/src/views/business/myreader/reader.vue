
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

        <span class="big" >加班审批</span>
        <el-button class="little" style="float: right" icon="el-icon-right" circle size="mini"></el-button>
      </div>
      <div class="father"> <span class="son">待审批</span></div>
      <div class="father"> <span class="conten">12</span>件</div>
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
    margin: 20px;
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
