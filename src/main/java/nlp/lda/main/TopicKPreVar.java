package nlp.lda.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.sound.midi.Synthesizer;

public class TopicKPreVar {
	
	//文档与主题概率的文件路径
	private static String z_d = "data/SWDSC/lda_100.theta";
	//文档主题文件中，文档数
	private static int docNum = 2870;
	//文档主题文件中，话题数
	private static int K = 400;
	
	//主题与单词概率的文件路径
	private static String w_z = "data/SWDSC/lda_100.phi";
	
	//主题单词概率文件中，单词数
	private static int wordNum = 4331;
	
	//数字文档的路径doc_wids
	private static String doc_widfile = "data/SWDSC/lda_100.tassign";
	
	//计算困惑度
	public static double getPreplexity() throws Exception{
		//读取每篇数字文档信息
		ArrayList<ArrayList<Integer>> doc_wids=new ArrayList<ArrayList<Integer>>();
		doc_wids=getdoc_wids(doc_widfile);
		//System.out.println(doc_wids.size());
		//读取单词下标对应信息
		//String[] index_word = new String[wordNum];
		//index_word=getvaca(voca);
		//System.out.println(index_word[0]);
		//读取文档主题信息
		double[][] theta=new double [docNum][K];
		theta=getz_d(z_d);
		//System.out.println(theta[0][0]);
		//读取主题单词信息
		double [][] phi=new double[K][wordNum];
		phi=getw_z(w_z);
		//System.out.println(phi[0][0]);
		
		double count=0;
		int i=0;
		//统计数字文档中的单词数目
		int N=0;
		//遍历每一个文档
		for(ArrayList<Integer> list:doc_wids){
			double mul=0;
			N=N+list.size();
			//System.out.println(N);
			//遍历每篇文档中每个数字单词
			for(int j=0;j<list.size();j++){
				
				double sum=0;
				//每个数字单词，其实数字也是在主题-单词中就是各异的下标
				//每个单词在不同话题下都有值，进行log累和
				int index=list.get(j);
				for(int k=0;k<K;k++){
					sum=sum+phi[k][index]*theta[i][k];
				}
				//一篇文档中的所有单词都累和
				mul=mul+Math.log(sum);
			}
			count=count+mul;
			i++;
		}
		System.out.println(N);
		count=0-count;
		double P=Math.exp(count/N);
		System.out.println("Perplexity:" + P);
		return P;
	}
	
	//计算Perplexity-Var
	public static double getVar() throws Exception{
		//读取主题-词的信息
		double [][] phi=new double[K][wordNum];
		phi=getw_z(w_z);
		//System.out.println(phi[0][0]);
		
		//得到词的均值矩阵,并保存在数组中
		double[] meanWord=new double[wordNum];
		for(int i=0;i<wordNum;i++){
			double meanVal=0;
			for(int j=0;j<K;j++){
				meanVal=meanVal+phi[j][i];
				//System.out.println(phi[j][i]+"--------"+meanVal);
			}
			meanWord[i]=meanVal/K;
			System.out.println(i+"==="+meanWord[i]);
		}
		
		//将每个话题下的JS距离进行累和
		double sum=0;
		//每个主题-单词概率都和矩阵概率进行JS距离计算
		for(int k=0;k<K;k++){
			//进行该话题下的JS计算
			//得到JS距离计算中的M=1/2（P+Q）
			double[] M=new double[wordNum];
			for(int i=0;i<wordNum;i++){
				double MVal=0.5*(phi[k][i]+meanWord[i]);
				M[i]=MVal;
//				System.out.println(k+"-->"+phi[k][i]+"----"+meanWord[i]+"----"+MVal+"----"+M[i]);
			}
			double each_topic_JS=0.5*getKL(phi[k],M)+0.5*getKL(meanWord,M);
			//计算公式中需要将距离平方然后累和
			double JSPow=each_topic_JS*each_topic_JS;
			sum=sum+JSPow;
			
		}
		double Var=sum/K;
		
		return Var;
	}
	//KL距离计算
	public static double getKL(double[] p, double[] q){
		double sum=0;
		for(int i=0;i<p.length;i++){
			double temp=p[i]*Math.log(p[i]/q[i]);
			sum=sum+temp;
		}
		return sum;
	}
	
