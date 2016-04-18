package com.example.redboy.fragment;

import com.example.redboy.utils.UiUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShoppingCarFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(UiUtils.getContext());
		tv.setText("我是ShoppingCarFragment");
		return tv;
	}
}
