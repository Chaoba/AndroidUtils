package com.mingy.android.input;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * ���뷨������
 * 
 * */
public class InputMethodUtils {
    public InputMethodUtils( Context context ){
        mContext = context;
        mInputMethodManager = ( InputMethodManager )mContext.getSystemService( Context.INPUT_METHOD_SERVICE );
    }
    
    /**
     * ��ʾ���뷨
     * 
     * @param view
     * 
     * */
    public void showInputMethod( View view ){
        if( null != view ){
            mInputMethodManager.showSoftInput( view, InputMethodManager.SHOW_FORCED );
            mIsInputMethodShow = true;
        }
    }
    
    /**
     * ��ʾ���뷨
     * 
     * @param view
     * 
     * */
    public void hideInputMethod( View view ){
        if( null != view ){
            mInputMethodManager.hideSoftInputFromWindow( view.getWindowToken( ), 0 );
            mIsInputMethodShow = false;
        }
    }
    
    /**
     * �л����뷨״̬
     * 
     * 
     * @param view
     * */
    public void toggle( View view ){
        mIsInputMethodShow = !mIsInputMethodShow;
        if( mIsInputMethodShow ){
            showInputMethod( view );
        }else{
            hideInputMethod( view );
        }
    }
    
    private boolean mIsInputMethodShow = false;
    private InputMethodManager mInputMethodManager = null;
    private Context mContext = null;
}
