<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row :gutter="50">
      <el-col :xs="24" :sm="24" :lg="18">
        <el-row>
          <el-col style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
            <line-chart :chart-data="lineChartData" />
          </el-col>
        </el-row>


        <el-row>
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <raddar-chart />
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <pie-chart />
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <bar-chart />
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="5" style="background: #fff;margin-left: 20px;width: 22.5%">
          <h4>全公司待处理申请</h4>
          <list />
      </el-col>

<!--      <el-col :xs="24" :sm="24" :lg="6">-->
<!--        -->
<!--      </el-col>-->
<!--      <el-col :xs="24" :sm="24" :lg="6" :offset="0.8">-->
<!--        -->
<!--      </el-col>-->
<!--      <el-col :xs="24" :sm="24" :lg="6" :offset="0.8">-->
<!--        -->
<!--      </el-col>-->


      </el-row>

<!--    <el-row :gutter="32">-->
<!--      -->
<!--    </el-row>-->
<!--    <el-row>-->
<!--      -->
<!--    </el-row>-->

  </div>
</template>

<script>
  import PanelGroup from './../total_detail/PanelGroup'
  import List from './../total_detail/list'
  import LineChart from './../total_detail/totalLineChart'
  import RaddarChart from './../total_detail/totalRaddarChart'
  import PieChart from './../total_detail/totalPieChart'
  import BarChart from './../total_detail/totalBarChart'

  const lineChartData = {
    newVisitis: {
      expectedData: [100, 120, 161, 134, 105, 160, 165],
      actualData: [120, 82, 91, 154, 162, 140, 145]
    },
    messages: {
      expectedData: [200, 192, 120, 144, 160, 130, 140],
      actualData: [180, 160, 151, 106, 145, 150, 130]
    },
    purchases: {
      expectedData: [80, 100, 121, 104, 105, 90, 100],
      actualData: [120, 90, 100, 138, 142, 130, 130]
    },
    shoppings: {
      expectedData: [130, 140, 141, 142, 145, 150, 160],
      actualData: [120, 82, 91, 154, 162, 140, 130]
    }
  }

  export default {
    name: 'Index',
    components: {
      PanelGroup,
      LineChart,
      RaddarChart,
      PieChart,
      BarChart,
      List
    },
    data() {
      return {
        lineChartData: lineChartData.newVisitis
      }
    },
    methods: {
      handleSetLineChartData(type) {
        this.lineChartData = lineChartData[type]
      }
    }
  }
</script>

<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }
.chart-wrapper{
  /*margin-right: 45px;*/
}
  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>
