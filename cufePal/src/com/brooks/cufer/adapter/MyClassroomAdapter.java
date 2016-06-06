package com.brooks.cufer.adapter;
import java.util.ArrayList;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Classroom;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class MyClassroomAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<Classroom> mClassrooms = null;
	public MyClassroomAdapter(ArrayList<Classroom> classrooms, Context mContext)
	{
		this.mClassrooms = classrooms;
		this.mContext = mContext;
	}
	@Override
	public int getCount()
	{
		return mClassrooms.size();
	}
	@Override
	public Object getItem(int position)
	{
		return mClassrooms.get(position);
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
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_classroom, null);
			holder.tv_jslb = (TextView) convertView.findViewById(R.id.tv_jslb);
			holder.tv_jsmc = (TextView) convertView.findViewById(R.id.tv_jsmc);
			holder.tv_skzws = (TextView) convertView
					.findViewById(R.id.tv_skzws);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_jslb.setText(mClassrooms.get(position).getJslb());
		holder.tv_jsmc.setText(mClassrooms.get(position).getJsmc());
		holder.tv_skzws.setText(mClassrooms.get(position).getSkzws());
		return convertView;
	}
	static class ViewHolder
	{
		private TextView tv_jsmc;
		private TextView tv_jslb;
		private TextView tv_skzws;
	}
}
