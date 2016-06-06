package com.brooks.cufer.activity;
import com.brooks.cufer.R;
import com.brooks.cufer.util.SharedPreferenceUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年11月27日
 *
 * @描述 闪屏页 判断是否已经登陆过，如果已经登陆过，则直接进入主页面；否则进入登录界面
 */
public class SplashActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		MobclickAgent.updateOnlineConfig(getApplicationContext());
		AnalyticsConfig.enableEncrypt(true);
		UmengUpdateAgent.update(this);
		setContentView(R.layout.activity_splash);
		((ImageView) findViewById(R.id.bg)).postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				SharedPreferenceUtil util = new SharedPreferenceUtil(
						getApplicationContext(), "accountInfo");
				String isLogin = util.getKeyData("isLogin");
				// 是否已登录
				if (isLogin.equals("TRUE"))
				{
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				} else
				{
					Intent intent = new Intent(SplashActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, 3000);
	}
	/**
	 * 友盟统计
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
