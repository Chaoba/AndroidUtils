package com.mingy.android.crash;

import android.app.Application;

/**
 * ������Application������Application���Լ̳����Applicationʵ�ֲ���Android���쳣��Ϣ
 * 
 * */
public class CrashApplication extends Application {
	@Override
	public void onCreate( ) {
		super.onCreate( );
		
		initCrashException( );
	}
	
	/**
	 * ��ʼ���쳣������
	 * 
	 * */
	private void initCrashException( ){
		if( mCrashException ){
			CrashHandler crashHandler = CrashHandler.getInstance( );  
	        crashHandler.init( this ); 
		}
	}
	
	public static final boolean mCrashException = true;
}
