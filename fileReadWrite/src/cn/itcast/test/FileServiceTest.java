package cn.itcast.test;

import cn.itcast.service.FileService;
import android.test.AndroidTestCase;
import android.util.Log;

public class FileServiceTest extends AndroidTestCase 
{
	private static final String TAG = "FileServiceTest";
	
	public void testRead() throws Exception{
		FileService service = new FileService(getContext());
		String result = service.read("liming.txt");
		Log.i(TAG, result);
	}
	
	public void testSaveAppend() throws Exception{
		FileService service = new FileService(getContext());
		service.saveAppend("append.txt", ",www.itcast.cn");
	}
	
	public void testSaveReadable() throws Exception{
		FileService service = new FileService(getContext());
		service.saveReadable("readable.txt", "readable");
	}
	
	public void testSaveWriteable() throws Exception{
		FileService service = new FileService(getContext());
		service.saveWriteable("writeable.txt", "writeable");
	}
	
	public void testSaveRW() throws Exception{
		FileService service = new FileService(getContext());
		service.saveRW("rw.txt", "read-write");
	}
}
