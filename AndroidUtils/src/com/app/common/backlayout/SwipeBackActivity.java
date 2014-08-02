
package com.app.common.backlayout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * �����˳�activity
 * ʹ�ò��裺
 * 1���½�һ��activity���̳�SwipeBackActivity��ע��SwipeBackActivity�̳���FragmentActivity��
 * 2������SwipBackLayout����������ʼ����eg:
 * SwipeBackLayout mSwipeBackLayout = null;
 * private void initSwipeBackLayout( ){
 * 		mSwipeBackLayout = getSwipeBackLayout();
 * 		mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
 *           @Override
 *           public void onScrollStateChange(int state, float scrollPercent) {
 *
 *           }
 *
 *           @Override
 *           public void onEdgeTouch(int edgeFlag) {
 *               vibrate(VIBRATE_DURATION);
 *           }
 *
 *           @Override
 *           public void onScrollOverThreshold() {
 *               vibrate(VIBRATE_DURATION);
 *           }
 *       });
 * }
 * 
 * 3��ͨ��mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);����Activity�˳�ģʽ��
 * 4��finish Acitivityʱ����scrollToFinishActivity();������
 * */
public class SwipeBackActivity extends FragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
