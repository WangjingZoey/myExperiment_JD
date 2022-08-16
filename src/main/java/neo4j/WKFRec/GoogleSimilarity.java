package neo4j.WKFRec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class GoogleSimilarity {
	final static int k = 8;
	private static String url = "jdbc:mysql://127.0.0.1/bioinformatics";
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private static String sql = null;
	private static String count = "-1"; 
//	static {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static double NGD(long x, long y, long xy){//归一化谷歌距离
		double ngd = 0;
		if (x==0||y==0||xy==0) {
			ngd= 1;
		}else if(x==-1||y==-1||xy==-1||x==-2||y==-2||xy==-2){
			ngd = -1;
		}else {
			ngd = (2/Math.PI)*Math.atan((Math.max(Math.log(x),Math.log(y))-Math.log(xy))/(Math.log(30)+12-Math.min(Math.log(x),Math.log(y))));
			if(ngd <0) ngd = -ngd;
		}
		return ngd;
	}
	
	public static long search(final String key) throws InterruptedException{//在谷歌查找包含关键词的网页
		if(key==""||key==null){
			return 0;
		}
		count = "-1";
		sql = "select num from googledata where k='"+key+"'";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getString("num");
				resultSet.close();
				statement.close();
			}else {
				while (count.equals("-1")||count.equals("-2")) {
					System.out.println("Searching "+key);
					ExecutorService executor = Executors.newSingleThreadExecutor();
					FutureTask<String> future =
							new FutureTask<String>(new Callable<String>() {//Use Callable interface as the structural parameters.
								public String call() throws InterruptedException {
									String k = key.replace("_", "%20");
									String num = "-1";
									HttpClient httpclient=new DefaultHttpClient();
									HttpGet httpget=new HttpGet("http://www.google.com.hk/search?q="+k);
									httpget.setHeader("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, */*");
									httpget.setHeader("Accept-Language", "zh-cn");
									httpget.setHeader("User-Agent", "IE/10.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
							
									HttpResponse response;
									try {
										response = httpclient.execute(httpget);
										HttpEntity entity=response.getEntity();
										if(entity!=null){
											BufferedReader reader=new BufferedReader(
													new InputStreamReader(entity.getContent(),"UTF-8"));
									        String s = new String(reader.readLine());
									        if(s.split("找到约").length>1){
									        	System.out.println(s);
									        	num = s.split("找到约 ")[1].split(" 条")[0];
									        }else if(s.split("获得 ").length>1){
									        	System.out.println(s);
									        	num = s.split("获得 ")[1].split(" 条")[0];
									        }else if(s.split("找不到").length>1){
									        	System.out.println(s);
									        	num = "0";
									        }else{
									        	num  = "-1";
									        	System.out.println("In Unknown Reason!");
									        	System.out.println(s);
									        }
									        reader.close();
										}
									} catch (ClientProtocolException e) {
										num = "-2";
										System.out.println("Connection lost when searching "+key+".");
									} catch (IOException e) {
										num = "-2";
										System.out.println("Connection lost when searching "+key+".");
									}
									httpclient.getConnectionManager().shutdown();
									return num;
					       }});
					executor.execute(future);
					
					try {
						count = future.get(100000, TimeUnit.MILLISECONDS); //Get result, and set the timeout execution time (100s)。
					}catch (InterruptedException e) {
						System.out.println("The main thread is interrupted when waiting for the results.");
						count = "-2";
					}catch (ExecutionException e) {
						System.out.println("Main thread waiting for results, but the calculation throws an exception.");
						count = "-2";
					}catch (TimeoutException e) {
						System.out.println("With the main thread timeout when waiting for results, interrupted task threads.");
						count = "-2";
					}finally {
						executor.shutdown();
					}
					if(count.equals("-1")){
						System.out.println("Try again after 1 minute.");
						Thread.sleep(60 * 1000L);
					}else if (count.equals("-2")) {
						System.out.println("Try again after 10 seconds.");
						Thread.sleep(10 * 1000L);
					}else {
						System.out.println("Searching result of "+key+":"+count);
						sql = "replace into googledata(k, num)values('"+ key+ "','" +count + "')";
						statement = connection.prepareStatement(sql);
						statement.executeUpdate();
						statement.close();
					}
				}
			}
		} catch (SQLException e) {
			count = "-2";
			System.out.println("A database error occurred!");
			return search(key);
		}
		return Long.parseLong(count.replace(",", ""));
	}
	
	public static long search(String key1, String key2) throws InterruptedException{
		final String key = key1+"_"+key2;
		count = "-1";
		sql = "select num from googledata where k='"+key+"'";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getString("num");
				resultSet.close();
				statement.close();
			}else {
				while (count.equals("-1")||count.equals("-2")) {
					System.out.println("Searching "+key);
					ExecutorService executor = Executors.newSingleThreadExecutor();
					FutureTask<String> future =
							new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数
								public String call() throws InterruptedException {
									String k = key.replace("_", "%20");
									String num = "-1";
									HttpClient httpclient=new DefaultHttpClient();
									HttpGet httpget=new HttpGet("http://www.google.com.hk/search?q="+k);
									httpget.setHeader("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, */*");
									httpget.setHeader("Accept-Language", "zh-cn");
									httpget.setHeader("User-Agent", "IE/10.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
									HttpResponse response;
									try {
										response = httpclient.execute(httpget);
										HttpEntity entity=response.getEntity();
										if(entity!=null){
											BufferedReader reader=new BufferedReader(
													new InputStreamReader(entity.getContent(),"UTF-8"));
									        String s = new String(reader.readLine());
									        System.out.println(s);
									        if(s.split("找到约").length>1){
									        	num = s.split("找到约 ")[1].split(" 条")[0];
									        }else if(s.split("获得 ").length>1){
									        	num = s.split("获得 ")[1].split(" 条")[0];
									        }else if(s.split("找不到").length>1){
									        	num = "0";
									        }else{
									        	num  = "-1";
									        	System.out.println("In Unknown Reason!");
									        	//System.out.println(s);
									        }
									        reader.close();
										}
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										num = "-2";
										System.out.println("Connection lost when searching "+key+".");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										num = "-2";
										System.out.println("Connection lost when searching "+key+".");
									}
									httpclient.getConnectionManager().shutdown();
									return num;
					       }});
					executor.execute(future);
					
					try {
						count = future.get(100000, TimeUnit.MILLISECONDS); ////Get result, and set the timeout execution time (100s)。
					}catch (InterruptedException e) {
						System.out.println("The main thread is interrupted when waiting for the results.");
						count = "-2";
					}catch (ExecutionException e) {
						System.out.println("Main thread waiting for results, but the calculation throws an exception.");
						count = "-2";
					}catch (TimeoutException e) {
						System.out.println("With the main thread timeout when waiting for results, interrupted task threads.");
						count = "-2";
					}finally {
						executor.shutdown();
					}
					if(count.equals("-1")){
						System.out.println("Try again after 1 minute.");
						Thread.sleep(60 * 1000L);
					}else if (count.equals("-2")) {
						System.out.println("Try again after 10 seconds.");
						Thread.sleep(10 * 1000L);
					}else {
						System.out.println("Searching result of "+key+":"+count);
						sql = "replace into googledata(k, num)values('"+ key+ "','" +count + "')";
						statement = connection.prepareStatement(sql);
						statement.executeUpdate();
						statement.close();
					}
				}
			}
		} catch (SQLException e) {
			count = "-2";
			
			System.out.println(key);Thread.sleep(60 * 1000L);
			System.out.println("A database error occurred!");
			return search(key1,key2);
		}
		return Long.parseLong(count.replace(",", ""));
	}
	
	public static double calculateGoogleDistance(String s1, String s2) throws InterruptedException{
		double d = 0;
		if(s1==null||s2==null||s1.equals("")||s2.equals("")){
			d = 1;
		}else if (s1.equalsIgnoreCase(s2)) {//忽略大小写对比
			d = 0;
		}else {
			
			long dxy = search(s1,s2);
			while (dxy==-2||dxy==-1) {
				System.gc();//提醒或告诉虚拟机，希望进行一次垃圾回收
				if (dxy==-2) {
					System.out.println("Try again after 10 seconds.");
					Thread.sleep(10 * 1000L);
					dxy = search(s1,s2);
				}else if (dxy==-1) {
					System.out.println("Try again after 3 minutes.");
					Thread.sleep(3 * 60 * 1000L);
					dxy = search(s1,s2);
				}
			}
			d = NGD(search(s1), search(s2), dxy);
			if (d==-1) {
				return -1;
			}
		}
		return d;
	}
	
	public static double calculateSimilarity(String s1, String s2) throws InterruptedException{
		String[] words1 = stringPartition(s1);
		String[] words2 = stringPartition(s2);
		int count = words1.length+words2.length+4;
		int[][] flows = new int[count][count];
		double[][] costs = new double[count][count];
		int sameWordCount = 0;
		for (int i = 1; i <= words1.length; i++) {
			flows[0][i] = 1;
			for (int j = words1.length+2; j <= words1.length+words2.length+1; j++) {
				flows[i][j] = 1;
				flows[j][count-1] = 1;
				costs[i][j] = calculateGoogleDistance(words1[i-1], words2[j-words1.length-2]);
				costs[j][i] = costs[i][j];
				System.out.println(" word1 "+words1[i-1]+" word2 "+words2[j-words1.length-2]+" cost "+costs[i][j]);
				if (words1[i-1].equalsIgnoreCase(words2[j-words1.length-2])) {
					sameWordCount++;
				}
			}
		}
		
		for (int i = 1; i <= words1.length; i++) {
			flows[0][i] = 1;
			for (int j = words1.length+2; j <= words1.length+words2.length+1; j++) {
				flows[i][j] = 1;
				flows[j][count-1] = 1;
				costs[i][j] = calculateGoogleDistance(words1[i-1], words2[j-words1.length-2]);
				System.out.println(" word1 "+words1[i-1]+" word2 "+words2[j-words1.length-2]+" cost "+costs[i][j]);
				costs[j][i] = costs[i][j];
			}
		}
		for (int i = 1; i <= words1.length; i++) {
			flows[i][count-2] = 1;
			costs[i][count-2] = calculateGoogleDistance(words1[i-1], "");
			costs[count-2][i] = costs[i][count-2];
		}
		for (int i=words1.length+2; i <= words1.length+words2.length+1; i++) {
			flows[words1.length+1][i] = 1;
			costs[words1.length+1][i] = calculateGoogleDistance("", words2[i-words1.length-2]);
			System.out.println(" word1 kong  word2"+words2[i-words1.length-2]+" cost "+costs[words1.length+1][i]);

			costs[i][words1.length+1] = costs[words1.length+1][i];
		}
		flows[0][words1.length+1] = words2.length;
		flows[count-2][count-1] = words1.length;
		flows[words1.length+1][count-2] = Math.abs(words2.length-words1.length);
		MinCostMaxFlow mcmf = new MinCostMaxFlow(1, count, count, flows, costs);
		double cost = mcmf.findMaxFlowPath();
		//cost = cost/更改 更新所有name之间的相似度
		cost=1-cost/Math.max(words1.length, words2.length);
		/*if (sameWordCount>=2) {
			cost *= Math.pow(0.5, sameWordCount);
		}*/
		/*for(int i=1;i<words1.length;i++)
		{
			for(int j=1;j<words2.length;j++)
				System.out.println(" word1 "+words1[i]+" word2 "+words2[j]+" cost "+costs[i-1][j-1]);
		}*/
		System.out.println(s1+" "+s2+": "+cost);
		return cost;
	}
	
	public static String[] stringPartition(String s){
		s = s.replace(":", " ");
		s = s.replace("(", " ");
		s = s.replace(")", " ");
		s = s.replace("'", " ");
		s = s.replace("_", " ");
		s = s.replace("- ", " ");
		s = s.replace(" -", " ");
		s = s.replace("  ", " ");
		s = s.replace("  ", " ");
		s.trim();
		s = s.replace(" ", "_");
		for(int i = 0; i < s.length()-1; i++){
			if (Character.isLowerCase(s.charAt(i))&&Character.isUpperCase(s.charAt(i+1))){
				s = s.substring(0,i+1)+"_"+s.substring(i+1);
				i+=2;
			}
		}
		return s.split("_");
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		System.out.println(calculateSimilarity("China", "Chinese"));
	}
}
