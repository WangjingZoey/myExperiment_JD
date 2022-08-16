package neo4j.DLP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neo4j.dataprocessing.Stopwords;
import neo4j.SRC.SWDesSet;
import neo4j.SRC.SrcDesSet;
import neo4j.SRC.WkfCrdSet;
import entity.Service;
import entity.Sets;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DlpCrdSet {
	//计算wkf的crd
	
	public static ArrayList<Float> rt = new ArrayList<Float>();//dlp的rt
	public static ArrayList<Float> dscrd = new ArrayList<Float>();//dlp的srccrd
	public static ArrayList<Float> cr = new ArrayList<Float>();//src的cr
	public static ArrayList<String> sd = new ArrayList<String>();//src的dlp
	public static ArrayList<Integer> sdid = new ArrayList<Integer>();//src的dlp的id
	
	public static ArrayList<String> dlpnm = new ArrayList<String>();
	
	
	public static void main(String[] args) 
	{
		calDRt();
		WkfCrdSet.main(args);
		isDlp();
		

	}
	
	//计算dlp的rt
	public static void calDRt()
	{
		try {
			
            File file = new File("C:/Users/DJ/Desktop/1/evaluation/dlprt.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
//            List<String>src = new ArrayList<String>();
            for (int i = 0; i < 175; i++) { // 循环打印Excel表中的内容 
            	float crd=0;         	
            	for (int j = 0; j < sheet.getColumns(); j++) { 
                    Cell cell = sheet.getCell(j, i); 
                    if(cell.getContents().isEmpty())
                    {
                    	break;
                    }
                    crd = Float.parseFloat(cell.getContents());
                }
            	
               rt.add(crd);
            }  
            
          
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	public static void isDlp()
	{
		try {
			File file = new File("C:/Users/DJ/Desktop/1/evaluation/isd.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
            
            File file2 = new File("C:/Users/DJ/Desktop/1/evaluation/dlpnm.xls"); // 创建文件对象
            Workbook wb2 = Workbook.getWorkbook(file2); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet2 = wb2.getSheet(0); // 从工作区中取得页（Sheet） 
            
            File file3 = new File("C:/Users/DJ/Desktop/1/evaluation/srccrd.xls"); // 创建文件对象
            Workbook wb3 = Workbook.getWorkbook(file3); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet3 = wb3.getSheet(0);
            
//            List<String>src = new ArrayList<String>();
            for (int i = 0; i < 2870; i++) { // 循环打印Excel表中的内容 
            	Cell cell = sheet.getCell(0, i); 
                if(cell.getContents().isEmpty())
                {
                	break;
                }
            	sd.add(i, cell.getContents());
            } 
            
            for(int i=0;i<175;i++)
            {
            	Cell cell2 = sheet2.getCell(0, i); 
                if(cell2.getContents().isEmpty())
                {
                	break;
                }
                dlpnm.add(i, cell2.getContents());
            }
            
            ArrayList<Float> scrd = new ArrayList<Float>();
            for(int i=0;i<2870;i++)
            {
            	Cell cell3 = sheet3.getCell(0, i); 
                if(cell3.getContents().isEmpty())
                {
                	break;
                }
                scrd.add(Float.parseFloat(cell3.getContents()));
            }
//            
            for (int i = 0; i < 2870; i++)
            {
            	for(int j =0; j<175;j++)
            	{
            		if(sd.get(i).compareTo(dlpnm.get(j))==0)
            		{
            			sdid.add(i, j+2001);
//            			System.out.println(sdid.get(i));
            		}
            	}
            }
            
            System.out.println("================================================================");
            ArrayList<Integer> ids = new ArrayList<Integer>();
            for (int i = 0; i < 175; i++)
            {
            	float sum=0;
            	for(int j =0; j<2870;j++)
            	{
            		if(sd.get(j).compareTo(dlpnm.get(i))==0)
            		{
            			ids.add(j);
//            			sum += Sets.cr.get(j);
            			sum += scrd.get(j);
            		}
            	}
            	dscrd.add(i, ((sum+rt.get(i))/20));
            	System.out.println(dscrd.get(i));
            	sum =0;
            	
            	Sets.dsid.put(i, ids);
//            	System.out.println(Sets.dsid.get(i));
            	ids.clear();
            	
            }
//          //创建Excel文件
//            File file3=new File("C:/Users/DJ/Desktop/1/evaluation/dlpcrd.xls");
//            //创建文件
//            file3.createNewFile();
//            //创建工作薄
//            WritableWorkbook workbook = Workbook.createWorkbook(file);
//            //创建sheet
//            WritableSheet sheet3=workbook.createSheet("sheet1",0);
//            //添加数据
//            Label label=null;
//            
//            for(int n =0; n<175; n++)
//            {
//                sheet3.addCell(new jxl.write.Number(0,n,dscrd.get(n)));
//            	
//            }
            
//            for(int i=0;i<175;i++)
//    		{
//    			float sum = 0;
//    			for(int j=0; j<Sets.dsid.get(i).size(); j++)
//    			{
//    				sum += Sets.cr.get(Sets.dsid.get(i).get(j));
//    				System.out.println(sum);
//    			}
//    		}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
}
