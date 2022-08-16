package neo4j.WKFRec;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import api.apiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import nlp.lda.main.Sim2Vec;

import org.apache.commons.io.FileUtils;
import org.neo4j.driver.v1.*;
import java.lang.String;
import entity.Service;
import entity.Sets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.swing.plaf.synth.SynthDesktopIconUI;

import static org.neo4j.driver.v1.Values.NULL;
import static org.neo4j.driver.v1.Values.parameters;
public class WkfRecIND 
{
	public static  List<String> KeyWrd = new ArrayList<String>();
	public static  List<String> INDs = new ArrayList<String>();
	public static  List<String> ReINDs = new ArrayList<String>();
	public static  List<String> Labels = new ArrayList<String>();
	public static int lmt = 3; 
	public static Driver driver;
	public static Map<String,String> serverPro = new HashMap<>();
	
    public static Map getInd(ArrayList keywords) throws IOException {
		// Get the database Settings
		File file = new File("properties.json");
		String data = readString(file);
		serverPro = (Map<String, String>) JSON.parse(data);

		// Return the query results in the searchResult
		Map<String,Object> searchResult=new HashMap<>();
		// Check the database Settings
    	if(serverPro.get("uri").length() ==0 || serverPro.get("username").length() ==0  || serverPro.get("password").length() ==0 ){
    		return searchResult;
		}

		KeyWrd.clear();
		INDs.clear();
		ReINDs.clear();
		Labels.clear();

		// get the first(only) keyword
		KeyWrd.add((String) keywords.get(0));

		// Connect neo4j
		driver = GraphDatabase.driver(serverPro.get("uri"),
				AuthTokens.basic(serverPro.get("username"), serverPro.get("password")));

    	getKey();
    	getInd();
    	driver.close();

		for(int i = 0; i<INDs.size(); i++){
			INDs.set(i, INDs.get(i).replace("\"", ""));
		}
		for(int i = 0; i<ReINDs.size(); i++){
			ReINDs.set(i, ReINDs.get(i).replace("\"", ""));
		}
		searchResult.put("INDs",INDs);
		searchResult.put("ReINDs",ReINDs);

		return searchResult;
    }


	public static void main( String[] args )
	{

	}

    public static List<String> getInd()
    {
    	try(Session session = driver.session())
    	{
    		for(int j=0;j<INDs.size();j++)
        	{
    			String cond;
//        		System.out.println("label:"+Labels.get(j));
        		if(Labels.get(j).equals("fathernode"))
        		{
        			cond = "match data=(na)-[rel*1]->(nb{name:'"+INDs.get(j)+"'}) return na.name";
//        			System.out.println("cond:"+cond.replace("\"", ""));
            		cond = cond.replace("\"", "");
            		StatementResult result2 = session.run(cond);   		
     
            		while (result2.hasNext())
                	{
                		Record record = result2.next();
                		
                		if(!(record.get("na").equals(null)))
                		{
                			ReINDs.add(String.valueOf(record.get("na.name")));
                		}	
                	}
            		System.out.println("相关的指标为："+ReINDs);	
        		}
        		else
        		{
        			cond = "match data=(na{name:'"+INDs.get(j)+"'})-[rel*1]->(nb) return nb.name";
//        			System.out.println("cond:"+cond.replace("\"", ""));
            		cond = cond.replace("\"", "");
            		StatementResult result2 = session.run(cond);
            		
            		while (result2.hasNext())
                	{
                		Record record = result2.next();
                		
                		if(!(record.get("nb").equals(null)))
                		{
                			ReINDs.add(String.valueOf(record.get("nb.name")));
                		}	
                	}
            		System.out.println("相关的指标为："+ReINDs);	
        		} 
        	}
    	}
    	return ReINDs;
    }
    
    public static List<String> getKey()
    {
    	// getRQ();
    	try(Session session = driver.session())
    	{
    		for(int i=0; i < KeyWrd.size(); i++){
				StatementResult resultnm = session.run("MATCH (n) where n.name =~'"+KeyWrd.get(i)+".*' or n.itemnode =~'"+KeyWrd.get(i)+".*' RETURN n.name, labels(n) LIMIT "+lmt);
	//    		StatementResult resultnm = session.run("match data=(na{name:'(民族自治地方)从业人员数'})-[rel*1]->(nb) return nb.name");
				while (resultnm.hasNext())
				{
					Record recordnm = resultnm.next();
					if(!(recordnm.get("n.name").equals(null)))
					{
						INDs.add(String.valueOf(recordnm.get("n.name")));
						String label;
						label = String.valueOf(recordnm.get("labels(n)")).replace("[", "");
						label = label.replace("]", "");
						label = label.replace("\"", "");
						Labels.add(label);
//    	        	System.out.println(label);
					}
				}
				System.out.println("找到包含关键词的实体为："+INDs);
			}

    	}

    	return INDs;
    }

	public static void getRQ()
	{
		try {
			File file = new File("C:/Users/wj/Desktop/1/tests-jd.xls"); // 创建文件对象
			Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）
			List<String> sids = new ArrayList<String>();

			for (int j=0; j<1; j++) { // 循环打印Excel表中的内容

				Cell cell = sheet.getCell(0, j);
				System.out.println("关键字："+cell.getContents());

				KeyWrd.add(cell.getContents());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readString(File file) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			StringBuilder content = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}




