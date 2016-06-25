package com.xianglin.station.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.xianglin.mmgw.service.ArchRequest;
import com.xianglin.mmgw.service.ArchResponse;
import com.xianglin.mobile.common.info.DeviceInfo;
import com.xianglin.station.adapter.HorizontalListViewAdapter;
import com.xianglin.station.adapter.MyListViewAdapter;
import com.xianglin.station.bean.ImageBean;
import com.xianglin.station.rpc.remote.SyncApi;
import com.xianglin.station.rpc.remote.SyncApi.CallBack;
import com.xianglin.station.rpc.service.LoanService;
import com.xianglin.xlStation.base.model.BaseRespModel;

import java.util.List;

public class ImageLoadTask extends AsyncTask<Void, Void, Void> implements CallBack{
	private static final int ONE = 1;
	private MyListViewAdapter adapter;
	private List<ArchRequest> archRequestList;
	private Context context;
	private ArchResponse archResponse;
	private ImageBean bean;
	
	private HorizontalListViewAdapter horizontalAdapter;
	private boolean isHList = false;
	/*
	 * 预申请页面构造函数
	 */
	public ImageLoadTask(Context context,MyListViewAdapter adapter, List<ArchRequest> archRequestList) {
		this.context = context;
		this.adapter = adapter;
		this.archRequestList = archRequestList;
	}
	
	/*
	 * 合同上传构造函数
	 */
	public ImageLoadTask(Context context, HorizontalListViewAdapter horizontalAdapter, List<ArchRequest> arcRequest, boolean isHList) {
		this.context = context;
		this.horizontalAdapter = horizontalAdapter;
		this.archRequestList = arcRequest;
		this.isHList = isHList;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if(!isHList){
			for (int i = 0; i < adapter.getCount(); i++) {
				bean = (ImageBean) adapter.getItem(i);
				if(archRequestList.get(i) != null){
//				getArch(archRequestList.get(i));
					if (!(context instanceof Activity)) {
						return null;
					}
					archResponse = SyncApi.getInstance().getArch((Activity)context, archRequestList.get(i));
					if(archResponse != null){
						byte[] b = archResponse.getArchData();
						if (b!= null && b.length != 0) {
							bean.setBitmap(createBitmap(b,140,170));
						}
					}
				}
				bean.setNeedBar(false);
				publishProgress(); // 通知去更新UI
			}
			return null;
		}else{
			for (int i = 0; i < horizontalAdapter.getCount(); i++) {
				bean = (ImageBean) horizontalAdapter.getItem(i);
				if(archRequestList.get(i) != null){
//				getArch(archRequestList.get(i));
					if (!(context instanceof Activity)) {
						return null;
					}
					archResponse = SyncApi.getInstance().getArch((Activity)context, archRequestList.get(i));
					if(archResponse != null){
						byte[] b = archResponse.getArchData();
						if (b!= null && b.length != 0) {
							bean.setBitmap(createBitmap(b,80,80));
						}
					}
				}
				bean.setNeedBar(false);
				publishProgress(); // 通知去更新UI
			}
			return null;
		}
	}

	public void onProgressUpdate(Void... voids) {
		if (isCancelled()) {
			return;
		}
		// 更新UI
		if(!isHList){
			adapter.notifyDataSetChanged();
		}else{
			horizontalAdapter.notifyDataSetChanged();
		}
	}
	
	/**
     * @param data 数据或路径
     * @param width 目标宽
     * @param height 目标高
     * @return 图片
     */
    private Bitmap createBitmap(Object data, int width, int height) {
        Options options = new Options();
        int scale = 1;

        if (width > 0 && height > 0) {//创建目标大小的图片
            options.inJustDecodeBounds = true;
            if (data instanceof String) {
                BitmapFactory.decodeFile((String) data, options);
            } else {
                BitmapFactory.decodeByteArray((byte[]) data, 0, ((byte[]) data).length, options);
            }
            int dw = options.outWidth / width;
            int dh = options.outHeight / height;
            scale = Math.max(dw, dh);

            options = new Options();
        }

        options.inDensity = DeviceInfo.getInstance().getDencity();
        options.inScaled = true;
        options.inPurgeable = true;
        options.inSampleSize = scale;

        Bitmap bitmap = null;
        if (data instanceof String) {
            bitmap = BitmapFactory.decodeFile((String) data, options);
        } else {
            bitmap = BitmapFactory.decodeByteArray((byte[]) data, 0, ((byte[]) data).length,
                options);
        }
        return bitmap;
    }
    
    public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ONE:
				if(archResponse != null){
					byte[] b = archResponse.getArchData();
					if (b!= null && b.length != 0) {
						bean.setBitmap(createBitmap(b,80,80));
						bean.setNeedBar(false);
						publishProgress(); // 通知去更新UI
					}
				}
				break;
			}
		}
	};
    
	 public void getArch(ArchRequest archRequest) {
		 if (!(context instanceof Activity)) {
			 return;
		 }
	    	SyncApi.getInstance().backgroundInterface(LoanService.class, "getArch", (Activity) context, archRequest, this);
//	    	SyncApi.getInstance().getArch(context, archRequest, this);
		}

	@Override
	public void preHandle() {
		
	}

	@Override
	public void success(BaseRespModel baseRespModel) {
		archResponse = (ArchResponse) baseRespModel;
		handler.sendEmptyMessage(ONE);
	}

	@Override
	public void failed(int type) {
		handler.sendEmptyMessage(ONE);
	}

	@Override
	public void postHandle() {
		
	}
}
