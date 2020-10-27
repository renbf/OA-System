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
            v-for="dict in departmentOptions"
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
      :data="expensesList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报销时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="项目"
        prop="projectName"
        :show-overflow-tooltip="true"
        width="200"
      />
      <el-table-column label="部门" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ selectDictLabel(departmentOptions, scope.row.deptId) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="交通费"
        prop="expenses_travel"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="出差补贴"
        prop="expenses_chuchai"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="其他费用"
        prop="expenses_othermoney"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="总金额"
        prop="expenses_allmoney"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="状态"
        prop="expenses_status"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="center"
        label="当前审批人"
        prop="expenses_exmain"
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
<!--    添加报销-->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="600px" class="abow_dialog">

      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col>
            <el-form-item label="部门" prop="deptId">
              <el-col :span="7">
                <el-select
                  v-model="form.deptId"
                  placeholder="请选择"
                  clearable
                >
                  <el-option
                    v-for="(item, index) in departmentOptions"
                    :key="index"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  ></el-option>
                </el-select>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="在项目中" prop="inPrjFlag">
              <el-radio-group v-model="form.inPrjFlag"  @change="changeHandler">
                <el-radio
                  v-for="(dict,index) in yesOrNo"
                  :key="index"
                  :label="dict.dictValue"
                  :value="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="报销项目" prop="projectName">
              <el-select
                v-model="form.projectId"
                placeholder="选择报销项目"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="dict in projectOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="报销事由" prop="reimburseReason">
            <el-input v-model="form.reimburseReason" type="textarea" placeholder="请输入内容"></el-input>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="reimburseSave">保存</el-button>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="交通费">
              <el-button type="primary" icon="el-icon-plus" circle @click="addtransport"></el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <template v-if="travel">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in transportList" >
                  <el-card class="box-card" @click.native="editTransport(item)">
                    <div slot="header" class="clearfix">
                      <span  style="font-size: 16px;margin-top: 10px;line-height: 35px;"><b>
                        {{ selectDictLabelByType(GLOBAL.TRANSPORT_TYPE, item.trafficType) }}
                      </b></span>
                      <el-button icon="el-icon-delete"  style="float: right;" circle @click="deleteticket"></el-button>
                    </div>
                    <div class="text item train">
                      <div>
                        <p><b>{{item.departureStation}}</b></p>
                        <p>{{item.trafficStartDate}}</p>
                        <p>附件 {{item.fujian}}张</p>
                      </div>
                      <div>
                        <p>至</p>
                      </div>
                      <div>
                        <p><b>{{item.terminalStation}}</b></p>
                        <p>{{item.trafficEndDate}}</p>
                        <p>￥<b>{{item.amountTotal}}</b></p>
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
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="出差补助">
              <el-button type="primary" icon="el-icon-plus" circle @click="goaway"></el-button>
            </el-form-item>
          </el-col>
        </el-row>
<!--        出差补贴-->
        <el-row>

          <el-col :span="24">
            <template v-if="butie">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in butieList">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span  style="font-size: 16px;line-height: 35px;"><b>{{item.title}}</b></span>
                      <el-button icon="el-icon-delete"  style="float: right;" circle @click="deleteticket"></el-button>
                    </div>
                    <div class="text item train">
                      <div>
                        <p>人数</p>
                        <p><b>{{item.people}}人</b></p>
                        <p>附件 {{item.fujian}}张</p>
                      </div>
                      <div>
                        <p>天数</p>
                        <p><b>{{item.day}}天</b></p>
                      </div>
                      <div>
                        <p>补贴标准</p>
                        <p>￥<b>{{item.money}}</b></p>
                        <p>￥<b>{{item.allmoney}}</b></p>
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
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="其他费用">
              <el-button type="primary" icon="el-icon-plus" circle @click="otherclick"></el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        其他费用-->
        <el-row>
          <el-col :span="24">
            <template v-if="other">
              <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                <el-carousel-item v-for="item in otherList">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span  style="font-size: 16px;line-height: 35px;"><b>{{item.title}}</b></span>
                      <el-button icon="el-icon-delete"  style="float: right;" circle @click="deleteticket"></el-button>
                    </div>
                    <div class="text item train">
                      <div>
                        <p>附件 {{item.fujian}}张</p>
                      </div>
                      <div>
                        <p>￥<b>{{item.allmoney}}</b></p>
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
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="附件">
              <el-button type="primary" icon="el-icon-download" circle></el-button>
              <span>共13张</span>
            </el-form-item>
          </el-col>
        </el-row>
