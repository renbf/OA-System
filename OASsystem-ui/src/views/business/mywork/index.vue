<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建申请</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi=""
        >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-message"
          size="mini"
          @click="handleReport"
        >报送</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi=""
        >导出</el-button
        >
      </el-col>
    </el-row>
    <el-form :modal="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="任务时间">
        <el-date-picker
          v-model="queryParams.tasktime"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态">
        <el-select
          v-model="queryParams.status"
          placeholder="选择状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门">
        <el-select
          v-model="queryParams.status"
          placeholder="选择状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
	export default {
		data() {
			return {
				tabdata: [{
						date: '2016-05-02',
						carddetail: [{
								title: 'Web页面设计',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
							{
								title: '加班申请',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
						]

					},
					{
						date: '2016-05-03',
						carddetail: [{
								title: '加班申请',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
							{
								title: '加班申请',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
						]

					},
					{
						date: '2016-05-04',
						carddetail: [{
								title: 'Web页面设计',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
							{
								title: '加班申请',
								workDetail: [{
										detail: '1.完成登录页面实现'
									},
									{
										detail: '2.完成登录页面实现'
									},
									{
										detail: '3.完成登录页面实现'
									}
								]
							},
						]

					},
				]
			}
		},
    methods:{
      gotoAdd(){
        this.$router.push({ path:'/mywork/addMywork'})
      }
    }
	}
</script>

<style lang="scss" scoped>
	@import '@/assets/styles/workflow/mywork/mywork.scss'
</style>
