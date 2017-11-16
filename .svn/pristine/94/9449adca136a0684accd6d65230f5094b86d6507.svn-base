/*
 * -----------------------------------------------------------------
 * Copyright ?2014 clife -
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------------------------
 *
 * File: FileUtils.java
 * Create: 2015/10/28 13:27
 */
package com.het.open.lib.utils;

import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Android Studio.
 * Author: UUXIA
 * Date: 2015-10-28 13:27
 * Description:
 */
public class FileUtils {
    public static String FilePath = Environment.getExternalStorageDirectory().getPath() + "/HeT/open/url/";
    public final static String filename = "url.txt";

    static {
        initDownPath(FilePath);
    }

    public static String readFileSdcardFile() throws IOException {
        String res="";
        try{
            FileInputStream fin = new FileInputStream(FilePath+filename);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
          //  res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static void writeFileSdcardFile(String write_str) throws IOException{
        try{
            FileOutputStream fout = new FileOutputStream(FilePath+filename);
            byte [] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeFileSdcardFile(String fileName,String write_str) throws IOException{
        try{
            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean fileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public static boolean initDownPath(String path){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
                return true;
            }
        }
        return false;
    }

    public static InputStream bytes2inputStream(byte[] data) {
        ByteArrayInputStream inputStream = null;

        try {
            inputStream = new ByteArrayInputStream(data);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return inputStream;
    }
}
