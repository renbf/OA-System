// 数据
import request from "@/utils/request";

export  function taskList(){
  return{
    code: 200,
    total: 4,
    data: [
      {
        Daily_title:'每日客服问题回答',
        Daily_time:'8:30-17:30',
        Daily_people:'马克尔 / 爱莲娜 / 凡尔纳 / 科斯特 / 李明浩 / 斯坦',
        Daily_newtime:"2020-05-12 05:30:00",
        status: [{ dictValue: 1, dictLabel: "已报送" }]
      },
      {
        Daily_title:'每日问题统计',
        Daily_time:'8:30-17:30',
        Daily_people:'马克尔 / 爱莲娜 ',
        Daily_newtime:"2020-05-13",
        status: [{ dictValue: 2, dictLabel: "未报送" }]
      },
      {
        Daily_title:'每日数据维护',
        Daily_time:'8:30-17:30',
        Daily_people:'马克尔 ',
        Daily_newtime:"2020-08-13",
        status: [{ dictValue: 2, dictLabel: "未报送" }]
      },
    ]
  }

}

// 编辑
export function editform(){
  return {
    code: 200,
    total: 4,
    data: [{
      form: {
        title: "每日客服回答的问题",
        department: [
          {name: '技术部'},
          {name: '软件部'}
        ],
        people: [
          {name: '张三'},
          {name: '李四'}
        ],
        tasktime: [new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)],
        taskmain: "1.修改法院项目时间轴图片\n" +
          "2.修改小贷系统2.9PPT\n" +
          "3.与公安部书籍组沟通 11张用图修改问题\n" +
          "4.与两家小贷公司沟通报表错误问题",
        workmain: "1、2、3",
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        timestamp: [{
          title:"迈克尔 06/02 17:45",
          timecontent:[
            {content:"1.客服解决问题4件"},
            {content:"2.客服解决问题4件"},
          ]
        }]
      },

    }]
  }
}
