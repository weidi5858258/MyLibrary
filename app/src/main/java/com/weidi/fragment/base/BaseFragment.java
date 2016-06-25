package com.weidi.fragment.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 */
public class BaseFragment extends Fragment {

	protected FragmentManager mFragmentManager = null;
	protected Context mContext = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected void animRightToLeft() {
		try {
//			mContext.overridePendingTransition(R.anim.push_right_in2, R.anim.push_right_to2);
		} catch (Exception e) {
		}
	}

	protected void animLeftToRight() {
		try {
//			mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} catch (Exception e) {
		}
	}

	protected void animBottomToTop() {
		try {
//			mContext.overridePendingTransition(R.anim.roll_up, R.anim.roll);
		} catch (Exception e) {
		}
	}

}
