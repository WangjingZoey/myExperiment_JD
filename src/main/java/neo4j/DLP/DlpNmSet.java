package neo4j.DLP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import neo4j.dataprocessing.PorterStemmer;
import neo4j.dataprocessing.Stopwords;
//import data.SrcDscNm;
import entity.Service;
import entity.Sets;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DlpNmSet {
	//生成dlp的名称描述文档
			public static void main(String[] args) 
			{
 				try {
					int id = 1;
		            File file = new File("C:/Users/DJ/Desktop/1/evaluation/dlpnm.xls"); // 创建文件对象
		            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
		            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） 
//		            List<String>src = new ArrayList<String>();
		            for (int i = 0; i < 175; i++) { // 循环打印Excel表中的内容 
		            	BufferedWriter writer1 = new BufferedWriter(
		    					new FileWriter(new File("C:/Users/DJ/Desktop/1/evaluation/dlpnm/"+id+".txt")));
		  		            	
		            	for (int j = 0; j < sheet.getColumns(); j++) { 
		                    Cell cell = sheet.getCell(j, i); 
		                    if(cell.getContents().isEmpty())
		                    {
		                    	break;
		                    }		              
		                    
		                    String spNm = SpltNm(cell.getContents());
		                    
		                    String[] wordarr = spNm.split("\\s+");
		                    ArrayList<String> words = new ArrayList<String>();
		                    for (int k = 0; k < wordarr.length; k++) {
		                        words.add(wordarr[k]);
		                    }
		                    String textremoveStopword = "";
		                    for (int n = 0; n < words.size(); n++) {
		                        textremoveStopword += words.get(n)+" ";
		                    }
//		                    PorterStemmer.stem(textremoveStopword);
		                    writer1.write(textremoveStopword);
		                    
		                }
	            	
		                id++;
		                writer1.write("\r\n");	                
		                writer1.close();
						
		            } 	            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				System.out.println(SpltNm("done"));
			}
			
			public static String SpltNm(String s) 
			{
				String regEx="[_]";
				String aa = " ";//这里是将特殊字符换为aa字符串,""代表直接去掉
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(s);//这里把想要替换的字符串传进来
				String newString = m.replaceAll(aa).trim(); //将替换后的字符串存在变量newString中
				
				
				return newString.replaceAll(
	                      String.format("%s",
	                         "(?<=[a-z])(?=[A-Z])"
//	                         "(?<=[A-Z])(?=[A-Z])"
//	                         "(?<=[^A-Z])(?=[A-Z])",
//	                         "(?<=[A-Za-z])(?=[^A-Za-z])"
	                      ),
	                      " "
	                   );
				 
			 }
}