<!--        附件-->
        <el-col :span="24">
          <template v-if="fujian">
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
      </el-form>


    </el-dialog>


<!--新建交通费用-->
    <el-dialog
      :title="transporttitle"
      :visible.sync="transport"
      width="600px" class="abow_dialog">

      <el-form ref="form" :model="transportform" :rules="transportrules" label-width="80px">
        <el-form-item label="交通类型" prop="trafficType">
          <el-select v-model="transportform.trafficType" size="small">
            <el-option
              v-for="item in trafficTypeOptions"
              :key="item.dictId"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="时间" prop="trafficDate">
          <el-date-picker
            v-model="transportform.trafficDate"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
<!--起始位置-->
        <el-form-item label="起始位置" prop="departureStation">
          <el-autocomplete
            class="inline-input"
            v-model="transportform.departureStation"
            :fetch-suggestions="querySearch"
            placeholder="请输入始发站"
            :trigger-on-focus="true"
            @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
<!--        终点位置-->
        <el-form-item label="终点位置" prop="terminalStation">
          <el-autocomplete
            class="inline-input"
            v-model="transportform.terminalStation"
            :fetch-suggestions="querySearch"
            placeholder="请输入终点站"
            :trigger-on-focus="true"
            @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
        <el-row>
          <el-col :span="10">
            <el-form-item label="金额" prop="amount">
              <el-input  v-model="transportform.amount" @change="transpormoneychange"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="票据数量" prop="billsNum">
              <el-input-number v-model="transportform.billsNum" @change="handleChange" :min="0" label="描述文字"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上传附件" prop="fujian">
          <el-upload
            ref="upload"
            class="upload-demo"
            :headers="headers"
            :action= "GLOBAL.UPLOADFILE_URL"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :auto-upload="true"
            :file-list="transportform.fileList"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <span>共{{fujiannum}}张</span>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <span class="lf"><b style="font-size: 18px;">￥{{transportform.amountTotal}}</b></span>
        <el-button @click="transport=false">取消</el-button>
        <el-button type="primary" @click="savetransport">保存</el-button>
      </div>
    </el-dialog>
