package com.mingy.android.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.text.TextUtils;

/**
 * Assert�ļ�������
 * 
 * */
public class AssertFileUtils {
    private AssertFileUtils( ){
        
    }
    
    /**
     * ��ȡAssert�ļ�����
     * 
     * @param context
     * @param fileName assert�ļ����ƣ�����"test.txt"
     * @return assert�ļ�����
     * */
    public static String getAssertContent( Context context, String fileName ) throws NullPointerException{
        if( null == context || TextUtils.isEmpty( fileName ) ){
            throw new NullPointerException( "fileName or context is nullpoint" );
        }
        
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null){
                result += line;
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
