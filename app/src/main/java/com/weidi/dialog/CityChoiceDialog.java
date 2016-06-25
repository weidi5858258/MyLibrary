package com.c1tech.dress.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.c1tech.dress.R;
import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.adapter.CityChoiceDialogAdapter;
import com.c1tech.dress.bean.CitiesBean;
import com.c1tech.dress.net.SyncApi;
import com.c1tech.dress.util.APP;
import com.c1tech.dress.util.APPCode;
import com.c1tech.dress.util.ErrorCode;
import com.c1tech.dress.util.Sys;

public class CityChoiceDialog extends Dialog {

	private Context context;
	private int marginTop;
	private List<CitiesBean> citys_list;
	private OnDismissListener dismissListener;
	private GridView gridview;
	private CityChoiceDialogAdapter adapter;
	private ImageView back; // 返回按钮
	private TextView city; // 城市名
	private Handler handler;
	private TextView loading;

	/**
	 * @Description 首页城市选择 dialog
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-6 下午5:48:52
	 * @update 2014-6-25 10:39:41
	 */
	public CityChoiceDialog(Context context, int marginTop) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
		this.marginTop = marginTop;
		setCancelable(true);//点击“Back”键不能关闭这个Dialog
		setCanceledOnTouchOutside(true);//点击外部区域时可以关闭这个Dialog
		handler = new Handler();
		initView();
		initPosition();
		initAdapter();
		getCityList();
	}

	private void initAdapter() {
		citys_list = new ArrayList<CitiesBean>();
		if (adapter == null) {
			adapter = new CityChoiceDialogAdapter(context, citys_list);
			gridview.setAdapter(adapter);
			gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//这边点击一个条目后，只要用户覆写了OnChosed()方法，那么就会把下面相关参数传递到用户操作的OnChosed()方法中去
					dismissListener.OnChosed(true, position, citys_list.get(position));
					dismiss();
				}
			});
		}

	}

	//得到城市
	private void getCityList() {
		citys_list.clear();
		BaseActivity.singlePool().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String json = SyncApi.getCities();//从服务器上得到城市列表，以json格式返回结果
					//如果一个字符串不是json格式，但是用它去生成了一个JSONObject对象，那么这个对象是否为空？？？
					JSONObject object = APP.checkReturnData(json, context);
					if (object != null && object.has("status") && object.getInt("status") == ErrorCode.SUCCESS) {
						JSONArray array = object.getJSONArray("data");//得到一个字符串数组
						for (int i = 0; i < array.length(); i++) {
							object = array.getJSONObject(i);
							citys_list.add(new CitiesBean(object.getString("id"), 
														  object.getString("name"),
														  object.getString("ps")));
						}
					}
//					System.out.println("CityChoiceDialog-getCityList()城市共有 " + citys_list.size() + " 个");
				} catch (Exception e1) {
					loading.clearAnimation();
					loading.setAlpha(0);//动画不可见
					APP.tip(context, "城市刷新失败，请重试");
					dismiss();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						loading.clearAnimation();
						loading.setAlpha(0);//“0”表示完全透明，“1”表示完全不透明 
						adapter.notifyDataSetChanged();
					}
				});

			}
		});
	}

	private void initPosition() {
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		// lp.dimAmount = 0.0f; // 不变暗
		lp.x = 0;
		lp.y = marginTop;
		lp.width = LayoutParams.MATCH_PARENT;//弹出框宽度填充整个父窗口
		lp.height = Sys.getScreenHeight(context) * 2 / 5;
		window.setAttributes(lp);
		window.setGravity(Gravity.TOP);
		window.setWindowAnimations(R.style.top_in_top_out);
		onWindowAttributesChanged(lp);
	}

	private void initView() {
		//发现一件事：点击“城市”按钮后，弹出的框是连带着标题栏一起出现的。这个从距离顶部多少位置后发现的。
		//因此难怪要隐藏“后退”按钮，因为“后退”按钮本来就是隐藏的，不需要再次隐藏，只有标题栏被重新开启后才需要再次隐藏。
		setContentView(R.layout.city_dialog);
		back = (ImageView) findViewById(R.id.back);
		city = (TextView) findViewById(R.id.city);
		gridview = (GridView) findViewById(R.id.gridview);
		loading = (TextView) findViewById(R.id.prog);//此时只是一个不会动的图片
		//现在开启动画让这个图片动起来
		loading.setAnimation(AnimationUtils.loadAnimation(context, R.anim.loading_animation));
		back.setVisibility(View.GONE);
		city.setVisibility(View.VISIBLE);
		city.setText(context.getSharedPreferences(APPCode.CITY, context.MODE_APPEND).getString("city_name", "全国"));
	}

	/**
	 * <p>
	 * Title: setOnDismissListener
	 * </p>
	 * <p>
	 * Description: 自定义dismiss监听
	 * </p>
	 * 
	 * @param l
	 * @see android.app.Dialog#setOnDismissListener(android.content.DialogInterface.OnDismissListener)
	 */
	public void setOnDismissListener(OnDismissListener l) {
		dismissListener = l;

	}

	/**
	 * @param chosed
	 *            点击了某一条选项
	 * @param position
	 *            点击的位置
	 * @param bean
	 *            CitiesBean
	 */
	public interface OnDismissListener {
		public void OnChosed(Boolean chosed, int position, CitiesBean bean);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onBackPressed() {
		dismissListener.OnChosed(false, -1, null);
		dismiss();
	}

}
