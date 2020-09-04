<!-- 导入导出组件（纯前端） -->
<template>
  <span>
    <input type="file" @change="importFile(this)" id="imFile" style="display:none;"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
    <a id="downlink"></a>
    <slot name="export">
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="downloadFile"
      >导出</el-button>
    </slot>
    <slot name="import">
      <el-button type="warning"
                 icon="el-icon-download"
                 size="mini"
                 @click="uploadFile"
                 v-show="importBtnShow"
      >导入</el-button>
    </slot>
  </span>
</template>

<script>
  import { exportExcelByTableId,exportExcelByArray,exportExcelByJson } from "@/utils/excelUtil";

  export default {
    name: 'export-import',
    data() {
      return {
        fullscreenLoading: false, // 加载中
        imFile: '', // 导入文件el对象
        outFile: '', // 导出文件el对象
        excelData: [], // excel处理数据
      }
    },
    props: {
      setExportData: {
        type: Function,
        default: function() {
          this.$message({message: '未传递获取excel导出数据方法', type: 'warning'});
        }
      },
      getImportData: {
        type: Function,
        default: function() {
          this.$message({message: '未传递设置excel导入数据方法', type: 'warning'});
        }
      },
      importBtnShow:{
        type: Boolean,
        default: false
      },
      tableId:{
        type: String,
        default: 'tableData',
      },
      excelTitle:{
        type: String
      },
      tableDatas:{
        type: Array
      },
      expFunction:{
        type: String
      },
      tableColumns:{
        type: Array
      },
      tableColuNames:{
        type: Array
      },

    },
    mounted() {
      this.imFile = document.getElementById('imFile')
      this.outFile = document.getElementById('downlink')
    },
    methods: {
      uploadFile: function() { // 导入文件点击事件
        this.imFile.click()
      },
      downloadFile: function() { // 导出文件点击事件
        let expFunction = this.expFunction;
        switch(expFunction) {
          case 'json':
            let exportData = this.setExportData()
            if(exportData){
              exportExcelByJson(exportData,this.excelTitle)
            }else{
              this.$message({
                message: '所传数据为空，请联系管理员',
                type: 'warning'
              });
            }
            break;
          case 'array':
            exportExcelByArray(this.tableDatas,this.tableColumns,this.tableColuNames,this.excelTitle)
            break;
          default:
          exportExcelByTableId(this.tableId,this.excelTitle)
        }

        // let exportData = this.setExportData()
        // this.excelTitle = exportData.excelTitle
        // this.excelData = exportData.excelData
        // let data = [{},{}]
        // for (let k in this.excelData[0]) { // 设置第1行为数据库字段行
        //   if (this.excelData[0].hasOwnProperty(k)) {
        //     data[0][k] = k
        //     data[1][k] = this.excelTitle[k] // 中文标题
        //   }
        // }
        // data = data.concat(this.excelData)
        // this.downloadExl(data, exportData.excelName || '导出文件')
      },
      importFile: function() { // 导入excel
        this.fullscreenLoading = true
        let obj = this.imFile
        if (!obj.files) {
          this.fullscreenLoading = false
          return
        }
        var f = obj.files[0]
        var reader = new FileReader()
        let $t = this
        reader.onload = function(e) {
          var data = e.target.result
          if ($t.rABS) {
            $t.wb = XLSX.read(btoa(this.fixdata(data)), { // 手动转化
              type: 'base64'
            })
          } else {
            $t.wb = XLSX.read(data, {
              type: 'binary'
            })
          }
          let json = XLSX.utils.sheet_to_json($t.wb.Sheets[$t.wb.SheetNames[0]])
          $t.dealFile($t.analyzeData(json)) // analyzeData: 解析导入数据
        }
        if (this.rABS) {
          reader.readAsArrayBuffer(f)
        } else {
          reader.readAsBinaryString(f)
        }
      },
      downloadExl: function(json, downName, type) { // 导出到excel
        let keyMap = [] // 获取键
        for (let k in json[0]) {
          if (json[0].hasOwnProperty(k)) {
            keyMap.push(k)
          }
        }
        let tmpdata = [] // 用来保存转换好的json
        json.map((v, i) => keyMap.map((k, j) => Object.assign({}, {
          v: v[k] || '',
          position: (j > 25 ? this.getCharCol(j) : String.fromCharCode(65 + j)) + (i + 1)
        }))).reduce((prev, next) => prev.concat(next)).forEach(function(v) {
          tmpdata[v.position] = {
            v: v.v
          }
        })
        let outputPos = Object.keys(tmpdata) // 设置区域,比如表格从A1到D10
        let tmpWB = {
          SheetNames: ['sheet'], // 保存的表标题
          Sheets: {
            'sheet': Object.assign({},
              tmpdata, // 内容
              {
                '!ref': outputPos[0] + ':' + outputPos[outputPos.length - 1] // 设置填充区域
              })
          }
        }
        let tmpDown = new Blob([this.s2ab(XLSX.write(tmpWB,
          { bookType: (type || 'xlsx'), bookSST: false, type: 'binary' } // 这里的数据是用来定义导出的格式类型
        ))], {
          type: ''
        }) // 创建二进制对象写入转换好的字节流
        var href = URL.createObjectURL(tmpDown) // 创建对象超链接
        this.outFile.download = downName + '.xlsx' // 下载名称
        this.outFile.href = href // 绑定a标签
        this.outFile.click() // 模拟点击实现下载
        setTimeout(function() { // 延时释放
          URL.revokeObjectURL(tmpDown) // 用URL.revokeObjectURL()来释放这个object URL
        }, 100)
      },
      analyzeData: function(data) { // 此处可以解析导入数据
        data.splice(0, 1) // 去除第二行（中文标题行）
        return data
      },
      dealFile: function(data) { // 处理导入的数据
        this.imFile.value = ''
        this.fullscreenLoading = false
        if (data.length <= 0) {
          this.$message({
            message: '请导入正确信息',
            type: 'warning'
          });
        } else {
          this.excelData = data
          this.getImportData(data)
        }
      },
      s2ab: function(s) { // 字符串转字符流
        var buf = new ArrayBuffer(s.length)
        var view = new Uint8Array(buf)
        for (var i = 0; i !== s.length; ++i) {
          view[i] = s.charCodeAt(i) & 0xFF
        }
        return buf
      },
      getCharCol: function(n) { // 将指定的自然数转换为26进制表示。映射关系：[0-25] -> [A-Z]。
        let s = ''
        let m = 0
        while (n > 0) {
          m = n % 26 + 1
          s = String.fromCharCode(m + 64) + s
          n = (n - m) / 26
        }
        return s
      },
      fixdata: function(data) { // 文件流转BinaryString
        var o = ''
        var l = 0
        var w = 10240
        for (; l < data.byteLength / w; ++l) {
          o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
        }
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
        return o
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
</style>
