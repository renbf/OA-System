<template>
  <div class="app-container travel_container projectdetail">
      <div class="one" name="1">{{projectInfo.projectName}} 项目负责人：{{projectInfo.leaderName}}
      </div>
      <el-card class="box-card">
        <div  style="width:30%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
          <el-form  ref="form" :model="projectInfo" label-width="80px">
            <el-form-item label="部门">
              <el-tag type="info">{{projectInfo.deptNames}}</el-tag>
            </el-form-item>
            <el-form-item label="项目任务">
              <el-tag type="info">{{projectInfo.taskNums}}件</el-tag>
            </el-form-item>
            <el-form-item label="项目时间">
              <div class="div1">
                <i class="el-icon-date" style="margin-left:10px;"></i>
                <span style="margin-left:40px;color:rgba(221, 221, 221,0.8);" >{{projectInfo.projectStartDate}}</span>
                <span style="margin-left:40px;">至</span>
                <span style="margin-left:40px; color:rgba(221, 221, 221,0.8);">{{projectInfo.projectEndDate}}</span>
              </div>
            </el-form-item>
            <el-form-item label="理由陈述">
              <el-input type="textarea" v-model="projectInfo.projectDesc"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div style="width:45%;float:left;padding:0 20px;border-right: 1px solid #ddd;">
          <p>参与人员</p>
          <template v-for="item in deptMemberList">
            <p>{{item.deptName}}</p>
            <p>
              <el-tag type="info" v-for="item1 in item.members">{{item1.memberName}}</el-tag>
            </p>
          </template>
        </div>


        <div style="width: 15%;float:left;padding:0 20px;position: relative">
          <svg-bar :value="projectInfo.projectProgress" :options="projectoptions" style="position:absolute;left:27px;top:7px"></svg-bar>
          <svg-bar :value="timeprocess" :options="timeoptions" style="position:absolute"></svg-bar>
          <div style="position: absolute;width: 192px;height: 220px;left:32px;top:20px" class="clear">
            <div class="lf" style="width:50%;height:80%;border-right: 2px dotted #ddd;text-align: center">
              <p style="margin-top:60%;margin-bottom:0">
                <span style="display: inline-block;width:6px;height:12px;background:#1989FA"></span>
                <span>任务进度</span>
              </p>
              <p style="margin-top: 0"><b style="font-size: 25px;">{{projectInfo.projectProgress}}%</b></p>
            </div>
            <div class="lf" style="width:50%;height: 100%;text-align: center">
              <p style="margin-top: 60%;margin-bottom:0">
                <span style="display: inline-block;width:6px;height:12px;background:#C6E2FF"></span>
                <span>时间进度</span>
              </p>
              <p style="margin-top: 0"><b style="font-size: 25px;">{{timeprocess}}%</b></p>
            </div>
          </div>
        </div>
      </el-card>
    <!--项目组申请模块-->
    <div style="margin-top:40px;">
      <p class="apply">
        <span>项目组申请</span>
        <span>
          <el-button>全部报送</el-button>
        </span>
        <span>
          <el-button icon="el-icon-right" size="small" circle @click="submission"></el-button>
        </span>
      </p>
    </div>
    <div class="card-carousel-wrapper" style="margin-left:-110px;">
      <el-button class="card-carousel--nav__left" type="info" icon="el-icon-arrow-left" circle @click="moveCarousel(-1)" :disabled="atHeadOfList"></el-button>
      <div class="card-carousel">
        <div class="card-carousel--overflow-container">
          <div class="card-carousel-cards clear" :style="{ transform: 'translateX' + '(' + currentOffset + 'px' + ')'}">
            <div class="card-carousel--card lf" v-for="item in applyproject">
              <div style="border-bottom:1px solid #ddd">
                <p>
                  <span style="margin-left: 10px;"><b>{{item.title}}</b></span>
                  <span class="rt" style="margin-right: 10px;">
                    <el-button circle icon="el-icon-message" type="danger"></el-button>
                  </span>
                  <span class="rt" style="margin-right: 10px;">
                    <el-button circle icon="el-icon-delete"></el-button>
                  </span>
                </p>
                <p style="margin-left: 10px; font-size: 12px;color:#C0C4CC">申请时间:{{item.applytime}}</p>
              </div>
              <div>
                <p style="margin-left: 10px;font-size: 13px;">申请人：{{item.applypeople}}</p>
                <p style="margin-left: 10px;font-size: 13px;">原  因：{{item.applyreason}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-button class="card-carousel--nav__right" type="info" icon="el-icon-arrow-right" circle @click="moveCarousel(1)" :disabled="atEndOfList"></el-button>
    </div>
    <!--项目任务模块-->
    <!--项目任务模块-->

    <div  style="margin-bottom: 10px;margin-top:45px;" >
     <h2>项目任务</h2>
      <el-button type="success"><i class="el-icon-message" style="margin-right:5px;" ></i>报送
      </el-button>
      <el-button type="warning"><i class=" el-icon-download" style="margin-right:5px;"></i> 导出</el-button>
    </div>
    <div v-if="model=='列表'" style="margin-top:20px;">

      <el-form :modal="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="任务时间">
          <el-date-picker
            v-model="queryParams.taskDates"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="queryParams.taskStatus"
            placeholder="选择状态"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
          >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
          >
        </el-form-item>
      </el-form>
      <!--      列表-->
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="taskList"
        @selection-change="handleSelectionChange"
        @row-click="lookdetail=true"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="编号"
          prop="taskNumber"
          :show-overflow-tooltip="true"
          style="width:20px;"
        />
        <el-table-column
          label="标题"
          prop="taskName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="任务内容"
          prop="taskDesc"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="更新日期"
          prop="updateTime"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="参与人"
          :show-overflow-tooltip="true"
        ><template slot-scope="scope">
          {{scope.row.memberNums}}人
        </template>
        </el-table-column>
        <el-table-column
          label="开始时间"
          prop="taskStartDate"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="结束时间"
          prop="taskEndDate"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="任务进度"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            {{scope.row.taskProgress}}%
          </template>
        </el-table-column>
        <el-table-column
          label="时间进度"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.timeProgress <= 0">0%</span>
            <span v-show="scope.row.timeProgress > 0">{{scope.row.timeProgress}}%</span>
          </template>
        </el-table-column>
        <el-table-column
          label="任务状态"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.taskStatus == 0">进行中</span>
            <span v-show="scope.row.taskStatus == 1">完成</span>
          </template>
        </el-table-column>

        <el-table-column
          label="任务状态"
          :show-overflow-tooltip="true"
        >

          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="1"
              inactive-value="0"
              @change="handlerTaskStatus(scope.row)"
              active-text="启用">
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!--  2是未报送按钮全部显示 -->
            <div>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                @click.stop="lookUpdate(scope.row)"
                style="color:#6C6C6C"
              >编辑</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-message"
                @click.stop="handleCloseTask(scope.row)"
                style="color:#6C6C6C"
              >报送</el-button
              >

            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          style="float:right;margin-top:30px;margin-bottom:80px;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageInfo.pageNum"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageInfo.total">
        </el-pagination>
      </div>
    </div>
    <div v-if="model=='时间'">
      <!--        项目进度甘特图-->
      <component :is="activeIndex"></component>
    </div>

    <el-dialog :title="addproject"
               :visible.sync="addopen"
               width="650px" class="abow_dialog">
      <el-form  ref="addform" :model="addform" :rules="addrules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="addform.projectName"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="leaderId">
          <el-select v-model="addform.leaderId" placeholder="请选择" ref="leaderName">
            <el-option
              v-for="item in userDeptUserList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="bumenStatus">
          <el-checkbox-group v-model="addform.bumenStatus" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="dict in department"
                         :label="dict.deptId"
                         border>{{dict.deptName}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="参与人员" prop="userList">
          <template>
            <el-transfer
              filterable
              :filter-method="filterMethod"
              filter-placeholder="请输入成员名称"
              v-model="addform.userList"
              :data="memberList">
            </el-transfer>
          </template>
        </el-form-item>
        <el-form-item label="项目日期" prop="tasktime">
          <el-date-picker
            v-model="addform.projectDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>

        </el-form-item>
        <!--        项目描述-->
        <el-form-item label="项目描述" prop="projectDesc">
          <el-input
            type="textarea"
            placeholder="请输入"
            v-model="addform.projectDesc"
          >
          </el-input>
        </el-form-item>
        <el-form-item  label="状态"  prop="status">
          <el-switch
            v-model="addform.status"
            active-value="1"
            inactive-value="0"
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>


    <!--关闭按钮模块-->

    <el-dialog
      title="关闭项目"
      :visible.sync="dialogVisible"
      width="30%">
      <el-divider></el-divider>
      <el-form>
        <el-form-item>
          关闭原因

          <el-input
            style="width:400px;margin-left:20px; vertical-align:text-top;display:inline-block;height:100px;"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入至少20字的原因描述"
            v-model="textarea2">
          </el-input>
        </el-form-item>
        <el-form-item>

          <el-collapse >
            <el-collapse-item title=" 关闭注意事项" name="1">
              <div>
                1、当执行关闭操作后，项目将自动默认完成所有任务，并不可在进行修改和编辑。
              </div>
              <div>
                2、关闭操作完成后将不可逆转。
              </div>
              <div>3、关闭后参与人将不能再进行项目的编辑操作。</div>

            </el-collapse-item>
          </el-collapse>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleCloseProject">确 定</el-button>
  </span>
    </el-dialog>

    <!--关闭任务-->
    <el-dialog
      title="关闭任务"
      :visible.sync="dialogTaskVisible"
      width="30%">
      <el-divider></el-divider>
      <el-form ref="closeTaskform" :model="closeTaskform" :rules="closeTaskformRules">
        <el-form-item prop="closeReason">
          关闭原因
          <el-input
            style="width:400px;margin-left:20px; vertical-align:text-top;display:inline-block;height:100px;"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入至少20字的原因描述"
            v-model="closeTaskform.closeReason">
          </el-input>
        </el-form-item>
        <el-form-item>

          <el-collapse >
            <el-collapse-item title=" 关闭注意事项" name="1">
              <div>
                1、当执行关闭操作后，项目将自动默认完成所有任务，并不可在进行修改和编辑。
              </div>
              <div>
                2、关闭操作完成后将不可逆转。
              </div>
              <div>3、关闭后参与人将不能再进行项目的编辑操作。</div>

            </el-collapse-item>
          </el-collapse>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogTaskVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleCloseTaskSubmit">确 定</el-button>
  </span>
    </el-dialog>


 <!--项目任务列表弹框-->
    <el-dialog
      :title="looktitle"
      :visible.sync="lookdetail"
      width="500px"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="任务进度"></el-form-item>
        <el-form-item label="时间进度">
          <el-progress :percentage="50"></el-progress>
        </el-form-item>
        <el-form-item label="任务进度">
          <el-progress :percentage="50"></el-progress>
        </el-form-item>
        <el-form-item >
          <el-button icon="el-icon-edit-outline" circle @click="openlittle=true"></el-button>
        </el-form-item>

        <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="任务内容" name="4">
        </el-collapse-item>
          </el-collapse>

        <el-form-item label="参与人员">
          <el-tag type="info" >{{form.name1}}</el-tag>
          <el-tag type="info" style="margin-left:10px;">{{form.name2}}</el-tag>
          <el-tag type="info" style="margin-left:10px;">{{form.name3}}</el-tag>

        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker
            v-model="form.lookvalue"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>

        </el-form-item>

        <el-form-item label="活动内容">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>

        <el-collapse v-model="activeNames" @change="handleChange">
          <el-collapse-item title="任务日志" name="4">
          </el-collapse-item>
        </el-collapse>

        <el-timeline>
          <el-timeline-item timestamp="2018/4/12" placement="top">
            <el-card>
              <h4>迈克尔06/05 19:39</h4>
              <p>1.客服解决问题4件</p>
              <p>1.解决投诉1件</p>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="2018/4/3" placement="top">
            <el-card>
              <h4>迈克尔06/05 19:39</h4>
              <p>1.客服解决问题4件</p>
              <p>1.解决投诉1件</p>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="2018/4/2" placement="top">
            <el-card>
              <h4>迈克尔06/05 19:39</h4>
              <p>1.客服解决问题4件</p>
              <p>1.解决投诉1件</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>

      </el-form>
    </el-dialog>
<!--报销数据库编辑里的小编辑按钮-->
    <el-dialog
      title="设置任务进度"
      :visible.sync="openlittle"
      width="30%">
      <el-form ref="form2" :model="form2" label-width="80px">
          <el-progress :percentage="50" style="width:300px;float:left;"></el-progress>
          <el-input-number v-model="form2.num" @change="handleChange3" :min="1" :max="10" label="描述文字" style="width:150px;margin-top:-10px"></el-input-number>
         <h6 style="color:#BEBEBE">注：当设置“100%”时 任务及为完成状态，则任务不可在进行修改与编辑</h6>


      </el-form>
      <el-divider></el-divider>
        <span slot="footer" class="dialog-footer">
    <el-button @click="openlittle = false">取 消</el-button>
    <el-button type="primary" @click="openlittle = false">确 定</el-button>
  </span>
    </el-dialog>


    <!--table里编辑弹框-->
    <el-dialog
               :visible.sync="lookOpen"
               width="600px" class="abow_dialog">
      <el-form ref="lookForm" :model="lookForm" label-width="80px">
      <el-form-item label="参与人员">
        <el-tag type="info" >{{lookForm.name1}}</el-tag>
        <el-tag type="info" style="margin-left:10px;">{{lookForm.name2}}</el-tag>
        <el-tag type="info" style="margin-left:10px;">{{lookForm.name3}}</el-tag>

      </el-form-item>
      <el-form-item label="活动时间">
        <el-date-picker
          v-model="lookForm.lookvalue"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>

      </el-form-item>

      <el-form-item label="活动内容">
        <el-input type="textarea" v-model="lookForm.desc"></el-input>
      </el-form-item>


      <el-form-item label="工作内容">
        <el-input type="textarea" v-model="lookForm.desc"></el-input>
      </el-form-item>

      <el-upload
        class="upload-demo"
        action="https://jsonplaceholder.typicode.com/posts/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :limit="3"
        :on-exceed="handleExceed"
        :file-list="form.fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>

      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="任务日志" name="4">
        </el-collapse-item>
      </el-collapse>

      <el-timeline>
        <el-timeline-item timestamp="2018/4/12" placement="top">
          <el-card>
            <h4>迈克尔06/05 19:39</h4>
            <p>1.客服解决问题4件</p>
            <p>1.解决投诉1件</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item timestamp="2018/4/3" placement="top">
          <el-card>
            <h4>迈克尔06/05 19:39</h4>
            <p>1.客服解决问题4件</p>
            <p>1.解决投诉1件</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item timestamp="2018/4/2" placement="top">
          <el-card>
            <h4>迈克尔06/05 19:39</h4>
            <p>1.客服解决问题4件</p>
            <p>1.解决投诉1件</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      </el-form>
      <div slot="footer" class="dialog-footer">

        <el-button @click="lookCancel">取消</el-button>
        <el-button type="primary" @click="lookSubmitForm">确定</el-button>
      </div>
    </el-dialog>






  </div>
</template>

<script>
  import project_progress from './project_progress';
  import { userDeptList } from "@/api/system/dept";
  import { userDeptUsers } from "@/api/system/user";
  import { listBusiProject,editBusiProject,changeStatus,addBusiTask,updateBusiTask,listTask,getProjectInfo,getTaskInfo,delBusiProject,delBusiTask,changeTaskStatus,closeProject,closeTask } from "@/api/business/mywork/myproject";

  export default {
    name: "detail",
    components: {
      project_progress
    },
    data() {
      return {
      looktitle:"报销数据库项目",
        lookOpen:false,
        // 是否显示弹出层
        lookdetail: false,
        //当前页数
        currentPage4:1,
        dialogVisible: false,
        applyproject: [
          {
            title: '任务时间增加申请1',
            applytime: '2020-05-21',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请2',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请3',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请4',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请5',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请6',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请7',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请8',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请9',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          },
          {
            title: '任务时间增加申请10',
            applytime: '2020-05-22',
            applypeople: '迈克尔',
            applyreason: '因功能修改需重新调整，需增加任务时间，故作此申请。'
          }
        ],
        //关闭原因
        textarea2: '',
        dialogTaskVisible: false,
        closeTaskform: {
          taskId:undefined,
          closeReason:undefined
        },
        closeTaskformRules: {
          closeReason: [{required: true, message: "关闭不能为空", trigger: "blur"}],
        },
        addproject: "",
        addopen:false,
        openlittle:false,
        projectId:this.$route.query.projectId,
        form:{
          deptId: '',
          name1:"迈克尔",
          name2:"任宝峰",
          name3:"谷歌",
          desc:"",
          lookvalue: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
          fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]


        },
        lookForm:{
          deptId: '',
          name1:"迈克尔",
          name2:"任宝峰",
          name3:"谷歌",
          desc:"",
          lookvalue: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
          fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]

        },
        form2:{
          num:1,
        },
        //新建编辑项目任务table数据
        addform: {
          projectName: '',
          leaderId:undefined,
          bumenStatus: [],
          projectDesc: '',
          projectDate: '',
          userList: [],
          status:''
        },
        addrules: {
          projectName: [{required: true, message: "项目名称不能为空", trigger: "change"}],
          bumenStatus: [{required: true, message: "部门不能为空", trigger: "change"}],
          userList: [{required: true, message: "参与人员不能为空", trigger: "change"}],
          projectDate: [{required: true, message: "项目时间不能为空", trigger: "change"}],
          projectDesc: [{required: true, message: "项目描述不能为空", trigger: "change"}],
          status: [{required: true, message: "状态必须选择", trigger: "change"}]
        },
        //责任人
        userDeptUserList:[],
        memberList:[],
        taskMemberList:[],
        department: [],
        matters_needing_attention: undefined,
        projectInfo: {},
        //部门成员列表
        deptMemberList: [],
        taskform:{
          taskId:undefined,
          taskName:'',
          taskNumber:undefined,
          taskDate: '',
          taskDesc:'',
          status:'',
          userList: [],
        },
        taskrules: {
          taskName: [{required: true, message: "任务名称不能为空", trigger: "change"}],
          taskNumber: [{required: true, message: "任务编号不能为空", trigger: "change"}],
          taskDate: [{required: true, message: "任务时间不能为空", trigger: "change"}],
          taskDesc: [{required: true, message: "任务描述不能为空", trigger: "change"}],
          userList: [{required: true, message: "参与人员不能为空", trigger: "change"}],
          status: [{required: true, message: "状态必须选择", trigger: "change"}]
        },
        title: "OA项目开发 编号：xcv23456 项目人：迈克尔",
        currentOffset: 0,
        windowSize: 3,
        paginationFactor: 322,
        loading: false,
        model: '列表',
        activeNames: ['1'],
        projectprocess: '',
        timeprocess: '',
        statusOptions: [],
        queryParams: {
          taskDates: '',
          taskStatus: '',
          page:1,
          limit:10
        },
        status: [
          {
            dictValue: "1",
            dictLabel: "未报送"
          },
          {
            dictValue: "2",
            dictLabel: "已报送"
          }
        ],
        taskList: [],
        pageInfo: {},
        taskIds: [],
        activeIndex: 'project_progress',

        value: true,
      }
    },
    created() {


      // 状态
      this.getDicts("task_status").then(response => {
        this.statusOptions = response.data;
      });
      this.getProject();
      //获取部门列表
      userDeptList().then(response => {
        if (response.code == 200) {
          this.department = response.data;
        }
      });
      this.getUserDeptUsers();
      this.getTaskList();
    },
    computed: {
      atEndOfList() {
        return this.currentOffset <= (this.paginationFactor * -1) * (this.applyproject.length - this.windowSize);
      },
      atHeadOfList() {
        return this.currentOffset === 0;
      },
      projectoptions: function () {
        return {
          radius: 100,//大小
          duration: 2,//变化时间长度
          circleWidth: 7,//圆环宽度
          pathColors: ['#eee', '#1989FA'], //圆环 底色和 上面的颜色
          circleLineCap: 'round',//进度条 形状  圆环 或者矩形
          text: function (value) {
            return '';
          },

        }
      },
      timeoptions: function () {
        return {
          radius: 107,//大小
          duration: 2,//变化时间长度
          circleWidth: 7,//圆环宽度
          pathColors: ['#eee', '#C6E2FF'], //圆环 底色和 上面的颜色
          circleLineCap: 'round',//进度条 形状  圆环 或者矩形
          text: function (value) {
            return '';
          },

        }
      },
    },
    methods: {

      //上传附件
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      //全部报送任务
      submission(){
        this.$router.push({ path:'/myproject/submission'})
      },
      moveCarousel(direction) {
        // Find a more elegant way to express the :style. consider using props to make it truly generic
        if (direction === 1 && !this.atEndOfList) {
          this.currentOffset -= this.paginationFactor;
        } else if (direction === -1 && !this.atHeadOfList) {
          this.currentOffset += this.paginationFactor;
        }
      },
      //计数器控件数据
      handleChange(value){
        console.log(value);
      },
      //新建编辑项目任务
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1;
      },

      resetTaskForm() {
        this.taskform = {
          taskId:undefined,
          taskName:'',
          taskNumber:undefined,
          taskDate: '',
          taskDesc:'',
          status:'',
          userList: [],
        };
        this.resetForm("taskform");
      },
      getProject() {
        let _this = this;
        getProjectInfo({projectId:_this.projectId}).then(response => {
          if(response.code == 200){
            _this.projectInfo = response.data;
            _this.projectInfo.projectStartDate = _this.projectInfo.projectStartDate.substring(0, 10);
            _this.projectInfo.projectEndDate = _this.projectInfo.projectEndDate.substring(0, 10);
            //任务时间进度
            _this.timeprocess = _this.projectInfo.timeProgress;
            if(_this.timeprocess < 0){
              _this.timeprocess = 0;
            }
            let busiProjectMembers= _this.projectInfo.busiProjectMembers;
            _this.getDeptMemberList(busiProjectMembers);
          }
        });
      },
      getDeptMemberList(busiProjectMembers) {
        let _this = this;
        let map = new Map();
        _this.deptMemberList = [];
        let deptMemberList = _this.deptMemberList;
        _this.taskMemberList = [];
        busiProjectMembers.forEach((val)=>{
          let deptId = val.deptId;
          _this.taskMemberList.push({key:val.memberId,label:val.memberName});
          if (map.has(deptId)) {
            let index = map.get(deptId);
            let deptObj = deptMemberList[index];
            let members = deptObj.members;
            members.push({memberId:val.memberId,memberName:val.memberName});
          }else{
            let size = map.size;
            map.set(deptId,size);
            let members = [];
            members.push({memberId:val.memberId,memberName:val.memberName});
            deptMemberList.push({deptId:deptId,deptName:val.deptName,members:members});
          }
        });
      },
      getUserDeptUsers() {
        let _this = this;
        userDeptUsers().then(response => {
          if (response.code == 200) {
            _this.userDeptUserList = response.data;
          }
        });
      },
      getTaskList() {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.projectId = _this.projectId;
        queryParams.taskStartDate = queryParams.taskDates[0];
        queryParams.taskEndDate = queryParams.taskDates[1];
        queryParams.taskDates = "";
        listTask(queryParams).then(response => {
          if (response.code == 200) {
            _this.taskList = response.data;
            _this.pageInfo = response.pageInfo;
          }
        });
      },
      handleChange() {
      },
      handleChange3(){

      },
      handleReport() {
      },
      handleExport() {
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.page = 1;
        this.getTaskList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.form.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleSelectionChange(val) {
        let _this = this;
        let taskIds = [];
        val.forEach((item) => {
          taskIds.push(item.taskId);
        });
        _this.taskIds = taskIds;
      },
     lookUpdate(item) {
        this.lookOpen=true;
        this.updateSetValue(item);
      },
      updateSetValue(item) {
        let _this = this;
        _this.taskform = {
          taskId:item.taskId,
          taskName:item.taskName,
          taskNumber:item.taskNumber,
          taskDate: [item.taskStartDate,item.taskEndDate],
          taskDesc:item.taskDesc,
          status:item.status,
          userList: [],
        };
        getTaskInfo({taskId:item.taskId}).then(response => {
          if(response.code == 200){
            let taskMembers = response.data;
            taskMembers.forEach((val) =>{
              _this.taskform.userList.push(val.memberId);
            });
          }
        });
      },
      changemodel() {
        if (this.model == '时间') {
          this.model = '列表'
        } else {
          this.model = '时间'
        }
      },

      handleCheckedCitiesChange(list) {
        let _this = this;
        let userList = _this.addform.userList;
        _this.addform.userList = [];
        _this.memberList = [];
        for (var i in list) {
          let deptId = list[i];
          _this.userDeptUserList.forEach((val) => {
              if (val.deptId == deptId) {
                _this.memberList.push({key: val.userId,label: val.nickName});
              }
            }
          );
        }
      },
      filterMethod(query, item) {
        // let aa = item.label.indexOf(query) > -1;
        if (query == "") {
          return true;
        }else{
          return item.label.indexOf(query) > -1;
        }
      },
      cancel() {
        this.addopen = false;
      },
      taskCancel() {
        this.add3 = false;
      },
      lookCancel(){
        this.lookOpen = false;
      },
      lookSubmitForm(){
        this.lookOpen = false;
      },
      //提交项目
      submitForm(){
        let _this = this;
        let leaderName = _this.$refs.leaderName.selected.label;
        _this.$refs.addform.validate(valid => {
          if (valid) {
            let addform = _this.addform;
            addform.leaderName = leaderName;
            addform.projectId = _this.projectId;
            if (addform.projectId != undefined) {
              editBusiProject(addform).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.addopen = false;
                  this.getProject();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {

            }
          }
        });
      },
      taskSubmitForm() {
        let _this = this;
        _this.$refs.taskform.validate(valid => {
          if (valid) {
            let form = _this.taskform;
            form.projectId = _this.projectId;
            if (form.taskId != undefined) {
              updateBusiTask(form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.add3 = false;
                  this.getTaskList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addBusiTask(form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.add3 = false;
                  this.getTaskList();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      //任务启用停用
      handlerTaskStatus(item) {
        let _this = this;
        let form = {
          taskId:item.taskId,
          status:item.status
        };
        changeTaskStatus(form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("修改成功");
          } else {
            this.msgError(response.msg);
          }
        });
      },
      //关闭项目
      handleCloseProject() {
        let _this = this;
        let form = {
          projectId:_this.projectId,
          closeReason:_this.textarea2
        };
        closeProject(form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("关闭成功");
            this.dialogVisible = false;
          } else {
            this.msgError(response.msg);
          }
        });
      },
      //关闭任务
      handleCloseTaskSubmit() {
        let _this = this;
        let form = _this.closeTaskform;
        _this.$refs.closeTaskform.validate(valid => {
          if (valid) {
            closeTask(form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("关闭成功");
                this.dialogTaskVisible = false;
              } else {
                this.msgError(response.msg);
              }
            });
          }
        });
      },
      handleSizeChange(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.limit = val;
        this.getTaskList();
      },
      handleCurrentChange(val) {
        let _this = this;
        let queryParams = _this.queryParams;
        queryParams.page = val;
        this.getTaskList();
      },
      //关闭任务弹框
      handleCloseTask(item) {
        this.dialogTaskVisible = true;
        this.resetCloseTaskform();
        this.closeTaskform.taskId = item.taskId;
      },
      resetCloseTaskform() {
        this.closeTaskform = {
          taskId:undefined,
          closeReason:undefined
        }
        this.resetForm("closeTaskform");
      },
      //删除
      handleDeleteTask(item) {
        let _this = this;
        this.$confirm('是否确认删除当前任务吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBusiTask({taskIds:[item.taskId]});
        }).then(() => {
          this.msgSuccess("删除成功");
          this.getTaskList();
        }).catch(function() {});
      },
    }

  }
</script>

<style>
  element.style{
    height: 300px !important;
  }
  .div1{
    width: 360px;
    height: 30px;
    border-radius: 5px;
    line-height: 30px;
    border:1px solid rgba(221, 221, 221,0.8)
  }
  .projectdetail .gantt-elastic__grid-line-time {
    display: none;
  }
  .projectdetail .el-button--primary.is-disabled{
    display: block;
    margin-left: 0;
    margin-right: 0;
  }
  .projectdetail .el-button--small.is-circle{
    padding:3px;
    font-size: 20px;
  }
  .projectdetail .card-carousel-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 20px 0 40px;
    color: #666a73;
  }

  .projectdetail .card-carousel {
    display: flex;
    justify-content: center;
    width:85%;
  }
  .projectdetail .card-carousel--overflow-container {
    overflow: hidden;
  }
  /*.projectdetail .card-carousel--nav__left, .card-carousel--nav__right {*/
  /*  display: inline-block;*/
  /*  width: 15px;*/
  /*  height: 15px;*/
  /*  padding: 10px;*/
  /*  box-sizing: border-box;*/
  /*  border-top: 2px solid #42b883;*/
  /*  border-right: 2px solid #42b883;*/
  /*  cursor: pointer;*/
  /*  margin: 0 10px;*/
  /*  transition: transform 150ms linear;*/
  /*}*/
  .projectdetail .card-carousel--nav__left[disabled], .card-carousel--nav__right[disabled] {
    opacity: 0.2;
    border-color: black;
  }
  /*.projectdetail .card-carousel--nav__left {*/
  /*  transform: rotate(-135deg);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__left:active {*/
  /*  transform: rotate(-135deg) scale(0.9);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__right {*/
  /*  transform: rotate(45deg);*/
  /*}*/
  /*.projectdetail .card-carousel--nav__right:active {*/
  /*  transform: rotate(45deg) scale(0.9);*/
  /*}*/

  .projectdetail .card-carousel-cards {
    display: block;
    transition: transform 150ms ease-out;
    transform: translatex(0px);
    width: 4000px;
    overflow: hidden;
  }
  .projectdetail .card-carousel-cards .card-carousel--card {
    width:322px;
    height:184px;
    margin: 0 10px;
    cursor: pointer;
    border:2px solid #ddd;
    background-color: #fff;
    border-radius: 10px;
    z-index: 3;
    margin-bottom: 2px;
  }
  .projectdetail .card-carousel-cards .card-carousel--card:first-child {
    margin-left: 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card:last-child {
    margin-right: 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer {
    border-top: 0;
    padding: 7px 15px;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p {
    padding: 3px 0;
    margin: 0;
    margin-bottom: 2px;
    font-size: 19px;
    font-weight: 500;
    color: #2c3e50;
    user-select: none;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2) {
    font-size: 12px;
    font-weight: 300;
    padding: 6px;
    background: rgba(40, 44, 53, 0.06);
    display: inline-block;
    position: relative;
    margin-left: 4px;
    color: #666a73;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2):before {
    content: "";
    float: left;
    position: absolute;
    top: 0;
    left: -12px;
    width: 0;
    height: 0;
    border-color: transparent rgba(40, 44, 53, 0.06) transparent transparent;
    border-style: solid;
    border-width: 12px 12px 12px 0;
  }
  .projectdetail .card-carousel-cards .card-carousel--card--footer p:nth-of-type(2):after {
    content: "";
    position: absolute;
    top: 10px;
    left: -1px;
    float: left;
    width: 4px;
    height: 4px;
    border-radius: 2px;
    background: white;
    box-shadow: -0px -0px 0px #004977;
  }

  .projectdetail .lf{
    float: left;
  }
  .projectdetail .rt{
    float: right;
  }
  .projectdetail .clear{
    clear:both;
    content:"";
    display: block;
  }
  .projectdetail .apply >span{
    margin-left: 20px;
  }
</style>


<style lang="scss" scoped>
  .box-card{
    width: 1600px;
    height:300px ;
  }
  .sty{
    margin-left: 10px;
  }
  .one{
    height: 60px;
    line-height: 60px;
  }
  .demo{
    padding: 5px 15px;
    position: relative;
  .chart-title {
    position: absolute;
    transform: translateX(-50%);
    left: 50%;
  i {
    font-style: normal;
    padding-right: 20px;
  }
  i:before {
    content: '';
    display: inline-block;
    width: 30px;
    height: 15px;
    border-radius: 5px;
    vertical-align: bottom;
    margin-right: 3px;
    background-color: #3e84e9;
  }
  i.to-be-completed:before {
    background-color: #d4cece;
  }
  i.timeout:before {
    background-color: #c23531;
  }
  }
  #gantt-chart {
    margin: 1em auto;
    height: 500px;
    width: 100%;
  }
  }
</style>
