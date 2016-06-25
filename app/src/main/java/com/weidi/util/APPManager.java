package com.xianglin.station.util;

import android.app.Activity;
import android.os.SystemClock;

import com.xianglin.station.activity.BaseActivity;
import com.xianglin.station.activity.IndexActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description 管理全部activity
 * @Tip 创建activity时调用 APPManager.getInstance().addActivity(this); <br>
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @date 2014-4-25 上午11:22:11
 * @update 2014-7-3 16:06:08 优化：不结束baseactivity 和 indexactivity
 */
public class APPManager {
	private List<Activity> activityList = new LinkedList<Activity>();// 链表结构，有序的，便于删除和添加元素
	private static APPManager instance;

	private APPManager() {
	}

	public static APPManager getInstance() {
		if (instance == null) {
			synchronized (APPManager.class) {// 在同一时刻，只能有一个调用者调用这个方法
				if (instance == null)
					instance = new APPManager();
			}
		}
		return instance;
	}

	public void add(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * @Description 不结束主 activity
	 */
	public void finish() {
		for (Activity activity : activityList) {
			if (!activity.getClass().getName().equals(BaseActivity.class.getName())
					&& !activity.getClass().getName().equals(IndexActivity.class.getName())) {
				activity.finish();
			}
		}
		System.gc();
	}

	public void finishAll() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.gc();
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.gc();
		new Thread(new Runnable() {
			@Override
			public void run() {
				SystemClock.sleep(2000);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).start();
	}

	public List<Activity> getList() {
		return activityList;
	}
}