<!--差旅费查看审批-->
    <el-dialog
      :title="transportdetailtitle"
      :visible.sync="transportdetail"
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
              <el-radio-group v-model="detailform.inPrjFlag"  >
                <el-radio
                  v-for="(dict,index) in yesOrNo"
                  :key="index"
                  :label="dict.dictValue"
                  :value="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>

            </el-form-item>
            <el-form-item label="报销项目">
              <el-input type="textarea" v-model="detailform.project" disabled></el-input>
            </el-form-item>
            <el-form-item label="报销事由">
              <el-input type="textarea" v-model="detailform.reimburseReason" disabled></el-input>
            </el-form-item>
            <!--        交通费-->
            <el-row>
              <el-col>
                <el-form-item label="交通费">
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>

              <el-col :span="24">
                <template v-if="travel">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in transportList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;margin-top: 10px;line-height: 35px;"><b>{{item.trafficType}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p><b>{{item.departureStation}}</b></p>
                            <p>{{item.trafficStartDate}}</p>
                            <p>附件 {{item.fujian}}张</p>
                          </div>
                          <div>
                            <p>至</p>
                          </div>
                          <div>
                            <p><b>{{item.terminalStation}}</b></p>
                            <p>{{item.trafficEndDate}}</p>
                            <p>￥<b>{{item.amountTotal}}</b></p>
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
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="出差补助">
                </el-form-item>
              </el-col>
            </el-row>
            <!--        出差补贴-->
            <el-row>

              <el-col :span="24">
                <template v-if="butie">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in butieList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;line-height: 35px;"><b>{{item.title}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p>人数</p>
                            <p><b>{{item.people}}人</b></p>
                            <p>附件 {{item.fujian}}张</p>
                          </div>
                          <div>
                            <p>天数</p>
                            <p><b>{{item.day}}天</b></p>
                          </div>
                          <div>
                            <p>补贴标准</p>
                            <p>￥<b>{{item.money}}</b></p>
                            <p>￥<b>{{item.allmoney}}</b></p>
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
            </el-row>

            <el-row>
              <el-col>
                <el-form-item label="其他费用">
                </el-form-item>
              </el-col>
            </el-row>
            <!--        其他费用-->
            <el-row>
              <el-col :span="24">
                <template v-if="other">
                  <el-carousel  arrow="always" type="card" :autoplay="false" height="200px">
                    <el-carousel-item v-for="item in otherList">
                      <el-card class="box-card">
                        <div slot="header" class="clearfix">
                          <span  style="font-size: 16px;line-height: 35px;"><b>{{item.title}}</b></span>
                        </div>
                        <div class="text item train">
                          <div>
                            <p>附件 {{item.fujian}}张</p>
                          </div>
                          <div>
                            <p>￥<b>{{item.allmoney}}</b></p>
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
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="附件">
                  <span>共13张</span>
                </el-form-item>
              </el-col>
            </el-row>
            <!--        附件-->
            <el-row>
              <el-col :span="24">
                <template v-if="fujian">
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
            <el-row>
              <el-form-item label="审批备注" style="margin-top: 50px;">
                <el-input type="textarea" v-model="detailform.remark"></el-input>
              </el-form-item>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <span class="lf"><b>￥5,120,000.00</b></span>
        <el-button >取 消</el-button>
        <el-button type="primary" >保存</el-button>
      </span>
    </el-dialog>

<!--新建编辑出差补助-->
    <el-dialog :title="beawaytitle"
               :visible.sync="beaway"
               width="600px" class="abow_dialog">
      <el-form ref="form" :model="beawayform" :rules="beawayrules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
            <el-input v-model="beawayform.name"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="人数" prop="peoplenum">
              <el-input-number v-model="beawayform.peoplenum"  :min="0" @change="peoplenumchange"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="天数" prop="daynum">
              <el-input-number v-model="beawayform.daynum"  :min="0" @change="daynumchange"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="补贴标准" prop="money">
          <el-input v-model="beawayform.money"  @change="moneychange"></el-input>
        </el-form-item>
        <el-form-item label="上传附件" prop="fujian">
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="beawayform.fileList"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <span>共{{beawayform.fujiannum}}张</span>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <span class="lf"><b style="font-size: 18px;">￥{{beawayTotal}}</b></span>
        <el-button @click="canceltransport">取消</el-button>
        <el-button type="primary" @click="savetransport">保存</el-button>
      </div>
    </el-dialog>
<!--新增编辑其他费用-->
    <el-dialog :title="othertitle"
               :visible.sync="otheropen"
               width="600px" class="abow_dialog">
        <el-form  ref="form" :model="otherform" :rules="otherrules" label-width="80px">
          <el-form-item label="项目名称" prop="name">
            <el-input v-model="otherform.name"></el-input>
          </el-form-item>
          <el-form-item label="金额" prop="money">
            <el-input v-model="otherform.money" @change="othermoney"></el-input>
          </el-form-item>
          <el-form-item label="上传附件" prop="fujian">
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="otherform.fileList"
              list-type="picture">
              <span>共{{otherform.fujiannum}}张</span>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <span class="lf"><b style="font-size: 18px;">￥{{otherTotal}}</b></span>
          <el-button @click="canceltransport">取消</el-button>
          <el-button type="primary" @click="savetransport">保存</el-button>
        </div>
    </el-dialog>


  </div>
