package com.xianglin.station.util;

import com.google.zxing.WriterException;

import android.graphics.Bitmap;
/**
 * 二维码生成类
 * @author longfeng
 * @version $Id: QRGen.java, v 1.0.0 2015年7月31日 下午3:10:45 LF_HH Exp $
 */
public class QRGen {
	//链接地址
	public static String Uri; 
	//二维码图片
	public static Bitmap qrCodeBitmap;
	//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小
	public static Bitmap creatQRGen(String str, int l){
		QRGen.Uri = str;
    	if(!Uri.equals("")){
    		try {
			qrCodeBitmap = EncodingHandler.createQRCode(Uri, l);
			} catch (WriterException e) {
				e.printStackTrace();
			}
    		return qrCodeBitmap;
    	}else {
			return null;
    	}
    }
}
