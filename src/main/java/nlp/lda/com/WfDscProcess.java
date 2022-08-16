package nlp.lda.com;
/*
 * 将一个txt文件中的每行数据存入一个新的txt
 * 
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class WfDscProcess {
    public static List<String> list = new ArrayList<String>();
    private String temp;
    public static int i;

    public static void main(String[] args) {
        WfDscProcess fileTest = new WfDscProcess();
        fileTest.readFile("C:/Users/DJ/Desktop/data/wfl_dsc/@0802final.txt");
        for (i = 0; i < list.size(); i++){
        	fileTest.printFile("C:/Users/DJ/Desktop/data/wfl_dsc/"+i+".txt",i);
        }
        System.out.println("txtDivided!");
//        fileTest.printFile("C:/Users/DJ/Desktop/data/b.txt");
//        System.out.println(fileTest.list);
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

    public void printFile(String fileName, int i) {

        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.write(list.get(i));
            bufWriter.newLine();
         
            bufWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

