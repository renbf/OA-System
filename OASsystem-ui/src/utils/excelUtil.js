import FileSaver from 'file-saver';
import XLSX from 'xlsx';


/**
 * 根据表格ID进行导出
 * @returns {any}
 */
export function exportExcelByTableId(tableId,excelTitle) {
  //转换成excel时，使用原始的格式 { raw: true };
  var wb = XLSX.utils.table_to_book(document.querySelector('#'+tableId),{ raw: true })
  var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
  try {
    FileSaver.saveAs(
      new Blob([wbout], { type: 'application/octet-stream' }),
      excelTitle+'.xlsx')
  } catch (e) {
    if (typeof console !== 'undefined')
      console.log(e, wbout)
  }
  return wbout
}


/**
 * 通过数组进行导出
 * coluname = ['姓名','部门','职务名称']
 * columns= ["name","department","titles"]
 * datas: [
             {
                name: "张三",
                department: "软件部",
                titles: "经理",
              },
             {
            name: "张三",
            department: "软件部",
            titles: "经理",
          },]
 */
export function exportExcelByArray(datas,columns,coluname,excelTitle) {
  datas =  datas.map(v => columns.map(j => v[j]))
  datas.unshift(coluname);

  const ws= XLSX.utils.aoa_to_sheet(datas);
  /* generate workbook and add the worksheet */
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
  /* save to file */
  XLSX.writeFile(wb, excelTitle+'.xlsx');
}


/**
 * 通过json格式进行导出
 * multipleSelection: [
           {
              name: "张三",
              department: "软件部",
              titles: "经理",
            },
           {
            name: "张三",
            department: "软件部",
            titles: "经理",
          },]
 */
export function exportExcelByJson(datas,excelTitle) {

  var wopts = {bookType: 'xlsx', bookSST: true, type: 'binary'};
  var workBook = {SheetNames: ['Sheet1'], Sheets: {}, Props: {}};
  //1、XLSX.utils.json_to_sheet(data) 接收一个对象数组并返回一个基于对象关键字自动生成的“标题”的工作表，默认的列顺序由使用Object.keys的字段的第一次出现确定
  //2、将数据放入对象workBook的Sheets中等待输出
  workBook.Sheets['Sheet1'] = XLSX.utils.json_to_sheet(datas);

  //3、XLSX.write() 开始编写Excel表格
  //4、changeData() 将数据处理成需要输出的格式
  FileSaver.saveAs(new Blob([changeData(XLSX.write(workBook, wopts))], {type: 'application/octet-stream'}), excelTitle+'.xlsx')
}

function changeData(s) {
  //如果存在ArrayBuffer对象(es6) 最好采用该对象
  if (typeof ArrayBuffer !== 'undefined') {
    //1、创建一个字节长度为s.length的内存区域
    var buf = new ArrayBuffer(s.length);
    //2、创建一个指向buf的Unit8视图，开始于字节0，直到缓冲区的末尾
    var view = new Uint8Array(buf);
    //3、返回指定位置的字符的Unicode编码
    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
  } else {
    var buf = new Array(s.length);
    for (var i = 0; i != s.length; ++i) buf[i] = s.charCodeAt(i) & 0xFF;
    return buf;
  }
}
