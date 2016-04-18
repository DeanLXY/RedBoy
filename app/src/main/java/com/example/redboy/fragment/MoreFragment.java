package com.example.redboy.fragment;

import com.example.redboy.AccountCenterActivity;
import com.example.redboy.HelpCenterActivity;
import com.example.redboy.LoginActivity;
import com.example.redboy.R;
import com.example.redboy.utils.GlobalConfig;
import com.example.redboy.utils.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = UiUtils.inflate(R.layout.fragment_more);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick({ R.id.item_account, R.id.rl_help_center })
	public void click(View view) {
		switch (view.getId()) {
		case R.id.item_account:
			if (GlobalConfig.isLogin()) {
				startActivity(new Intent(getActivity(),
						AccountCenterActivity.class));
			} else {
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.rl_help_center:
			startActivity(new Intent(getActivity(), HelpCenterActivity.class));

			break;

		default:
			break;
		}
	}
}
