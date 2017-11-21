package com.het.open.lib.utils;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.het.log.Logc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * h5文件处理工具类
 * Created by 80010814 on 2016/9/23.
 */

public class H5FileUtils {


    public static final String PAGE_URL = "/page";
    public static final String TAG = H5FileUtils.class.getName();

    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    /**
     * 获取h5文件夹路径
     * @param mContext
     * @return
     */
    public static String getfolderPath(Context mContext) {
        String path;
        if (isExistSDCard()) {
            path = mContext.getExternalFilesDir(null) + "/h5/";
        } else {
            path = Environment.getDataDirectory().getAbsolutePath() + "/h5/";
        }
        return path;
    }

    /**
     * 获取webview缓存文件夹路径
     * @param mContext
     * @return
     */
    public static String getWebviewCachefolderPath(Context mContext) {
        return mContext.getDir("cache", Context.MODE_PRIVATE).getPath();
    }

    /**
     * 获取指定文件夹大小
     *
     * @param mContext
     * @param folderName 文件命
     * @return
     */
    public static Long getfolderSize(Context mContext, String folderName) {
        long size = -1;
        if (!TextUtils.isEmpty(folderName)) {
            try {
                size= getFileSizes(new File(folderName));
            } catch (Exception e) {
                Logc.e(TAG, e.toString());
            }
        }
        return size;
    }

    /**
     * 判断本地是否有common包,有返回路径
     */
    public static String isComFolderExit(Context mContext) {
        String path = getfolderPath(mContext);
        if (TextUtils.isEmpty(path)) {
            return null ;
        } else {
            path = path + "/common/";
            File filePath = new File(path);
            if (filePath.exists()) {
                return path;
            } else {
                return null;
            }
        }
    }

    /**
     * 判断本地是否有h5插件包
     */
    public static boolean isPlugH5FolderExit(String path) {
        File filePath = new File(path);
        if (filePath.exists()) {
            return true;
        } else {
            return false;
        }

    }



    /**
     * 将压缩文件解决到指定目录
     *
     * @param pathUrl 压缩l文件临时路径
     *                //* @param folderPath 解压后目录
     * @return
     * @throws IOException
     */
    public synchronized static String upZipFile(Context mContext, String pathUrl) throws IOException {

        ZipEntry ze = null;
        ZipFile zfile = null;
        File tempFile = null;
        try {
            String folderPath = getfolderPath(mContext);
            tempFile = new File(pathUrl);
            zfile = new ZipFile(tempFile);
            Enumeration zList = zfile.entries();
            File path = new File(folderPath);
            if (!path.exists()) {
                path.mkdir();
            }
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    //Logc.d("upZipFile", "ze.getName() = " + ze.getName());
                    String dirStr = folderPath + ze.getName();
                    // Logc.d("upZipFile", "str = " + dirStr);
                    File f = new File(dirStr);
                    deleteDirctory(f);
                    f.mkdir();
                    continue;
                }
                if (!writeToFile(folderPath, ze, zfile)){
                    return null;
                }


            }
        } catch (FileNotFoundException e) {
            Logc.e(TAG, e.toString());
            return null;
        } finally {
            if (zfile != null) {
                zfile.close();
            }
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        if (ze != null) {
            String name = ze.getName();
            if (TextUtils.isEmpty(name)) {
                return null;
            } else {
                return name.split("/")[0];
            }
        } else {
            return null;
        }


    }

//    /**
//     * 请求读写权限
//     */
//    public static void getPermission(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            RxPermissions.getInstance(AppDelegate.getAppContext())
//                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe(grant->{
//                        if (grant) {
//                            Logc.w("@@@@@@@@@@@@@读写:申请成功");
//                        }
//                    });
//        }
//    }

    /**
     * 写入文件
     *
     * @param folderPath
     * @param ze
     * @param zfile
     */
    public static boolean writeToFile(String folderPath, ZipEntry ze, ZipFile zfile) {
        boolean writeFlag;
        if (TextUtils.isEmpty(folderPath)){
            writeFlag=false;
        }
        if (ze!=null&&zfile!=null){
            OutputStream os=null ;
            InputStream is=null ;
            try {
                byte[] buf = new byte[1024];
                os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
                writeFlag=true;
            } catch (FileNotFoundException e) {
                Logc.e(TAG, e.toString());
                writeFlag=false;
            } catch (IOException e) {
                Logc.e(TAG, e.toString());
                writeFlag=false;
            } finally {
                try {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (os != null) {
                        os.close();
                        os = null;
                    }

                } catch (IOException e) {
                    Logc.e(TAG, e.toString());
                    writeFlag=false;
                }

            }
        }else {
            writeFlag= false;
        }
        return  writeFlag;

    }

    /**
     * 删除文件或者文件夹下所有文件
     * @param file
     */
    public static void deleteDirctory(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (File childFile : childFiles) {
                deleteDirctory(childFile);
            }
            file.delete();
        }
    }

    /**
     * 获取文件名称
     * @param baseDir
     * @param absFileName
     * @return
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        if (absFileName!=null){
            try {
                String[] dirs = absFileName.split("/");
                File ret = new File(baseDir);
                String substr;
                int dirLength=dirs.length;
                if (dirLength > 1) {
                    for (int i = 0; i < dirLength - 1; i++) {
                        substr = dirs[i];
                        ret = new File(ret, substr);
                    }
                    // Logc.d("upZipFile", "1ret = " + ret);
                    if (!ret.exists())
                        ret.mkdirs();
                    substr = dirs[dirs.length - 1];
                    ret = new File(ret, substr);
                    return ret;

                }
            } catch (Exception e) {
                Logc.e(TAG,e.toString());
            }
        }

        return null;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                size = size + getFileSizes(aFlist);
            } else {
                size = size + getFileSize(aFlist);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis ;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;

    }
}
