package com.app.common.dataautorefresh;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;

/**
 * ģ�������Զ�ˢ��
 * ʹ�ò��裺
 * 1���½�һ��activity��ʵ��AutoRefreshListener�ӿڣ�eg:
 * public class MainActivity extends Activity implements AutoRefreshListener {
	    @Override
	    public ArrayList<String> onGetDataPathList( ) {
		    // �õ�������������ļ�·���б�
	    }
		
		  @Override
	    public void onDataRefresh(ArrayList<String> DataPathList) {
		    // ��������������ӵ����
	    }
		
		  @Override
	    public void onDataScan() {
	        // ȫ��ɨ��
	    }
	}
	
	2������DataAutoRefresh����������ʼ����eg��
	private static final String[] mDataScanPath = new String[]{Environment.getExternalStorageDirectory( ).toString( ), Environment.getExternalFlashStorageDirectory( ).toString( ) };
	private static final String[] mSupportFileSuffix = new String[]{"ptr","hsr"};
	
	private DataAutoRefresh mDataAutoRefresh = null;
	private void initAutoRefreshData( ){
        mDataAutoRefresh = new DataAutoRefresh( this, this, mSupportFileSuffix );
        mDataAutoRefresh.DataAutoRefresh( );
    }
    
    3����Activity��onPause��onResume��onDestory�����е������·�����
    @overwrite
    public void onPause( ){
        mDataAutoRefresh.onPause( );
    }
    
    @overwrite
    public void onResume( ){
        onResume( );
    }
    
    @overwrite
    public void onDestory( ){
        unregisterAutoRefreshDataShelf( );
    }
    
    private void unregisterAutoRefreshDataShelf( ){
	    if( null != mAutoRefreshDataShelf ){
	        mDataAutoRefresh.unregisterAutoRefreshData( );
	    }
	}  
 * 
 * */
public class DataAutoRefresh {
	public DataAutoRefresh( Context context, AutoRefreshListener autoRefreshListener, String[] supportSuffix ) throws NullPointerException{
        if( null == context || null == autoRefreshListener || null == supportSuffix ){
            throw new NullPointerException( "���˷ǿյĲ���������" );
        }
        
        mContext = context;
        mAutoRefreshListener = autoRefreshListener;
        mSupportSuffix = supportSuffix;
        
        initAutoRefreshData( );
    }
    
	/**
	 * ���ڱ�����ֹͣ��̨����
	 * 
	 * */
    public void onPause( ){
        stopCheckFileTimer( );
    }
    
    /**
     * ���ؽ���ָ���̨����
     * 
     * */
    public void onResume( ){
        startCheckFileTimer( );
    }
    
    /**
     * ע���㲥
     * 
     * */
    public void unregisterAutoRefreshData( ) throws NullPointerException{
        if( null == mBroadcastReceiver || null == mMediaStoreChangeObserver || null == mContext ){
            throw new NullPointerException( "û�г�ʼ��" );
        }
        mContext.unregisterReceiver( mBroadcastReceiver );
        mContext.getContentResolver( ).unregisterContentObserver( mMediaStoreChangeObserver );
        stopCheckFileTimer( );
    }
    
    /**
     * �õ��仯���ļ��б�
     * 
     * */
    public void getChangedFileList( ){
        System.out.println( "toast ================= getChangedFileList " );
        startCheckFileTimer( );
    }
    
    private void initAutoRefreshData( ){
        startMediaFileListener( );
        observerMediaStoreChange( );
    }
    
    private void observerMediaStoreChange( ){
        if( null == mMediaStoreChangeObserver ){
            mMediaStoreChangeObserver = new MediaStoreChangeObserver( );
        }
        mContext.getContentResolver( ).registerContentObserver( MediaStore.Files.getContentUri("external"), false, mMediaStoreChangeObserver );
    }
    
