package com.brooks.cufer.activity;
import com.brooks.cufer.R;
import com.brooks.cufer.util.SharedPreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import android.R.anim;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class AboutActivity extends AppCompatActivity
{
	private ListView lv_about;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initToolbar();
		lv_about = (ListView) findViewById(R.id.lv_about);
		final String[] items = {"检查更新", "评分", "关于作者"};
		lv_about.setAdapter(new BaseAdapter()
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				LinearLayout ll = (LinearLayout) LayoutInflater.from(
						AboutActivity.this).inflate(R.layout.about_item, null);
				TextView tv_about = (TextView) ll.findViewById(R.id.tv_about);
				tv_about.setText(items[position]);
				return ll;
			}
			@Override
			public long getItemId(int position)
			{
				return position;
			}
			@Override
			public Object getItem(int position)
			{
				return items[position];
			}
			@Override
			public int getCount()
			{
				return items.length;
			}
		});
		lv_about.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				switch (position)
				{
					case 0 :
						UmengUpdateAgent.forceUpdate(getApplicationContext());
						break;
					case 1 :
						Uri uri = Uri.parse("market://details?id="
								+ getPackageName());
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						break;
					case 2 :
						new AlertDialog.Builder(
								AboutActivity.this,
								android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
								.setTitle("关于我")
								.setMessage(
										"李松达,中财信息学院信息管理与信息系统13班,"
												+ "邮箱 lisongda0129@outlook.com")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener()
										{
											@Override
											public void onClick(
													DialogInterface dialog,
													int which)
											{
											}
										})
								.setNegativeButton("联系我",
										new DialogInterface.OnClickListener()
										{
											@Override
											public void onClick(
													DialogInterface dialog,
													int which)
											{
												startSendEmailIntent();
											}
										}).create().show();
				}
			}
		});
	}
	/**
	 * 初始化toolbar
	 */
	private void initToolbar()
	{
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mToolBarTextView.setText("关于");
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}
	/**
	 * 发送邮件
	 */
	private void startSendEmailIntent()
	{
		Intent data = new Intent(Intent.ACTION_SENDTO);
		data.setData(Uri.parse("mailto:lisongda0129@outlook.com"));
		data.putExtra(Intent.EXTRA_SUBJECT, "联系我");
		data.putExtra(Intent.EXTRA_TEXT, "");
		startActivity(data);
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
