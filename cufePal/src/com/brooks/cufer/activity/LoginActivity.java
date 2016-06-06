package com.brooks.cufer.activity;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.litepal.tablemanager.Connector;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.brooks.cufer.R;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.CommonUtil;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.SharedPreferenceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
public class LoginActivity extends Activity
{
	private EditText username, password, secrectCode;// 账号，密码，验证码
	private ImageView code;// 验证码
	private Button flashCode, login;// 刷新验证码，登录
	private PersistentCookieStore cookie;
	private SQLiteDatabase db;
	private LinkService linkService;
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.getCode :
					getCode();
					break;
				case R.id.login :
					login();
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initValue();// 变量初始化
		initView();// 视图初始化
		showAlert();
		initEvent();// 事件初始化
		initCookie(this);// cookie初始化
		initDatabase();// 数据库初始化
		getCode();
	}
	/**
	 * 显示注意
	 */
	private void showAlert()
	{
		final SharedPreferenceUtil util1 = new SharedPreferenceUtil(
				getApplicationContext(), "alert");
		String noAlert = util1.getKeyData("noAlert");
		if (noAlert == null || noAlert.equals("") || noAlert.equals("false"))
		{
			new AlertDialog.Builder(LoginActivity.this,
					android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
					.setMessage(
							"如果您所使用的网络(如中财Wifi-CUFE)无法打开教务系统网站,"
									+ "那么该网络同样无法使用该app进行有关查询教务信息方面的操作(包括登录),请更换网络~")
					.setTitle("注意")
					.setNegativeButton("不再提醒",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									util1.setKeyData("noAlert", "true");
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
								}
							}).setCancelable(false).create().show();
		}
	}
	private void initValue()
	{
		linkService = LinkService.getLinkService();
	}
	/**
	 * 初始化View
	 */
	private void initView()
	{
		secrectCode = (EditText) findViewById(R.id.secrectCode);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		flashCode = (Button) findViewById(R.id.getCode);
		login = (Button) findViewById(R.id.login);
		code = (ImageView) findViewById(R.id.codeImage);
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "accountInfo");
		if (util.getKeyData("username") != null)
		{
			username.setText(util.getKeyData("username"));
		}
	}
	/**
	 * 初始事件
	 */
	private void initEvent()
	{
		// 一些列点击事件的初始化
		flashCode.setOnClickListener(listener);
		login.setOnClickListener(listener);
	}
	/**
	 * 初始化数据库
	 */
	private void initDatabase()
	{
		// 在assets目录下的litepal.xml里配置数据库名，版本，映射关系
		db = Connector.getDatabase();
	}
	/**
	 * 初始化Cookie
	 */
	private void initCookie(Context context)
	{
		// 必须在请求前初始化
		cookie = new PersistentCookieStore(context);
		HttpUtil.getClient().setCookieStore(cookie);
	}
	/**
	 * 跳转到主页
	 * 
	 * @param name
	 *            学生姓名
	 */
	private void jump2Main(String name)
	{
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "accountInfo");
		util.setKeyData("username", HttpUtil.txtUserName);
		util.setKeyData("password", HttpUtil.TextBox2);
		util.setKeyData("isLogin", "TRUE");
		util.setKeyData("name", name);
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 登录
	 */
	private void login()
	{
		// 获取输入的用户名、密码和验证码，并忽略前后的空格
		HttpUtil.__VIEWSTATE = "dDwyODE2NTM0OTg7Oz61Zd0asz08gVFyU4yEAJCAIam0cg==";
		HttpUtil.txtUserName = username.getText().toString().trim();
		HttpUtil.TextBox2 = password.getText().toString().trim();
		HttpUtil.txtSecretCode = secrectCode.getText().toString().trim();
		// 验证码为空
		if (TextUtils.isEmpty(HttpUtil.txtSecretCode))
		{
			Toast.makeText(getApplicationContext(), "验证码不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 用户名或密码为空
		if (TextUtils.isEmpty(HttpUtil.txtUserName)
				|| TextUtils.isEmpty(HttpUtil.TextBox2))
		{
			Toast.makeText(getApplicationContext(), "账号或者密码不能为空!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "正在登录中！！！");
		dialog.show();
		RequestParams params = HttpUtil.getLoginRequestParams();// 获得请求参数
		// 登录成功的首地址
		HttpUtil.URL_MAIN = "http://202.205.208.96/xs_main.aspx?xh=XH";
		// 请求地址
		HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
		HttpUtil.URL_MAIN = HttpUtil.URL_MAIN.replace("XH",
				HttpUtil.txtUserName);// 获得请求地址
		HttpUtil.getClient().setURLEncodingEnabled(true);
		HttpUtil.post(HttpUtil.URL_LOGIN, params,
				new AsyncHttpResponseHandler()
				{
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
					{
						try
						{
							String resultContent = new String(arg2, "gb2312");
							if (resultContent.contains("验证码不正确"))
							{
								Toast.makeText(getApplicationContext(),
										"验证码错误", Toast.LENGTH_SHORT).show();
								getCode();
								return;
							}
							if (linkService.isLogin(resultContent) != null)
							{
								String name = linkService
										.isLogin(resultContent);
								String ret = linkService
										.parseMenu(resultContent);
								Log.d("返回信息", "login success:" + ret);
								Toast.makeText(getApplicationContext(),
										"登录成功！！！", Toast.LENGTH_SHORT).show();
								jump2Main(name);
							} else
							{
								getCode();
								Toast.makeText(getApplicationContext(),
										"账号或者密码错误！！！", Toast.LENGTH_SHORT)
										.show();
							}
						} catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						} finally
						{
							dialog.dismiss();
						}
					}
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3)
					{
						Toast.makeText(getApplicationContext(), "登录失败！！！！",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
	}
	/**
	 * 获得验证码
	 */
	private void getCode()
	{
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "正在获取验证码");
		dialog.show();
		HttpUtil.get(HttpUtil.URL_CODE, new AsyncHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody)
			{
				InputStream is = new ByteArrayInputStream(responseBody);
				Bitmap decodeStream = BitmapFactory.decodeStream(is);
				code.setImageBitmap(decodeStream);
				dialog.dismiss();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error)
			{
				Toast.makeText(getApplicationContext(), "验证码获取失败！！！",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
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
