package neo4j.DLP;

import java.io.File;

import jxl.Workbook;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nlp.lda.com.FileUtil;

public class WriteToExcl {
	public static void main(String[] args) throws Exception{
        //创建Excel文件
        File file=new File("C:/Users/DJ/Desktop/1/evaluation/dlptp.xls");
        //创建文件
        file.createNewFile();
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        //创建sheet
        WritableSheet sheet=workbook.createSheet("sheet1",0);
        //添加数据
        Label label=null;
        List<String> tw_list = new ArrayList<String>();
        tw_list = FileUtil.readFileByLines("data/DDSC/lda_100.theta");
        //追加数据
        for (int i=0;i<175;i++){
            label=new Label(0,i,tw_list.get(i));
            sheet.addCell(label);
        }
        workbook.write();
        workbook.close();

    }

}