</template>

<script>
  import { listDept } from "@/api/system/dept";
  import {expensesList,loadAll} from "@/api/business/mywork/expenses"
  import { listReimburse, getReimburse, delReimburse, addReimburse, updateReimburse, exportReimburse,getRemburseDetail } from "@/api/business/mywork/reimburse";
  import { listTrafficFee, getTrafficFee, delTrafficFee, addTrafficFee, updateTrafficFee, exportTrafficFee } from "@/api/business/mywork/trafficfee";
  import { getToken } from '@/utils/auth'
  import {uploadFile,delFile} from '@/api/system/file'
  import {isNotEmpty} from "../../../utils/common";

  export default {
        name: "travel",
        data(){
          return{
            headers: { 'Authorization': 'Bearer ' + getToken() },
            queryParams:{
              deptId:undefined,
              time:[]
            },
            expensesList:[],
            dateRange: [],
            departmentOptions:[],
            statusOptions:[],
            trafficTypeOptions:[],
          // 遮罩层
            loading: true,
            dialogVisible: false,
            transport:false,
            beaway:false,
            otheropen:false,
            value1:'',
            value2:'',
            travel:true,
            butie:true,
            other:true,
            fujian:true,
            transportdetail:false,
            fujiannum:0,
            transportmoney:0,
            transportnum:0,
            beawayTotal:0,
            otherTotal:0,
            yesOrNo: [],
            fileList: [],
            uploadForm: new FormData(),
            form:{
              reimburseId:'',
              deptId: '',
              inPrjFlag: true,
              projectId:'',
              projectName:'',
              reimburseReason: '',
            },
            projectOptions: [
              {
                dictValue: "0",
                dictLabel: "小额贷款项目"
              },
              {
                dictValue: "1",
                dictLabel: "网信办项目"
              }
            ],
            delfileIds:[],
            transportform:{
              reimburseId: '',
              trafficType: '',
              trafficDate: [],
              departureStation:'',
              terminalStation:'',
              amount:0,
              billsNum:0,
              amountTotal:0,
              fileIds:[],
              fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
            },
            beawayform:{
              name:"",
              peoplenum:"",
              daynum:"",
              money:"",
              fujiannum:"",
              fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
            },
            otherform:{
              name:'',
              money:'',
              fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
            },
            detailform:{
              inPrjFlag:true,
              project:'马克尔\n' +
                      '河北省小贷管理系统开发项目\n' +
                      'XCV23456',
              reimburseReason:'唐山项目出差费用',
              remark:''
            },
            title:"",
            transporttitle:"",
            beawaytitle:"",
            othertitle:"",
            transportdetailtitle:"",
            transportList:[
              {trafficType:'火车票',departureStation:'石家庄',terminalStation:'北京',trafficStartDate:'2020/05/03 17:30',trafficEndDate:'2020/05/04 17:20',fujian:1,amountTotal:'199.05'},
              {trafficType:'火车票',departureStation:'郑州',terminalStation:'上海',trafficStartDate:'2020/05/03 17:30',trafficEndDate:'2020/05/04 17:20',fujian:1,amountTotal:'199.05'},
              // {title:'火车票',begin:'南昌',end:'上海',begintime:'2020/05/03 17:30',endtime:'2020/05/04 17:20',fujian:1,money:'199.05'},
            ],
            butieList:[
              {title:'出差补贴',people:2,day:7,money:'80.00',fujian:1,allmoney:'199.05'},
              {title:'出差补贴',people:5,day:9,money:'30.00',fujian:1,allmoney:'500.05'},
            ],

            // 其他费用
            otherList:[
              {title:'住宿停车费',fujian:'1',allmoney:'199.05'},
              {title:'住宿停车费',fujian:'2',allmoney:'200.05'},
            ],
            // 附件

          fujianList:[
            {src:'@/assets/image/train_ticket.png'}
          ],
            //部门label
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
            transportrules:{
              trafficType: [
                { required: true, message: "交通类型不能为空", trigger: "blur" }
              ],
              trafficDate:[
                { required: true, message: "时间不能为空", trigger: "blur" }
              ],
              departureStation:[
                { required: true, message: "起始位置", trigger: "blur" }
              ],
              terminalStation:[
                { required: true, message: "结束位置", trigger: "blur" }
              ],
              amount:[
                { required: true, message: "金额", trigger: "blur" }
              ],
              billsNum:[
                { required: true, message: "票据数量", trigger: "blur" }
              ],
              fujian:[
                { required: true, message: "附件", trigger: "blur" }
              ]
            },
            beawayrules:{
              name:[
                { required: true, message: "项目名称", trigger: "blur" }
              ],
              peoplenum:[
                { required: true, message: "人数", trigger: "blur" }
              ],
              daynum:[
                { required: true, message: "天数", trigger: "blur" }
              ],
              money:[
                { required: true, message: "补贴标准", trigger: "blur" }
              ],
            },
            otherrules:{
              name:[
                { required: true, message: "项目名称", trigger: "blur" }
              ],
              money:[
                { required: true, message: "金额", trigger: "blur" }
              ],
              fujian:[
                { required: true, message: "附件", trigger: "blur" }
              ]
            }
          }
        },
        created() {
          this.getList();
          // 状态
          this.getDicts("sys_check_status").then(response => {
            this.statusOptions = response.data;
          });
          //系统是否
          this.getDicts("sys_yes_no").then(response => {
            response.data.forEach( (val) => this.yesOrNo.push({'dictValue': eval(val.dictValue),'dictLabel': val.dictLabel}))
          });
          //获取部门列表
          this.getDeptList({parentId:'100'}).then(response => {
            response.data.forEach((val)=> this.departmentOptions.push({'dictValue': val.deptId,'dictLabel': val.deptName}))
          });
          // 交通类型
          this.getDicts("transport_type").then(response => {
            this.trafficTypeOptions = response.data;
          });


        },
        methods:{
            getList(){
              this.loading = true;
              listReimburse(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
                this.expensesList = response.rows;
                this.total = response.total;
                this.loading = false;
              });
            },
            // 新建
            handleAdd(){
              this.dialogVisible=true;
            },

            reimburseSave(){
              this.projectOptions.forEach(e=>{
                if(Object.is(e.dictValue,this.form.projectId)){
                  this.form.projectName = e.dictLabel;
                }
              })
              addReimburse(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("保存成功");
                } else {
                  this.msgError(response.msg);
                }
              });

            },

          cancelFun(){

          },

          savetransport(){
            if(this.form.reimburseId){
              this.transportform.fileIds = JSON.stringify(this.transportform.fileIds);
              if(isNotEmpty(this.transportform.fileIds)){
                let fileIds = this.transportform.fileIds;
                this.transportform.fileIds = fileIds.replace("[","").replace("]","")
              }

              this.transportform.reimburseId = this.form.reimburseId;
              this.transportform.trafficDate = JSON.stringify(this.transportform.trafficDate);

              addTrafficFee(this.transportform).then(response => {
                if (response .code === 200) {
                  this.msgSuccess("保存成功");
                  this.delFiles()

                } else {
                  this.msgError(response.msg);
                }
              });
            }else{
              this.msgWarning('请先保存报销基础信息！');
            }

          },

          delFiles(){
            this.delfileIds.forEach(e=>{
              delFile(e)
            })
          },

          // 删除
          handleDelete(){},
          // 报送
          handleReport(){},
          // 导出
          handleExport(){},
          // 编辑
          handleUpdate(row){
            this.form = row;
            this.dialogVisible=true;

            getRemburseDetail(row.reimburseId).then(response => {
              this.transportList = response.data.busiReimTrafficFeeList;
              // this.butieList = response.data.busiReimTravelSubsidyList
              // this.otherList = response.data.busiReimOtherFeeList
            });
          },
          // 行点击
          handleRowClick(row){
            this.transportdetail=true;
            this.transportdetailtitle='查看'
          },
          handleSelectionChange(){},
          // 搜索
          handleQuery(){},
          // 重置
          resetQuery(){},
          // 删除文件
          handleRemove(file, fileList) {
            this.delfileIds.push(file.id)
            this.fujiannum -= 1;
            let fileIndex = this.transportform.fileIds.indexOf(file.id);
            this.transportform.fileIds.splice(fileIndex, 1);
          },
          handleSuccess(response, file, fileList){
            this.fujiannum += 1;
            this.transportform.fileIds.push(response.fileId)
          },
          changeHandler(){},
          // 删除车票
          deleteticket(){

          },
          addtransport(){
              this.transport=true
              this.transporttitle='新增'
          },

          editTransport(data){
            let {trafficStartDate,trafficEndDate,fileIds } = data;
            this.transportform = data;
            this.transportform.trafficDate = new Array();
            this.transportform.trafficDate.push(trafficStartDate);
            this.transportform.trafficDate.push(trafficEndDate);

            if(isNotEmpty(fileIds)){
              if(fileIds instanceof Array){
                fileIds = JSON.stringify(fileIds).replace("[","").replace("]","")
              }

              let fileIdStr = fileIds.split(',');
              this.transportform.fileIds =  fileIdStr.map(Number);
              this.fujiannum = this.transportform.fileIds.length;
            }

            let fileList = this.transportform.fileList;
            this.transportform.fileList.forEach(e=>{
              e.name = e.fileNameReal
              e.url =process.env.VUE_APP_BASE_API + e.previewPath;
            })
            this.transport=true
          },

          // 搜索建议
          querySearch(queryString, cb) {
            var restaurants = loadAll();
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
          },
          createFilter(queryString) {
            return (restaurant) => {
              return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
          },

          handleSelect(item) {
            console.log(item);
          },
          handleChange(value){
            this.transportnum=value;
            this.transportform.amountTotal=this.transportmoney*this.transportnum;
          },
          transpormoneychange(value){
            this.transportmoney=value;
            this.transportform.amountTotal=this.transportmoney*this.transportnum;
          },
          handlePreview(file) {
            console.log(file);
          },
          canceltransport(){},
          goaway(){
              this.beaway=true;
              this.beawaytitle='新增'
          },
          peoplenumchange(value){
            this.beawayform.peoplenum=value
            this.beawayTotal=this.beawayform.peoplenum*this.beawayform.daynum*this.beawayform.money
          },
          daynumchange(value){
            this.beawayform.daynum=value
            this.beawayTotal=this.beawayform.peoplenum*this.beawayform.daynum*this.beawayform.money
          },
          moneychange(value){
            this.beawayform.money=value
            this.beawayTotal=this.beawayform.peoplenum*this.beawayform.daynum*this.beawayform.money
          },
          othermoney(value){
              this.otherform.money=value
            this.otherTotal=this.otherform.money

          },
          otherclick(){
              this.otheropen=true;
              this.othertitle='新增'
          }
        }
    }
</script>

<style>
  .train{
    display: flex;
    justify-content:space-around
  }
  .train p{
    text-align: center;
  }

  .travel_container .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .travel_container .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .travel_container .el-carousel__indicators--horizontal{
    display: none;
  }
  .lf{
    float:left;
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
</style>
