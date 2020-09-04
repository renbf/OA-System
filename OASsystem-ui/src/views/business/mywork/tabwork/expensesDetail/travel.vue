<template>
  <div>

    <div style="display: flex">
        <div class="detail">
          <p>出差人</p>
          <p>迈克尔</p>
        </div>
        <div class="detail">
          <p>日期</p>
          <p>2020-05-03</p>
        </div>
    </div>
  <el-form ref="form" :model="form" label-width="54px">
    <el-col :span="24">
      <el-form-item class="d-lg-inline-block">
        <div class="sub-title">预制旅费</div>
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item class="d-lg-inline-block">
        <div class="sub-title">退回金额</div>
        <el-input v-model="form.money"></el-input>
      </el-form-item>
      <el-form-item class="d-lg-inline-block">
        <div class="sub-title">补领金额</div>
        <el-input v-model="form.Replenishment"></el-input>
      </el-form-item>
      <el-form-item class="d-lg-inline-block">
        <div class="sub-title">所属项目</div>
        <el-select v-model="form.belongto" placeholder="请选择项目编号">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="14">
      <el-form-item>
        <div class="sub-title">事由</div>
        <el-input type="textarea" :rows="6" v-model="form.desc"></el-input>
      </el-form-item>
    </el-col>
  </el-form>
<!--  交通费-->
    <div>
      <el-col :span="24">
      <p class="p_title">交通费</p>
      <div class="addtravel">
        <div @click="dialogVisible = true" class="add">
          <p><i class="el-icon-circle-plus-outline"></i></p>
          <p>新增交通费</p>
        </div>
        <div class="show_add">
<!--            左-->
          <div class="lf">
            <p>05月03日</p>
            <p>17:30</p>
            <p>石家庄</p>
            <p>附件1张</p>
          </div>
          <div class="lf">
            <p style="margin-top: 50px;margin-bottom: 0px">火车</p>
            <p style="margin-top: 0px;"> -----------> </p>
          </div>
          <div class="lf">
            <p>05月03日</p>
            <p>17:30</p>
            <p>石家庄</p>
            <p>￥199</p>
          </div>
        </div>
      </div>
      </el-col>
<!--      交通费合计-->
      <el-col :span="10" :offset="1">
        <p>共计费用 ￥199 附件 1张</p>
      </el-col>
    </div>
    <el-dialog
      title="添加交通费"
      :visible.sync="dialogVisible"
      width="600px"
      :before-close="handleClose" class="abow_dialog">

      <el-form ref="form">
        <el-row>
          <el-col :span="12">
            <el-form-item prop="">
              <div class="sub-title">开始时间</div>
              <el-date-picker
                v-model="value1"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="">
              <div class="sub-title">结束时间</div>
              <el-date-picker
                v-model="value2"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">起始</div>
              <el-select v-model="form.begin" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">终点</div>
              <el-select v-model="form.end" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">交通工具</div>
              <el-select v-model="form.transport" placeholder="请选择交通工具">
                <el-option label="火车" value="huoche"></el-option>
                <el-option label="飞机" value="plan"></el-option>
                <el-option label="轮船" value="ship"></el-option>
                <el-option label="自驾" value="go"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">单据张数</div>
              <el-input v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item>
            <div class="sub-title">金额</div>
            <el-input v-model="form.money"></el-input>
          </el-form-item>
        </el-row>
        <el-col :span="12">
          <el-row>
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-row>
        </el-col>
        <el-col :span="24">
          <div class="sub-title">合计金额</div>
          <div><b>￥ 199.00</b></div>
        </el-col>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">保存</el-button>
      </span>
    </el-dialog>



<!--    出差补贴-->
    <div>
      <el-col :span="24">
        <p class="p_title">出差补贴</p>
        <div  class="addtravel">
          <div @click="butieVisible = true" class="add">
            <p><i class="el-icon-circle-plus-outline"></i></p>
            <p>新增出差补贴</p>
          </div>
          <div class="show_add">
            <h4>出差补助</h4>
            <div>
              <el-col :span="6">
                <p class="p1">
                  <span>人数</span>
                  <br>
                  <span><b>1人</b></span>
                </p>
              </el-col>
              <el-col :span="6">
                <p class="p1">
                  <span>天数</span>
                  <br>
                  <span><b>4天</b></span>
                </p>
              </el-col>
              <el-col :span="10">
                <p class="p1">
                  <span>补贴标准</span>
                  <br>
                  <span><b>￥40</b></span>
                </p>
              </el-col>
            </div>
            <div>
              <el-col :span="6">
                <p>附件1张</p>
              </el-col>
              <el-col :span="6"  :offset="10">
                <p>￥199</p>
              </el-col>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="10" :offset="1">
        <p>共计费用 ￥199 附件 1张</p>
      </el-col>
    </div>
    <el-dialog
      title="出差补贴"
      :visible.sync="butieVisible"
      width="600px"
      :before-close="handleClose" class="abow_dialog">

      <el-form ref="travelform">
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">项目</div>
              <el-input v-model="travelform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" >
            <el-form-item>
              <div class="sub-title">人数</div>
              <el-input v-model="travelform.person"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">天数</div>
              <el-input v-model="travelform.number"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">补贴标准</div>
              <el-input v-model="travelform.money"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-col :span="12">
          <el-row>
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-row>
        </el-col>
        <el-col :span="24">
          <div class="sub-title">合计金额</div>
          <div><b>￥ 199.00</b></div>
        </el-col>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="butieVisible = false">取 消</el-button>
        <el-button type="primary" @click="butieVisible = false">保存</el-button>
      </span>
    </el-dialog>
