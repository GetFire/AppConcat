package ver2;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TxtToExcel {
    public static void main(String[] args) {
        String line;
        List<List<String>> lists = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Похожие модели");
        try (BufferedReader br = new BufferedReader(
                new FileReader("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы2.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\t");
                lists.add(Arrays.asList(arr));
            }


            int rowNum = 0;
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue("Model");
            row.createCell(1).setCellValue("Related_product");

            for (List<String> list : lists) {
                ++rowNum;
                Row row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(list.get(0));
                String neddedLine;
                StringBuilder sb = new StringBuilder();
                for (String s : list) {
                    if (s.equals(list.get(0))) {
                        continue;
                    }
                    sb.append(s).append(" | ");
                }
                neddedLine = sb.toString();
                sb = new StringBuilder();
                row1.createCell(1).setCellValue(neddedLine);
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            try {
                workbook.write(new FileOutputStream("D:\\practice\\AppConcat\\src\\main\\resources\\Final1.xls"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
