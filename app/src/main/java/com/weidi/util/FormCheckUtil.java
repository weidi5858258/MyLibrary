package com.xianglin.station.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import com.xianglin.station.R;

/**
 * 表单处理,目前支持TextView、EditText、RadioButton
 * 
 * @author licaiyi
 * @version $Id: FormHandler.java, v 1.0.0 2015-10-13 下午2:35:28 xl Exp $
 */
public class FormCheckUtil {
	private final String TAG = FormCheckUtil.class.getSimpleName();

	private List<View> mViews; // view 集合
	private Map<Integer, ArrayList<String>> mPreDataMap; // 缓存数据
	private Map<Integer, ArrayList<String>> mIsDataChangeMap; // 获取改变数据
	private Context mContext;
	private final String VIEW_TYPE_BUTTON = "button"; // 设置类型为button
	private final String VIEW_TYPE_TEXTVIEW = "textview"; // 设置类型为textview

	private final String BUTTON_IS_CLICKED = "clicked";
	private final String BUTTON_IS_NOT_CLICKED = "no_clicked";

	public FormCheckUtil(Context context) {
		mContext = context;
		initData();
	}

	private void initData() {
		mViews = new ArrayList<View>();
		mPreDataMap = new HashMap<Integer, ArrayList<String>>();
		mIsDataChangeMap = new HashMap<Integer, ArrayList<String>>();
	}

	/**
	 * 添加View
	 * 
	 * @param view
	 * @param label
	 */
	public void addView(View view, String label) {
		mViews.add(view);
		ArrayList<String> list = new ArrayList<String>();
		list.add(label);

		if (view instanceof RadioButton) { // 如果是button，获取button内容
			final RadioButton button = (RadioButton) view;
			list.add(button.getText().toString()); // 添加标签
			if (button.isChecked()) { // 记录当前状态是否为选中
				list.add(BUTTON_IS_CLICKED);
			} else {
				list.add(BUTTON_IS_NOT_CLICKED);
			}

			button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (button.isChecked()) {
						ArrayList<String> list = new ArrayList<String>();
						list.add(mPreDataMap.get(button.getId()).get(0)); // 添加label
						list.add(mPreDataMap.get(button.getId()).get(1)); // 添加内容
						list.add(VIEW_TYPE_BUTTON); // 添加类型
						mIsDataChangeMap.put(button.getId(), list);
					} else {
						if (mIsDataChangeMap.containsKey(button.getId())) {
							mIsDataChangeMap.remove(button.getId());
						}
					}
				}
			});
		} else if (view instanceof TextView) { // 如果是textview 获取textview内容
			final TextView textView = (TextView) view;
			list.add(textView.getText().toString());
			textView.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (TextUtils.isEmpty(mPreDataMap.get(textView.getId()).get(1)) && TextUtils.isEmpty(s.toString().trim()))
						return;
					if (!TextUtils.equals(mPreDataMap.get(textView.getId()).get(1), textView.getText().toString())) {
						if (mIsDataChangeMap.containsKey(textView.getId())) {
							mIsDataChangeMap.remove(textView.getId());
						}
						ArrayList<String> list = new ArrayList<String>();
						list.add(mPreDataMap.get(textView.getId()).get(0));
						list.add(textView.getText().toString());
						list.add(VIEW_TYPE_TEXTVIEW);
						mIsDataChangeMap.put(textView.getId(), list);
					} else {
						if (mIsDataChangeMap.containsKey(textView.getId())) {
							mIsDataChangeMap.remove(textView.getId());
						}
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});
		} else { // 其他模式则获取一个id
			list.add(view.getId() + "");
		}
		mPreDataMap.put(view.getId(), list);
	}

	public String getFormChange() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry entry : mIsDataChangeMap.entrySet()) {
			boolean btIsChanged = VIEW_TYPE_BUTTON.equals(mIsDataChangeMap.get(entry.getKey()).get(2))
					&& BUTTON_IS_NOT_CLICKED.equals(mPreDataMap.get((Integer) (entry.getKey())).get(2));
			boolean tvIsChanged = (VIEW_TYPE_TEXTVIEW.equals(mIsDataChangeMap.get(entry.getKey()).get(2))
					&& !mPreDataMap.get((Integer) (entry.getKey())).get(1).equals(mIsDataChangeMap.get(entry.getKey()).get(1)));
			if (btIsChanged || tvIsChanged) {
				if (sb.length() == 0) {
					sb.append(mIsDataChangeMap.get(entry.getKey()).get(0));
					sb.append(mContext.getResources().getString(R.string.modify_for));
					sb.append(TextUtils.isEmpty(mIsDataChangeMap.get(entry.getKey()).get(1)) ? "空" : mIsDataChangeMap.get(entry.getKey()).get(1));
				} else {
					sb.append("," + mIsDataChangeMap.get(entry.getKey()).get(0));
					sb.append(mContext.getResources().getString(R.string.modify_for));
					sb.append(TextUtils.isEmpty(mIsDataChangeMap.get(entry.getKey()).get(1)) ? "空" : mIsDataChangeMap.get(entry.getKey()).get(1));
				}
			}
		}
		if (sb.length() > 0)
			sb.insert(0, mContext.getResources().getString(R.string.modified));
		return sb.toString();
	}

}