<!--    其他-->
    <div>
      <el-col :span="24">
        <p class="p_title">其他</p>
        <div  class="addtravel">
          <div @click="otherVisible = true" class="add">
            <p><i class="el-icon-circle-plus-outline"></i></p>
            <p>新增其他内容</p>
          </div>
          <div class="show_add">
            <h4>其他</h4>
            <div>
              <el-col :span="6">
                <p class="p1">
                  <span>人数</span>
                  <br>
                  <span><b>1人</b></span>
                </p>
              </el-col>
              <el-col :span="6">
                <p class="p1">
                  <span>天数</span>
                  <br>
                  <span><b>4天</b></span>
                </p>
              </el-col>
              <el-col :span="10">
                <p class="p1">
                  <span>补贴标准</span>
                  <br>
                  <span><b>￥40</b></span>
                </p>
              </el-col>
            </div>
            <div>
              <el-col :span="6">
                <p>附件1张</p>
              </el-col>
              <el-col :span="6"  :offset="10">
                <p>￥199</p>
              </el-col>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="10" :offset="1">
        <p>共计费用 ￥199 附件 1张</p>
      </el-col>
    </div>
    <el-dialog
      title="其他"
      :visible.sync="otherVisible"
      width="600px"
      :before-close="handleClose" class="abow_dialog">

      <el-form ref="otherform">
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <div class="sub-title">项目</div>
              <el-input v-model="otherform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" >
            <el-form-item>
              <div class="sub-title">人数</div>
              <el-input v-model="otherform.money"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-col :span="12">
          <el-row>
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-row>
        </el-col>
        <el-col :span="24">
          <div class="sub-title">合计金额</div>
          <div><b>￥ 199.00</b></div>
        </el-col>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="otherVisible = false">取 消</el-button>
        <el-button type="primary" @click="otherVisible = false">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "travel",
        data(){
          return{
            form:{
              name:'',
              money:'',
              Replenishment:'',
              desc:'',
              transport:'',
              begin:'',
              end:'',
              belongto:''
            },
            travelform:{
              name:'',
              person:'',
              number:'',
              money:''
            },
            otherform:{
              name:'',
              money:''
            },
            dialogVisible: false,
            butieVisible:false,
            otherVisible:false,
            value1:'',
            value2:'',
            fileList: [
              {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'},
              {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
              ],
            options: [{
              value: '选项1',
              label: '黄金糕'
            }, {
              value: '选项2',
              label: '双皮奶'
            }, {
              value: '选项3',
              label: '蚵仔煎'
            }, {
              value: '选项4',
              label: '龙须面'
            }, {
              value: '选项5',
              label: '北京烤鸭'
            }],
          }
        },
      methods:{
          // 新增交通费
        /** 新增按钮操作 */
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        }
      }
    }
</script>
<style>
  .abow_dialog .el-dialog__body{
    height: 556px;
    overflow-x: hidden;
    overflow-y: scroll;
  }
</style>
<style scoped>


  .lf{
    float:left;
  }
  .rt{
    float:right;
  }
  h4{
    margin-bottom:0;
    padding-left: 10px;
  }
  .p1{
    margin-right: 25px;
  }
.addtravel{
  display:flex;justify-content:flex-start
}
  .show_add>div{
    margin-left: 12px;
    text-align: center;
  }
  .show_add>div p{
    text-align: center;
  }
.addtravel>div{
  margin-left: 50px;
}
.detail{
  width: 300px;
  margin-left: 54px;
}
.d-lg-inline-block{
  display: inline-block;
}
  .el-form{
    width: 100%;
  }
  .p_title{
    margin-left: 30px;
  }
  .add{
    width: 250px;
    height:150px;
    border:1px solid #ddd;
    box-sizing: border-box;

  }
  .add i{
    display: table;
    font-size: 80px;
    margin:0 auto;
  }
  .add p{
    text-align: center;
  }
  .show_add{
    border:1px solid #ddd;
    width: 250px;
    height:150px;
  }
</style>
