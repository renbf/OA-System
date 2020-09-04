<template>
  <div class="child2">
    我是第二个子组件
  </div>
</template>

<script>
  export default {
    name: 'child1',
    mounted(){
      console.log("tab2组件")
    }
  }
</script>

<style scoped>

</style>
