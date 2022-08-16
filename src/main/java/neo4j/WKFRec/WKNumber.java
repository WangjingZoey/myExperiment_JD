package neo4j.WKFRec;

import java.util.ArrayList;
import java.util.List;

import nlp.lda.com.FileUtil;

public class WKNumber {
	public static void main(String[] args)
	{
		// 初始结点集合
		List<String> nodeStrings = new ArrayList<String>();
		// get Nodes
		// 读取目标文件，获取点的信息，生成点的集合
		nodeStrings = FileUtil.readFileByLines("data/wknumber.txt");
		double sum = 0;
		int n = 0;
		for (String tmp : nodeStrings) 
		{
			String[] pntArray = tmp.split(",");// 将字符串按逗号分割
			// 将数组中0,1位赋值给点的x,y
			
			n = pntArray.length;
			sum+=n;
		
			System.out.println(n);
			
		}
		System.out.println(sum/2876);
//		int num[] = {0};
//		for(int k = 0; k<20;k++)
//		{
//			num[wid[k]-1]++;
//		}
//		for(int m=0;m<100;m++)
//		{
//			System.out.println((m+1)+": "+num[m]);
//		}
		
	}
}
