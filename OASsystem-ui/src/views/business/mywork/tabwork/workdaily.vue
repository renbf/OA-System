<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="工作主题">
      <el-input v-model="form.name" placeholder="请输入工作主题"></el-input>
    </el-form-item>
    <el-form-item label="工作时间">
      <el-time-picker
        v-model="value1"
        :picker-options="{
      selectableRange: '00:00:00 - 24:00:00'}"
        placeholder="任意时间点">
      </el-time-picker>
      至
      <el-time-picker
        arrow-control
        v-model="value2"
        :picker-options="{selectableRange: '18:30:00 - 20:30:00'}"
        placeholder="任意时间点">
      </el-time-picker>
    </el-form-item>
    <el-form-item label="工作内容">
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
    data() {
      return {
        form: {
          name: ''
        },
        value1: new Date(2016, 9, 10, 18, 40),
        value2: new Date(2016, 9, 10, 18, 40)
      };
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      }
    }
  };
</script>

<style scoped>

</style>
