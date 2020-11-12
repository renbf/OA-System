
<template>
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

          }).catch((err) => {
           _self.$message('地图创建失败，' + err);
        });
      },

    }
  };
</script>
<style scoped>
  body {
    margin: 0;   /**主要是去除谷歌浏览器默认的8像素的外边距 */
  }
  #app {
    position: absolute;   /**使这个div的大小撑满整个屏幕 */
    width: 100%;
    height: 100%;
  }
</style>

