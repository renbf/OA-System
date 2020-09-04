<template>
  <div class="app-container travel_container myproject">
    <el-row :gutter="10"  class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新建项目</el-button
        >
      </el-col>
    </el-row>
    <el-row>
      <h3>进行中</h3>
    </el-row>
    <el-row class="doing">
      <el-card class="box-card lf">
        <div slot="header" class="clearfix">
          <span><b>OA项目开发</b></span>
          <el-button icon="el-icon-setting" style="float: right;" circle @click="editproject"></el-button>
          <p>2020-05-22至2020-12-31</p>
        </div>
        <div class="text item"  @click="projectdetail">
          <p>公司内容OA系统开发项目</p>
          <p><span>总计项目任务:35件</span><span>部门:软件部</span></p>
          <el-progress :percentage="50"></el-progress>
        </div>
      </el-card>
    </el-row>
    <el-row>
      <h3>已完成</h3>
    </el-row>
    <el-row class="end">
      <el-card class="box-card lf">
        <div slot="header" class="clearfix">
          <span><b>OA项目开发</b></span>
          <p>2020-05-22至2020-12-31</p>
        </div>
        <div class="text item">
          <p>公司内容OA系统开发项目</p>
          <p><span>总计项目任务:35件</span><span>部门:软件部</span></p>
          <p style="margin:0;font-size: 12px;">完成时间:2021-01-01(用时：130%)</p>
          <el-progress :percentage="100"  :format="format"></el-progress>
        </div>
      </el-card>
    </el-row>




<!--    新建项目-->
    <el-dialog :title="addproject"
               :visible.sync="addopen"
               width="650px" class="abow_dialog">
      <el-form  ref="form" :model="addform" :rules="addrules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="addform.name"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="bumenStatus">
          <el-checkbox-group v-model="addform.bumenStatus" @change="handleCheckedCitiesChange">
            <!--              <el-checkbox-button v-for="dict in department"-->
            <!--                                  :key="dict.dictValue"-->
            <!--                                  :label="dict.dictValue"-->
            <!--                                  :name="dict.dictLabel"-->
            <!--                                  border-->
            <!--              ></el-checkbox-button>-->
            <el-checkbox v-for="dict in department"
                         :label="dict.dictLabel"
                         border>
              {{dict.dictLabel}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="参与人员" prop="peoplevalue">
          <template>
            <el-transfer
              filterable
              :filter-method="filterMethod"
              filter-placeholder="请输入成员名称"
              v-model="addform.peoplevalue"
              :data="canyupeople">
            </el-transfer>
          </template>
        </el-form-item>
        <el-form-item label="项目时间" prop="tasktime">
          <el-time-picker
            is-range
            v-model="addform.projecttime"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <!--        项目描述-->
        <el-form-item label="项目描述" prop="projectremark">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="addform.projectremark"
          >
          </el-input>
        </el-form-item>
        <el-form-item  label="状态"  prop="projectstatus">
          <el-switch
            v-model="addform.projectstatus"
            active-text="启用">
          </el-switch>
          <span style="font-size: 13px;color:#ccc;margin-left: 20px;">注:状态为启用时参与人才会显示此项目</span>
        </el-form-item>
        <el-collapse v-model="matters_needing_attention">
          <el-collapse-item title="项目注意事项" name="1">
            <div>
              1、当项目状态为“禁用”时，可对项目进行“删除”操作。
            </div>
            <div>
              2、当项目任务有人参与并发表过内容时，项目与项目任务则不能被删除。但可“关闭项目”与“项目任务”。
            </div>
            <div>3、项目任务状态为“禁用”时，可对任务进行“删除”操作。</div>
            <div>
              4、当“关闭项目”或“关闭任务”后“项目”与“任务”将仅能“查看”
            </div>
          </el-collapse-item>
        </el-collapse>
        <div slot="footer" class="dialog-footer">
          <el-button>取消</el-button>
          <el-button type="primary">确定</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
  import { listDept } from "@/api/system/dept";
    export default {
      name: "index",
      data() {
        const generateData = _ => {
          const data = [];
          const namelist = ['迈克尔', '丹尼斯', '伊利斯', '卡梅隆'];
          const canyu = ['迈克尔', '丹尼斯', '伊利斯', '卡梅隆'];
          namelist.forEach((name, index) => {
            data.push({
              label: name,
              key: index,
              pinyin: canyu[index]
            });
          });
          return data;
        };
        return {
          // 穿梭框
          canyupeople: generateData(),
          addproject: '',
          addopen: false,
          addform: {
            name: '',
            bumenStatus: [],
            projectremark: '',
            projecttime: '',
            peoplevalue: [],
          },
          addrules: {
            name: [{required: true, message: "项目名称不能为空", trigger: "change"}],
            bumenStatus: [{required: true, message: "部门不能为空", trigger: "change"}],
            peoplevalue: [{required: true, message: "参与人员不能为空", trigger: "change"}],
            projecttime: [{required: true, message: "项目时间不能为空", trigger: "change"}],
            projectremark: [{required: true, message: "项目描述不能为空", trigger: "change"}],
            projectstatus: [{required: true, message: "状态必须选择", trigger: "change"}]
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

          matters_needing_attention: undefined,
// 穿梭框内容

          filterMethod(query, item) {
            return item.pinyin.indexOf(query) > -1;
          },
        }

      },
      created() {
        //获取部门列表
        listDept({parentId: '100'}).then(response => {
          response.data.forEach((val) => this.bumenOptions.push({'dictValue': val.deptId, 'dictLabel': val.deptName})
          )
        });
      },

      methods: {
        handleAdd() {
          this.addproject = "新建项目";
          this.addopen = true
        },
        format(percentage) {
          return percentage === 100 ? '完成' : `${percentage}%`;
        },
        handleCheckedCitiesChange() {
        },

        editproject() {
        },
        projectdetail() {
          console.log(123)
          this.$router.push({ path:'/myproject/detail'})
        }
      }
    }
</script>

<style>
.myproject .doing,.myproject .end{
  width: 316px;
}
.myproject .doing .clearfix,.myproject .end .clearfix{
  margin:0;
  margin-top: 10px;
}
.myproject .doing .el-card__header,.myproject .end .el-card__header{
  padding:2px 20px;
}

</style>
