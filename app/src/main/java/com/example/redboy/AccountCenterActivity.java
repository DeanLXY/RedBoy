package com.example.redboy;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.redboy.utils.GlobalConfig;
import com.example.redboy.utils.HttpManager;
import com.example.redboy.utils.HttpManager.OnRequestResonseListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_accountcenter)
public class AccountCenterActivity extends BaseActivity {

	@Override
	protected void init() {
		super.init();
		long userId = GlobalConfig.getUserId();
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userId + "");
		HttpManager http = new HttpManager(this, Constans.USERINFO, params,
				new OnRequestResonseListener() {

					@Override
					public void onSucesss(String json) {
						parseJson(json);
					}

					@Override
					public void onFailure(String errorMsg) {
						Toast.makeText(getApplicationContext(), errorMsg,
								Toast.LENGTH_SHORT).show();

					}
				});
//		http.get();
	}

	protected void parseJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String response = jsonObject.getString("response");
			if ("error".equals(response)) {
				Toast.makeText(getApplicationContext(), "获取信息失败",
						Toast.LENGTH_SHORT).show();
			} else {
//				jsonObject.getJSONObject("")
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@OnClick(R.id.btn_exit)
	public void logout(View view){
		GlobalConfig.setUserId(0);
		finish();
		startActivity(new Intent(getBaseContext(), LoginActivity.class));
	}
}
