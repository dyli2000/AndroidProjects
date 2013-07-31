package com.eoeAndroid.SQLite;

//import cn.itcast.file.R;
//import cn.itcast.service.FileService;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMain extends Activity
{
	OnClickListener mListener1 = null;
	OnClickListener mListener2 = null;
	OnClickListener mListener3 = null;
	OnClickListener mListener4 = null;
	OnClickListener mListener5 = null;
	OnClickListener mListenSaveBtn = null;
	OnClickListener mListenOpenBtn = null;
	
	Button mButton1;
	Button mButton2;
	Button mButton3;
	Button mButton4;
	Button mButton5;
	Button mSaveBtn;
	Button mOpenBtn;
	
	TextView mShowDataTView;
	DatabaseHelper mOpenHelper;
	EditText mNameText;
	EditText  mContentText;
	
	private static final String DATABASE_NAME = "questionBank.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Subject";
	private static final String SID = "SubjectId";
	private static final String BODY = "SubjectBody";
	private static final String ANSWER  = "Answer";
	private static final String IS_CORRECTED = "IsCorrected";   
	
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
			try
			{
				String sql = "CREATE TABLE " + TABLE_NAME + " (" 
								  + SID + " int not null, " 
								  + BODY + " text not null, " 
								  +  ANSWER +" text no null," 
								  +  IS_CORRECTED + " int no null"
								  + ");";
				
				Log.i("giggle:createDB=", sql);
				db.execSQL(sql);
			}
			catch(Exception e)
			{
				//setTitle(e.toString());
				Log.i("In onCreate .... ",e.toString());
			}
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
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			prepareListener();       				// ��װ�ø����¼�
			initLayout();
			this.mShowDataTView.clearComposingText();
			mOpenHelper = new DatabaseHelper(this);
		}
		catch(Exception e)
		{
			this.mContentText.setText(e.toString());
		}
	}

	private void initLayout() 
	{
		try
		{
			mButton1 = (Button) findViewById(R.id.button1);
			mButton1.setOnClickListener(mListener1);
	
			mButton2 = (Button) findViewById(R.id.button2);
			mButton2.setOnClickListener(mListener2);
	
			mButton3 = (Button) findViewById(R.id.button3);
			mButton3.setOnClickListener(mListener3);
			mButton4 = (Button) findViewById(R.id.button4);
			mButton4.setOnClickListener(mListener4);
	
			mButton5 = (Button) findViewById(R.id.button5);
			mButton5.setOnClickListener(mListener5);
		
			mSaveBtn = (Button) findViewById(R.id.button7);
			mSaveBtn.setOnClickListener(mListenSaveBtn);
			mOpenBtn = (Button) findViewById(R.id.button8);
			mOpenBtn.setOnClickListener(mListenOpenBtn);
			
			this.mNameText = (EditText)findViewById(R.id.nameText);
			this.mContentText = (EditText)findViewById(R.id.contentText);
			
			mShowDataTView = (TextView)findViewById(R.id.textView1);
			this.mShowDataTView.setMovementMethod(ScrollingMovementMethod.getInstance());  // ����textView�ɹ���
		}
		catch(Exception e)
		{
			setTitle(e.toString());
		}
	}

	//��ť��Ӧ����
	private void prepareListener() 
	{
		try
		{
			mListener1 = new OnClickListener() 
			{
				public void onClick(View v) 
				{
					createTable();
				}
			};
			
			mListener2 = new OnClickListener() 
			{
				public void onClick(View v) 
				{
					dropTable();
				}
			};
			
			mListener3 = new OnClickListener() 
			{
				public void onClick(View v) 
				{
					insertItem();
				}
			};
			
			mListener4 = new OnClickListener() 
			{
				public void onClick(View v) 
				{
					deleteItem();
				}
			};
			
			mListener5 = new OnClickListener() 
			{
				public void onClick(View v) 
				{
					showItems();
				}
			};
			
			this.mListenSaveBtn = new OnClickListener()
			{
				public void onClick(View v)
				{
					saveTextToFile();
				}
			};
			
			this.mListenOpenBtn = new OnClickListener()
			{
				public void onClick(View v)
				{
					openText();
				}
			};
		
		}
		catch(Exception e)
		{
			setTitle(e.toString());
		}
	}

	
	private void insertRecords(String sqlcmdList) 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String[] lines = sqlcmdList.split("\n");
		try 
		{
			for(String line : lines)
			{
				mContentText.setText(line);
				db.execSQL(line);
			}
		} 
		catch (SQLException e) 
		{
			setTitle(e.toString());
		}
	}
	
	
	private void openText()
	{
		String fileName = mNameText.getText().toString();
		CFileService service = new CFileService(getApplicationContext());
		try 
		{
			String result = service.ReadFromSDCard(fileName);
			//mContentText.setText("Read string from " + fileName + "\n" + result);
			insertRecords(result);
		} 
		catch (Exception e) 
		{
			//setTitle(e.toString());
			setTitle(Environment.getExternalStorageDirectory().toString() + "/" + mNameText.getText());
			mContentText.setText(e.toString());
		}
	}
	
	private void saveTextToFile()
	{
		String filename = mNameText.getText().toString();      // Get file name.
		String content = mContentText.getText().toString();   // Get file content.
		CFileService service = new CFileService(getApplicationContext());
		setTitle("׼���洢�ļ���");
		try 
		{
			/* ����Ƿ����������SD�� */
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				service.saveToSDCard(filename, content);
				Toast.makeText(getApplicationContext(), R.string.success, 1).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.sdcardnoexsit, 1).show();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.fail, 1).show();
		}
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
						  +  IS_CORRECTED + " int no null"
						  + ");";
		
		Log.i("giggle:createDB=", sql);
		try 
		{
			db.execSQL("DROP TABLE IF EXISTS Subject");
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
		String sql1 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_CORRECTED +")" +
							" values(1,'���������ģʽ�ж�����?', 23, 0);";
		String sql2 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_CORRECTED +")" +
							" values(2,'NBA�������ж��ٳ�?', 81, 0);";		
		try 
		{
			Log.i("giggle:sql1=", sql1);
			Log.i("dungeon:sql2=", sql2);
			db.execSQL(sql1);
			db.execSQL(sql2);
			setTitle("�����������ݳɹ�");
			//this.showItems();
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
			db.delete(TABLE_NAME, " SubjectId = 1", null);
			setTitle("ɾ��SubjectIdΪ1�ļ�¼");
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
			int  sidIndex = cur.getColumnIndex("SubjectId");
			int bodyIndex = cur.getColumnIndex("SubjectBody");
			int answerIndex = cur.getColumnIndex("Answer");
			int isOkIndex = cur.getColumnIndex("IsCorrected");
			
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
			this.mShowDataTView.setText("Sid\t Body\t Answer\t IsCorrected\n"+result);
		}
		catch(Exception e)
		{
			setTitle("���ݿ��Ѿ���ɾ��!");
		}
	}	
}