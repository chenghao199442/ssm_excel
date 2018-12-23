package com.chenghao.poi.controller;

import com.chenghao.poi.domain.Emp;
import com.chenghao.poi.service.ExcelService;
import com.chenghao.poi.util.GsonUtils;
import com.chenghao.poi.util.OutputExcel;
import com.chenghao.poi.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    private static final Logger log = Logger.getLogger(ExcelController.class);// 日志文件


    @RequestMapping("/distribution.do")
    @ResponseBody
    public void distribution(String name) {
        List<Emp> tenDataEmp = excelService.getTenDataEmp();
        if (tenDataEmp != null && tenDataEmp.size() > 0) {
            excelService.updateNameByEmpsBatch(tenDataEmp, name);
        }
    }


    @RequestMapping("/getAll.do")
    @ResponseBody
    public String getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html,charset=utf-8");
        List<Emp> list = excelService.getAll();
        ResponseUtil.write(response, GsonUtils.object2json(list));
        return null;
    }

    @RequestMapping(value = "/inputExcel.do")
    @ResponseBody
    public String InputExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String flag = "02";// 上传标志
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();// 原文件名字
                log.info("文件名：" + originalFilename);
                InputStream is = file.getInputStream();// 获取输入流
                flag = excelService.inputExcel(is, originalFilename);
            } catch (Exception e) {
                flag = "03";// 上传出错
                e.printStackTrace();
            }
        }
        return flag;
    }

    @RequestMapping(value = "/outputExcel.do", produces = "application/form-data; charset=utf-8")
    @ResponseBody
    public String OutputExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html,charset=utf-8");

        List<Emp> list = excelService.outputExcel();

        String message = OutputExcel.outExcel(request, response, list);
        if (message.equals("fail")) {
            ServletOutputStream out = response.getOutputStream();
            message = "导出失败，请重试";
            String s = "<!DOCTYPE HTML><html><head><script>alert('" + message + "');</script></head><body></body></html>";
            out.print(s);
        }
        return null;
    }

}
