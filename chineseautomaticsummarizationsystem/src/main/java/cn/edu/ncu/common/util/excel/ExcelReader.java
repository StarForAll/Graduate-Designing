package cn.edu.ncu.common.util.excel;

/**
 * @author zhuoda
 * @Date 2020/8/10
 */


import cn.edu.ncu.common.util.basic.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelReader {

    public static Excel openExcel(String filePath) throws IOException {
        FileUtil.isFileExistThrowException(filePath);
        return new Excel(new File(filePath).getCanonicalPath());
    }

    public static Excel openExcel(File file) throws IOException {
        return new Excel(file.getCanonicalPath());
    }

    public static Excel openExcel(InputStream ins, ExcelFileType fileType) throws IOException {
        return new Excel(ins, fileType);
    }

    public static void main(String[] args) throws Exception {
        Excel Excel = openExcel(new FileInputStream(new File("F:/privilege.xlsx")), ExcelFileType.XLSX);
        System.out.println(Excel.getSheetList());
    }

}
