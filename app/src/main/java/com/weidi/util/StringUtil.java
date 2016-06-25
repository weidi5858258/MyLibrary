package com.xianglin.station.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xianglin.mobile.common.info.DeviceInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.Editable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 工具类
 * 
 * @author licaiyi update by huang yang 2015/10/06
 * @version $Id: StringUtil.java, v 1.0.0 2015-8-11 下午1:40:35 xl Exp $
 */
public class StringUtil {

	/**
	 * 是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	/**
	 * 通过{n},格式化.
	 * 
	 * @param src
	 * @param objects
	 * @return
	 */
	public static String format(String src, Object... objects) {
		int k = 0;
		for (Object obj : objects) {
			src = src.replace("{" + k + "}", obj.toString());
			k++;
		}
		return src;
	}

	/**
	 * parse String to int
	 * 
	 * @param str
	 * @param defaultInt
	 * @return int
	 */
	public static int getInt(String str, int defaultInt) {
		if (str == null) {
			return defaultInt;
		}
		try {
			defaultInt = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}

		return defaultInt;
	}

	/**
	 * parse String to float
	 * 
	 * @param str
	 * @param defaultFloat
	 * @return float
	 */
	public static float getFloat(String str, float defaultFloat) {
		if (str == null) {
			return defaultFloat;
		}
		try {
			defaultFloat = Float.parseFloat(str);
		} catch (NumberFormatException e) {
		}

		return defaultFloat;
	}

	/**
	 * parse String to long
	 * 
	 * @param str
	 * @param defaultLong
	 * @return defaultLong
	 */
	public static long getLong(String str, long defaultLong) {
		if (str == null) {
			return defaultLong;
		}
		try {
			defaultLong = Long.parseLong(str);
		} catch (NumberFormatException e) {
		}

		return defaultLong;
	}

	/**
	 * 
	 * float保留小数点位数函数
	 * 
	 * @param floatValue
	 * @param format
	 *            保留小数格式
	 * @return
	 */
	public static String decimalFormat(float floatValue, String decimal) {
		DecimalFormat decimalFormat = new DecimalFormat(decimal);
		String str = decimalFormat.format(floatValue);
		return str;
	}

	/**
	 * 
	 * double保留小数点位数函数
	 * 
	 * @param floatValue
	 * @param num
	 *            小数点后几位
	 * @return
	 */
	public static String decimalFormat(double floatValue, int num) {
		BigDecimal decimal = new BigDecimal(floatValue);
		BigDecimal amount = decimal.setScale(num, BigDecimal.ROUND_HALF_UP);
		return amount.toString();
	}

	/**
	 * 格式化金额显示，小数点前面数字每隔3位添加“,”号
	 * 
	 * @param value
	 * @return
	 */
	public static String formatForMoney(String value) {
		if (value == null) {
			return "0.00";
		}
		String str = "0.00";
		try {
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			value = value.replaceAll(",", "");
			BigDecimal bigDecimal = new BigDecimal(value);
			str = decimalFormat.format(bigDecimal);
		} catch (Exception e) {
			str = "0.00";
		}

		return str;
	}

	/**
	 * 格式化金额显示，整数前面数字每隔3位添加“,”号
	 * 
	 * @param value
	 * @return
	 */
	public static String formatForMoneyInt(String value) {
		if (value == null) {
			return "0";
		}
		String str = "0";
		try {
			DecimalFormat decimalFormat = new DecimalFormat("#,##0");
			value = value.replaceAll(",", "");
			BigDecimal bigDecimal = new BigDecimal(value);
			str = decimalFormat.format(bigDecimal);
		} catch (Exception e) {
			str = "0";
		}

		return str;
	}

	/**
	 * 格式化金额显示，整数前面数字每隔3位添加“,”号
	 * 
	 * @param value
	 * @return
	 */
	public static String formatForMoneyByDouble(Double value) {
		if (value == null) {
			return "0";
		}
		String str = "0.00";
		try {
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			BigDecimal bigDecimal = new BigDecimal(value);
			str = decimalFormat.format(bigDecimal);
		} catch (Exception e) {
			str = "0.00";
		}
		return str;
	}

