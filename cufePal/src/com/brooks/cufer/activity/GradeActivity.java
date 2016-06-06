package com.brooks.cufer.activity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.brooks.cufer.R;
import com.brooks.cufer.adapter.GradeAdapter;
import com.brooks.cufer.model.Grade;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.CommonUtil;
import com.brooks.cufer.util.FileUtils;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.SharedPreferenceUtil;
import com.brooks.cufer.util.HttpUtil.QueryCallback;
import com.brooks.cufer.util.LinkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.MsgConstant;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class GradeActivity extends AppCompatActivity
{
	private Button bt_lncj;
	private Button bt_cjtj;
	private LinkService linkService;
	Handler handle = null;
	private ListView lv_lncj;
	private LinearLayout ll_cjtj;
	private LinearLayout ll_lncj;
	private TextView tv_xfjdzh;
	private TextView tv_zyzrs;
	private TextView tv_pjxfjd;
	private String viewState;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grade);
		initValue();
		initView();
		initEvent();
	}
	/**
	 * 初始化值
	 */
	private void initValue()
	{
		linkService = LinkService.getLinkService();
		// 得到viewstate的值
		HttpUtil.getQuery(GradeActivity.this, linkService, LinkUtil.CJCX,
				new QueryCallback()
				{
					@Override
					public String handleResult(byte[] result)
					{
						try
						{
							String string = new String(result, "gb2312");
							parseViewState(string);
						} catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
						return null;
					}
				});
	}
	/**
	 * 解析出viewState
	 * 
	 * @param result
	 */
	private void parseViewState(String result)
	{
		Document document = Jsoup.parse(result);
		Elements elements = document.select("input[name=__VIEWSTATE]");
		viewState = elements.get(0).attr("value");
		System.out.println(viewState);
	}
	/**
	 * 初始化事件
	 */
	private void initEvent()
	{
		// 历年成绩
		bt_lncj.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				RequestParams params = HttpUtil.getGradeRequestParams();
				params.add("__VIEWSTATE", viewState);
				// 请求地址
				HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
				HttpUtil.URL_MAIN = HttpUtil.URL_QUERY.replace("QUERY",
						linkService.getLinkByName(LinkUtil.CJCX));// 获得请求地址
				System.out.println("请求地址--->" + HttpUtil.URL_MAIN);
				HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
				System.out.println("referer-->" + HttpUtil.URL_MAIN);
				HttpUtil.getClient().setURLEncodingEnabled(true);
				final ProgressDialog dialog = CommonUtil.getProcessDialog(
						GradeActivity.this, "正在处理");
				dialog.show();
				HttpUtil.post(HttpUtil.URL_MAIN, params,
						new AsyncHttpResponseHandler()
						{
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody)
							{
								String resultContent;
								try
								{
									resultContent = new String(responseBody,
											"gb2312");
									FileUtils.write(resultContent);
									parseGrade(resultContent);
								} catch (UnsupportedEncodingException e)
								{
									e.printStackTrace();
								}
								dialog.dismiss();
								Toast.makeText(getApplicationContext(), "处理成功",
										Toast.LENGTH_SHORT).show();
							}
							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error)
							{
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"处理失败><", Toast.LENGTH_SHORT).show();
								showAlert();
							}
						});
			}
		});
		// 成绩统计
		bt_cjtj.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				RequestParams params = HttpUtil.getGradeAnaRequestParams();
				params.add("__VIEWSTATE", viewState);
				// 请求地址
				HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
				HttpUtil.URL_MAIN = HttpUtil.URL_QUERY.replace("QUERY",
						linkService.getLinkByName(LinkUtil.CJCX));// 获得请求地址
				HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
				HttpUtil.getClient().setURLEncodingEnabled(true);
				final ProgressDialog dialog = CommonUtil.getProcessDialog(
						GradeActivity.this, "正在处理");
				dialog.show();
				HttpUtil.post(HttpUtil.URL_MAIN, params,
						new AsyncHttpResponseHandler()
						{
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody)
							{
								String resultContent;
								try
								{
									resultContent = new String(responseBody,
											"gb2312");
									// FileUtils.write(resultContent);
									parseGradeAna(resultContent);
								} catch (UnsupportedEncodingException e)
								{
									e.printStackTrace();
								}
								dialog.dismiss();
							}
							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error)
							{
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"处理失败><", Toast.LENGTH_SHORT).show();
								showAlert();
							}
						});
			}
		});
	}
	/**
	 * 解析返回的网页
	 * 
	 * @param resultContent
	 */
	private void parseGrade(String resultContent)
	{
		Document document = Jsoup.parse(resultContent);
		Elements elements = document.select("table[id=Datagrid1]");
		Elements trs = elements.get(0).getElementsByTag("tr");
		ArrayList<Grade> grades = new ArrayList<>();
		if (trs.size() > 0)
		{
			for (int i = 1; i < trs.size(); i++)
			{
				Element element = trs.get(i);
				Elements tds = element.getElementsByTag("td");
				Grade grade = new Grade();
				grade.setXn(tds.get(0).ownText());
				grade.setXq(tds.get(1).ownText());
				grade.setKcdm(tds.get(2).ownText());
				grade.setKcmc(tds.get(3).ownText());
				grade.setKcxz(tds.get(4).ownText());
				grade.setKcgs(tds.get(5).ownText());
				grade.setXf(tds.get(6).ownText());
				grade.setJd(tds.get(7).ownText());
				grade.setDj(tds.get(8).ownText());
				grade.setFs(tds.get(9).ownText());
				grade.setFxbj(tds.get(10).ownText());
				grade.setBkcj(tds.get(11).ownText());
				grade.setCxcj(tds.get(12).ownText());
				grade.setKkxy(tds.get(13).ownText());
				grade.setBz(tds.get(14).ownText());
				grade.setCxbj(tds.get(15).ownText());
				grades.add(grade);
			}
		}
		Message message = handle.obtainMessage();
		message.obj = grades;
		message.what = 1;
		handle.sendMessage(message);
	}
	/**
	 * 解析成绩统计
	 * 
	 * @param resultContent
	 */
	private void parseGradeAna(String resultContent)
	{
		Document document = Jsoup.parse(resultContent);
		Elements elements = document.select("span[id=zyzrs]");
		Bundle bundle = new Bundle();
		if (elements != null && elements.size() != 0)
		{
			String zyzrs = elements.get(0).text();
			bundle.putString("zyzrs", zyzrs);
		}
		Elements elements2 = document.select("span[id=pjxfjd]");
		if (elements2 != null && elements2.size() != 0)
		{
			String pjxfjd = elements2.get(0).text();
			bundle.putString("pjxfjd", pjxfjd);
		}
		Elements elements3 = document.select("span[id=xfjdzh]");
		if (elements3 != null && elements3.size() != 0)
		{
			String xfjdzh = elements3.get(0).text();
			bundle.putString("xfjdzh", xfjdzh);
		}
		Message message = handle.obtainMessage();
		message.what = 2;
		message.obj = bundle;
		handle.sendMessage(message);
	}
	/**
	 * 初始化视图
	 */
	private void initView()
	{
		initToolbar();
		bt_lncj = (Button) findViewById(R.id.bt_lncj);
		bt_cjtj = (Button) findViewById(R.id.bt_cjtj);
		lv_lncj = (ListView) findViewById(R.id.lv_lncj);
		ll_cjtj = (LinearLayout) findViewById(R.id.ll_cjtj);
		ll_lncj = (LinearLayout) findViewById(R.id.ll_lncj);
		tv_xfjdzh = (TextView) findViewById(R.id.tv_xfjdzh);
		tv_zyzrs = (TextView) findViewById(R.id.tv_zyzrs);
		tv_pjxfjd = (TextView) findViewById(R.id.tv_pjxfjd);
		handle = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == 1)
				{
					final ArrayList<Grade> grades = (ArrayList<Grade>) msg.obj;
					lv_lncj.setVisibility(View.VISIBLE);
					ll_lncj.setVisibility(View.VISIBLE);
					ll_cjtj.setVisibility(View.GONE);
					lv_lncj.setAdapter(new GradeAdapter(GradeActivity.this,
							grades));
					lv_lncj.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id)
						{
							new AlertDialog.Builder(GradeActivity.this)
									.setMessage(grades.get(position).toString())
									.create().show();
						}
					});
				} else if (msg.what == 2)
				{
					lv_lncj.setVisibility(View.GONE);
					ll_lncj.setVisibility(View.GONE);
					ll_cjtj.setVisibility(View.VISIBLE);
					Bundle bundle = (Bundle) msg.obj;
					tv_pjxfjd.setText(bundle.getString("pjxfjd"));
					tv_xfjdzh.setText(bundle.getString("xfjdzh"));
					tv_zyzrs.setText(bundle.getString("zyzrs"));
				}
			}
		};
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
		mToolBarTextView.setText("成绩查询");
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
	 * 显示session过期的警告
	 */
	private void showAlert()
	{
		new AlertDialog.Builder(GradeActivity.this)
				.setMessage("请求失败可能有多种原因，您可以尝试重新登录~")
				.setPositiveButton("重新登录",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								SharedPreferenceUtil util = new SharedPreferenceUtil(
										GradeActivity.this, "accountInfo");
								util.setKeyData("isLogin", "false");
								GradeActivity.this
										.startActivity(new Intent(
												GradeActivity.this,
												LoginActivity.class));
								//MainActivity.this.finish();
								GradeActivity.this.finish();
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).create().show();
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
