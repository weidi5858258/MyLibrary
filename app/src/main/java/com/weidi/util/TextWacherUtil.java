package com.xianglin.station.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 输入框监听器
 * @author LiuHaoLiang
 * @version $Id: TextWatcherUtil.java, v 1.0.0 2015-09-24 上午11:42:23  Exp $
 */
public class TextWacherUtil {

public static void setPoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".")-1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(1);
                }
 
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
 
            }

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
 
 
        });
 
    }
}
