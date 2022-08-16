package neo4j.WKFRec;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import nlp.lda.main.Sim2Vec;

import org.neo4j.driver.v1.*;

import entity.Service;
import entity.Sets;
import static org.neo4j.driver.v1.Values.parameters;
public class WkfRecKey 
{

	
	public static  Map<Integer, List<String>> Ans = new HashMap<Integer,List<String>>();
	public static  Map<Integer, List<String>> Ept = new HashMap<Integer,List<String>>();
	
	public static int lmt = 3; 

	
    
    
    
}




