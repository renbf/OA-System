<template>
  <div class="app-container travel_container">
    <el-row :gutter="10"  class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建报销</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
        >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-message"
          size="mini"
          @click="handleReport"
        >报送</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <b>单位：元</b>
      </el-col>
    </el-row>
    <el-form :modal="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="申请时间">
        <el-date-picker
          v-model="queryParams.time"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="queryParams.deptId" placeholder="请选择部门">
          <el-option
            v-for="dict in bumenOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="multipleTable"
      v-loading="loading"
      :data="costList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column
        label="报销时间"
        prop="cost_time"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="项目"
        prop="cost_name"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="部门"
        prop="cost_department"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="原借款"
        prop="cost_lendmoney"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="应退(补)款"
        prop="cost_havetogive"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="总金额"
        prop="cost_allmoney"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="状态"
        prop="cost_status"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="当前审批人"
        prop="cost_examine"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click.stop="handleUpdate(scope.row)"
          >编辑
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click.stop="handleDelete(scope.row)"
          >删除
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click.stop="handleReport(scope.row)"
          >报送
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>
<!--    弹窗-->
    <el-dialog
      :title="costtitle"
      :visible.sync="costexpenses"
      width="600px" class="abow_dialog">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="部门" prop="bumen_department">
          <el-radio-group v-model="form.bumen_department">
            <el-radio
              v-for="dict in department"
              :key="dict.dictValue"
              :label="dict.dictValue"
              border
            >{{ dict.dictLabel }}
            </el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="在项目中" prop="inProject">
          <el-radio-group v-model="form.inProject">
            <el-radio
              v-for="dict in profect"
              :key="dict.dictValue"
              :label="dict.dictValue"
              border
            >{{ dict.dictLabel }}
            </el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="报销项目" prop="costname">
          <el-select v-model="form.costname">
            <el-option
              v-for="dict in projectname"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" aria-placeholder="请填写不少于12字的真实事由信息"></el-input>
        </el-form-item>
<!--报销项目-->
        <el-row>
          <el-col>
            <el-form-item label="报销项目">
              <el-button type="primary" icon="el-icon-plus" circle @click="goaway"></el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="24">
            <template v-if="iscostdata">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in costdata">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span  style="font-size: 16px;margin-top: 10px;line-height: 35px;"><b>{{item.title}}</b></span>
                      <el-button icon="el-icon-delete"  style="float: right;" circle @click="deleteticket"></el-button>
                    </div>
                    <div class="text item train">
                      <div>
                        <p><b>{{item.content}}</b></p>
                        <p>附件 {{item.fujian}}张</p>
                      </div>
                      <div>
                        <p>￥<b>{{item.money}}</b></p>
                      </div>
                    </div>
                  </el-card>
                </el-carousel-item>
              </el-carousel>
            </template>
            <template v-else>
              <el-card class="box-card" style="width:322px;height: 184px;">
                <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
              </el-card>
            </template>
          </el-col>
          <el-col :span="24">
            <template v-if="otherphoto">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in fujianList">
                  <el-card class="box-card">
                    <div class="text item train">
                      <img src="@/assets/image/train_ticket.png" alt="">
                    </div>
                  </el-card>
                </el-carousel-item>
              </el-carousel>
            </template>
            <template v-else>
              <el-card class="box-card" style="width:322px;height: 184px;">
                <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
              </el-card>
            </template>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <span class="lf"><b style="font-size: 18px;">￥{{costTotal}}</b></span>
        <el-button @click="cancelcost">取消</el-button>
        <el-button type="primary" @click="savecost">保存</el-button>
      </div>
    </el-dialog>

<!--    新增、编辑-->
    <el-dialog
      :title="addcosttitle"
      :visible.sync="addcost"
      width="600px" class="abow_dialog">
      <el-form ref="form" :model="addcostform" :rules="addcostrules">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="addcostform.name"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="addcostform.remark"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="10">
            <el-form-item label="金额" prop="money">
              <el-input v-model="addcostform.money"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
            <el-form-item label="票据数量" prop="num">
              <el-input-number v-model="addcostform.num" :min="0"  label="描述文字"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上传附件" prop="fujian">
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="addcostform.fileList"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <span>共{{fujiannum}}张</span>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <span class="lf"><b>￥5,120,000.00</b></span>
        <el-button >取 消</el-button>
        <el-button type="primary" >保存</el-button>
      </span>
    </el-dialog>


