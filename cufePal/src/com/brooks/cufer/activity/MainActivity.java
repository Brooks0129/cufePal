package com.brooks.cufer.activity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Course;
import com.brooks.cufer.model.Exam;
import com.brooks.cufer.model.LinkNode;
import com.brooks.cufer.service.CourseService;
import com.brooks.cufer.service.ExamService;
import com.brooks.cufer.service.LinkService;
import com.brooks.cufer.util.FileUtils;
import com.brooks.cufer.util.HttpUtil;
import com.brooks.cufer.util.LinkUtil;
import com.brooks.cufer.util.SharedPreferenceUtil;
import com.brooks.cufer.util.HttpUtil.QueryCallback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年11月27日
 *
 * @描述 主页面
 */
public class MainActivity extends AppCompatActivity
		implements
			OnMenuItemClickListener
{
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	final int SHARE = 1;
	final int LOGOFF = 2;
	final int EXIT = 3;
	final int FEEDBACK = 4;
	final int ABOUT = 5;
	private LinkService linkService;
	private CourseService courseService;
	private FragmentManager fragmentManager;
	private ContextMenuDialogFragment mMenuDialogFragment;
	private ExamService examService;
	private LinearLayout ll_xsgrkb;
	private LinearLayout ll_gzytjkb;
	private LinearLayout ll_kscx;
	private LinearLayout ll_tydkcx;
	private LinearLayout ll_grxx;
	private LinearLayout ll_kjscx;
	private LinearLayout ll_cjcx;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initValue();// 变量初始化
		initView();// 视图初始化
		initEvent();// 事件初始化
		// addFragment(new MainFragment(), true, R.id.container);
	}
	/**
	 * 初始化变量
	 */
	private void initValue()
	{
		linkService = LinkService.getLinkService();
		courseService = CourseService.getCourseService();
		examService = ExamService.getExamService();
	}
	/**
	 * 初始化View
	 */
	private void initView()
	{
		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();
		List<LinkNode> objects = linkService.findAll();
		ll_xsgrkb = (LinearLayout) findViewById(R.id.ll_xsgrkb);
		ll_gzytjkb = (LinearLayout) findViewById(R.id.ll_gzytjkb);
		ll_kscx = (LinearLayout) findViewById(R.id.ll_kscx);
		ll_tydkcx = (LinearLayout) findViewById(R.id.ll_tydkcx);
		ll_grxx = (LinearLayout) findViewById(R.id.ll_grxx);
		ll_kjscx = (LinearLayout) findViewById(R.id.ll_kjscx);
		ll_cjcx = (LinearLayout) findViewById(R.id.ll_cjcx);
	}
	/**
	 * 初始事件
	 */
	private void initEvent()
	{
		// 学生个人课表
		ll_xsgrkb.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				jump2Kb(false);
			}
		});
		// 各专业推荐课表
		ll_gzytjkb.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "该功能即将开启，敬请期待", 0)
						.show();
			}
		});
		// 考试查询
		ll_kscx.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				jump2xskscx(false);
			}
		});
		// 体育打卡查询
		ll_tydkcx.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "该功能即将开启，敬请期待", 0)
						.show();
			}
		});
		// 个人信息
		ll_grxx.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				jump2grxx();
			}
		});
		// 空教室查询
		ll_kjscx.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				jump2jscx();
			}
		});
		// 成绩查询
		ll_cjcx.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, GradeActivity.class));
			}
		});
	}
	/**
	 * 调到个人信息界面
	 */
	private void jump2grxx()
	{
		startActivity(new Intent(MainActivity.this, PersonInforActivity.class));
	}
	/**
	 * 跳到修改密码界面
	 */
	protected void jump2mmxg()
	{
		startActivity(new Intent(MainActivity.this, UpdatePswActivity.class));
	}
	/**
	 * 跳转到学生考试查询页面
	 * 
	 * @param flag
	 */
	protected void jump2xskscx(boolean flag)
	{
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "flag");
		if (flag)
		{
			// flag为true直接跳转
			util.setKeyData(LinkUtil.XSKSCX, "TRUE");
			Intent intent = new Intent(MainActivity.this, ExamActivity.class);
			startActivity(intent);
		} else
		{
			// flag为false，则先判断是否获取过课表
			// 如果已经获取过课表，则跳转
			String keyData = util.getKeyData(LinkUtil.XSKSCX);
			if (keyData.equals("TRUE"))
			{
				Intent intent = new Intent(MainActivity.this,
						ExamActivity.class);
				startActivity(intent);
			} else
			{
				// 未获取则获取
				HttpUtil.getQuery(MainActivity.this, linkService,
						LinkUtil.XSKSCX, new QueryCallback()
						{
							@Override
							public String handleResult(byte[] result)
							{
								String ret = null;
								try
								{
									String ss = new String(result, "gb2312");
									examService.parseExam(new String(result,
											"gb2312"));
								} catch (Exception e)
								{
									new AlertDialog.Builder(MainActivity.this)
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
									return ret;
								}
								jump2xskscx(true);
								return ret;
							}
						});
			}
		}
	}
	/**
	 * 跳到教室查询页面
	 */
	protected void jump2jscx()
	{
		startActivity(new Intent(MainActivity.this, ClassRoomActivity.class));
	}
	/**
	 * 跳到课表页面
	 */
	private void jump2Kb(boolean flag)
	{
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "flag");
		if (flag)
		{
			// flag为true直接跳转
			util.setKeyData(LinkUtil.XSGRKB, "TRUE");
			Intent intent = new Intent(MainActivity.this, CourseActivity.class);
			startActivity(intent);
		} else
		{
			// flag为false，则先判断是否获取过课表
			// 如果已经获取过课表，则跳转
			String keyData = util.getKeyData(LinkUtil.XSGRKB);
			if (keyData.equals("TRUE"))
			{
				Intent intent = new Intent(MainActivity.this,
						CourseActivity.class);
				startActivity(intent);
			} else
			{
				// 未获取则获取
				HttpUtil.getQuery(MainActivity.this, linkService,
						LinkUtil.XSGRKB, new QueryCallback()
						{
							@Override
							public String handleResult(byte[] result)
							{
								String ret = null;
								try
								{
									ret = courseService.parseCourse(new String(
											result, "gb2312"));
								} catch (UnsupportedEncodingException e)
								{
									e.printStackTrace();
								}
								jump2Kb(true);
								return ret;
							}
						});
			}
		}
	}
	private void initMenuFragment()
	{
		MenuParams menuParams = new MenuParams();
		menuParams.setActionBarSize((int) getResources().getDimension(
				R.dimen.tool_bar_height));
		menuParams.setMenuObjects(getMenuObjects());
		menuParams.setClosableOutside(false);
		mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
		mMenuDialogFragment.setItemClickListener(this);
	}
	private List<MenuObject> getMenuObjects()
	{
		List<MenuObject> menuObjects = new ArrayList<MenuObject>();
		MenuObject close = new MenuObject();
		close.setResource(R.drawable.close);
		MenuObject share = new MenuObject("分享");
		share.setResource(R.drawable.share);
		MenuObject logoff = new MenuObject("退出登录");
		logoff.setResource(R.drawable.logoff);
		MenuObject exit = new MenuObject("退出应用");
		exit.setResource(R.drawable.exit);
		MenuObject feedback = new MenuObject("反馈");
		feedback.setResource(R.drawable.feedback);
		MenuObject about = new MenuObject("关于");
		about.setResource(R.drawable.about);
		menuObjects.add(close);
		menuObjects.add(share);
		menuObjects.add(logoff);
		menuObjects.add(exit);
		menuObjects.add(feedback);
		menuObjects.add(about);
		return menuObjects;
	}
	private void initToolbar()
	{
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "accountInfo");
		String name = util.getKeyData("name");
		Log.d("学生姓名", name);
		if (name != null)
		{
			mToolBarTextView.setText("hello," + name);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.context_menu :
				if (fragmentManager
						.findFragmentByTag(ContextMenuDialogFragment.TAG) == null)
				{
					mMenuDialogFragment.show(fragmentManager,
							ContextMenuDialogFragment.TAG);
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onMenuItemClick(View clickedView, int position)
	{
		switch (position)
		{
			case SHARE :
				share();
				break;
			case LOGOFF :
				logoff();
				break;
			case EXIT :
				exit();
				break;
			case FEEDBACK :
				feedback();
				break;
			case ABOUT :
				about();
				break;
		}
	}
	/**
	 * 打开反馈界面
	 */
	private void feedback()
	{
		startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
	}
	/**
	 * 打开关于界面
	 */
	private void about()
	{
		startActivity(new Intent(MainActivity.this, AboutActivity.class));
	}
	/**
	 * 退出应用
	 */
	private void exit()
	{
		new AlertDialog.Builder(MainActivity.this)
				.setMessage("确定要退出应用吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						MobclickAgent.onKillProcess(getApplicationContext());
						android.os.Process.killProcess(android.os.Process
								.myPid());
						System.exit(0);
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
	 * 退出登录
	 */
	private void logoff()
	{
		new AlertDialog.Builder(MainActivity.this)
				.setMessage("确定要退出登录吗？退出登录后,您存储在手机里的信息将会删除。")
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						DataSupport.deleteAll(Exam.class);
						DataSupport.deleteAll(Course.class);
						DataSupport.deleteAll(LinkNode.class);
						FileUtils.delete("CookiePrefsFile.xml");
						startActivity(new Intent(MainActivity.this,
								LoginActivity.class));
						finish();
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
	private void share()
	{
		initSocialSDk();
		qqShare();
		qZoneShare();
		WeiXinShare();
		circleShare();
		weiBoshare();
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
				SHARE_MEDIA.SINA);
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
				SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT);
		mController.openShare(MainActivity.this, false);
	}
	/**
	 * 微博分享
	 */
	private void weiBoshare()
	{
		SinaShareContent shareContent = new SinaShareContent();
		shareContent.setShareContent("看课表、查自习室、考试安排、考试成绩，一键完成！中财人自己的app！  "
				+ "http://www.wandoujia.com/apps/com.brooks.cufer?"
				+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		shareContent.setTitle("cufePal");
		shareContent.setShareImage(new UMImage(MainActivity.this,
				R.drawable.icon));
		shareContent
				.setTargetUrl("http://www.wandoujia.com/apps/com.brooks.cufer?"
						+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		mController.setShareMedia(shareContent);
	}
	/**
	 * 微信朋友圈分享
	 */
	private void circleShare()
	{
		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent("看课表、查自习室、考试安排、考试成绩，一键完成！中财人自己的app！");
		// 设置朋友圈title
		circleMedia.setTitle("cufePal");
		// 设置分享图片
		circleMedia.setShareImage(new UMImage(MainActivity.this,
				R.drawable.icon));
		circleMedia
				.setTargetUrl("http://www.wandoujia.com/apps/com.brooks.cufer?"
						+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		mController.setShareMedia(circleMedia);
	}
	/**
	 * 微信分享
	 */
	private void WeiXinShare()
	{
		// 设置微信好友分享内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置分享文字
		weixinContent.setShareContent("看课表、查自习室、考试安排、考试成绩，一键完成！中财人自己的app！");
		// 设置title
		weixinContent.setTitle("cufePal");
		// 设置分享内容跳转URL
		weixinContent
				.setTargetUrl("http://www.wandoujia.com/apps/com.brooks.cufer?"
						+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		// 设置分享图片
		weixinContent.setShareImage(new UMImage(MainActivity.this,
				R.drawable.icon));
		mController.setShareMedia(weixinContent);
	}
	/**
	 * qq空间分享
	 */
	private void qZoneShare()
	{
		QZoneShareContent qZoneShareContent = new QZoneShareContent();
		qZoneShareContent.setTitle("cufePal");
		qZoneShareContent.setShareContent("看课表、查自习室、考试安排、考试成绩，一键完成！中财人自己的app！");
		qZoneShareContent
				.setTargetUrl("http://www.wandoujia.com/apps/com.brooks.cufer?"
						+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		qZoneShareContent.setShareMedia(new UMImage(MainActivity.this,
				R.drawable.icon));
		mController.setShareMedia(qZoneShareContent);
	}
	/**
	 * qq分享
	 */
	private void qqShare()
	{
		// 设置qq分享内容
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setTitle("cufePal");
		qqShareContent.setShareContent("看课表、查自习室、考试安排、考试成绩，一键完成！中财人自己的app！");
		qqShareContent
				.setTargetUrl("http://www.wandoujia.com/apps/com.brooks.cufer?"
						+ "utm_source=share_intent&utm_campaign=app&utm_medium=p4");
		qqShareContent.setShareMedia(new UMImage(MainActivity.this,
				R.drawable.icon));
		mController.setShareMedia(qqShareContent);
	}
	/**
	 * 初始化分享SDK
	 */
	private void initSocialSDk()
	{
		// qq分享
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(MainActivity.this,
				"1105005022", "ZaST69eDy6p2p5wq");
		qqSsoHandler.addToSocialSDK();
		// qq空间的分享
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				MainActivity.this, "1105005022", "ZaST69eDy6p2p5wq");
		qZoneSsoHandler.addToSocialSDK();
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(MainActivity.this,
				"wx84ec414045cbeecd", "2307a38a273c676b10d14ede7372dbd9");
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(MainActivity.this,
				"wx84ec414045cbeecd", "2307a38a273c676b10d14ede7372dbd9");
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
	}
	/**
	 * 如果有使用新浪、人人的SSO授权或者集成了facebook平台, 则必须在对应的activity中实现onActivityResult方法,
	 * 并添加如下代码
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null)
		{
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
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