	//=====================================================================================
	//将文件数据读入数组中，先读入文档-主题文件
	public static double[][] getz_d(String z_dfile) throws Exception{
		double[][] theta=new double [docNum][K];
		//读文件
		FileReader fr = new FileReader(z_dfile);
		BufferedReader br = new BufferedReader(fr);
		String cellinfo="";
		//文档下标从0开始数的
		int i=0;
		while ((cellinfo = br.readLine()) != null) {
			
			//System.out.println(i+"-----"+cellinfo);
			String[] values=cellinfo.split("\\s+");
			int count=values.length;
			for(int j=0;j<count;j++){
				theta[i][j]=Double.parseDouble(values[j]);
			}
			i++;
		}
		
		return theta;
	}
	//读主题-词文件
	public static double[][] getw_z(String w_zfile) throws Exception{
		double [][] phi=new double[K][wordNum];
		//读文件
		FileReader fr = new FileReader(w_zfile);
		BufferedReader br = new BufferedReader(fr);
		String cellinfo="";
		int i=0;		
		while ((cellinfo = br.readLine()) != null) {
			String[] values=cellinfo.split("\\s+");
			for(int j=0;j<values.length;j++){
				phi[i][j]=Double.parseDouble(values[j]);
			}
			i++;
		}
		return phi;
	}

	// 读取各异单词的下标-单词对应表到数组中
//	public static String[] getvaca(String vocafile) throws Exception {
//		String[] index_word = new String[wordNum];
//		// 读文件
//		FileReader fr = new FileReader(vocafile);
//		BufferedReader br = new BufferedReader(fr);
//		String cellinfo = "";
//		while ((cellinfo = br.readLine()) != null) {
//			String[] values = cellinfo.split("\t");
//			int index=Integer.parseInt(values[0]);
//			index_word[index]=values[1];
//		}
//		return index_word;
//	}
//	
	//读数字文档数据
	public static ArrayList<ArrayList<Integer>> getdoc_wids(String doc_widfile) throws Exception{
		ArrayList<ArrayList<Integer>> doc_wids=new ArrayList<ArrayList<Integer>>();
		// 读文件
		FileReader fr = new FileReader(doc_widfile);
		BufferedReader br = new BufferedReader(fr);
		String cellinfo = "";
		int j =1;
		while (j<2871) {
			cellinfo = br.readLine();
			String[] values=cellinfo.split("\\s+");
			ArrayList<Integer> eachDoc=new ArrayList<Integer>();
			for(int i=0;i<values.length;i++){
				String tz_arr[] = values[i].split(":");
//				System.out.println(j+":"+tz_arr[0].trim());
				eachDoc.add(Integer.parseInt(tz_arr[0]));
			}
			j++;
			doc_wids.add(eachDoc);
		}
		return doc_wids;		
	}
	
	
	public static void main(String[] args) throws Exception {
		//double[][] theta=new double [docNum][K];
		//theta=getz_d(z_d);
//		double [][] phi=new double[K][wordNum]`;
//		phi=getw_z(w_z);
//		for(int i=0;i<K;i++){
//			for(int j=0;j<wordNum;j++){
//				System.out.print(i+"--"+phi[i][j]);
//			}
//			System.out.println();
//		}
//		String[] index_word = new String[wordNum];
//		index_word=getvaca(voca);
//		for(int j=0;j<wordNum;j++){
//			System.out.println(j+"--"+index_word[j]);
//		}
//		ArrayList<ArrayList<Integer>> doc_wids=new ArrayList<ArrayList<Integer>>();
//		doc_wids=getdoc_wids(doc_widfile);
//		System.out.println(doc_wids.get(1556));
		
		//以上是测试功能
		
//		double[] p={0.5,0.5};
//		double[] q={0.25,0.75};
//		double[] L={0.5,0.5};
//		System.out.println(getKL(p,L));
		
		
		double P=getPreplexity();
		double Var=getVar();
		double getPerplexity_Var=P/Var;
		System.out.println(P+"===="+Var+"====="+getPerplexity_Var);
		System.out.println(P);
	}

}
