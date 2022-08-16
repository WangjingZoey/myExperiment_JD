package nlp.lda.main;

import java.util.ArrayList;
import java.util.List;

import nlp.lda.com.FileUtil;

public class WrdNmber {
	public static void main(String[] args)
	{
		List<String> tw_list = new ArrayList<String>();
		tw_list = FileUtil.readFileByLines("data/SWDSC/lda_100.phi");
//		tw_list = FileUtil.readFileByLines("data/DDSC/lda_100.phi");
		int nmbr = 0;
		for(int j = 0; j < tw_list.size(); j++)
		{ //遍历topics 
			String tw_arr[] = tw_list.get(j).split("\\s+");
			
			System.out.println("wrdnmbr:"+tw_arr.length);
			
		}
		
	}
}
