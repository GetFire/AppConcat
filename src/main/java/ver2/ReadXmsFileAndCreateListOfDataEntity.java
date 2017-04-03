package ver2;


import entity.DataModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadXmsFileAndCreateListOfDataEntity {
    public static void main(String[] args) throws IOException {

        //Read excel resource file and create list of entities
        List<DataModel> list = readModelFromExcelFile("D:\\practice\\AppConcat\\src\\main\\resources\\res.xls");
        System.out.println(list.size());

        // you need to prepare this file for next steps. See example in рксурсы3.txt
        String txtFilePath = "D:\\practice\\AppConcat\\src\\main\\resources\\TestExe1.xls";

        writeDataModelToExcel(findNededLines(list, "D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы1.txt"),
                txtFilePath);

    }

    //This method read data from excel and create entity object
    public static List<DataModel> readModelFromExcelFile(String excelFilePath) throws IOException {
        List<DataModel> dataModels = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            DataModel dataModel = new DataModel();
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();
                switch (columnIndex) {
                    case 1:
                        dataModel.setModel(nextCell.getStringCellValue());
                        break;
                    case 2:
                        dataModel.setName(nextCell.getStringCellValue());
                        break;
                    case 3:
                        dataModel.setRelatedProduct(nextCell.getStringCellValue());
                        break;
                }
            }
            dataModels.add(dataModel);
        }
        workbook.close();
        fileInputStream.close();
        return dataModels;
    }

    //This method write entity object to excel file
    public static void writeDataModelToExcel(List<DataModel> dataModels, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowCount = 0;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue("Product_ID");
        row.createCell(1).setCellValue("Model");
        row.createCell(2).setCellValue("Name");
        row.createCell(3).setCellValue("Related_product");


        for (DataModel dataModel : dataModels) {
            row = sheet.createRow(++rowCount);
            writeDataModel(dataModel, row);
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    //This method help me to create rows in excel file when I writing it
    private static void writeDataModel(DataModel dataModel, Row row) {
        row.createCell(0).setCellValue(dataModel.getOne());
        row.createCell(1).setCellValue(dataModel.getModel());
        row.createCell(2).setCellValue(dataModel.getName());
        row.createCell(3).setCellValue(dataModel.getRelatedProduct());

    }

    // This method is main method of this app. It is compare and find those rows which we needed to find
    private static List<DataModel> findNededLines(List<DataModel> dataModels, String txtFilePath) {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        List<String> content = new LinkedList<>();
        List<DataModel> readyToWrite = new LinkedList<>();

        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(txtFilePath)));
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (DataModel dataModel : dataModels) {
            // retrieve model name from entity
            String modelName = dataModel.getModel();
            for (String s : content) {
                /**BE CAREFUL WITH SPLIT REGEX*/
                List<String> some = Arrays.asList(s.split(", "));
                if (some.contains(modelName)) {
                    List<String> lines = some.stream().filter(a -> !a.endsWith(modelName)).collect(Collectors.toList());
                    for (String line1 : lines) {
                        sb.append(" | ").append(line1);
                    }
                    String relatedProduct = sb.toString();
                    dataModel.setRelatedProduct(relatedProduct);
                    readyToWrite.add(dataModel);
                    sb = new StringBuilder();
                }else {
                    readyToWrite.add(dataModel);
                }
            }
        }

        Set<DataModel> removeDuplicates = new HashSet<>(readyToWrite);
        readyToWrite.clear();
        readyToWrite.addAll(removeDuplicates);

        return readyToWrite;
    }

}
