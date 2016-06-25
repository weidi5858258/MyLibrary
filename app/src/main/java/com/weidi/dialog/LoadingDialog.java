package com.c1tech.dress.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.c1tech.dress.R;

/**
 * @Description 加载中等待框（纯动画，show即可）
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @date 2014-4-22下午4:40:32
 * @update 2014-6-22 17:32:52
 */
public class LoadingDialog extends Dialog {
	private Context context;
	private ImageView load_ico;

	public LoadingDialog(Context context) {
		super(context, R.style.FullScreenDialog);
		this.context = context;
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
		setContentView(R.layout.loading);
		load_ico = (ImageView) findViewById(R.id.load_round);
		Animation load_ring = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		load_ico.setAnimation(load_ring);
	}

	@Override
	public void dismiss() {
		try {
			if (isShowing()) {
				super.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
