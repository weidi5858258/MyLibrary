package com.xianglin.station.util;

import java.util.ArrayList;
import java.util.List;

import com.xianglin.mobile.common.logging.LogCatLog;
import com.xianglin.station.R;
import com.xianglin.station.dialog.DialogUnifiedFragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import cn.koolcloud.ipos.appstore.service.aidl.IMSCService;
import cn.koolcloud.ipos.appstore.service.aidl.ParcelableApp;

public class CheckUpdateUtil {
	private final String TAG = "CheckUpdateUtil";
	private IMSCService mIService;
	private Context mContext;
	private String mPackageName;
	private String mFmscPackageName;
	private ACache mACache;
	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIService = IMSCService.Stub.asInterface(service);
			new CheckVersionTask().execute(mPackageName, mFmscPackageName);
		}

		public void onServiceDisconnected(ComponentName name) {
			mIService = null;
		}
	};

	public CheckUpdateUtil(Context context, String packageName, String fmscPackageName) {
		mContext = context;
		mPackageName = packageName;
		mFmscPackageName = fmscPackageName;
		Intent service = new Intent(IMSCService.class.getName());
		mContext.bindService(service, connection, Context.BIND_AUTO_CREATE); // 启动服务
	}

	public void openAppDetail(ParcelableApp app) {
		try {
			mIService.openAppDetail(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CheckVersion(String packageName, String fmscPackageName) {
		new CheckVersionTask().execute(packageName, fmscPackageName);
	}

	/**
	 * 根据 Package Name 检测新版本
	 */
	private ParcelableApp checkNewVersion(String packageName) {
		ParcelableApp appUpdateInfo = null;
		if (mIService != null) {
			try {
				int versionCode = getAppVersionCode(mContext, packageName);
				appUpdateInfo = mIService.checkUpdate(packageName, versionCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return appUpdateInfo;
	}

	/**
	 * 使用完AIDL服务后，请解绑定!
	 */
	public void unbindService() {
		if (connection != null && mContext != null){
			mContext.unbindService(connection);
			connection = null;
		}
	}

	public static int getAppVersionCode(Context context, String pageName) {
		int versionName = 0;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(pageName, 0);
			versionName = pi.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static String getAPPVersionName(Context context, String packageName) {
		String versionName = "1.0";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			versionName = pi.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	private long statrTime;
	private long endTime;

	private class CheckVersionTask extends AsyncTask<String, Void, List<ParcelableApp>> {
		@Override
		protected void onPreExecute() {
			statrTime = System.currentTimeMillis();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(final List<ParcelableApp> result) {
			endTime = System.currentTimeMillis() - statrTime;
			LogCatLog.i(TAG, endTime + "");
			if (result.get(0) != null && mContext != null && !((Activity) mContext).isFinishing()) {
				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						final DialogUnifiedFragment dialog = new DialogUnifiedFragment();
						dialog.addContext(mContext);
						dialog.addTitle(mContext.getString(R.string.update_title));
						dialog.addContent(mContext.getString(R.string.version_now,
								getAPPVersionName(mContext, mPackageName))
								+ "\n" + mContext.getString(R.string.version_new, result.get(0).getVersion()));
						dialog.addButton(1, mContext.getString(R.string.goto_update), new OnClickListener() {

							@Override
							public void onClick(View view) {
								try {
									mIService.openAppDetail(result.get(0));
									finishActivity();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, false);
						dialog.show(((Activity) mContext).getFragmentManager(), "checkUpdate");
						endTime = System.currentTimeMillis() - statrTime;
						LogCatLog.i(TAG, endTime + "");
					}
				});

			} else if (result.get(1) != null && mContext != null && !((Activity) mContext).isFinishing()) {
				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						final DialogUnifiedFragment dialog = new DialogUnifiedFragment();
						dialog.addContext(mContext);
						dialog.addTitle(mContext.getString(R.string.update_title));
						dialog.addContent(mContext.getString(R.string.version_now,
								getAPPVersionName(mContext, mFmscPackageName))
								+ "\n" + mContext.getString(R.string.version_new, result.get(1).getVersion()));
						dialog.addButton(1, mContext.getString(R.string.goto_update), new OnClickListener() {

							@Override
							public void onClick(View view) {
								try {
									mIService.openAppDetail(result.get(1));
									finishActivity();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, false);
						dialog.show(((Activity) mContext).getFragmentManager(), "checkUpdate");
						endTime = System.currentTimeMillis() - statrTime;
						LogCatLog.i(TAG, endTime + "");
					}
				});
			} else {

			}
			super.onPostExecute(result);
		}

		@Override
		protected List<ParcelableApp> doInBackground(String... params) {
			List<ParcelableApp> list = new ArrayList<ParcelableApp>();
			list.add(checkNewVersion(params[0]));
			list.add(checkNewVersion(params[1]));
			return list;
		}
	}

	private void finishActivity() {
		APPManager.getInstance().exit();
		if (mACache != null) {
			mACache.clear();
		}
	}
}
