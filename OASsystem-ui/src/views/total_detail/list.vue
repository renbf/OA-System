<template>

  <el-table :data="tableData" style="width: 100%">

    <el-table-column  label="标题" width="210px">
      <template slot-scope="scope">
        <span>{{scope.row.name}}/{{scope.row.nametype}}</span>
      </template>
    </el-table-column>

    <el-table-column prop="type" label="类型" width="120px">
      <template scope="scope">
        <span class="textAlign">{{scope.row.type}}</span>
      </template>
    </el-table-column>

  </el-table>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [{
          name: '迈克尔',
          nametype: '任务时间增加申请',
          type: "加班申请"
        }, {
          name: '迈克尔',
          nametype: '任务时间增加申请',
          type: "加班申请"
        }, {
          name: '迈克尔',
          nametype: '任务时间增加申请',
          type: "加班申请"
        }, {
          name: '迈克尔',
          nametype: '任务时间增加申请',
          type: "加班申请"
        }]
      }
    }
  }
</script>
<style scoped>
  .textAlign{
    width: 100%;
    display: inline-block;
    text-align: right;
  }
</style>
