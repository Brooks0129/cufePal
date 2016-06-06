package com.brooks.cufer.adapter;
import java.util.List;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Exam;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
 * @author 李松达(Brooks)
 *
 * @创建日期 2015年12月1日
 *
 * @描述 考试科目ListView的适配器
 */
public class ExamAdapter extends BaseAdapter
{
	Context mContext;
	List<Exam> exams;
	public ExamAdapter(Context conext, List<Exam> exams)
	{
		this.mContext = conext;
		this.exams = exams;
	}
	@Override
	public int getCount()
	{
		return exams.size();
	}
	@Override
	public Object getItem(int position)
	{
		return exams.get(position);
	}
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.exam_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_exam_name = (TextView) convertView
					.findViewById(R.id.tv_exam_name);
			viewHolder.tv_exam_time = (TextView) convertView
					.findViewById(R.id.tv_exam_time);
			viewHolder.tv_exam_location = (TextView) convertView
					.findViewById(R.id.tv_exam_location);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_exam_location.setText(exams.get(position)
				.getExamLocation());
		viewHolder.tv_exam_name.setText(exams.get(position).getCourseName());
		viewHolder.tv_exam_time.setText(exams.get(position).getExamTime());
		return convertView;
	}
	static class ViewHolder
	{
		TextView tv_exam_name;
		TextView tv_exam_location;
		TextView tv_exam_time;
	}
}
