package nlp.lda.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import entity.Sets;

//将LDA文件中的每行数据分配给相应的实体，存入它的dsc属性
public class LDADistribution {
	public static List<String> list = new ArrayList<String>();
    private String temp;

    public static void main(String[] args) {
        WfDscProcess fileTest = new WfDscProcess();
        fileTest.readFile("data/ResultsData/lda_data.theta");
        for (int i = 0; i < list.size(); i++){
//        	fileTest.printFile("C:/Users/DJ/Desktop/data/"+i+".txt",i);
        	Sets.Src.get(i).setDes(list.get(i));
        }

    }

    public void readFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(new File(fileName));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((temp = bufferedReader.readLine()) != null) {
                list.add(temp);
            }
            bufferedReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
