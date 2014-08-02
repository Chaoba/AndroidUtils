package com.app.common.multimedia;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * ��ý���ļ�ɨ��
 * ʹ��ʾ����
 * ArrayList<String> supportFileList = MediaFileScanner.getSupportFileList( this, new String[]{"mp3","ogg","wav"} );
 * 
 * */
public class MediaFileScanner {
	/**
     * ��ý����л�ȡָ����׺���ļ��б�
     * 
     * @param context
     * @param searchFileSuffix �������ļ�����׺���飬����new String[]{"mp3","ogg","wav"}
     * 
     * */
    public static ArrayList<String> getSupportFileList( Context context, String[] searchFileSuffix ) {
    	ArrayList<String> searchFileList = null;
    	if( null == context || null == searchFileSuffix || searchFileSuffix.length == 0 ){
    		return null;
    	}
    	
    	String searchPath = "";
    	int length = searchFileSuffix.length;
    	for( int index = 0; index < length; index++ ){
    		searchPath += ( MediaStore.Files.FileColumns.DATA + " LIKE '%" + searchFileSuffix[ index ] + "' " );
    		if( ( index + 1 ) < length ){
    			searchPath += "or ";
    		}
    	}
    	searchFileList = new ArrayList<String>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = context.getContentResolver().query(
                uri, new String[] { MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE },
                searchPath, null, null);

        if (cursor == null) {
            System.out.println("Cursor ��ȡʧ��!");
        } else {
            if (cursor.moveToFirst()) {
                do {
                    String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    try {
                        searchFileList.add(new String(filepath.getBytes("UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return searchFileList;
    }
}
