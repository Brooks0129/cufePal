package com.brooks.cufer.service;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;
import android.app.Fragment.SavedState;
import android.util.Log;
import com.brooks.cufer.model.Course;
import com.brooks.cufer.model.Exam;
import com.brooks.cufer.util.FileUtils;
public class ExamService
{
	private static volatile ExamService examService;
	private ExamService()
	{
	}
	public static ExamService getExamService()
	{
		if (examService == null)
		{
			synchronized (ExamService.class)
			{
				if (examService == null)
					examService = new ExamService();
			}
		}
		return examService;
	}
	/**
	 * 解析网页返回的数据
	 * 
	 * @param string
	 * @return
	 */
	public void parseExam(String content) throws Exception
	{
		//FileUtils.write(content);
		Document document = Jsoup.parse(content);
		Elements table = document.select("table[id=DataGrid1]");
		Element tbody = table.get(0);
		Elements trs = tbody.getElementsByTag("tr");
		if (trs!=null&&trs.size()!=0)
		{
			int i = 0;
			for (Element element : trs)
			{
				if (i != 0)
				{
					Elements tds = element.getElementsByTag("td");
					Exam exam = new Exam();
					exam.setCourseNumber(tds.get(0).text());
					exam.setCourseName(tds.get(1).text());
					exam.setName(tds.get(2).text());
					exam.setExamTime(tds.get(3).text());
					exam.setExamLocation(tds.get(4).text());
					exam.setExamType(tds.get(5).text());
					exam.setSeatNumber(tds.get(6).text());
					exam.setCampus(tds.get(7).text());
					Log.d("exam", exam.toString());
					save(exam);
				}
				i++;
			}
		}

	}
	/**
	 * 保存一门开始
	 * 
	 * @param exam
	 */
	private void save(Exam exam)
	{
		exam.save();
	}
	/**
	 * 返回数据库中的所有考试科目
	 * 
	 * @return
	 */
	public List<Exam> findAll()
	{
		return DataSupport.findAll(Exam.class);
	}
	public void deleteAll(){
		DataSupport.deleteAll(Exam.class);
	}
	
}