<!--    查看详情、审批-->
    <el-dialog
      :title="detailcosttitle"
      :visible.sync="detailcost"
      width="800px" class="abow_dialog">
      <el-row>
        <el-col :span="8">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :size="activity.size"
              :timestamp="activity.timestamp"
            >
              <p>{{ activity.content }}</p>
              <p>{{ activity.description }}</p>
            </el-timeline-item>
          </el-timeline>
        </el-col>
        <el-col :span="14">
          <el-form  ref="form" :model="detailform" label-width="80px">
            <el-form-item label="部门">
              <el-input disabled v-model="detailform.department"></el-input>
            </el-form-item>
            <el-form-item label="在项目中">
              <el-radio-group v-model="detailform.inproject" disabled>
                <el-radio label="是"></el-radio>
                <el-radio label="否"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="报销项目">
              <el-input type="textarea" v-model="detailform.project" disabled></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input type="textarea" v-model="detailform.reason" disabled></el-input>
            </el-form-item>
            <el-row>
              <el-form-item label="审批备注" style="margin-top: 50px;">
                <el-input type="textarea" v-model="detailform.remark"></el-input>
              </el-form-item>
            </el-row>

            <el-row>
              <el-col>
                <el-form-item label="报销项目">
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>

              <el-col :span="24">
                <template v-if="iscostdata">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in costdata">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;margin-top: 10px;line-height: 35px;"><b>{{item.title}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p><b>{{item.content}}</b></p>
                            <p>附件 {{item.fujian}}张</p>
                          </div>
                          <div>
                            <p>￥<b>{{item.money}}</b></p>
                          </div>
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
              <el-col :span="24">
                <template v-if="otherphoto">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in fujianList">
                      <el-card class="box-card">
                        <div class="text item train">
                          <img src="@/assets/image/train_ticket.png" alt="">
                        </div>
                      </el-card>
                    </el-carousel-item>
                  </el-carousel>
                </template>
                <template v-else>
                  <el-card class="box-card" style="width:322px;height: 184px;">
                    <img src="@/assets/image/none.png" alt="" style="display:block;width:178px;margin:40px auto">
                  </el-card>
                </template>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
  import { listDept } from "@/api/system/dept";
  import { costList } from "@/api/business/mywork/expenses";

    export default {
        name: "expenses",
        data(){
          return{
            loading:true,
            queryParams:{},
            bumenOptions:[],
            statusOptions:[],
            costList:[],
            costtitle:"",
            addcosttitle:"",
            detailcosttitle:"",
            costexpenses:false,
            addcost:false,
            detailcost:false,
            iscostdata:true,
            otherphoto:true,
            costTotal:0,
            form:{
              bumen_department:'',
              inProject:'',
              costname:''
            },
            addcostform:{
              name:'',
              remark:'',
              money:'',
              num:0,
              fileList:[{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
            },
            department: [
              {
                dictValue: "1",
                dictLabel: "技术部"
              },
              {
                dictValue: "2",
                dictLabel: "软件部"
              }
            ],
            detailform:{
              inproject:'是',
              project:'马克尔\n' +
                '河北省小贷管理系统开发项目\n' +
                'XCV23456',
              reason:'唐山项目出差费用',
              remark:''
            },
            projectname:[
              {
                dictValue: "1",
                dictLabel: "马克尔/河北省小贷管理系统开发项目/XCV23456"
              },
              {
                dictValue: "2",
                dictLabel: "XCV23456"
              },

            ],
            profect: [
              {
                dictValue: "0",
                dictLabel: "否"
              },
              {
                dictValue: "1",
                dictLabel: "是"
              }
            ],
            costdata:[
              {title:'会议费用',content:'XXX会议费用',fujian:1,money:'199.05'},
              {title:'会议费用',content:'XXX会议费用',fujian:2,money:'199.05'},
            ],
            fujianList:[
              {src:'@/assets/image/train_ticket.png'}
            ],
            activities: [
              {
                content: "主管审批",
                placement: "top",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                size: "large",
                type: "success",
                icon: "el-icon-check",
                description: "审核通过"
              },
              {
                content: "主管审批2",
                placement: "top",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                size: "large",
                type: "info",
                description: "审核通过"
              },
              {
                content: "主管审批3",
                placement: "top",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                size: "large",
                type: "info",
                description: "审核通过"
              },
              {
                content: "主管审批",
                size: "large",
                placement: "top",
                type: "danger",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                description: "审核通过"
              },
              {
                content: "主管审批",
                size: "large",
                placement: "top",
                type: "info",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                description: "审核通过"
              },
              {
                content: "主管审批",
                size: "large",
                placement: "top",
                type: "info",
                timestamp: "丹尼尔（软件部)2020-05-22 ",
                description: "审核未通过"
              }
            ],
            addcostrules:{
              name: [
                { required: true, message: "项目名称", trigger: "blur" }
              ],
              money: [
                { required: true, message: "金额", trigger: "blur" }
              ],
              num: [
                { required: true, message: "票据数量", trigger: "blur" }
              ],
            }
          }
        },
        created() {
          var data=costList();
          this.costList=data.data;
          this.loading=false
          // 状态
          this.getDicts("sys_check_status").then(response => {
            this.statusOptions = response.data;
          });
          // 部门
          //获取部门列表
          listDept({parentId:'100'}).then(response => {
            response.data.forEach((val)=> this.bumenOptions.push({'dictValue': val.deptId,'dictLabel': val.deptName})
            )
          });

        },
        methods:{
          // 新建
          handleAdd(){
            this.costexpenses=true;
            this.costtitle='新增'
          },
          // 删除
          handleDelete(){},
          // 报送
          handleReport(){},
          // 导出
          handleExport(){},
          // 搜索
          handleQuery(){},
          // 重置
          resetQuery(){},
          handleRowClick(){
            this.detailcost=true;
            this.detailcosttitle='查看'
          },
          handleSelectionChange(){},
          handleUpdate(){},
          deleteticket(){},
          goaway(){
            this.addcosttitle="新建"
            this.addcost=true
          },
          savecost(){},
          cancelcost(){},
          handleRemove(){}
        }
    }
</script>

<style>
  .travel_container .el-carousel__indicators--horizontal{
    display: none;
  }
  .travel_container .el-card{
    height: 200px;
  }
  .travel_container .el-card__header{
    padding:0;
  }
  .travel_container .el-card__header .clearfix{
    margin:10px;
  }
  .travel_container .el-carousel__item{
    width: 62%;
  }
  .travel_container .el-carousel__item,.el-card{
    border-radius: 20px;
  }
  .travel_container .el-carousel__arrow--left {
    left: 74px;
  }
  .travel_container .box-card b{
    font-size: 16px;
  }
  .travel_container .box-card{
    width: 100%;
  }
  .travel_container .el-button--medium.is-circle{
    padding:6px;
  }
  .lf{
    float: left;
  }
</style>
