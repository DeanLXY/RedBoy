package com.example.redboy;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.redboy.utils.HttpManager;
import com.example.redboy.utils.HttpManager.OnRequestResonseListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {
	@ViewInject(R.id.et_account)
	EditText et_account;
	@ViewInject(R.id.et_psw)
	EditText et_psw;
	@ViewInject(R.id.et_perform_psw)
	EditText et_perform_psw;

	@OnClick(R.id.btn_regist)
	public void regist(View view) {
		// trim(); 去除空格
		String account = et_account.getText().toString().trim(); // 正则表达式
		String psw = et_psw.getText().toString().trim();
		String performPsw = et_perform_psw.getText().toString().trim();
		if (TextUtils.isEmpty(account)) {
			Toast.makeText(getApplicationContext(), "帐号不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(psw) || TextUtils.isEmpty(performPsw)
				|| !psw.equals(performPsw)) {
			Toast.makeText(getApplicationContext(), "密码输入错误",
					Toast.LENGTH_SHORT).show();
			return;
		}
		regist(account, psw);
	}

	private void regist(String account, String psw) {
		Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_SHORT)
				.show();
//		HttpUtils http = new HttpUtils();
//		// 网络请求有几种方式 ---- 9种 ---- get/post
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("username", account);
//		params.addBodyParameter("password", psw);
//		http.send(HttpMethod.POST, Constans.BASEURL + "/register", params,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						System.out.println("result = " + responseInfo.result);
//						parseJson(responseInfo.result);
//					}
//
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						System.out.println("error = " + msg);
//					}
//				});
		//
		//
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		Map<String, String> params  = new HashMap<String, String>();
		params.put("username", account);
		params.put("password", psw);
		HttpManager http = new HttpManager(this, Constans.BASEURL + "/register", params  , new OnRequestResonseListener() {
			
			@Override
			public void onSucesss(String json) {
				parseJson(json);
			}
			
			@Override
			public void onFailure(String errorMsg) {
				
			}
		});
	http.post();
//	http.get();
//	http.requestByVolley();
	}

	// gson --- 万能 == String
	// String
	// userid
	// {"response":"register","userinfo":{"userid":22}}
	// gson--- 效率很低 100------600

	protected void parseJson(String json) {
		// json ----- 格式良好的字符串
		try {
			JSONObject jsonObject = new JSONObject(json);
			String response = jsonObject.getString("response");
			if (TextUtils.equals("error", response)) {
				Toast.makeText(getApplicationContext(), "注册失败",
						Toast.LENGTH_SHORT).show();
			} else {
//注册成功 
				finish();
				startActivity(new Intent(getBaseContext(), LoginActivity.class));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
