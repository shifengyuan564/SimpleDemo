package com.sfy.controller;

import com.google.gson.Gson;
import com.sfy.domain.EmployeeBasic;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
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
import java.io.FileOutputStream;
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

    private static Workbook wb = new SXSSFWorkbook(1000);
    private FormulaEvaluator formulaEvaluator = null;
    private static Map<String, Integer> cellRules;
    private int holidayMonth;   // 发"节日补助"的月份

    static{
        cellRules = new HashMap<>();
        cellRules.put("人力资源系统编码", 0);
        cellRules.put("姓名", 1);
        cellRules.put("表一应纳税额", 25);
        cellRules.put("表二应纳税额", 25);
        cellRules.put("表三应纳税额", 25);
        cellRules.put("表四收入合计", 8);
    }

    @RequestMapping(value = "/dealExcel", method = RequestMethod.POST, produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public void uploadAndGenerateExcel(HttpServletRequest request,
                                       HttpServletResponse response,
                                       MultipartFile file) {
        List<EmployeeBasic> list = new ArrayList<>();

        try {
            InputStream is = file.getInputStream();//转换成输入流
            Workbook work = this.getWorkbook(is, file.getOriginalFilename());//得到excel

            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }
            Sheet sheet1 = work.getSheet("基础数据表一");
            Sheet sheet2 = work.getSheet("基础数据表二");
            Sheet sheet3 = work.getSheet("基础数据表三");
            Sheet sheet4 = work.getSheet("基础数据表四");

            // 获取列
            int col_stuffNo = cellRules.get("人力资源系统编码");
            int col_name = cellRules.get("姓名");
            int col_m1 = cellRules.get("表一应纳税额");
            int col_m2 = cellRules.get("表二应纳税额");
            int col_m3 = cellRules.get("表三应纳税额");
            int col_year = cellRules.get("表四收入合计");

            // 计算发过节费的月份 (可能是1月，或者2月)
            String holidayCell = (String) this.getCellValue(sheet1.getRow(2).getCell(12));
            if (StringUtils.isEmpty(holidayCell)) {
                holidayMonth = 2;
            } else {
                holidayMonth = 1;
            }


            for (int i = 2; i < sheet1.getLastRowNum(); i++) { // 以基础表一为基准，从第3行 开始遍历

                EmployeeBasic employeeBasic = new EmployeeBasic();
                String stuffNo = (String) this.getCellValue(sheet1.getRow(i).getCell(col_stuffNo));
                String name = (String) this.getCellValue(sheet1.getRow(i).getCell(col_name));

                double m1 = (double) this.getCellValueFormula(sheet1.getRow(i).getCell(col_m1), formulaEvaluator);
                double m2 = (double) this.getCellValueFormula(sheet2.getRow(i).getCell(col_m2), formulaEvaluator);
                double m3 = (double) this.getCellValueFormula(sheet3.getRow(i).getCell(col_m3), formulaEvaluator);
                double yearAll = (double) this.getCellValueFormula(sheet4.getRow(i).getCell(col_year), formulaEvaluator);
                double oncePayTax = this.calcOncePayTax(yearAll, m1); // 计算表四的一次性纳税

                employeeBasic.setStuffNo(stuffNo);
                employeeBasic.setName(name);
                employeeBasic.setM1(m1);
                employeeBasic.setM2(m2);
                employeeBasic.setM3(m3);
                employeeBasic.setYearAll(yearAll);
                employeeBasic.setOncePayTax(roundDouble(oncePayTax));
                employeeBasic = this.calcTaxFinal(employeeBasic);  // 六选一

                list.add(employeeBasic);
                System.out.println(new Gson().toJson(employeeBasic));
            }
            generateOutputExcel(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generateOutputExcel(List<EmployeeBasic> list) throws IOException {
        Sheet sh = wb.createSheet("汇总表");

        //============================
        //        设置表头 Start
        //============================
        // 表头样式
        XSSFCellStyle regionStyle = (XSSFCellStyle) wb.createCellStyle();
        regionStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        regionStyle.setAlignment(CellStyle.ALIGN_CENTER);
        regionStyle.setWrapText(true);
        regionStyle.setBorderBottom(CellStyle.BORDER_THIN);
        regionStyle.setBorderTop(CellStyle.BORDER_THIN);
        regionStyle.setBorderLeft(CellStyle.BORDER_THIN);
        regionStyle.setBorderRight(CellStyle.BORDER_THIN);

        // 最终发放金额下的子项目
        Row row = sh.createRow(1);
        Cell cell = row.createCell(3);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("一月份年终奖发放额");

        cell = row.createCell(4);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("纳税额");

        cell = row.createCell(5);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("一月份");

        cell = row.createCell(6);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("一月纳税额");

        cell = row.createCell(7);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("二月份");

        cell = row.createCell(8);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("二月纳税额");

        cell = row.createCell(9);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("三月份");

        cell = row.createCell(10);
        cell.setCellStyle(regionStyle);
        cell.setCellValue("三月纳税额");


        // 人力资源系统编码
        Row regionRow0 = sh.createRow(0);
        Cell regionCell0 = regionRow0.createCell(0);
        regionCell0.setCellValue("人力资源系统编码");
        regionCell0.setCellStyle(regionStyle);
        CellRangeAddress region0 = new CellRangeAddress(0, 1, 0, 0);    // 合并
        sh.addMergedRegion(region0);
        RegionUtil.setBorderBottom(1, region0, sh, wb);
        RegionUtil.setBorderLeft(1, region0, sh, wb);
        RegionUtil.setBorderRight(1, region0, sh, wb);
        RegionUtil.setBorderTop(1, region0, sh, wb);

        // 姓名
        Cell regionCell1 = regionRow0.createCell(1);
        regionCell1.setCellValue("姓名");
        regionCell1.setCellStyle(regionStyle);
        CellRangeAddress region1 = new CellRangeAddress(0, 1, 1, 1);    // 合并
        sh.addMergedRegion(region1);
        RegionUtil.setBorderBottom(1, region1, sh, wb);
        RegionUtil.setBorderLeft(1, region1, sh, wb);
        RegionUtil.setBorderRight(1, region1, sh, wb);
        RegionUtil.setBorderTop(1, region1, sh, wb);

        // 纳税总额
        Cell regionCell2 = regionRow0.createCell(2);
        regionCell2.setCellValue("纳税总额");
        regionCell2.setCellStyle(regionStyle);
        CellRangeAddress region2 = new CellRangeAddress(0, 1, 2, 2);    // 合并
        sh.addMergedRegion(region2);
        RegionUtil.setBorderBottom(1, region2, sh, wb);
        RegionUtil.setBorderLeft(1, region2, sh, wb);
        RegionUtil.setBorderRight(1, region2, sh, wb);
        RegionUtil.setBorderTop(1, region2, sh, wb);

        // 最终发放金额
        Cell regionCell3 = regionRow0.createCell(3);
        regionCell3.setCellValue("最终发放金额");
        regionCell3.setCellStyle(regionStyle);
        CellRangeAddress region3 = new CellRangeAddress(0, 0, 3, 10);    // 合并
        sh.addMergedRegion(region3);
        RegionUtil.setBorderBottom(1, region3, sh, wb);
        RegionUtil.setBorderLeft(1, region3, sh, wb);
        RegionUtil.setBorderRight(1, region3, sh, wb);
        RegionUtil.setBorderTop(1, region3, sh, wb);
        //============================
        //        设置表头 end
        //============================


        //============================
        //        循环输出list start
        //============================
        for (int i = 0; i < list.size(); i++) {
            EmployeeBasic eb = list.get(i);
            row = sh.createRow(i + 2);
            row.createCell(0).setCellValue(eb.getStuffNo());
            row.createCell(1).setCellValue(eb.getName());
            row.createCell(2).setCellValue(Math.round(eb.getTaxFinal()));
            row.createCell(3).setCellValue(Math.round(eb.getM1BonusPay()));
            row.createCell(4).setCellValue(Math.round(eb.getM1BonusTax()));
            row.createCell(5).setCellValue(Math.round(eb.getM1Pay()));
            row.createCell(6).setCellValue(Math.round(eb.getM1Tax()));
            row.createCell(7).setCellValue(Math.round(eb.getM2Pay()));
            row.createCell(8).setCellValue(Math.round(eb.getM2Tax()));
            row.createCell(9).setCellValue(Math.round(eb.getM3Pay()));
            row.createCell(10).setCellValue(Math.round(eb.getM3Tax()));
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
            wb.write(outputStream);

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
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
        } else if (".xlsx".equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);

        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    //未处理公式
    public Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        DecimalFormat df = new DecimalFormat("0.00"); // 格式化数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化


        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString().trim();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return df.format(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    //处理公式
    public Object getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null || formulaEvaluator == null) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return formulaEvaluator.evaluate(cell).getNumberValue();
        }
        return getCellValue(cell);
    }

    // double四舍五入
    public static double roundDouble(double d) {
        return (double) Math.round(d * 100) / 100;
    }

    // 计算六选一最优
    private EmployeeBasic calcTaxFinal(EmployeeBasic employeeBasic) {

        // 克隆5个完全相同的实体
        EmployeeBasic eb1 = (EmployeeBasic) employeeBasic.clone();
        EmployeeBasic eb2 = (EmployeeBasic) employeeBasic.clone();
        EmployeeBasic eb3 = (EmployeeBasic) employeeBasic.clone();
        EmployeeBasic eb4 = (EmployeeBasic) employeeBasic.clone();
        EmployeeBasic eb5 = (EmployeeBasic) employeeBasic.clone();

        // 分别计算5种避税方式，计算结果保存在参数中
        avoidTaxFunc1(eb1, 18000.00f, 540.00f);
        avoidTaxFunc1(eb2, 54000.00f, 5295.00f);
        avoidTaxFunc1(eb3, 108000.00f, 21045.00f);
        avoidTaxFunc2(eb4);
        avoidTaxFunc3(eb5);

        return this.min(eb1, eb2, eb3, eb4, eb5);
    }

    // 多值计算最小值
    private EmployeeBasic min(EmployeeBasic... vals) {
        EmployeeBasic ret = null;
        for (EmployeeBasic val : vals) {
            if (ret == null || (val != null && val.getTaxFinal() < ret.getTaxFinal())) {
                ret = val;
            }
        }

        // 当一次性发放纳税 < 其他5中纳税的情况下
        if (ret.getOncePayTax() < ret.getTaxFinal()) {
            ret.setTaxFinal(ret.getOncePayTax());
            ret.setM1BonusPay(ret.getYearAll());
            ret.setM1BonusTax(ret.getOncePayTax());
            ret.setM1Pay(0.00f);
            ret.setM2Pay(0.00f);
            ret.setM3Pay(0.00f);
            ret.setM1Tax(0.00f);
            ret.setM2Tax(0.00f);
            ret.setM3Tax(0.00f);
        }
        return ret;
    }

    // 避税方式一
    private void avoidTaxFunc1(EmployeeBasic eb1, double bonusPay, double bonusTax) {
        eb1.setM1BonusPay(bonusPay);
        eb1.setM1BonusTax(bonusTax);

        // 节日月份支付，非节日月份支付
        double noHolidayMonthPay = (eb1.getYearAll() - eb1.getM1BonusPay() + 2000.00f) / 3;
        double holidayMonthPay = noHolidayMonthPay - 2000.00f;

        // 一月份过节，或者二月份过节
        if (holidayMonth == 1) {
            eb1.setM1Pay(Math.round(holidayMonthPay));
            eb1.setM2Pay(Math.round(noHolidayMonthPay));
        } else if (holidayMonth == 2) {
            eb1.setM1Pay(Math.round(noHolidayMonthPay));
            eb1.setM2Pay(Math.round(holidayMonthPay));
        }
        eb1.setM3Pay(Math.round(noHolidayMonthPay));

        // 季度绩效工资扣税 (1,4,7,10月)
        double quarterTax = calcSubstractTax(eb1.getM1());
        // 1~3月份工资扣税
        double m1Tax = calcSubstractTax(eb1.getM1() + eb1.getM1Pay()) - quarterTax;
        double m2Tax = calcSubstractTax(eb1.getM2() + eb1.getM2Pay());
        double m3Tax = calcSubstractTax(eb1.getM3() + eb1.getM3Pay());
        // 纳税合计
        double taxFinal = bonusTax + m1Tax + m2Tax + m3Tax;

        eb1.setM1Tax(m1Tax);
        eb1.setM2Tax(m2Tax);
        eb1.setM3Tax(m3Tax);
        eb1.setTaxFinal(taxFinal);

        //System.out.println(new Gson().toJson(eb1));
    }

    // 避税方式二
    private void avoidTaxFunc2(EmployeeBasic eb2) {

        double m1Pay = 0, m2Pay = 0, m3Pay = 0;

        // 先算：1~3月份发放额
        double judge = eb2.getM1() + eb2.getYearAll() - 3500;

        if (judge >= 0 && judge <= 18000) {
            m2Pay = 0;
            m3Pay = 0;
            m1Pay = 0;
        } else if (judge > 18000 && judge <= 54000) {
            m2Pay = 4500 + 3500 - eb2.getM2();
            m3Pay = 4500 + 3500 - eb2.getM3();
            m1Pay = 4500 + 3500 - eb2.getM1();
        } else if (judge > 54000 && judge <= 108000) {
            m2Pay = 9000 + 3500 - eb2.getM2();
            m3Pay = 9000 + 3500 - eb2.getM3();
            m1Pay = 9000 + 3500 - eb2.getM1();
        } else if (judge > 108000 && judge <= 420000) {
            m2Pay = 35000 + 3500 - eb2.getM2();
            m3Pay = 35000 + 3500 - eb2.getM3();
            m1Pay = 35000 + 3500 - eb2.getM1();
        } else if (judge > 420000 && judge <= 660000) {
            m2Pay = 55000 + 3500 - eb2.getM2();
            m3Pay = 55000 + 3500 - eb2.getM3();
            m1Pay = 55000 + 3500 - eb2.getM1();
        } else if (judge > 660000 && judge <= 960000) {
            m2Pay = 80000 + 3500 - eb2.getM2();
            m3Pay = 80000 + 3500 - eb2.getM3();
            m1Pay = 80000 + 3500 - eb2.getM1();
        }

        // 再算1~3月的税
        // 季度绩效工资扣税 (1,4,7,10月 U列)
        double quarterTax = calcSubstractTax(eb2.getM1());
        // 1~3月份工资扣税
        double m2Tax = calcSubstractTax(eb2.getM2() + m2Pay);
        double m3Tax = calcSubstractTax(eb2.getM3() + m3Pay);
        double m1Tax = calcSubstractTax(eb2.getM1() + m1Pay) - quarterTax;

        // 避税方式二的一月份实发
        double m1BonusPay = eb2.getYearAll() - m1Pay - m2Pay - m3Pay;

        double averBonus = m1BonusPay / 12;
        double temp_tax = 0, substract = 0;
        if (averBonus > 0 && averBonus <= 1500) {
            temp_tax = 0.03f;
            substract = 0;
        } else if (averBonus > 1500 && averBonus <= 4500) {
            temp_tax = 0.1f;
            substract = 105;
        } else if (averBonus > 4500 && averBonus <= 9000) {
            temp_tax = 0.2f;
            substract = 555;
        } else if (averBonus > 9000 && averBonus <= 35000) {
            temp_tax = 0.25f;
            substract = 1005;
        } else if (averBonus > 35000 && averBonus <= 55000) {
            temp_tax = 0.3f;
            substract = 2755;
        } else if (averBonus > 55000 && averBonus <= 80000) {
            temp_tax = 0.35f;
            substract = 5505;
        } else if (averBonus > 80000) {
            temp_tax = 0.45f;
            substract = 13505;
        }

        // 计算避税方式二，Q列：一月实发纳税金额
        double m1BounsTax = m1BonusPay * temp_tax - substract;
        double taxFinal = m1BounsTax + m1Tax + m2Tax + m3Tax;

        eb2.setM1Pay(m1Pay);
        eb2.setM2Pay(m2Pay);
        eb2.setM3Pay(m3Pay);
        eb2.setM1Tax(m1Tax);
        eb2.setM2Tax(m2Tax);
        eb2.setM3Tax(m3Tax);
        eb2.setM1BonusPay(m1BonusPay);
        eb2.setM1BonusTax(m1BounsTax);
        eb2.setTaxFinal(taxFinal);

        //System.out.println(new Gson().toJson(eb2));
    }

    // 避税方式三
    private void avoidTaxFunc3(EmployeeBasic eb3) {

        double m1Pay = 0, m2Pay = 0, m3Pay = 0;

        // 先算：1~3月份发放额
        double judge = eb3.getM1() + eb3.getYearAll() - 3500;

        if (judge >= 0 && judge <= 18000) {
            m2Pay = 0;
            m3Pay = 0;
            m1Pay = 0;
        } else if (judge > 18000 && judge <= 54000) {
            m2Pay = 1500 + 3500 - eb3.getM2();
            m3Pay = 1500 + 3500 - eb3.getM3();
            m1Pay = 1500 + 3500 - eb3.getM1();
        } else if (judge > 54000 && judge <= 108000) {
            m2Pay = 4500 + 3500 - eb3.getM2();
            m3Pay = 4500 + 3500 - eb3.getM3();
            m1Pay = 4500 + 3500 - eb3.getM1();
        } else if (judge > 108000 && judge <= 420000) {
            m2Pay = 9000 + 3500 - eb3.getM2();
            m3Pay = 9000 + 3500 - eb3.getM3();
            m1Pay = 9000 + 3500 - eb3.getM1();
        } else if (judge > 420000 && judge <= 660000) {
            m2Pay = 35000 + 3500 - eb3.getM2();
            m3Pay = 35000 + 3500 - eb3.getM3();
            m1Pay = 35000 + 3500 - eb3.getM1();
        } else if (judge > 660000 && judge <= 960000) {
            m2Pay = 55000 + 3500 - eb3.getM2();
            m3Pay = 55000 + 3500 - eb3.getM3();
            m1Pay = 55000 + 3500 - eb3.getM1();
        }

        // 再算1~3月的税
        // 季度绩效工资扣税 (1,4,7,10月 U列)
        double quarterTax = calcSubstractTax(eb3.getM1());
        // 1~3月份工资扣税
        double m2Tax = calcSubstractTax(eb3.getM2() + m2Pay);
        double m3Tax = calcSubstractTax(eb3.getM3() + m3Pay);
        double m1Tax = calcSubstractTax(eb3.getM1() + m1Pay) - quarterTax;

        // 避税方式二的一月份实发
        double m1BonusPay = eb3.getYearAll() - m1Pay - m2Pay - m3Pay;

        double averBonus = m1BonusPay / 12;
        double temp_tax = 0, substract = 0;
        if (averBonus > 0 && averBonus <= 1500) {
            temp_tax = 0.03f;
            substract = 0;
        } else if (averBonus > 1500 && averBonus <= 4500) {
            temp_tax = 0.1f;
            substract = 105;
        } else if (averBonus > 4500 && averBonus <= 9000) {
            temp_tax = 0.2f;
            substract = 555;
        } else if (averBonus > 9000 && averBonus <= 35000) {
            temp_tax = 0.25f;
            substract = 1005;
        } else if (averBonus > 35000 && averBonus <= 55000) {
            temp_tax = 0.3f;
            substract = 2755;
        } else if (averBonus > 55000 && averBonus <= 80000) {
            temp_tax = 0.35f;
            substract = 5505;
        } else if (averBonus > 80000) {
            temp_tax = 0.45f;
            substract = 13505;
        }

        // 计算避税方式二，Q列：一月实发纳税金额
        double m1BounsTax = m1BonusPay * temp_tax - substract;
        double taxFinal = m1BounsTax + m1Tax + m2Tax + m3Tax;

        eb3.setM1Pay(m1Pay);
        eb3.setM2Pay(m2Pay);
        eb3.setM3Pay(m3Pay);
        eb3.setM1Tax(m1Tax);
        eb3.setM2Tax(m2Tax);
        eb3.setM3Tax(m3Tax);
        eb3.setM1BonusPay(m1BonusPay);
        eb3.setM1BonusTax(m1BounsTax);
        eb3.setTaxFinal(taxFinal);

        //System.out.println(new Gson().toJson(eb3));
    }

    // 计算 一次性发放纳税
    // yearAll=30000,  m1=8000
    private double calcOncePayTax(double yearAll, double m1) {

        double tax = 0;
        double substract = 0;

        if (yearAll >= 0 && yearAll <= 18000) {
            tax = 0.03f;
            substract = 0;
        } else if (yearAll > 18000 && yearAll <= 54000) {
            tax = 0.1f;
            substract = 105;
        } else if (yearAll > 54000 && yearAll <= 108000) {
            tax = 0.2f;
            substract = 555;
        } else if (yearAll > 108000 && yearAll <= 420000) {
            tax = 0.25f;
            substract = 1005;
        } else if (yearAll > 420000 && yearAll <= 660000) {
            tax = 0.3f;
            substract = 2755;
        } else if (yearAll > 660000 && yearAll <= 960000) {
            tax = 0.35f;
            substract = 5505;
        }

        // 当基本工资 > 3500
        if (m1 > 3500) {
            return yearAll * tax - substract;
        } else {
            return (yearAll - (3500 - m1)) * tax - substract;
        }
    }

    // 计算基本工资扣税 (避税方式一、二、三，算R列,U列)
    private double calcSubstractTax(double m1) {
        double gap = m1 - 3500;
        double tax = 0;
        double substract = 0;

        if (gap > 0 && gap <= 1500) {
            tax = 0.03f;
            substract = 0;
        } else if (gap > 1500 && gap <= 4500) {
            tax = 0.1f;
            substract = 105;
        } else if (gap > 4500 && gap <= 9000) {
            tax = 0.2f;
            substract = 555;
        } else if (gap > 9000 && gap <= 35000) {
            tax = 0.25f;
            substract = 1005;
        } else if (gap > 35000 && gap <= 55000) {
            tax = 0.3f;
            substract = 2755;
        } else if (gap > 55000 && gap <= 80000) {
            tax = 0.35f;
            substract = 5505;
        } else if (gap > 80000) {
            tax = 0.45f;
            substract = 13505;
        }

        // 小于3500的不扣税
        return gap > 0 ? gap * tax - substract : 0;
    }



}
