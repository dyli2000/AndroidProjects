package com.eoeAndroid.SQLite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ActivityMain extends Activity
{
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	OnClickListener listener3 = null;
	OnClickListener listener4 = null;
	OnClickListener listener5 = null;

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	TextView mShowDataTView;

	DatabaseHelper mOpenHelper;

	private static final String DATABASE_NAME = "dbForTest.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Subjects";
	private static final String SID = "Sid";
	private static final String BODY = "Body";
	private static final String ANSWER  = "Answer";
	private static final String IS_ANSWER_CORRECTLY = "IsAnswerCorrectly";   
	
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
		{
			/* ͨ��DatabaseHelper ��������database  */
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}


	    /** *************************************************************************************************
	     *       DESC :    ��SQLiteOpenHelper����database��ʱ���Զ�����
	     *       ARGC :
	     *                     @ db:        SQLiteOpenHelper �϶�ά��һ�����ݿ�Ķ���
	     *---------------------------------------------------------------------------------------------------
	     ****************************************************************************************************/    	
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String sql = "CREATE TABLE " + TABLE_NAME + " (" 
							  + SID + " int not null, " 
							  + BODY + " text not null, " 
							  +  ANSWER +" text no null," 
							  +  IS_ANSWER_CORRECTLY + " int no null"
							  + ");";
			
			Log.i("giggle:createDB=", sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
		}
	}

    /** *************************************************************************************************
     *       DESC :    Activity���󴴽�ʱ������
     *       ARGC :
     *                     @ savedInstanceState:    A mapping from String values to various parcelable types.     
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/    	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		prepareListener();       				// ��װ�ø����¼�
		initLayout();
		this.mShowDataTView.clearComposingText();
		mOpenHelper = new DatabaseHelper(this);
	}

	private void initLayout() 
	{
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);

		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(listener3);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(listener4);

		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(listener5);
		
		mShowDataTView = (TextView)findViewById(R.id.textView1);
		this.mShowDataTView.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	//��ť��Ӧ����
	private void prepareListener() 
	{
		listener1 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				createTable();
			}
		};
		
		listener2 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				dropTable();
			}
		};
		
		listener3 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				insertItem();
			}
		};
		
		listener4 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				deleteItem();
			}
		};
		
		listener5 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				showItems();
			}
		};
	}

	/*
	 * ���½������ݱ�
	 */
	private void createTable() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "CREATE TABLE " + TABLE_NAME + " (" 
						  + SID + " int not null, " 
						  + BODY + " text not null, " 
						  +  ANSWER +" text no null," 
						  +  IS_ANSWER_CORRECTLY + " int no null"
						  + ");";
		
		Log.i("giggle:createDB=", sql);
		try 
		{
			db.execSQL("DROP TABLE IF EXISTS Subjects");
			db.execSQL(sql);
			setTitle("���ݱ�ɹ��ؽ�");
			this.showItems();
		} 
		catch (SQLException e) 
		{
			setTitle("���ݱ��ؽ�����");
		}
	}

	/*
	 * ɾ�����ݱ�
	 */
	private void dropTable() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "drop table " + TABLE_NAME;
		try 
		{
			db.execSQL(sql);
			setTitle("���ݱ�ɹ�ɾ����" + sql);
			this.mShowDataTView.setTextColor(Color.RED);
			this.mShowDataTView.setTextSize(20.0f);
			this.mShowDataTView.setText("Sid\t Body\t Answer\t IsAnswerd\n");
		} 
		catch (SQLException e) 
		{
			setTitle("���ݱ�ɾ������");
		}
	}

	/*
	 * ������������
	 */
	private void insertItem() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql1 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_ANSWER_CORRECTLY +")" +
							" values(1,'���������ģʽ�ж�����?', 23, 0);";
		String sql2 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_ANSWER_CORRECTLY +")" +
							" values(2,'NBA�������ж��ٳ�?', 81, 0);";		
		try 
		{
			Log.i("giggle:sql1=", sql1);
			Log.i("dungeon:sql2=", sql2);
			db.execSQL(sql1);
			db.execSQL(sql2);
			setTitle("�����������ݳɹ�");
			this.showItems();
		} 
		catch (SQLException e) 
		{
			setTitle("������������ʧ��");
		}
	}

	/*
	 * ɾ�����е�һ������
	 */
	private void deleteItem() 
	{
		try 
		{
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			db.delete(TABLE_NAME, " Sid = 1", null);
			setTitle("ɾ��SidΪ1�ļ�¼");
			this.showItems();
		} 
		catch (SQLException e) {}
	}

	/*
	 * ����Ļ��title������ʾ��ǰ���ݱ��е����ݵ�������
	 */
	private void showItems() 
	{
		try
		{
			SQLiteDatabase db = mOpenHelper.getReadableDatabase();
			String result = "";
			Cursor cur = db.query(TABLE_NAME, null, null, null, null, null, null);
			int  sidIndex = cur.getColumnIndex("Sid");
			int bodyIndex = cur.getColumnIndex("Body");
			int answerIndex = cur.getColumnIndex("Answer");
			int isOkIndex = cur.getColumnIndex("IsAnswerCorrectly");
			
			if(cur == null)
				return;
			for(cur.moveToFirst(); !(cur.isAfterLast());cur.moveToNext())
			{
				result = result + cur.getInt(sidIndex) + "\t";
				result = result + cur.getString(bodyIndex) + "\t";
				result = result + cur.getString(answerIndex) + "\t";
				result = result + cur.getInt(isOkIndex) + "\n";
			}
			
			Integer num = cur.getCount();
			setTitle(Integer.toString(num) + " ����¼");	
			this.mShowDataTView.setTextColor(Color.RED);
			this.mShowDataTView.setTextSize(20.0f);
			this.mShowDataTView.setText("Sid\t Body\t Answer\t IsAnswerd\n"+result);
		}
		catch(Exception e)
		{
			setTitle("���ݿ��Ѿ���ɾ��!");
		}
	}	
}