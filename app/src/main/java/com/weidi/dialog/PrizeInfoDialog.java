package com.c1tech.dress.dialog;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.c1tech.dress.R;
import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.share.Share;
import com.c1tech.dress.util.APP;
import com.c1tech.dress.util.Constant;
import com.c1tech.dress.util.LBS;
import com.c1tech.dress.util.MyDate;
import com.c1tech.dress.util.NumArithmetic;
import com.c1tech.dress.util.ScreenShot;
import com.c1tech.dress.util.Sys;

/**
 * 获得优惠后的dialog
 * 
 * @author ly
 * @updated 2014-9-2 16:49:00 新增截屏后分享
 *          <hr>
 *          2014-8-28 15:06:50 更新计算两点距离的方法
 *          <hr>
 *          at 2014-7-23 18:20:30 by ceychen@foxmail.com
 */
public class PrizeInfoDialog extends Dialog implements android.view.View.OnClickListener {

	private Context context;
	private Button dialog_exit, prize_info_now_buy, price_info_add_buycar;
	private TextView prize_info_jiang, juli, share;
	private int goodsID, actID, agmId;
	private double lat, lng;
	private double cutPrices;// 减价多少元
	private NumArithmetic numArithmetic = new NumArithmetic();

	public PrizeInfoDialog(Context context) {
		super(context);
	}

	public PrizeInfoDialog(Context context, double cutPrices, int goodsID, int actID, int agmId, double lat, double lng) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.cutPrices = numArithmetic.div(cutPrices, 1.0, 2);// 取 2 位
		this.goodsID = goodsID;
		this.actID = actID;
		this.agmId = agmId;
		this.lat = lat;
		this.lng = lng;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.prize_info_dialog);

		dialog_exit = (Button) findViewById(R.id.dialog_exit);
		prize_info_jiang = (TextView) findViewById(R.id.prize_info_jiang);
		share = (TextView) findViewById(R.id.share);
		juli = (TextView) findViewById(R.id.juli);
		prize_info_now_buy = (Button) findViewById(R.id.prize_info_now_buy);
		price_info_add_buycar = (Button) findViewById(R.id.price_info_add_buycar);

		prize_info_jiang.setText(cutPrices + "元优惠");
		dialog_exit.setOnClickListener(this);
		share.setOnClickListener(this);
		prize_info_now_buy.setOnClickListener(this);
		price_info_add_buycar.setOnClickListener(this);
		juli.setText("离你约有" + LBS.getDistance(lat, lng, Constant.lat, Constant.lng) + "公里");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.dialog_exit:
			this.dismiss();
			break;

		case R.id.prize_info_now_buy:
			BaseActivity.doAddtoCart4ActionAndBuy(context, goodsID, actID, agmId);
			dismiss();
			break;

		case R.id.price_info_add_buycar:
			BaseActivity.addToCartList(context, goodsID, actID, agmId);
			dismiss();
			break;

		case R.id.share:
			new Share(context).doit("哈哈，我刚才中奖啦，有图有真相，特意分享给大家，请羡慕。", APP.host+"/item-" + goodsID + ".html ",
					ScreenShot.getScreenShootPath((Activity) context));
			break;
		}
	}
}