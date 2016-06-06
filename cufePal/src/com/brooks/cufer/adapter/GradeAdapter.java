package com.brooks.cufer.adapter;
import java.util.ArrayList;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Grade;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class GradeAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<Grade> mGrades;
	public GradeAdapter(Context mContext, ArrayList<Grade> mGrades)
	{
		this.mContext = mContext;
		this.mGrades = mGrades;
	}
	@Override
	public int getCount()
	{
		return mGrades.size();
	}
	@Override
	public Object getItem(int position)
	{
		return mGrades.get(position);
	}
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.item_grade, null);
			convertView = view;
			holder = new ViewHolder();
			holder.tv_dj = (TextView) view.findViewById(R.id.tv_dj);
			holder.tv_kcmc = (TextView) view.findViewById(R.id.tv_kcmc);
			holder.tv_kcxz = (TextView) view.findViewById(R.id.tv_kcxz);
			holder.tv_xf = (TextView) view.findViewById(R.id.tv_xf);
			holder.tv_jd = (TextView) view.findViewById(R.id.tv_jd);
			holder.tv_fs = (TextView) view.findViewById(R.id.tv_fs);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_dj.setText(mGrades.get(position).getDj());
		holder.tv_kcmc.setText(mGrades.get(position).getKcmc());
		holder.tv_kcxz.setText(mGrades.get(position).getKcxz());
		holder.tv_xf.setText(mGrades.get(position).getXf());
		holder.tv_jd.setText(mGrades.get(position).getJd());
		holder.tv_fs.setText(mGrades.get(position).getFs());
		return convertView;
	}
	static class ViewHolder
	{
		private TextView tv_kcmc;
		private TextView tv_xf;
		private TextView tv_kcxz;
		private TextView tv_dj;
		private TextView tv_jd;
		private TextView tv_fs;
	}
}
