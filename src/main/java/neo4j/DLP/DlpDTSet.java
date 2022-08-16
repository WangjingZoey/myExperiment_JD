package neo4j.DLP;

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

public class DlpDTSet { 
	
	//生成src描述文档，将wkf相关内容加入src描述
			public static void main(String[] args) 
			{		
		        setDlpTDes();
			}
			
			//将src的des和wkf的des合并
			public static void setDlpTDes()
			{
				try{
					int id = 1;
					for (int i = 0; i < 175; i++) 
					{	
						String FileOut="C:/Users/DJ/Desktop/1/evaluation/dlpdes/"+id+".txt";
						BufferedWriter bw=new BufferedWriter(new FileWriter(FileOut));
						String FileName1="C:/Users/DJ/Desktop/1/evaluation/dlpdes/"+(i+1)+".txt";
						File file1=new File(FileName1);
						String FileName2="C:/Users/DJ/Desktop/1/evaluation/dlptag/"+(i+1)+".txt";
						File file2=new File(FileName2);
						String FileName3="C:/Users/DJ/Desktop/1/evaluation/dlpnm/"+(i+1)+".txt";
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
			
			
}
