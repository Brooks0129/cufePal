package com.brooks.cufer.activity;
import java.util.List;
import com.brooks.cufer.R;
import com.brooks.cufer.model.Course;
import com.brooks.cufer.service.CourseService;
import com.brooks.cufer.util.CommonUtil;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
/**
 * 
 * @author ���ɴ�(Brooks)
 *
 * @�������� 2015��11��28��
 *
 * @����
 */
public class CourseActivity extends Activity
{
	// �γ�ҳ���button���ã�6��7��
	private int[][] lessons = {
			{R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14,
					R.id.lesson15, R.id.lesson16, R.id.lesson17},
			{R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24,
					R.id.lesson25, R.id.lesson26, R.id.lesson27},
			{R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34,
					R.id.lesson35, R.id.lesson36, R.id.lesson37},
			{R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44,
					R.id.lesson45, R.id.lesson46, R.id.lesson47},
			{R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54,
					R.id.lesson55, R.id.lesson56, R.id.lesson57},
			{R.id.lesson61, R.id.lesson62, R.id.lesson63, R.id.lesson64,
					R.id.lesson65, R.id.lesson66, R.id.lesson67},};
	// ĳ�ڿεı���ͼ,���������ȡ
	private int[] bg = {R.drawable.kb1, R.drawable.kb2, R.drawable.kb3,
			R.drawable.kb4, R.drawable.kb5, R.drawable.kb6, R.drawable.kb7,
			R.drawable.kb8, R.drawable.kb9, R.drawable.kb10, R.drawable.kb11,
			R.drawable.kb12, R.drawable.kb13};
	private CourseService courseService;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		initValue();
		initView();
	}
	/**
	 * ��ʼ������
	 */
	private void initValue()
	{
		courseService = CourseService.getCourseService();
	}
	/**
	 * ��ʼ����ͼ
	 */
	private void initView()
	{
		// �������߼����⣬ֻ�Ǽ򵥵���ʾ�������ݣ����ݲ���һ������ʾ����ȷλ��
		// �γ̿������ص�
		// �γ̿�����1�ڿεģ�2�ڿεģ�3�ڿεģ��������Ӧ�øĳ����Զ���View����ʾ������
		List<Course> courses = courseService.findAll();// ������ݿ��еĿγ�
		Course course = null;
		// ѭ������
		for (int i = 0; i < courses.size(); i++)
		{
			course = courses.get(i);// �õ���ǰ�γ�
			int dayOfWeek = course.getDayOfWeek() - 1;// ת��Ϊlessons�����Ӧ���±�
			int section = course.getStartSection() / 2;// ת��Ϊlessons�����Ӧ���±�
			Button lesson = (Button) findViewById(lessons[section][dayOfWeek]);// ��øýڿε�button
			int bgRes = bg[CommonUtil.getRandom(bg.length - 1)];// �����ȡ����ɫ
			lesson.setBackgroundResource(bgRes);// ���ñ���
			lesson.setText(course.getCourseName() + "@"
					+ course.getClasssroom());// �����ı�Ϊ�γ���+��@��+����
		}
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
