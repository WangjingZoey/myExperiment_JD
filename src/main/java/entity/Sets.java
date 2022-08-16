package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Sets {
	//服务集合
	public static List<Service> Src = new ArrayList<Service>();
	//整理后的服务集合
	public static List<Service> iniSrc = new ArrayList<Service>();
	//作者集合
	public static List<Developer> Dpr = new ArrayList<Developer>();
	//作者边集
	public static List<Relation> Dprl = new ArrayList<Relation>();
	
	//NCSR服务集合
	public static List<Service> NCSrc = new ArrayList<Service>();
	
	//服务边集
	public static List<Relation> SEdg = new ArrayList<Relation>();
	
	//服务边集
	public static List<Relation> Srcrl = new ArrayList<Relation>();
	
	//NCSR正规则边集
	public static List<Relation> NPEdg = new ArrayList<Relation>();
	
	//NCSR负规则边集
	public static List<Relation> NNEdg = new ArrayList<Relation>();
	
	//NCSR边集
	public static List<Relation> NEdg = new ArrayList<Relation>();
	
	public static List<Wkf> Wkfl = new ArrayList<Wkf>();
	
	//创建srcnm对象
	public static Map<Integer, List<String>> srcnm = new HashMap<Integer,List<String>>();     
	
	//创建wkfds对象
	public static Map<Integer, List<String>> wkfdes = new HashMap<Integer,List<String>>();  
  
	//创建wkftg对象
	public static Map<Integer, List<String>> wkftg = new HashMap<Integer,List<String>>();  
  
	//创建srcwkfid对象
	public static Map<Integer, List<Integer>> srcwid = new HashMap<Integer,List<Integer>>();     

	//创建srcwkdesd对象
	public static Map<Integer, List<String>> swdes = new HashMap<Integer,List<String>>();  
  
	//创建srcwktg对象
	public static Map<Integer, List<String>> swtg = new HashMap<Integer,List<String>>(); 
	
	
	public static Map<Integer, List<Integer>> dsid = new HashMap<Integer,List<Integer>>();//dlp的src的id
	
	public static ArrayList<Float> cr = new ArrayList<Float>();//src的cr
	
	public static ArrayList<ArrayList<Integer>> wid = new ArrayList<ArrayList<Integer>>();
	
	//包含关键字的指标
	public static  List<String> IndND = new ArrayList<String>();
}
