package org.airms.kn.cnitpm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

public class GetInfo {
    public static void main(String[] args) {
        BufferedInputStream in = FileUtil.getInputStream("/Users/rubenchen/work/01-krtech/00-周报/视频连接.xlsx");
        ExcelReader reader = ExcelUtil.getReader(in);
        List<Sheet> sheetList = reader.getSheets();
        List<Cni> cniList = new ArrayList<>();
        for (Sheet sheet : sheetList) {
            String sheetName = sheet.getSheetName();
            System.out.println(sheetName);
            if(!sheetName.equals("Sheet1")){
                reader.setSheet(sheet);
                int rowCount = reader.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    String name = (String) reader.readCellValue(0, i);
                    Cell pdfCell = reader.getCell(1, i);
                    Cell vedioCell = reader.getCell(2, i);
                    String pdfLink = getLink(pdfCell);
                    String vedioLink = getLink(vedioCell);
                    Cni cni = new Cni();
                    cni.setName1(sheetName);
                    cni.setName2(name);
                    cni.setPdfLink(pdfLink);
                    cni.setVeiodLink(vedioLink);
                    cniList.add(cni);
                    System.out.println();
                }
            }
        }
    }

    private static String getLink(Cell cell){
        Hyperlink hyperlink = cell.getHyperlink();
        if(hyperlink!=null){
            return hyperlink.getAddress();
        }
        return null;
    }
}
