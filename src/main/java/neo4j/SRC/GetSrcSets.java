package neo4j.SRC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import nlp.lda.com.FileUtil;
import nlp.lda.main.Sim2Vec;
import entity.Developer;
import entity.Relation;
import entity.Service;
import entity.Sets;
import entity.Wkf;

public class GetSrcSets {
	public static void main(String[] args)
	{
		Sets.Src.clear();
		Sets.SEdg.clear();
		Sets.Srcrl.clear();
		Sets.SEdg.clear();
		getIK();
		getWKEntity();
		getSRCEntity();
//		
//		
//		System.out.println("wkfl done"+"\n");
//		
//		getEdge();
//		getneo4jSrc();
//		getneo4jSrcRL();
	}
	
	
	public static double getTimeValue(String date)
	{ 
		String[] hss = date.split("-");// 将字符串按逗号分割
		LocalDate end = LocalDate.of(2019, 10, 01);
		LocalDate start = LocalDate.of(Integer.parseInt(hss[0]), 
				Integer.parseInt(hss[1]),
				Integer.parseInt(hss[2]));
		return end.toEpochDay()-start.toEpochDay(); 
	}
	
	public static double setWkcrd(double dw, double vw)
	{
		double crd;
		crd = dw/5000 + vw/5000;
		return crd;
	}
	
