package com.xianglin.station.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 */
public class FieldUtil {

	public static void setValueToFiled(Field field, Object object, String fieldValue) throws IllegalArgumentException,
			IllegalAccessException {

		field.setAccessible(true);//
		Class fieldType = field.getType();

		if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {// int
			Integer value = Integer.parseInt(fieldValue);
			field.set(object, value);
		} else if (String.class == fieldType) {// string
			field.set(object, fieldValue);
		} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {// long
			Long value = Long.parseLong(fieldValue);
			field.set(object, value);
		} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {// float
			Float value = Float.parseFloat(fieldValue);
			field.set(object, value);
		} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {// short
			Short value = Short.parseShort(fieldValue);
			field.set(object, value);
		} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {// double
			Double value = Double.parseDouble(fieldValue);
			field.set(object, value);
		} else if (Character.TYPE == fieldType) {// char
			Character value = Character.valueOf(fieldValue.charAt(0));
			field.set(object, value);
		} else if (Date.class == fieldType) {// date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = sdf.parse(fieldValue);
				field.set(object, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (Boolean.class == fieldType) {
			field.setBoolean(object, (Boolean.parseBoolean(String.valueOf(fieldValue))));
		}
	}

	public static void setDefValueToFiled(Field field, Object object) throws IllegalArgumentException,
			IllegalAccessException {

		field.setAccessible(true);
		Class fieldType = field.getType();

		if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {// int
			Integer value = 0;
			field.set(object, value);
		} else if (String.class == fieldType) {// string
			field.set(object, "");
		} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {// long
			Long value = Long.parseLong("0");
			field.set(object, value);
		} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {// float
			Float value = Float.parseFloat("0");
			field.set(object, value);
		} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {// short
			Short value = Short.parseShort("0");
			field.set(object, value);
		} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {// double
			Double value = Double.parseDouble("0");
			field.set(object, value);
		} else if (Date.class == fieldType) {// date
			try {
				Date date = null;
				field.set(object, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (Boolean.class == fieldType) {
			field.setBoolean(object, false);
		}
	}

	public static void getValueFromField(Field field, Object object) throws IllegalArgumentException,
			IllegalAccessException {
		field.setAccessible(true);

		Class fieldType = field.getType();

		if (Integer.TYPE == fieldType || Integer.class == fieldType) {// int
			field.getInt(object);
		} else if (String.class == fieldType) {// string
			String.valueOf(field.get(object));
		} else if (Short.class == fieldType) {// short
			field.getShort(object);
		} else if (Long.class == fieldType) {// long
			field.getLong(object);
		} else if (Float.class == fieldType) {// float
			field.getFloat(object);
		} else if (Double.class == fieldType) {// double
			field.getDouble(object);
		} else if (Character.class == fieldType) {// char
			field.getChar(object);
		} else if (Date.class == fieldType) {// date
			String.valueOf(field.get(object));
		} else if (Boolean.class == fieldType) {// boolean
			field.getBoolean(object);
		}
	}

	public static Object getFieldValue(Field field, Object object) throws Exception {
		Object temp = null;
		field.setAccessible(true);
		Class<?> fildType = field.getType();
		if (String.class == fildType || Character.class == fildType) {
			temp = String.valueOf(field.get(object));
		} else if (Integer.TYPE == fildType || Integer.class == fildType) {
			temp = field.getInt(object);
		} else if (boolean.class == fildType) {
			temp = field.getBoolean(object);
		} else if (Long.class == fildType) {
			temp = field.getLong(object);
		} else if (Float.class == fildType) {
			temp = field.getFloat(object);
		}
		return temp;
	}

}
