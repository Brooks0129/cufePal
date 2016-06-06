package com.brooks.cufer.model;

import org.litepal.crud.DataSupport;

public class Exam extends DataSupport
{
	private String courseNumber;
	private String courseName;
	private String name;
	private String examTime;
	private String examLocation;
	private String examType;
	private String seatNumber;
	private String campus;
	public String getCourseNumber()
	{
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber)
	{
		this.courseNumber = courseNumber;
	}
	public String getCourseName()
	{
		return courseName;
	}
	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getExamTime()
	{
		return examTime;
	}
	public void setExamTime(String examTime)
	{
		this.examTime = examTime;
	}
	public String getExamLocation()
	{
		return examLocation;
	}
	public void setExamLocation(String examLocation)
	{
		this.examLocation = examLocation;
	}
	public String getExamType()
	{
		return examType;
	}
	public void setExamType(String examType)
	{
		this.examType = examType;
	}
	public String getSeatNumber()
	{
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber)
	{
		this.seatNumber = seatNumber;
	}
	public String getCampus()
	{
		return campus;
	}
	public void setCampus(String campus)
	{
		this.campus = campus;
	}
	@Override
	public String toString()
	{
		return "Exam [courseNumber=" + courseNumber + ", courseName="
				+ courseName + ", name=" + name + ", examTime=" + examTime
				+ ", examLocation=" + examLocation + ", examType=" + examType
				+ ", seatNumber=" + seatNumber + ", campus=" + campus + "]";
	}
	
}
