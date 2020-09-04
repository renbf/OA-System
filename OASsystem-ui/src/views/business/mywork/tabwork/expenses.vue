<template>

  <div>
    <el-row class="home_nav" type="flex" justify="flex-start" align="middle">
      <el-col :span="12">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="travel">差旅费报销</el-menu-item>
          <el-menu-item index="account">费用报销</el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
    <component :is="activeIndex"></component>
  </div>
</template>

<script>
  // 差旅费
  import travel from './expensesDetail/travel'
  // 费用报销
  import account from './expensesDetail/account'

  export default {
    // name: "expenses",
    components: {
      travel,
      account
    },
    data() {
      return {
        activeIndex: 'travel'
      }
    },
    methods: {
      handleSelect(index) {
        this.activeIndex = index
      }
    }
  }
</script>

<style scoped>

</style>
