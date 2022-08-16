package neo4j.SRC;

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
import entity.Service;
import entity.Sets;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class WkfCrdSet {
	//计算wkf的crd
	
	static ArrayList<Float> wcrd = new ArrayList<Float>();//wkf的crd
	static ArrayList<Float> wpcrd = new ArrayList<Float>();//wkf的平均crd
	
	
	
	public static void main(String[] args) 
	{
		calWCrd();
		claWPCrd();
		claSrcCrd();
	}
	
	//计算wkf的crd
	public static void calWCrd()
	{
		try {
			float[] dv = new float[2];
			float crd;
			
            File file = new File("C:/Users/DJ/Desktop/1/evaluation/wkfcrd.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
//            List<String>src = new ArrayList<String>();
            for (int i = 0; i < 1058; i++) { // 循环打印Excel表中的内容 
                         	
            	for (int j = 0; j < sheet.getColumns(); j++) { 
                    Cell cell = sheet.getCell(j, i); 
                    if(cell.getContents().isEmpty())
                    {
                    	break;
                    }
                    dv[j] = Integer.parseInt(cell.getContents());
                }
            	
               crd = (float) (0.5*(dv[0]/10000) + 0.5*(dv[1]/6000));
               wcrd.add(crd);
            }  
//            for(int n =0; n<1058; n++)
//            {
//            	System.out.println("wrc["+(n+1)+"]: "+wcrd.get(n));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//计算wkf中src的平均信誉度
	public static void claWPCrd()
	{
		SWDesSet.getSWID();
		ArrayList<Integer> snm = new ArrayList<Integer>(Collections.nCopies(1058, 0));
		int id = 0;
		int nm = 0;
		float pcrd;
		for(int i =0; i< Sets.srcwid.size(); i++)
		{
			for(int j=0; j<Sets.srcwid.get(i).size(); j++)
			{
				id = Sets.srcwid.get(i).get(j)-1;
				nm = snm.get(id);
				nm++;
				snm.set(id, nm);
				nm =0;
			}
		}
				
		for(int k =0; k<snm.size(); k++)
		{
			pcrd = wcrd.get(k)/snm.get(k);
			wpcrd.add(k, pcrd);
//			System.out.println("wcrd"+(k+1)+": "+wcrd.get(k));
//			System.out.println("snm"+(k+1)+": "+snm.get(k));
//			System.out.println("wpcrd"+(k+1)+": "+wpcrd.get(k));
		}
	}
	
	//计算src的信誉度
	public static void claSrcCrd()
	{
				
		for(int i=0; i<Sets.srcwid.size();i++)
		{
			float scr=0;
			for(int j=0; j<Sets.srcwid.get(i).size(); j++)
			{
				scr += wpcrd.get(Sets.srcwid.get(i).get(j)-1);
			}
			Sets.cr.add(i, scr/Sets.srcwid.get(i).size());
//			System.out.println(Sets.cr.get(i));
		}
	}
			
}
