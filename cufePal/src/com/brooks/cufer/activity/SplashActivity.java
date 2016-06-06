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
 * @author ���ɴ�(Brooks)
 *
 * @�������� 2015��11��27��
 *
 * @���� ����ҳ �ж��Ƿ��Ѿ���½��������Ѿ���½������ֱ�ӽ�����ҳ�棻��������¼����
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
				// �Ƿ��ѵ�¼
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
	 * ����ͳ��
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
