package com.djh.admin.uitls;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
public class ExcelUtil {

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "templates/";

    /**
     * 生成excel对象
     * @param params 模板导出参数设置
     * @param data 模板导出数据
     * @param templateName 模板名称
     * @return workBook对象
     * @throws Exception 异常抛出
     */
    public static Workbook getWorkbook(TemplateExportParams params, Map<String, Object> data, String templateName) throws Exception {
        String templatePath = TEMPLATE_PATH + templateName;
        File file = getTemplateFile(templatePath);
        params.setTemplateUrl(file.getAbsolutePath());
        Workbook book = ExcelExportUtil.exportExcel(params, data);
        if(file.exists()) {
            file.delete();
        }
        return book;
    }

    /**
     * 导出excel对象
     * @param response httpResponse对象
     * @param workbook workBook对象
     * @param fileName 导出文件名
     * @throws Exception 异常抛出
     */
    public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/x-msdownload");
        fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("gb2312"),"ISO-8859-1")+".xls");
        ServletOutputStream outStream=null;
        log.info("start to save excel: " + fileName);
        try{
            outStream = response.getOutputStream();
            workbook.write(outStream);
        }finally{
            workbook.close();
            if(outStream != null){
                outStream.close();
            }
        }
    }

    /**
     * 获取模板文件--获取到的文件为临时文件，用完后需要手动删除
     * <p>由于springboot打包成jar之后，不能以绝对路径的形式读取模板文件，故此处将模板文件以临时文件的形式写到磁盘中，用完请手动删除</p>
     * @param templatePath 模板路径
     * @return 模板文件
     * @throws Exception 异常抛出
     */
    public static File getTemplateFile(String templatePath) throws IOException {
        File file = File.createTempFile("temp", null);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(templatePath);
        if(resources.length == 1) {
            InputStream inputStream = resources[0].getInputStream();
            inputStreamToFile(inputStream, file);
            inputStream.close();
        }else {
            System.out.println("请检查模板文件是否存在");
        }
        return file;
    }

    /**
     * InputStream 转file
     * @param ins 输入流
     * @param file 目标文件
     */
    public static void inputStreamToFile(InputStream ins,File file){
        try(OutputStream os = new FileOutputStream(file)) {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e)
        {
            log.error("error to convert stream :", e);
        }
    }

//导入获取数据方法

    public static List<List<String>> readXlsx(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        return readXlsx(input);
    }

    public static List<List<String>> readXls(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        return readXls(input);
    }

    public static List<List<String>> readXlsx(InputStream input) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        for (Sheet xssfSheet : workbook) {
            if (xssfSheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Row row = xssfSheet.getRow(rowNum);
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(((XSSFCell) cell).toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }

    public static List<List<String>> readXls(InputStream input) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    HSSFCell cell = row.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(getStringVal(cell));
                }
                result.add(rowList);
            }
        }
        return result;
    }

    private static String getStringVal(HSSFCell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }
}