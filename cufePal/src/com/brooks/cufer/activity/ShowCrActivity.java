package com.brooks.cufer.activity;
import java.util.ArrayList;
import java.util.Iterator;
import com.brooks.cufer.R;
import com.brooks.cufer.adapter.MyClassroomAdapter;
import com.brooks.cufer.model.Classroom;
import com.umeng.analytics.MobclickAgent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
public class ShowCrActivity extends AppCompatActivity
{
	private TextView tv_summery;
	private String summary;
	private ListView lv_cr;
	private ArrayList<Classroom> classrooms;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcr);
		initValue();
		initView();
	}
	/**
	 * 初始化视图
	 */
	private void initView()
	{
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mToolBarTextView.setText("查询结果");
		tv_summery = (TextView) findViewById(R.id.tv_summery);
		tv_summery.setText(summary);
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		lv_cr = (ListView) findViewById(R.id.lv_cr);
		lv_cr.setAdapter(new MyClassroomAdapter(classrooms, this));
	}
	/**
	 * 初始化值
	 */
	private void initValue()
	{
		classrooms = getIntent().getParcelableArrayListExtra("classrooms");
		summary = getIntent().getStringExtra("summary");
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
