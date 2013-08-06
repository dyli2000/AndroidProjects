package sam.sucl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;



public class CDatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "questionBank.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Subject";
	private static final String SID = "SubjectId";
	private static final String BODY = "SubjectBody";
	private static final String ANSWER  = "Answer";
	private static final String IS_CORRECTED = "IsCorrected";  
	private boolean mIsCreateDB = true;
	
	CDatabaseHelper(Context context)
	{
		/* ͨ��DatabaseHelper ��������database  */
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		try
		{
			/* ���ǵڶ������캯��ʹ�õ�������������ר�����������ݵġ� */
			if(!this.mIsCreateDB)
				return;
			
			String sql = "CREATE TABLE " + TABLE_NAME + " (" 
							  + SID + " int not null, " 
							  + BODY + " text not null, " 
							  +  ANSWER +" text no null," 
							  +  IS_CORRECTED + " int no null"
							  + ");";
			db.execSQL(sql);
		}
		catch(Exception e)
		{
			Log.i("In onCreate .... ",e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
    /** *************************************************************************************************
     *       DESC :     ���������ʽ��DB��ȡ��һ����¼
     *       ARGC :
     *          subject:		Bitmap����
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/     
	
	public static void GetSubjects(GlobalData subject)
	{
		//subject = readRecord();
	}
	
	
	
	/*
	 * ����Ļ��title������ʾ��ǰ���ݱ��е����ݵ�������
	 */
	private void readRecord() 
	{
			SQLiteDatabase db =  this.getReadableDatabase();  
			String result = "";
			Cursor cur = db.query(TABLE_NAME, null, null, null, null, null, null);
			int  sidIndex = cur.getColumnIndex("SubjectId");
			int bodyIndex = cur.getColumnIndex("SubjectBody");
			int answerIndex = cur.getColumnIndex("Answer");
			int isOkIndex = cur.getColumnIndex("IsCorrected");
			
			List listSubjects = new ArrayList();
			
			if(cur == null)
				return; 
			for(cur.moveToFirst(); !(cur.isAfterLast());cur.moveToNext())
			{
				GlobalData tmpSubject = new GlobalData();
				tmpSubject.SetAttributes(cur.getInt(sidIndex), cur.getString(bodyIndex), cur.getString(answerIndex), cur.getInt(isOkIndex));
				listSubjects.add(tmpSubject);
			}
			
			Random random = new Random(listSubjects.size());
			int index = random.nextInt();
			GlobalData subject = (GlobalData)listSubjects.get(index);
	}	
	
	
};
