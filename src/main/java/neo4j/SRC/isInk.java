package neo4j.SRC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nlp.lda.main.Sim2Vec;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class isInk {
	public static void main(String[] args)
	{
		getSimOfFrd();
	}
	
	public static void getSimOfFrd()
	{
		
		try {
			File file = new File("C:/Users/DJ/Desktop/1/evaluation/isInk.xls"); // 创建文件对象
		    Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
			
			File file1 = new File("C:/Users/DJ/Desktop/1/evaluation/srctp.xls"); // 创建文件对象
		    Workbook wb1 = Workbook.getWorkbook(file1);
			Sheet sheet1 = wb1.getSheet(0); // 从工作区中取得页（Sheet） 
			
			
			ArrayList<Integer> dlp1 = new ArrayList<Integer>();
			ArrayList<Integer> dlp2 = new ArrayList<Integer>();
			for(int i=0;i<2516;i++)
            {
            	Cell cell1 = sheet.getCell(0, i); 
            	Cell cell2 = sheet.getCell(1, i); 
                if(cell1.getContents().isEmpty())
                {
                	break;
                }
                dlp1.add(Integer.parseInt(cell1.getContents()));
                dlp2.add(Integer.parseInt(cell2.getContents()));
            }
			
			ArrayList<String> dlptp = new ArrayList<String>();
			for(int i=0;i<2870;i++)
            {
            	Cell cell1 = sheet1.getCell(0, i); 
                if(cell1.getContents().isEmpty())
                {
                	break;
                }
                dlptp.add(cell1.getContents());
            }
			
			for(int i=0;i<2516;i++)
			{
				Sim2Vec t = new Sim2Vec(String.valueOf((dlptp.get(dlp1.get(i)-3001))),String.valueOf(dlptp.get(dlp2.get(i)-3001)));
				System.out.println(t.sim()/2);
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 从文件流中获取Excel工作区对象（WorkBook）
	    
	}
	
}
