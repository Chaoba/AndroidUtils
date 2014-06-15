package com.mingy.android.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.mingy.android.text.StringUtils;

/**
 * �ļ�������
 * 
 * */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils( ){
        
    }
    
    /**
     * �õ�SD����·��
     * 
     * */
    public static String getSDPATH( ){
        return Environment.getExternalFlashStorageDirectory( ).toString( ) + "/";
    }

    /**
     * �õ�������·��
     * 
     * */
    public static String getFlashAPath( ){
        return Environment.getInternalStorageDirectory( ).toString( ) + "/";
    }

    /**
     * �õ��ڲ��洢�豸��·��
     * 
     * */
    public static String getFlashBPath( ){
        return Environment.getExternalStorageDirectory( ).toString( ) + "/";
    }
    
    /**
     * �ж��ļ��Ƿ����
     * 
     * @param filePath �ļ�ȫ·��
     * */
    public static boolean isFileExist(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        
        return (file.exists() && file.isFile());
    }

    /**
     * �ж��ļ����Ƿ����
     * 
     * @param directoryPath �ļ���ȫ·��
     * */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }
    
    /**
     *  �жϴ��̿ռ��Ƿ��㹻
     *  
     *  @param filePath �ļ�·��
     *  @param sizeMb ����M����λ��M��
     * */
    public static boolean isSpaceEnough( String filePath, int sizeMb ){
        boolean hasSpace = false;
        if( TextUtils.isEmpty( filePath ) || sizeMb <= 0 ){
            return hasSpace;
        }
        StatFs statFs = new StatFs( filePath );

        if( null != statFs ){
            long blockSize = statFs.getBlockSize( );
            long blocks = statFs.getAvailableBlocks( );
            double availableSpare = ( blocks * blockSize * 1.0 ) / ( 1024 * 1024 );

            if( availableSpare > sizeMb ){
                hasSpace = true;
            }
        }

        return hasSpace;
    }
    
    /**
     * �õ�������׺���ļ���
     * 
     * @param filePath �ļ�ȫ·��
     * @return ������׺���ļ���
     * */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * �õ��ļ���
     * 
     * @param filePath �ļ�ȫ·��
     * @return �ļ���
     * */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * �õ��ļ��е�����
     * 
     * @param filePath �ļ���ȫ·��
     * @return �ļ�������
     * */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * �õ��ļ���׺
     * 
     * @param filePath �ļ�ȫ·��
     * @return �ļ���׺��������
     * */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }
    
    /**
     * �����ļ���
     * 
     * @param filePath �ļ���·��
     * @return �Ƿ񴴽��ɹ�
     * */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * ɾ���ļ�
     * 
     * @param filePath �ļ�ȫ·��
     * @return �Ƿ�ɾ���ɹ�
     * */
    public static boolean deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return true;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        
        return file.delete();
    }

    /**
     * �õ��ļ���С����λB
     * 
     * @param filePath �ļ�ȫ·��
     * @return �ļ���С
     * */
    public static long getFileSize(String filePath) {
        if (StringUtils.isBlank( filePath )) {
            return -1;
        }

        File file = new File( filePath );
        return (file.exists() && file.isFile() ? file.length() : -1);
    }
    
    /**
     * ���ļ�
     * 
     * @param filePath �ļ�ȫ·��
     * @param charsetName �����ʽ
     * @return �ļ������ڷ���null�������򷵻��ļ�����
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * д�ļ�
     * 
     * @param filePath �ļ�ȫ·��
     * @param content д������
     * @param append �Ƿ�Ϊappendģʽ
     * @return �������Ϊ���򷵻�false������Ϊtrue
     * */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * д�ļ�
     * 
     * @param filePath �ļ�ȫ·��
     * @param content д������
     * @return true д�ɹ���false дʧ��
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }
}
