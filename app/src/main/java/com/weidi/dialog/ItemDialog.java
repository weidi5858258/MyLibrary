package com.c1tech.dress.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.c1tech.dress.R;
import com.c1tech.dress.adapter.ItemDialogAdapter;
import com.c1tech.dress.view.CustomerListView;

public class ItemDialog extends Dialog {

	private Context context;
	private String title;
	private String[] items;
	private OnDismissListener dismissListener;

	/**
	 * @Description 列表选择框
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-5-21 上午10:46:57
	 * @update 2014-6-22 16:55:45
	 */
	public ItemDialog(Context context, String title, String[] items) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
		this.title = title;
		this.items = items;
		initView();
		initWindow();
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent); // 透明，避免黑色背景框
	}

	private void initWindow() {
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.dimAmount = 0.0f; // 不变暗
		window.setAttributes(lp);
		window.setWindowAnimations(R.style.top_in_top_out);
		onWindowAttributesChanged(lp);
	}

	private void initView() {
		setContentView(R.layout.item_dialog);
		((TextView) findViewById(R.id.dialog_title)).setText(title);

		ItemDialogAdapter adapter = new ItemDialogAdapter(context, items);

		((CustomerListView) findViewById(R.id.listview)).setAdapter(adapter);

		((CustomerListView) findViewById(R.id.listview)).setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismissListener.OnChosed(true, position);
				dismiss();
			}
		});
	}

	/**
	 * <p>
	 * 
	 * @Title setOnDismissListener
	 *        </p>
	 *        <p>
	 * @Description 自定义dismiss监听
	 *              </p>
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
	 */
	public interface OnDismissListener {
		public void OnChosed(Boolean chosed, int position);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onBackPressed() {
		dismissListener.OnChosed(false, -1);
		dismiss();
	}

}
