
<template>
<!--  <div class="wrapper">-->
<!--    <div ref="map" class="map"></div>-->
<!--  </div>-->
  <div id="app">

  </div>
</template>

<script>
  import esriLoader from "esri-loader";

  export default {
    name: "app",

    components: {},

    props: {},

    data() {
      return {};
    },

    created() {},

    mounted() {
      // const options = { url: "https://js.arcgis.com/3.27/" };
      // esriLoader
      //   .loadModules(
      //     [
      //       "esri/map"
      //     ],
      //     options
      //   )
      //   .then(
      //     ([
      //        Map
      //      ]) => {
      //       let map = new Map(this.$refs.map, {
      //         backgroundColor: "#eee",
      //         basemap: 'streets',
      //         logo: false,
      //         slider: false,
      //         zoom: 7,
      //         minZoom: 7
      //       });
      //       Vue.prototype.$map = map;
      //     }
      //   ).catch();

      this._createMapView();
    },

    computed: {},

    methods: {
      //创建地图
      _createMapView: function() {
        const _self = this;   //定义一个_self防止后续操作中this丢失
        const option = {      //定义一个包含有JS API中js开发包和css样式文件的对象
          url: 'http://js.arcgis.com/4.16/init.js',
          css: 'http://js.arcgis.com/4.16/esri/themes/light/main.css',
        };

        //通过loadModules来做衔接
        esriLoader.loadModules(['esri/Map', 'esri/views/MapView'], option)
          .then(([Map, MapView]) => {
            var map = new Map({
              basemap: "streets"
            });

            var view = new MapView({
              container: "app",
              map: map,
              center: [114.48,38.03], // longitude, latitude
              zoom: 13
            });

            view.ui.components = [];   //清除掉地图左上角默认的缩放图标
          }).catch((err) => {
           _self.$message('地图创建失败，' + err);
        });
      },

    }
  };
</script>
<!--<style scoped>-->
<!--  @import url('https://js.arcgis.com/3.27/esri/css/esri.css');-->
<!--  .map {-->
<!--    width: 100%;-->
<!--    height: 100vh;-->
<!--  }-->
<!--</style>-->
