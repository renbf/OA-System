
<template>
  <div id="app">

  </div>
</template>

<script>
  import esriLoader from "esri-loader";
  import config from "@/api/tool/gisconfig";

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
        //通过loadModules来做衔接
        esriLoader.loadModules(['esri/Map', 'esri/views/MapView'], config.loadConfig)
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

