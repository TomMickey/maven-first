package com.grgbanking;

import java.io.*;

public class TransferSql {

    public static void main(String[] args) throws IOException {
        FileWriter fw=new FileWriter(new File("D:\\DataLoad\\device-data-old.sql"));
        //写入中文字符时会出现乱码
        BufferedWriter bw=new BufferedWriter(fw);
        //for (File subFile : files) {
        BufferedReader reader = null;//字符输入流进行读取操作读取
        String tempString = null;//每一行的内容
        try {
            //输入字节流，FileInputStream主要用来操作文件输入流
            FileInputStream intput = new FileInputStream("D:\\DataLoad\\ibankpro\\device-data-old.sql");
            // System.out.println("以行为单位读取文件内容，一次读一整行：")
            //InputStreamReader是转换流，将字节流转成字符流
            reader = new BufferedReader(new InputStreamReader(intput));
            while ((tempString = reader.readLine()) != null) {
                if((tempString.startsWith("INSERT"))){
                    String [] stringArr = tempString.split(",");
                    for(String subString: stringArr){
                        if(subString.indexOf(":") != -1 && subString.indexOf("'20") != -1 && subString.indexOf("-") != -1){
                            if(subString.endsWith(");")){
                                subString = subString.substring(0, subString.indexOf(");"));
                            }
                            String sub = "TO_DATE(" + subString + ", 'SYYYY-MM-DD HH24:MI:SS')";
                            tempString = tempString.replaceAll(subString, sub);
                            //System.out.println(tempString.replaceAll(subString, sub));
                        }
                    }
                    //bw.write(tempString+"\t\n");
                } else {

                }
                bw.write(tempString+"\t\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
