package com.example.redboy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.redboy.bean.Help;
import com.example.redboy.utils.HttpManager;
import com.example.redboy.utils.HttpManager.OnRequestResonseListener;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_help_center)
public class HelpCenterActivity extends BaseActivity {
//     ViewUtils  HttpUtils DBUtils BitmapUtils
	@ViewInject(R.id.listview)
	ListView mListView;
	private SharedPreferences sp;

	@Override
	protected void initView() {
		super.initView();
		mAdapter = new ArrayAdapter<Help>(this, android.R.layout.simple_list_item_1, helps){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView, parent);
				Help help = helps.get(position);
				view.setText(help.title);
				return view;
			}
		};
		mListView.setAdapter(mAdapter);
		sp = getSharedPreferences("zzitcastz11", Context.MODE_PRIVATE);
		String version = sp.getString("version", "0");
		
		// dbUtils对象 
		
		dbUtils = DbUtils.create(this, "itcastz11.db");
		
		
		
		if (TextUtils.equals("0", version)) {
			//  新用户    ---  请求网络数据
			loadFromServer(version);
		}else{
			//本地数据库获取数据
			loadFromDB();
			loadFromServer(version);
		}
		

	}
	//    从数据库获取数据
	private void loadFromDB() {
		try {
			List<Help> list = dbUtils.findAll(Help.class);
			if (list!= null) {
				helps.addAll(list);
				updateUi();
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	private void loadFromServer(String version) {
		// 请求网络数据
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("version", version);
		HttpManager http = new HttpManager(this, Constans.BASEURL
				+ "/help?version=" + version, null,
				new OnRequestResonseListener() {

					@Override
					public void onSucesss(String json) {
						System.out.println("result = " + json);

						// 校验数据
						if (checkJson(json)) {
							parseJson(json);
							updateUi();
							//缓存数据
							storeHelpList();
							storeLastVersion(json);
							
						}
					}

					@Override
					public void onFailure(String errorMsg) {
						System.out.println("error = " + errorMsg);
					}
				});
		http.get();
	}
	// {"helpList":[{"id":1,"title":"如何派送"},{"id":3,"title":"帮助3"},{"id":4,"title":"支付失败"},{"id":5,"title":"添加的帮助"}],"response":"help","version":3}
	protected void storeLastVersion(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String version = jsonObject.getString("version");
			// 存到sp
			Editor edit = sp.edit();
			edit.putString("version", version);
			//一定不能省去
			edit.commit();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 缓存数据
	 */
	protected void storeHelpList() {
		//helps
		try {
			dbUtils.saveAll(helps);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新界面
	 */
	protected void updateUi() {
		mAdapter.notifyDataSetChanged();
	}

	private List<Help> helps = new ArrayList<Help>();
	private ArrayAdapter<Help> mAdapter;
	private DbUtils dbUtils;

	// {"helpList":[{"id":1,"title":"如何派送"},{"id":3,"title":"帮助3"},{"id":4,"title":"支付失败"},{"id":5,"title":"添加的帮助"}],"response":"help","version":3}
	//  ----     	Help help    栈内存 
	protected void parseJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("helpList");
			Help help = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				long id = obj.getLong("id");
				String title = obj.getString("title");
				// ------ java
				help = new Help(id, title);
				helps.add(help);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// {"helpList":[{"id":1,"title":"如何派送"},{"id":3,"title":"帮助3"},{"id":4,"title":"支付失败"},{"id":5,"title":"添加的帮助"}],"response":"help","version":3}

	protected boolean checkJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String response = jsonObject.getString("response");
			if (TextUtils.equals("error", response)) {
				return false;
			}
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
