package com.xhkj.project.system.controller;

import com.xhkj.common.constant.Constants;
import com.xhkj.common.utils.StringUtils;
import com.xhkj.common.utils.file.FileUploadSonUtils;
import com.xhkj.framework.config.MainConfig;
import com.xhkj.framework.web.controller.BaseController;
import com.xhkj.framework.web.domain.AjaxResult;
import com.xhkj.framework.web.page.TableDataInfo;
import com.xhkj.project.system.domain.Attachment;
import com.xhkj.project.system.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author xxw
 * @date 2020/03/23/13:45
 * @Description 文件上传下载
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("system/attachment")
public class FildUploadDownloadController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FildUploadDownloadController.class);
    @Autowired
    private IAttachmentService iAttachmentService;

    /**
     *  文件列表
     * @param attachment
     * @return TableDataInfo
     * @throws IOException
     */
    @RequestMapping("/filesLists")
    @ResponseBody
    public TableDataInfo filesLists(Attachment attachment) {
        startPage();
        List<Attachment> list = iAttachmentService.selectAttachmentList(attachment);
        return getDataTable(list);
    }

    /**
     * 文件上传
     * @param file
     * @return  AjaxResult
     * @throws IOException
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public AjaxResult fileUpload(@RequestParam("data") MultipartFile[] file
    ) throws IOException {
           for(MultipartFile f:file){
               Attachment attachment = new Attachment();
               ///文件路径
               String fileUrl = FileUploadSonUtils.upload(f);
               //
               String fileName =f.getOriginalFilename();
               //真是名字
               String name = f.getName();
               long size = f.getSize();   //文件的大小
               attachment.setCreateTime(new Date());
//                attachment.setBusinessType(businessType);
               attachment.setFileNameReal(fileName);
//                   attachment.setFileNameShow(name);
               attachment.setFilePath(fileUrl);
               attachment.setFileSize(size);
               iAttachmentService.insertAttachment(attachment);
           }


        return AjaxResult.success();
    }
    @RequestMapping("/files")
    @ResponseBody
    public AjaxResult files(MultipartFile file
    ){
        AjaxResult ajaxResult = new AjaxResult();
          try {
              Attachment attachment = new Attachment();
              ///文件路径
              String fileUrl = FileUploadSonUtils.upload(file);

              int dirLastIndex = MainConfig.getProfile().length() + 1;
              String currentDir = StringUtils.substring(fileUrl, dirLastIndex);
              String previewPath = Constants.RESOURCE_PREFIX + "/" + currentDir;

              //
              String fileName =file.getOriginalFilename();
              //真是名字
              String name = file.getName();
              long size = file.getSize();   //文件的大小
              attachment.setCreateTime(new Date());
              attachment.setFileNameReal(fileName);
              attachment.setFilePath(fileUrl);
              attachment.setFileSize(size);
              attachment.setPreviewPath(previewPath);
              iAttachmentService.insertAttachment(attachment);

              ajaxResult.put("fileId",attachment.getId());
              ajaxResult.put("code",200);
              ajaxResult.put("msg","操作成功");
          }catch (Exception e){
              e.printStackTrace();
              ajaxResult.put("code","-1");
              ajaxResult.put("msg","数据异常");
          }
          return ajaxResult;
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @PostMapping("/deleteFile")
    @ResponseBody
    public AjaxResult deleteFile(String id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Attachment attachment = iAttachmentService.selectAttachmentById(Long.parseLong(id));
            //删除数据文件
            String path = attachment.getFilePath();
            File file = new File(path);
            try{
                boolean delete = file.delete();
                if(delete){
                    iAttachmentService.deleteAttachmentByIds(id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            ajaxResult.put("path",path);
            ajaxResult.put("code",200);
            ajaxResult.put("msg","刪除成功");
        }catch (Exception e){
            log.error("删除异常",e);
            ajaxResult.put("code",-1);
            ajaxResult.put("msg","刪除异常");
        }
        return ajaxResult;
    }

    /**
     * 删除文件以后执行该方法去删除磁盘上的数据
     * @param path
     * @return
     */
    @PostMapping("/deleteDiskFile")
    @ResponseBody
   public AjaxResult deleteDiskFile(String path){
       File file = new File(path);
       try{
           boolean delete = file.delete();

       }catch (Exception e){
           e.printStackTrace();
       }
      return  AjaxResult.success();
   }
    /**
     * 在线预览 pdf格式
     * @param request
     * @param response
     */
    @GetMapping("/onlineBrowse")
    public void onlineBrowse(HttpServletRequest request,HttpServletResponse response)  {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try{
            String id=  request.getParameter("id");
            Attachment attachment = iAttachmentService.selectAttachmentById(Long.valueOf(id));
            if(Objects.nonNull(attachment)){
                String fileName = attachment.getFileNameReal(); //文件名字
                File file = new File(attachment.getFilePath());  //文件路径
                if(file.exists()){
                    fileInputStream = new FileInputStream(file);
                    OutputStream os = response.getOutputStream();
//                    response.setContentType("multipart/form-data");
                    response.setHeader("content-Type", "application/octet-stream");
                    response.addHeader("Content-Disposition","inline;filename="+new String(fileName.getBytes(),"iso-8859-1")); //设置文件名

//                     response.setHeader();
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    byte[] buffer = new byte[fileInputStream.available()];
                    int i = bufferedInputStream.read(buffer);
                    while (i!=-1){
                        os.write(buffer,0,i);
                        i=bufferedInputStream.read();
                    }
                    os.flush();
                    response.flushBuffer();
                }
            }
        }catch (Exception e){log.error("异常",e);}
        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
