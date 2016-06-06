package com.brooks.cufer.util;
import android.app.ProgressDialog;
import android.content.Context;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年11月27日
 *
 * @描述 公共工具类
 */
public class CommonUtil
{
	/**
	 * 获得指定范围内的随机数
	 * 
	 * @param max
	 * @return int
	 */
	public static int getRandom(int max)
	{
		return (int) (Math.random() * max);
	}
	/**
	 * 返回一个对话框
	 * 
	 * @param context
	 * @param tips
	 * @return
	 */
	public static ProgressDialog getProcessDialog(Context context, String tips)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(tips);
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return dialog;
	}
}
