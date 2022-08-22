package com.grgbanking;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException {
        FileWriter fw=new FileWriter(new File("D:\\DataLoad\\ibankpro\\data.sql"));
        //写入中文字符时会出现乱码
        BufferedWriter  bw=new BufferedWriter(fw);
        //BufferedWriter  bw=new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8")));

        //for (File subFile : files) {
        BufferedReader reader = null;//字符输入流进行读取操作读取
        String tempString = null;//每一行的内容
        try {
            //输入字节流，FileInputStream主要用来操作文件输入流
            FileInputStream intput = new FileInputStream("D:\\贵阳银行\\微服务版本监控\\sys-用户中心数据和表结构.sql");
            // System.out.println("以行为单位读取文件内容，一次读一整行：")
            //InputStreamReader是转换流，将字节流转成字符流
            reader = new BufferedReader(new InputStreamReader(intput));
            while ((tempString = reader.readLine()) != null) {
                if((tempString.startsWith("INSERT"))){
                    bw.write(tempString+"\t\n");
                }
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
    //}
}
