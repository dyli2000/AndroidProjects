package sam.sucl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

//import net.sourceforge.jtds.jdbc.Driver;

/* ��Ҫ��������������ĳ�ʼ���� */
public class CutAnimActivity extends   Activity 
{
	private static final String DATABASE_NAME = "questionBank.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Subject";
	private static final String SID = "SubjectId";
	private static final String BODY = "SubjectBody";
	private static final String ANSWER  = "Answer";
	private static final String IS_CORRECTED = "IsCorrected";   
	
	OnClickListener mImportListener;
	OnClickListener mSetErrorListener;
	OnClickListener mEnterListener;
	
	Context context = null;
    String UserName = "root";
    String Password = "";
    CDatabaseHelper mOpenHelper = null;
    
    Button mEnterBtn = null;
    Button mImportBtn = null;
    Button mSetErrorBtn = null;
    EditText mNameText = null;
	
	public static class CDatabaseHelper extends SQLiteOpenHelper
	{
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
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	};

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context=this;
        this.mOpenHelper = new CDatabaseHelper(this);  // ����ȫ�����ݿ���
        
        /*  ��ʼ��һЩ�ؼ������¼��Ȳ���,
         *  �������������밴������ĵ���˳����е��� */      
        this.PrepareListener();
        this.InitControls();
    }
    
    public void InitControls()
    {
    	mEnterBtn=(Button)findViewById(R.id.button1);
    	this.mEnterBtn.setOnClickListener(this.mEnterListener);
    	
    	mImportBtn = (Button)findViewById(R.id.importBtn);
    	this.mImportBtn.setOnClickListener(this.mImportListener);
    	
    	mSetErrorBtn = (Button)findViewById(R.id.setErrorBtn);
    	this.mSetErrorBtn.setOnClickListener(this.mSetErrorListener);
    	
    	mNameText = (EditText)findViewById(R.id.nameText);
    }
    
    public void PrepareListener()
    {
    	this.mEnterListener = new OnClickListener()
    	{
            /* ��Buttonע��һ���ص�����������onClick�¼�����ʱִ��.
             * ע�⣡������һ����䣬����һ������  */
    		public void onClick(View v)
    		{
				/*  ������ʹ��Cutact */
				Intent intent=new Intent(context, Cutact.class);
				startActivity(intent);
    		}
    	};
    	
    	this.mImportListener = new OnClickListener()
    	{
			public void onClick(View v)
			{
				insertSqlcmd();
			}
    	};
    	
    	this.mSetErrorListener = new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			resetAllAnswers();
    		}
    	};
    }
    
    private void insertSqlcmd()
    {
    	String fileName = this.mNameText.getText().toString();
    	CFileService server = new CFileService(getApplicationContext());
    	SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
    	try
    	{
    		String result = server.ReadFromSDCard(fileName);
    		String[] lines = result.split("\n");
    		for(String line : lines)
    		{
    			db.execSQL(line);
    		}
    		Toast.makeText(getApplicationContext(), "��������ݳɹ�", 1).show();
    		showItems();
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getApplicationContext(), e.toString(), 1).show();
    	}
    }
    
    private void resetAllAnswers()
    {
    	try
    	{
	    	String sqlcmd = "UPDATE Subject SET IsCorrected=0;";
	    	SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
	    	db.execSQL(sqlcmd);
	    	Toast.makeText(getApplicationContext(), "���ô𰸳ɹ�", 1).show();
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getApplicationContext(), e.toString(), 1).show();
    	}
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
		}
		catch(Exception e)
		{
			setTitle("���ݿ��Ѿ���ɾ��!");
		}
	}	
    
}