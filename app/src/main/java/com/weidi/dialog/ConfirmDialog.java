package com.c1tech.dress.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.c1tech.dress.R;

public class ConfirmDialog extends Dialog implements OnClickListener {

	private TextView sure, cancel;
	private Context context;
	private String title;
	private String msg;
	private String confirmText;
	private OnDismissListener dismissListener;

	/**
	 * @Description 确认框（询问框）
	 * @param context
	 * @param confirmTxt
	 *            【确定】按钮上显示的文本
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-5-5 16:56:27
	 * @update 2014-6-22 17:02:07
	 */
	public ConfirmDialog(Context context, String title, String msg, String confirmText) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
		this.title = title;
		this.msg = msg;
		this.confirmText = confirmText;
		initView();
		initWindow();
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		//android.R.color.transparent:#00000000
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
		setContentView(R.layout.confirm_dialog);
		((TextView) findViewById(R.id.dialog_title)).setText(title);
		((TextView) findViewById(R.id.dialog_txt)).setText(msg);
		cancel = (TextView) findViewById(R.id.dialog_cancel);
		sure = (TextView) findViewById(R.id.dialog_sure);
		sure.setText(confirmText);
		
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
	 * @param @param clicked 点击了【确认】或者【取消】其中一个按钮，而不是按的返回键
	 * @param @param confirmed 点击的是【确认】按钮
	 */
	public interface OnDismissListener {
		public void OnConfirmed(Boolean confirmed);
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
			dismissListener.OnConfirmed(false);
			dismiss();
			break;
		case R.id.dialog_sure:
			dismissListener.OnConfirmed(true);
			dismiss();
			break;
		}

	}

}
