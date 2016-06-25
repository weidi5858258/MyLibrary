package com.c1tech.dress.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import com.c1tech.dress.R;
import com.c1tech.dress.util.APP;
import com.c1tech.dress.view.WheelView;
import com.c1tech.dress.view.wheel.ArrayWheelAdapter;
import com.c1tech.dress.view.wheel.OnWheelChangedListener;

public class ProvincesDialog extends Dialog implements OnWheelChangedListener {

	private String tag = " ProvincesDialog ";
	private Context context;

	private OnDismissListener listener;

	/**
	 * @Description 修改省市区的dialog（用 {@link WheelView} 修改）
	 * @see com.c1tech.dress.view.WheelView
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-5-7 下午1:49:25
	 * @update 2014-6-10 15:40:42
	 */
	public ProvincesDialog(Context context) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
	}

	/**
	 * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	 */
	private JSONObject mJsonObj;
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provinces_citys_dialog);
		initPosition();
		getWindow().setBackgroundDrawableResource(android.R.color.transparent); // 透明，避免黑色背景框
		initJsonData();

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);

		initDatas();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(10);
		mCity.setVisibleItems(10);
		mArea.setVisibleItems(10);
		updateCities();
		// updateAreas();

		initClick();
	}

	private void initPosition() {
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = LayoutParams.MATCH_PARENT;
		window.setAttributes(lp);
		window.setGravity(Gravity.CENTER);
		onWindowAttributesChanged(lp);
	}

	private void initClick() {
		findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showChoose();
			}
		});
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		// for (int i = 0; i < areas.length; i++) {
		// System.out.println("------> 当前市的区有：" + areas[i]);
		// }
		if (areas == null) {
			areas = new String[] { "" };
			mCurrentAreaName = "";
		} else {
			// 更新市时,默认选中第一个区
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[0];

			// 打印当前省市信息，unicode
			System.out.println("\u3010"
							+ mCurrentProviceName
							+ "\u3011\u7701\u0028\u5e02\u002f\u533a\u0029\u9ed8\u8ba4\u9009\u4e2d\u7b2c "
							+ (pCurrent + 1)
							+ " \u4e2a\u5e02\u0028\u533a\u0029\uff0c\u4e3a\u0020\u3010"
							+ mCurrentCityName
							+ "\u3011\n\u8be5\u5e02\u0028\u533a\u0029\u6709\u0020\u3010"
							+ areas.length
							+ "\u3011\u0020\u4e2a\u533a\u0028\u53bf\u0029\uff0c\u9ed8\u8ba4\u9009\u4e2d\u7b2c\u0020\u0031\u0020\u4e2a\uff0c\u4e3a\u3010"
							+ mCurrentAreaName + "\u3011");

		}
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
		mArea.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);

		// for (int i = 0; i < cities.length; i++) {
		// System.out.println("---> 当前省的市有：" + cities[i]);
		// }
		if (cities == null) {
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
		mCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	private void initDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try {
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1) {
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try {
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e) {
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++) {
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e) {
			APP.exception(tag, e);
		}
		mJsonObj = null;
	}

	/**
	 * 从assert文件夹中读取省市区的json文件，然后转化为json对象 <br>
	 * 终于把乱码搞定了，ca！
	 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = context.getAssets().open("china_city.txt");

			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mProvince) {
			updateCities();
		} else if (wheel == mCity) {
			updateAreas();
		} else if (wheel == mArea) {
			try {
				mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
			} catch (Exception e) {
			}
		}
	}

	public void showChoose() {
		// APP.tip(context, mCurrentProviceName + "-" + mCurrentCityName + "-" +
		// mCurrentAreaName);

		// 选择的结果记进行赋值
		listener.OnChoiced(mCurrentProviceName, mCurrentCityName, mCurrentAreaName);
		dismiss();
	}

	public void setOnDismissListener(OnDismissListener l) {
		listener = l;
	}

	public interface OnDismissListener {
		public void OnChoiced(String province, String city, String area);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onBackPressed() {
	}
}
