package com.xianglin.station.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 解决ListView嵌套显示不全类
 * 
 * @author longfeng
 * @version $Id: Utility.java, v 1.0.0 2015年9月25日 下午3:54:38 xl Exp $
 */
public class Utility {
	/*public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			getMeasureHeight((LinearLayout)listItem);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	public static void getMeasureHeight(LinearLayout mLinearLayout) {
		int width = mLinearLayout.getMeasuredWidth();
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		mLinearLayout.measure(widthMeasureSpec, heightMeasureSpec);
	}*/
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {  
            return;  
        }  
  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
        listView.setLayoutParams(params);  
    }  
}
