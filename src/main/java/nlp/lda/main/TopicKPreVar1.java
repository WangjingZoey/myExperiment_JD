package nlp.lda.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Synthesizer;

import entity.Sets;
import nlp.lda.com.FileUtil;

public class TopicKPreVar1 {
	public static int docNum = 2870;
	public static int  K = 43;
//	public static int wordNum = 1284;
	public static int wordNum = 4331;
	public static double[][] tw = new double[K][wordNum];
	public static double[][] dt = new double[docNum][K];
	
	
	public static void main(String[] args) 
	{
		System.out.println("per:"+getPer());
	}
	
	/**
	 * 计算lda困惑度，找出最佳topic数量
	 * @param tw_list是topic word矩阵(.phi文件)的每一行
	 * @param dt_list是document topic 矩阵（.theta）的每一行
	 * @param as_list是 .tassign文件的每一行
	 * */
	static public double getPer(){
		
		List<String> tw_list = new ArrayList<String>();
		List<String> dt_list = new ArrayList<String>();
		List<String> as_list = new ArrayList<String>();
		
		double perp = 0;
		double sum_ln_pt = 0;
		double sum_t = 0;		
		double sum_value = 0;
		
		
		tw_list = FileUtil.readFileByLines("data/SWDSC/lda_100.phi");
		dt_list = FileUtil.readFileByLines("data/SWDSC/lda_100.theta");
		as_list = FileUtil.readFileByLines("data/SWDSC/lda_100.tassign");
	
//		tw_list = FileUtil.readFileByLines("data/DDSC/lda_100.phi");
//		dt_list = FileUtil.readFileByLines("data/DDSC/lda_100.theta");
//		as_list = FileUtil.readFileByLines("data/DDSC/lda_100.tassign");
		
		for(int i=0;i<tw_list.size();i++)
		{
			String tw_arr[] = tw_list.get(i).split("\\s+");
			for(int j=0; j<tw_arr.length;j++)
			{
				tw[i][j] = Double.parseDouble(tw_arr[j]);
			}
		}
		
		for(int i=0;i<dt_list.size();i++)
		{
			String dt_arr[] = dt_list.get(i).split("\\s+");
			for(int j=0; j<dt_arr.length;j++)
			{
				dt[i][j] = Double.parseDouble(dt_arr[j]);
			}
		}
		
		
//		ArrayList<ArrayList<Integer>> wid = new ArrayList<ArrayList<Integer>>();
		
		
		for(int i=0;i<as_list.size();i++)
		{
			String ass_arr[] = as_list.get(i).split("\\s+");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for(int j=0; j<ass_arr.length;j++)
			{
				
				String tz[] = ass_arr[j].split(":");
				ids.add(Integer.parseInt(tz[0]));
//				System.out.println(Integer.parseInt(tz[0]));
//				wid.add(i, Integer.parseInt(tz[0]));
//				wid[i][j] = Integer.parseInt(tz[0]);
			}
			Sets.wid.add(i, ids);
//			System.out.println(wid.get(i).size());

		}
		
		
		System.out.println(Sets.wid.size());
		try {
			BufferedWriter writer1 = new BufferedWriter(
					new FileWriter(new File("C:/Users/DJ/Desktop/1/evaluation/ldatest.txt")));
			for(int i =0; i<Sets.wid.size(); i++)
			{
//				System.out.println(Sets.wid.get(i).size());
				sum_t += Sets.wid.get(i).size(); // 那个 ducument 有多少 word
//				System.out.println(sum_t);
				double pt = 0.0;//用来加总 每一个document的 log概率
				for(int j=0; j<Sets.wid.get(i).size(); j++)
				{
					double pz = 0.0;
					for(int k=0; k<tw_list.size(); k++)
					{
						double p_tz = 0.0;
						int id = Sets.wid.get(i).get(j);
						p_tz = tw[k][id]; // 第 j 个topic 第 t_id个词
//						System.out.println(j+":"+p_tz);
						double p_zd = 0.0;
						p_zd = dt[i][k];//第i篇document 第j个topic		
						writer1.write(i+"== "+j);
						writer1.write("\r\n");
//		                
		               
						pz += p_tz*p_zd;
					}
					pt += Math.log(pz);	
				}
				sum_ln_pt += pt;
			}
			 writer1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(int i = 0; i < as_list.size(); i++){///每一行 对应一个 document
// 
//			String as_arr[] = as_list.get(i).split("\\s+");
//			sum_t += as_arr.length; // 那个 ducument 有多少 word
//			double pt = 0.0;//用来加总 每一个document的 log概率
//			for(String as:as_arr)
//			{//对于每一个词
//				if(!as.isEmpty())
//				{
//					//System.out.println("as = " + as);
//					double pz = 0.0;
//					String tz_arr[] = as.split(":");
//					int t_id = Integer.parseInt(tz_arr[0]);// t_id 是 词 的id
//					//int z_id = Integer.parseInt(tz_arr[1]);
//					for(int j = 0; j < tw_list.size(); j++)
//					{ //遍历topics 
//						double p_tz = 0.0;
//						String tw_arr[] = tw_list.get(j).split("\\s+");
//						p_tz = Double.parseDouble(tw_arr[t_id]);// 第 j 个topic 第 t_id个词
//						double p_zd = 0.0;
//						String dt_arr[] = dt_list.get(i).split("\\s+");
//						p_zd = Double.parseDouble(dt_arr[j]);//第i篇document 第j个topic					
//						pz += p_tz*p_zd;				
//					}//end for(int j = 0; j < tz_list.size(); j++)
//					pt += Math.log(pz);	
//				}
//				
//				
//			}//end for(String as:as_arr)
//			sum_ln_pt += pt;
//		}//end for i
		//System.out.println("sum_ln_pt = " + sum_ln_pt);
		//System.out.println("sum_t = " + sum_t);
		sum_ln_pt = 0-sum_ln_pt;
		System.out.println(sum_t);
		perp = Math.exp(sum_ln_pt/sum_t);
//		perp = Math.pow(Math.E, (-sum_ln_pt/sum_t));
		return perp;
	}

}
