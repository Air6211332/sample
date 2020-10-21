package org.airms.kn.cnitpm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
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
            if (!sheetName.equals("Sheet1")) {
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
                    cni.setVeiodLink(changeUrl(vedioLink));
                    cniList.add(cni);
                }
            }
        }

        List<String[]> downloadList = new ArrayList<>();
        downloadList.add(new String[]{"url","title"});
        for (Cni cni : cniList) {
            if(cni.getVeiodLink()!=null&&cni.getVeiodLink().endsWith(".m3u8")){
                CniDownload download = new CniDownload();
                download.setUrl(cni.getVeiodLink());
                download.setTitle(cni.getName1()+"__"+cni.getName2());
                downloadList.add(new String[]{download.getUrl(),download.getTitle()});
            }
        }
        File csvFile = new File("/Users/rubenchen/work/01-krtech/00-周报/out.csv");
        CsvWriter writer = CsvUtil.getWriter(csvFile, Charset.defaultCharset(), false);
//        writer.write(downloadList);
        writer.write(downloadList).flush();

//        download(cniList);
//        File outFile = new File("/Users/rubenchen/work/01-krtech/00-周报/out.xlsx");
//        ExcelUtil.getWriter(outFile).write(cniList, true).flush();
    }

    private static void download(List<Cni> cniList) {
        for (Cni cni : cniList) {
            File dir = new File("/Users/rubenchen/work/01-krtech/99-temp/" + cni.getName1() + "/" + cni.getName2() + "/");
            dir.mkdirs();

//            String pdfLink = cni.getPdfLink();
//            if(pdfLink!=null&&pdfLink.endsWith(".pdf")){
//                String name = ReUtil.getGroup1("file/(.+)\\.pdf", pdfLink);
//                name = URLUtil.decode(name);
//                System.out.println(name+"\n"+pdfLink);
//                File file = new File(dir, name+".pdf");
//                HttpUtil.downloadFile(pdfLink,file);
//            }

            String veiodLink = cni.getVeiodLink();


            if (veiodLink != null && veiodLink.endsWith(".m3u8")) {
//                System.out.println(veiodLink);
                HttpRequest request = HttpUtil.createGet(veiodLink);
                HttpResponse response = request.execute();
                if (response.getStatus() == 200) {
                    String resp = response.body();
                    System.out.println(resp);
                } else {
                    System.err.print(response.getStatus());
                    System.err.print('\t');
                    System.err.print(cni.getName1());
                    System.err.print('\t');
                    System.err.print(cni.getName2());
                    System.err.print('\t');
                    System.err.println(veiodLink);
                }

            }
        }
    }

    private static String getLink(Cell cell) {
        Hyperlink hyperlink = cell.getHyperlink();
        if (hyperlink != null) {
            return hyperlink.getAddress();
        }
        return null;
    }

    private static String changeUrl(String url) {
        if (url != null) {
            String key = ReUtil.getGroup1("play\\.aspx\\?vsid=\\d+&w=\\d+&h=\\d+&v=(\\w+)(_\\w)", url);
            if (key == null) {
                return null;
            }
            if (key.length() == 32) {
//                return "http://hls.videocc.net/source/" + key.substring(0, 10) + "/f/" + key + ".m3u8";
                return "http://hls.videocc.net/" + key.substring(0, 10) + "/a/" + key + ".m3u8";
            } else {
                System.out.println(key);
            }
        }
        return url;
    }
}
