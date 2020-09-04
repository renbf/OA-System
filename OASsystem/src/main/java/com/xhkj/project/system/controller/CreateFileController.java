package com.xhkj.project.system.controller;

import com.xhkj.common.utils.WordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther:  xxw
 * @Date: 2020/03/31/10:41
 * @Description:
 */
@Controller
@RequestMapping("/create")
public class CreateFileController {


  @RequestMapping("/file")
  @ResponseBody
  public void createFile(HttpServletRequest request, HttpServletResponse response){
      try {
          Map<String, Object> resultMap = new HashMap<String, Object>();
              WordUtils.exportMillCertificateWord(request, response, resultMap, "河北省住房和城乡建设厅", "create_file.ftl");
      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
}
