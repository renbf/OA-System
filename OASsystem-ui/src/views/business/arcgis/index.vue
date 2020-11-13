<template>
  <div class="main">
    <div id="map"></div>
  </div>
</template>

<script>
  // 引入 ArcGIS 模块，并进行实例化
  import ArcGIS from "@/api/business/arcgis/init";

  let Map = new ArcGIS();
  export default {
    name: "App",
    mounted() {
      Map.init("map"); // 初始化地图模块
    },
  };
</script>

<style lang="less">
  html,
  body {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100%;
  }

  .main {
    position: absolute;
    top: 70px;
    bottom: 0;
    width: 100%;

    #map {
      width: 100%;
      height: 100%;
    }
  }
</style>
