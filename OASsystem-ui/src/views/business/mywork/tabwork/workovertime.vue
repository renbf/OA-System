<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="加班主题">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="加班时间">
      <el-date-picker
        v-model="value1"
        type="datetime"
        placeholder="选择日期时间">
      </el-date-picker>
      至
      <el-date-picker
        v-model="value2"
        type="datetime"
        placeholder="选择日期时间">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="加班原因">
        <el-input type="textarea" v-model="form.desc"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">保存</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    export default {
        name: "workovertime",
        data(){
          return {
            form: {
              name: ''
            },
            value1:'',
            value2:''
          }
      },
      methods: {
        onSubmit() {
        }
      }
    }
</script>

<style scoped>

</style>
