package com.grgbanking;

import java.io.*;

public class TestSql {

    public static void main(String[] args) throws IOException {

            BufferedReader reader = null;//字符输入流进行读取操作读取
            String tempString = null;//每一行的内容
            try {
                //输入字节流，FileInputStream主要用来操作文件输入流
                FileInputStream intput = new FileInputStream("D:\\贵阳银行\\微服务版本监控\\sys-用户中心数据和表结构.sql");
                // System.out.println("以行为单位读取文件内容，一次读一整行：")
                //InputStreamReader是转换流，将字节流转成字符流
                reader = new BufferedReader(new InputStreamReader(intput));
                while ((tempString = reader.readLine()) != null) {
                    if(tempString.startsWith("INSERT") && !tempString.endsWith(");")){
                        System.out.println(tempString);
                    } else {
                        System.out.println(tempString);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}

