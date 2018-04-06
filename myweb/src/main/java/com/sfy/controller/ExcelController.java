package com.sfy.controller;

import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shifengyuan.
 * Date: 2018/4/4
 * Time: 14:54
 */

@Controller
@RequestMapping("/excel")
public class ExcelController {

    private static Workbook work;

    @RequestMapping(value = "/dealExcel", method = RequestMethod.POST, produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public void uploadAndGenerateExcel(HttpServletRequest request,
                                       HttpServletResponse response,
                                       MultipartFile file) {

        try {
            InputStream is = file.getInputStream();//转换成输入流

            List<List<Object>> list = new ArrayList<List<Object>>();
            work = this.getWorkbook(is, file.getOriginalFilename());//得到excel

            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            // 遍历Excel中所有的sheet
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                // 遍历当前sheet中的所有行
                for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {

                    row = sheet.getRow(j);
                    //getFirstRowNum/getFirstCellNum-->从0开始取
                    //去除第一行row.getFirstCellNum() == 0 == j
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }

                    // 遍历所有的列
                    List<Object> li = new ArrayList<Object>();
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(this.getCellValue(cell));
                    }
                    list.add(li);
                }
            }

            System.out.println(new Gson().toJson(list));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/download")
    public void downloadExcel(HttpServletResponse response){
        OutputStream outputStream = null;

        try {
            String fileName = "汇总表";
            response.reset();
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xlsx", "UTF-8"))));

            outputStream = response.getOutputStream();
            work.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {

        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (".xlsx".equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    private Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字

        System.out.println("cell=" + cell);

        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

}
