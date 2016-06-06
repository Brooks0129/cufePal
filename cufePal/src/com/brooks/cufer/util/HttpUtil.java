package com.brooks.cufer.util;
import org.apache.http.Header;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.brooks.cufer.activity.LoginActivity;
import com.brooks.cufer.service.LinkService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年11月27日
 *
 * @描述 http的请求工具类
 */
public class HttpUtil
{
	private static AsyncHttpClient client = new AsyncHttpClient();
	// Host地址
	public static final String HOST = "202.205.208.96";
	// 基础地址
	public static final String URL_BASE = "http://202.205.208.96/";
	// 验证码地址
	public static final String URL_CODE = "http://202.205.208.96/CheckCode.aspx";
	// 登陆地址
	public static final String URL_LOGIN = "http://202.205.208.96/default2.aspx";
	// 登录成功的首地址
	public static String URL_MAIN = "http://202.205.208.96/xs_main.aspx?xh=XH";
	// 请求地址
	public static String URL_QUERY = "http://202.205.208.96/QUERY";
	/**
	 * 请求参数
	 */
	public static String Button1 = "";
	public static String hidPdrs = "";
	public static String hidsc = "";
	public static String lbLanguage = "";
	public static String RadioButtonList1 = "学生";
	public static String __VIEWSTATE = "dDwyODE2NTM0OTg7Oz61Zd0asz08gVFyU4yEAJCAIam0cg==";
	public static String TextBox2 = null;
	public static String txtSecretCode = null;
	public static String txtUserName = null;
	/**
	 * 请求查询教室的参数
	 */
	public static String __EVENTTARGET = null;
	public static String __EVENTARGUMENT = "";
	public static String xiaoq = "";
	public static String jslb = "";
	public static String min_zws = "0";
	public static String max_zws = "";
	public static String kssj = null;
	public static String jssj = null;
	public static String xqj = null;
	public static String ddlDsz = null;
	public static String sjd = null;
	public static String xn = "2015-2016";
	public static String xq = "1";
	public static String ddlSyXn = "2015-2016";
	public static String ddlSyxq = "1";
	public static String Button2 = "空教室查询";
	static
	{
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
		// 设置请求头
		client.addHeader("Host", HOST);
		client.addHeader("Referer", URL_LOGIN);
		client.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
	}
	/**
	 * get,用一个完整url获取string对象
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void get(String urlString, AsyncHttpResponseHandler res)
	{
		client.get(urlString, res);
	}
	/**
	 * 
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res)
	{
		client.get(urlString, params, res);
	}
	/**
	 * get,下载数据使用，会返回byte数据
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void get(String uString, BinaryHttpResponseHandler bHandler)
	{
		client.get(uString, bHandler);
	}
	/**
	 * post,不带参数
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void post(String urlString, AsyncHttpResponseHandler res)
	{
		client.post(urlString, res);
	}
	/**
	 *
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res)
	{
		client.post(urlString, params, res);
	}
	/**
	 * post,返回二进制数据时使用，会返回byte数据
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void post(String uString, BinaryHttpResponseHandler bHandler)
	{
		client.post(uString, bHandler);
	}
	/**
	 * 返回请求客户端
	 * 
	 * @return
	 */
	public static AsyncHttpClient getClient()
	{
		return client;
	}
	/**
	 * 
	 * 登录操作的参数
	 * 
	 * @return
	 */
	public static RequestParams getLoginRequestParams()
	{
		// 设置请求参数
		RequestParams params = new RequestParams();
		params.add("__VIEWSTATE", __VIEWSTATE);
		params.add("Button1", Button1);
		params.add("hidPdrs", hidPdrs);
		params.add("hidsc", hidsc);
		params.add("lbLanguage", lbLanguage);
		params.add("RadioButtonList1", RadioButtonList1);
		params.add("TextBox2", TextBox2);
		params.add("txtSecretCode", txtSecretCode);
		params.add("txtUserName", txtUserName);
		return params;
	}
	/**
	 * 查询空教室的参数
	 * 
	 * @return
	 */
	public static RequestParams getClassroomRequestParams()
	{
		RequestParams params = new RequestParams();
		params.add("__EVENTTARGET", __EVENTTARGET);
		params.add("__EVENTARGUMENT", __EVENTARGUMENT);
		params.add("__VIEWSTATE", __VIEWSTATE);
		params.add("xiaoq", xiaoq);
		params.add("jslb", jslb);
		params.add("min_zws", min_zws);
		params.add("max_zws", max_zws);
		params.add("kssj", kssj);
		params.add("jssj", jssj);
		params.add("xqj", xqj);
		params.add("ddlDsz", ddlDsz);
		params.add("sjd", sjd);
		params.add("xn", xn);
		params.add("xq", xq);
		params.add("ddlSyXn", ddlSyXn);
		params.add("ddlSyxq", ddlSyxq);
		params.add("Button2", Button2);
		params.add("dpDataGrid1:txtChoosePage:", "1");
		params.add("dpDataGrid1:txtPageSize", "600");
		return params;
	}
	/**
	 * 查询考试成绩的参数
	 */
	public static RequestParams getGradeRequestParams()
	{
		RequestParams params = new RequestParams();
		params.add("__EVENTTARGET", "");
		params.add("__EVENTARGUMENT", "");
		params.add("ddlXN", "");
		params.add("ddlXQ", "");
		params.add("ddl_kcxz", "");
		params.add("btn_zcj", "历年成绩");
		return params;
	}
	public interface QueryCallback
	{
		public String handleResult(byte[] result);
	}
	public static void getQuery(final Context context, LinkService linkService,
			final String urlName, final QueryCallback callback)
	{
		final ProgressDialog dialog = CommonUtil.getProcessDialog(context,
				"正在获取" + urlName);
		dialog.show();
		String link = linkService.getLinkByName(urlName);
		if (link != null)
		{
			HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
			HttpUtil.URL_QUERY = HttpUtil.URL_QUERY.replace("QUERY", link);
		} else
		{
			Toast.makeText(context, "链接出现错误", Toast.LENGTH_SHORT).show();
			return;
		}
		HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
		HttpUtil.getClient().setURLEncodingEnabled(true);
		HttpUtil.get(HttpUtil.URL_QUERY, new AsyncHttpResponseHandler()
		{
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				if (callback != null)
				{
					callback.handleResult(arg2);
				}
				/*
				 * Toast.makeText(context, "请求成功！！", Toast.LENGTH_LONG) .show();
				 */
				dialog.dismiss();
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3)
			{
				dialog.dismiss();
				Toast.makeText(context, "请求失败><", Toast.LENGTH_SHORT).show();
				new AlertDialog.Builder(context)
						.setMessage("请求失败可能有多种原因，您可以尝试重新登录~")
						.setPositiveButton("重新登录",
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
										SharedPreferenceUtil util = new SharedPreferenceUtil(
												context, "accountInfo");
										util.setKeyData("isLogin", "false");
										context.startActivity(new Intent(
												context, LoginActivity.class));
										((Activity) context).finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
									}
								}).create().show();
			}
		});
	}
	public static RequestParams getGradeAnaRequestParams()
	{
		RequestParams params = new RequestParams();
		params.add("__EVENTTARGET", "");
		params.add("__EVENTARGUMENT", "");
		params.add("hidLanguage", "");
		params.add("ddlXN", "");
		params.add("ddlXQ", "");
		params.add("ddl_kcxz", "");
		params.add("Button1", "成绩统计");
		return params;
	}
}