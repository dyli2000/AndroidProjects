package sam.sucl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Environment;

public class CFileService 
{
	private Context context;

	public CFileService(Context context) 
	{
		this.context = context;
	}

	/**
	 * �����ļ�
	 * @param filename �ļ�����
	 * @param content �ļ�����
	 * @throws Exception
	 */
	public void save(String filename, String content) throws Exception
	{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveAppend(String filename, String content) throws Exception{// ctrl+shift+y/x
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_APPEND);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveReadable(String filename, String content) throws Exception
	{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveWriteable(String filename, String content) throws Exception
	{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveRW(String filename, String content) throws Exception
	{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE+ Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ��ȡ�ļ�����
	 * @param filename �ļ�����
	 * @return �ļ�����
	 * @throws Exception
	 */
	public String ReadFromSDCard(String filename) throws Exception
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// The error usage.
		// FileInputStream inStream = context.openFileInput(Environment.getExternalStorageDirectory().toString()+"/" + filename);
		FileInputStream inStream = new FileInputStream(Environment.getExternalStorageDirectory() +"/" + filename);
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		byte[] data = outStream.toByteArray();
		return new String(data);
	}
	
	/**
	 * ���ļ����浽SDCard��
	 * @param filename �ļ�����
	 * @param content �ļ�����
	 * @throws Exception
	 */
	public void saveToSDCard(String filename, String content) throws Exception
	{
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}
}

