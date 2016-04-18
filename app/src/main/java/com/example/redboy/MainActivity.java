package com.example.redboy;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.redboy.fragment.BrandFragment;
import com.example.redboy.fragment.HomeFragment;
import com.example.redboy.fragment.MoreFragment;
import com.example.redboy.fragment.SearchFragment;
import com.example.redboy.fragment.ShoppingCarFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	@ViewInject(R.id.rg_menu)
	RadioGroup rg_menu;
	@ViewInject(R.id.rb_menu_home)
	RadioButton rb_menu_home;
	private List<Fragment> fragments;

	@Override
	protected void init() {
		super.init();
		fragments = new ArrayList<Fragment>();
		
		fragments.add(new HomeFragment());
		fragments.add(new SearchFragment());
		fragments.add(new BrandFragment());
		fragments.add(new ShoppingCarFragment());
		fragments.add(new MoreFragment());
	}

	@Override
	protected void initView() {
		super.initView();
		ViewUtils.inject(this);
		rg_menu.setOnCheckedChangeListener(this);
		// 默认首页 打印两次
		// rg_menu.check(R.id.rb_menu_home);
		rb_menu_home.setChecked(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_menu_home:
			changeFragment(0);
			break;
		case R.id.rb_menu_search:
			changeFragment(1);
			break;
		case R.id.rb_menu_brand:
			changeFragment(2);
			break;
		case R.id.rb_menu_shopping:
			changeFragment(3);
			break;
		case R.id.rb_menu_more:
			changeFragment(4);
			break;

		}
	}

	private Fragment preFragment;

	private void changeFragment(int index) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = fragments.get(index);
		if (preFragment != null) {
			transaction.hide(preFragment);
		}
		if (fragment.isAdded()) {
			transaction.show(fragment);
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("key", "value");
			fragment.setArguments(bundle);
			transaction.add(R.id.container, fragment);
		}
		transaction.commit();
		preFragment = fragment;
	}
}
