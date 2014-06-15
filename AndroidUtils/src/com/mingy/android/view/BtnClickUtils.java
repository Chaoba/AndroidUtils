package com.mingy.android.view;

/**
 * ��ť��ز���
 * 
 * */
public class BtnClickUtils {
    private BtnClickUtils( ){
        
    }
    
    /**
     * ��ⰴť�Ƿ���ٵ��
     * 
     * */
    public static boolean isFastClick( ){
        long currentClickTime = System.currentTimeMillis( );
        long timeDistance = currentClickTime - mLastClickTime;
        
        if( timeDistance > 0 && timeDistance < 1000 ){
            return true;
        }
        
        mLastClickTime = currentClickTime;
        
        return false;
    }
    
    private static long mLastClickTime = 0;
}
