import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shifengyuan.
 * Date: 2018/4/6
 * Time: 20:13
 */
public class excelTest {

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
        Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for (int rownum = 0; rownum < 2000; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }
        }
        FileOutputStream out = new FileOutputStream("theme/11-multi.xlsx");
        wb.write(out);
        out.close();
    }
}
