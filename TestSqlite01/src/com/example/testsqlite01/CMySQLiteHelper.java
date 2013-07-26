package com.example.testsqlite01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class CMySQLiteHelper  extends SQLiteOpenHelper
{
	public CMySQLiteHelper(Context contex,String name,CursorFactory factory,int version)
	{
		super(contex,name,factory,version);
	}

    /** *************************************************************************************************
    *       DESC :     当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行. 
    *       				重写onCreate方法，调用execSQL方法创建表. 
    *       ARGC :
    *                      @db:  
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("create table if not exists StarInfo( "+"id integer primary key," + "name varchar," + "ballAge integer)" ); 
	}

    /** *************************************************************************************************
    *       DESC :     当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
    *       ARGC :
    *                      @db:  
    *                      @oldVersion:
    *                      @newVersion:
    *       RET  :       Success,true; Fail,false.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	}
}

