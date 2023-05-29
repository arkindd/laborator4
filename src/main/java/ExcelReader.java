import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    public static Data xData = new Data("X");
    public static Data yData = new Data("Y");
    public static Data zData = new Data("Z");


    public static void readFromExcel(File file) throws IOException, InvalidFormatException {
        XSSFWorkbook book = new XSSFWorkbook(file);
        XSSFSheet sheet = book.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            xData.dataList.add(row.getCell(0).getNumericCellValue());
            yData.dataList.add(row.getCell(1).getNumericCellValue());
            zData.dataList.add(row.getCell(2).getNumericCellValue());
        }
        xData.calculateAll(yData.getDataInArray());
        yData.calculateAll(zData.getDataInArray());
        zData.calculateAll(xData.getDataInArray());
    }

    public static void writeToExcel() throws IOException {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Results");
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 1600);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 4800);
        sheet.setColumnWidth(9, 15500);
        sheet.setColumnWidth(10, 3500);
        sheet.setColumnWidth(11, 3500);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Variable");
        row.createCell(1).setCellValue("Geometric mean");
        row.createCell(2).setCellValue("Arithmetic mean");
        row.createCell(3).setCellValue("Standard deviation estimation");
        row.createCell(4).setCellValue("Range");
        row.createCell(5).setCellValue("Covariance coefficient");
        row.createCell(6).setCellValue("Length");
        row.createCell(7).setCellValue("Variation coefficient");
        row.createCell(8).setCellValue("Variance estimation");
        row.createCell(9).setCellValue("Confidence interval");
        row.createCell(10).setCellValue("Minimum");
        row.createCell(11).setCellValue("Maximum");
        writeRowOfCalculations(xData, 1, sheet, "Y");
        writeRowOfCalculations(yData, 2, sheet, "Z");
        writeRowOfCalculations(zData, 3, sheet, "X");
        File f = new File("src/main/resources/Results.xlsx");
        book.write(new FileOutputStream(f));
        book.close();
    }

    public static void writeRowOfCalculations(Data data, int rowNum, XSSFSheet sheet, String secondVariable) {
        XSSFRow row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(data.variable);
        row.createCell(1).setCellValue(data.geometricMean);
        row.createCell(2).setCellValue(data.arithmeticMean);
        row.createCell(3).setCellValue(data.standardDeviationEstimation);
        row.createCell(4).setCellValue(data.range);
        row.createCell(5).setCellValue(data.variable + secondVariable + ": " + data.covarianceCoefficient);
        row.createCell(6).setCellValue(data.length);
        row.createCell(7).setCellValue(data.variationCoefficient);
        row.createCell(8).setCellValue(data.varianceEstimation);
        row.createCell(9).setCellValue(String.valueOf(data.confidenceInterval));
        row.createCell(10).setCellValue(data.minimum);
        row.createCell(11).setCellValue(data.maximum);
    }
}