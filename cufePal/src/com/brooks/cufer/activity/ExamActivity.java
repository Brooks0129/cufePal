package com.brooks.cufer.activity;
import java.util.List;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.brooks.cufer.R;
import com.brooks.cufer.adapter.ExamAdapter;
import com.brooks.cufer.model.Exam;
import com.brooks.cufer.service.ExamService;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.LinkUtil;
import com.brooks.cufer.util.HttpUtil.QueryCallback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.proguard.ag;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年12月1日
 *
 * @描述
 */
public class ExamActivity extends AppCompatActivity
{
	private ListView lv_exam;
	private ExamService examService;
	private LinkService linkService;
	private List<Exam> exams;
	private ExamAdapter examAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);
		initValue();
		initView();
	}
	private void initView()
	{
		initToolbar();
		lv_exam = (ListView) findViewById(R.id.lv_exam);
		exams = examService.findAll();
		if (exams != null && exams.size() != 0)
		{
			examAdapter = new ExamAdapter(ExamActivity.this, exams);
			lv_exam.setAdapter(examAdapter);
			lv_exam.setSelector(R.drawable.clickview);
			lv_exam.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					LinearLayout ll = (LinearLayout) LayoutInflater.from(
							getApplicationContext()).inflate(
							R.layout.exam_infor, null);
					TextView tv_campus = (TextView) ll
							.findViewById(R.id.tv_campus);
					tv_campus.setText(exams.get(position).getCampus());
					TextView tv_course_name = (TextView) ll
							.findViewById(R.id.tv_course_name);
					tv_course_name.setText(exams.get(position).getCourseName());
					TextView tv_course_number = (TextView) ll
							.findViewById(R.id.tv_course_number);
					tv_course_number.setText(exams.get(position)
							.getCourseNumber());
					TextView tv_exam_location = (TextView) ll
							.findViewById(R.id.tv_exam_location);
					tv_exam_location.setText(exams.get(position)
							.getExamLocation());
					TextView tv_exam_time = (TextView) ll
							.findViewById(R.id.tv_exam_time);
					tv_exam_time.setText(exams.get(position).getExamTime());
					TextView tv_exam_type = (TextView) ll
							.findViewById(R.id.tv_exam_type);
					tv_exam_type.setText(exams.get(position).getExamType());
					TextView tv_name = (TextView) ll.findViewById(R.id.tv_name);
					tv_name.setText(exams.get(position).getName());
					TextView tv_seat_number = (TextView) ll
							.findViewById(R.id.tv_seat_number);
					tv_seat_number.setText(exams.get(position).getSeatNumber());
					new AlertDialog.Builder(ExamActivity.this)
							.setView(ll)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener()
									{
										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
										}
									}).create().show();
				}
			});
		} else
		{
			Toast.makeText(getApplicationContext(), "无考试科目~",
					Toast.LENGTH_SHORT).show();
		}
		
	}
	private void initToolbar()
	{
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mToolbar.setOnMenuItemClickListener(onMenuItemClick);
		mToolBarTextView.setText("考试查询");
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
	private void initValue()
	{
		linkService = LinkService.getLinkService();
		examService = ExamService.getExamService();
	}
	// 刷新考试科目数据
	private void refresh()
	{
		examAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_exam, menu);
		return true;
	}
	/**
	 * 重新请求
	 */
	private void reQuery()
	{
		HttpUtil.getQuery(ExamActivity.this, linkService,
				LinkUtil.XSKSCX, new QueryCallback()
				{
					@Override
					public String handleResult(byte[] result)
					{
						String ret = null;
						try
						{
							examService.deleteAll();
							examService.parseExam(new String(result,
									"gb2312"));
						} catch (Exception e)
						{
							new AlertDialog.Builder(ExamActivity.this)
									.setMessage("当前无法查询！")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener()
											{
												@Override
												public void onClick(
														DialogInterface dialog,
														int which)
												{
												}
											}).create().show();
							e.printStackTrace();
						}
						exams = examService.findAll();
						refresh();
						return ret;
					}
				});
	}
	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem arg0)
		{
			switch (arg0.getItemId())
			{
				case R.id.action_refresh :
					reQuery();
					break;
				default :
					break;
			}
			return true;
		}
		
		};
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
