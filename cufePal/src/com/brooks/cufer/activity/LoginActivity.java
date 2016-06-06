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
	private EditText username, password, secrectCode;// �˺ţ����룬��֤��
	private ImageView code;// ��֤��
	private Button flashCode, login;// ˢ����֤�룬��¼
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
		initValue();// ������ʼ��
		initView();// ��ͼ��ʼ��
		showAlert();
		initEvent();// �¼���ʼ��
		initCookie(this);// cookie��ʼ��
		initDatabase();// ���ݿ��ʼ��
		getCode();
	}
	/**
	 * ��ʾע��
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
							"�������ʹ�õ�����(���в�Wifi-CUFE)�޷��򿪽���ϵͳ��վ,"
									+ "��ô������ͬ���޷�ʹ�ø�app�����йز�ѯ������Ϣ����Ĳ���(������¼),���������~")
					.setTitle("ע��")
					.setNegativeButton("��������",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									util1.setKeyData("noAlert", "true");
								}
							})
					.setPositiveButton("ȷ��",
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
	 * ��ʼ��View
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
	 * ��ʼ�¼�
	 */
	private void initEvent()
	{
		// һЩ�е���¼��ĳ�ʼ��
		flashCode.setOnClickListener(listener);
		login.setOnClickListener(listener);
	}
	/**
	 * ��ʼ�����ݿ�
	 */
	private void initDatabase()
	{
		// ��assetsĿ¼�µ�litepal.xml���������ݿ������汾��ӳ���ϵ
		db = Connector.getDatabase();
	}
	/**
	 * ��ʼ��Cookie
	 */
	private void initCookie(Context context)
	{
		// ����������ǰ��ʼ��
		cookie = new PersistentCookieStore(context);
		HttpUtil.getClient().setCookieStore(cookie);
	}
	/**
	 * ��ת����ҳ
	 * 
	 * @param name
	 *            ѧ������
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
	 * ��¼
	 */
	private void login()
	{
		// ��ȡ������û������������֤�룬������ǰ��Ŀո�
		HttpUtil.__VIEWSTATE = "dDwyODE2NTM0OTg7Oz61Zd0asz08gVFyU4yEAJCAIam0cg==";
		HttpUtil.txtUserName = username.getText().toString().trim();
		HttpUtil.TextBox2 = password.getText().toString().trim();
		HttpUtil.txtSecretCode = secrectCode.getText().toString().trim();
		// ��֤��Ϊ��
		if (TextUtils.isEmpty(HttpUtil.txtSecretCode))
		{
			Toast.makeText(getApplicationContext(), "��֤�벻��Ϊ��",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// �û���������Ϊ��
		if (TextUtils.isEmpty(HttpUtil.txtUserName)
				|| TextUtils.isEmpty(HttpUtil.TextBox2))
		{
			Toast.makeText(getApplicationContext(), "�˺Ż������벻��Ϊ��!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "���ڵ�¼�У�����");
		dialog.show();
		RequestParams params = HttpUtil.getLoginRequestParams();// ����������
		// ��¼�ɹ����׵�ַ
		HttpUtil.URL_MAIN = "http://202.205.208.96/xs_main.aspx?xh=XH";
		// �����ַ
		HttpUtil.URL_QUERY = "http://202.205.208.96/QUERY";
		HttpUtil.URL_MAIN = HttpUtil.URL_MAIN.replace("XH",
				HttpUtil.txtUserName);// ��������ַ
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
							if (resultContent.contains("��֤�벻��ȷ"))
							{
								Toast.makeText(getApplicationContext(),
										"��֤�����", Toast.LENGTH_SHORT).show();
								getCode();
								return;
							}
							if (linkService.isLogin(resultContent) != null)
							{
								String name = linkService
										.isLogin(resultContent);
								String ret = linkService
										.parseMenu(resultContent);
								Log.d("������Ϣ", "login success:" + ret);
								Toast.makeText(getApplicationContext(),
										"��¼�ɹ�������", Toast.LENGTH_SHORT).show();
								jump2Main(name);
							} else
							{
								getCode();
								Toast.makeText(getApplicationContext(),
										"�˺Ż���������󣡣���", Toast.LENGTH_SHORT)
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
						Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ�������",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
	}
	/**
	 * �����֤��
	 */
	private void getCode()
	{
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "���ڻ�ȡ��֤��");
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
				Toast.makeText(getApplicationContext(), "��֤���ȡʧ�ܣ�����",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
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
