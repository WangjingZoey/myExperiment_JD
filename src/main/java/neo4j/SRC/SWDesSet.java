package neo4j.SRC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Service;
import entity.Sets;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import nlp.lda.com.FileUtil;

public class SWDesSet {
  
	
	//生成src描述文档，将wkf相关内容加入src描述
			public static void main(String[] args) 
			{
//				测试
				List<Integer> swid = new ArrayList<Integer>();
//				swid.add(1);
//				swid.add(2);
//				List<String> snm = new ArrayList<String>();
//				snm.add("ab");
//				snm.add("cd");
//				List<String> wdes1 = new ArrayList<String>();
//				List<String> wdes2 = new ArrayList<String>();
//				wdes1.add("abc");
//				wdes1.add("abcd");
//				wdes2.add("ddafaab");
//				wdes2.add("cdssss");
//				srcnm.put(0, snm);
//				srcwid.put(0, swid);
//				wkfdes.put(0, wdes1);
//				wkfdes.put(1, wdes2);
				
				
				
//				getSWID();
//		        getWfkDes();//将wkf的txt描述存入hashmap		            
//		        getSrcNm();//将src的名称txt存入hashmap
//		        getSrcWkfDes();	
//		        getWfkTg();
//				getSrcWkfTg();
//				
//				setSrcDes();
		        setSWDes();
			}
			
			//将src的nm存入hashmap
			public static void getSrcNm()
			{
		        List<String> srcNm = new ArrayList<String>();
		        List<String> nms = new ArrayList<String>();
		        
		        for(int i=0; i<2870; i++)
		        {
		        	srcNm = FileUtil.readFileByLines("C:/Users/DJ/Desktop/1/evaluation/srcnm/"+(i+1)+".txt");
		        	
		        	if(srcNm != null && !srcNm.isEmpty())
		        	{
		        		
//		        		System.out.println(i+":"+wkfDes.get(0));
		        		nms = Arrays.asList(srcNm.get(0).split("\\s+"));
//		        		for (int j = 0; j < nms.size(); j++) //测试
//		        		{
//		        			System.out.println(i+":"+j+":"+nms.get(j));
//		        		}
		        		Sets.srcnm.put(i, nms);
		  	
		        	}

	        
		        }
			}
			
			//将wkf的描述存入hashmap
			public static void getWfkDes()
			{
//		             //数据采用的哈希表结构
		        List<String> wkfDes = new ArrayList<String>();
		        List<String> destxt = new ArrayList<String>();  
		        for(int i=0; i<1058; i++)
		        {
		        	wkfDes = FileUtil.readFileByLines("C:/Users/DJ/Desktop/1/evaluation/wkfdes/"+(i+1)+".txt");
		        	if(wkfDes != null && !wkfDes.isEmpty())
		        	{
		        		
//		        		System.out.println(i+":"+wkfDes.get(0));
		        		destxt = Arrays.asList(wkfDes.get(0).split("\\."));
		        		for (int j = 0; j < destxt.size(); j++) {
		        			System.out.println(i+":"+j+":"+destxt.get(j));
		        		}
		        		Sets.wkfdes.put(i, destxt);
		        		
//		        		wkfdes.get(i).forEach(s ->System.out.print("wkfdes:"+s+" "));
		        		
		        	}
		        }
		        
			}
			
			//将wkf的tag存入hashmap
			public static void getWfkTg()
			{
//		             //数据采用的哈希表结构
		        List<String> wkfTg = new ArrayList<String>();
		        List<String> tgtxt = new ArrayList<String>();  
		        for(int i=0; i<1058; i++)
		        {
		        	wkfTg = FileUtil.readFileByLines("C:/Users/DJ/Desktop/1/evaluation/wkftag/"+(i+1)+".txt");
		        	if(wkfTg != null && !wkfTg.isEmpty())
		        	{
		        		
//		        		System.out.println(i+":"+wkfTg.get(0));
		        		tgtxt = Arrays.asList(wkfTg.get(0).split("\\s+"));
//		        		for (int j = 0; j < tgtxt.size(); j++) {
//		        			System.out.println(i+":"+j+":"+tgtxt.get(j));
//		        		}
		        		Sets.wkftg.put(i, tgtxt);	
		        	}
		        }
		        
			}
			