	//从文件中获取服务
	public static void getSRCEntity()
	{
		List<Service> src = new ArrayList<Service>();
		
		try {
			//topic
			List<String>dsc = FileUtil.readFileByLines("data/SWDSC/lda_100.theta");
            File file = new File("C:/Users/DJ/Desktop/1/evaluation/srcsets.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
//            List<String>src = new ArrayList<String>();
            for (int i = 0; i < 2870; i++) 
            { // 循环打印Excel表中的内容 
//            	BufferedWriter writer1 = new BufferedWriter(
//    					new FileWriter(new File("C:/Users/DJ/Desktop/1/evaluation/dlpdsc/"+id+".txt")));
            	Service s = new Service(Integer.parseInt(sheet.getCell(0, i).getContents()), sheet.getCell(1, i).getContents(),
    					dsc.get(i), Double.parseDouble(sheet.getCell(2, i).getContents()), Integer.parseInt(sheet.getCell(3, i).getContents()), 
    					sheet.getCell(4, i).getContents());
    			src.add(s);
    			System.out.println("src done"+s.getSrcid()+"\n");
            }
            } catch (Exception e) {
	            e.printStackTrace();
	        }
//			System.out.println("src done"+"\n");
		List<Service> src2 = new ArrayList<Service>();
		for(Service sr : src)
		{
			List<Integer> Wid = new ArrayList<Integer>();
			String[] id = sr.getwids().split(",");// 将字符串按逗号分割
			for(int i = 0; i< id.length; i++)
			{
				Wid.add(Integer.parseInt(id[i]));
			}
			Service ss = new Service(sr.getSrcid(), sr.getName(), sr.getDes(), sr.getCrd(), sr.getDpid(), Wid);
			src2.add(ss);
			System.out.println("11111111111111111111");
					
		}
				
		List<Service> src3 = new ArrayList<Service>();
		for(Service sr2 : src2)
		{
			double ct = 0;
			for(int i : sr2.getWids())
			{
				if(Wkf.getWCTUsedId(i)>ct)
					ct = Wkf.getWCTUsedId(i);
			}
			Service sss = new Service(sr2.getSrcid(), sr2.getName(), sr2.getDes(), sr2.getCrd(), ct, sr2.getDpid(), sr2.getWids());
			src3.add(sss);
			System.out.println("22222222222222: " + sss.getCrd());
					
		}
		
		File fileA = new File("C:/Users/DJ/Desktop/1/evaluation/srcEntity.xls");
        if(fileA.exists()){
            //如果文件存在就删除
            fileA.delete();
        }
        try {
            fileA.createNewFile();
            //创建工作簿
            WritableWorkbook workbookA = Workbook.createWorkbook(fileA);            
            //创建sheet
            WritableSheet sheetA = workbookA.createSheet("sheet1", 0);
            Label label1 = null;  
            Label label2 = null;
            Label label3 = null;
            Label label4 = null;
            Label label5 = null;
            Label label6 = null;
            Label label7 = null;
            //设置列名
            for (int i = 0; i < 2870; i++) {
                label1 = new Label(0,i,String.valueOf((src3.get(i).getSrcid())));
                label2 = new Label(1,i,String.valueOf((src3.get(i).getName())));
                label3 = new Label(2,i,String.valueOf((src3.get(i).getDes())));
                label4 = new Label(3,i,String.valueOf((src3.get(i).getCrd())));
                label5 = new Label(4,i,String.valueOf((src3.get(i).getCtm())));
                label6 = new Label(5,i,String.valueOf((src3.get(i).getDpid())));
                label7 = new Label(6,i,String.valueOf((src3.get(i).getWids())));
                sheetA.addCell(label1); 
                sheetA.addCell(label2);
                sheetA.addCell(label3);
                sheetA.addCell(label4);
                sheetA.addCell(label5);
                sheetA.addCell(label6);
                sheetA.addCell(label7);
            }            
            
            workbookA.write();    //写入数据        
            workbookA.close();  //关闭连接
            

        } catch (Exception e) {
        }
		
//		//topic
//		List<String>dsc = FileUtil.readFileByLines("data/SWDSC/lda_100.theta");
//		for(Service sr3 : src3)
//		{
//			Service s4 = new Service(sr3.getSrcid(), sr3.getDpid(),
//					sr3.getCrd(), dsc.get(sr3.getSrcid()-3001), sr3.getSt());
//			Sets.Src.add(s4);
//			System.out.println("44444: " +s4.getDpid()+"; "+s4.getSrcid()+"; " +s4.getCrd()+"; "+s4.getDes());
//					
//		}
	}
	
	//从文件中获取workflow
	public static void getWKEntity()
	{
		// 初始结点集合
		List<String> dscStrings2 = new ArrayList<String>();
		// 读取目标文件，获取点的信息，生成点的集合
		dscStrings2 = FileUtil.readFileByLines("C:/Users/DJ/Desktop/1/evaluation/wk.txt");
		List<Wkf> wkf = new ArrayList<Wkf>();
		for (String tmp : dscStrings2) 
		{
			String[] pntArray = tmp.split(",");// 将字符串按逗号分割
			// 将数组中0,1位赋值给点的x,y
			Wkf w = new Wkf(Integer.parseInt(pntArray[0]), Double.parseDouble(pntArray[1]),
						Double.parseDouble(pntArray[2]), pntArray[3], 0);
			wkf.add(w);
		}
		System.out.println("wkf done"+"\n");
		for(Wkf wk : wkf)
		{
			Wkf ww = new Wkf(wk.getWid(), setWkcrd(wk.getWdw(),wk.getWvw()),
							getTimeValue(wk.getWst()));
			Sets.Wkfl.add(ww);
			System.out.println(ww.getWid()+","+ww.getWcrd()+"\n");
							
		}
		System.out.println("wkfl done"+"\n");
	}
	
	public static void getSrcTXT()
	{
		try {
            File file = new File("C:/Users/DJ/Desktop/data1116/src/src.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/src/src.txt")));
            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 
                for (int j = 0; j < sheet.getColumns(); j++) { 
                    Cell cell = sheet.getCell(j, i); 
                    writer1.write(cell.getContents());
                    if(j != sheet.getColumns()-1)
                    {
                    	writer1.write(",");
                    }
                } 
                writer1.write("\r\n");
                System.out.println(); 
            } 
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void getFriendsTXT()
	{
		try {
            File file = new File("C:/Users/DJ/Desktop/data1116/dprl/dprlId.xls"); // 创建文件对象
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/dprl/dprlId.txt")));
            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 
                for (int j = 0; j < sheet.getColumns(); j++) { 
                    Cell cell = sheet.getCell(j, i); 
                    writer1.write(cell.getContents());
                    if(j != sheet.getColumns()-1)
                    {
                    	writer1.write(",");
                    }
                } 
                writer1.write("\r\n");
                System.out.println(); 
            } 
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void getFR()
	{
		List<String> dscStrings2 = new ArrayList<String>();
		// 读取目标文件，获取点的信息，生成点的集合
		dscStrings2 = FileUtil.readFileByLines("C:/Users/DJ/Desktop/data1116/dprl/dprlId.txt");
		List<Relation> dprl = new ArrayList<Relation>();
		for (String tmp : dscStrings2) 
		{
			String[] pntArray = tmp.split(",");// 将字符串按逗号分割
			// 将数组中0,1位赋值给点的x,y
			Relation rl = new Relation(Integer.parseInt(pntArray[0]), Integer.parseInt(pntArray[1]),0);
			Sets.Dprl.add(rl);
		}
		System.out.println("dddddddddddd");
	}
	
	public static double getCT(double ct1, double ct2)
	{
		if(ct1<ct2)
			return ct1;
		else
			return ct2;
	}
	
//	public static void getInvokeTXT()
//	{
//		try {
//            File file = new File("C:/Users/DJ/Desktop/data1116/srcrl/invoke.xls"); // 创建文件对象
//            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
//            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
//            BufferedWriter writer1 = new BufferedWriter(
//					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/srcrl/invoke.txt")));
//            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 
//                for (int j = 0; j < sheet.getColumns(); j++) { 
//                    Cell cell = sheet.getCell(j, i); 
//                    writer1.write(cell.getContents());
//                    if(j != sheet.getColumns()-1)
//                    {
//                    	writer1.write(",");
//                    }
//                } 
//                writer1.write("\r\n");
//                System.out.println(); 
//            } 
//            writer1.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
	
	public static void getIK()
	{
		List<String> dscStrings2 = new ArrayList<String>();
		// 读取目标文件，获取点的信息，生成点的集合
		dscStrings2 = FileUtil.readFileByLines("C:/Users/DJ/Desktop/1/evaluation/invoke.txt");
		List<Relation> dprl = new ArrayList<Relation>();
		for (String tmp : dscStrings2) 
		{
			String[] pntArray = tmp.split(",");// 将字符串按逗号分割
			// 将数组中0,1位赋值给点的x,y
			Relation rl = new Relation(Integer.parseInt(pntArray[0]), Integer.parseInt(pntArray[1]),0);
			Sets.Srcrl.add(rl);
		}
		System.out.println("hhhhhhhhhhhhhh");
	}
	
	public static void getEdge()
	{
		int rid = 0;
		for(Service s1: Sets.Src)
		{
			for(Service s2: Sets.Src)
			{
				if(s1.getSrcid()==s2.getSrcid())
					continue;
				else
				{
					Sim2Vec t =new Sim2Vec(s1.getDes(),s2.getDes());
					double sim = t.sim();
					double ct = getCT(s1.getSt(),s2.getSt());
					double wgt=0;
					if(Relation.isIN(s1.getSrcid(),s2.getSrcid()))
					{
						wgt = sim + s1.getCrd()*s2.getCrd();
					}
					else
					{
						wgt = sim + s1.getCrd()*s2.getCrd()
								- ct*s1.getCrd()*s2.getCrd();
					}
					if(((s1.getDpid() == s2.getDpid())
							|| Relation.isFR(s1.getSrcid(),s2.getSrcid()))
							&& wgt>0)
					{
						Relation r = new Relation(s1.getSrcid(), s2.getSrcid(),
								(100-wgt)/200);
						Sets.SEdg.add(r);
						System.out.println(r.getSink()+"; "+r.getSource()+"; "+r.getWeight());
					}
					
				}
			}
		}
		try 
		{
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/srcrl/relation.txt")));
            for (Relation rl : Sets.SEdg) { // 循环打印Excel表中的内容 
                writer1.write(rl.getSource()+","+ rl.getSink()+","+rl.getWeight() + "\n"); 
                rid++;
                System.out.println(rid); 
            }
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void getneo4jSrc()
	{
		int rid = 0;
		try 
		{
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/srcneo4j/srcneo4j.txt")));
            for (Service s : Sets.Src) { // 循环打印Excel表中的内容 
            	
                writer1.write(s.getSrcid()+";"+s.getDpid()
                		+";"+s.getCrd()+";"+s.getDes()+ ";"+s.getSt());
                writer1.write("\r\n");
                rid++;
                System.out.println(rid); 
            }
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void getneo4jSrcRL()
	{
		int rid = 0;
		try 
		{
            BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/data1116/srcneo4j/srcrlneo4j.txt")));
            for (Relation r1 : Sets.SEdg) { // 循环打印Excel表中的内容 
      
                writer1.write(+r1.getSource()+";"+r1.getSink()
                		+";"+(r1.getWeight()));
                writer1.write("\r\n");
                rid++;
                System.out.println(rid); 
            }
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
