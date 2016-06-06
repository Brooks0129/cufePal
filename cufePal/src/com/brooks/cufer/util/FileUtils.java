package com.brooks.cufer.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import android.os.Environment;
public class FileUtils
{
	public static void write(String content)
	{
		File file = new File(Environment.getExternalStorageDirectory(),
				"a.html");
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void delete(String fileName){
		File file=new File("/data/data/com.brooks.cufer/shared_prefs/"+fileName);
		file.delete();
	}
}
