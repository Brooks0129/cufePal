package com.brooks.cufer.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.brooks.cufer.R;
import com.brooks.cufer.util.StringUtils;
import com.umeng.analytics.MobclickAgent;
public class UpdatePswActivity extends AppCompatActivity
{
	private TextView tv_number;
	private EditText tv_old_psw;
	private EditText tv_new_psw_con;
	private EditText tv_new_psw;
	private Button bt_update_psw;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepsw);
		initView();
		initEvent();
	}
	private void initView()
	{
		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_old_psw = (EditText) findViewById(R.id.tv_old_psw);
		tv_new_psw = (EditText) findViewById(R.id.tv_new_psw);
		tv_new_psw_con = (EditText) findViewById(R.id.tv_new_psw_con);
		bt_update_psw = (Button) findViewById(R.id.bt_update_psw);
	}
	private void initEvent()
	{
		bt_update_psw.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String old = tv_old_psw.getText().toString();
				String newPsw = tv_new_psw.getText().toString();
				String newPswCon = tv_new_psw_con.getText().toString();
				if (StringUtils.isEmpty(old) || StringUtils.isEmpty(newPsw)
						|| StringUtils.isEmpty(newPswCon))
				{
					Toast.makeText(UpdatePswActivity.this, "信息不完整",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!newPsw.trim().equals(newPswCon.trim()))
				{
					Toast.makeText(UpdatePswActivity.this, "确认密码与原密码不相同",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(getApplicationContext(), "即将实现", Toast.LENGTH_SHORT).show();
			}
		});
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