    /**
     * ����USB��״̬����������鱾��Ϣ
     * 
     * */
    private void startMediaFileListener( ){
        if( null != mBroadcastReceiver ){
            return;
        }
        
        IntentFilter intentFilter = new IntentFilter( );
        intentFilter.addAction( Intent.ACTION_MEDIA_SCANNER_FINISHED );
        intentFilter.addAction( Intent.ACTION_MEDIA_MOUNTED );
        intentFilter.addAction( Intent.ACTION_MEDIA_EJECT );
        intentFilter.addDataScheme( "file" );
          
        mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context,Intent intent){
                String action = intent.getAction( );
                if( Intent.ACTION_MEDIA_SCANNER_FINISHED.equals( action ) ){
                    System.out.println( "toast ================= ACTION_MEDIA_SCANNER_FINISHED " );
                    mTimerWorking = false;
                    startCheckFileTimer( );
                }else if( action.equals( Intent.ACTION_MEDIA_MOUNTED ) ){
                    System.out.println( "toast ================= ACTION_MEDIA_MOUNTED or ACTION_MEDIA_EJECT " );
                    mTimerWorking = true;
                    mAutoRefreshListener.onDataScan( );
                }else if( action.equals( Intent.ACTION_MEDIA_EJECT ) ){
                    mAutoRefreshListener.onDataScan( );
                }
            }
        };
        mContext.registerReceiver( mBroadcastReceiver, intentFilter );//ע���������
    }
    
    /**
     * ý�����ݿ����۲���
     * 
     * */
    class MediaStoreChangeObserver extends ContentObserver{
        public MediaStoreChangeObserver( ) {
            super( new Handler( ) );
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            startCheckFileTimer( );
        }
    }
    
    private void startCheckFileTimer( ){
        if( mTimerWorking ){
            return;
        }
        
        mCheckFileTimer = new Timer( );
        mCheckFileTimer.schedule( new CheckFileChangeTimerTask( ), 5000 );
        mTimerWorking = true;
    }
    
    private void stopCheckFileTimer( ){
        if( null != mCheckFileTimer ){
            mCheckFileTimer.cancel( );
            mCheckFileTimer = null;
            
            mTimerWorking = false;
        }
    }
    
    /**
     * �õ��������ļ��б�
     * 
     * */
    public ArrayList<String> getChangedFileList( Context context, String[] searchFileSuffix, ArrayList<String> existFileList ){
        ArrayList<String> changedFileList = null;
        if( null == context || null == searchFileSuffix ){
            return changedFileList;
        }
        
        ArrayList<String> supportFileList = getSupportFileList( context, searchFileSuffix );
        changedFileList = getDifferentFileList( supportFileList, existFileList );
        if( null == changedFileList || changedFileList.size( ) == 0 ){
            changedFileList = null;
        }
        
        return changedFileList;
    }
    
    /**
     * ��ȡ�������ļ��б�
     * 
     * */
    private ArrayList<String> getDifferentFileList( ArrayList<String> newFileList, ArrayList<String> existFileList ){
        ArrayList<String> differentFileList = null;
        if( null == newFileList || newFileList.size( ) == 0 ){
            return differentFileList;
        }
        
        differentFileList = new ArrayList<String>( );
        boolean isExist = false;
        if( null == existFileList ){
            // ����Ѵ����ļ�Ϊ�գ��ǿ϶���ȫ���ӽ�������
            for( String newFilePath : newFileList ){
                differentFileList.add( newFilePath );
            }
        }else{
            for( String newFilePath : newFileList ){
                isExist = false;
                for( String existFilePath : existFileList ){
                    if( existFilePath.equals( newFilePath ) ){
                        isExist = true;
                        break;
                    }
                }
                
                if( !isExist ){
                    differentFileList.add( newFilePath );
                }
            }
        }
        
        return differentFileList;
    }
    
    /**
     * ��ý����л�ȡָ����׺���ļ��б�
     * 
     * */
    public ArrayList<String> getSupportFileList( Context context, String[] searchFileSuffix ) {
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
                uri, new String[] { MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns._ID },
                searchPath, null, null);
        
        String filepath = null;
        if (cursor == null) {
            System.out.println("Cursor ��ȡʧ��!");
        } else {
            if (cursor.moveToFirst()) {
                do {
                    filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
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
    
    /**
     * �õ�ý�����µ��ļ�
     * 
     * */
    class GetMediaStoreDataTask extends AsyncTask< Void , Void , Void>{
        @Override
        protected Void doInBackground(Void... arg0) {
            ArrayList<String> changedFileList = getChangedFileList( mContext, mSupportSuffix, mAutoRefreshListener.onGetDataPathList( ) );
            if( null != changedFileList && changedFileList.size( ) > 0 ){
                mAutoRefreshListener.onDataRefresh( changedFileList );
            }
            mTimerWorking = false;
            
            return null;
        }
    }
    
    class CheckFileChangeTimerTask extends java.util.TimerTask{
        @Override
        public void run() {
            new GetMediaStoreDataTask( ).execute( );
        }
    }
    
    /**
     * ����Զ�ˢ�½ӿ�
     * 
     * */
    public interface AutoRefreshListener{
        public ArrayList<String> onGetDataPathList( ); // �õ�����鱾�б�
        public void onDataRefresh( ArrayList<String> datPathList );// ˢ�����
        public void onDataScan( );//ȫ��ɨ�����
    }
    
    private boolean mTimerWorking = false;
    private Context mContext = null;
    private String[] mSupportSuffix = null;
    private BroadcastReceiver mBroadcastReceiver = null;
    private MediaStoreChangeObserver mMediaStoreChangeObserver = null;
    private AutoRefreshListener mAutoRefreshListener = null;
    private Timer mCheckFileTimer = null;
}
