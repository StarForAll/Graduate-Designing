package cn.edu.ncu.common.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Excel {

    List<Sheet> sheetList = new ArrayList<Sheet>();;

    public Excel(String fileName) {
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = fileName.endsWith(".xls") ? new HSSFWorkbook(new FileInputStream(fileName)) : new XSSFWorkbook(new FileInputStream(fileName));
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int index = 0; index < numberOfSheets; index++) {
                addSheet(new Sheet(workbook.getSheetAt(index)));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
                workbook = null;
            }
        }
    }

    public Excel(InputStream ins, ExcelFileType fileType) {
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = fileType == ExcelFileType.XLS ? new HSSFWorkbook(ins) : new XSSFWorkbook(ins);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int index = 0; index < numberOfSheets; index++) {
                addSheet(new Sheet(workbook.getSheetAt(index)));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
                workbook = null;
            }
        }
    }

    final protected void addSheet(Sheet sheet) {
        this.sheetList.add(sheet);
    }

    final protected void addSheetList(Collection<Sheet> sheets) {
        this.sheetList.addAll(sheets);
    }

    final public List<Sheet> getSheetList() {
        return sheetList;
    }

    final public Sheet getSheet(String sheetName) {
        for (Sheet sheet : sheetList) {
            if (sheet.getName().equals(sheetName)) {
                return sheet;
            }
        }
        return null;
    }
}

