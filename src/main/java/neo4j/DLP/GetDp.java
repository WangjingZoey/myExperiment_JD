package neo4j.DLP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import nlp.lda.com.FileUtil;
import entity.Developer;
import entity.Sets;
import entity.Wkf;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class GetDp {
	
	public static void main(String[] args)
	{
		// 初始结点集合
		List<String> dscStrings = new ArrayList<String>();
		// 读取目标文件，获取点的信息，生成点的集合
		dscStrings = FileUtil.readFileByLines("C:/Users/DJ/Desktop/data/dpdsc/dp.txt");
		List<Developer> dps = new ArrayList<Developer>();
		for (String tmp : dscStrings) 
		{
			String[] pntArray = tmp.split(",");// 将字符串按逗号分割
			// 将数组中0,1位赋值给点的x,y
			Developer d = new Developer(Integer.parseInt(pntArray[0]), pntArray[1],
					pntArray[2], Double.parseDouble(pntArray[3]));
			dps.add(d);
		}
		System.out.println("dps done"+"\n");
		
		for(Developer dp : dps)
		{
			List<String> dsc;
			double crd = 0;
			Wkf wk;
			//wids
			List<Integer> Wids = new ArrayList<Integer>();
			String[] pntArray = dp.getWids().split(";");
			for(int i = 0; i<pntArray.length; i++)
			{
				if(!pntArray[i].equals(""))
					Wids.add(Integer.parseInt(pntArray[i]));
			}
			//topic
			dsc = FileUtil.readFileByLines("data/DpResults/50");
			//crd
			for(int cr: Wids)
			{
//				wk = Wkf.getWUsedId(cr);
//				crd += wk.getWcrd();
			}
			
			Developer dd = new Developer(dp.getAtrid(),
					dp.getAtrnm(),dsc.get(dp.getAtrid()-2001), crd, Wids);
			Sets.Dpr.add(dd);
			System.out.println(dd.getAtrid()+","+dd.getAtrnm()+","+dd.getAtrdes()+","+dd.getWid()+"\n");
			
		}

//		getDpTXT();
		System.out.println("wkfl done"+"\n");
	}
	
	public static void getDpTXT()
	{
		try {
            File file = new File("C:/Users/DJ/Desktop/data/dpdsc/dpnew.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data/dpdsc/dp.txt")));
            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 
                for (int j = 0; j < sheet.getColumns(); j++) { 
                    Cell cell = sheet.getCell(j, i); 
                    writer1.write(cell.getContents());
                    System.out.println(cell.getContents()+"\n"); 
                    if(j != sheet.getColumns()-1)
                    {
                    	writer1.write(",");
                    }
                } 
                writer1.write("\r\n");
                
            } 
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