	public static String insertString(String obj, String insert, int len) {
		if (obj == null || insert == null || obj.length() < len || len < 0) {
			return obj;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(obj).insert(len, insert);
		return sb.toString();
	}

	/**
	 * 替换target中[start, end]区间的所有字符，用*代替
	 * 
	 * @param target
	 * @param start
	 * @param end
	 * @return
	 */
	public static String coverString(String target, int start, int end) {
		if (target == null || start < 0 || end - start < 0 || end >= target.length()) {
			// 如果区间不合法，返回原字符串
			return target;
		}
		StringBuffer sb = new StringBuffer(target);
		for (int i = 0; i < end - start + 1; i++) {
			sb.replace(start + i, start + i + 1, "*");
		}
		return sb.toString();
	}

	/**
	 * 根据width 和 Paint 分割字符串
	 * 
	 * @param content
	 * @param p
	 * @param width
	 * @return
	 */
	public static String[] autoSplit(String content, Paint p, float width) {
		int length = content.length();
		float textWidth = p.measureText(content);
		if (textWidth <= width) {
			return new String[] { content };
		}

		int start = 0, end = 1, i = 0;
		int lines = (int) Math.ceil(textWidth / width);
		String[] lineTexts = new String[lines];
		while (start < length) {
			if (p.measureText(content, start, end) > width) {
				lineTexts[i++] = (String) content.subSequence(start, end);
				start = end;
			}
			if (end == length) {
				lineTexts[i] = (String) content.subSequence(start, end);
				break;
			}
			end += 1;
		}
		return lineTexts;
	}

	/**
	 * 根据Paint 获取字符高度
	 * 
	 * @param paint
	 * @return
	 */
	public static int getStringHeight(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

	/**
	 * 接口拼接
	 * 
	 * @param map
	 * @return
	 */
	public static String getParamerters(HashMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map != null && !map.isEmpty()) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				if (i != map.size() - 1) {
					sb.append("&");
				}
				i++;
			}
		}
		return sb.toString();
	}

	/**
	 * 格式化大数据类型
	 * 
	 * @param value
	 * @return
	 */
	public static String formatForLPMSPoint(String value) {
		if (TextUtils.isEmpty(value)) {
			return "0";
		}
		String str = "0";
		try {
			if (value.length() >= 8) {
				str = (long) Long.parseLong(value) / 10000000 + "千万";
			} else if (value.length() >= 5) {
				str = (int) Integer.parseInt(value) / 10000 + "万";
			} else {
				str = value;
			}
		} catch (Exception e) {
		}

		return str;
	}

	/**
	 * 每隔几位添加空格
	 * 
	 * @param input
	 *            输入数据
	 * @param number
	 *            空格位数
	 * @return output 输出字符串
	 */
	public static String formatSpace(String input, int number) {
		String regex = "(.{" + String.valueOf(number) + "})";
		String output = input.replaceAll(regex, "$1 ");
		return output;
	}

	/**
	 * 银行卡号脱敏
	 * 
	 * @param pan
	 * @return
	 */
	public static String panSensitive(String pan) {
		if (pan.length() < 18) {
			return pan;
		}
		return pan.substring(0, 4) + "******" + pan.substring(pan.length() - 4, pan.length());
	}

	/**
	 * 身份证号脱敏处理
	 * 
	 * @return
	 */
	public static String wipeSensitivity(String number) {
		if (isEmpty(number) || number.length() <= 6)
			return number;
		String frontPart = number.substring(0, number.length() - 6); // 截取前面12位
		return frontPart + "******";
	}

	/**
	 * BigDecimal转String
	 * 
	 * @param number
	 * @param precision
	 *            精度
	 * @return
	 */
	public static String bigDecimal2String(BigDecimal number, int precision) {
		if (number == null)
			return "";
		String mNunber = number.toString();
		BigDecimal bigDecimal = new BigDecimal(mNunber);
		return bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * String 转 BigDecimal
	 * 
	 * @param num
	 * @return
	 */
	public static BigDecimal string2BigDecimal(String num) {
		if (TextUtils.isEmpty(num))
			return null;
		return new BigDecimal(num);
	}

	/**
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * 校验RadioGroup是否被选中
	 * 
	 * @param group
	 * @return
	 */
	public static boolean checkRadioGroupSelected(RadioGroup group) {
		if (group.getCheckedRadioButtonId() == -1)
			return false;
		return true;
	}

	/**
	 * 校验文本框空值
	 * 
	 * @param tv
	 *            TextView
	 * @return
	 */
	public static boolean checkTextIsEmpty(TextView tv) {
		if (TextUtils.isEmpty(tv.getText().toString().trim()))
			return true;
		return false;
	}

	/**
	 * 校验文本框
	 * 
	 * @param et
	 *            EditText
	 * @return
	 */
	public static boolean checkTextIsEmpty(EditText et) {
		if (TextUtils.isEmpty(et.getText().toString().trim()))
			return true;
		return false;
	}

	/**
	 * 根据传入的校验规则，验证文本的格式是否正确
	 * 
	 * @param text
	 *            传入的参与校验的字符串
	 * @param regex
	 *            校验规则
	 * @return
	 */
	public static boolean checkRule(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * 校验输入金额精确小数点后两位
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkPrecision(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		}
		int point = value.indexOf(".");
		if (point <= 0)
			return true;
		if (value.length() - point - 1 > 2)
			return false;
		return true;
	}

	/**
	 * 限制小数点后位数
	 */
	public static void limitNumberLen(Editable edt, int len) {
		String textValue = edt.toString();
		int point = textValue.indexOf(".");
		if (point <= 0)
			return;
		if (textValue.length() - point - 1 > len)
			edt.delete(point + len + 1, point + len + 2);
	}

	/**
	 * desc:将数组转为16进制
	 * 
	 * @param bArray
	 * @return modified:
	 */
	public static String bytesToHexString(byte[] bArray) {
		if (bArray == null) {
			return null;
		}
		if (bArray.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * desc:将16进制的数据转为数组
	 * 
	 * @param data
	 * @return modified:
	 */
	public static byte[] StringToBytes(String data) {
		String hexString = data.toUpperCase().trim();
		if (hexString.length() % 2 != 0) {
			return null;
		}
		byte[] retData = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i++) {
			int int_ch; // 两位16进制数转化后的10进制数
			char hex_char1 = hexString.charAt(i); // 两位16进制数中的第一位(高位*16)
			int int_ch1;
			if (hex_char1 >= '0' && hex_char1 <= '9')
				int_ch1 = (hex_char1 - 48) * 16; // 0 的Ascll - 48
			else if (hex_char1 >= 'A' && hex_char1 <= 'F')
				int_ch1 = (hex_char1 - 55) * 16; // A 的Ascll - 65
			else
				return null;
			i++;
			char hex_char2 = hexString.charAt(i); // / 两位16进制数中的第二位(低位)
			int int_ch2;
			if (hex_char2 >= '0' && hex_char2 <= '9')
				int_ch2 = (hex_char2 - 48); // 0 的Ascll - 48
			else if (hex_char2 >= 'A' && hex_char2 <= 'F')
				int_ch2 = hex_char2 - 55; // A 的 Ascll - 65
			else
				return null;
			int_ch = int_ch1 + int_ch2;
			retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
		}
		return retData;
	}

	/**
	 * @param data
	 *            数据或路径
	 * @param width
	 *            目标宽
	 * @param height
	 *            目标高
	 * @return 图片
	 */
	public static Bitmap createBitmap(Object data, int width, int height) {
		Options options = new Options();
		int scale = 1;

		if (width > 0 && height > 0) {// 创建目标大小的图片
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
			bitmap = BitmapFactory.decodeByteArray((byte[]) data, 0, ((byte[]) data).length, options);
		}
		return bitmap;
	}

	public static void rotaingImageView(ImageView bitmap) {
		/** 设置旋转动画 */
		final RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(0);// 设置动画持续时间
		/** 常用方法 */
		animation.setFillAfter(true);// 动画执行完后是否停留在执行完的状态
		bitmap.setAnimation(animation);
	}

	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	/**
	 * remove空格
	 * 
	 * @param aStr
	 * @return
	 */
	public static String replaceAllSpace(String aStr) {
		if (StringUtil.isEmpty(aStr))
			return null;
		String lNewStr = aStr.replaceAll(" +", "");
		return lNewStr;
	}

	/**
	 * 正则表达式，校验
	 * 
	 * @param reg
	 * @param value
	 */
	public static boolean mathes(String reg, String value) {
		if (reg == null || value == null)
			return false;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