			//将src相关的wkf的id存入hashmap
			static public void getSWID()
			{
				
				try {
					File file = new File("C:/Users/DJ/Desktop/1/evaluation/srcwkfid.xls"); // 创建文件对象
		            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
		            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
		            List<String>src = new ArrayList<String>();
		            
		          
			        List<String>swid = new ArrayList<String>();
			        
			        for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 
			            	
		            	for (int j = 0; j < sheet.getColumns(); j++) { 
		                    Cell cell = sheet.getCell(j, i); 
		                    if(cell.getContents().isEmpty())
		                    {
		                    	break;
		                    }
		                    String wid = cell.getContents();
		                    swid = Arrays.asList(wid.split(","));
		        					
		                    Sets.srcwid.put(i, swid.stream().map(Integer::parseInt).collect(Collectors.toList()));
		                    
//		                    srcwid.get(i).forEach(s ->System.out.print(s+" "));
		                    System.out.println(i+":"+Sets.srcwid.get(i));
		                }
	 
		            } 
					
				}catch (Exception e) {
		            e.printStackTrace();
		        }
		        
			}
			
			//根据src的nm从wkf的描述中选取相关的内容
			public static void getSrcWkfDes()
			{
			
				for(int i = 0; i<Sets.srcwid.size(); i++)
				{
					List<String> wdes = new ArrayList<String>();
					for(int j=0; j<Sets.srcwid.get(i).size(); j++)
					{
						for(int k=0; k<Sets.srcnm.get(i).size(); k++)
						{
							if(Sets.wkfdes.get(Sets.srcwid.get(i).get(j)-1)!=null)
							{
								for(int m =0; m<Sets.wkfdes.get(Sets.srcwid.get(i).get(j)-1).size(); m++)
								{
//									System.out.println("wkfdes.get(srcwid.get(i).get(j)-1).get(m):"+wkfdes.get(srcwid.get(i).get(j)-1).get(m));
									if(Sets.wkfdes.get(Sets.srcwid.get(i).get(j)-1).get(m).contains(Sets.srcnm.get(i).get(k)))
									{
										wdes.add(Sets.wkfdes.get(Sets.srcwid.get(i).get(j)-1).get(m));
									}
								}
							}
							
						}
					}
					Sets.swdes.put(i, wdes);
					System.out.println("swdes.get(i):"+Sets.swdes.get(i));
					

				}
			}
			
			//根据src的nm从wkf的tag中选取相关的内容
			public static void getSrcWkfTg()
			{
			
				for(int i = 0; i<Sets.srcwid.size(); i++)
				{
					List<String> wtg = new ArrayList<String>();
					for(int j=0; j<Sets.srcwid.get(i).size(); j++)
					{
						for(int k=0; k<Sets.srcnm.get(i).size(); k++)
						{
							if(Sets.wkftg.get(Sets.srcwid.get(i).get(j)-1)!=null)
							{
								for(int m =0; m<Sets.wkftg.get(Sets.srcwid.get(i).get(j)-1).size(); m++)
								{
//									System.out.println("wkfdes.get(srcwid.get(i).get(j)-1).get(m):"+wkfdes.get(srcwid.get(i).get(j)-1).get(m));
									if(Sets.wkftg.get(Sets.srcwid.get(i).get(j)-1).get(m).contains(Sets.srcnm.get(i).get(k)))
									{
										wtg.add(Sets.wkftg.get(Sets.srcwid.get(i).get(j)-1).get(m));
									}
								}
							}
							
						}
					}
					removeDuplicate(wtg);
					Sets.swtg.put(i, wtg);
					System.out.println("swdes.get(i):"+Sets.swtg.get(i));
					

				}
				
//				setSrcDes();
			}
			
			//根据wkf的描述和tg构建src描述
			public static void setSrcDes()
			{
				try {
					int id = 1;
					
					for(int i = 0; i<2870; i++)
					{
						String des = "";
						BufferedWriter writer1 = new BufferedWriter(
		    					new FileWriter(new File("C:/Users/DJ/Desktop/1/evaluation/swdes/"+id+".txt")));
						
						System.out.println("swdes.get(i).size:"+Sets.swdes.get(i).size());
						for(int j=0; j<Sets.swdes.get(i).size();j++)
						{
							des = des + Sets.swdes.get(i).get(j);
							
						}
						for(int k=0; k<Sets.swtg.get(i).size();k++)
						{
							des = des + Sets.swtg.get(i).get(k);
							
						}
						System.out.println("des:"+des);
						writer1.write(des);
						id++;
						writer1.close();
					}
                
					
				}catch (Exception e) {
		            e.printStackTrace();
		        }
				
			}
			
			//将src的des和wkf的des合并
			public static void setSWDes()
			{
				try{
					int id = 1;
					for (int i = 0; i < 2870; i++) 
					{	
						String FileOut="C:/Users/DJ/Desktop/1/evaluation/des/"+id+".txt";
						BufferedWriter bw=new BufferedWriter(new FileWriter(FileOut));
						String FileName1="C:/Users/DJ/Desktop/1/evaluation/srcdes/"+(i+1)+".txt";
						File file1=new File(FileName1);
						String FileName2="C:/Users/DJ/Desktop/1/evaluation/swdes/"+(i+1)+".txt";
						File file2=new File(FileName2);
						String FileName3="C:/Users/DJ/Desktop/1/evaluation/srcnm/"+(i+1)+".txt";
						File file3=new File(FileName3);
						
						BufferedReader br = new BufferedReader(new FileReader(file1));
						String line;
						while((line=br.readLine())!=null) 
						{
							bw.write(line);
							bw.newLine();
						}
						br.close();
						BufferedReader br2 = new BufferedReader(new FileReader(file2));
						String line2;
						while((line2=br2.readLine())!=null) 
						{
							bw.write(line2);
							bw.newLine();
						}
						br2.close();
						BufferedReader br3 = new BufferedReader(new FileReader(file3));
						String line3;
						while((line3=br3.readLine())!=null) 
						{
							bw.write(line3);
							bw.newLine();
						}
						br3.close();
						bw.close();
						id++;
						System.out.println("id:"+id);
					}
					
					
				}catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
			//list去重
			public   static   List  removeDuplicate(List list)  {       
				  for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {       
				      for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {       
				           if  (list.get(j).equals(list.get(i)))  {       
				              list.remove(j);       
				            }        
				        }        
				      }        
				    return list;       
				}
}
