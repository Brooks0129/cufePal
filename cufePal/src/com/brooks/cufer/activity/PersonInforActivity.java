package com.brooks.cufer.activity;
import java.io.UnsupportedEncodingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.brooks.cufer.R;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.FileUtils;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.LinkUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.proguard.v;
public class PersonInforActivity extends AppCompatActivity
{
	// Content View Elements
	private ImageView userface;
	private TextView tv_xm;
	private TextView tv_xh;
	private TextView tv_lbl_xb;
	private TextView tv_lbl_csrq;
	private TextView tv_lbl_mz;
	private TextView tv_lbl_zzmm;
	private TextView tv_lbl_lydq;
	private TextView tv_lbl_xy;
	private TextView tv_lbl_zymc;
	private TextView tv_lbl_xzb;
	private TextView tv_lbl_xz;
	private TextView tv_lbl_xjzt;
	private TextView tv_lbl_dqszj;
	private TextView tv_lbl_ksh;
	private TextView tv_dzyxdz;
	private TextView tv_lxdh;
	private TextView tv_lbl_sfzh;
	private TextView tv_lbl_CC;
	private TextView tv_tbxmpyo;
	private LinkService linkService;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initView();
		initValue();
	}
	Handler handler;
	/**
	 * 初始化值
	 */
	private void initValue()
	{
		linkService = LinkService.getLinkService();
		HttpUtil.getQuery(PersonInforActivity.this, linkService, LinkUtil.GRXX,
				new HttpUtil.QueryCallback()
				{
					@Override
					public String handleResult(byte[] result)
					{
						try
						{
							parse(new String(result, "gb2312"));
						} catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
						return null;
					}
				});
	}
	/*
	 * 解析网页
	 */
	private void parse(String string)
	{
		Document document = Jsoup.parse(string);
		String xm = getEleBySpan(document, "xm");
		String xh = getEleBySpan(document, "xh");
		String lbl_xb = getEleBySpan(document, "lbl_xb");
		String lbl_csrq = getEleBySpan(document, "lbl_csrq");
		String lbl_mz = getEleBySpan(document, "lbl_mz");
		String lbl_zzmm = getEleBySpan(document, "lbl_zzmm");
		String lbl_lydq = getEleBySpan(document, "lbl_lydq");
		String lbl_xy = getEleBySpan(document, "lbl_xy");
		String lbl_zymc = getEleBySpan(document, "lbl_zymc");
		String lbl_xzb = getEleBySpan(document, "lbl_xzb");
		String lbl_xz = getEleBySpan(document, "lbl_xz");
		String lbl_xjzt = getEleBySpan(document, "lbl_xjzt");
		String lbl_dqszj = getEleBySpan(document, "lbl_dqszj");
		String lbl_ksh = getEleBySpan(document, "lbl_ksh");
		String lbl_sfzh = getEleBySpan(document, "lbl_sfzh");
		String lbl_CC = getEleBySpan(document, "lbl_CC");
		String dzyxdz = getEleByInput(document, "dzyxdz");
		String lxdh = getEleByInput(document, "lxdh");
		String tbxmpyo = getEleByInput(document, "tbxmpyo");
		Message msg = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putString("xm", xm);
		bundle.putString("xh", xh);
		bundle.putString("lbl_xb", lbl_xb);
		bundle.putString("lbl_csrq", lbl_csrq);
		bundle.putString("lbl_mz", lbl_mz);
		bundle.putString("lbl_zzmm", lbl_zzmm);
		bundle.putString("lbl_lydq", lbl_lydq);
		bundle.putString("lbl_xy", lbl_xy);
		bundle.putString("lbl_zymc", lbl_zymc);
		bundle.putString("lbl_xzb", lbl_xzb);
		bundle.putString("lbl_xz", lbl_xz);
		bundle.putString("lbl_xjzt", lbl_xjzt);
		bundle.putString("lbl_dqszj", lbl_dqszj);
		bundle.putString("lbl_ksh", lbl_ksh);
		bundle.putString("lbl_sfzh", lbl_sfzh);
		bundle.putString("lbl_CC", lbl_CC);
		bundle.putString("dzyxdz", dzyxdz);
		bundle.putString("lxdh", lxdh);
		bundle.putString("tbxmpyo", tbxmpyo);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	/**
	 * 根据id获取input的value的值
	 * 
	 * @param document
	 * @param string
	 */
	private String getEleByInput(Document document, String id)
	{
		Elements elements = document.select("input[id=" + id + "]");
		return elements.get(0).attr("value");
	}
	/**
	 * 根据id获取相应span的值
	 * 
	 * @param document
	 * @param id
	 * @return
	 */
	private String getEleBySpan(Document document, String id)
	{
		Elements elements = document.select("span[id=" + id + "]");
		return elements.get(0).ownText();
	}
	/**
	 * 初始化视图
	 */
	private void initView()
	{
		userface = (ImageView) findViewById(R.id.userface);
		tv_xm = (TextView) findViewById(R.id.tv_xm);
		tv_xh = (TextView) findViewById(R.id.tv_xh);
		tv_lbl_xb = (TextView) findViewById(R.id.tv_lbl_xb);
		tv_lbl_csrq = (TextView) findViewById(R.id.tv_lbl_csrq);
		tv_lbl_mz = (TextView) findViewById(R.id.tv_lbl_mz);
		tv_lbl_zzmm = (TextView) findViewById(R.id.tv_lbl_zzmm);
		tv_lbl_lydq = (TextView) findViewById(R.id.tv_lbl_lydq);
		tv_lbl_xy = (TextView) findViewById(R.id.tv_lbl_xy);
		tv_lbl_zymc = (TextView) findViewById(R.id.tv_lbl_zymc);
		tv_lbl_xzb = (TextView) findViewById(R.id.tv_lbl_xzb);
		tv_lbl_xz = (TextView) findViewById(R.id.tv_lbl_xz);
		tv_lbl_xjzt = (TextView) findViewById(R.id.tv_lbl_xjzt);
		tv_lbl_dqszj = (TextView) findViewById(R.id.tv_lbl_dqszj);
		tv_lbl_ksh = (TextView) findViewById(R.id.tv_lbl_ksh);
		tv_dzyxdz = (TextView) findViewById(R.id.tv_dzyxdz);
		tv_lxdh = (TextView) findViewById(R.id.tv_lxdh);
		tv_lbl_sfzh = (TextView) findViewById(R.id.tv_lbl_sfzh);
		tv_lbl_CC = (TextView) findViewById(R.id.tv_lbl_CC);
		tv_tbxmpyo = (TextView) findViewById(R.id.tv_tbxmpyo);
		handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				Bundle bundle = msg.getData();
				tv_xm.setText(bundle.getString("xm"));
				tv_xh.setText(bundle.getString("xh"));
				tv_lbl_xb.setText(bundle.getString("lbl_xb"));
				tv_lbl_csrq.setText(bundle.getString("lbl_csrq"));
				tv_lbl_mz.setText(bundle.getString("lbl_mz"));
				tv_lbl_zzmm.setText(bundle.getString("lbl_zzmm"));
				tv_lbl_lydq.setText(bundle.getString("lbl_lydq"));
				tv_lbl_xy.setText(bundle.getString("lbl_xy"));
				tv_lbl_zymc.setText(bundle.getString("lbl_zymc"));
				tv_lbl_xzb.setText(bundle.getString("lbl_xzb"));
				tv_lbl_xz.setText(bundle.getString("lbl_xz"));
				tv_lbl_xjzt.setText(bundle.getString("lbl_xjzt"));
				tv_lbl_dqszj.setText(bundle.getString("lbl_dqszj"));
				tv_lbl_ksh.setText(bundle.getString("lbl_ksh"));
				tv_dzyxdz.setText(bundle.getString("dzyxdz"));
				tv_lxdh.setText(bundle.getString("lxdh"));
				tv_lbl_sfzh.setText(bundle.getString("lbl_sfzh"));
				tv_lbl_CC.setText(bundle.getString("lbl_CC"));
				tv_tbxmpyo.setText(bundle.getString("tbxmpyo"));
			};
		};
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
