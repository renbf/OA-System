import { loadModules, loadCss } from "esri-loader"; // 异步加载模块
import config from "./gisconfig"; // 配置项


class ArcGIS {
  constructor() {
    this.map = null; // 地图
    this.baseMap = null; // 地图底图
  }

  init($el) {
    const _self = this;   //定义一个_self防止后续操作中this丢失
    // 加载地图必备样式文件
    // loadCss("http://js.arcgis.com/4.17/esri/css/esri.css");
    // loadCss("http://js.arcgis.com/4.17/dijit/themes/claro/claro.css");
    loadModules(
      [
        "esri/Map",
        'esri/views/MapView',
        "esri/views/SceneView",
        "esri/core/watchUtils",
        "esri/Graphic",
        "esri/layers/GraphicsLayer"
      ],
      config.loadConfig
    )
      .then(
        ([Map, MapView, SceneView,  watchUtils, Graphic, GraphicsLayer]) => {

          var map = new Map({
             // basemap: "streets"
             basemap: "oceans",
          });

          var view = new MapView({
            container: $el,
            map: map,
            center: [114.48,38.03], // longitude, latitude
            zoom: 6,
          });

          var graphicsLayer = new GraphicsLayer();
          var point = {
            type: "point",
            longitude: 114.48,
            latitude: 38.03
          };

          var simpleMarkerSymbol = {
            type: "simple-marker",
            color: [226, 119, 40],  // orange
            outline: {
              color: [255, 255, 255], // white
              width: 1
            }
          };

          var attributes = {
            Name: "My point",  // The name of the
            Location: " Point Dume State Beach",  // The owner of the
          };
          // Create popup template
          var popupTemplate = {
            title: "{Name}",
            content: "I am located at <b>{Location}</b>."
          };

          var pointGraphic = new evGraphic({
            geometry: point,
            symbol: simpleMarkerSymbol,
            attributes: attributes,
            popupTemplate: popupTemplate
          });

          graphicsLayer.add(pointGraphic);

          map.add(graphicsLayer);

        }
      ) //end
      .catch((err) => {
        _self.$message('地图创建失败，' + err);
      });
  }

  /*
   *  description:  添加图层
   *  param {Layer,Array<Layer>} layer  需添加的图层
   *  param {number,Array<number>} lever 添加图层的层数
   */
  addLayer(layer, lever) {
    // 判断是都为数组
    if(Array.isArray(layer)){
      layer.forEach((item, index) => {
        lever ? this.map.addLayer(item, lever[index]) : this.map.addLayer(item);
      });
    } else {
      lever ? this.map.addLayer(layer, lever) : this.map.addLayer(layer);
    }
  }
}

export default ArcGIS;
