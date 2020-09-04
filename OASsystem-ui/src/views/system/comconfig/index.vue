<template>
  <div  class="app-container">
    <el-row :gutter="300">
      <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="100px">
        <el-col :span="24">
          <el-form-item label-width="120px" label="上午上班时间" prop="amWorkDate">
            <el-time-picker v-model="form.amWorkDate" is-range format="HH:mm" value-format="HH:mm"
                            :style="{width: '30%'}" start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                            clearable @change="dateChange"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label-width="120px" label="下午上班时间" prop="pmWorkDate">
            <el-time-picker v-model="form.pmWorkDate" is-range format="HH:mm" value-format="HH:mm"
                            :style="{width: '30%'}" start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                            clearable @change="dateChange"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label-width="120px" label="总工时" prop="workHourTotal">
            <input v-model="form.workHourTotal" clearable
                      :style="{width: '30%'}" readonly id="el_jia3"></input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label-width="120px" label="加班工时单位" prop="workHourUnit">
            <el-radio-group v-model="form.workHourUnit" size="medium">
              <el-radio v-for="(item, index) in workHourUnitOptions"
                        :key="index"
                        :label="item.value"
                        :disabled="item.disabled"
              >{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <div style="margin-top:10px" v-for="(item, index) in form.overPeriod" :key="index">
          <el-col :span="24">
            <el-form-item label-width="120px" label="晚上加班时间段" prop="overPeriod">
              <el-time-picker v-model="item[0]" is-range format="HH:mm" value-format="HH:mm"
                              :style="{width: '30%'}" start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                              clearable></el-time-picker>
              <el-input v-model="form.overDay[index]" placeholder="对应天数" clearable :style="{width: '10%'}" v-show="overDayShow"/>
              <el-button type="primary" icon="el-icon-plus" circle @click="addDomain" v-show="index == 0 ? true : datePickerShow"></el-button>
              <el-button type="danger" icon="el-icon-delete" circle @click.prevent="removeDomain(index, item)" v-show="index == 0 ? false : datePickerShow"></el-button>
            </el-form-item>
          </el-col>
        </div>

        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
  import { listComConfig, addComConfigList } from "@/api/system/comconfig";
  import { toHourDifference } from '@/utils/common'


  export default {
    components: {},
    props: [],
    data() {
      return {
        form: {
          amWorkDate: ["08:30", "12:00"],
          pmWorkDate: ["13:30", "17:30"],
          workHourTotal: undefined,
          workHourUnit: '时',
          overPeriod: [[]],
          overDay: [],
        },
        overDayShow :false,
        datePickerShow: false,
        rules: {
          amWorkDate: [{
            required: true,
            type: 'array',
            message: '请选择上午上班时间',
            trigger: 'change'
          }],
          pmWorkDate: [{
            required: true,
            type: 'array',
            message: '请选择下午上班时间',
            trigger: 'change'
          }],
          workHourTotal: [{
            required: true,
            message: '请输入总工时',
            trigger: 'blur'
          }],
        },
        workHourUnitOptions: [{
          "label": "时",
          "value": "时"
        }, {
          "label": "天",
          "value": "天"
        }],
      }
    },
    computed: {},
    watch: {
      'form.workHourUnit':{
        deep:true,
        handler(val){
          if(val=='天'){
            this.overDayShow=true
          }else{
            this.overDayShow=false
          }
        }
      }
    },
    created() {
      listComConfig({}).then(res => {
        res.rows.forEach(item => {
          if(Object.is(item.comConfigKey,'amWorkDate')){
            this.form.amWorkDate = eval(item.comConfigValue)
          }
          if(Object.is(item.comConfigKey,'pmWorkDate')){
            this.form.pmWorkDate = eval(item.comConfigValue)
          }
          if(Object.is(item.comConfigKey,'workHourTotal')){
            this.form.workHourTotal = item.comConfigValue
          }
          if(Object.is(item.comConfigKey,'workHourUnit')){
            this.form.workHourUnit = item.comConfigValue
          }
          if(Object.is(item.comConfigKey,'overPeriod')){
            let eval1 = eval(item.comConfigValue);
            this.form.overPeriod = eval(item.comConfigValue)
          }
          if(Object.is(item.comConfigKey,'overDay')){
            let eval2 = eval(item.comConfigValue);
            this.form.overDay = eval(item.comConfigValue)
          }

        })
      })
      //计算时差
      this.dateChange();
    },
    mounted() {},
    methods: {
      submitForm() {

        this.$refs['form'].validate(valid => {
          if (!valid) return
          let amWorkDateStr = JSON.stringify(this.form.amWorkDate);
          let pmWorkDateStr = JSON.stringify(this.form.pmWorkDate);
          let overPeriodStr = JSON.stringify(this.form.overPeriod);
          let overDayStr = JSON.stringify(this.form.overDay);

          const paramArray = [];
          //组装数据
          paramArray.push({comConfigName: '上午上班时间',comConfigKey: 'amWorkDate',comConfigValue: amWorkDateStr})
          paramArray.push({comConfigName: '下午上班时间',comConfigKey: 'pmWorkDate',comConfigValue: pmWorkDateStr})
          paramArray.push({comConfigName: '总工时',comConfigKey: 'workHourTotal',comConfigValue: this.form.workHourTotal})
          paramArray.push({comConfigName: '加班工时单位',comConfigKey: 'workHourUnit',comConfigValue: this.form.workHourUnit})
          paramArray.push({comConfigName: '晚上加班时间段',comConfigKey: 'overPeriod',comConfigValue: overPeriodStr})
          if(Object.is(this.form.workHourUnit,'天')){
            paramArray.push({comConfigName: '晚上加班时间段工时',comConfigKey: 'overDay',comConfigValue: overDayStr})
          }

          addComConfigList(paramArray).then(response => {
            if (response.code === 200) {
              this.msgSuccess("保存成功！");
            } else {
              this.msgError(response.msg);
            }
          });

        })
      },

      dateChange(){
        let amhours = toHourDifference(this.form.amWorkDate[0],this.form.amWorkDate[1]);
        let pmhours = toHourDifference(this.form.pmWorkDate[0],this.form.pmWorkDate[1]);
        this.form.workHourTotal = amhours+pmhours
      },

      addDomain(index) {
        let arr = [];
        this.form.overPeriod.push(arr);
        this.datePickerShow = true;
      },
      removeDomain(index, item) {
        if(this.form.overDay){
          this.form.overDay.splice(index, 1);
        }

        let add = this.form.overPeriod;
        if (add.length > 1) {
          let newArr = [];
          let time = 0;
          add.forEach((value, index) => {
            if (time == 0) {
              newArr.push(value);
            } else {
              value = value.filter(item => item !== undefined);
              newArr.push(value);
            }
            time += 1;
          });
          this.form.overPeriod = newArr;
          if (index !== -1) {
            this.form.overPeriod.splice(index, 1);
          }
        } else {
          if (index !== -1) {
            this.form.overPeriod.splice(index, 1);
          }
        }
      },

    }
  }

</script>
<style>
  #el_jia3 {
    padding: 0;
    height: 20px;
    border: none;
    outline: none;
  }
</style>
