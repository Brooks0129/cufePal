package com.brooks.cufer.service;
public class ClassroomService
{
	private static volatile ClassroomService classroomService;
	private ClassroomService()
	{
	}
	public static ClassroomService getClassroomService()
	{
		if (classroomService == null)
		{
			synchronized (ClassroomService.class)
			{
				if (classroomService == null)
					classroomService = new ClassroomService();
			}
		}
		return classroomService;
	}
	
}
