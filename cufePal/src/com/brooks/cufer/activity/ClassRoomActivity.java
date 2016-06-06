package com.brooks.cufer.activity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import android.R.anim;
import android.R.integer;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Classroom;
import com.brooks.cufer.service.ClassroomService;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.CommonUtil;
import com.brooks.cufer.util.FileUtils;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.HttpUtil.QueryCallback;
import com.brooks.cufer.util.LinkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
public class ClassRoomActivity extends AppCompatActivity
{
	private LinkService linkService;
	private Button bt_xq;
	private Handler handler;
	private Button bt_jslb;
	private Button bt_jssj;
	private Button bt_kssj;
	private Button bt_ddlDsz;
	private Button bt_xqj;
	private Button bt_sjd;
	private Button bt_query_classroom;
	private LinkedHashMap<String, String> xiaoqu;
	private LinkedHashMap<String, String> jslb;
	private LinkedHashMap<String, String> kssj;
	private LinkedHashMap<String, String> jssj;
	private LinkedHashMap<String, String> xqj;
	private LinkedHashMap<String, String> ddlDsz;
	private LinkedHashMap<String, String> sjd;
	private String __VIEWSTATE;
	private String sjd_selected;
	private String ddlDsz_selected;
	private String xqj_selected;
	private String jssj_selected;
	private String kssj_selected;
	private String jslb_selected;
	private static String xq_selected;
	private ClassroomService classroomService;
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classroom);
		initValue();
		initView();
		initEvent();
	}
	// 事件初始化
	private void initEvent()
	{
		bt_xq.setOnClickListener(myClickListener);
		bt_jslb.setOnClickListener(myClickListener);
		bt_kssj.setOnClickListener(myClickListener);
		bt_jssj.setOnClickListener(myClickListener);
		bt_ddlDsz.setOnClickListener(myClickListener);
		bt_xqj.setOnClickListener(myClickListener);
		bt_sjd.setOnClickListener(myClickListener);
		bt_query_classroom.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				HttpUtil.__VIEWSTATE = __VIEWSTATE;
				HttpUtil.__EVENTTARGET = "dpDataGrid1:txtPageSize";
				HttpUtil.__VIEWSTATE = "dDw2NDc1Nzc1OTI7dDxwPGw8anN5eW1za2c7ZHF4bnhxO2pzeXV5ZGR5Oz47bDwwMDA7MjAxNS0yMDE2MTtcZTs+PjtsPGk8MT47PjtsPHQ8O2w8aTwxPjtpPDM+O2k8OT47aTwxMT47aTwxND47aTwxOD47aTwyMT47aTwyND47aTwyNj47aTwzMz47aTwzOD47aTwzOT47aTw0MT47aTw0NT47aTw0Nz47aTw0OD47aTw1ND47aTw2MD47aTw2MT47PjtsPHQ8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDx4cW1jO3hxZG07Pj47Pjt0PGk8ND47QDxcZTvlrabpmaLljZfot6/moKHljLo75rKZ5rKz5qCh5Yy6O+aymeays+agoeWMui3opb87PjtAPFxlOzE7MjszOz4+Oz47Oz47dDx0PHA8cDxsPERhdGFUZXh0RmllbGQ7RGF0YVZhbHVlRmllbGQ7PjtsPGpzbGI7anNsYjs+Pjs+O3Q8aTwxMD47QDxcZTtDQVRJ5a6e6aqM5a6kO+ahiOS+i+aVmeWupDvlpJrlqpLkvZPmlZnlrqQ75YWs55So6LWE5rqQO+e7j+euoeS4reW/g+WunumqjOWupDvmlofkvKDlrabpmaLlrp7pqozlrqQ75L+h5oGv5a2m6Zmi5a6e6aqM5a6kO+ivremfs+aVmeWupDtcZTs+O0A8XGU7Q0FUSeWunumqjOWupDvmoYjkvovmlZnlrqQ75aSa5aqS5L2T5pWZ5a6kO+WFrOeUqOi1hOa6kDvnu4/nrqHkuK3lv4Plrp7pqozlrqQ75paH5Lyg5a2m6Zmi5a6e6aqM5a6kO+S/oeaBr+WtpumZouWunumqjOWupDvor63pn7PmlZnlrqQ7XGU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTUtMjAxNjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDxueXI7eHE7Pj47Pjt0PGk8NTE+O0A8MjAxNS0xMi0wNTsyMDE1LTEyLTA2OzIwMTUtMTItMDc7MjAxNS0xMi0wODsyMDE1LTEyLTA5OzIwMTUtMTItMTA7MjAxNS0xMi0xMTsyMDE1LTEyLTEyOzIwMTUtMTItMTM7MjAxNS0xMi0xNDsyMDE1LTEyLTE1OzIwMTUtMTItMTY7MjAxNS0xMi0xNzsyMDE1LTEyLTE4OzIwMTUtMTItMTk7MjAxNS0xMi0yMDsyMDE1LTEyLTIxOzIwMTUtMTItMjI7MjAxNS0xMi0yMzsyMDE1LTEyLTI0OzIwMTUtMTItMjU7MjAxNS0xMi0yNjsyMDE1LTEyLTI3OzIwMTUtMTItMjg7MjAxNS0xMi0yOTsyMDE1LTEyLTMwOzIwMTUtMTItMzE7MjAxNi0wMS0wMTsyMDE2LTAxLTAyOzIwMTYtMDEtMDM7MjAxNi0wMS0wNDsyMDE2LTAxLTA1OzIwMTYtMDEtMDY7MjAxNi0wMS0wNzsyMDE2LTAxLTA4OzIwMTYtMDEtMDk7MjAxNi0wMS0xMDsyMDE2LTAxLTExOzIwMTYtMDEtMTI7MjAxNi0wMS0xMzsyMDE2LTAxLTE0OzIwMTYtMDEtMTU7MjAxNi0wMS0xNjsyMDE2LTAxLTE3OzIwMTYtMDEtMTg7MjAxNi0wMS0xOTsyMDE2LTAxLTIwOzIwMTYtMDEtMjE7MjAxNi0wMS0yMjsyMDE2LTAxLTIzOzIwMTYtMDEtMjQ7PjtAPDYxMzs3MTM7MTE0OzIxNDszMTQ7NDE0OzUxNDs2MTQ7NzE0OzExNTsyMTU7MzE1OzQxNTs1MTU7NjE1OzcxNTsxMTY7MjE2OzMxNjs0MTY7NTE2OzYxNjs3MTY7MTE3OzIxNzszMTc7NDE3OzUxNzs2MTc7NzE3OzExODsyMTg7MzE4OzQxODs1MTg7NjE4OzcxODsxMTk7MjE5OzMxOTs0MTk7NTE5OzYxOTs3MTk7MTIwOzIyMDszMjA7NDIwOzUyMDs2MjA7NzIwOz4+O2w8aTwwPjs+Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8bnlyO3hxOz4+Oz47dDxpPDUxPjtAPDIwMTUtMTItMDU7MjAxNS0xMi0wNjsyMDE1LTEyLTA3OzIwMTUtMTItMDg7MjAxNS0xMi0wOTsyMDE1LTEyLTEwOzIwMTUtMTItMTE7MjAxNS0xMi0xMjsyMDE1LTEyLTEzOzIwMTUtMTItMTQ7MjAxNS0xMi0xNTsyMDE1LTEyLTE2OzIwMTUtMTItMTc7MjAxNS0xMi0xODsyMDE1LTEyLTE5OzIwMTUtMTItMjA7MjAxNS0xMi0yMTsyMDE1LTEyLTIyOzIwMTUtMTItMjM7MjAxNS0xMi0yNDsyMDE1LTEyLTI1OzIwMTUtMTItMjY7MjAxNS0xMi0yNzsyMDE1LTEyLTI4OzIwMTUtMTItMjk7MjAxNS0xMi0zMDsyMDE1LTEyLTMxOzIwMTYtMDEtMDE7MjAxNi0wMS0wMjsyMDE2LTAxLTAzOzIwMTYtMDEtMDQ7MjAxNi0wMS0wNTsyMDE2LTAxLTA2OzIwMTYtMDEtMDc7MjAxNi0wMS0wODsyMDE2LTAxLTA5OzIwMTYtMDEtMTA7MjAxNi0wMS0xMTsyMDE2LTAxLTEyOzIwMTYtMDEtMTM7MjAxNi0wMS0xNDsyMDE2LTAxLTE1OzIwMTYtMDEtMTY7MjAxNi0wMS0xNzsyMDE2LTAxLTE4OzIwMTYtMDEtMTk7MjAxNi0wMS0yMDsyMDE2LTAxLTIxOzIwMTYtMDEtMjI7MjAxNi0wMS0yMzsyMDE2LTAxLTI0Oz47QDw2MTM7NzEzOzExNDsyMTQ7MzE0OzQxNDs1MTQ7NjE0OzcxNDsxMTU7MjE1OzMxNTs0MTU7NTE1OzYxNTs3MTU7MTE2OzIxNjszMTY7NDE2OzUxNjs2MTY7NzE2OzExNzsyMTc7MzE3OzQxNzs1MTc7NjE3OzcxNzsxMTg7MjE4OzMxODs0MTg7NTE4OzYxODs3MTg7MTE5OzIxOTszMTk7NDE5OzUxOTs2MTk7NzE5OzEyMDsyMjA7MzIwOzQyMDs1MjA7NjIwOzcyMDs+PjtsPGk8MD47Pj47Oz47dDx0PDt0PGk8MT47QDzlha07PjtAPDY7Pj47bDxpPDA+Oz4+Ozs+O3Q8dDw7dDxpPDE+O0A85Y2VOz47QDzljZU7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8endzamQ7c2o7Pj47Pjt0PGk8MTA+O0A856ysMSwy6IqCO+esrDMsNOiKgjvnrKw1LDboioI756ysNyw46IqCO+esrDksMTAsMTHoioI75LiK5Y2IO+S4i+WNiDvmmZrkuIo755m95aSpO+aVtOWkqTs+O0A8JzEnfCcxJywnMCcsJzAnLCcwJywnMCcsJzAnLCcwJywnMCcsJzAnOycyJ3wnMCcsJzMnLCcwJywnMCcsJzAnLCcwJywnMCcsJzAnLCcwJzsnMyd8JzAnLCcwJywnNScsJzAnLCcwJywnMCcsJzAnLCcwJywnMCc7JzQnfCcwJywnMCcsJzAnLCc3JywnMCcsJzAnLCcwJywnMCcsJzAnOyc1J3wnMCcsJzAnLCcwJywnMCcsJzknLCcwJywnMCcsJzAnLCcwJzsnNid8JzEnLCczJywnMCcsJzAnLCcwJywnMCcsJzAnLCcwJywnMCc7JzcnfCcwJywnMCcsJzUnLCc3JywnMCcsJzAnLCcwJywnMCcsJzAnOyc4J3wnMCcsJzAnLCcwJywnMCcsJzknLCcwJywnMCcsJzAnLCcwJzsnOSd8JzEnLCczJywnNScsJzcnLCcwJywnMCcsJzAnLCcwJywnMCc7JzEwJ3wnMScsJzMnLCc1JywnNycsJzknLCcwJywnMCcsJzAnLCcwJzs+Pjs+Ozs+O3Q8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDE+Oz47bDx0PHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Ozs+Oz4+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDx4bjt4bjs+Pjs+O3Q8aTwxNj47QDwyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7MjAxNi0yMDE3OzIwMTctMjAxODsyMDE4LTIwMTk7PjtAPDIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjsyMDE2LTIwMTc7MjAxNy0yMDE4OzIwMTgtMjAxOTs+Pjs+Ozs+O3Q8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs+O3Q8QDA8O0AwPDs7Ozs7Ozs7Ozs7Ozs7Ozs7QDA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Ozs7Pjs7Pjs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDE+Oz47bDx0PHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Ozs+Oz4+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDx4bjt4bjs+Pjs+O3Q8aTwxNj47QDwyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7MjAxNi0yMDE3OzIwMTctMjAxODsyMDE4LTIwMTk7PjtAPDIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjsyMDE2LTIwMTc7MjAxNy0yMDE4OzIwMTgtMjAxOTs+Pjs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDE+Oz47bDx0PHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Ozs+Oz4+Oz4+Oz4+Oz6n0ac3No4TyAvsGS+w8KEGvBOFCw==";
				HttpUtil.ddlDsz = getparam(bt_ddlDsz, ddlDsz);
				HttpUtil.kssj = getparam(bt_kssj, kssj);
				HttpUtil.jssj = getparam(bt_jslb, jssj);
				HttpUtil.xqj = getparam(bt_xqj, xqj);;
				HttpUtil.sjd = getparam(bt_sjd, sjd);
				HttpUtil.xiaoq = getparam(bt_xq, xiaoqu);
				HttpUtil.jslb = getparam(bt_jslb, jslb);
				HttpUtil.max_zws = max_zws.getText().toString();
				HttpUtil.min_zws = min_zws.getText().toString();
				final ProgressDialog dialog1 = CommonUtil.getProcessDialog(
						ClassRoomActivity.this, "正在处理...");
				dialog1.show();
				RequestParams params = HttpUtil.getClassroomRequestParams();
				// 请求地址
				HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
				HttpUtil.URL_MAIN = HttpUtil.URL_QUERY.replace("QUERY",
						linkService.getLinkByName(LinkUtil.JSCX));// 获得请求地址
				HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
				HttpUtil.getClient().setURLEncodingEnabled(true);
				HttpUtil.post(HttpUtil.URL_MAIN, params,
						new AsyncHttpResponseHandler()
						{
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody)
							{
								try
								{
									String resultContent = new String(
											responseBody, "gb2312");
									// FileUtils.write(resultContent);
									parseClassroom(resultContent);
								} catch (UnsupportedEncodingException e)
								{
									e.printStackTrace();
								} finally
								{
									dialog1.dismiss();
								}
							}
							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error)
							{
								Toast.makeText(getApplicationContext(),
										"处理失败><", Toast.LENGTH_SHORT).show();
								dialog1.dismiss();
							}
						});
			}
		});
	}
	// 视图初始化
	private void initView()
	{
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mToolBarTextView.setText("查询教室");
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		bt_xq = (Button) findViewById(R.id.bt_xq);
		bt_jslb = (Button) findViewById(R.id.bt_jslb);
		bt_kssj = (Button) findViewById(R.id.bt_kssj);
		bt_jssj = (Button) findViewById(R.id.bt_jssj);
		bt_xqj = (Button) findViewById(R.id.bt_xqj);
		bt_ddlDsz = (Button) findViewById(R.id.bt_ddlDsz);
		bt_sjd = (Button) findViewById(R.id.bt_sjd);
		min_zws = (EditText) findViewById(R.id.min_zws);
		max_zws = (EditText) findViewById(R.id.max_zws);
		bt_query_classroom = (Button) findViewById(R.id.bt_query_classroom);
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				bundle = (Bundle) msg.obj;
				bt_xq.setText(xiaoqu.get(xq_selected));
				bt_jslb.setText(jslb.get(jslb_selected));
				bt_kssj.setText(kssj.get(kssj_selected));
				bt_jssj.setText(jssj.get(jssj_selected));
				bt_sjd.setText(sjd.get(sjd_selected));
				bt_ddlDsz.setText(ddlDsz.get(ddlDsz_selected));
				bt_xqj.setText(xqj.get(xqj_selected));
			}
		};
	}
	// 变量初始化
	private void initValue()
	{
		linkService = LinkService.getLinkService();
		classroomService = ClassroomService.getClassroomService();
		HttpUtil.getQuery(ClassRoomActivity.this, linkService, LinkUtil.JSCX,
				new QueryCallback()
				{
					@Override
					public String handleResult(byte[] result)
					{
						String ret = null;
						try
						{
							ret = new String(result, "gb2312");
							parse(ret);
						} catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
						return null;
					}
				});
	}
	/**
	 * 解析查询出的教室
	 * 
	 * @param resultContent
	 */
	private void parseClassroom(String resultContent)
	{
		Document document = Jsoup.parse(resultContent);
		Elements elements = document.select("span[id=lblbt]");
		summary = elements.get(0).ownText();
		classrooms = getClassroom(document);
	}
	/**
	 * 解析出返回的教室
	 * 
	 * @param document
	 * @return
	 */
	private ArrayList<Classroom> getClassroom(Document document)
	{
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
		Elements elements = document.select("table[id=DataGrid1]");
		Elements trs = elements.get(0).getElementsByTag("tr");
		trs.remove(0);
		Parcel parcel = Parcel.obtain();
		for (Element tr : trs)
		{
			Elements tds = tr.getElementsByTag("td");
			Classroom classroom = Classroom.CREATOR.createFromParcel(parcel);
			classroom.setJsbh(tds.get(0).ownText());
			classroom.setJsmc(tds.get(1).ownText());
			classroom.setJslb(tds.get(2).ownText());
			classroom.setXq(tds.get(3).ownText());
			classroom.setSkzws(tds.get(4).ownText());
			classroom.setSybm(tds.get(5).ownText());
			classroom.setKszws(tds.get(6).ownText());
			classroom.setYyqk(tds.get(7).ownText());
			System.out.println(classroom.toString());
			classrooms.add(classroom);
		}
		parcel.recycle();
		Intent intent = new Intent(ClassRoomActivity.this, ShowCrActivity.class);
		intent.putParcelableArrayListExtra("classrooms", classrooms);
		intent.putExtra("summary", summary);
		startActivity(intent);
		return null;
	}
	/**
	 * 解析查询教室网页
	 * 
	 * @param ret
	 */
	private void parse(String ret)
	{
		// FileUtils.write(ret);
		Document document = Jsoup.parse(ret);
		xiaoqu = getXiaoqu(document);
		jslb = getJslb(document);
		kssj = getKssj(document);
		jssj = getJssj(document);
		xqj = getXqj(document);
		ddlDsz = getDdlDsz(document);
		sjd = getSjd(document);
		__VIEWSTATE = getViewState(document);
		Bundle bundle = new Bundle();
		bundle.putStringArray("jslb", objArr2StrArr(jslb.values().toArray()));
		bundle.putStringArray("xiaoqu",
				objArr2StrArr(xiaoqu.values().toArray()));
		bundle.putStringArray("kssj", objArr2StrArr(kssj.values().toArray()));
		bundle.putStringArray("jssj", objArr2StrArr(jssj.values().toArray()));
		bundle.putStringArray("xqj", objArr2StrArr(xqj.values().toArray()));
		bundle.putStringArray("ddlDsz",
				objArr2StrArr(ddlDsz.values().toArray()));
		bundle.putStringArray("sjd", objArr2StrArr(sjd.values().toArray()));
		Message message = handler.obtainMessage();
		message.what = 1;
		message.obj = bundle;
		handler.sendMessage(message);
	}
	/**
	 * 获取ViewState
	 * 
	 * @param document
	 * @return
	 */
	private String getViewState(Document document)
	{
		Elements elements = document.select("input[name=__VIEWSTATE]");
		Element element = elements.get(0);
		if (element.hasAttr("value"))
		{
			Log.d("__VIEWSTATE", element.attr("value"));
			return element.attr("value");
		}
		return null;
	}
	/**
	 * 获取时间段
	 * 
	 * @param document
	 * @return
	 */
	private LinkedHashMap<String, String> getSjd(Document document)
	{
		Elements elements = document.select("select[id=sjd]");
		Element sjd = elements.get(0);
		LinkedHashMap<String, String> sjdMap = new LinkedHashMap<String, String>();
		List<Element> nodes = sjd.getElementsByTag("option");
		sjd_selected = nodes.get(0).attr("value");
		for (Element node : nodes)
		{
			if (node.hasAttr("selected"))
			{
				sjd_selected = node.attr("value");
			}
			if (node.hasAttr("value"))
				sjdMap.put(node.attr("value"), node.childNode(0).toString());
		}
		return sjdMap;
	}
	/**
	 * 获取单双周
	 * 
	 * @param document
	 * @return
	 */
	private LinkedHashMap<String, String> getDdlDsz(Document document)
	{
		Elements elements = document.select("select[id=ddlDsz]");
		Element ddlDsz = elements.get(0);
		LinkedHashMap<String, String> ddlDszMap = new LinkedHashMap<String, String>();
		List<Element> nodes = ddlDsz.getElementsByTag("option");
		ddlDsz_selected = nodes.get(0).attr("value");
		for (Element node : nodes)
		{
			if (node.hasAttr("selected"))
			{
				ddlDsz_selected = node.attr("value");
			}
			if (node.hasAttr("value"))
				ddlDszMap.put(node.attr("value"), node.childNode(0).toString());
		}
		return ddlDszMap;
	}
	/**
	 * 获取星期几
	 * 
	 * @param document
	 * @return
	 */
	private LinkedHashMap<String, String> getXqj(Document document)
	{
		Elements elements = document.select("select[id=xqj]");
		Element xqj = elements.get(0);
		LinkedHashMap<String, String> xqjMap = new LinkedHashMap<String, String>();
		List<Node> nodes = xqj.childNodes();
		for (Node node : nodes)
		{
			if (node.hasAttr("selected"))
			{
				xqj_selected = node.attr("value");
			}
			if (node.hasAttr("value"))
				xqjMap.put(node.attr("value"), node.childNode(0).toString());
		}
		return xqjMap;
	}
	/**
	 * 获取结束时间
	 * 
	 * @param document
	 * @return
	 */
	private LinkedHashMap<String, String> getJssj(Document document)
	{
		Elements elements = document.select("select[id=jssj]");
		Element jssj = elements.get(0);
		LinkedHashMap<String, String> jssjMap = new LinkedHashMap<String, String>();
		List<Node> nodes = jssj.childNodes();
		for (Node node : nodes)
		{
			if (node.hasAttr("selected"))
			{
				jssj_selected = node.attr("value");
			}
			if (node.hasAttr("value"))
				jssjMap.put(node.attr("value"), node.childNode(0).toString());
		}
		return jssjMap;
	}
	/**
	 * 获取开始时间
	 * 
	 * @param document
	 * @return
	 */
	private LinkedHashMap<String, String> getKssj(Document document)
	{
		Elements elements = document.select("select[id=kssj]");
		Element kssj = elements.get(0);
		LinkedHashMap<String, String> kssjMap = new LinkedHashMap<String, String>();
		List<Node> nodes = kssj.childNodes();
		for (Node node : nodes)
		{
			if (node.hasAttr("selected"))
			{
				kssj_selected = node.attr("value");
			}
			if (node.hasAttr("value"))
				kssjMap.put(node.attr("value"), node.childNode(0).toString());
		}
		return kssjMap;
	}
	/**
	 * Object[] to String[]
	 * 
	 * @param array
	 * @return
	 */
	private String[] objArr2StrArr(Object[] array)
	{
		String[] ret = new String[array.length];
		for (int i = 0; i < array.length; i++)
		{
			ret[i] = (String) array[i];
		}
		return ret;
	}
	/**
	 * 获取教室类别
	 * 
	 * @param document
	 */
	private LinkedHashMap<String, String> getJslb(Document document)
	{
		Elements elements = document.select("select[id=jslb]");
		Element jslb = elements.get(0);
		LinkedHashMap<String, String> jslbMap = new LinkedHashMap<String, String>();
		List<Node> nodes = jslb.childNodes();
		for (Node node : nodes)
		{
			if (node.childNodeSize() == 0)
			{
				jslbMap.put(node.attr("value"), "");
			} else
			{
				jslbMap.put(node.attr("value"), node.childNode(0).toString());
			}
			if (node.hasAttr("selected"))
			{
				jslb_selected = node.attr("value");
			}
		}
		return jslbMap;
	}
	/**
	 * 获取校区
	 * 
	 * @param document
	 * @return
	 */
	private static LinkedHashMap<String, String> getXiaoqu(Document document)
	{
		Elements elements = document.select("select[id=xiaoq]");
		Element xiaoqus = elements.get(0);
		LinkedHashMap<String, String> xiaoqu = new LinkedHashMap<String, String>();
		List<Node> nodes = xiaoqus.childNodes();
		for (Node node : nodes)
		{
			if (node.childNodeSize() == 0)
			{
				xiaoqu.put(node.attr("value"), "");
			} else
			{
				xiaoqu.put(node.attr("value"), node.childNode(0).toString());
			}
			if (node.hasAttr("selected"))
			{
				xq_selected = node.attr("value");
			}
		}
		return xiaoqu;
	}
	// button的事件点击弹出listview
	View.OnClickListener myClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.bt_xq :
					viewClick(bt_xq, "xiaoqu");
					break;
				case R.id.bt_jslb :
					viewClick(bt_jslb, "jslb");
					break;
				case R.id.bt_kssj :
					viewClick(bt_kssj, "kssj");
					break;
				case R.id.bt_jssj :
					viewClick(bt_jssj, "jssj");
					break;
				case R.id.bt_xqj :
					viewClick(bt_xqj, "xqj");
					break;
				case R.id.bt_ddlDsz :
					viewClick(bt_ddlDsz, "ddlDsz");
					break;
				case R.id.bt_sjd :
					viewClick(bt_sjd, "sjd");
					break;
			}
		}
		/**
		 * @param bt_xq
		 * @param string
		 * 
		 */
		private void viewClick(final Button button, final String value)
		{
			if (bundle == null)
			{
				Toast.makeText(ClassRoomActivity.this, "请退出该界面重新进入~",
						Toast.LENGTH_SHORT).show();
				return;
			}
			final String[] array = bundle.getStringArray(value);
			new AlertDialog.Builder(ClassRoomActivity.this)
					.setSingleChoiceItems(array, 0,
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									String oldText = button.getText()
											.toString();
									button.setText(array[which]);
									if (!oldText.equals(array[which]))
									{
										if (value.equals("kssj")
												|| value.equals("jssj")
												|| value.equals("ddlDsz")
												|| value.equals("xqj")
												|| value.equals("sjd"))
										{
											reQuery(value);
										}
									}
									dialog.dismiss();
								}
								/**
								 * 重新请求
								 * 
								 * @param target
								 */
								private void reQuery(String target)
								{
									HttpUtil.__EVENTTARGET = target;
									HttpUtil.__VIEWSTATE = __VIEWSTATE;
									HttpUtil.ddlDsz = getparam(bt_ddlDsz,
											ddlDsz);
									HttpUtil.kssj = getparam(bt_kssj, kssj);
									HttpUtil.jssj = getparam(bt_jslb, jssj);
									HttpUtil.xqj = getparam(bt_xqj, xqj);;
									HttpUtil.sjd = getparam(bt_sjd, sjd);
									final ProgressDialog dialog1 = CommonUtil
											.getProcessDialog(
													ClassRoomActivity.this,
													"正在处理...");
									dialog1.show();
									RequestParams params = HttpUtil
											.getClassroomRequestParams();
									HttpUtil.URL_MAIN = HttpUtil.URL_QUERY
											.replace("QUERY", LinkUtil.JSCX);// 获得请求地址
									HttpUtil.getClient().addHeader(
											"Refer",
											HttpUtil.URL_QUERY.replace("QUERY",
													LinkUtil.JSCX));
									HttpUtil.getClient().setURLEncodingEnabled(
											true);
									HttpUtil.post(HttpUtil.URL_MAIN, params,
											new AsyncHttpResponseHandler()
											{
												@Override
												public void onSuccess(
														int statusCode,
														Header[] headers,
														byte[] responseBody)
												{
													try
													{
														String resultContent = new String(
																responseBody,
																"gb2312");
														parse(resultContent);
													} catch (UnsupportedEncodingException e)
													{
														e.printStackTrace();
													} finally
													{
														dialog1.dismiss();
													}
												}
												@Override
												public void onFailure(
														int statusCode,
														Header[] headers,
														byte[] responseBody,
														Throwable error)
												{
													Toast.makeText(
															getApplicationContext(),
															"处理失败><",
															Toast.LENGTH_SHORT)
															.show();
													dialog1.dismiss();
												}
											});
								}
							}).create().show();
		}
	};
	private EditText min_zws;
	private EditText max_zws;
	private ArrayList<Classroom> classrooms;
	private String summary;
	/**
	 * 获取button上的文本的值对应的key
	 * 
	 * @param button
	 * @param map
	 * @return
	 */
	private String getparam(Button button, LinkedHashMap<String, String> map)
	{
		String value = button.getText().toString();
		Set<String> set = map.keySet();
		for (String key : set)
		{
			if (map.get(key).equals(value))
			{
				return key;
			}
		}
		return "";
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
