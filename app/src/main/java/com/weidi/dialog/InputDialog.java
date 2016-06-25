package com.c1tech.dress.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.c1tech.dress.R;
import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.view.ClearEditText;

public class InputDialog extends Dialog implements OnClickListener {

	private TextView sure, cancel;
	private Context context;
	private String title;
	private String holder;
	private String confirmTxt;
	private OnDismissListener dismissListener;
	private ClearEditText edit;

	/**
	 * @Description 输入框
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-7-31 下午5:18:34
	 * @param title
	 *            弹窗标题
	 * @param holder
	 *            输入框占位符
	 * @param confirmTxt
	 *            确认按钮显示字样
	 */
	public InputDialog(Context context, String title, String holder, String confirmTxt) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
		this.title = title;
		this.holder = holder;
		this.confirmTxt = confirmTxt;
		initView();
		initWindow();
		setCancelable(true);
		setCanceledOnTouchOutside(false);
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
		setContentView(R.layout.input_dialog);
		edit = (ClearEditText) findViewById(R.id.dialog_txt);
		edit.setHint(holder);
		((TextView) findViewById(R.id.dialog_title)).setText(title);
		cancel = (TextView) findViewById(R.id.dialog_cancel);
		sure = (TextView) findViewById(R.id.dialog_sure);
		sure.setText(confirmTxt);

		cancel.setOnClickListener(this);
		sure.setOnClickListener(this);
	}

	/**
	 * <p>
	 * 
	 * @Title: setOnDismissListener
	 *         </p>
	 *         <p>
	 * @Description: 自定义dismiss监听
	 *               </p>
	 * 
	 * @param l
	 * @see android.app.Dialog#setOnDismissListener(android.content.DialogInterface.OnDismissListener)
	 */
	public void setOnDismissListener(OnDismissListener l) {
		dismissListener = l;

	}

	/**
	 * @param clicked
	 *            点击了【确认】或者【取消】其中一个按钮，而不是按的返回键
	 * @param confirmed
	 *            点击的是【确认】按钮
	 * @param input
	 *            输入的文本
	 */
	public interface OnDismissListener {
		public void OnConfirmed(Boolean confirmed, String input);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_cancel:
			dismissListener.OnConfirmed(false, null);
			dismiss();
			break;
		case R.id.dialog_sure:
			dismissListener.OnConfirmed(true, BaseActivity.getTextFromView(edit));
			dismiss();
			break;
		}

	}

}
