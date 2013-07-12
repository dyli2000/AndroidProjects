package com.example.testsqlite01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	CMySQLiteHelper myHelper;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.tv);
		tv.clearComposingText();
		
		/* ����CMySQLiteHelper��������� */
		myHelper = new CMySQLiteHelper(this,"star.db",null,1);
		
		/* �����ݿ��в���͸������� */
		InsertAndUpdateData(myHelper);
		
		String result = QueryData(myHelper);
		tv.setTextColor(Color.RED);
		tv.setTextSize(20.0f);
		tv.setText("name\tballAge\n"+result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    /** *************************************************************************************************
    *       DESC :     �����ݿ��в���͸������ݡ�
    *       ARGC :
    *                      @myHelper:   CMySQLiteHelper ����
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	public void InsertAndUpdateData(CMySQLiteHelper myHelper)
	{
		/* ��ȡ���ݿ����  */
		SQLiteDatabase db = myHelper.getWritableDatabase();
		
		/* ʹ��execSQL��������в��� */
		db.execSQL("insert into StarInfo(name,ballAge) values('Kobe',15)");

		ContentValues values = new ContentValues();
		
		/*  ʹ��insert������������  */
		values.put("name", "James");
		values.put("ballAge",10);
		db.insert("StarInfo","id",values);
		
		/* ���ContentValues����  */	
		values.clear();	
		
		/*  ʹ��UPDATE�������±��е�����  */
		values.put("name", "Durant");
		values.put("ballAge", 6);
		db.update("StarInfo", values, "ballAge=10", null);  	// ��James��¼��Ϊ��Durant ballAge��Ϊ6
		
		/* �ر�SQLiteDatabase����  */
		db.close();		
	}
	
    /** *************************************************************************************************
    *       DESC :     ���ݿ����ݲ�ѯ
    *       ARGC :
    *                      @myHelper:   CMySQLiteHelper ����
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	public String QueryData(CMySQLiteHelper myHelper)
	{
		String result = "";
		SQLiteDatabase db  = myHelper.getReadableDatabase();	// ��ȡ���ݿ����

		/* ��ѯ�������  */
		Cursor cursor = db.query("StarInfo", null, null, null, null, null, "id asc");  
		/*  ��ȡ���� */
		int nameIndex = cursor.getColumnIndex("name");
		int ballAgeIndex = cursor.getColumnIndex("ballAge");
		
		for(cursor.moveToFirst(); !(cursor.isAfterLast());cursor.moveToNext())
		{
			result = result + cursor.getString(nameIndex) + "\t";
			result = result + cursor.getInt(ballAgeIndex) + "\n";
 		}
		
		/*  ��Դ�ͷ�  */
		cursor.close();	// �رս����
		db.close();			// �ر����ݿ����
		return result;
	}
	
    /** *************************************************************************************************
    *       DESC :     �����˳���ֻ�����ؼ�ʱ��ִ�У����������ϵġ���ҳ��������Ч�ģ�
    *       ARGC :
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	protected void onDestroy()
	{
		SQLiteDatabase db = myHelper.getWritableDatabase();  // ��ȡ���ݶ���
		/* ɾ��StarInfo�е��������ݣ�1 ��ʾɾ�������� */
		db.delete("StarInfo","1",null);
		super.onDestroy();
	}

}
