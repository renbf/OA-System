<template>
  <div class="main">
    <div id="map"></div>
    <span id="layerToggle" class="esri-widget"> <input type="checkbox" id="streetsLayer" checked /> Transportation </span>
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
  }
  .main #map {
      width: 100%;
      height: 100%;
  }

  #layerToggle {
    top: 20px;
    right: 20px;
    position: absolute;
    z-index: 99;
    background-color: white;
    border-radius: 8px;
    padding: 10px;
    opacity: 0.75;
  }
</style>
