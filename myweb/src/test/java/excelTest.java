import com.google.gson.Gson;
import com.sfy.domain.EmployeeBasic;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shifengyuan. (目前没做的是小于0的，按照10000填充)
 * Date: 2018/4/6
 * Time: 20:13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-excel.xml"})
public class excelTest {

    private Workbook work = null;
    private FormulaEvaluator formulaEvaluator = null;
    private Map<String, Integer> cellRules;
    private int holidayMonth;   // 发"节日补助"的月份

    @Test
    public void createSimple() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();        //创建新工作簿
        HSSFSheet sheet = workbook.createSheet("hello");        //新建工作表
        HSSFRow row = sheet.createRow(0);        //创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
        HSSFCell cell = row.createCell(2);        //创建单元格,row已经确定了行号,列号作为参数传递给createCell(),第一列从0开始计算
        cell.setCellValue("hello sheet");        //设置单元格的值,即C1的值(第一行,第三列)

        //输出到磁盘中
        FileOutputStream out = new FileOutputStream(new File("F:\\Web Tutorials\\Bootstrap\\模板\\Bootstrap HTML5 Responsive Templates\\flatty\\theme\\11.xls"));
        workbook.write(out);
        out.close();
    }


    /*
    POI之前的版本不支持大数据量处理，如果数据过多则经常报OOM错误，有时候调整JVM大小效果也不是太好。
    3.8版本的POI新出来了SXSSFWorkbook,可以支持大数据量的操作，只是SXSSFWorkbook只支持.xlsx格式，不支持.xls格式。
    3.8版本的POI对excel的导出操作，一般只使用HSSFWorkbook以及SXSSFWorkbook，HSSFWorkbook用来处理较少的数据量，
    SXSSFWorkbook用来处理大数据量以及超大数据量的导出。 HSSFWorkbook的使用方法和之前的版本的使用方法一致，这里就不在陈述使用方法了
    SXSSFWorkbook的使用例子如下：
    */
    @Test
    public void createMulti() throws IOException {
        Workbook wb = new SXSSFWorkbook(1000); //内存中保留 1000 条数据，以免内存溢出，其余写入硬盘
        Sheet sh = wb.createSheet();
        for (int rownum = 0; rownum < 2000; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }
        }
        FileOutputStream out = new FileOutputStream("C:\\Users\\yuan\\Desktop\\输出.xlsx");
        wb.write(out);
        out.close();
    }

    @Test
    public void formateExportExcel() {
        try {
            // 创建Excel表格工作簿
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("表格单元格格式化");

            //============================
            //       设置单元格的字体
            //============================
            Row ztRow = sheet.createRow((short) 0);
            Cell ztCell = ztRow.createCell(0);
            ztCell.setCellValue("中国");
            // 创建单元格样式对象
            XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
            // 创建字体对象
            Font ztFont = wb.createFont();
            ztFont.setItalic(true);                     // 设置字体为斜体字
            ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”
            ztFont.setFontHeightInPoints((short) 22);    // 将字体大小设置为18px
            ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上
            ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）
//          ztFont.setStrikeout(true);                  // 是否添加删除线
            ztStyle.setFont(ztFont);                    // 将字体应用到样式上面
            ztCell.setCellStyle(ztStyle);               // 样式应用到该单元格上

            //============================
            //        设置单元格边框
            //============================
            Row borderRow = sheet.createRow(2);
            Cell borderCell = borderRow.createCell(1);
            borderCell.setCellValue("中国");
            // 创建单元格样式对象
            XSSFCellStyle borderStyle = (XSSFCellStyle) wb.createCellStyle();
            // 设置单元格边框样式
            // CellStyle.BORDER_DOUBLE      双边线
            // CellStyle.BORDER_THIN        细边线
            // CellStyle.BORDER_MEDIUM      中等边线
            // CellStyle.BORDER_DASHED      虚线边线
            // CellStyle.BORDER_HAIR        小圆点虚线边线
            // CellStyle.BORDER_THICK       粗边线
            borderStyle.setBorderBottom(CellStyle.BORDER_THICK);
            borderStyle.setBorderTop(CellStyle.BORDER_DASHED);
            borderStyle.setBorderLeft(CellStyle.BORDER_DOUBLE);
            borderStyle.setBorderRight(CellStyle.BORDER_THIN);

            // 设置单元格边框颜色
            borderStyle.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));
            borderStyle.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));
            borderStyle.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));

            borderCell.setCellStyle(borderStyle);

            //============================
            //      设置单元内容的对齐方式
            //============================
            Row alignRow = sheet.createRow(4);
            Cell alignCell = alignRow.createCell(1);
            alignCell.setCellValue("中国");

            // 创建单元格样式对象
            XSSFCellStyle alignStyle = (XSSFCellStyle) wb.createCellStyle();

            // 设置单元格内容水平对其方式
            // XSSFCellStyle.ALIGN_CENTER       居中对齐
            // XSSFCellStyle.ALIGN_LEFT         左对齐
            // XSSFCellStyle.ALIGN_RIGHT        右对齐
            alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            // 设置单元格内容垂直对其方式
            // XSSFCellStyle.VERTICAL_TOP       上对齐
            // XSSFCellStyle.VERTICAL_CENTER    中对齐
            // XSSFCellStyle.VERTICAL_BOTTOM    下对齐
            alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

            alignCell.setCellStyle(alignStyle);

            //============================
            //      设置单元格的高度和宽度
            //============================
            Row sizeRow = sheet.createRow(6);
            sizeRow.setHeightInPoints(30);                  // 设置行的高度

            Cell sizeCell = sizeRow.createCell(1);
            String sizeCellValue = "《Java编程思想》";            // 字符串的长度为10，表示该字符串中有10个字符，忽略中英文
            sizeCell.setCellValue(sizeCellValue);
            // 设置单元格的长度为sizeCellVlue的长度。而sheet.setColumnWidth使用sizeCellVlue的字节数
            // sizeCellValue.getBytes().length == 16
            sheet.setColumnWidth(1, (sizeCellValue.getBytes().length) * 256);

            //============================
            //      设置单元格自动换行
            //============================
            Row wrapRow = sheet.createRow(8);
            Cell wrapCell = wrapRow.createCell(2);
            wrapCell.setCellValue("宝剑锋从磨砺出,梅花香自苦寒来");

            // 创建单元格样式对象
            XSSFCellStyle wrapStyle = (XSSFCellStyle) wb.createCellStyle();
            wrapStyle.setWrapText(true);                    // 设置单元格内容是否自动换行
            wrapCell.setCellStyle(wrapStyle);

            //============================
            //         合并单元格列
            //============================
            Row regionRow = sheet.createRow(12);
            Cell regionCell = regionRow.createCell(0);
            regionCell.setCellValue("宝剑锋从磨砺出,梅花香自苦寒来");

            // 合并第十三行中的A、B、C三列
            CellRangeAddress region = new CellRangeAddress(12, 12, 0, 2); // 参数都是从O开始
            sheet.addMergedRegion(region);

            //============================
            //         合并单元格行和列
            //============================
            Row regionRow2 = sheet.createRow(13);
            Cell regionCell2 = regionRow2.createCell(3);
            String region2Value = "宝剑锋从磨砺出,梅花香自苦寒来。"
                    + "采得百花成蜜后,为谁辛苦为谁甜。"
                    + "操千曲而后晓声,观千剑而后识器。"
                    + "察己则可以知人,察今则可以知古。";
            regionCell2.setCellValue(region2Value);

            // 合并第十三行中的A、B、C三列
            CellRangeAddress region2 = new CellRangeAddress(13, 17, 3, 7); // 参数都是从O开始
            sheet.addMergedRegion(region2);

            XSSFCellStyle region2Style = (XSSFCellStyle) wb.createCellStyle();
            region2Style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            region2Style.setWrapText(true);                     // 设置单元格内容是否自动换行
            regionCell2.setCellStyle(region2Style);

            //============================
            // 将Excel文件写入到磁盘上
            //============================
            FileOutputStream is = new FileOutputStream("C:\\Users\\yuan\\Desktop\\测试合并单元格.xlsx");
            wb.write(is);
            is.close();

            System.out.println("写入成功，运行结束！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calcMinDouble() {

        EmployeeBasic employeeBasic = new EmployeeBasic();

        // 克隆5个完全相同的实体
        EmployeeBasic eb1 = (EmployeeBasic) employeeBasic.clone();
        eb1.setTaxFinal(1000.0f);
        EmployeeBasic eb2 = (EmployeeBasic) employeeBasic.clone();
        eb2.setTaxFinal(2000.0f);
        EmployeeBasic eb3 = (EmployeeBasic) employeeBasic.clone();
        eb3.setTaxFinal(300.0f);
        EmployeeBasic eb4 = (EmployeeBasic) employeeBasic.clone();
        eb4.setTaxFinal(4000.0f);
        EmployeeBasic eb5 = (EmployeeBasic) employeeBasic.clone();
        eb5.setTaxFinal(900.0f);

        System.out.println(eb1);
        System.out.println(eb2);
        System.out.println(eb3);
        System.out.println(eb4);
        System.out.println(eb5);

        // 分别计算5种避税方式，计算结果保存在参数中

        employeeBasic = this.min(eb1, eb2, eb3, eb4, eb5);
        System.out.println(new Gson().toJson(employeeBasic));
        System.out.println(employeeBasic);
    }

    @Test
    public void cloneEmployee() {
        EmployeeBasic eb1 = new EmployeeBasic();
        eb1.setM1(1000.0f);

        EmployeeBasic eb2 = (EmployeeBasic) eb1.clone();
        eb2.setM1(2000.0f);

        EmployeeBasic eb3 = (EmployeeBasic) eb1.clone();
        eb3.setM1(3000.0f);

        EmployeeBasic eb4 = eb1;

        System.out.println(eb1);
        System.out.println(eb2);
        System.out.println(eb3);
        System.out.println(eb4);
    }

    @Test
    public void testRoundDouble() {
        double value1 = 100.218f;
        double value2 = 100.551f;
        System.out.println(roundDouble(value1));
        System.out.println(roundDouble(value2));
        System.out.println(Math.round(value1));
        System.out.println(Math.round(value2));
    }

    private void initMap() {
        cellRules = new HashMap<>();
        cellRules.put("人力资源系统编码", 0);
        cellRules.put("姓名", 1);
        cellRules.put("表一应纳税额", 25);
        cellRules.put("表二应纳税额", 25);
        cellRules.put("表三应纳税额", 25);
        cellRules.put("表四收入合计", 8);
    }

    @Test
    public void soutBasicValue() throws Exception {

        initMap();
        List<EmployeeBasic> list = new ArrayList<>();

        File excelFile = new File("C:\\Users\\yuan\\Desktop\\测试.xlsx");
        FileInputStream is = new FileInputStream(excelFile);

        work = this.getWorkbook(is, excelFile.getName());
        Sheet sheet1 = work.getSheet("基础数据表一");
        Sheet sheet2 = work.getSheet("基础数据表二");
        Sheet sheet3 = work.getSheet("基础数据表三");
        Sheet sheet4 = work.getSheet("基础数据表四");
        //System.out.println("row: " + sheet1.getFirstRowNum() + " -------- " + sheet1.getLastRowNum());    // 0-394
        //System.out.println("Cell: " + sheet1.getRow(0).getFirstCellNum() + "-------" + sheet1.getRow(0).getLastCellNum());    // 0-26
        //System.out.println("0*0:" + this.getCellValue(sheet1.getRow(0).getCell(0)));
        //System.out.println("1*0:" + this.getCellValueFormula(sheet1.getRow(2).getCell(0), formulaEvaluator));
        //System.out.println("1*0:" + this.getCellValueFormula(sheet1.getRow(2).getCell(25), formulaEvaluator));

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

        outputExcel(list);  // 输出list
    }

    private void outputExcel(List<EmployeeBasic> list) throws IOException {
        Workbook wb = new SXSSFWorkbook(1000);
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

        FileOutputStream out = new FileOutputStream("C:\\Users\\yuan\\Desktop\\border-output.xlsx");
        wb.write(out);
        out.close();

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


    public Map<String, Integer> getCellRules() {
        return cellRules;
    }

    public void setCellRules(Map<String, Integer> cellRules) {
        this.cellRules = cellRules;
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
